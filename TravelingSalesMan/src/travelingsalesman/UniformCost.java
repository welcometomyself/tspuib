/*
 * UniformCost.java
 *
 * $LastChangedDate$
 * $LastChangedRevision$
 * Vicente J. Ferrer Dalmau
 * < vicente@jdalmau.es >
 *
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
    PriorityQueue<Town> opened = new PriorityQueue<Town>(200, 
        new Comparator<Town>() {
          public int compare(Town a, Town b) {
            return a.g - b.g;
          }
        }
      );  
    String result = new String();
    
    ArrayList optimumRoute;
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
        
        // initial town
        opened.add(new Town(sourceCity, 0, 0));
        
        
        while (!opened.isEmpty() && !solution) {
            Town currentTown = opened.poll();
            
            for (int i=0; i<distances.getCitiesCount(); i++) {
                // have we visited this city in the current followed route?
                boolean visited = false;
                boolean isSolution = false;
                Town aux = currentTown;
                ArrayList followedRoute = new ArrayList();
                followedRoute.add(aux.number);
                while (aux.level != 0) {
                    followedRoute.add(aux.number);
                    if (aux.number == i)
                        visited = true;
                    aux = aux.parent;
                }
                isSolution = (followedRoute.size() == distances.getCitiesCount())&&(i == sourceCity);
                
                if (!visited || isSolution) {
                    
                    Town childTown = new Town(i, currentTown.g + distances.getCost(currentTown.number, i), currentTown.level + 1);
                    childTown.parent = currentTown;
                    opened.add(childTown);  
                }
            }
            
            
        }
        
        return "";
    }
    
}
