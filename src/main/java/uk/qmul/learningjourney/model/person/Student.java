package uk.qmul.learningjourney.model.person;

import uk.qmul.learningjourney.model.Position;
import uk.qmul.learningjourney.model.person.Person;

import java.util.ArrayList;

public class Student extends Person {

    private String college;
    private String major;
    private String classId;

    private ArrayList<String> courses;
    private ArrayList<Position> position;

    public Student() {
        super();
        this.college = null;
        this.major = null;
        this.classId = null;
        this.courses = null;
        this.position = null;
    }

    public Student(String id, String name, String password,
                   String college, String major, String classId) {
        super(id, name, password);
        this.college = college;
        this.major = major;
        this.classId = classId;
        this.courses = null;
        this.position = null;
    }

    public Student(String id, String name, String password, String college, String major,
                   String classId, ArrayList<String> courses, ArrayList<Position> position) {
        super(id, name, password);
        this.college = college;
        this.major = major;
        this.classId = classId;
        this.courses = courses;
        this.position = position;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public ArrayList<String> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<String> courses) {
        this.courses = courses;
    }

    public ArrayList<Position> getPosition() {
        return position;
    }

    public void setPosition(ArrayList<Position> position) {
        this.position = position;
    }
}
