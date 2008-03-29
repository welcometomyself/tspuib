/*
 * NearestNeighbour.java
 *
 * $LastChangedDate$
 * $LastChangedRevision$
 * Vicente J. Ferrer Dalmau
 * < vicente@jdalmau.es >
 *
 * Implementation of the Nearest Neighbour algorithm.
 */

package travelingsalesman;

import java.util.ArrayList;

/**
 *
 * @author Vicente J. Ferrer Dalmau
 */
public class NearestNeighbour {
    
    RoutesMatrix distances;
    int sourceCity;

    String result = new String();
    
    ArrayList followedRoute;
    int nodes = 0;
    int routeCost = 0;   
    
    /** Creates a new instance of NearestNeighbour */
    public NearestNeighbour(RoutesMatrix matrix, int sourceCity) {
        
        distances = matrix;
        this.sourceCity = sourceCity;        
    }
    
    /**
     * executes the algorithm
     */
    public String execute () {
        
        followedRoute = new ArrayList();
        followedRoute.add(sourceCity);
        nodes++;
        
        result =  "NEAREST NEIGHBOUR SEARCH\n\n";
        
        long startTime = System.currentTimeMillis();
        search(sourceCity);
        long endTime = System.currentTimeMillis();
        
        result += "\nBetter solution: "+followedRoute.toString() + "// Cost: "+routeCost+"\n";
        result += "Visited Nodes: "+nodes+"\n";
        result += "Elapsed Time: "+(endTime-startTime)+" ms\n";
        
        return result;         
    }   
    
    /**
     * @param from node where we start the search.
     */
    public void search (int from) {
        
        int currentTown = from;
        
        while (nodes != distances.getCitiesCount()) {
            // choose the closest town
            int lowestDistance = Integer.MAX_VALUE;
            int chosen = -1;
            for (int i=0; i < distances.getCitiesCount(); i++) {
                if (!followedRoute.contains(i)) {
                    int tempDistance = distances.getCost(currentTown, i);
                    if (tempDistance < lowestDistance) {
                        lowestDistance = tempDistance;
                        chosen = i;
                    }
                }
            }
            routeCost += distances.getCost(currentTown, chosen);
            followedRoute.add(chosen);
            currentTown = chosen;
            nodes++;
        }
        // add the last town
        routeCost += distances.getCost(currentTown, sourceCity);
        followedRoute.add(sourceCity);
        nodes++;
    }    
}
