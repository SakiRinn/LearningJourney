package uk.qmul.learningjourney.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.qmul.learningjourney.util.DataIO;
import uk.qmul.learningjourney.util.GradeUtil;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Definition of Student's grade
 *
 * @author Lyuhua Wu
 */
public class Grade {

    /**
     * ID of the course
     */
    private String courseId;
    /**
     * Owned student
     */
    private String student;
    /**
     * Score (max: 100)
     */
    private int score;
    /**
     * GPA (max: 4.0)
     */
    private double GPA;

    public Grade() {
        this.courseId = null;
        this.student = null;
        this.score = 0;
        this.GPA = 0.0;
    }

    public Grade(String courseName, String student, int score) {
        this.courseId = courseName;
        this.student = student;
        this.score = score;
        this.GPA = GradeUtil.score2GPA(this.score);
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
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

    /**
     * Based on the `courseId` property, get the credits of this course
     *
     * @return double
     */
    @JsonIgnore
    public double getCredit() {
        try {
            for (Course course : (ArrayList<Course>) DataIO.loadObjects(Course.class)) {
                if (course.getName().equals(this.getCourseName()))
                    return course.getCredit();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return 0.0;
    }

    /**
     * Based on the `courseId` property, get the course name
     *
     * @return double
     */
    @JsonIgnore
    public String getCourseName() {
        ArrayList<Course> courses;
        try {
            courses = (ArrayList<Course>) DataIO.loadObjects(Course.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (Course course : courses) {
            if (course.getId().equals(this.courseId))
                return course.getName();
        }
        throw new RuntimeException("Invalid course ID for this grade!");
    }

    @Override
    public String toString() {
        return "Grade {" + '\n'
               + "\tStudent: \t" + this.getStudent() + '\n'
               + "\tCourse: \t" + this.getCourseName() + '\n'
               + "\tCredit: \t" + this.getCredit() + '\n'
               + "\tScore: \t\t" + this.getScore() + '\n'
               + "\tGPA: \t\t" + this.getGPA() + '\n'
               + '}';
    }
}
