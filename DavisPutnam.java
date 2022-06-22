import java.io.*;
import java.util.*;

public class DavisPutnam {
    ArrayList<String> last = new ArrayList<String>();
    ArrayList<Integer> values = new ArrayList<Integer>();
    ArrayList<ArrayList<Literal>> clauses = new ArrayList<ArrayList<Literal>>();
    
    public void davisPutnam() throws FileNotFoundException {
        
        //read file
        try {
            File file = new File("tmp.txt");
            Scanner sc = new Scanner(file);
            String tmp = null;
            l1:
            while (sc.hasNextLine()) {
                tmp = sc.nextLine();
                String[] s = tmp.split("\\s");
                if (Integer.parseInt(s[0]) == 0) {
                    while (sc.hasNextLine())
                        last.add(sc.nextLine());
                    break l1;
                }
                ArrayList<Literal> row = new ArrayList<Literal>();
                for (int i = 0; i < s.length; i++) {
                    row.add(new Literal(Integer.parseInt(s[i])));
                    values.add(Math.abs(Integer.parseInt(s[i])));
                }
                clauses.add(row);
            }
        
            Collections.sort(values);
            
            int i = 1;
            int j = values.get(0);
            while (i < values.size()) {
                while ((values.get(i) == j)) {
                    values.remove(i);
                    if (i == values.size())
                        break;
                }
                if (i < values.size())
                    j = values.get(i);
                i++;
            }
        }
        catch(FileNotFoundException e) {
            System.out.println("No file found");
        }
        
        //run the algorithm
        Boolean[] answer = new Boolean[values.size()];
        answer = algorithm(clauses, answer, values);
        
        //output
        try {
            File file = new File("tmp2.txt");
            FileWriter a = new FileWriter(file.getAbsoluteFile());
            BufferedWriter b = new BufferedWriter(a);
            for (int i = 0; i < answer.length; i++) {
                if(answer[i] == null) {
                    b.write("NO SOLUTION");
                    b.newLine();
                    break;
                }
                b.write((i + 1) + " ");
                if (answer[i])
                    b.write("T");
                else
                    b.write("F");
                b.newLine();
            }
            b.write("0");
            b.newLine();
            for (int i = 0; i < last.size(); i++) {
                b.write(last.get(i));
                b.newLine();
            }
            b.close();
        }
        catch(Exception e) {
            System.out.println("Error");
        }
    }
    
    Boolean[] algorithm(ArrayList<ArrayList<Literal>> clauses, Boolean[] answer, ArrayList<Integer> values) {
        
        //copy
        int k = 1;
        Boolean[] tmp = new Boolean[answer.length];
        System.arraycopy(answer, 0, tmp, 0, answer.length);
        ArrayList<ArrayList<Literal>> tmplist = new ArrayList<ArrayList<Literal>>();
        for (int i = 0; i < clauses.size(); i++) {
            tmplist.add(new ArrayList<Literal>());
            for (int j = 0; j < clauses.get(i).size(); j++) {
                Literal l = new Literal(clauses.get(i).get(j));
                tmplist.get(i).add(l);
            }
        }
        
        while (k > 0) {
            k = 0;
            if (tmplist.size() == 0) {
                for (int i = 0; i < tmp.length; i++) {
                    if(tmp[i] == null)
                        tmp[i] = true;
                }
                return tmp;
            }
            
            //empty clause
            for (int i = 0; i < tmplist.size(); i++) {
                if(tmplist.get(i).size() == 0)
                    return new Boolean[1];
            }
            
            //pure literal
            boolean p;
            Boolean negative;
            l1:
            for (int i = 0; i < values.size(); i++) {
                p = true;
                negative = null;
                int val = values.get(i);
                
                for (int j = 0; j < tmplist.size(); j++) {
                    for (int q = 0; q < tmplist.get(j).size(); q++) {
                        if (tmplist.get(j).get(q).value == val) {
                            negative = tmplist.get(j).get(q).negative;
                            break;
                        }
                    }
                }
                
                if (negative != null) {
                    l2:
                    for (int j = 0; j < tmplist.size(); j++) {
                        for (int q = 0; q < tmplist.get(j).size(); q++) {
                            if (tmplist.get(j).get(q).value == val) {
                                if (tmplist.get(j).get(q).negative != negative) {
                                    p = false;
                                    break l2;
                                }
                            }
                        }
                    }
                } else
                    p = false;
                
                if (p) {
                    Literal n = new Literal(values.get(i));
                    n.negative = negative;
                    if(!negative)
                        tmp[i] = true;
                    else
                        tmp[i] = false;
                    for(int m = 0; m < tmplist.size(); m++) {
                        l4:
                        for(int q = 0; q < tmplist.get(m).size(); q++) {
                            if(tmplist.get(m).get(q).value == n.value) {
                                tmplist.remove(m);
                                m--;
                                break l4;
                            }
                        }
                    }
                    k++;
                    break l1;
                }
            }

            //singleton clause
            l3:
            for (int i = 0; i < tmplist.size(); i++) {
                if (tmplist.get(i).size() == 1) {
                    if (tmplist.get(i).get(0).negative == false)
                        tmp[tmplist.get(i).get(0).value - 1] = true;
                    else
                        tmp[tmplist.get(i).get(0).value - 1] = false;
                    Literal n = new Literal(tmplist.get(i).get(0));
                    n.negative = !tmp[tmplist.get(i).get(0).value - 1];
                    for(int s = 0; s < tmplist.size(); s++) {
                        l5:
                        for(int j = 0; j < tmplist.get(s).size(); j++) {
                            if(tmplist.get(s).get(j).value == n.value) {
                                if(tmplist.get(s).get(j).negative == true) {
                                    if(n.negative == true) {
                                        tmplist.remove(s);
                                        s--;
                                        break l5;
                                    } else {
                                        tmplist.get(s).remove(j);
                                        j--;
                                    }
                                } else if(tmplist.get(s).get(j).negative == false) {
                                    if(n.negative == false) {
                                        tmplist.remove(s);
                                        s--;
                                        break l5;
                                    } else {
                                        tmplist.get(s).remove(j);
                                        j--;
                                    }
                                }
                            }
                        }
                    }
                    k++;
                    break l3;
                }
            }
        }
        
        //give it a start
        int m = 0;
        for(m = 0; m < tmplist.size(); m++) {
            if(tmplist.get(m).size() > 0)
                break;
        }
        
        Literal n = new Literal(tmplist.get(m).get(0));
        n.negative = false;
        tmp[n.value - 1] = true;
        ArrayList<Literal> l = new ArrayList<Literal>();
        l.add(n);
        tmplist.add(l);
        Boolean[] tmpB = algorithm(tmplist, tmp, values);
        if(tmpB[0] != null)
            return tmpB;
        tmplist.remove(tmplist.size() - 1);
        l.remove(l.size() - 1);
        n.negative = true;
        tmp[n.value - 1] = false;
        l.add(n);
        tmplist.add(l);
        return algorithm(tmplist, tmp, values);
    }
    
}
