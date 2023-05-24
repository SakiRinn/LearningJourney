package uk.qmul.learningjourney.util;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;
import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import uk.qmul.learningjourney.MainApplication;
import uk.qmul.learningjourney.model.Course;
import uk.qmul.learningjourney.model.Grade;
import uk.qmul.learningjourney.model.Position;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

import static uk.qmul.learningjourney.util.CourseUtil.getCourse;

public class DataIO {

    private static final String dataPath = "src/main/resources/uk/qmul/learningjourney/data/";

    public static <T> void saveObject(T obj) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);

        File file = new File(dataPath + obj.getClass().getSimpleName() + ".json");
        ArrayList<T> data = (ArrayList<T>) DataIO.loadObjects(obj.getClass());
        if (data == null) {
            data = new ArrayList<>();
        }
        data.add(obj);
        mapper.writeValue(file, data);
    }

    public static <T> void saveObject(T obj, String fileName) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);

        File file = new File(dataPath + fileName + ".json");
        ArrayList<T> data = (ArrayList<T>) DataIO.loadObjects(obj.getClass());
        if (data == null) {
            data = new ArrayList<>();
        }
        data.add(obj);
        mapper.writeValue(file, data);
    }

    public static void saveObjects(ArrayList<?> list, Class<?> cls) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);

        File file = new File(dataPath + cls.getSimpleName() + ".json");
        mapper.writeValue(file, list);
    }

    public static void saveObjects(ArrayList<?> list, String fileName) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);

        File file = new File(dataPath + fileName + ".json");
        mapper.writeValue(file, list);
    }

    public static ArrayList<?> loadObjects(Class<?> cls) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        File file = new File(dataPath + cls.getSimpleName() + ".json");
        if (!file.exists() || file.length() == 0)
            return null;
        return mapper.readValue(file, CollectionsTypeFactory.listOf(cls));
    }

    public static ArrayList<?> loadObjects(Class<?> cls, String fileName) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        File file = new File(dataPath + fileName + ".json");
        if (!file.exists() || file.length() == 0)
            return null;
        return mapper.readValue(file, CollectionsTypeFactory.listOf(cls));
    }

    public static void exportSchedule(String name, int week, ArrayList<String> courseId)
            throws IOException, XDocReportException {

        try (InputStream ins = MainApplication.class.getResourceAsStream("template/scheduleExport.docx")) {
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
            File folder = new File("output");
            if (!folder.exists())
                folder.mkdirs();
            String path = "output/" + name.replace(" ", "") + "_schedule.docx";
            FileOutputStream out = new FileOutputStream(path);
            report.process(context, out);
        }
    }

    public static void exportGrade(ArrayList<Grade> grades, String name) throws IOException {
        //Creating Word Document Objects
        XWPFDocument document = new XWPFDocument();

        XWPFTable table = setLayout(document, "Grade");

        //Set header
        table.getRow(0).setHeight(500);
        setCellText(table.getRow(0).getCell(0), "Course name", 1000);
        setCellText(table.getRow(0).getCell(1), "Credit", 2000);
        setCellText(table.getRow(0).getCell(2), "Score", 2000);

        int j = 0;
        for (Grade grade : grades) {
            //This is the addition in the table, which row is it added to
            XWPFTableRow row = table.insertNewTableRow(j + 1);
            //Set Cell Height
            row.setHeight(1000);
            for (int i = 0; i < 3; i++) {

                XWPFTableCell cell = row.createCell();
                CTTc ctTc = cell.getCTTc();
                CTTcPr ctTcPr = ctTc.addNewTcPr();
                if (i == 0) {   // name
                    ctTcPr.addNewTcW().setW(BigInteger.valueOf(1000));
                    cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);
                    cell.setText(grade.getCourse());
                }
                if (i == 1) {   // credit
                    ctTcPr.addNewTcW().setW(BigInteger.valueOf(2000));
                    cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);
                    cell.setText(String.valueOf(grade.getCredit()));
                }
                if (i == 2) {   // score
                    ctTcPr.addNewTcW().setW(BigInteger.valueOf(2000));
                    cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);
                    cell.setText(String.valueOf(grade.getScore()));
                }
            }
            j++;
        }

        //File stream output
        File folder = new File("output");
        if (!folder.exists())
            folder.mkdirs();
        FileOutputStream fos = new FileOutputStream("output/" + name.replace(" ", "") + "_grade.docx");
        document.write(fos);
        fos.close();
    }

    public static void exportAchievement(ArrayList<Position> positions, String name) throws IOException {
        //Creating Word Document Objects
        XWPFDocument document = new XWPFDocument();

        XWPFTable table = setLayout(document, "Personal Achievement");

        //Set header
        table.getRow(0).setHeight(500);
        setCellText(table.getRow(0).getCell(0), "Achievement name", 1000);
        setCellText(table.getRow(0).getCell(1), "DATE", 2000);
        setCellText(table.getRow(0).getCell(2), "Credit", 2000);
        int j = 0;
        for (Position position : positions) {
            //This is the addition in the table, which row is it added to
            XWPFTableRow row = table.insertNewTableRow(j + 1);
            //Set Cell Height
            row.setHeight(1000);
            for (int i = 0; i < 3; i++) {

                XWPFTableCell cell = row.createCell();
                CTTc ctTc = cell.getCTTc();
                CTTcPr ctTcPr = ctTc.addNewTcPr();
                if (i == 0) {//name
                    ctTcPr.addNewTcW().setW(BigInteger.valueOf(1000));
                    cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);
                    cell.setText(position.getName());
                }
                if (i == 1) {  //date
                    ctTcPr.addNewTcW().setW(BigInteger.valueOf(2000));
                    cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);
                    cell.setText(position.getDate());
                }
                if (i == 2) {  //?
                    ctTcPr.addNewTcW().setW(BigInteger.valueOf(2000));
                    cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);
                    cell.setText(position.getIsCreditable().toString());
                }
            }
            j++;
        }

        //File stream output
        File folder = new File("output");
        if (!folder.exists())
            folder.mkdirs();
        FileOutputStream fos = new FileOutputStream("output/" + name.replace(" ", "") + "_achievement.docx");
        document.write(fos);
        fos.close();
    }

    private static void setCellText(XWPFTableCell cell, String text, Integer width) {
        CTTc ctTc = cell.getCTTc();
        CTTcPr ctTcPr = ctTc.addNewTcPr();
        ctTcPr.addNewTcW().setW(BigInteger.valueOf(width));
        cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
        CTTcPr ctPr = ctTc.addNewTcPr();
        ctPr.addNewVAlign().setVal(STVerticalJc.CENTER);
        cell.setText(text);
    }

    private static XWPFTable setLayout(XWPFDocument document, String string) {
        //Create Title
        XWPFParagraph title = document.createParagraph();
        //Set paragraph center
        title.setAlignment(ParagraphAlignment.valueOf(STJc.INT_CENTER));
        XWPFRun titleRun = title.createRun();
        titleRun.setFontSize(20);
        titleRun.setFontFamily("Candara");
        titleRun.setBold(true);
        titleRun.setText(string);
        titleRun.addBreak();
        //3 is the number of columns and can be adjusted independently
        XWPFTable table = document.createTable(1, 3);
        CTTbl tTbl = table.getCTTbl();
        CTTblPr tTblPr = tTbl.getTblPr() == null ? tTbl.addNewTblPr() : tTbl.getTblPr();
        CTTblWidth tTblWidth = tTblPr.isSetTblW() ? tTblPr.getTblW() : tTblPr.getTblW();

        //This width is for the entire large table, not for local or specific individual sizes
        tTblWidth.setW(new BigInteger("10000"));
        tTblWidth.setType(STTblWidth.DXA);
        return table;
    }
}

class CollectionsTypeFactory {
    static JavaType listOf(Class cls) {
        return TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, cls);
    }
}
