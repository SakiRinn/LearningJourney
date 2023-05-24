package uk.qmul.learningjourney.model.user;

public class Teacher extends User {

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
