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

    public static ArrayList<Course> getAvailCourses() throws IOException {
        ArrayList<Course> courses = (ArrayList<Course>) DataIO.loadObjects(Course.class);
        ArrayList<Course> availCourses = new ArrayList<>();
        ArrayList<Course> selectedCourses = Context.student.getCourses();
        HashMap<String, Course> courseId = new HashMap<>();
        for (Course course : selectedCourses) {
            courseId.put(course.getId(), course);
        }
        for (Course course : courses) {
            if (!courseId.containsKey(course.getId())) {
                availCourses.add(course);
            }
        }
        return availCourses;

    }

    public static void studentAddCourse(Course course) throws IOException {
        ArrayList<Course> courses = Context.student.getCourses();
        courses.add(course);
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
    public void generateWord(String name, String weeknum,ArrayList<String> arr,String path) throws IOException, XDocReportException {
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
        for(int i=0;i<arr.size();i++){
            context.put("course"+(i+1), arr.get(i));
        }




        //输出到本地目录
        FileOutputStream out = new FileOutputStream(new File(path));
        report.process(context, out);
    }
}
