package ServidorPrincipal;

import java.util.HashMap;

public class Cache {

    private int maxsize;
    private HashMap<Expressao, Integer> cache;


    public Cache(int maxsize) {
        this.maxsize = maxsize;
        this.cache = new HashMap<>();
    }

    public void add(Expressao e, Integer value){

    }
}
