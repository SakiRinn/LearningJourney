package uk.qmul.learningjourney;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

class DataIOTest {

    @Test
    void saveObject() {
        try {
            ArrayList<int[]> times = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                times.add(new int[]{i, 2*i, 3*i});
            }
            for (int i = 0; i < 10; i++)
                DataIO.saveObject(new Course("同性原理", "114514", "捧月星", 100 + i, 4, "B205", 6, times));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void loadObjects() {
    }

    @Test
    void addStudentTest() throws IOException {
        Student student1 = new Student("2020213181", "Wu lyuhua", "123456",
                "International School", "Telecommunication and management", "2020215105");
        DataIO.saveObject(student1);
        System.out.println(student1.getClass());
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


}