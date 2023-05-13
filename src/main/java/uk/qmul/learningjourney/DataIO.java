package uk.qmul.learningjourney;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class DataIO {

    private static final String dataPath = "src/main/resources/uk/qmul/learningjourney/data/";

    public static <T> void saveObject(T obj) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);

        File file = new File(dataPath + obj.getClass().getSimpleName() + ".json");
        ArrayList<T> data = (ArrayList<T>) DataIO.loadObjects(obj.getClass());
        if (data != null) {
            data.add(obj);
            mapper.writeValue(file, data);
        }
        else {
            data = new ArrayList<>();
            data.add(obj);
            mapper.writeValue(file, data);
        }
    }

    public static ArrayList<?> loadObjects(Class<?> cls) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        File file = new File(dataPath + cls.getSimpleName() + ".json");
        if (!file.exists() || file.length() == 0)
            return null;
        return mapper.readValue(file, CollectionsTypeFactory.listOf(cls));
    }
}

class CollectionsTypeFactory {
    static JavaType listOf(Class clazz) {
        return TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, clazz);
    }
}
