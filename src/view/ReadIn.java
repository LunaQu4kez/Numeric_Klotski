package view;

import java.io.*;
import java.util.ArrayList;

public class ReadIn {
    private static int pieceSizeX;
    private static int pieceSizeY;


    public static void setPieceSizeY(int pieceSizeY) {
        ReadIn.pieceSizeY = pieceSizeY;
    }

    public static void setPieceSizeX(int pieceSizeX) {
        ReadIn.pieceSizeX = pieceSizeX;
    }

    public static int getPieceSizeX() {
        return pieceSizeX;
    }
    public static int getPieceSizeY() {
        return pieceSizeY;
    }

    public static ArrayList<String> read(String name) throws IOException {
        String fileName = "./src/util/" + name + ".txt";
        File file = new File(fileName);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;
        ArrayList<String> in = new ArrayList<>();
        while((line = br.readLine()) != null){
            String[] arr = line.split("\\s+");
            for (int i = 0; i < arr.length; i++){
                in.add(arr[i]);
            }
        }
        return in;
    }
}
