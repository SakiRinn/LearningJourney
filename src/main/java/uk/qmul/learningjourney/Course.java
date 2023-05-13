package uk.qmul.learningjourney;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Map;

public class Course {
    private String name;
    private String id;
    private String teacher;
    private int type;
    private int level = 0;
    private int credit;
    private int score = -1;
    private String room;
    private int semester;

    private ArrayList<int[]> times;

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

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
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

    public ArrayList<int[]> getTimes() {
        return times;
    }

    public void setTimes(ArrayList<int[]> times) {
        this.times = times;
    }

    public Course(String name, String id, String teacher, int type, int credit, String room, int semester, ArrayList<int[]> times) {
        this.name = name;
        this.id = id;
        this.teacher = teacher;
        this.type = type;
        this.credit = credit;
        this.room = room;
        this.semester = semester;
        this.times = times;
    }

    @JsonCreator
    public Course(Map<String, Object> property) {
        try {
            for (Field field: this.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                field.set(this, property.get(field.getName()));
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
