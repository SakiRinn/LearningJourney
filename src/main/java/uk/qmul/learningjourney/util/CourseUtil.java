package uk.qmul.learningjourney.util;

import uk.qmul.learningjourney.Context;
import uk.qmul.learningjourney.model.Course;
import uk.qmul.learningjourney.model.user.Student;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A Utility class for course operation
 *
 * @author Zekai Liu
 * @date 2023/05/25
 */
public class CourseUtil {

    /**
     * Get {@link Course} from Course ID
     *
     * @param courseId
     * @return {@link Course}
     * @throws IOException
     */
    public static Course getCourse(String courseId) throws IOException {
        ArrayList<Course> courses;
        courses = (ArrayList<Course>) DataIO.loadObjects(Course.class);
        for (Course course : courses) {
            if (course.getId().equals(courseId))
                return course;
        }
        return null;
    }

    /**
     * @return {@link ArrayList}<{@link Course}>
     * @throws IOException
     */
    public static ArrayList<Course> getAvailCourses() throws IOException {
        ArrayList<Course> courses = (ArrayList<Course>) DataIO.loadObjects(Course.class);
        ArrayList<Course> availCourses = new ArrayList<>();
        Student student = (Student) Context.user;
        ArrayList<String> selectedCourses = student.getCourses();
        HashMap<String, Course> courseMap = new HashMap<>();
        for (String s : selectedCourses) {
            courseMap.put(s, getCourse(s));
        }
        for (Course course : courses) {
            if (!courseMap.containsKey(course.getId())) {
                availCourses.add(course);
            }
        }
        return availCourses;
    }

    /**
     * @param course
     * @throws IOException
     */
    public static void studentAddCourse(Course course) throws IOException {
        Student student = (Student) Context.user;
        ArrayList<String> courses = student.getCourses();
        courses.add(course.getId());
        student.setCourses(courses);
        ArrayList<Student> students = UserUtil.loadStudents();
        for (Student stu : students) {
            if (stu.getId().equals(student.getId())) {
                students.remove(stu);
                break;
            }
        }
        students.add(student);
        DataIO.saveObjects(students, Student.class);
    }
}
