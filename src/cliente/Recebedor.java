package cliente;

import java.io.InputStream;
import java.util.Scanner;
import logger.Logger;


public class Recebedor extends Thread{

    private InputStream servidor;

    private Logger logger = new Logger("ClienteLOG.txt");

    public Recebedor(InputStream servidor) {
        this.servidor = servidor;
    }

    public void run() {

        Scanner s = new Scanner(this.servidor);
        while (s.hasNextLine()) {
            String msg = s.nextLine();
            System.out.println(msg);
            logger.writeLog("[INFO] O cliente recebeu a mensagem: " + msg);
        }
    }
}