package ServidorPrincipal;


import java.util.LinkedHashMap;
import java.util.Map;


public class Cache extends LinkedHashMap<Expressao, Integer> {

    private int maxSize;

    public Cache(int capacity) {
        super(capacity, 0.75f, true);
        this.maxSize = capacity;
    }

//    //return -1 if miss
//    public int get(Expressao key) {
//        Integer v = super.get(key);
//        return v == null ? -1 : v;
//    }

//    public void put(Expressao key, int value) {
//        super.put(key, value);
//    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<Expressao, Integer> eldest) {
        return this.size() > maxSize; //must override it if used in a fixed cache
    }


}