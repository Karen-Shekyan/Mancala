import java.util.*;
import java.io.*;

public class game {
  //board has 2 rows and 7 columns.
//ADD BAD INPUT DETECTION TO PLAYER INPUT WHEN PLAYING=================================================
  //update this************************************************************************************************************************************************
  public static int evaluate(int[][] board) {
    int value = 0;
    boolean endGame = true;
    for (int i = 0; i < 7; i++) {
      int c = board[0][i];
      int p = board[1][i];
      if (i == 6) {
        value += 2*c;
        value -= 2*p;
      }
      else {
        value += c;
        value -= p;
      }

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
        int[][] newboard = doTurnC(board, turns.get(i));//THIS METHOD RETURNS A COPY!;
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
      int index = 0;
      for (int i = 0; i < turns.size(); i++) {
        int[][] newboard = doTurnP(board, turns.get(i));//THIS METHOD RETURNS A COPY!;
        int value = value(newboard,depth+1,maxDepth);
        if (value < min) {
          min = value;
          index = i;
        }
      }
      return min;
    }
  }

  public static ArrayList<Integer> findTurn(ArrayList<ArrayList<Integer>> turns, int[][] board, int maxDepth) {
    int max = Integer.MIN_VALUE;
    int index = 0;
    for (int i = 0; i < turns.size(); i++) {
      int[][] newboard = doTurnC(board, turns.get(i));//THIS METHOD RETURNS A COPY!;
      int value = value(newboard, 1, maxDepth);
      if (value > max) {
        max = value;
        index = i;
      }
    }

    return turns.get(index);
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
      stones--;
      board[row][col]++;

      if (stones == 0) {
        if (col != 6 && row == 0 && board[row][col] == 1 && board[1][5-col] != 0) {
          board[row][6] = board[row][6] + 1 + board[1][5-col];
          board[1][5-col] = 0;
          board[row][col] = 0;
        }
      }
    }
    return board;
  }

  public static int[][] doMoveP(int[][] board, int well) {
    int stones = board[1][well-1];
    board[1][well-1] = 0;
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
      board[row][col]++;
      stones--;

      if (stones == 0) {
        if (col != 6 && row == 1 && board[row][col] == 1 && board[0][5-col] != 0) {
          board[row][6] = board[row][6] + 1 + board[0][5-col];
          board[0][5-col] = 0;
          board[row][col] = 0;
        }
      }
    }
    return board;
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

//returns maxDepth. returns -1 for invalid input.
//adjust the output values (currently 2/4/6).
  public static int difficulty(String input) {
    if (input.equals("1")) {
      return 2;
    }
    else if (input.equals("2")) {
      return 4;
    }
    else if (input.equals("3")) {
      return 8;
    }
    else {
      System.out.println("Please enter a valid difficulty.");
      return -1;
    }
  }
//-1 for invalid (any reason). 0 for end of turn. 1 for extra move.
  public static int playerTurn(String input, int[][] board) {
    int well = Integer.parseInt(input);
    if (well > 6 || well < 1 ||( board[1][well-1] == 0)) {
      return -1;
    }
    ArrayList<Integer> moves = new ArrayList<Integer>();
    moves.add(well);
    if (extraTurnP(moves,board)) {
      return 1;
    }
    else {
      return 0;
    }
  }

  public static void main(String[] args) throws IOException {
    BufferedReader f = new BufferedReader(new InputStreamReader(System.in));

    System.out.println("Welcome to Mancala. Please input 1 to play on easy, 2 to play on medium, or 3 to play on hard.");
    String input = "";
    int maxDepth = -1;
    while (maxDepth == -1) {
      input = f.readLine();
      maxDepth = difficulty(input);
    }

    int[][] board = {
      {4,4,4,4,4,4,0},
      {4,4,4,4,4,4,0}
    };

    System.out.println("Input 1 to start as player 1 or 2 to start as player 2.");

    boolean done = false;
    while (!done) {
      input = f.readLine();
      if (input.equals("1")) {
        System.out.println("Your turn!");
        for (int i = 6; i >= 0; i--) {
          System.out.printf("%2d ", board[0][i]);
        }
        System.out.print("\n   ");
        for (int i = 0; i < 7; i++) {
          System.out.printf("%2d ", board[1][i]);
        }
        System.out.println();
        int P = 1;
        while (P != 0) {
          input = f.readLine();
          P = playerTurn(input,board);
          if (P == -1) {
            System.out.println("Wells are numbered 1 to 6 (inclusive) and must contain at least 1 stone to be a valid move. Your input: " + input);
          }
          if (P == 1) {
            System.out.println("Extra turn!");
          }

          for (int i = 6; i >= 0; i--) {
            System.out.printf("%2d ", board[0][i]);
          }
          System.out.print("\n   ");
          for (int i = 0; i < 7; i++) {
            System.out.printf("%2d ", board[1][i]);
          }
          System.out.println();
        }
        done = true;
      }

      else if (input.equals("2")) {
        done = true;
      }

      else {
        System.out.println("Please input either 1 to start as player 1 or 2 to start as player 2. Your input: " + input);
      }
    }

    done = false;
    while (!done) {
      System.out.println("Opponent's turn!");
      ArrayList<Integer> turn = findTurn(moveSetC(board), board, maxDepth);
      for (int j = 0; j < turn.size(); j++) {
        board = doMoveC(board,turn.get(j));

        for (int i = 6; i >= 0; i--) {
          System.out.printf("%2d ", board[0][i]);
        }
        System.out.print("\n   ");
        for (int i = 0; i < 7; i++) {
          System.out.printf("%2d ", board[1][i]);
        }
        System.out.println();

        if (j != turn.size()-1) {
          System.out.println("Extra turn!");
        }
      }

      System.out.println("Your turn!");

      for (int i = 6; i >= 0; i--) {
        System.out.printf("%2d ", board[0][i]);
      }
      System.out.print("\n   ");
      for (int i = 0; i < 7; i++) {
        System.out.printf("%2d ", board[1][i]);
      }
      System.out.println();

      int P = 1;
      while (P != 0) {
        input = f.readLine();
        P = playerTurn(input,board);
        if (P == -1) {
          System.out.println("Wells are numbered 1 to 6 (inclusive) and must contain at least 1 stone to be a valid move. Your input: " + input);
        }
        if (P == 1) {
          System.out.println("Extra turn!");
        }

        for (int i = 6; i >= 0; i--) {
          System.out.printf("%2d ", board[0][i]);
        }
        System.out.print("\n   ");
        for (int i = 0; i < 7; i++) {
          System.out.printf("%2d ", board[1][i]);
        }
        System.out.println();
      }

      boolean donec = true;
      boolean donep = true;
      for (int i = 0; i < 6; i++) {
        if (board[0][i] != 0) {
          donec = false;
        }
        if (board[1][i] != 0) {
          donep = false;
        }
      }
      done = donec || donep;
    }

    endGame(board);
  }
}
