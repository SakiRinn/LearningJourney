package uk.qmul.learningjourney.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.qmul.learningjourney.util.DataIO;
import uk.qmul.learningjourney.util.GradeUtil;

import java.io.IOException;
import java.util.ArrayList;

public class Grade {

    private String course;
    private String student;
    private int score;
    private double GPA;

    public Grade() {
        this.course = null;
        this.student = null;
        this.score = 0;
        this.GPA = 0.0;
    }

    public Grade(String course, String student, int score) {
        this.course = course;
        this.student = student;
        this.score = score;
        this.GPA = GradeUtil.score2GPA(this.score);
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public double getGPA() {
        return GPA;
    }

    public void setGPA(double GPA) {
        this.GPA = GPA;
    }

    @JsonIgnore
    public double getCredit() {
        try {
            for (Course course : (ArrayList<Course>) DataIO.loadObjects(Course.class)) {
                if (course.getName().equals(this.getCourse()))
                    return course.getCredit();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return 0.0;
    }

    @Override
    public String toString() {
        return "Grade {" + '\n'
               + "\tStudent: \t" + this.getStudent() + '\n'
               + "\tCourse: \t" + this.getCourse() + '\n'
               + "\tCredit: \t" + this.getCredit() + '\n'
               + "\tScore: \t\t" + this.getScore() + '\n'
               + "\tGPA: \t\t" + this.getGPA() + '\n'
               + '}';
    }
}
