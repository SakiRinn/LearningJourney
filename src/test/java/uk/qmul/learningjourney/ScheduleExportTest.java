package uk.qmul.learningjourney;

import fr.opensagres.xdocreport.core.XDocReportException;
import org.junit.jupiter.api.Test;
import uk.qmul.learningjourney.util.DataIO;

import java.io.IOException;
import java.util.ArrayList;

public class ScheduleExportTest {
    @Test
    void Test() {
        ArrayList<String> schedule = new ArrayList<>();
        schedule.add("111");
        schedule.add("222");
        schedule.add("333");
        schedule.add("444");
        schedule.add("555");

        try {
            DataIO.exportSchedule("wulvh", 2, schedule);
        } catch (IOException | XDocReportException e) {
            throw new RuntimeException(e);
        }
    }
}
