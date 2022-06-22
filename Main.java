import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Frontend f = new Frontend();
        f.frontend();
        
        DavisPutnam d = new DavisPutnam();
        d.davisPutnam();
        
        Backend b = new Backend();
        b.backend();
    }
}
