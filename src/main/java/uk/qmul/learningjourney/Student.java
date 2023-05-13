package uk.qmul.learningjourney;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.ArrayList;
import java.util.Map;

public class Student {
    private String id;
    private String name;
    private String password;
    private String college;
    private String major;
    private String classId;

    private ArrayList<Course> courses;

    @JsonCreator
    public Student(Map<String, Object> property) {
        id = (String) property.get("id");
        name = (String) property.get("name");
        password = (String) property.get("password");
        college = (String) property.get("college");
        major = (String) property.get("major");
        classId = (String) property.get("classId");
        courses = (ArrayList<Course>) property.get("courses");
    }

    public Student(String id, String name, String password, String college, String major, String classId) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.college = college;
        this.major = major;
        this.classId = classId;
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

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }
}
