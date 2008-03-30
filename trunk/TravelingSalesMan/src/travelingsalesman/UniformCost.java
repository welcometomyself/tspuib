/*
 * UniformCost.java
 *
 * $LastChangedDate$
 * $LastChangedRevision$
 * Vicente J. Ferrer Dalmau
 * < vicente@jdalmau.es >
 *
 * Implementation of the Uniform Cost algorithm.
 */

package travelingsalesman;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 *
 * @author Vicente J. Ferrer Dalmau
 */
public class UniformCost {
    
    RoutesMatrix distances;
    int sourceCity;
    PriorityQueue<Town> toExpand = new PriorityQueue<Town>(200, 
        new Comparator<Town>() {
          public int compare(Town a, Town b) {
            return a.g - b.g;
          }
        }
      );  
    String result = new String();
    
    ArrayList optimumRoute, followedRoute;
    int nodes = 0;
    int routeCost = 0;
    int optimumCost = Integer.MAX_VALUE;      
    
    /** Creates a new instance of UniformCost */
    public UniformCost(RoutesMatrix matrix, int sourceCity) {
        
        distances = matrix;
        this.sourceCity = sourceCity;
    }
    
    /**
     * executes the algorithm
     */
    public String execute() {
        
        // have we found the solution?
        boolean solution = false;
        
        // start the timer
        long startTime = System.currentTimeMillis();
        
        // initial town
        toExpand.add(new Town(sourceCity, 0, 0, 0));
        
        while (!toExpand.isEmpty() && !solution) {
            // gets the city with lower g value
            Town currentTown = toExpand.poll();
            nodes++;
            //System.out.println("selected node: "+currentTown.number+" with g = "+currentTown.g);

            // TODO: It could be in Town class ... so it wouldn't need to be computed
            // every time ...
            // rebuild the followed route for the selected town
            Town aux = currentTown;
            followedRoute = new ArrayList();
            followedRoute.add(aux.number);
            while (aux.level != 0) {
                aux = aux.parent;
                followedRoute.add(0, aux.number);
                //System.out.println("the level of city "+aux.number+" is not zero");
            }
            
            if (currentTown.level == distances.getCitiesCount()) {
                solution = true;
                optimumRoute = followedRoute;
                optimumCost = currentTown.g;
            } else {
                
                for (int i=0; i<distances.getCitiesCount(); i++) {
                    // have we visited this city in the current followed route?
                    boolean visited = followedRoute.contains(i);
                    boolean isSolution = (followedRoute.size() == distances.getCitiesCount())&&(i == sourceCity);

                    /*
                    System.out.println("candidate child node: "+i);
                    System.out.println("visited = "+visited);
                    System.out.println("isSolution = "+isSolution);
                     */

                    if (!visited || isSolution) {
                        Town childTown = new Town(i, currentTown.g + distances.getCost(currentTown.number, i), 0, currentTown.level + 1);
                        //System.out.println("selected child town: "+childTown.number);
                        childTown.parent = currentTown;
                        toExpand.add(childTown);  
                    }
                }                
            }
        }
        long endTime = System.currentTimeMillis();
        
        result =  "UNIFORM COST SEARCH\n\n";     
        result += "Better solution: "+optimumRoute.toString() + "// Cost: "+optimumCost+"\n";
        result += "Visited Nodes: "+nodes+"\n";
        result += "Elapsed Time: "+(endTime-startTime)+" ms\n";
        
        return result;
    }
    
}
