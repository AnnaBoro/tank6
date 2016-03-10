package tankproject;


import java.io.*;

public class SaveGame {

    private static File file;
    private static String str = "";

    public SaveGame() {

        file = new File("savegame.txt");
    }

    public static void saveAction(String data) {

        try (
             FileWriter fileWriter = new FileWriter(file, true);
             BufferedWriter writer = new BufferedWriter(fileWriter))
        {
            writer.write(data + "\n");
            writer.flush();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public static String getSaveGame() {
        str = "";
        try (FileReader fileReader = new FileReader(file);
             BufferedReader reader = new BufferedReader(fileReader))
        {
            String sCurrentLine;

            while ((sCurrentLine = reader.readLine()) != null) {
                str = str + sCurrentLine + "/";
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }
}
