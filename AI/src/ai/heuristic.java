package AI;
import java.util.*;
import static java.lang.System.exit;


public class heuristic {
	    private static final int PUZZLE_SIZE = 3; 
	    private static final int ERROR = -1; 
	    private static final int FINISHED = 0;  
	    private static final int EUCLIDEAN_DISTANCE = 1;
	    private static final int A_STAR_MANHATTAN = 2;
	    private static PriorityQueue<State> queue = new PriorityQueue<>(Comparator.comparing(State::getF_n));  // Sort queue by f_n value
	    private static ArrayList<State> explored = new ArrayList<>();  // previously explored nodes
	    private int[][] goalState = {{0, 1, 2},
	                                 {3, 4, 5},
	                                 {6, 7, 8}};
	    private static int nodesExpanded = 0; //"Number of nodes expanded: "
	    private static int maxQueueSize = 1;
    public heuristic() {
	    	  System.out.println("+=================================+");
	          System.out.println("|         8-Puzzel Solver         |");
	          System.out.println("|                                 |");
	          System.out.println("+=================================+\n");
	          int returnVal = -1; // check if success of Error
	          int algorithm ;  // manhatten or Eculiden 
	          long startTime=0;
	          long endTime=0 ;
	          Scanner scanner = new Scanner(System.in);
                  
                  
	              System.out.println("Please enter your initial state with white spaces separating each number \n" +
	                      "and hit enter to go to the next row. Use 0 to represent the blank slot.");
	              int[][] custom = new int[PUZZLE_SIZE][PUZZLE_SIZE];
	              for(int i=0; i<PUZZLE_SIZE; i++) {
	                  String[] row;
	                  String line = scanner.nextLine();
	                  if(line == null) {
	                      System.err.println("Please enter a valid layout!");
	                      exit(1);
	                  }
	                  row = line.split(" ");
	                  for(int j=0; j<PUZZLE_SIZE; j++)
	                      custom[i][j] = Integer.parseInt(row[j]);
	              }for(int j=0; j<PUZZLE_SIZE; j++)
                          for(int i=0; i<PUZZLE_SIZE; i++)
                              if (custom[i][j]>8||custom[i][j]<0)
                              {System.out.println("ther are a number that greatest than 8");
                      exit(1);}	
                      
                      
	              System.out.println("Please select a search algorithm:\n1) A* Euclidean Ditance\n2) A* Manhattan");
	              algorithm = Integer.parseInt(scanner.nextLine());
	              if(algorithm<=0 || algorithm>2) {
	                  System.err.println("Please enter a correct algorithm: 1-2!");
	                  exit(1);
	              } else {
	                  System.out.println("Input state:");
	                  printState(custom);
	                  startTime = System.currentTimeMillis();
	                  returnVal = generalSearch(custom,algorithm);
	                  endTime = System.currentTimeMillis();
	              }
	            if(returnVal == FINISHED) {
	                System.out.println("Solved!\n");
	                System.out.println("Number of nodes expanded: " + nodesExpanded);
	                System.out.println("Max queue size: " + maxQueueSize);
	                System.out.println("Time taken: "+(endTime-startTime)+"ms");
	            } else if (returnVal == ERROR) {
	                System.out.println("Error: Given input has no solution!");
	            }
}

