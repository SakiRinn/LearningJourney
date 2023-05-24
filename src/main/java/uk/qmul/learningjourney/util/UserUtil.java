package uk.qmul.learningjourney.util;

import uk.qmul.learningjourney.model.user.Student;
import uk.qmul.learningjourney.model.user.Teacher;
import uk.qmul.learningjourney.model.user.User;

import java.io.IOException;
import java.util.ArrayList;

public class UserUtil {

    public static void saveUser(User user) throws IOException {
        DataIO.saveObject(user, "User.json");
    }

    public static void saveUsers(ArrayList<User> users) throws IOException {
        DataIO.saveObjects(users, "User.json");
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
