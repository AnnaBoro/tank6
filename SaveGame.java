package tankproject;


import java.io.*;

public class SaveGame {

    private static File file;

    public SaveGame() {

        file = new File("savegame.txt");
    }

    public static void saveAction(String data) {

        try (
             FileWriter fileWriter = new FileWriter(file, true);
             BufferedWriter writer = new BufferedWriter(fileWriter);
        )
        {
            writer.write(data + "\n");
            writer.flush();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
//    public static void main(String[] args) {
//        SaveGame saveGame = new SaveGame();
//        SaveGame.saveAction("ggg");
//        SaveGame.saveAction("yyy");
//        SaveGame.saveAction("777");
//    }
}
