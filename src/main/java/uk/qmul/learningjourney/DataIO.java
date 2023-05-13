package uk.qmul.learningjourney;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.TypeVariable;
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
        return mapper.readValue(file, new TypeReference<ArrayList<?>>(){});
    }
}
