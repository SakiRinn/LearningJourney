package uk.qmul.learningjourney.grade;

import uk.qmul.learningjourney.DataIO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class GradeModel {

    public static HashMap<Integer, Double> score2GPA = getGPAMap();

    public static ArrayList<Grade> getGrades() {
        ArrayList<Grade> grades = new ArrayList<>();
        try {
            ArrayList<Grade> allGrades = (ArrayList<Grade>) DataIO.loadObjects(Grade.class);
            if (allGrades != null) {
                for (Grade grade : allGrades) {
//                    if (grade.getStudent() == Context.student)
                        grades.add(grade);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return grades;
    }

    public static HashMap<Integer, Double> getGPAMap() {
        int[] scores = new int[]{59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69,
                                 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80,
                                 81, 82, 83, 84, 85, 86, 87, 88, 89, 90,
                                 91, 92, 93, 94, 95, 96, 97, 98, 99, 100};
        double[] GPAs = new double[]{0.00, 1.00, 1.15, 1.29, 1.43, 1.57, 1.70, 1.83, 1.96, 2.08, 2.20,
                                     2.31, 2.42, 2.53, 2.63, 2.73, 2.83, 2.92, 3.01, 3.09, 3.17, 3.25,
                                     3.32, 3.39, 3.46, 3.52, 3.58, 3.63, 3.68, 3.73, 3.77, 3.81,
                                     3.85, 3.88, 3.91, 3.93, 3.95, 3.97, 3.98, 3.99, 4.00, 4.00};
        HashMap<Integer, Double> map = new HashMap<>();
        for (int i = 0; i < scores.length; i++)
            map.put(scores[i], GPAs[i]);
        return map;
    }

    public static double getAverageScore() {
        double totalScore = 0.0;
        for (Grade grade : getGrades())
            totalScore += grade.getScore();
        return totalScore / getGrades().size();
    }

    public static double getAverageGPA() {
        return score2GPA.get((int) Math.round(getAverageScore()));
    }
}
