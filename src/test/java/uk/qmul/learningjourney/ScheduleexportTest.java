package uk.qmul.learningjourney;

import fr.opensagres.xdocreport.core.XDocReportException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

public class ScheduleexportTest {
    @Test
void Test() throws IOException, XDocReportException {
    ArrayList<String> schedule = new ArrayList<>();
    schedule.add("111");
    schedule.add("222");
    schedule.add("333");
    schedule.add("444");
    schedule.add("555");
    Util util = new Util();
    util.generateWord("wulvh","2",schedule,"./tests.docx");
    }
}