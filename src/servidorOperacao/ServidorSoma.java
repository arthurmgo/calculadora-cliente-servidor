package servidorOperacao;

import java.io.IOException;

public class ServidorSoma extends ServidorOp{

    public ServidorSoma(int porta) {
        super(porta);
    }

    public static void main(String[] args) throws IOException {
        new ServidorSoma(10001).executa();
    }

    @Override
    public int op(int a, int b) {
        System.out.println("FIZ UMA SOMA");
        return a + b;
    }
}
