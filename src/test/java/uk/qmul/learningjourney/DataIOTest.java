package uk.qmul.learningjourney;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

class DataIOTest {

    @Test
    void saveObject() {
        try {
            ArrayList<int[]> times = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                times.add(new int[]{i, 2*i, 3*i});
            }
            for (int i = 0; i < 10; i++)
                DataIO.saveObject(new Course("同性原理", "114514", "捧月星", 100 + i, 4, "B205", 6, times));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void loadObjects() {
    }


}