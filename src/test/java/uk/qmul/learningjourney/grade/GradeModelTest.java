package uk.qmul.learningjourney.grade;

import org.junit.jupiter.api.Test;
import uk.qmul.learningjourney.Context;
import uk.qmul.learningjourney.Course;
import uk.qmul.learningjourney.DataIO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

class GradeModelTest {

    @Test
    void addTestGrades() {
        ArrayList<Course> courses;
        try {
            courses = (ArrayList<Course>) DataIO.loadObjects(Course.class);
            for (Course course : courses) {
                Grade grade = new Grade(60 + new Random().nextInt(40), course, Context.student);
                DataIO.saveObject(grade);
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


}