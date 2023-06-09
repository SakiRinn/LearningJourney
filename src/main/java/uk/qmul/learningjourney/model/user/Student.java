package uk.qmul.learningjourney.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.qmul.learningjourney.model.Achievement;
import uk.qmul.learningjourney.model.Course;
import uk.qmul.learningjourney.util.DataIO;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Define student entity
 */
public class Student extends User {
    /**
     * student's college
     */
    private String college;
    /**
     * student's major
     */
    private String major;
    /**
     * student's class ID
     */
    private String classId;
    /**
     * attended courses
     */
    private ArrayList<String> courses;
    /**
     * student's achievement list
     */
    private ArrayList<Achievement> achievement;

    public Student() {
        super();
        this.college = null;
        this.major = null;
        this.classId = null;
        this.courses = null;
        this.achievement = null;
    }

    public Student(String id, String name, String password, String college, String major,
                   String classId, ArrayList<String> courses, ArrayList<Achievement> achievement) {
        super(id, name, password);
        this.college = college;
        this.major = major;
        this.classId = classId;
        this.courses = courses;
        this.achievement = achievement;
    }

    public Student(String id, String name, String password,
                   String college, String major, String classId) {
        super(id, name, password);
        this.college = college;
        this.major = major;
        this.classId = classId;
        this.courses = new ArrayList<>();
        this.achievement = new ArrayList<>();
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

    public ArrayList<Achievement> getPosition() {
        return achievement;
    }

    public void setPosition(ArrayList<Achievement> achievement) {
        this.achievement = achievement;
    }

    /**
     * Get a list of all learning courses based on the `courses` attribute.
     *
     * @return {@link ArrayList}<{@link Course}> List of learning courses
     */
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
