package uk.qmul.learningjourney.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import uk.qmul.learningjourney.model.Course;

import java.io.IOException;

class CourseUtilTest {


    @Test
    void getCourse() throws IOException {

        Course course = CourseUtil.getCourse("EBU6230");
        Assertions.assertEquals(course.getId(), "EBU6230");

    }

    @Test
    void getAvailCourses() {
    }

    @Test
    void studentAddCourse() {
    }
}