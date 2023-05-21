package uk.qmul.learningjourney.grade;

import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import uk.qmul.learningjourney.Context;
import uk.qmul.learningjourney.DataIO;
import uk.qmul.learningjourney.Student;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

public class GradeModel {

    public static HashMap<Integer, Double> score2GPA = getGPAMap();
    public static HashMap<String, Double> studentId2Score = getScoreMap();

    private static HashMap<Integer, Double> getGPAMap() {
        int[] scores = new int[]{59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69,
                                 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80,
                                 81, 82, 83, 84, 85, 86, 87, 88, 89, 90,
                                 91, 92, 93, 94, 95, 96, 97, 98, 99, 100};
        double[] GPAs = new double[]{0.00, 1.00, 1.15, 1.29, 1.43, 1.57, 1.70, 1.83, 1.96, 2.08, 2.20,
                                     2.31, 2.42, 2.53, 2.63, 2.73, 2.83, 2.92, 3.01, 3.09, 3.17, 3.25,
                                     3.32, 3.39, 3.46, 3.52, 3.58, 3.63, 3.68, 3.73, 3.77, 3.81,
                                     3.85, 3.88, 3.91, 3.93, 3.95, 3.97, 3.98, 3.99, 4.00, 4.00};
        HashMap<Integer, Double> map = new HashMap<>();
        for (int i = 0; i < scores.length; i++)
            map.put(scores[i], GPAs[i]);
        return map;
    }

    private static HashMap<String, Double> getScoreMap() {
        HashMap<String, Double> map = new HashMap<>();
        try {
            for (Student student : (ArrayList<Student>) DataIO.loadObjects(Student.class))
                map.put(student.getId(), getAverageScore(student));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static ArrayList<Grade> getGrades() {
        ArrayList<Grade> grades = new ArrayList<>();
        try {
            ArrayList<Grade> allGrades = (ArrayList<Grade>) DataIO.loadObjects(Grade.class);
            if (allGrades != null) {
                for (Grade grade : allGrades) {
                    if (grade.getStudent().getName().equals(Context.student.getName()))
                        grades.add(grade);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return grades;
    }

    public static ArrayList<Grade> getGrades(Student student) {
        ArrayList<Grade> grades = new ArrayList<>();
        try {
            ArrayList<Grade> allGrades = (ArrayList<Grade>) DataIO.loadObjects(Grade.class);
            if (allGrades != null) {
                for (Grade grade : allGrades) {
                    if (grade.getStudent().getName().equals(student.getName()))
                        grades.add(grade);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return grades;
    }

    public static double getAverageScore() {
        double totalScore = 0.0;
        for (Grade grade : getGrades())
            totalScore += grade.getScore();
        return totalScore / getGrades().size();
    }

    public static double getAverageScore(Student student) {
        double totalScore = 0.0;
        for (Grade grade : getGrades(student))
            totalScore += grade.getScore();
        return totalScore / getGrades().size();
    }

    public static double getAverageGPA() {
        return score2GPA.get((int) Math.round(getAverageScore()));
    }

    public static double getAverageGPA(Student student) {
        return score2GPA.get((int) Math.round(getAverageScore(student)));
    }

    public static int getRank() {
        double currentScore = studentId2Score.get(Context.student.getId());
        int rank = 1;
        try {
            for (Student stu : (ArrayList<Student>) DataIO.loadObjects(Student.class)) {
                if (studentId2Score.get(stu.getId()) > currentScore)
                    rank++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return rank;
    }

    public static int getRank(Student student) {
        double currentScore = studentId2Score.get(student.getId());
        int rank = 1;
        try {
            for (Student stu : (ArrayList<Student>) DataIO.loadObjects(Student.class)) {
                if (studentId2Score.get(stu.getId()) > currentScore)
                    rank++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return rank;
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

    public static void generateWord(ArrayList<Grade> grades, String path) throws IOException {
        //Creating Word Document Objects
        XWPFDocument document = new XWPFDocument();
        //Create Title
        XWPFParagraph title = document.createParagraph();
        //Set paragraph center
        title.setAlignment(ParagraphAlignment.valueOf(STJc.INT_CENTER));
        XWPFRun titleRun = title.createRun();

        titleRun.setFontSize(20);
        titleRun.setFontFamily("Candara");
        titleRun.setBold(true);
        titleRun.setText("transcript");
        titleRun.addBreak();

        // 3 is the number of columns and can be adjusted independently
        XWPFTable table = document.createTable(1, 3);
        CTTbl tTbl = table.getCTTbl();
        CTTblPr tTblPr = tTbl.getTblPr() == null ? tTbl.addNewTblPr() : tTbl.getTblPr();
        CTTblWidth tTblWidth = tTblPr.isSetTblW() ? tTblPr.getTblW() : tTblPr.getTblW();

        //This width is for the entire large table, not for local or specific individual sizes
        tTblWidth.setW(new BigInteger("10000"));
        tTblWidth.setType(STTblWidth.DXA);

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
                    cell.setText(grade.getName());
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
        FileOutputStream fos = new FileOutputStream(new File(path));
        document.write(fos);
        fos.close();
    }
}
