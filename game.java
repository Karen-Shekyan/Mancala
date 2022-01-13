import java.util.*;
import java.io.*;

public class game {
  //consider making board a global variable.
  //board has 2 rows and 7 columns.
  public static ArrayList<Integer> evaluate(int[][] board) {
    int value = 0;
    //detect a win and return int max.
    //detect a loss and return int min.
    for (int i = 0; i < 7; i++) {
      value += board[0][i];
      value -= board[1][i];
    }

    ArrayList<Integer> values = new ArrayList<Integer>();
    values.add(value);
    return values;
  }

  public static ArrayList<Integer> max (ArrayList<ArrayList<Integer>> moves) {
    ArrayList<Integer> ans = max.get(0);
    for (int i = 1; i < moves.size(); i++) {
      if ( > ) {
        max = moves.get(i);
      }
    }
    return ans;
  }

  //This structure is remarkably similar to the calculator the one used in polynomial evaluation (if written w/o recursion).
  //Though recursion has the same time/space complexity, it allows for pruning and is a "simple" dfs (and is neater).
  //depth starts at 0. maxDepth is the turns ahead it looks. Even is max, odd is min.
  // public static ArrayList<Integer> findTurn(int[][] board, int depth, int maxDepth) {
  //   if (depth == maxDepth) {
  //     return evaluate(board);
  //   }
  //
  //   else {
  //     int upvalue;
  //     if (depth%2 == 0) {
  //       upvalue = Integer.MIN_VALUE;
  //     }
  //     else {
  //       upvalue = Integer.MAX_VALUE;
  //     }
  //
  //     ArrayList<ArrayList<Integer>> firstMoves = new ArrayList<ArrayList<Integer>>();
  //
  //     for (int i = 1; i < 7; i++) {
  //       if (board[0][i-1] != 0) {
  //         //do the move ArrayList thing.
  //         int[][] boardc = new int[2][7];
  //         for (int i = 0; i < 2; i++) {
  //           for (int j = 0; j < 7; j++) {
  //             boardc[i][j] = board[i][j];
  //           }
  //         }
  //         boardc = doTurnC(boardc,i);
  //
  //         //check around this ^ line for extra turn.
  //         int n = findTurn(boardc,depth+1,maxDepth).get(0);
  //
  //         if (depth%2 == 0) {
  //           if (n > upvalue) {
  //             upvalue = n;
  //           }
  //         }
  //         else {
  //           if (n < upvalue) {
  //             upvalue = n;
  //           }
  //         }
  //
  //       }
  //     }
  //
  //   }
  //
  //   return max(moves);
  // }

// //maybe make this check for invalid turns?...
//   public static boolean extraTurn(int[][] board, ArrayList<Integer> wells) {
//     int[][] boardc = new int[2][7];
//     for (int i = 0; i < 2; i++) {
//       for (int j = 0; j < 7; j++) {
//         boardc[i][j] = board[i][j];
//       }
//     }
//
//     for (int i = 0; i < wells.size()-1; i++) {
//       boardc = doTurnC(boardc,wells.get(i));
//     }
//
//     int well = wells.get(wells.size()-1);
//     return (boardc[well-1]%13 == 7-well);
//   }
// //...instead of this.
//   public static ArrayList<ArrayList<Integer>> genMove(int[][] board) {
//     ArrayList<ArrayList<Integer>> moves = new ArrayList<Integer>();
//     for (int i = 1; i < 7; i++) {
//       ArrayList<Integer> move = new ArrayList<Integer>();
//       if (board[0][i-1] != 0) {
//         move.add(i);
//       }
//       moves.add(move);
//     }
//
//     boolean done = false;
//     while (!done) {
//       for (int i = 0; i < moves.size(); i++) {
//         if (extraTurn(board,moves.get(i))) {
//           ArrayList<Integer> move = new ArrayList<Integer>();
//           for (int j = 0; j < moves.get(i).size(); j++) {
//             move.add(moves.get(i).get(j));
//           }
//           moves.remove(i)
//           i--;
//           for (int j = 1; j < 7; j++) {
//             if (board)
//           }
//         }
//       }
//     }
//   }

  public static ArrayList<Integer> moveSet(int[][] board) {
    ArrayList<ArrayList<Integer>> allFirstMoves = new ArrayList<ArrayList<Integer>>();
    for (int i = 1; i < 7; i++) {
      
    }
  }

//Computer always moves row 0. The player going first only changes the search space, not what the algorithm does.
//Consider making this take an ArrayList of moves.
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

  public static int[][] doTurnP(int[][] board, int well) {
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
        int[][] boardc = doTurnP(board,move);
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
