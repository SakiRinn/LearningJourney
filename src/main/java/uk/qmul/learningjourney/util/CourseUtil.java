package uk.qmul.learningjourney.util;

import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import uk.qmul.learningjourney.Context;
import uk.qmul.learningjourney.model.Course;
import uk.qmul.learningjourney.model.Position;
import uk.qmul.learningjourney.model.user.Student;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

public class CourseUtil {
    private static CourseUtil courseUtil;

    private CourseUtil() {
    }

    public static CourseUtil getInstance() {
        if (courseUtil == null)
            courseUtil = new CourseUtil();
        return courseUtil;
    }

    public static boolean login(String id, String password) throws IOException {
        ArrayList<Student> students = (ArrayList<Student>) DataIO.loadObjects(Student.class);
        assert students != null;
        for (Student student : students) {
            if (student.getId().equals(id))
                if (student.getPassword().equals(password)) {
//                    Context.account = student;
                    Context.user = student;
                    return true;
                }
        }
        return false;
    }

    public static Course getCourse(String courseId) {
        ArrayList<Course> courses = null;
        try {
            courses = (ArrayList<Course>) DataIO.loadObjects(Course.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        HashMap<String, Course> courseMap = new HashMap<>();
        for (Course course : courses) {
            if (course.getId().equals(courseId))
                return course;
        }
        return null;
    }

    public static ArrayList<Course> getAvailCourses() throws IOException {
        ArrayList<Course> courses = (ArrayList<Course>) DataIO.loadObjects(Course.class);
        ArrayList<Course> availCourses = new ArrayList<>();
        Student student = (Student) Context.user;
        ArrayList<String> selectedCourses = student.getCourses();
        HashMap<String, Course> courseMap = new HashMap<>();
        for (String s : selectedCourses) {
            courseMap.put(s, getCourse(s));
        }
        for (Course course : courses) {
            if (!courseMap.containsKey(course.getId())) {
                availCourses.add(course);
            }
        }
        return availCourses;
    }

    public static void studentAddCourse(Course course) throws IOException {
        Student student = (Student) Context.user;
        ArrayList<String> courses = student.getCourses();
        courses.add(course.getId());
        student.setCourses(courses);
        ArrayList<Student> students = (ArrayList<Student>) DataIO.loadObjects(Student.class);
        for (Student s : students) {
            if (s.getId().equals(student.getId())) {
                students.remove(s);
                break;
            }
        }
        students.add(student);
        DataIO.saveObjects(students, student.getClass());
    }

    public void generateWord(String name, String weeknum, ArrayList<String> arr, String path)
            throws IOException, XDocReportException {
        //获取Word模板，模板存放路径在项目的resources目录下
        InputStream ins = this.getClass().getResourceAsStream("/sceduletest.docx");
        //注册xdocreport实例并加载FreeMarker模板引擎
        IXDocReport report = XDocReportRegistry.getRegistry().loadReport(ins,
                TemplateEngineKind.Freemarker);
        //创建xdocreport上下文对象
        IContext context = report.createContext();
        //创建要替换的文本变量
        context.put("name", name);
        context.put("weeknum", weeknum);
        for (int i = 0; i < arr.size(); i++) {
            context.put("course" + (i + 1), arr.get(i));
        }
        //输出到本地目录
        FileOutputStream out = new FileOutputStream(new File(path));
        report.process(context, out);
    }

    public void generateWord(String name, String weeknum, String arr, int index) throws IOException, XDocReportException {
        //获取Word模板，模板存放路径在项目的resources目录下
        InputStream ins = this.getClass().getResourceAsStream("/sceduletest.docx");
        //注册xdocreport实例并加载FreeMarker模板引擎
        IXDocReport report = XDocReportRegistry.getRegistry().loadReport(ins,
                TemplateEngineKind.Freemarker);
        //创建xdocreport上下文对象
        IContext context = report.createContext();
        //创建要替换的文本变量
        context.put("name", name);
        context.put("weeknum", weeknum);
        context.put("course" + index, arr);
        //输出到本地目录
        String path = "D://" + name + "_schedule.docx";
        FileOutputStream out = new FileOutputStream(path);
        report.process(context, out);
    }

    public void exportSchedule(String name, int week, ArrayList<String> courseId) {
        InputStream ins = this.getClass().getResourceAsStream("docxmodel/scheduleExport.docx");
        try {
            IXDocReport report = XDocReportRegistry.getRegistry().loadReport(ins, TemplateEngineKind.Freemarker);
            IContext context = report.createContext();
            context.put("name", name);
            context.put("weeknum", week);
            HashMap<Integer, String> map = new HashMap<>();
            for (String s : courseId) {
                Course course = getCourse(s);
                if (course.getSchedule().containsKey(0)) {
                    week = 0;
                    Integer[] sched = course.getSchedule().get(week);
                    for (Integer integer : sched) {
                        map.put(integer, course.getName());
                    }
                }
                if (course.getSchedule().containsKey(week)) {
                    Integer[] sched = course.getSchedule().get(week);
                    for (Integer integer : sched) {
                        map.put(integer, course.getName());
                    }
                }
            }
            for (int i = 1; i <= 14 * 5; i++) {
                context.put("course" + i, map.getOrDefault(i, ""));
            }
            //输出到本地目录
            String path = "D://" + name + "_schedule.docx";
            FileOutputStream out = new FileOutputStream(path);
            report.process(context, out);
        } catch (IOException | XDocReportException e) {
            throw new RuntimeException(e);
        }
    }
    private static void  setCellText(XWPFTableCell cell, String text, String bgcolor, Integer width){
        CTTc ctTc = cell.getCTTc();
        CTTcPr ctTcPr = ctTc.addNewTcPr();
        ctTcPr.addNewTcW().setW(BigInteger.valueOf(width));
//        cell.setColor(bgcolor);
        cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
        CTTcPr ctPr = ctTc.addNewTcPr();
        ctPr.addNewVAlign().setVal(STVerticalJc.CENTER);
        cell.setText(text);

    }

    public static void generateAchievementWord(ArrayList<Position> positions) throws IOException {
        //ArrayList<Achievement> achievements= new ArrayList<>();
        //Achievement a1 = new Achievement();
        //a1.setName("我会自己上厕所");
        //a1.setDate("yesterday");
        //Achievement a2 = new Achievement();
        //a2.setName("连续三天没尿床");
        //a2.setDate("future");
        //achievements.add(a1);
        //achievements.add(a2);
        //Creating Word Document Objects
        XWPFDocument document = new XWPFDocument();
        //Create Title
        XWPFParagraph title = document.createParagraph();
        //Set paragraph center
        title.setAlignment(ParagraphAlignment.valueOf(STJc.INT_CENTER));
//        //Set paragraph left alignment
//        title.setAlignment(ParagraphAlignment.valueOf(STJc.INT_LEFT));
//        //Set paragraph right alignment
//        title.setAlignment(ParagraphAlignment.valueOf(STJc.INT_RIGHT));
        XWPFRun titleRun = title.createRun();
//        titleRun.setColor("00000");
        titleRun.setFontSize(20);
        titleRun.setFontFamily("Candara");
        titleRun.setBold(true);
        titleRun.setText("Personal Achievement");
        titleRun.addBreak(); //wrap
        //3 is the number of columns and can be adjusted independently
        XWPFTable table = document.createTable(1,3);
        CTTbl tTbl = table.getCTTbl();
        CTTblPr tTblPr = tTbl.getTblPr() == null ? tTbl.addNewTblPr() : tTbl.getTblPr();
        CTTblWidth tTblWidth = tTblPr.isSetTblW() ? tTblPr.getTblW() : tTblPr.getTblW();
        //This width is for the entire large table, not for local or specific individual sizes
        tTblWidth.setW(new BigInteger("10000"));
        tTblWidth.setType(STTblWidth.DXA);
        //Set header
        table.getRow(0).setHeight(500);
        setCellText(table.getRow(0).getCell(0),"Achievement name",null,1000);
        setCellText(table.getRow(0).getCell(1),"DATE",null,2000);
        setCellText(table.getRow(0).getCell(2),"Credit",null,2000);
        Integer j = 0;
        for (Position position : positions) {
            //This is the addition in the table, which row is it added to
            XWPFTableRow row = table.insertNewTableRow(j+1);
            //Set Cell Height
            row.setHeight(1000);
            for (int i = 0; i < 3; i++ ) {

                XWPFTableCell cell = row.createCell();
                CTTc ctTc = cell.getCTTc();
                CTTcPr ctTcPr = ctTc.addNewTcPr();
                if(i == 0){//name
                    ctTcPr.addNewTcW().setW(BigInteger.valueOf(1000));
                    cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);
                    cell.setText(position.getName());
                }
                if(i == 1){  //date
                    ctTcPr.addNewTcW().setW(BigInteger.valueOf(2000));
                    cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);
                    cell.setText(position.getDate());
                }
                if(i == 2){  //?
                    ctTcPr.addNewTcW().setW(BigInteger.valueOf(2000));
                    cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);
                    cell.setText(position.getIsCreditable().toString());
                }
            }
            j++;
        }
        //File stream output
        FileOutputStream fos = new FileOutputStream(new File("D://aaaaaaaaaa.docx"));
        document.write(fos);
        fos.close();








    }
}
