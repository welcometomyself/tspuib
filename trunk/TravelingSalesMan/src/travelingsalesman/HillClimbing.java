/*
 * HillClimbing.java
 *
 * $LastChangedDate$
 * $LastChangedRevision$
 * Vicente J. Ferrer Dalmau
 * < vicente@jdalmau.es >
 *
 * Implementation of the Hill Climbing algorithm (simple version).
 */

package travelingsalesman;

import java.util.ArrayList;

/**
 *
 * @author Vicente J. Ferrer Dalmau
 */
public class HillClimbing {
   
    RoutesMatrix distances;
    int sourceCity;

    String result = new String();
    
    ArrayList followedRoute;
    int nodes = 0;
    int routeCost = 0;     
   
    int HEURISTICCONSTANT = 15;    
    
    /**
     * Gets the heuristic value for a given depth
     * The level 0 has the maximum value.
     */
    private int getHeuristicValue (int level) {
        
        return HEURISTICCONSTANT * (distances.getCitiesCount() - level);
    }    
    
    /** Creates a new instance of HillClimbing */
    public HillClimbing(RoutesMatrix matrix, int sourceCity) {
        
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
        
        long startTime = System.currentTimeMillis();
        search(sourceCity);
        long endTime = System.currentTimeMillis();        
        
        result = "-------------------------------------\n";
        result +=  "MÉTODO DE ESCALADA:\n";
        result += "-------------------------------------\n";
        result += "MEJOR SOLUCIÓN: \t"+followedRoute.toString() + "\nCOSTE: \t\t"+routeCost+"\n";
        result += "NODOS VISITADOS: \t"+nodes+"\n";
        result += "TIEMPO TRANSCURRIDO: \t"+(endTime-startTime)+" ms\n";
        result += "-------------------------------------\n";        
        
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
                    int tempDistance = routeCost + getHeuristicValue(nodes-1); // f = g + h
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
