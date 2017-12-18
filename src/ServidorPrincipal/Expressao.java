package ServidorPrincipal;

public class Expressao{

    private Integer x;
    private Integer y;
    private String op;

    public Expressao(int x, int y, String op) {
        this.x = x;
        this.y = y;
        this.op = op;
    }

    public Expressao(String st){

        String s = st.replaceAll("\\s", "");


        if(s.contains("+")){
            this.x  = Integer.parseInt(s.substring(0, s.lastIndexOf("+")));
            this.y  = Integer.parseInt(s.substring(s.lastIndexOf("+") + 1));
            this.op = "+";

        }else if(s.contains("-")){
            this.x  = Integer.parseInt(s.substring(0, s.lastIndexOf("-")));
            this.y  = Integer.parseInt(s.substring(s.lastIndexOf("-") + 1));
            this.op = "-";

        }else if(s.contains("*")){
            this.x  = Integer.parseInt(s.substring(0, s.lastIndexOf("*")));
            this.y  = Integer.parseInt(s.substring(s.lastIndexOf("*") + 1));
            this.op = "*";

        }else if(s.contains("/")){
            this.x  = Integer.parseInt(s.substring(0, s.lastIndexOf("/")));
            this.y  = Integer.parseInt(s.substring(s.lastIndexOf("/") + 1));
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


        if((expr == null) || (getClass() != expr.getClass())){
            System.out.printf("AQUI");
            return false;

        }
        else{
            Expressao exprNova = (Expressao) expr;
            return this.op.equals(exprNova.getOp()) && this.x == exprNova.getX() && this.y == exprNova.getY();
        }

    }

    @Override
    public int hashCode() {
        return x.hashCode() + y.hashCode() + op.hashCode();
    }
}
