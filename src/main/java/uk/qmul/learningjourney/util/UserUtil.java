package uk.qmul.learningjourney.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import uk.qmul.learningjourney.model.user.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for user operation
 * <p>
 *     Mainly include user I/O functions.
 * </p><p>
 *     Since the methods in `DataIO` class do not support polymorphism with generic,
 *     we write new methods specifically for the User class and its superclasses.
 * </p>
 *
 * @author Lyuhua Wu
 */
public class UserUtil {

    /**
     * Add a new user into database.
     *
     * @param user new user
     * @throws IOException
     */
    public static void saveUser(User user) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);

        File file = new File(DataIO.dataPath + "User.json");
        ArrayList<User> list = loadUsers();
        if (list == null)
            list = new ArrayList<>();
        list.add(user);
        mapper.writerFor(new TypeReference<List<User>>(){}).writeValue(file, list);
    }

    public static void saveUsers(ArrayList<User> users) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);

        File file = new File(DataIO.dataPath + "User.json");
        mapper.writerFor(new TypeReference<List<User>>(){}).writeValue(file, users);
    }

    public static ArrayList<User> loadUsers() throws IOException {
        return (ArrayList<User>) DataIO.loadObjects(User.class);
    }

    public static ArrayList<Student> loadStudents() throws IOException {
        ArrayList<Student> students = new ArrayList<>();
        for (User user : loadUsers()) {
            if (user instanceof Student)
                students.add((Student) user);
        }
        return students;
    }

    public static ArrayList<Teacher> loadTeachers() throws IOException {
        ArrayList<Teacher> teachers = new ArrayList<>();
        for (User user : loadUsers()) {
            if (user instanceof Teacher)
                teachers.add((Teacher) user);
        }
        return teachers;
    }
}
