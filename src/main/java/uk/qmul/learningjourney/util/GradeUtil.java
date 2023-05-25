package uk.qmul.learningjourney.util;

import uk.qmul.learningjourney.Context;
import uk.qmul.learningjourney.model.Course;
import uk.qmul.learningjourney.model.Grade;
import uk.qmul.learningjourney.model.user.Student;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class GradeUtil {

    public static DecimalFormat df = new DecimalFormat("#.##");

    private static HashMap<String, Double> getScoreMap() {
        HashMap<String, Double> map = new HashMap<>();
        try {
            for (Student stu : UserUtil.loadStudents()) {
                map.put(stu.getId(), getWeightedScore(stu));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static ArrayList<Grade> getGrades() {
        if (!(Context.user instanceof Student))
            throw new RuntimeException("Only students can use this function!");
        ArrayList<Grade> grades = new ArrayList<>();
        Student student = (Student) Context.user;
        return getGrades(student);
    }

    public static ArrayList<Grade> getGrades(Student student) {
        if (!(Context.user instanceof Student))
            throw new RuntimeException("Only students can use this function!");
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

    public static double getWeightedScore() {
        double totalScore = 0.0;
        for (Grade grade : getGrades())
            totalScore += grade.getScore() * grade.getCredit();
        return Double.parseDouble(df.format(totalScore / getTotalCredit()));
    }

    public static double getWeightedScore(Student student) {
        double totalScore = 0.0;
        for (Grade grade : getGrades(student))
            totalScore += grade.getScore() * grade.getCredit();
        return Double.parseDouble(df.format(totalScore / getTotalCredit()));
    }

    public static double score2GPA(int score) {
        return Double.parseDouble(df.format((score > 60) ? (4 - 3 * Math.pow(100 - score, 2) / 1600) : 0.0));
    }

    public static double getWeightedGPA() {
        double totalGPA = 0.0;
        for (Grade grade : getGrades())
            totalGPA += score2GPA(grade.getScore()) * grade.getCredit();
        return Double.parseDouble(df.format(totalGPA / getTotalCredit()));
    }

    public static double getWeightedGPA(Student student) {
        double totalGPA = 0.0;
        for (Grade grade : getGrades(student))
            totalGPA += score2GPA(grade.getScore()) * grade.getCredit();
        return Double.parseDouble(df.format(totalGPA / getTotalCredit()));
    }

    public static int getRank() {
        if (!(Context.user instanceof Student))
            throw new RuntimeException("Only students can get the credit!");
        Student student = (Student) Context.user;
        double currentScore = getScoreMap().get(student.getId());
        int rank = 1;
        try {
            for (Student stu : UserUtil.loadStudents()) {
                if (getScoreMap().get(stu.getId()) > currentScore)
                    rank++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return rank;
    }

    public static int getRank(Student student) {
        if (!(Context.user instanceof Student))
            throw new RuntimeException("Only students can use this function!");
        double currentScore = getScoreMap().get(student.getId());
        int rank = 1;
        try {
            for (Student stu : UserUtil.loadStudents()) {
                if (getScoreMap().get(stu.getId()) > currentScore)
                    rank++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return rank;
    }

    public static double getTotalCredit() {
        if (!(Context.user instanceof Student))
            throw new RuntimeException("Only students can use this function!");
        double totalCredit = 0.0;
        Student student = (Student) Context.user;
        try {
            for (Grade grade : (ArrayList<Grade>) DataIO.loadObjects(Grade.class)) {
                if (grade.getStudent().equals(Context.user.getName()))
                    totalCredit += grade.getCredit();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return totalCredit;
    }

    public static ArrayList<Grade> getCourseGrades(Course course) {
        ArrayList<Grade> grades = new ArrayList<>();
        try {
            for (Grade grade : (ArrayList<Grade>) DataIO.loadObjects(Grade.class)) {
                if (grade.getCourseId().equals(course.getId()))
                    grades.add(grade);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return grades;
    }
}
