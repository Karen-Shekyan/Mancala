import java.util.*;
import java.io.*;

public class game {
  //board has 2 rows and 7 columns.

  //update this************************************************************************************************************************************************
  public static int evaluate(int[][] board) {
    int value = 0;
    boolean endGame = true;
    for (int i = 0; i < 7; i++) {
      int c = board[0][i];
      int p = board[1][i];
      value += c;
      value -= p;
      if (c != 0 || p != 0) {
        endGame = false;
      }
    }

    if (endGame) {
      if (value < 0) {
        return Integer.MIN_VALUE;
      }
      else if (value > 0) {
        return Integer.MAX_VALUE;
      }
    }

    return value;
  }
  //***********************************************************************************************************************************************************

//minimum values for depth and maxDepth is 1. You must look at least 1 turn ahead.
  public static int value(int[][] board, int depth, int maxDepth) {
    if (depth == maxDepth) {
      return evaluate(board);
    }

    if (depth%2 == 0) {//want max here, computer moves.
      int[][] boardc = new int[2][7];
      for (int l = 0; l < 2; l++) {
        for (int j = 0; j < 7; j++) {
          boardc[l][j] = board[l][j];
        }
      }
      ArrayList<ArrayList<Integer>> turns = moveSetC(boardc);
      int max = Integer.MIN_VALUE;
      int index = -1;
      for (int i = 0; i < turns.size(); i++) {
        int[][] newboard = doTurnC(int[][] board, turns.get(i));//THIS METHOD RETURNS A COPY!;
        int value = value(newboard,depth+1,maxDepth);
        if (value > max) {
          max = value;
          index = i;
        }
      }
      return max;
    }

    else {//want min here, player moves.
      int[][] boardc = new int[2][7];
      for (int l = 0; l < 2; l++) {
        for (int j = 0; j < 7; j++) {
          boardc[l][j] = board[l][j];
        }
      }
      ArrayList<ArrayList<Integer>> turns = moveSetP(boardc);
      int min = Integer.MAX_VALUE;
      int index = -1;
      for (int i = 0; i < turns.size(); i++) {
        int[][] newboard = doTurnP(int[][] board, turns.get(i));//THIS METHOD RETURNS A COPY!;
        int value = value(newboard,depth+1,maxDepth);
        if (value < min) {
          min = value;
          index = i;
        }
      }
      return min;
    }

    return Integer.MIN_VALUE;
  }
//combine these? ^v
  public static ArrayList<Integer> findTurn(ArrayList<ArrayList<Integer>> turns, int[][] board, int maxDepth) {
    int max = Integer.MIN_VALUE;
    int index = -1;
    for (int i = 0; i < turns.size(); i++) {
      int[][] newboard = doTurnC(int[][] board, turns.get(i));//THIS METHOD RETURNS A COPY!;
      int value = value(newboard, 1, maxDepth);
      if (value > max) {
        max = value;
        index = i;
      }
    }

    return turns.get(i);
  }

  public static boolean extraTurnP (ArrayList<Integer> moves, int[][] board) {
    for (int i = 0; i < moves.size()-1; i++) {
      board = doMoveP(board,moves.get(i));
    }
    int well = moves.get(moves.size()-1);
    boolean ans = (board[1][well-1]%13 == 7-well);
    board = doMoveP(board,well);
    return ans;
  }

  public static ArrayList<ArrayList<Integer>> moveSetP(int[][] board) {
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
        if (extraTurnP(allFirstMoves.get(i),boardc)) {
          done = false;
          ArrayList<ArrayList<Integer>> movesAfter = moveSetP(boardc);

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

  public static boolean extraTurnC (ArrayList<Integer> moves, int[][] board) {
    for (int i = 0; i < moves.size()-1; i++) {
      board = doMoveC(board,moves.get(i));
    }
    int well = moves.get(moves.size()-1);
    boolean ans = (board[0][well-1]%13 == 7-well);
    board = doMoveC(board,well);
    return ans;
  }

  public static ArrayList<ArrayList<Integer>> moveSetC(int[][] board) {
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
        if (extraTurnC(allFirstMoves.get(i),boardc)) {
          done = false;
          ArrayList<ArrayList<Integer>> movesAfter = moveSetC(boardc);

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

  public static int[][] doTurnC(int[][] board, ArrayList<Integer> moves) {
    int[][] boardc = new int[2][7];
    for (int l = 0; l < 2; l++) {
      for (int j = 0; j < 7; j++) {
        boardc[l][j] = board[l][j];
      }
    }
    for (int i = 0; i < moves.size(); i++) {
      boardc = doMoveC(boardc, moves.get(i));
    }

    return boardc;
  }

  public static int[][] doTurnP(int[][] board, ArrayList<Integer> moves) {
    int[][] boardc = new int[2][7];
    for (int l = 0; l < 2; l++) {
      for (int j = 0; j < 7; j++) {
        boardc[l][j] = board[l][j];
      }
    }
    for (int i = 0; i < moves.size(); i++) {
      boardc = doMoveP(boardc, moves.get(i));
    }

    return boardc;
  }

  public static int[][] doMoveC(int[][] board, int well) {
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

  public static int[][] doMoveP(int[][] board, int well) {
    if (well > 6 || well < 1) {
      return board;
    }
    int[][] boardc = new int[2][7];
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 7; j++) {
        boardc[i][j] = board[i][j];
      }
    }
    int stones = boardc[1][well-1];
    boardc[1][well-1] = 0;
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
        row = 0;
      }
      else {
        row = 1;
      }
      boardc[row][col]++;
      stones--;
    }
    return boardc;
  }

//needs testing
  public static void endGame(int[][] board) {
    System.out.println("------------------------");
    int pScore = 0;
    int oScore = 0;
    for (int i = 0; i < 7; i++) {
      pScore += board[1][i];
      oScore += board[0][i];
    }

    System.out.println("Player Score: " + pScore);
    System.out.println("Opponent Score: " + oScore);
    System.out.println();

    if (pScore > oScore) {
      System.out.println("You win!");
    }
    else if (pScore < oScore) {
      System.out.println("Your opponent wins!");
    }
    else {
      System.out.println("It's a tie!");
    }
  }

  public static void main(String[] args) throws IOException {
    BufferedReader f = new BufferedReader(new InputStreamReader(System.in));
    System.out.println("Input 1 to start game as player 1. Input 2 to start as player 2");//consider explaining rules.
    int[][] board = {
      {4,4,4,4,4,4,0},
      {4,4,4,4,4,4,0}
    };
    for (int i = 0; i < 2; i++) {
      System.out.println(Arrays.toString(board[i]));
    }

    if (f.readLine().equals("1")) {
      boolean done = false;
      while (!done) {
        System.out.println("Your turn! Which well would you like to move?");
        int move = Integer.parseInt(f.readLine());
        int[][] boardc = doMoveP(board,move);
        if (board == boardc) {
          System.out.println("Wells are numbered 1 to 6 (inclusive). You entered " + move + ". Please enter a valid well.");
        }
        else {
          done = true;
        }
        board = boardc;
      }
    }
    else if(!f.readLine().equals("2")) {//change from exception to asking again...
      throw new IllegalArgumentException ("You must enter \"1\" or \"2\" to begin as that player.");
    }

    boolean done = false;
    while (!done) {
      for (int i = 0; i < 2; i++) {
        System.out.println(Arrays.toString(board[i]));
      }


      done = true;
    }

    endGame(board);
  }
}
