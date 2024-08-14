package classes;

import dataBase.SQLConnect;

import java.util.List;
import java.util.stream.Collectors;

public class Teacher{
    final private static SQLConnect sql = SQLConnect.getInstance();
    final private String name;
    public Teacher(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    public List<Course> getTeacherCourses(){
        List<Course> courses = sql.query("SELECT course_id FROM teachers_courses" +
                STR." WHERE teacher_name = '\{name}';").stream()
                .map(a -> new Course(a, this))
                .collect(Collectors.toList());
        courses.forEach(d -> {
            d.setName(d.getCourseNameById());
            d.setUnit(d.getCourseUnitById());
        });
        return courses;
    }
}
