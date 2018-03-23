import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AStarAlgorithm
{


    //Important Notice :
    //The graph must contain ONE start node and ONE goal node. (Nodes that have start and goal as their name, no matter the case)
    //Otherwise the algorithms won't work.
    public static List<NodeAStar> findPath(List<NodeAStar> graph, int timeoutMs) throws Exception
    {
        NodeAStar start=null;
        NodeAStar goal=null;

        for (NodeAStar node : graph)
        {
            if (node.getName().toLowerCase().equals("start")) //making sure there is no case mistake
            {
                if (start == null)
                    start = node;
                else
                    throw new IllegalArgumentException("The graph contains two nodes called start. Please restart your graph.");
            }
            else if (node.getName().toLowerCase().equals("goal"))
            {
                if (goal == null)
                    goal = node;
                else
                    throw new IllegalArgumentException("The graph contains two node called goal. Please restart your graph.");
            }
        }

        // check node not null
        if(start == null || goal == null)
            throw new IllegalArgumentException("Start or Goal nodes cannot be null.");


        //Contains the path that will be returned
        List<NodeAStar> path = new ArrayList<>();


        //Evaluated nodes
        List<NodeAStar> closed = new ArrayList<>();

        //Nodes to evaluate (initialized with start)
        List<NodeAStar> open = new ArrayList<>();

        start.setgScore(0);
        start.computefScore(start.getgScore(), start.distance(goal));

        open.add(start);


        //used to timeout in case no solution is found yet
        long endTimeMillis = System.currentTimeMillis() + timeoutMs;

        while (open.size() != 0)
        {

            if (System.currentTimeMillis() >= endTimeMillis)
                throw new Exception("Timeout exception: The search timed out after "+ timeoutMs+" ms");

            //current will contain the node we are checking
            NodeAStar current = open.get(0);
            double min = current.getfScore();
            // Find min fCost of open nodes
            for (NodeAStar node : open)
            {
                if (min > node.getfScore())
                {
                    min = node.getfScore();
                    current = node;
                }
            }


            //If the current node is goal then we found the shortest path
            if (current == goal)
            {
                path = constructPath(current);
                return path;
            }

            //Else we check the neighbors
            else
            {
                //once checked the node goes from the open list to the closed list
                open.remove(current);
                closed.add(current);

                //Go through the neighbors of current node
                for (NodeAStar neighbor : current.getNeighbors())
                {

                    //If we have already gone through neighbor, then skip it
                    if (!closed.contains(neighbor))
                    {
                        //If neighborAStar is not in open, add it
                        if (!open.contains(neighbor))
                            open.add(neighbor);


                        // Get gScore and create neighborAStar's
                        double currentGScore = current.getgScore();

                        double neighborGScore = currentGScore + current.distance(neighbor);

                        //verify that the new path is better than the previous one --> newGcost<oldGCost
                        if (neighborGScore < neighbor.getgScore())
                        {
                            neighbor.setgScore(neighborGScore);
                            neighbor.computefScore(neighborGScore, neighbor.distance(goal));
                            neighbor.setParent(current);
                        }
                    }
                }
            }
        }



        //If no path have been found print it
        if (path.size()==0)
            System.out.println("No path found");
        return path;

    }


    //reconstructs path from the given node until reaching a node with no parent
    private static List<NodeAStar> constructPath(NodeAStar node)
    {

        List<NodeAStar> path= new ArrayList<>();
        path.add(node);

        //hasParent will make sure the observed node has a parent
        boolean hasParent = (node.getParent() !=null);
        while (hasParent)
        {
            node = node.getParent();
            path.add(node);
            hasParent=(node.getParent()!=null);
        }

        //path is (Goal,...,Start) so has to be reversed
        Collections.reverse(path);

        return path;
    }


}
