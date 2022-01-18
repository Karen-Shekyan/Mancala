import java.util.*;
import java.io.*;

public class tester {

  public static int[][] doTurnC(int[][] board, int well) {
    int stones = board[0][well-1];
    board[0][well-1] = 0;
    int curr = well;
    while (stones != 0) {
      if (curr >= 1) {
        curr++;
      }
      if (curr <= -1) {
        curr--;
      }

      if (curr == 8) {
        curr = -1;
      }
      if (curr == -7) {
        curr = 1;
      }

      int col = Math.abs(curr)-1;
      int row = -1;
      if (curr < 0) {
        row = 1;
      }
      else {
        row = 0;
      }
      board[row][col]++;
      stones--;
    }
    return board;
  }

  public static boolean extraTurn (ArrayList<Integer> moves, int[][] board) {
    for (int i = 0; i < moves.size()-1; i++) {
      board = doTurnC(board,moves.get(i));
    }
    int well = moves.get(moves.size()-1);
    boolean ans = (board[0][well-1]%13 == 7-well);
    board = doTurnC(board,well);
    return ans;
  }

  public static ArrayList<ArrayList<Integer>> moveSet(int[][] board) {
    ArrayList<ArrayList<Integer>> allFirstMoves = new ArrayList<ArrayList<Integer>>();
    for (int i = 1; i < 7; i++) {
      if (board[0][i-1] != 0) {
        ArrayList<Integer> move = new ArrayList<Integer>();
        move.add(i);
        allFirstMoves.add(move);
      }
    }

    boolean done = false;
    while (!done) {
      done = true;
      for (int i = 0; i < allFirstMoves.size(); i++) {
        int[][] boardc = new int[2][7];
        for (int l = 0; l < 2; l++) {
          for (int j = 0; j < 7; j++) {
            boardc[l][j] = board[l][j];
          }
        }
        if (extraTurn(allFirstMoves.get(i),boardc)) {
          done = false;
          ArrayList<ArrayList<Integer>> movesAfter = moveSet(boardc);

          for (int j = 0; j < movesAfter.size(); j++) {
            ArrayList<Integer> newMove = new ArrayList<Integer>();
            for (int k = 0; k < allFirstMoves.get(i).size(); k++) {
              newMove.add(allFirstMoves.get(i).get(k));
            }

            for (int k = 0; k < movesAfter.get(j).size(); k++) {
              newMove.add(movesAfter.get(j).get(k));
            }
            allFirstMoves.add(newMove);
          }

          allFirstMoves.remove(i);
          i--;
        }
      }
    }
    return allFirstMoves;
  }

  public static void main(String[] args) {
    int[][] board = {
      {4,4,4,4,4,4,0},
      {4,4,4,4,4,4,0}
    };
    ArrayList<ArrayList<Integer>> moves = moveSet(board);
    for (int i = 0; i < moves.size(); i++) {
      System.out.println(moves.get(i));
    }
  }
}
