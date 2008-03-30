/*
 * AStar.java
 *
 * $LastChangedDate$
 * $LastChangedRevision$
 * Vicente J. Ferrer Dalmau
 * < vicente@jdalmau.es >
 *
 * Implementation of the A* (A Star) algorithm.
 */

package travelingsalesman;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Vector;

/**
 *
 * @author Vicente J. Ferrer Dalmau
 */
public class AStar {
    
    RoutesMatrix distances;
    int sourceCity;
    PriorityQueue<Town> opened = new PriorityQueue<Town>(1000, 
        new Comparator<Town>() {
          public int compare(Town a, Town b) {
            return a.f - b.f;
          }
        }
      );  
    String result = new String();
    
    ArrayList optimumRoute;
    int nodes = 0;
    int optimumCost = Integer.MAX_VALUE;     
    
    // Estimation of the cost between two cities, it can overestimate the real value (h' > h),
    // so the algorithm it's not optimum.
    int HEURISTICCONSTANT = 15;
    
    /**
     * Gets the heuristic value for a given depth
     * The level 0 has the maximum value.
     */
    private int getHeuristicValue (int level) {
        
        return HEURISTICCONSTANT * (distances.getCitiesCount() - level);
    }
    
    /** Creates a new instance of AStar */
    public AStar(RoutesMatrix matrix, int sourceCity) {
        
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
        opened.add(new Town(sourceCity, 0, getHeuristicValue(0), 0));
        
        while (!opened.isEmpty() && !solution) {
            // gets the city with lower g value
            Town currentTown = opened.poll();
            nodes++;

            // rebuild the followed route for the selected town
            Town aux = currentTown;
            ArrayList followedRoute = new ArrayList();
            followedRoute.add(aux.number);
            while (aux.level != 0) {
                aux = aux.parent;
                followedRoute.add(aux.number);
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

                    if (!visited || isSolution) {
                        Town childTown = new Town(i, currentTown.g + distances.getCost(currentTown.number, i), 
                                getHeuristicValue(currentTown.level + 1), currentTown.level + 1);
                        childTown.parent = currentTown;
                        opened.add(childTown);  
                    }
                }                
            }
        }
        long endTime = System.currentTimeMillis();
        
        result = "-------------------------------------\n";
        result +=  "A ESTRELLA:\n";
        result += "-------------------------------------\n";
        result += "MEJOR SOLUCIÓN: \t"+optimumRoute.toString() + "\nCOSTE: \t\t"+optimumCost+"\n";
        result += "NODOS VISITADOS: \t"+nodes+"\n";
        result += "TIEMPO TRANSCURRIDO: \t"+(endTime-startTime)+" ms\n";
        result += "-------------------------------------\n";
        
        return result;        
    }    
    
}
