package uk.qmul.learningjourney;

import org.junit.jupiter.api.Test;
import uk.qmul.learningjourney.model.Grade;
import uk.qmul.learningjourney.model.user.Student;
import uk.qmul.learningjourney.util.DataIO;
import uk.qmul.learningjourney.util.GradeUtil;

import java.io.IOException;
import java.util.ArrayList;

class GradeCourseUtilTest {

    @Test
    void addTestGrades() {
//        try {
//            for (Student student : (ArrayList<Student>) DataIO.loadObjects(Student.class)) {
//                Context.account = student;
//                for (Course course : (ArrayList<Course>) DataIO.loadObjects(Course.class)) {
//                    Grade grade = new Grade(course.getName(), Context.account.getName(),
//                            60 + new Random().nextInt(40));
//                    for (String c : Context.account.getCourses()) {
//                        if (c.equals(course.getId()))
//                            DataIO.saveObject(grade);
//                    }
//                }
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

    @Test
    void getGrades() {
        for (Grade grade : GradeUtil.getGrades())
            System.out.println(grade);
    }

    @Test
    void getAverageScore() {
        System.out.println(GradeUtil.getAverageScore());
    }

    @Test
    void getAverageGPA() {
        System.out.println(GradeUtil.getAverageGPA());
    }

    @Test
    void exportGrade() throws IOException {
        GradeUtil.generateWord(GradeUtil.getGrades(), "./test.docx");
    }

    @Test
    void getRank() {
        try {
            ArrayList<Student> students = (ArrayList<Student>) DataIO.loadObjects(Student.class);
            Context.account = students.get(0);
            System.out.println(GradeUtil.getRank());
            System.out.printf("%.2f%%\n", (double) GradeUtil.getRank() / students.size() * 100);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}