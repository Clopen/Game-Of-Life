package game;

public class GameofLifeRules {
    
    final static boolean ALIVE = true;
    final static boolean DEAD = false;
    
    public static boolean isAlive(boolean cellState, int numberofLiveNeighbors){
        
        return numberofLiveNeighbors == 3 || cellState == ALIVE && numberofLiveNeighbors == 2;
    }
    
    public static boolean isInBounds(int size, int x, int y){
    	
    	return x >= 0 && x < size && y >= 0 && y < size ;
    }
    
    public static int numberOfLiveNeighbors(boolean[][] theGrid, int xCoordinate, int yCoordinate){
       
    	int numberAlive = 0;        
             
        for (int x = xCoordinate - 1; x <= xCoordinate + 1; x++){
            for (int y = yCoordinate - 1; y <= yCoordinate + 1; y++){
                if(isInBounds(theGrid.length, x, y) && theGrid[x][y] == ALIVE)
                    numberAlive++;
            }
        }
        
        return theGrid[xCoordinate][yCoordinate] ? numberAlive - 1 : numberAlive; 
    }
    
    public static boolean[][] nextGeneration(boolean[][] currentGrid){    	
    
    	boolean[][] nextGrid = new boolean[currentGrid.length][currentGrid[0].length];     	
    	
    	for(int row = 0; row < currentGrid.length; row++){ 
            for(int col = 0; col < currentGrid[0].length; col++){ 
                int aliveNeighbors = numberOfLiveNeighbors(currentGrid, row, col); 
                nextGrid[row][col] = isAlive(currentGrid[row][col], aliveNeighbors);
            }
    	}
    	
    	return nextGrid;
    	
    }
    
}
