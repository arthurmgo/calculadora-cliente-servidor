package logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

    private String fileName;
    DateFormat dateFormat;
    Date date;
    public static String SEPARATOR = File.separator;

    public Logger(String fileName) {

        this.fileName = fileName;
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        this.date = new Date();

        File directory = new File("log");

        if (!directory.exists()) {
            directory.mkdir();
        }


    }

    public void writeLog(String s) {


        try {
            FileWriter fw = new FileWriter("log" + SEPARATOR + this.fileName, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(dateFormat.format(date) + "  |  " + s);
            bw.newLine();
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
