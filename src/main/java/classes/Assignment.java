package classes;

import dataBase.CRUD;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import static dataBase.CRUD.*;

public class Assignment {
    Course course;
    String title;
    String description;
    String deadline;
    Time estimatedTime;
    String studentDescription;
    double score;



    public Assignment(Course course, String title) {
        this.course = course;
        this.title = title;
    }
    public Assignment(String title) {
        this.title = title;
    }
    public Assignment(Course course, String title, String description, String deadline) {
        this.course = course;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
    }
    public Assignment(Course course, String title, String description, String deadline, double score) {
        this.course = course;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.score = score;
    }



    public void setDescription(String description) {
        this.description = description;
    }
    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }


    public String getDeadline() {
        return deadline;
    }

    public Course getCourse() {
        return course;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }


    /*public Assignment getWholeAssignment(){//we only have title in assignment
        try (Stream<String> lines = Files.lines(Paths.get(STR."\{COURSE_DIR}\\\{this.id}.txt"))){

            setNameAndUnit(
                    lines.filter(a -> a.startsWith("name:")).collect(Collectors.joining()).substring(5),
                    Integer.parseInt(Files.lines(Paths.get(STR."\{COURSE_DIR}\\\{this.id}.txt")).filter(a -> a.startsWith("unit:")).collect(Collectors.joining()).substring(5)));

            setTeacher(new Teacher(
                    Files.lines(Paths.get(STR."\{COURSE_DIR}\\\{this.id}.txt")).filter(a -> a.startsWith("teacher:")).collect(Collectors.joining()).substring(8)
            ));
            return this;
        } catch (IOException e){
            System.out.println("ERROR in getting Assignments for course");
        }
        return null;
    }*/

    public String toStringCourse(){
        return STR."ASS:\{title}?\{course.getId()}?\{description}?\{deadline}";
    }
    public String toString(){
        return STR."ASS:\{title}?\{course.getId()}?\{score}?\{description}";
    }
    public String toStringALLOBJECTS(){
        return STR."ASS:\{title}?\{course.getId()}";
    }

    /*public static void main(String[] args) throws IOException {
        String s = STR."\{COURSE_DIR}\\4022AP-A.txt";
        var temp = Files.readAllLines(Path.of(s)).get(4);
        var tem = temp.substring(4);
        var o = tem.split("\\?")[3];
        LocalDate d = LocalDate.parse();
        System.out.println(d);

    }*/
    public static List<Assignment> getAllTheAssignmentsTitles(){
        try {
            var temp = Files.readAllLines(Paths.get(STR."\{ALL_OBJECTS}"));
            List<Assignment> result = new ArrayList<>();
            for (String s : temp){
                if (s.startsWith("ASS:")){
                    result.add(new Assignment(s.substring(4).split("\\?")[0]));
                }
            }
            return result;
        } catch (IOException e){
            System.out.println("ERROR in  getting all assignments of ALL_OBJECT");
            return null;
        }
    }

    public void removeAssignment(){
        if (this.getCourse() == null){
            System.out.println("we don't know the course of the assignment, so we can't delete it");
        } else {
            CRUD.deleteLine(STR."ASS:\{this.getTitle()}?\{this.getCourse().getId()}", ALL_OBJECTS.toPath());// delete from ALL_OBJECT
            CRUD.deleteLine(
                    STR."ASS:\{this.getTitle()}?\{this.getCourse().getId()}",
                    Paths.get(STR."\{COURSE_DIR}\\\{this.getCourse().getId()}.txt"));// delete from COURSE file
            try { // delete from student files
                Files.lines(Paths.get(STR."\{COURSE_DIR}\\\{this.getCourse().getId()}.txt"))
                        .filter(b -> b.startsWith("STUDENT:"))
                        .map(c -> c.substring(8))
                        .forEach(d -> CRUD.deleteLine(
                                STR."ASS:\{this.getTitle()}?\{this.getCourse().getId()}",
                                Paths.get(STR."\{STUDENT_DIR}\\\{d}.txt")));
            } catch (IOException e) {
                System.out.println("exception in deleting assignment from student files");
            }
        }
    }

}
