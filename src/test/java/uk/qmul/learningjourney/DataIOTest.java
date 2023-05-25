package uk.qmul.learningjourney;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import uk.qmul.learningjourney.model.Course;
import uk.qmul.learningjourney.model.Position;
import uk.qmul.learningjourney.model.user.Student;
import uk.qmul.learningjourney.model.user.Teacher;
import uk.qmul.learningjourney.util.DataIO;
import uk.qmul.learningjourney.util.UserUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

class DataIOTest {

    @Test
    void generateStudents() throws IOException {
        ArrayList<String> courses = new ArrayList<>();
        courses.add("EBU6304");
        ArrayList<Position> position = new ArrayList<>();
        Position position1 = new Position("4th International Conference of Nanton Impart", "2020-12-12", true);
        Position position2 = new Position("Supreme radiant star on king's pesticide", "2023-1-1", false);
        Position position3 = new Position("Drawf Technology Award for Compressed towel","2023-2-2",true);
        position.add(position1);
        Student student1 = new Student("2020213171", "Wu Lyuhua", "123456",
                "International School", "Telecommunication and management", "2020215105", courses, position);
        courses.add("EBC5001");
        position.add(position2);
        Student student2 = new Student("2020213160", "Huang Xiyuan", "123456",
                "International School", "Telecommunication and management", "2020215105", courses, position);
        courses.add("EBU6230");
        position.add(position3);
        Student student3 = new Student("2020213156", "Liu Zekai", "123456",
                "International School", "Telecommunication and management", "2020215105", courses, position);

        UserUtil.saveUser(student1);
        UserUtil.saveUser(student2);
        UserUtil.saveUser(student3);
    }

    @Test
    void generateTeachers() {
        ArrayList<String> courses = new ArrayList<>();
        courses.add("EBU6304");
        courses.add("EBU6230");
        Teacher teacher = new Teacher("2010213171", "Gokop Goteng", "123456", true, courses);
        try {
            UserUtil.saveUser(teacher);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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