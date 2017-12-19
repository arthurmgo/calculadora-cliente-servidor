package servidorOperacao;

import java.io.IOException;

public class ServidorMultiplicacao extends ServidorOp {

    public ServidorMultiplicacao(int porta) {
        super(porta);
    }

    public static void main(String[] args) throws IOException {
        new ServidorMultiplicacao(10003).executa();
    }

    @Override
    public int op(int a, int b) {
        return a * b;
    }
}
