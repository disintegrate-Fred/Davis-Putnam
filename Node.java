import java.util.*;

public class Node {
    private String name;
    private ArrayList<Treasure> treasure;
    private ArrayList<Node> neighbor;

    public Node(String name) {
        this.name = name;
        this.treasure = new ArrayList<Treasure>();
        this.neighbor = new ArrayList<Node>();
    }
        
    public Node(String name, ArrayList<Treasure> treasure, ArrayList<Node> neighbor) {
        this.name = name;
        this.treasure = treasure;
        this.neighbor = neighbor;
    }

    public String getName() {
        return name;
    }
    
    public void addNeighbor(Node node) {
        this.neighbor.add(node);
    }
    
    public List<Node> getNeighbor() {
        return neighbor;
    }
    
    public List<Treasure> getTreasure() {
        return treasure;
    }
}


