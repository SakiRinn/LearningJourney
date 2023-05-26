package uk.qmul.learningjourney.util;

import com.fasterxml.jackson.core.type.TypeReference;
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
import uk.qmul.learningjourney.model.Achievement;
import uk.qmul.learningjourney.model.Course;
import uk.qmul.learningjourney.model.Grade;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static uk.qmul.learningjourney.util.CourseUtil.getCourse;

/**
 * Util class that read and write data.
 * <p>
 *     The supported data I/O formats are
 *     <ul>
 *         <li><code>.json</code>: Used as database, storing the objects of entity class.</li>
 *         <li><code>.docx</code>: Used for exporting data for users to view.</li>
 *     </ul>
 * </p>
 *
 * @author Lyuhua Wu
 */
public class DataIO {

    /**
     * The root path for storing all data files.
     */
    public static final String dataPath = "src/main/resources/uk/qmul/learningjourney/data/";

    /**
     * Add an object to the database (JSON file).
     * <p>
     *     The base name of the JSON file defaults to the class name of the object.
     *     If the database not exists, create a new one.
     * </p><p>
     *     Note that This method is a generic method, which means that it can automatically
     *     read the type of `obj` and store it into a proper database.
     * </p><p>
     *     <em>Attention: </em>This method does not support polymorphism with generic.
     * </p>
     *
     * @param obj The object to be stored.
     * @throws IOException
     */
    public static <T> void saveObject(T obj) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);

        File file = new File(dataPath + obj.getClass().getSimpleName() + ".json");
        ArrayList<T> list = (ArrayList<T>) DataIO.loadObjects(obj.getClass());
        if (list == null)
            list = new ArrayList<>();
        list.add(obj);
        mapper.writerFor(new TypeReference<List<T>>(){}).writeValue(file, list);
    }

    /**
     * Import the entire list into the database and <b>overwrite</b> all the original data.
     * <p>
     *     Limited by Java's type erasure mechanism, this method requires passing
     *     the generic class of the list (a `class` object).
     * </p><p>
     *     As an example, storing an <code>Arraylist&lt;String&gt;</code>` container named <code>strings</code>:
     * </p><code>
     *     saveObjects(strings, String.class);
     * </code>
     *
     * @param list  The list to be stored.
     * @param cls   The <code>Class</code> object of the generic class of the list.
     * @throws IOException
     */
    public static <T> void saveObjects(ArrayList<T> list, Class<T> cls) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);

        File file = new File(dataPath + cls.getSimpleName() + ".json");
        mapper.writerFor(new TypeReference<List<T>>(){}).writeValue(file, list);
    }

    /**
     * Read stored data of a single class.
     * <p>
     *     Similar to the above method, the base name of the JSON file read by default is the class name.
     * </p>
     *
     * @param cls The class of the data to be read
     * @return {@link ArrayList}<{@link ?}> All stored data
     * @throws IOException
     */
    public static ArrayList<?> loadObjects(Class<?> cls) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        File file = new File(dataPath + cls.getSimpleName() + ".json");
        if (!file.exists() || file.length() == 0)
            return null;
        return mapper.readValue(file, CollectionsTypeFactory.listOf(cls));
    }

    /**
     * Export course schedule to a file
     *
     * @param name     user name
     * @param week     week
     * @param courseId course ID
     * @throws IOException         IOException
     * @throws XDocReportException XDocReportException
     */
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

    /**
     * Export student's grade to a file
     *
     * @param grades grades
     * @param name   student's name
     * @throws IOException IOException
     */
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
                    cell.setText(grade.getCourseName());
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

    /**
     * Export student's achievements to a file
     *
     * @param achievements achievements
     * @param name         student's name
     * @throws IOException IOException
     */
    public static void exportAchievement(ArrayList<Achievement> achievements, String name) throws IOException {
        //Creating Word Document Objects
        XWPFDocument document = new XWPFDocument();

        XWPFTable table = setLayout(document, "Personal Achievement");

        //Set header
        table.getRow(0).setHeight(500);
        setCellText(table.getRow(0).getCell(0), "Achievement name", 1000);
        setCellText(table.getRow(0).getCell(1), "DATE", 2000);
        setCellText(table.getRow(0).getCell(2), "Credit", 2000);
        int j = 0;
        for (Achievement achievement : achievements) {
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
                    cell.setText(achievement.getName());
                }
                if (i == 1) {  //date
                    ctTcPr.addNewTcW().setW(BigInteger.valueOf(2000));
                    cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);
                    cell.setText(achievement.getDate());
                }
                if (i == 2) {  //?
                    ctTcPr.addNewTcW().setW(BigInteger.valueOf(2000));
                    cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);
                    cell.setText(achievement.getIsCreditable().toString());
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

    /**
     * Set table cell
     *
     * @param cell  {@link XWPFTableCell}
     * @param text  {@link String}
     * @param width {@link Integer}
     */

    private static void setCellText(XWPFTableCell cell, String text, Integer width) {
        CTTc ctTc = cell.getCTTc();
        CTTcPr ctTcPr = ctTc.addNewTcPr();
        ctTcPr.addNewTcW().setW(BigInteger.valueOf(width));
        cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
        CTTcPr ctPr = ctTc.addNewTcPr();
        ctPr.addNewVAlign().setVal(STVerticalJc.CENTER);
        cell.setText(text);
    }

    /**
     * Set document layout
     *
     * @param document document
     * @param string   string
     * @return {@link XWPFTable}
     */
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

/**
 * Encapsulation of TypeFactory-related operations.
 * <p>
 *     Can be viewed as a <b>function</b>.
 * </p>
 *
 * @author Lyuhua Wu
 */
class CollectionsTypeFactory {
    static JavaType listOf(Class cls) {
        return TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, cls);
    }
}
