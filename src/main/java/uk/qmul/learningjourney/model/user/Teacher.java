package uk.qmul.learningjourney.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import uk.qmul.learningjourney.Context;
import uk.qmul.learningjourney.model.Course;
import uk.qmul.learningjourney.util.DataIO;
import uk.qmul.learningjourney.util.GradeUtil;

import java.io.IOException;
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

    public Teacher(String id, String name, String password, boolean isAdmin) {
        super(id, name, password);
        this.isAdmin = isAdmin;
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

    @JsonIgnore
    public ArrayList<Course> getCourseList() {
        ArrayList<Course> courses = new ArrayList<>();
        for (String courseId : this.getCourses()) {
            try {
                for (Course course : (ArrayList<Course>) DataIO.loadObjects(Course.class)) {
                    if (course.getId().equals(courseId))
                        courses.add(course);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return courses;
    }
}
