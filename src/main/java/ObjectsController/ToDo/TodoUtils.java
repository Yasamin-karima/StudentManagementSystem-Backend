package ObjectsController.ToDo;

import classes.Student;
import classes.Todo;
import dataBase.CRUD;

import java.io.File;
public class TodoUtils {

    private static final File STUDENT_DIR = new File("C:\\Users\\Yasamin\\OneDrive\\universe\\term2\\AP\\project\\project_repsitoryPattern\\src\\main\\java\\dataBase\\Students");


    //#CREATE# method
    public static void createTodo (Long studentId, String title){
        Student st = new Student(studentId);
        //creating to do
        st.createTodo(title);
    }

    //#DONE# & #UNDONE# method
    public static String todoDone(Long studentId, String title){
        Student st = new Student(studentId);
        if (!st.doesTodoExists(title)) return  "409";
        else st.setTodoDone(title);
        return  "201";
    }
    public static String todoUndone(Long aLong, String s) {
        Student st = new Student(aLong);
        if (!st.doesTodoExists(s)) return  "409";
        else st.setTodoUndone(s);
        return  "201";
    }
    /*public static void todoUnDone(Long studentId, String title){
        Path studentFilePath = Paths.get(STR."\{STUDENT_DIR}\\\{studentId}.txt");
        try {
            Files.lines(studentFilePath)
                    .filter(a -> a.startsWith(STR."TODO:\{title}"))
                    .forEach(b -> CRUD.update("true", "false", studentFilePath.toString()));
        } catch (IOException e){
            e.printStackTrace();
        }
    }*/

    //#REMOVE# method
    public static void removeTodo(Long studentId, String title){
        Student st = new Student(studentId);
        st.removeTodo(title);
    }


}
