package uk.qmul.learningjourney.model.person;

public abstract class Person {

    protected String id;
    protected String name;
    protected String password;

    public Person() {
        this.id = null;
        this.name = null;
        this.password = null;
    }

    public Person(String id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
