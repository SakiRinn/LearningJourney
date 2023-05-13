package uk.qmul.learningjourney;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Util {
    public static boolean login(String id, String password) throws IOException {
        ArrayList<Student> students = (ArrayList<Student>) DataIO.loadObjects(Student.class);
        assert students != null;
        for (Student student : students) {
            if (student.getId().equals(id))
                if (student.getPassword().equals(password)) {
                    Context.student = student;
                    return true;
                }
        }
        return false;
    }

    public static ArrayList<Course> getAvailCourses() throws IOException {
        ArrayList<Course> courses = (ArrayList<Course>) DataIO.loadObjects(Course.class);
        ArrayList<Course> availCourses = new ArrayList<>();
        ArrayList<Course> selectedCourses = Context.student.getCourses();
        HashMap<String, Course> courseId = new HashMap<>();
        for (Course course : selectedCourses) {
            courseId.put(course.getId(), course);
        }
        for (Course course : courses) {
            if (!courseId.containsKey(course.getId())) {
                availCourses.add(course);
            }
        }
        return availCourses;

    }

    public static void studentAddCourse(Course course) throws IOException {
        ArrayList<Course> courses = Context.student.getCourses();
        courses.add(course);
        Context.student.setCourses(courses);
        ArrayList<Student> students = (ArrayList<Student>) DataIO.loadObjects(Student.class);
        for (Student student : students) {
            if (student.getId().equals(Context.student.getId())) {
                students.remove(student);
                break;
            }
        }
        students.add(Context.student);
        DataIO.saveObjects(students, Context.student.getClass());
    }
}
