package uk.qmul.learningjourney.model.user;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.ArrayList;

public class Teacher extends User {

    private boolean isAdmin;
    private ArrayList<String> courses;

    public Teacher() {
        super();
        this.isAdmin = false;
        this.courses = null;
    }

    public Teacher(String id, String name, String password, boolean isAdmin, ArrayList<String> courses) {
        super(id, name, password);
        this.isAdmin = isAdmin;
        this.courses = courses;
    }

    public Teacher(String id, String name, String password) {
        super(id, name, password);
        this.isAdmin = false;
        this.courses = new ArrayList<>();
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public ArrayList<String> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<String> courses) {
        this.courses = courses;
    }
}
