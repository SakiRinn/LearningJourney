package uk.qmul.learningjourney.util;

import uk.qmul.learningjourney.Context;
import uk.qmul.learningjourney.model.Course;
import uk.qmul.learningjourney.model.Grade;
import uk.qmul.learningjourney.model.user.Student;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Utility class for user operation
 * <p>
 *     Mainly include calculating functions related to grades.
 * </p>
 *
 * @author Lyuhua Wu
 */
public class GradeUtil {

    /**
     * Format of grade output (2 decimal places)
     */
    public static DecimalFormat df = new DecimalFormat("#.##");

    /**
     * @return {@link HashMap}<{@link String}, {@link Double}> `Student-Score` mapping table
     */
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

    /**
     * @return {@link ArrayList}<{@link Grade}> Grade of the current student
     * @see #getGrades(Student student)
     */
    public static ArrayList<Grade> getGrades() {
        if (!(Context.user instanceof Student))
            throw new RuntimeException("Only students can use this function!");
        ArrayList<Grade> grades = new ArrayList<>();
        Student student = (Student) Context.user;
        return getGrades(student);
    }

    /**
     * Get grades for specific students
     *
     * @param student a specific student
     * @return {@link ArrayList}<{@link Grade}> Grade of the student
     */
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

    /**
     * @return double Weighted average score of the current student
     * @see #getWeightedScore(Student student)
     */
    public static double getWeightedScore() {
        double totalScore = 0.0;
        for (Grade grade : getGrades())
            totalScore += grade.getScore() * grade.getCredit();
        return Double.parseDouble(df.format(totalScore / getTotalCredit()));
    }

    /**
     * Get weighted average score for specific students
     *
     * @param student a specific student
     * @return {@link ArrayList}<{@link Grade}> Weighted average score of the student
     */
    public static double getWeightedScore(Student student) {
        double totalScore = 0.0;
        for (Grade grade : getGrades(student))
            totalScore += grade.getScore() * grade.getCredit();
        return Double.parseDouble(df.format(totalScore / getTotalCredit()));
    }

    /**
     * Convert score to GPA
     *
     * @param score Score to be converted
     * @return double corresponding GPA
     */
    public static double score2GPA(int score) {
        return Double.parseDouble(df.format((score > 60) ? (4 - 3 * Math.pow(100 - score, 2) / 1600) : 0.0));
    }

    /**
     * @return double Weighted Average GPA of the current student
     * @see #getWeightedGPA(Student student)
     */
    public static double getWeightedGPA() {
        double totalGPA = 0.0;
        for (Grade grade : getGrades())
            totalGPA += score2GPA(grade.getScore()) * grade.getCredit();
        return Double.parseDouble(df.format(totalGPA / getTotalCredit()));
    }

    /**
     * Get weighted average GPA for specific students
     *
     * @param student a specific student
     * @return {@link ArrayList}<{@link Grade}> Weighted average GPA of the student
     */
    public static double getWeightedGPA(Student student) {
        double totalGPA = 0.0;
        for (Grade grade : getGrades(student))
            totalGPA += score2GPA(grade.getScore()) * grade.getCredit();
        return Double.parseDouble(df.format(totalGPA / getTotalCredit()));
    }

    /**
     * @return int The ranking of the current student
     * @see #getRank(Student student)
     */
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

    /**
     * Get the ranking for specific students
     *
     * @param student a specific student
     * @return {@link ArrayList}<{@link Grade}> The ranking of the student
     */
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

    /**
     * @return double The total credit of the current student
     */
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

    /**
     * Get all grades for a specific course
     *
     * @param course a specific course
     * @return {@link ArrayList}<{@link Grade}> The list of this course
     */
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
