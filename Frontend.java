import java.util.*;
import java.lang.*;
import java.io.*;

public class Frontend {
    Node[] nodes;
    String[] nodeName;
    String[] treasureName;
    int step = 0;
    
    //all atoms
    ArrayList<Atom> atom = new ArrayList<Atom>();
    
    //clause list and integer form
    ArrayList<ArrayList<Atom>> clauseList = new ArrayList<ArrayList<Atom>>();
    ArrayList<ArrayList<Integer>> intForm = new ArrayList<ArrayList<Integer>>();
    
    public void frontend() throws FileNotFoundException {
        List<Node> output = new ArrayList<>();
        try {
            //get file
            Scanner s1 = new Scanner(System.in);
            System.out.println("Enter the input file name: ");
            String str = s1.next();
            Scanner sc = new Scanner(new File(str));
            
            //get node
            nodeName = sc.nextLine().split("\\s");
            nodes = new Node[nodeName.length];
            for(int i = 0; i < nodeName.length; i++)
                    nodes[i] = new Node(nodeName[i]);
            
            //get treasure
            treasureName = sc.nextLine().split("\\s");
            
            //get steps
            step = Integer.parseInt(sc.nextLine());
            
            int p = 0;
            String tmp = null;
            
            //get maze
            while (sc.hasNextLine()) {
                tmp = sc.nextLine();
                String[] s = tmp.split("\\s");
                int q = 2;
                while(!s[q].equals("NEXT")) {
                    Treasure treasure = new Treasure(s[q]);
                    nodes[p].getTreasure().add(treasure);
                    q++;
                }
                q++;
                while(q < s.length) {
                    Node t = null;
                    for(int j = 0; j < nodes.length; j++) {
                        if((nodes[j].getName()).equals(s[q])) {
                            t = nodes[j];
                            break;
                        }
                    }
                    nodes[p].addNeighbor(t);
                    q++;
                }
                p++;
            }
            
        
        }
        catch(FileNotFoundException e) {
            System.out.println("No file found");
        }
        
        //category 1
        for(int t = 0; t <= step; t++) {
            for(int i = 0; i < nodes.length; i++) {
                At a = new At(nodeName[i], t);
                a.negative = true;
                atom.add(a);
                for(int j = i + 1; j < nodes.length; j++) {
                    At b = new At(nodeName[j], t);
                    b.negative = true;
                    clauseList.add(new ArrayList<Atom>());
                    clauseList.get(clauseList.size() - 1).add(a);
                    clauseList.get(clauseList.size() - 1).add(b);
                }
            }
        }
        
        //category 2
        for(int t = 0; t < step; t++) {
            for(int i = 0; i < nodes.length; i++) {
                At a = new At(nodeName[i], t);
                a.negative = true;
                atom.add(a);
                clauseList.add(new ArrayList<Atom>());
                clauseList.get(clauseList.size() - 1).add(a);
                for(int j = 0; j < nodes[i].getNeighbor().size(); j++) {
                    At b = new At(nodes[i].getNeighbor().get(j).getName(), t + 1);
                    atom.add(b);
                    clauseList.get(clauseList.size() - 1).add(b);
                }
            }
        }
        
        //category 3
        for(int t = 0; t <= step; t++) {
            for(int i = 0; i < nodes.length; i++) {
                for(int j = 0; j < nodes[i].getTreasure().size(); j++) {
                    At a = new At(nodeName[i], t);
                    a.negative = true;
                    atom.add(a);
                    Has b = new Has(nodes[i].getTreasure().get(j).name, t);
                    atom.add(b);
                    clauseList.add(new ArrayList<Atom>());
                    clauseList.get(clauseList.size() - 1).add(a);
                    clauseList.get(clauseList.size() - 1).add(b);
                }
            }
        }
        
        //category 4
        for(int t = 0; t < step; t++) {
            for(int i = 0; i < nodes.length; i++) {
                for(int j = 0; j < nodes[i].getTreasure().size(); j++) {
                    Has a = new Has(nodes[i].getTreasure().get(j).name, t);
                    a.negative = true;
                    atom.add(a);
                    Has b = new Has(nodes[i].getTreasure().get(j).name, t + 1);
                    atom.add(b);
                    clauseList.add(new ArrayList<Atom>());
                    clauseList.get(clauseList.size() - 1).add(a);
                    clauseList.get(clauseList.size() - 1).add(b);
                }
            }
        }
        
        //category 5
        for(int t = 0; t < step; t++) {
            for(int i = 0; i < treasureName.length; i++) {
                Has a = new Has(treasureName[i], t);
                atom.add(a);
                Has b = new Has(treasureName[i], t + 1);
                b.negative = true;
                atom.add(b);
                clauseList.add(new ArrayList<Atom>());
                clauseList.get(clauseList.size() - 1).add(a);
                clauseList.get(clauseList.size() - 1).add(b);
                for(int j = 0; j < nodes.length; j++) {
                    for(int k = 0; k < nodes[j].getTreasure().size(); k++) {
                        if (nodes[j].getTreasure().get(k).name.equals(treasureName[i])) {
                            At c = new At(nodeName[j], t + 1);
                            atom.add(c);
                            clauseList.get(clauseList.size() - 1).add(c);
                        }
                    }
                }
            }
        }
        
        //category 6
        At l = new At("START", 0);
        atom.add(l);
        clauseList.add(new ArrayList<Atom>());
        clauseList.get(clauseList.size() - 1).add(l);
        
        //category 7
        for(int i = 0; i < treasureName.length; i++) {
            Has a = new Has(treasureName[i], 0);
            a.negative = true;
            atom.add(a);
            clauseList.add(new ArrayList<Atom>());
            clauseList.get(clauseList.size() - 1).add(a);
        }
        
        //category 8
        for(int i = 0; i < treasureName.length; i++) {
            Has a = new Has(treasureName[i], step);
            atom.add(a);
            clauseList.add(new ArrayList<Atom>());
            clauseList.get(clauseList.size() - 1).add(a);
        }
        
        //assign the clause number
        int q = 1, t = 1;
        for(int i = 0; i < atom.size(); i++) {
            for(int j = 0; j < clauseList.size(); j++) {
                ArrayList<Atom> tmp = clauseList.get(j);
                for(int k = 0; k < tmp.size(); k++) {
                    if(tmp.get(k).toString().equals(atom.get(i).toString()) && tmp.get(k).number == 0) {
                            tmp.get(k).number = q;
                            t++;
                    }
                }
            }
            if (q != t)
                q++;
            t = q;
        }
        
        //store clause with number
        for(int i = 0; i < clauseList.size(); i++) {
            intForm.add(new ArrayList<Integer>());
            ArrayList<Atom> tmp = clauseList.get(i);
            for(int j = 0; j < tmp.size(); j++) {
                int number = 0 - tmp.get(j).number;
                if(tmp.get(j).negative == false)
                    number = Math.abs(number);
                intForm.get(i).add(number);
            }
        }
        
        //output to the txt file
        try {
            File file = new File("tmp.txt");
            FileWriter a = new FileWriter(file.getAbsoluteFile());
            BufferedWriter b = new BufferedWriter(a);
            int s = 0;
            for(int i = 0; i < intForm.size(); i++) {
                for(int j = 0; j < intForm.get(i).size(); j++) {
                    b.write(Integer.toString(intForm.get(i).get(j)));
                    if(Math.abs(intForm.get(i).get(j)) > s)
                        s = Math.abs(intForm.get(i).get(j));
                    if(j < intForm.get(i).size() - 1)
                        b.write(" ");
                }
                b.newLine();
            }
            
            b.write("0");
            b.newLine();
            
            for(int i = 1; i <= s; i++) {
                b.write(i + " ");
                l1:
                for(int j = 0; j < intForm.size(); j++) {
                    for(int k = 0; k < intForm.get(j).size(); k++) {
                        if(Math.abs(intForm.get(j).get(k)) == i) {
                            b.write(clauseList.get(j).get(k).toString());
                            break l1;
                        }
                    }
                }
                b.newLine();
            }
            b.close();
        }
        catch(Exception e) {
            System.out.println("Error");
        }
    }
}
