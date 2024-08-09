package dataBase;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static dataBase.CRUD.TEACHER_DIR;


public class SQLTest {
    private static final File ALL_USERS = new File( "C:\\Users\\Yasamin\\OneDrive\\universe\\term2\\AP\\project\\project_repsitoryPattern\\src\\main\\java\\dataBase\\allUsers.txt");

    public static void main(String[] args) {
        String url = "jdbc:sqlite:data.db";

        var names = getTeachersNames();
        System.out.println(names);
        var passwords = getTeachersPasswords(names);
        System.out.println(passwords);

        String sql = "INSERT INTO teachers(name, password) VALUES(?, ?)";

        try (var conn = DriverManager.getConnection(url);
                var pstmt = conn.prepareStatement(sql)) {
            for (int i = 0; i < names.size(); i++) {
                pstmt.setString(1, names.get(i));
                pstmt.setString(2, passwords.get(i));
                pstmt.executeUpdate();
            }


        } catch (SQLException e) {
            System.err.println(e.getMessage());  
        }
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
