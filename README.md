# graphSearchAStar


This a very simple and NOT OPTIMAL little program to search through a graph of star using an A* algorithm.
It was developped during my internship at IHMC, Pensacola, Florida, USA as part of my Java learning period.
 
Please refer to the tests to get any information about how to use the algorithms.
 
 
This program uses AStarNodes which are 2D points which have a name and a position (x,y).
In this program, the weights of the graph's edges is the euclidian distance between two nodes. 

Here is a simple exemple on how to do a graph seach with a hand made graph.
 
Simple Test :
Here is a visual version of the grpah (without the weigths):

              Start
                /\
               /  \
              /    B
             A     |
               \_Goal
                
               
               
Here is the graph building : 
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
        
Here the graph is created and to look for the path you can just get :
      private List<NodeAStar> path = AStarAlgorithm.findPath(graph, 10000);
Wich gives you the shortest path : Start - b - Goal.
  
 This program also includes a random graph generator which you can call using :
 List<NodeAStar> graph = new RandomGraphCreator().create(numNodes);
(This algorithms is NOT OPTIMAL once again), please do not expect high speed calculations.
  
  
  Thank you for reading this till the end =).
Feel free to use and abuse this code. And make any improvements you wish to it. 
