import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class AStarTestNoSolution
{

    private List<NodeAStar> path;
    @Before

    //graph for AStar2, should not work (no path to goal)
    //The graph looks that:
    //
    //       Start--a--b
    //              |  |
    //              d--c      goal

    public void initGraph2() throws Exception
    {
        NodeAStar start;
        NodeAStar goal;
        NodeAStar a;
        NodeAStar b;
        NodeAStar c;
        NodeAStar d;

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
            path = AStarAlgorithm.findPath(graph, 10000);
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