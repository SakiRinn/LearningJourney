package uk.qmul.learningjourney;

import java.util.ArrayList;

public class Student {
    private String id;
    private String name;
    private String password;
    private String college;
    private String major;
    private String classId;

    private ArrayList<String> courses;

    public Student() {
        this.id = null;
        this.name = null;
        this.password = null;
        this.college = null;
        this.major = null;
        this.classId = null;
        this.courses = null;
    }

    public Student(String id, String name, String password, String college, String major, String classId) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.college = college;
        this.major = major;
        this.classId = classId;
        this.courses = null;
    }

    public Student(String id, String name, String password, String college, String major, String classId, ArrayList<String> courses) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.college = college;
        this.major = major;
        this.classId = classId;
        this.courses = courses;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
