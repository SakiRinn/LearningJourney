package uk.qmul.learningjourney.util;

import uk.qmul.learningjourney.Context;
import uk.qmul.learningjourney.model.Course;
import uk.qmul.learningjourney.model.user.Student;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class CourseUtil {

    public static Course getCourse(String courseId) throws IOException {
        ArrayList<Course> courses;
        courses = (ArrayList<Course>) DataIO.loadObjects(Course.class);
        for (Course course : courses) {
            if (course.getId().equals(courseId))
                return course;
        }
        return null;
    }

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

    public static void studentAddCourse(Course course) throws IOException {
        Student student = (Student) Context.user;
        ArrayList<String> courses = student.getCourses();
        courses.add(course.getId());
        student.setCourses(courses);
        ArrayList<Student> students = (ArrayList<Student>) DataIO.loadObjects(Student.class);
        for (Student s : students) {
            if (s.getId().equals(student.getId())) {
                students.remove(s);
                break;
            }
        }
        students.add(student);
        DataIO.saveObjects(students, student.getClass());
    }



}
