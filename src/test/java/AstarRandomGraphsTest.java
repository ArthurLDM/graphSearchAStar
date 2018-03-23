import org.junit.Test;

import java.util.List;

public class AstarRandomGraphsTest
{
    private List<NodeAStar> path;

    @Test
    //creates graph with numNode randomly generated nodes
    //it includes the node start (always in [0,0])
    // and the node goal (always in [numNode,numNode])
    // there always is path between the start node and the goal
    public void randomGraph() throws Exception
    {
        //////////////////////////////////////////////////////////////////////////
        // This is the value you want to change for tests with different graphs//
        ////////////////////////////////////////////////////////////////////////
        int numNodes =10;


        List<NodeAStar> graph = new RandomGraphCreator().create(numNodes);
        try
        {
            //you can also change the timeout value as you which
            path = AStarAlgorithm.findPath(graph, 10000);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        System.out.println(path);

    }




}