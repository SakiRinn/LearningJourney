package uk.qmul.learningjourney;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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
        Course course1 = new Course("Software Management", "EBU6304", "Gokop Goteng", 0, 4,
                "3-535", 6, new int[]{1, 4, 9, 13}, new int[]{1, 2, 3, 4}, new int[]{10, 11});
        ArrayList<Course> courses = new ArrayList<>();
        courses.add(course1);
        Student student1 = new Student("2020213171", "Wu lyuhua", "123456",
                "International School", "Telecommunication and management", "2020215105", courses);
        Course course2 = new Course("PDP", "EBC5001", "Mona JABER", 0, 0.5,
                "3-208", 6, new int[]{9}, new int[]{2}, new int[]{8, 9});
        courses.add(course2);
        Student student2 = new Student("2020213160", "Huang Xiyuan", "123456",
                "International School", "Telecommunication and management", "2020215105", courses);
        Course course3 = new Course("Image and Video Processing", "EBU6230", "Gokop Goteng", 0, 3.0,
                "3-535", 6, null, new int[]{5}, new int[]{1, 2});
        courses.add(course3);
        Student student3 = new Student("2020213156", "Liu Zekai", "123456",
                "International School", "Telecommunication and management", "2020215105", courses);

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
                "3-535", 6, new int[]{1, 4, 9, 13}, new int[]{1, 2, 3, 4}, new int[]{10, 11});
        Course course2 = new Course("PDP", "EBC5001", "Mona JABER", 0, 0.5,
                "3-208", 6, new int[]{9}, new int[]{2}, new int[]{8, 9});
        Course course3 = new Course("Image and Video Processing", "EBU6230", "Gokop Goteng", 0, 3.0,
                "3-535", 6, null, new int[]{5}, new int[]{1, 2});
        Course course4 = new Course("Internet Application", "BBU1234", "Huang Xiaohong", 0, 3.0,
                "3-535", 6, null, new int[]{1}, new int[]{3, 4});
        DataIO.saveObject(course1);
        DataIO.saveObject(course2);
        DataIO.saveObject(course3);
        DataIO.saveObject(course4);
    }


}