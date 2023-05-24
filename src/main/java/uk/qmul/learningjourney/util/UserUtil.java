package uk.qmul.learningjourney.util;

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
}
