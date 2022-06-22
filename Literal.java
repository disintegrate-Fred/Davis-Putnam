import java.util.*;
import java.lang.*;

public class Literal {
    int value;
    boolean negative;
    
    public Literal(int value) {
        this.value = Math.abs(value);
        if(value >= 0)
            this.negative = false;
        else
            this.negative = true;
    }
    
    public Literal(Literal literal) {
        this.value = literal.value;
        this.negative = literal.negative;
    }
}
