package uk.qmul.learningjourney.model;

import java.util.HashMap;

public class Course {
    private String name;
    private String id;
    private String teacher;
    private int type;
    private int level = 0;
    private double credit;
    private String room;
    private int semester;

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
