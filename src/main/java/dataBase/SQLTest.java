package dataBase;

import classes.Course;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static dataBase.CRUD.*;


public class SQLTest {
    private static final File ALL_USERS = new File( "C:\\Users\\Yasamin\\OneDrive\\universe\\term2\\AP\\project\\project_repsitoryPattern\\src\\main\\java\\dataBase\\allUsers.txt");

    public static void main(String[] args) {
        String url = "jdbc:sqlite:src/main/java/dataBase/data.db";

        var names = getCoursesIds();
        System.out.println(names);

        var pass = getCoursesAssigns(names);
        System.out.println(pass);




        String sql = "INSERT INTO courses_assignments (course_id, ass_title) VALUES (?, ?)";
// courses_assignments (course_id VARCHAR,ass_title VARCHAR);
         try (var conn = DriverManager.getConnection(url);
                var stmt = conn.prepareStatement(sql)) {
            for (int i = 0; i < pass.size(); i++){
                var list = pass.get(names.get(i));
                if (list.isEmpty()){
                    continue;
                } else {
                    for (String s : list){
                        stmt.setString(1, names.get(i));
                        stmt.setString(2, s);
                        stmt.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private static Map<String, List<String>> getCoursesAssigns(List<String> names) {
        Map<String, List<String>> result = new HashMap<>();
        for (String name : names){
            List<String> assigns = new ArrayList<>();
            try (var line = Files.lines(Path.of(STR."\{COURSE_DIR}\\\{name}.txt"))){
                line.filter(a -> a.startsWith("ASS:"))
                        .map(b -> b.substring(4).split("\\?")[0])
                        .forEach(assigns::add);
            } catch (IOException e) {
                e.printStackTrace();
            }
            result.put(name, assigns);
        }
        return result;
    }

    private static List<Map<String, List<String>>> getStudentsCourses(List<String> names) {
        List<Map<String, List<String>>> result = new ArrayList<>();
        for (String id : names){
            List<String> lines = new ArrayList<>();
            try {
                lines = Files.readAllLines(Paths.get(STR."\{COURSE_DIR}\\\{id}.txt"));
            } catch (IOException e) {
                System.err.println("error in reading file in SQLTest");
            }
            List<String> temp = new ArrayList<>();
            for (String line : lines) {
                if (line.startsWith("STUDENT:"))
                    temp.add(line.substring(8));
            }
            Map<String, List<String>> t = new HashMap<>();
            t.put(id, temp);
            result.add(t);
        }
        return result;
    }

    private static Map<String, String> getTeachersCourses(List<String> names) {
        Map<String, String> result = new HashMap<>();
        for (String name : names){
            System.out.println(name);
            try (var line = Files.lines(Path.of(STR."\{TEACHER_DIR}\\\{name}.txt"))){
                line.filter(a -> a.startsWith("COURSE:"))
                        .map(b -> b.substring(7))
                        .forEach(c -> result.put(c, name));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private static List<Map<String, String>> getCourseLine(List<String> names) {
        List<Map<String, String>> result = new ArrayList<>();
        for (String id : names){
            List<String> lines = new ArrayList<>();
            try {
                lines = Files.readAllLines(Paths.get(STR."\{COURSE_DIR}\\\{id}.txt"));
            } catch (IOException e) {
                System.err.println("error in reading file in SQLTest");
            }
            Map<String, String> temp = getMap(id, lines);
            result.add(temp);
        }
        return result.reversed();
    }

    private static Map<String, String> getMap(String id, List<String> lines) {
        Map<String, String> temp = new HashMap<>();
        temp.put("id", id);
        for (String line : lines) {
            if (line.startsWith("name:"))
                temp.put("name", line.substring(5));
            if (line.startsWith("unit:"))
                temp.put("unit", line.substring(5));
            if (line.startsWith("examDate:"))
                temp.put("examDate", line.substring(9));
            if (line.startsWith("times:"))
                temp.put("time", line.substring(6));
            if (line.startsWith("teacher:"))
                temp.put("teacher", line.substring(8));
        }
        return temp;
    }

    private static List<String> getStudentTodos(List<String> names) {
        List<String> result = new ArrayList<>();
        for (String id : names){
            try (var lines = Files.lines(Path.of(STR."\{STUDENT_DIR}\\\{id}.txt"))){
                lines.filter(a -> a.startsWith("TODO:"))
                        .map(b -> b.substring(5))
                        .map(c -> c.concat(STR."?\{id}"))
                        .forEach(result::add);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    private static List<String> getAssLines(List<String> names) {
        List<String> result = new ArrayList<>();
        for (String id : names){
            try (var lines = Files.lines(Path.of(STR."\{COURSE_DIR}\\\{id}.txt"))){
                lines.filter(a -> a.startsWith("ASS:"))
                        .map(b -> b.substring(4))
                        .forEach(result::add);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    private static List<String> getCoursesIds() {
        List<String> result = null;
        try (var lines = Files.lines(ALL_OBJECTS.toPath())){
            result = lines
                    .filter(a -> a.startsWith("COURSE:"))
                    .map(b -> b.substring(7))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println("error in getting teacher names in SQLTest");
        }
        return result;
    }

    private static List<List<String>> getStudentsNamesAndPass(List<String> ids) {
        List<List<String>> result = new ArrayList<>();
        for (String id : ids){
            List<String> lines = new ArrayList<>();
            try {
                lines = Files.readAllLines(Paths.get(STR."\{STUDENT_DIR}\\\{id}.txt"));
            } catch (IOException e) {
                System.err.println("error in reading file in SQLTest");
            }
            List<String> temp = new ArrayList<>();
            for (String line : lines) {
                if (line.startsWith("name:"))
                    temp.add(1, line.substring(5));
                if (line.startsWith("password:"))
                    temp.add(0, line.substring(9));
            }
            result.add(temp);
        }
        return result.reversed();
    }

    private static List<String> getStudentsIds() {
        List<String> result = null;
        try (var lines = Files.lines(ALL_USERS.toPath())){
            result = lines
                    .filter(a -> a.startsWith("4"))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println("error in getting teacher names in SQLTest");
        }
        return result;
    }

    public static List<String> getTeachersNames (){
        List<String> result = null;
        try (var lines = Files.lines(ALL_USERS.toPath())){
            result = lines
                    .filter(a -> !a.startsWith("4"))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println("error in getting teacher names in SQLTest");
        }
        return result;
    }

    public static List<String> getTeachersPasswords(List<String> names){
        List<String> result = new ArrayList<>();
        for (String name : names){
            try {
                String pass = Files.readAllLines(Path.of(STR."\{TEACHER_DIR}\\\{name}.txt"))
                        .getFirst()
                        .split("\\?")[2];
                result.add(pass);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }
}
