package uk.qmul.learningjourney.model.user;

import java.util.ArrayList;

public class Teacher extends User {

    private boolean isAdmin;
    private ArrayList<String> courses;

    public Teacher() {
        this.isAdmin = false;
    }

    public Teacher(String id, String name, String password) {
        super(id, name, password);
        this.isAdmin = false;
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
