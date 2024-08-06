package classes;

import Exceptions.AssignmentDoesntBelongToTeacher;
import Exceptions.AssignmentIsAlreadyInUse;
import Exceptions.CourseDoesntBelongToTeacher;
import Exceptions.StudentNotRegisteredInTerm;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

public class Teacher{
    final private String name;
    private static final File TEACHER_DIR = new File("C:\\Users\\Yasamin\\OneDrive\\universe\\term2\\AP\\project\\project_repsitoryPattern\\src\\main\\java\\dataBase\\Teachers");

    public Teacher(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Course> getTeacherCourses(){
        try (Stream<String> lines = Files.lines(Paths.get(STR."\{TEACHER_DIR}\\\{this.getName()}.txt"))){
            var result =  lines.filter(a -> a.startsWith("COURSE:"))
                    .map(b -> b.substring(7))
                    .map(c -> new Course(c, this))
                    .toList();
            result.forEach(d -> {
                d.setName(d.getCourseNameById());
                d.setUnit(d.getCourseUnitById());
            });
            return result;
        } catch (IOException e){
            System.out.println("ERROR in getting teacher course for CLI");
        }
        return null;
    }
}
