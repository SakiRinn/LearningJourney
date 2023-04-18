package uk.qmul.learningjourney;

public class ClassSchedule {
    public static final int CLASSHOUR = 45;
    public static final int CLASSCOUNT = 14;

    Course[] courses;
    int semester;
    int week;
    String Sid;


    public Course getCourse(int day) {

        return null;
    }

    public void addCourse(Course course) {

    }

    public void delCourse(Course course) {

    }

    public Course[] getCourseSchedule() {
        return null;
    }
}

class Course {
    String name;
    String id;
    String teacher;

    static class Time {
        int[] day;
        int start;
        int end;
    }

    Time[] time;
    int type;
    int level = 0;
    int credit;
    int score = -1;
    String room;
    int semester;


}
