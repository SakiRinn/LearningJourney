package uk.qmul.learningjourney.grade;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.qmul.learningjourney.Course;
import uk.qmul.learningjourney.Student;

public class Grade {

    private Course course;
    private Student student;
    private int score;


    public Grade() {
        this.course = null;
        this.student = null;
        this.score = 0;
    }

    public Grade(int score, Course course, Student student) {
        this.course = course;
        this.student = student;
        this.score = score;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @JsonIgnore
    public double getCredit() {
        return this.course != null? this.course.getCredit() : 0.0;
    }

    @JsonIgnore
    public String getName() {
        return this.course != null? this.course.getName() : null;
    }


    @Override
    public String toString() {
        return "Grade {" + '\n'
               + "\tStudent: \t" + (student != null ? student.getName() : null) + '\n'
               + "\tCourse: \t" + this.getName() + '\n'
               + "\tScore: \t\t" + score + '\n'
               + "\tCredit: \t" + this.getCredit() + '\n'
               + '}';
    }
}
