import java.util.*;

public class Atom {
    int time;
    int number;
    boolean negative;
}

class At extends Atom {
    String node;
    
    public At(String node, int time) {
        this.negative = false;
        this.number = 0;
        this.node = node;
        this.time = time;
    }
    
    @Override
    public String toString() {
        String s = "At" + "(" + this.node + "," + this.time + ")";
        return s;
    }
}

class Has extends Atom {
    String treasure;
    
    public Has(String treasure, int time) {
        this.negative = false;
        this.number = 0;
        this.treasure = treasure;
        this.time = time;
    }
    
    @Override
    public String toString() {
        String s = "Has" + "(" + this.treasure + "," + this.time + ")";
        return s;
    }
}

