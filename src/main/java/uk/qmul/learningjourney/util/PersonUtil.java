package uk.qmul.learningjourney.util;

import uk.qmul.learningjourney.model.person.Person;

import java.io.IOException;
import java.util.ArrayList;

public class PersonUtil {

    public static void savePerson(Person person) throws IOException {
        DataIO.saveObject(person, "Person.json");
    }

    public static void savePersons(ArrayList<Person> persons) throws IOException {
        DataIO.saveObjects(persons, "Person.json");
    }

    public static ArrayList<Person> loadPersons() throws IOException {
        return (ArrayList<Person>) DataIO.loadObjects(Person.class);
    }
}
