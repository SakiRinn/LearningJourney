package uk.qmul.learningjourney;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

class DataIOTest {

//    @Test
//    void saveObject() {
//        try {
//            ArrayList<int[]> times = new ArrayList<>();
//            for (int i = 0; i < 3; i++) {
//                times.add(new int[]{i, 2*i, 3*i});
//            }
//            for (int i = 0; i < 10; i++)
//                DataIO.saveObject(new Course("同性原理", "114514", "捧月星", 100 + i, 4, "B205", 6, times));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

    @Test
    void loadObjects() {
    }

    @Test
    void addStudentTest() throws IOException {
        ArrayList<String> courses = new ArrayList<>();
        courses.add("EBU6304");
        ArrayList<Position> position = new ArrayList<>();
        Position position1 = new Position("4th International Conference of Nanton Impart", "2020.12.12", true);
        Position position2 = new Position("Supreme radiant star on king's pesticide", "2023.1.1", false);
        position.add(position1);
        position.add(position2);
        Student student1 = new Student("2020213171", "Wu lyuhua", "123456",
                "International School", "Telecommunication and management", "2020215105", courses,position);
        courses.add("EBC5001");
        Student student2 = new Student("2020213160", "Huang Xiyuan", "123456",
                "International School", "Telecommunication and management", "2020215105", courses,position);
        courses.add("EBU6230");
        Student student3 = new Student("2020213156", "Liu Zekai", "123456",
                "International School", "Telecommunication and management", "2020215105", courses,position);

        DataIO.saveObject(student1);
        DataIO.saveObject(student2);
        DataIO.saveObject(student3);
        System.out.println(student1.getClass());
    }

    @Test
    void addCourseScheduleTest() {

    }


    @Test
    void loadStudentTest() throws IOException {

        ArrayList<Student> students = (ArrayList<Student>) DataIO.loadObjects(Student.class);
        System.out.println(Student.class);
        System.out.println(students.get(0));
        System.out.println(students.get(0).getClass());
        Student student = (Student) students.get(0);
        Assertions.assertEquals("2020213181", student.getId());
    }

    @Test
    void loadTest() throws IOException {
//读json
        File file = new File("src/main/resources/uk/qmul/learningjourney/data/Student.json");
        ArrayList<Student> students;
        ObjectMapper mapper = new ObjectMapper();
        try {
            students = mapper.readValue(file, new TypeReference<>() {
            });
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        Assertions.assertEquals("2020213181", students.get(0).getId());
    }

    @Test
    void addCourseTest() throws IOException {

        Course course1 = new Course("Software Management", "EBU6304", "Gokop Goteng", 0, 4.0,
                "3-535", 6, new HashMap<>() {{
            put(0, Arrays.stream(new int[]{10, 11}).boxed().toArray(Integer[]::new));
        }});
        Course course2 = new Course("PDP", "EBC5001", "Mona JABER", 0, 0.5,
                "3-208", 6, new HashMap<>() {{
            put(9, Arrays.stream(new int[]{18, 19}).boxed().toArray(Integer[]::new));
        }});
        Course course3 = new Course("Image and Video Processing", "EBU6230", "Gokop Goteng", 0, 3.0,
                "3-535", 6, new HashMap<>() {{
            put(0, Arrays.stream(new int[]{29, 30}).boxed().toArray(Integer[]::new));
        }});
        Course course4 = new Course("Internet Application", "BBU1234", "Huang Xiaohong", 0, 3.0,
                "3-535", 6, new HashMap<>() {{
            put(0, Arrays.stream(new int[]{3, 4, 5}).boxed().toArray(Integer[]::new));
        }});
        DataIO.saveObject(course1);
        DataIO.saveObject(course2);
        DataIO.saveObject(course3);
        DataIO.saveObject(course4);
    }


}