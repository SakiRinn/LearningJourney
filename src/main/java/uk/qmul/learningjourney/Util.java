package uk.qmul.learningjourney;

import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class Util {
    private static Util util;

    private Util() {
    }

    public static Util getInstance() {
        if (util == null)
            return new Util();
        return util;
    }

    public static boolean login(String id, String password) throws IOException {
        ArrayList<Student> students = (ArrayList<Student>) DataIO.loadObjects(Student.class);
        assert students != null;
        for (Student student : students) {
            if (student.getId().equals(id))
                if (student.getPassword().equals(password)) {
                    Context.student = student;
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
        ArrayList<String> selectedCourses = Context.student.getCourses();
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
        ArrayList<String> courses = Context.student.getCourses();
        courses.add(course.getId());
        Context.student.setCourses(courses);
        ArrayList<Student> students = (ArrayList<Student>) DataIO.loadObjects(Student.class);
        for (Student student : students) {
            if (student.getId().equals(Context.student.getId())) {
                students.remove(student);
                break;
            }
        }
        students.add(Context.student);
        DataIO.saveObjects(students, Context.student.getClass());
    }

    public void generateWord(String name, String weeknum, ArrayList<String> arr, String path) throws IOException, XDocReportException {
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

        String path = "D://" + name + "_schedule.docx";


        //输出到本地目录
        FileOutputStream out = new FileOutputStream(path);
        report.process(context, out);
    }

    public void exportSchedule(String name, int week, ArrayList<String> courseId) {
        InputStream ins = this.getClass().getResourceAsStream("docxmodel/scheduleModel.docx");
        try {
            IXDocReport report = XDocReportRegistry.getRegistry().loadReport(ins,
                    TemplateEngineKind.Freemarker);
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
            String path = "D://" + name + "_schedule.docx";


            //输出到本地目录
            FileOutputStream out = new FileOutputStream(path);
            report.process(context, out);
        } catch (IOException | XDocReportException e) {
            throw new RuntimeException(e);
        }


    }
}
