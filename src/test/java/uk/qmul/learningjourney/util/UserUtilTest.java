package uk.qmul.learningjourney.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import uk.qmul.learningjourney.model.user.Student;
import uk.qmul.learningjourney.model.user.Teacher;
import uk.qmul.learningjourney.model.user.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static uk.qmul.learningjourney.util.UserUtil.loadUsers;

public class UserUtilTest {
    @Test
    void saveUser() throws IOException {
        User user = loadUsers().get(0);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);

        File file = new File(DataIO.dataPath + "User.json");
        ArrayList<User> list = loadUsers();
        if (list == null)
            list = new ArrayList<>();
        list.add(user);
        mapper.writerFor(new TypeReference<List<User>>() {
        }).writeValue(file, list);
    }

    @Test
    void saveUsers() throws IOException {
        ArrayList<User> users = loadUsers();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);

        File file = new File(DataIO.dataPath + "User.json");
        mapper.writerFor(new TypeReference<List<User>>() {
        }).writeValue(file, users);
    }

    @Test
    void loadStudents() throws IOException {
        ArrayList<Student> students = new ArrayList<>();
        for (User user : loadUsers()) {
            if (user instanceof Student)
                students.add((Student) user);
        }
    }

    @Test
    void loadTeachers() throws IOException {
        ArrayList<Teacher> teachers = new ArrayList<>();
        for (User user : loadUsers()) {
            if (user instanceof Teacher)
                teachers.add((Teacher) user);
        }
    }
}
