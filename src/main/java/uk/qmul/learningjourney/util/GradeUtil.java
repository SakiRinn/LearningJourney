package uk.qmul.learningjourney.util;

import uk.qmul.learningjourney.Context;
import uk.qmul.learningjourney.model.Grade;
import uk.qmul.learningjourney.model.user.Student;
import uk.qmul.learningjourney.model.user.User;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class GradeUtil {

    public static DecimalFormat df = new DecimalFormat("#.##");
    public static HashMap<String, Double> studentId2Score = getScoreMap();
//    public static HashMap<Integer, Double> score2GPA = getGPAMap();

//    private static HashMap<Integer, Double> getGPAMap() {
//        int[] scores = new int[]{59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69,
//                                 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80,
//                                 81, 82, 83, 84, 85, 86, 87, 88, 89, 90,
//                                 91, 92, 93, 94, 95, 96, 97, 98, 99, 100};
//        double[] GPAs = new double[]{0.00, 1.00, 1.15, 1.29, 1.43, 1.57, 1.70, 1.83, 1.96, 2.08, 2.20,
//                                     2.31, 2.42, 2.53, 2.63, 2.73, 2.83, 2.92, 3.01, 3.09, 3.17, 3.25,
//                                     3.32, 3.39, 3.46, 3.52, 3.58, 3.63, 3.68, 3.73, 3.77, 3.81,
//                                     3.85, 3.88, 3.91, 3.93, 3.95, 3.97, 3.98, 3.99, 4.00, 4.00};
//        HashMap<Integer, Double> map = new HashMap<>();
//        for (int i = 0; i < scores.length; i++)
//            map.put(scores[i], GPAs[i]);
//        return map;
//    }

    private static HashMap<String, Double> getScoreMap() {
        HashMap<String, Double> map = new HashMap<>();
        try {
            for (User user : (ArrayList<User>) DataIO.loadObjects(User.class)) {
                if (user instanceof Student)
                    map.put(user.getId(), getAverageScore((Student) user));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static ArrayList<Grade> getGrades() {
        ArrayList<Grade> grades = new ArrayList<>();
        Student student = (Student) Context.user;
        return getGrades(student);
    }

    public static ArrayList<Grade> getGrades(Student student) {
        ArrayList<Grade> grades = new ArrayList<>();
        try {
            ArrayList<Grade> allGrades = (ArrayList<Grade>) DataIO.loadObjects(Grade.class);
            if (allGrades != null) {
                for (Grade grade : allGrades) {
                    if (grade.getStudent().equals(student.getName()))
                        grades.add(grade);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return grades;
    }

    public static double getAverageScore() {
        double totalScore = 0.0;
        for (Grade grade : getGrades())
            totalScore += grade.getScore();
        return Double.parseDouble(df.format(totalScore / getGrades().size()));
    }

    public static double getAverageScore(Student student) {
        double totalScore = 0.0;
        for (Grade grade : getGrades(student))
            totalScore += grade.getScore();
        return Double.parseDouble(df.format(totalScore / getGrades().size()));
    }

    public static double score2GPA(int score) {
        return Double.parseDouble(df.format((score > 60) ? (4 - 3 * Math.pow(100 - score, 2) / 1600) : 0.0));
    }

    public static double getAverageGPA() {
        int score = (int) Math.round(getAverageScore());
        return Double.parseDouble(df.format((score > 60) ? (4 - 3 * Math.pow(100 - score, 2) / 1600) : 0.0));
    }

    public static double getAverageGPA(Student student) {
        int score = (int) Math.round(getAverageScore(student));
        return Double.parseDouble(df.format((score > 60) ? (4 - 3 * Math.pow(100 - score, 2) / 1600) : 0.0));
    }

    public static int getRank() {
        Student student = (Student) Context.user;
        double currentScore = studentId2Score.get(student.getId());
        int rank = 1;
        try {
            for (User user : (ArrayList<User>) DataIO.loadObjects(User.class)) {
                if (user instanceof Student && studentId2Score.get(user.getId()) > currentScore)
                    rank++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return rank;
    }

    public static int getRank(Student student) {
        double currentScore = studentId2Score.get(student.getId());
        int rank = 1;
        try {
            for (User user : (ArrayList<User>) DataIO.loadObjects(User.class)) {
                if (user instanceof Student && studentId2Score.get(user.getId()) > currentScore)
                    rank++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return rank;
    }


}