	        private int generalSearch(int[][] currGrid, int searchFunction) {
	            queue.add(new State(currGrid,0,0));
	            explored.add(new State(currGrid,0,0));
	            while(true) {
	                 
                        if(queue.isEmpty()) return ERROR;
                        
	                ArrayList<State> children;
	                State tempState = queue.peek();
	                int[][] tempNode = tempState.getGrid();    // return first element  
	                int [][] topNode = new int[currGrid.length][]; //3*3
	                for(int i = 0; i < currGrid.length; i++)
	                 topNode[i] = tempNode[i].clone();  // copy i 
	                
                        
                        State topState = new State(topNode,tempState.getG_n(),tempState.getH_n());
	                System.out.println("pop top node in queue with g(n)="+topState.getG_n()+" and h(n)="+topState.getH_n()+" and f(n)="+topState.getF_n()+"...");
	                printState(topState.getGrid());
	                queue.remove();     // remove the peeked element
                        
	                if(stateEqual(topState.getGrid(), goalState)) {
	                    return FINISHED;
	                }
                        else {  // keep expanding
	                    children = getChildren(topState, searchFunction);    // number of nodes expanded
	                    System.out.println("children size -> "+children.size()+"\n");
	                    if(children.size()==0) { 
	                        System.out.println("Top node in queue has no descendants, going to next node.\n");
	                        continue;  
	                    }
	                    nodesExpanded++;
	                    System.out.println("Expanding top node in queue...");
	                    for(State child : children) {
	                        if(!containsChild(child)) { 
	                            queue.add(child);
	                            explored.add(child);
	                            if(queue.size() > maxQueueSize) 
                                        maxQueueSize = queue.size();
	                            System.out.println("Adding following child to queue:");
	                            printState(child.getGrid());
	                        } 
	                    }
	                }
	            }

	        }
	        private ArrayList<State> getChildren(State currState, int searchFunction) {     // 3ashan negib el x w el y
	            ArrayList<State> retArray = new ArrayList<>();
                    HashSet<State> hs = new HashSet<State>(); // to make the search o(1)
	            int[][] currGrid = currState.getGrid();
	            int g_n = currState.getG_n();
	            int zero_loc_x = -1;
	            int zero_loc_y = -1;
	            for(int i=0; i<PUZZLE_SIZE; i++) {
	                for(int j=0; j<PUZZLE_SIZE; j++) { // searching for zero 
	                    if(currGrid[i][j]==0) {
	                        zero_loc_x = i;
	                        zero_loc_y = j;
	                    }
	                }
	            }

	            int h_n = 0;
	            switch(searchFunction) {
	            case EUCLIDEAN_DISTANCE: //1
	            	int counter = 0;
	            	for (int i = 0;i < 3;i++)
	            	{
	            		for (int j = 0;j < 3;j++)
	            		{
	            			if (currGrid[i][j] == 0)
	            			{
	            				continue;
	            			}
	            			if (currGrid[i][j] != goalState[i][j])
	            			{
	            				double n = 0;
	            				double m = 0;

	            				for (int y = 0;y < 3;y++)
	            				{
	            					for (int z = 0;z < 3;z++)
	            					{
	            						if (goalState[y][z] == currGrid[i][j])
	            						{
	            							n = y;
	            							m = z;
	            						}
	            					}
	            				}
	            				n = Math.pow((n - m),2);
	            				m = Math.pow((i - j),2);
	            				while (n != 0 || m != 0)
	            				{
	            					if (n < 0)
	            					{
	            						counter++;
	            						n++;
	            					}
	            					if (n > 0)
	            					{
	            						counter++;
	            						n--;
	            					}
	            					if (m < 0)
	            					{
	            						counter++;
	            						m++;
	            					}
	            					if (m > 0)
	            					{
	            						counter++;
	            						m--;
	            					}
                                                }
                                        }
                                }
                            
                        }
                        
                        
                        
	                case A_STAR_MANHATTAN: //2
	                	 for(int i=0; i<PUZZLE_SIZE; i++)
	                         for(int j=0; j<PUZZLE_SIZE; j++) {
	                             int target = currGrid[i][j];
	                             for (int k = 0; k < PUZZLE_SIZE; k++)
	                                 for (int l = 0; l < PUZZLE_SIZE; l++) { // Note: Deeply-nested for loop is not a bottle neck here due to PUZZLE_SIZE limit
	                                     if(goalState[k][l]==target) {
	                                         h_n += Math.abs(k-i) + Math.abs(l-j);
	                                     }
	                                 }
	                         }
	                     break;
	                 default:
	                     System.err.println("Error: Entered wrong algorithm!");
	                     break;
	             }
                    
	             if(zero_loc_x-1 >=0) { 
	                 int [][] workingGrid = new int[currGrid.length][];
	                 
                         for(int i = 0; i < currGrid.length; i++)
	                     workingGrid[i] = currGrid[i].clone();
                         
	                 int temp = workingGrid[zero_loc_x][zero_loc_y];
	                 workingGrid[zero_loc_x][zero_loc_y] = workingGrid[zero_loc_x-1][zero_loc_y];
	                 workingGrid[zero_loc_x-1][zero_loc_y] = temp;
                         
                        
                         
                        State check = new State (workingGrid , g_n+1 ,h_n);
                        if(hs.isEmpty())
                        {  hs.add(new State (workingGrid , g_n+1 ,h_n));
                            retArray.add(new State(workingGrid,g_n+1,h_n));
                        }
                        else if (!hs.contains(check))
                        {
                            hs.add(new State (workingGrid , g_n+1 ,h_n));
                            retArray.add(new State(workingGrid,g_n+1,h_n));
                        }
                     
                     }
	             if(zero_loc_x+1 < PUZZLE_SIZE) {  
	                 int [][] workingGrid = new int[currGrid.length][];
	                 for(int i = 0; i < currGrid.length; i++)
	                     workingGrid[i] = currGrid[i].clone();
	                 int temp = workingGrid[zero_loc_x][zero_loc_y];
	                 workingGrid[zero_loc_x][zero_loc_y] = workingGrid[zero_loc_x+1][zero_loc_y];
	                 workingGrid[zero_loc_x+1][zero_loc_y] = temp;
	                 
                         State check = new State (workingGrid , g_n+1 ,h_n);
                        if(hs.isEmpty())
                        {  hs.add(new State (workingGrid , g_n+1 ,h_n));
                            retArray.add(new State(workingGrid,g_n+1,h_n));
                        }
                        else if (!hs.contains(check))
                        {
                            hs.add(new State (workingGrid , g_n+1 ,h_n));
                            retArray.add(new State(workingGrid,g_n+1,h_n));
                        }
                     }
                     
	             if(zero_loc_y-1 >=0) { 
	                 int [][] workingGrid = new int[currGrid.length][];
	                 for(int i = 0; i < currGrid.length; i++)
	                     workingGrid[i] = currGrid[i].clone();
	                 int temp = workingGrid[zero_loc_x][zero_loc_y];
	                 workingGrid[zero_loc_x][zero_loc_y] = workingGrid[zero_loc_x][zero_loc_y-1];
	                 workingGrid[zero_loc_x][zero_loc_y-1] = temp;
	                 
                         State check = new State (workingGrid , g_n+1 ,h_n);
                        if(hs.isEmpty())
                        {  hs.add(new State (workingGrid , g_n+1 ,h_n));
                            retArray.add(new State(workingGrid,g_n+1,h_n));
                        }
                        else if (!hs.contains(check))
                        {
                            hs.add(new State (workingGrid , g_n+1 ,h_n));
                            retArray.add(new State(workingGrid,g_n+1,h_n));
                        }
                     }
                     
	             if(zero_loc_y+1 < PUZZLE_SIZE) { 
	                 int [][] workingGrid = new int[currGrid.length][];
	                 for(int i = 0; i < currGrid.length; i++)
	                     workingGrid[i] = currGrid[i].clone();
	                 int temp = workingGrid[zero_loc_x][zero_loc_y];
	                 workingGrid[zero_loc_x][zero_loc_y] = workingGrid[zero_loc_x][zero_loc_y+1];
	                 workingGrid[zero_loc_x][zero_loc_y+1] = temp;
	                  State check = new State (workingGrid , g_n+1 ,h_n);
                        if(hs.isEmpty())
                        {  hs.add(new State (workingGrid , g_n+1 ,h_n));
                            retArray.add(new State(workingGrid,g_n+1,h_n));
                        }
                        else if (!hs.contains(check))
                        {
                            hs.add(new State (workingGrid , g_n+1 ,h_n));
                            retArray.add(new State(workingGrid,g_n+1,h_n));
                        }
                     }
	             return retArray;
	         }
                
