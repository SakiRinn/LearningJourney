package uk.qmul.learningjourney.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Define user entity
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes(value = {
        @JsonSubTypes.Type(value = Teacher.class, name = "Teacher"),
        @JsonSubTypes.Type(value = Student.class, name = "Student"),
})

public abstract class User {
    /**
     * user ID
     */
    protected String id;
    /**
     * user name
     */
    protected String name;
    /**
     * user password
     */
    protected String password;

    public User() {
        this.id = null;
        this.name = null;
        this.password = null;
    }

    public User(String id, String name, String password) {
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

    @JsonIgnore
    public String getType() {
        return this.getClass().getSimpleName();
    }

    @Override
    public String toString() {
        return name + " (" + id + ")";
    }
}
