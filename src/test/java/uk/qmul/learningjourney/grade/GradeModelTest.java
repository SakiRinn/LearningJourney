package uk.qmul.learningjourney.grade;

import org.junit.jupiter.api.Test;
import uk.qmul.learningjourney.Context;
import uk.qmul.learningjourney.Course;
import uk.qmul.learningjourney.DataIO;
import uk.qmul.learningjourney.Student;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

class GradeModelTest {

    @Test
    void addTestGrades() {
        ArrayList<Course> courses;

        try {
            ArrayList<Student> students = (ArrayList<Student>) DataIO.loadObjects(Student.class);
            courses = (ArrayList<Course>) DataIO.loadObjects(Course.class);
            for (int i = 0; i < students.size(); i++) {
                Context.student = students.get(i);
                for (Course course : courses) {
                    Grade grade = new Grade(60 + new Random().nextInt(40), course, Context.student);
                    for (String c : Context.student.getCourses()) {
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
        for (Grade grade : GradeModel.getGrades())
            System.out.println(grade);
    }

    @Test
    void getAverageScore() {
        System.out.println(GradeModel.getAverageScore());
    }

    @Test
    void getAverageGPA() {
        System.out.println(GradeModel.getAverageGPA());
    }

    @Test
    void exportGrade() throws IOException {
        GradeModel.generateWord(GradeModel.getGrades(), "./test.docx");
    }

    @Test
    void getRank() {
        try {
            ArrayList<Student> students = (ArrayList<Student>) DataIO.loadObjects(Student.class);
            Context.student = students.get(0);
            System.out.println(GradeModel.getRank());
            System.out.printf("%.2f%%\n", (double)GradeModel.getRank()/students.size()*100);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}