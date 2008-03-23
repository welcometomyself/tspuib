/*
 * BranchAndBound.java
 *
 * $LastChangedDate$
 * $LastChangedRevision$
 * Vicente J. Ferrer Dalmau
 * < vicente@jdalmau.es >
 *
 * Implementation of the branch and bound search algorithm.
 */

package travelingsalesman;

import java.util.ArrayList;

/**
 *
 * @author Vicente J. Ferrer Dalmau
 */
public class BranchAndBound {
    
    RoutesMatrix distances;
    int sourceCity;
    String result = new String();
    
    ArrayList initialRoute, optimumRoute;
    int nodes = 0;
    int routeCost = 0;
    int optimumCost = Integer.MAX_VALUE;    
    
    /** Creates a new instance of BranchAndBound */
    public BranchAndBound(RoutesMatrix matrix, int sourceCity) {
        
        distances = matrix;
        this.sourceCity = sourceCity;
    }
    
    /**
     * executes the algorithm
     */
    public String execute () {
        
        initialRoute = new ArrayList();
        initialRoute.add(sourceCity);
        optimumRoute = new ArrayList();
        nodes++;
        
        result =  "BRANCH AND BOUND SEARCH\n\n";
        
        long startTime = System.currentTimeMillis();
        search(sourceCity, initialRoute);
        long endTime = System.currentTimeMillis();
        
        result += "\nBetter solution: "+optimumRoute.toString() + "// Cost: "+optimumCost+"\n";
        result += "Visited Nodes: "+nodes+"\n";
        result += "Elapsed Time: "+(endTime-startTime)+" ms\n";
        
        return result;         
    }
    
    
    /**
     * @param from node where we start the search.
     * @param route followed route for arriving to node "from".
     */
    public void search (int from, ArrayList followedRoute) {
        
        // we've found a new solution
        if (followedRoute.size() == distances.getCitiesCount()) {
            
            followedRoute.add(sourceCity);
            nodes++;
            
            // update the route's cost
            routeCost += distances.getCost(from, sourceCity);
            
            if (routeCost < optimumCost) {
                optimumCost = routeCost;
                optimumRoute = (ArrayList)followedRoute.clone();
            }
            
            result += followedRoute.toString() + "// Cost: "+routeCost + "\n";
            
            // update the route's cost (back to the previous value)
            routeCost -= distances.getCost(from, sourceCity);
        }
        else {
            for (int to=0; to<distances.getCitiesCount(); to++){
                if (!followedRoute.contains(to)) {
                    
                    // update the route's cost
                    routeCost += distances.getCost(from, to);
                    
                    if (routeCost < optimumCost) { 
                        ArrayList increasedRoute = (ArrayList)followedRoute.clone();
                        increasedRoute.add(to);
                        nodes++;
                        search(to, increasedRoute);    
                    }
                    
                    // update the route's cost (back to the previous value)
                    routeCost -= distances.getCost(from, to);
                }
            }
        }
        
    }
    
}
