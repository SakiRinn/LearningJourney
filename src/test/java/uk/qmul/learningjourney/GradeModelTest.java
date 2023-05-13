package uk.qmul.learningjourney;

import org.junit.jupiter.api.Test;
import uk.qmul.learningjourney.grade.Grade;
import uk.qmul.learningjourney.grade.GradeModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

class GradeModelTest {

    @Test
    void getGrades() {
        for (Grade grade : GradeModel.getGrades())
            System.out.println(grade);
    }

    @Test
    void addTestGrades() {
        ArrayList<Course> courses;
        try {
            courses = (ArrayList<Course>) DataIO.loadObjects(Course.class);
            for (Course course : courses) {
                Grade grade = new Grade(new Random().nextInt(100), course, Context.student);
                DataIO.saveObject(grade);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}