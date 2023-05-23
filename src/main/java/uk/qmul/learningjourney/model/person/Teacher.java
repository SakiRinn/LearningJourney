package uk.qmul.learningjourney.model.person;

import uk.qmul.learningjourney.model.person.Person;

public class Teacher extends Person {

    private boolean isAdmin;

    public Teacher() {
        this.isAdmin = false;
    }

    public Teacher(String id, String name, String password) {
        super(id, name, password);
        this.isAdmin = false;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
