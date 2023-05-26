package uk.qmul.learningjourney.util;

import org.junit.jupiter.api.Test;
import uk.qmul.learningjourney.Context;
import uk.qmul.learningjourney.model.Course;
import uk.qmul.learningjourney.model.Grade;
import uk.qmul.learningjourney.model.user.Student;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

class GradeCourseUtilTest {

    @Test
    void generateGrades() {
        try {
            Context.user = UserUtil.loadStudents().get(0);
            for (Student s : UserUtil.loadStudents()) {
                for (Course course : (ArrayList<Course>) DataIO.loadObjects(Course.class)) {
                    Grade grade = new Grade(course.getId(), s.getName(), 60 + new Random().nextInt(40));
                    for (String c : s.getCourses()) {
                        if (c.equals(course.getId()))
                            DataIO.saveObject(grade);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getGrades() {
        for (Grade grade : GradeUtil.getGrades())
            System.out.println(grade);
    }

    @Test
    void getAverageScore() {
        System.out.println(GradeUtil.getWeightedScore());
    }

    @Test
    void getAverageGPA() {
        System.out.println(GradeUtil.getWeightedGPA());
    }

    @Test
    void exportGrade() throws IOException {
        DataIO.exportGrade(GradeUtil.getGrades(), "./test.docx");
    }

    @Test
    void getRank() {
        try {
            ArrayList<Student> students = (ArrayList<Student>) DataIO.loadObjects(Student.class);
            Context.user = students.get(0);
            System.out.println(GradeUtil.getRank());
            System.out.printf("%.2f%%\n", (double) GradeUtil.getRank() / students.size() * 100);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}