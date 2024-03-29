package AStar;

import Puzzle.PuzzleGame;
import java.awt.Point;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class AStarGameSolver {

    public static int puzzleDimension = Puzzle.PuzzleGame.puzzleDimension;
    public static int maxValue = Puzzle.PuzzleGame.gameSlotsNumber;
    private PuzzleNode root;
    PriorityQueue<PuzzleNode> openList;
    private int addedToOpenCounter=0;
    /*  we keep the previous positions and insure we never get there again. This keeps us from moving the same
    square back and forth ,and forces the moves taken  to advance towrds the goal. */
    public static HashSet<PuzzleNode> prevPos = new HashSet<PuzzleNode>();
    long startTime = 0;
    long endTime = 0;

    public AStarGameSolver(int[][] puzzle) throws Exception {

        /*Validate Size*/
        if ((puzzle.length != puzzleDimension) | (puzzle[0].length != puzzleDimension)) {
            throw new Exception("InValid Game Width");
        }

        /*Make Sure it has Space in it And Get It*/
        Point spaceCell = PuzzleGame.getSpacePoint(puzzle);
        if (spaceCell == null) {
            throw new Exception("where is the space?  :-) ");
        }

        /*Create Puzzle*/
        this.root = new PuzzleNode(puzzle, null, spaceCell);

        AStarGameSolver.prevPos.clear();
        openList = new PriorityQueue<PuzzleNode>(100,new PuzzleNodeRelaxCompartor());
    }

    /**
     * This function will run the A* algorithm.
     */
    public void solveGame() {

        startTime = System.currentTimeMillis();
        openList.add(root); /*Add Root to Open List*/
        addedToOpenCounter++;
        while (openList.size() > 0) {
            PuzzleNode currSolutionNode = openListExtrectMin();
            //System.out.println(openList.size()+prevPos.size());
            if (isDone(currSolutionNode)) {
                endTime = System.currentTimeMillis();
                printResultInfo(currSolutionNode);
                return;
            } else {
                //expend not yet seen childs to open list.
                LinkedList<PuzzleNode> childs = currSolutionNode.getMyChilds();
                prevPos.add(currSolutionNode);
                for (int i = 0; i < childs.size(); i++) {
                    openList.add(childs.get(i));
                    addedToOpenCounter++;
                }
            }
        }
        endTime = System.currentTimeMillis();
        printResultInfo(null);
    }

    /**
     * This function will compare the given state to the goal node - by using the heuristic function of Manhattan distance
     * @param currNode the current PuzzleNodw
     * @return true if the current state is the goal state, false otherwise
     */
    private boolean isDone(PuzzleNode currNode) {
        return (currNode.getMovesToGoal() == 0);
    }

    /**
     * This function will get the next state to handle.
     * @return the minimum cost PuzzleNode which need to be handled
     */
    private PuzzleNode openListExtrectMin() {
        PuzzleNode res = openList.remove();
        return res;
    }

    private void printResultInfo(PuzzleNode resultNode) {

        long runTime = endTime - startTime;
        System.out.println("Run Time: " + runTime + " millisec");

        if (resultNode == null) {
            System.out.println("No Solution Found");
            return;
        }

        System.out.println("Solution Found");
        System.out.println("Solution Depth :" + (resultNode.getNodeDepth()-1));
        System.out.println("Number of Nodes That has Been expended: " + prevPos.size());
        System.out.println("Number of Nodes That has Been Generated: " + addedToOpenCounter);
        
        if (puzzleDimension == 3) {
            System.out.println("Number Of Nodes in The Search Space: 9!/2=181440");
        }
        if (puzzleDimension == 4) {
            System.out.println("Number Of Nodes in The Search Space: 16!/2=Approx " + Math.pow(10, 13));
        }
    }

    /**
     * This class is a Comparator class for comparing PuzzleNode according to their puzzle state
     */
    private class PuzzleNodeRelaxCompartor implements Comparator {

        public int compare(Object first, Object second) {

            int firstValue = ((PuzzleNode) first).getMovesToGoal() + ((PuzzleNode) first).getMovesFromStart();
            int secondValue = ((PuzzleNode) second).getMovesToGoal() + ((PuzzleNode) second).getMovesFromStart();

            if (firstValue > secondValue) {
                return 1;
            }
            if (firstValue < secondValue) {
                return -1;
            }
            
            //return 0;
            /* if tie then return  the one with bigger G (Steps to Here) Or (Eqviv) the Lowest H*/
            if (firstValue==secondValue)
            {
                if (((PuzzleNode) first).getMovesFromStart() >  ((PuzzleNode) second).getMovesFromStart())
                {
                    return -1;
                }
                else
                {
                    return 1;
                }
            }
            return 0;
        }
    }             
}//End of AStarGameSolver Class

