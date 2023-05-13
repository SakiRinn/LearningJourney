package uk.qmul.learningjourney.grade;

import uk.qmul.learningjourney.Context;
import uk.qmul.learningjourney.DataIO;
import uk.qmul.learningjourney.grade.Grade;

import java.io.IOException;
import java.util.ArrayList;

public class GradeModel {

    public static ArrayList<Grade> getGrades() {
        ArrayList<Grade> grades = new ArrayList<>();
        try {
            ArrayList<Grade> allGrades = (ArrayList<Grade>) DataIO.loadObjects(Grade.class);
            if (allGrades != null) {
                for (Grade grade : allGrades) {
                    if (grade.getStudent() == Context.student)
                        grades.add(grade);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return grades;
    }
}
