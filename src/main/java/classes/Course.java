package classes;

import dataBase.CRUD;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static dataBase.CRUD.*;
import static dataBase.CRUD.STUDENT_DIR;

public class Course {
    private static final File COURSE_DIR = new File("C:\\Users\\Yasamin\\OneDrive\\universe\\term2\\AP\\project\\project_repsitoryPattern\\src\\main\\java\\dataBase\\Courses");
    private String name; //مدار الکتریکی
    private String id; //4022EC
    private int unit; //3
    private String examDate;
    private Teacher teacher;
    private List<String> courseTimes = new ArrayList<>();
    private ArrayList<Assignment> assignments = new ArrayList<>();
    private ArrayList<Student> students = new ArrayList<>();



    public Course(String id) {
        this.id = id;
    }
    public Course(String id, Teacher teacher) {
        this.teacher = teacher;
        this.id = id;
    }




    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public int getUnit() {
        return unit;
    }
    public String getExamDate() {
        return examDate.toString();
    }

    public Course getWholeCourse() {//we only have id
        List<String> linesss = new ArrayList<>();
        try {
            linesss = Files.readAllLines(Paths.get(STR."\{COURSE_DIR}\\\{this.id}.txt"));
        } catch (IOException e) {
            System.out.println("ERROR in getting Assignments for course");
            return null;
        }
        for (String s : linesss) {
            if (s.startsWith("name:")) setName(s.substring(5));
            else if (s.startsWith("unit:")) setUnit(Integer.parseInt(s.substring(5)));
            else if (s.startsWith("teacher:")) setTeacher(new Teacher(s.substring(8)));
            else if (s.startsWith("examDate:")) setExamDate(s.substring(9));
            else if (s.startsWith("times:")) setCourseTimes(List.of(s.substring(7, s.length() - 2).trim().split(",")));
            else continue;
        }
        return this;
    }
    public String toStringInCourses(){
        return STR."id:\{id}\nname:\{name}\nunit:\{unit}\nteacher:\{teacher.getName()}\nexamDate:\{examDate}\ntimes:\{courseTimes}";
    }
    public String toString(){
        return STR."COURSE:\{id}";
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public void setAssignments(Assignment assignment) {
        assignments.add(assignment);
    }
    public void setStudent(Student student) {
        students.add(student);
    }
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
    public void setCourseTimes(List<String> courseTimes) {
        this.courseTimes = courseTimes;
    }
    public void setExamDate(String examDate) {
        this.examDate = examDate;
    }

    public List<String> getAssignmentTitles() {
        try (Stream<String> lines = Files.lines(Paths.get(STR."\{COURSE_DIR}\\\{id}.txt"))){
            return lines.filter(a -> a.startsWith("ASS:"))
                    .map(b -> b.substring(4))
                    .map(c -> c.split("\\?")[0])
                    .toList();
        } catch (IOException e){
            System.out.println("ERROR in getting Assignments for course");
        }
        return null;
    }
    public List<String> getStudentIds() {
        try (Stream<String> lines = Files.lines(Paths.get(STR."\{COURSE_DIR}\\\{id}.txt"))){
            return lines.filter(a -> a.startsWith("STUDENT:"))
                    .map(b -> b.substring(8))
                    .toList();
        } catch (IOException e){
            System.out.println("ERROR in getting Student Ids for course");
        }
        return null;
    }
    public String getCourseNameById(){
        return CRUD.read(STR."\{COURSE_DIR}\\\{id}.txt").lines()
                .filter(a -> a.startsWith("name:"))
                .map(b -> b.substring(5))
                .collect(Collectors.joining());
    }
    public Integer getCourseUnitById(){
        return Integer.valueOf(CRUD.read(STR."\{COURSE_DIR}\\\{id}.txt").lines()
                .filter(a -> a.startsWith("unit:"))
                .map(b -> b.substring(5))
                .collect(Collectors.joining()));
    }
    public String getTeacherName(){
        return CRUD.read(STR."\{COURSE_DIR}\\\{id}.txt").lines()
                .filter(a -> a.startsWith("teacher:"))
                .map(b -> b.substring(8))
                .collect(Collectors.joining());
    }

    public void removeCourse(){
        CRUD.deleteLine(STR."COURSE:\{this.getId()}", ALL_OBJECTS.toPath()); // delete from ALL_OBJECT
        CRUD.deleteLine(// delete from teacher file
                STR."COURSE:\{this.getId()}",
                Paths.get(STR."\{TEACHER_DIR}\\\{this.getTeacherName()}.txt"));
        try { // delete from student files
            Files.lines(Paths.get(STR."\{COURSE_DIR}\\\{this.getId()}.txt"))
                    .filter(b -> b.startsWith("STUDENT:"))
                    .map(c -> c.substring(8))
                    .forEach(d -> CRUD.deleteLine(
                            STR."COURSE:\{this.getId()}",
                            Paths.get(STR."\{STUDENT_DIR}\\\{d}.txt")));
            Files.delete(Paths.get(STR."\{COURSE_DIR}\\\{this.getId()}.txt"));
        } catch (IOException e) {
            System.out.println("exception in deleting course from student files or course file");
        }

    }
}
