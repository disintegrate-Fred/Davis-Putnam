import java.io.*;
import java.util.*;

public class Backend {
    
    public void backend() throws FileNotFoundException {
        ArrayList<String[]> answer = new ArrayList<String[]>();
        ArrayList<String[]> value = new ArrayList<String[]>();
        ArrayList<String> trues = new ArrayList<String>();
        
        File file = new File("tmp2.txt");
        Scanner sc = new Scanner(file);
        String tmp = null;
        
        //read the file
        while (sc.hasNextLine()) {
            while (!(tmp = sc.nextLine()).equals("0")) {
                String[] s = tmp.split("\\s");
                answer.add(s);
            }
            while (sc.hasNextLine()) {
                tmp = sc.nextLine();
                String[] s = tmp.split("\\s");
                value.add(s);
            }
        }
        
        //no solution
        if (answer.size() == 1)
            System.out.print(answer.get(0)[0] + " " + answer.get(0)[1]);
        
        //get the true clauses
        for(int i = 0; i < answer.size(); i++) {
            if (answer.get(i)[1].equals("T"))
                trues.add(value.get(Integer.parseInt(answer.get(i)[0]) - 1)[1]);
        }
        
        //output
        for(int i = 0; i < trues.size(); i++) {
            int k = 3;
            if (trues.get(i).charAt(2) == '(') {
                while (trues.get(i).charAt(k) != ',') {
                    System.out.print(trues.get(i).charAt(k));
                    k++;
                }
            } else {
                break;
            }
            System.out.print(" ");
        }
        
        System.out.println();
    }
}
