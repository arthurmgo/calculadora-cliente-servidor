package servidorPrincipal;


import java.util.LinkedHashMap;
import java.util.Map;


public class Cache extends LinkedHashMap<Expressao, Integer> {

    private int maxSize;

    public Cache(int capacity) {
        super(capacity, 0.75f, true);
        this.maxSize = capacity;
    }


    @Override
    protected boolean removeEldestEntry(Map.Entry<Expressao, Integer> eldest) {
        return this.size() > maxSize;
    }


}