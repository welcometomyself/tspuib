/*
 * RoutesMatrix.java
 *
 * $LastChangedDate$
 * $LastChangedRevision$
 * Vicente J. Ferrer Dalmau
 * < vicente@jdalmau.es >
 *
 * This class defines all the distances between the cities in the problem.
 * For two cities, x and y, the both distances x to y and y to x remain the same.
 */

package travelingsalesman;

import java.util.Random;


/**
 *
 * @author Vicente J. Ferrer Dalmau
 */
public class RoutesMatrix {
    
    private int[][] theMatrix;
    private int cities;
    // all the distance values will be in a Uniform(MAXDISTANCE)
    private int MAXDISTANCE = 100;
    
    /** Creates a new instance of RoutesMatrix
     */
    public RoutesMatrix(int cities) {
        
        theMatrix = new int[cities][cities];
        this.cities = cities;
        
        // fill the matrix with random values
        // a new random generator (seed based on the current time)
        Random generator = new Random();
        
        for (int i=0; i<cities; i++) {
            for (int j=i; j<cities; j++) {
                if (i == j)
                    theMatrix[i][j] = 0;
                else {
                    theMatrix[i][j] = generator.nextInt(MAXDISTANCE);
                    theMatrix[j][i] = theMatrix[i][j];
                }
            }
        }
    }
   
    /**
     * returns the number of cities
     */
    public int getCitiesCount() {
        
        return cities;
    }
    
    /**
     * gets the cost of going from city "a" to city "b"
     */
    public int getCost(int a, int b) {
        
        return theMatrix[a][b];
    }
    
    /**
     * sets the cost of going from city "a" to city "b"
     */
    public void setCost(int a, int b, int cost) {
        
        theMatrix[a][b] = cost;
    }
    
    /**
     * gets the array of costs as an Object[][] array.
     */
    public Object[][] getCosts () {
        
        Object [][] array = new Object[cities][cities];
        for (int i=0; i<cities; i++){
            for (int j=0; j<cities; j++){
                array[i][j] = theMatrix[i][j];
            }
        }
        return array;
    }
    
    /**
     * gets the cities in an Object[] array.
     */
    public Object[] getCities () {
        
        Object[] array = new Object[cities];
        for (int i=0; i<cities; i++) {
            array[i] = i;
        }
        return array;
    }
    
    /**
     * gets the maximum distance between two cities.
     */
    public int getMaxDistance() {
        
        return MAXDISTANCE;
    }
    
    public String toString() {
        
        String str = new String();
        for (int i=0; i<cities; i++) {
            for (int j=0; j<cities; j++) {
                if (j == cities - 1)
                    str += theMatrix[i][j] + "\n";
                else
                    str += theMatrix[i][j] + ", ";
            }
        }
        return str;
    }
    
}
