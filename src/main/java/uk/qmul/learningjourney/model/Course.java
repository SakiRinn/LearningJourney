package uk.qmul.learningjourney.model;

import java.util.HashMap;

/**
 * Define the Course entity
 *
 * @author Zekai Liu
 * @date 2023/05/25
 */
public class Course {
    /**
     * course name
     */
    private String name;
    /**
     * course ID
     */
    private String id;
    /**
     * lecturer
     */
    private String teacher;
    /**
     * course type
     */
    private int type;
    /**
     * course level
     */
    private int level = 0;
    /**
     * course credit
     */
    private double credit;
    /**
     * classroom
     */
    private String room;
    /**
     * the semester that the course if offered
     */
    private int semester;

    /**
     * course schedule
     * <p>
     * <strong>key</strong>: week
     * <p>
     * <strong>value</strong>: class time in that week, since there are five working days in a week,
     * each day has no more than 14 lessons,
     * the value should in the range of [1,70].
     */
    private HashMap<Integer, Integer[]> schedule;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public HashMap<Integer, Integer[]> getSchedule() {
        return schedule;
    }

    public void setSchedule(HashMap<Integer, Integer[]> schedule) {
        this.schedule = schedule;
    }

    public Course() {
        this.name = null;
        this.id = null;
        this.teacher = null;
        this.type = 0;
        this.credit = 0.0;
        this.room = null;
        this.semester = 0;
        this.schedule = null;
    }

    public Course(String name, String id, String teacher, int type, double credit,
                  String room, int semester, HashMap<Integer, Integer[]> schedule) {
        this.name = name;
        this.id = id;
        this.teacher = teacher;
        this.type = type;
        this.credit = credit;
        this.room = room;
        this.semester = semester;
        this.schedule = schedule;
    }
}
