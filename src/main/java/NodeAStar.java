import java.util.ArrayList;
import java.util.List;

public class NodeAStar
{
    private String name;
    private List<NodeAStar> neighbors;

    //Position
    private double x;
    private double y;

    private double fScore;
    private double gScore;

    private NodeAStar parent;


    public NodeAStar(String name, double x, double y, double fScore, double gScore)
    {
        this.name = name;
        this.neighbors = new ArrayList<>();
        this.x = x;
        this.y = y;
        this.fScore = fScore;
        this.gScore = gScore;

    }

    public NodeAStar(String name, double x, double y)
    {
        this.name = name;
        this.neighbors = new ArrayList<>();
        this.x = x;
        this.y = y;

        this.fScore = Double.MAX_VALUE;
        this.gScore = Double.MAX_VALUE;

    }

    public List<NodeAStar> getNeighbors()
    {
        return neighbors;
    }


    public double getfScore()
    {
        return fScore;
    }

    public void computefScore(double gScrore, double hScore)
    {
        this.fScore = gScore + hScore;
    }


    public double getgScore()
    {
        return gScore;
    }

    public void setgScore(double gScore)
    {
        this.gScore = gScore;
    }

    public NodeAStar getParent()
    {
        return parent;
    }

    public void setParent(NodeAStar parent)
    {
        if(parent == null)
            throw new IllegalArgumentException("Parent node cannot be null");
        this.parent = parent;
    }


    public void addNeighbor(NodeAStar neighbor){
        if (neighbor != null && !this.neighbors.contains(neighbor) && !neighbor.equals(this))
            this.neighbors.add(neighbor);
    }

    public void removeNeighborNode(NodeAStar node){
        this.neighbors.remove(node);
    }



    //Methods

    //Euclidean distance between two points
    public double distance(NodeAStar node){

        double dx = node.x - this.x;
        double dy = node.y - this.y;

        return Math.sqrt((dy*dy) +(dx*dx));
    }


    //Prints all properties of the node and the neighbor's node
    public String toString()
    {
        String out = "";
        if (this.name.equals(""))
            out+="Name : None";
        else
            out+="Name :" + this.name;

        StringBuilder neighborsName = new StringBuilder(" (");
        int i = 0;
        for (NodeAStar n: this.neighbors)
        {
            if (i < neighbors.size() - 1)
                neighborsName.append(n.name).append(",");


            else
                neighborsName.append(n.name);
            i++;
        }
        //out+=", Neighbors :" + neighborsName + ")";

        //out+= ", Pos :" + " (" + x + ";" + y + ")";
        return out;
    }

    public String getName()
    {
        return name;
    }

    public double getX()
    {
        return x;
    }

    public double getY()
    {
        return y;
    }
}

