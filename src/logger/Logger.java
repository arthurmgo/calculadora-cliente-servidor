package logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Logger {

    private String fileName;


    public Logger(String fileName) {
        this.fileName = fileName;
    }

    public void writeLog(String s) {

        File directory = new File("LOG");

        if (!directory.exists())
            directory.mkdir();


        try {
            FileWriter fw = new FileWriter("LOG\\" + this.fileName, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(s);
            bw.newLine();
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
