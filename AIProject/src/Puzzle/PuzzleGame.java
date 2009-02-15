package Puzzle;

public class PuzzleGame {

    public static final int gameSlotsNumber = 15;
    public static int puzzleDimension = 4;
    public enum Algorithm {AStar,IDAStar};    
    private static int[][][] manhatanDistance;

   static {
        manhatanDistance = new int[gameSlotsNumber + 1][puzzleDimension][puzzleDimension];
        for (int value = 0; value <= gameSlotsNumber; value++) {
            for (int i = 0; i < puzzleDimension; i++) {
                for (int j = 0; j < puzzleDimension; j++) {
                    manhatanDistance[value][i][j] = Math.abs(value / puzzleDimension - i) + Math.abs(value % puzzleDimension - j);
                }
            }
        }
    }

    public static int getManahtanDistance(int[][] puzzle) {
        int distance = 0;
        int[] puzzleRaw;
        int tValue;
        for (int i = 0; i < puzzle.length; i++) {
            puzzleRaw = puzzle[i];
            for (int j = 0; j < puzzleRaw.length; j++) {
                tValue = puzzle[i][j];
                if (tValue != 0) {
                    distance += manhatanDistance[tValue][i][j];
                }
            }
        }
        return distance;
    }

    public static void main(String[] args) {

        int[][][] puzzels = {
            //Puzzle 0
            {{1, 9, 3, 7},
                {4, 0, 6, 15},
                {5, 13, 2, 11},
                {8, 12, 14, 10}
            },
            //Puzzle 1
            {{1, 3, 6, 7},
                {13, 14, 9, 15},
                {4, 0, 8, 11},
                {12, 5, 2, 10}
            },
            //Puzzle 2
            {{1, 14, 6, 7},
                {13, 15, 0, 11},
                {4, 5, 3, 9},
                {12, 2, 8, 10}
            },
            //Puzzle 3
            {{4, 8, 1, 15},
                {13, 11, 10, 2},
                {0, 12, 3, 14},
                {5, 9, 7, 6}
            },
            //85 - Puzzle 4 initial h=32, depth = 44 time
            {{4, 7, 13, 10},
                {1, 2, 9, 6},
                {12, 8, 14, 5},
                {3, 0, 11, 15}
            },
            //Puzzle 5
            {{5, 7, 11, 8},
                {0, 14, 9, 13},
                {10, 12, 3, 15},
                {6, 1, 4, 2}
            },
            //Puzzle 6 initial h=38, depth = 54
            {{9, 8, 0, 2},
                {15, 1, 4, 14},
                {3, 10, 7, 5},
                {11, 13, 6, 12}
            },
            //55 - Puzzle 7   initial h= 29, depth =  41,  927,212.0
            {
                {13, 8, 14, 3},
                {9, 1, 0, 7},
                {15, 5, 4, 10},
                {12, 2, 6, 11}
            },
            //95 - Puzzle 8 - initial h = 34, depth = 50,  7,115,967.0
            {{4, 3, 6, 13},
                {7, 15, 9, 0},
                {10, 5, 8, 11},
                {2, 12, 1, 14}
            },
            //88 - Puzzle 9 initial h = 43, depth = 65,  6,009,130,748.0
            {{15, 2, 12, 11},
                {14, 13, 9, 5},
                {1, 3, 8, 7},
                {0, 10, 6, 4}
            }, 
            //29 - Puzzle 10  h = 38,depth =  54,       117076111.0
            {{9,8,0,2},
             {15,1,4,14},
             {3,10,7,5},
             {11,13,6,12}
            }            
        };
        int puzzleNumber = 10; 
        Algorithm chosenAlgorithm = Algorithm.IDAStar;
        
        try {
            switch(chosenAlgorithm){
                case AStar:
                 /*Astar Solver*/
                   AStar.PuzzleGame puzzleGame = new AStar.PuzzleGame(puzzels[puzzleNumber]);
                   puzzleGame.solveGame();              
                    break;
                case IDAStar:
                    /*IDAStar Solver*/
                    IDAstar.IDAStar idaStar = new IDAstar.IDAStar(puzzels[puzzleNumber]);
                    idaStar.solveGame();
                    break;                    
            }                            
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }


    }
}