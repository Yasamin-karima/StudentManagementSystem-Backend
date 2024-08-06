package dataBase;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class CRUD {
    public static final File TEACHER_DIR = new File("C:\\Users\\Yasamin\\OneDrive\\universe\\term2\\AP\\project\\project_repsitoryPattern\\src\\main\\java\\dataBase\\Teachers");
    public static final File STUDENT_DIR = new File("C:\\Users\\Yasamin\\OneDrive\\universe\\term2\\AP\\project\\project_repsitoryPattern\\src\\main\\java\\dataBase\\Students");
    public static final File COURSE_DIR = new File("C:\\Users\\Yasamin\\OneDrive\\universe\\term2\\AP\\project\\project_repsitoryPattern\\src\\main\\java\\dataBase\\Courses");
    public static final File ALL_OBJECTS = new File("C:\\Users\\Yasamin\\OneDrive\\universe\\term2\\AP\\project\\project_repsitoryPattern\\src\\main\\java\\dataBase\\allObjects.txt");


    public static void createDataOnFile(String data, String filePath){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(data);
            writer.write("\n");
            writer.flush();
        } catch (IOException e ){
            System.out.println("ERROR in CRUD creating data" + e.getMessage());
        }
    }


    public static void createFile(String path, String name, String firstData){
        try {
            Files.createFile(Paths.get(STR."\{path}\\\{name}.txt"));
            createDataOnFile(firstData, STR."\{path}\\\{name}.txt");
        } catch (IOException e) {
            System.out.println("exception in creating file    ");
            e.printStackTrace();
        }
    }

    public static String read(String path){
        try {
            return Files.readString(Paths.get(path));
        } catch(IOException e){
            System.out.println("error in reading file CRUD");
        }
        return "couldn't read from file";
    }

    public static void update(String partToUpdate, String updatedText, String filePath){
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))){

            StringBuilder result = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null){
                if (line.contains(partToUpdate)){
                    line = line.replace(partToUpdate, updatedText);
                    result.append(STR."\{line}\n");
                } else {
                    result.append(STR."\{line}\n");
                }
            }

            Files.writeString(Paths.get(filePath), "");
            Files.writeString(Paths.get(filePath), result.toString());

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    //todo : implement a lineDelete method

    // todo : deletes all the occurrences of that part in the file
    public static void deletePartOfFile (String partToDelete, String filePath) {
        update(partToDelete, "", filePath);
    }

    public static void deleteFile (String path) {
        try {
            Files.deleteIfExists(Paths.get(path));
        } catch (IOException e) {
            System.out.println("error in deleting file    " + e.getMessage());
        }
    }

    public static void deleteLine (String startsWith, Path filePath){
        try {
            var temp = Files.lines(filePath)
                    .filter(b -> !b.startsWith(startsWith))
                    .collect(Collectors.toList());
//            System.out.println(temp);
            Files.delete(filePath);
            String fileName = STR."\\\{filePath.getFileName().toString()}";
            fileName = fileName.replace(".txt", "");
            var placePath = filePath.toString().replace(STR."\{fileName}.txt", "");
            createFile(placePath, fileName,  temp.getFirst());
            temp.removeFirst();
            for (String s : temp){
                createDataOnFile(s, filePath.toString());
            }
        } catch (IOException e) {
            System.out.println("ERROR in CRUD deleting line");
            e.printStackTrace();
        }
    }

}
