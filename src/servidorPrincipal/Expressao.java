package servidorPrincipal;

public class Expressao {

    private Integer x;
    private Integer y;
    private String op;

    /**
     * Construtor padrão para uma expressão a partir de uma string
     * @param st String que representa a expressão
     */
    public Expressao(String st) {

        String s = st.replaceAll("\\s", ""); // Remove expaçoes da expressão

        if (s.contains("+")) {
            this.x = Integer.parseInt(s.substring(0, s.lastIndexOf("+")));
            this.y = Integer.parseInt(s.substring(s.lastIndexOf("+") + 1));
            this.op = "+";

        } else if (s.contains("-")) {
            this.x = Integer.parseInt(s.substring(0, s.lastIndexOf("-")));
            this.y = Integer.parseInt(s.substring(s.lastIndexOf("-") + 1));
            this.op = "-";

        } else if (s.contains("*")) {
            this.x = Integer.parseInt(s.substring(0, s.lastIndexOf("*")));
            this.y = Integer.parseInt(s.substring(s.lastIndexOf("*") + 1));
            this.op = "*";

        } else if (s.contains("/")) {
            this.x = Integer.parseInt(s.substring(0, s.lastIndexOf("/")));
            this.y = Integer.parseInt(s.substring(s.lastIndexOf("/") + 1));
            this.op = "/";

        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getOp() {
        return op;
    }

    @Override
    public String toString() {
        return x.toString() + op + y.toString();
    }

    @Override
    public boolean equals(Object expr) {
        if ((expr == null) || (getClass() != expr.getClass())) {
            return false;
        } else {
            Expressao exprNova = (Expressao) expr;
            return this.op.equals(exprNova.getOp()) && this.x == exprNova.getX() && this.y == exprNova.getY();
        }
    }

    @Override
    public int hashCode() {
        return x.hashCode() + y.hashCode() + op.hashCode();
    }
}
