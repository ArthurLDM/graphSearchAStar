import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class PathFinderTest
{

    //Create nodes
    private NodeAStar start;
    private NodeAStar goal;
    private NodeAStar a;
    private NodeAStar b;
    private NodeAStar c;
    private NodeAStar d;

    private List<NodeAStar> path;

    //creates graph with numNode randomly generated nodes
    //it includes the node start (always in [0,0])
    // and the node goal (always in [numNode,numNode])
    // there always is path between the start node and the goal
    public List<NodeAStar> GraphCreator(int numNode) throws Exception
    {
        //Randomly generate numNode nodes
        List<NodeAStar> nodes = new ArrayList<>();

        //Add Start node (index 0)
        start = new NodeAStar("Start", 0,0);
        nodes.add(start);

        //index will also serve as name for the nodes
        int index = 1;
        Random rand = new Random();
        //create all nodes except Start and Goal
        while (index < numNode-1)
        {
            //initialize x and y (position) and node to generate random nodes
            double x = 0;
            double y =0;
            NodeAStar node = start;

            //While the node is already in nodes <=> already created
            while (node != null)
            {
                //generate random position
                x = ThreadLocalRandom.current().nextDouble(0, numNode);
                y = ThreadLocalRandom.current().nextDouble(0, numNode);

                //check if there is no node already at that position
                double finalX = x;
                double finalY = y;

                node = nodes.stream()
                        .filter(n -> n.getX() == finalX && n.getY() == finalY)
                        .findAny()
                        .orElse(null);
            }

            nodes.add(new NodeAStar(String.valueOf(index), x, y));
            index++;
        }


        //add goal at the end of the list (index numNode-1)
        goal = new NodeAStar("Goal", numNode,numNode);
        nodes.add(goal);



        //create a random path from goal to start
        index = numNode-1;
        NodeAStar node;
        while (index != 0)
        {
            int  n = rand.nextInt(index);

            //Add index as neighbor of node.get(n)
            node = nodes.get(n);
            node.addNeighbor(nodes.get(index));
            nodes.set(n,node);
            index=n;
        }


        //print the assured path
        path = AStarAlgorithm.findPath(nodes,start,goal, 1000);

        System.out.println("Assured Path :");
        System.out.println(path);

        //Generate other random edges of the graph
        //Generate in average of numNode/2 edge per node except goal
        //make a total of (numNode-1)*numNode/2 edges
        index = (int)Math.ceil((numNode-1)*(numNode/2));

        List<NodeAStar[]> edges = new ArrayList<>();
        while (index!=0)
        {
            boolean exists=false;
            while(!exists)
            {
                //first node (cannot be goal)
                int random = rand.nextInt(numNode-2);
                String firstNodeName;
                if (random==0)
                    firstNodeName = "Start";
                else
                    firstNodeName= String.valueOf(random);

                //second node (cannot be start)
                random = rand.nextInt(numNode-2) + 1;
                String secondNodeName;
                if (random == numNode-1)
                    secondNodeName = "Goal";
                else
                    secondNodeName = String.valueOf(random);

                //find corresponding nodes
                NodeAStar firstNode = nodes.stream()
                        .filter(n -> n.getName().equals(firstNodeName))
                        .findAny()
                        .orElse(null);

                NodeAStar secondNode = nodes.stream()
                        .filter(n -> n.getName().equals(secondNodeName))
                        .findAny()
                        .orElse(null);


                NodeAStar[] edge = new NodeAStar[]{firstNode,secondNode};
                //if edge not created, create it
                if(!edges.contains(edge))
                {
                    edges.add(edge);
                    exists=true;
                }
            }
            index--;
        }


        //transform edges to neighbors in the graph
        while(edges.size()!=0)
        {
            //node is the node the will get secondNode as neighbor
            NodeAStar firstNode = edges.get(0)[0];
            //  secondNode is added as firstNode's neighbor
            NodeAStar secondNode = edges.get(0)[1];


            //get the firstNode index from the name
            String name = firstNode.getName();
            int nodeName;
            switch (name)
            {
                case "Start":
                    nodeName = 0;
                    break;
                case "Goal":
                    nodeName = numNode - 1;
                    break;
                default:
                    nodeName = Integer.valueOf(firstNode.getName());
                    break;
            }

            //extract node from nodes (the node name is the same as it's index)
            node = nodes.get(nodeName);
            //add secondNode as neighbor
            node.addNeighbor(secondNode);
            //update node back in nodes
            nodes.set(nodeName, node);

            //remove from edge
            edges.remove(0);
        }

        return nodes;
    }

    @Test
    public void randomGraph() throws Exception
    {
        List<NodeAStar> graph = GraphCreator(100);
        try
        {
            path = AStarAlgorithm.findPath(graph,start,goal, 10000);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        System.out.println(path);

    }

    @Before
    //graph for AStar1, very simple graph
    public void initGraph1() throws Exception
    {
        start = new NodeAStar("Start", 0,0);
        goal = new NodeAStar("Goal", 2,2);
        a = new NodeAStar("A", 0,3);
        b = new NodeAStar("B", 2,0);

        //Link the nodes
        start.addNeighbor(a);
        a.addNeighbor(goal);

        start.addNeighbor(b);
        b.addNeighbor(goal);

        //Create the graph
        List<NodeAStar> graph = new ArrayList<>();
        graph.add(start);
        graph.add(a);
        graph.add(b);
        graph.add(goal);


        try
        {
            path = AStarAlgorithm.findPath(graph,start,goal, 10000);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }

    @Test
    public void aStar1() throws Exception
    {
        initGraph1();
        List<NodeAStar> expectedPath = new ArrayList<>();
        expectedPath.add(start);
        expectedPath.add(b);
        expectedPath.add(goal);
        Assert.assertThat(path, CoreMatchers.is(expectedPath));
    }


    @Before
    //graph for AStar2, should not work (no path to goal)
    public void initGraph2() throws Exception
    {
        start = new NodeAStar("Start", 0,0);
        goal = new NodeAStar("Goal", 0,1);
        a = new NodeAStar("A", 1,0);
        b = new NodeAStar("B", 2,0);
        c = new NodeAStar("D", 2,1);
        d = new NodeAStar("B", 1,1);

        //Link the nodes
        start.addNeighbor(a);
        a.addNeighbor(b);
        b.addNeighbor(c);
        c.addNeighbor(d);
        d.addNeighbor(a);

        //Create the graph
        List<NodeAStar> graph = new ArrayList<>();
        graph.add(start);
        graph.add(a);
        graph.add(b);
        graph.add(c);
        graph.add(d);
        graph.add(goal);

        try
        {
            path = AStarAlgorithm.findPath(graph,start,goal, 10000);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }

    @Test
    public void aStar2() throws Exception
    {
        initGraph2();
        List<NodeAStar> expectedPath = new ArrayList<>();

        Assert.assertThat(path, CoreMatchers.is(expectedPath));
    }

}