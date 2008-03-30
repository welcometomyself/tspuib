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

            // rebuild the followed route for the selected town
            Town aux = currentTown;
            followedRoute = new ArrayList();
            followedRoute.add(aux.number);
            while (aux.level != 0) {
                aux = aux.parent;
                followedRoute.add(0, aux.number);
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
                        Town childTown = new Town(i, currentTown.g + distances.getCost(currentTown.number, i), 0, currentTown.level + 1);
                        childTown.parent = currentTown;
                        toExpand.add(childTown);  
                    }
                }                
            }
        }
        long endTime = System.currentTimeMillis();        
        
        result = "-------------------------------------\n";
        result +=  "BÚSQUEDA DE COSTO UNIFORME:\n";
        result += "-------------------------------------\n";
        result += "MEJOR SOLUCIÓN: \t"+optimumRoute.toString() + "\nCOSTE: \t\t"+optimumCost+"\n";
        result += "NODOS VISITADOS: \t"+nodes+"\n";
        result += "TIEMPO TRANSCURRIDO: \t"+(endTime-startTime)+" ms\n";
        result += "-------------------------------------\n";        
        
        return result;
    }
    
}