	         private void printState(int[][] currGrid) {
	             for(int i=0; i<PUZZLE_SIZE; i++) {
	                 for(int j=0; j<PUZZLE_SIZE; j++) {
	                     System.out.print(currGrid[i][j]+" ");
	                 }
	                 System.out.println();
	             }
	             System.out.println();
	         }
                 
	         private boolean stateEqual(int[][] grid1, int[][] grid2) {
	             for(int i=0; i<PUZZLE_SIZE; i++)
	                 for(int j=0; j<PUZZLE_SIZE; j++)
	                     if(grid1[i][j] != grid2[i][j])
	                    	 return false;
	             return true;
	         }
                 
	         private boolean containsChild(State state) {
	             int[][] child = state.getGrid();
	             for(State state2 : explored) {
	                 int[][] temp = state2.getGrid();
	                 boolean identical = true;
	                 for(int i=0; i<PUZZLE_SIZE; i++)
	                     for(int j=0; j<PUZZLE_SIZE; j++)
	                         if(temp[i][j] != child[i][j])
	                             identical = false;
	                 if(identical)
	                     return true;
	             }
	             return false;
	         }
                 
                 
	         public class State {
	             private int[][] grid;
	             private int g_n;  //cost
	             private int h_n;  //cost to goal
	             private State(int[][] grid, int g_n, int h_n) {
	                 this.grid = grid;
	                 this.g_n = g_n;
	                 this.h_n = h_n;
	             }
	             private int[][] getGrid() {
	                 return grid;
	             }
	             private int getG_n() {   // cost to reach n 
	                 return g_n;
	             }
	             private int getH_n() {   // cost to get from n to the goal 
	                 return h_n;
	             }
	             private int getF_n() {   // g(n)+h👎
	                 return (g_n + h_n);
	             }}
	    }