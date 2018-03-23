import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class AStarTestSimpleGraph
{

    private NodeAStar start;
    private NodeAStar goal;
    private NodeAStar a;
    private NodeAStar b;

    private List<NodeAStar> path;
    @Before
    //graph for AStar1, very simple graph, it look like:
    //
    //              Start
    //                /\
    //               /  \
    //              /    B
    //             A     |
    //              \__Goal

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
            path = AStarAlgorithm.findPath(graph, 10000);
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
        System.out.println("Expected path : Start - b - Goal" );
        System.out.println("Path found : "+ path);

        Assert.assertThat(path, CoreMatchers.is(expectedPath));
    }

}