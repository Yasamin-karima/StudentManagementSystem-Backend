/*
package ObjectsController;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AddTerm {
    private static final File ALL_OBJECTS = new File("src/main/java/dataBase/allObjects");
    public static void doAddTerm(String name, Long id, Boolean availability){
        String termData = createTermData(name, id, availability);
        writeToFile(ALL_OBJECTS, termData);
    }
    private static String createTermData(String name, Long id, boolean a){
        return "TERM:" +
                name + "?" +
                id + "?" +
                a + "?";
    }
    private static void writeToFile(File file, String data){
        try(FileWriter writer = new FileWriter(file, true)){
            writer.write(data);
            writer.write(System.lineSeparator());
        }catch (IOException e){
            System.err.println("Error writing to file: " + e.getMessage());
            //todo fix it
        }
    }

    */
/*public static void main(String[] args) {
        String name1 = "4022", name2 = "4031";
        Long id1 = 78L, id2 = 74L;
        doAddTerm(name1, id1, true);
        doAddTerm(name2, id2, false);
    }*//*

}
*/
