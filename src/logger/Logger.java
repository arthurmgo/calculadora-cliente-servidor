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


    /**
     * Construtor, recebe como parametro o nome do arquivo e gera a pasta que o irá armazenar
     * @param fileName
     */
    public Logger(String fileName) {

        this.fileName = fileName;
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        this.date = new Date();

        File directory = new File("log");

        // Cria uma pasta se ela ainda não existe
        if (!directory.exists()) {
            directory.mkdir();
        }


    }

    /**
     * Metodo que escreve uma mensagem em um arquivo. A hora da mensagem também é escrita
     * @param s mensagem que será escrita
     */
    public void writeLog(String s) {

        try {

            FileWriter fw = new FileWriter("log" + SEPARATOR + this.fileName, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(dateFormat.format(date) + "  |  " + s);
            bw.newLine();
            bw.flush();
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
