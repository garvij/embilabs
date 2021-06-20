package com.embilabs.assignment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AssignmentApp {

  public static void main(String[] args) {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    try {
      int tc = Integer.parseInt(br.readLine());
      while (tc > 0) {
        int n = Integer.parseInt(br.readLine());
        int[] cheese = new int[n];
        String[] cheeseStrArr = br.readLine().split(" ");
        for (int i = 0; i < n; i++) {
          cheese[i] = Integer.parseInt(cheeseStrArr[i]);
        }
        System.out.println(maxCheeseMemoized(n, cheese));
        tc--;
      }
    } catch (IOException | NumberFormatException ipException) {
      System.out.println("Invalid input!");
      ipException.printStackTrace();
    }
  }

  private static int maxCheeseMemoized(int n, int[] cheese) {
    Integer[][] memoize = new Integer[n][2];
    return Math.max(recursiveMemoized(n, cheese, 0, false, memoize),
        recursiveMemoized(n, cheese, 0, true, memoize));
  }

  private static int recursiveMemoized(int n, int[] cheese, int idx, boolean previousEaten,
      Integer[][] memoize) {
    // base condition
    if (idx == cheese.length) {
      return 0;
    }

    if (memoize[idx][previousEaten ? 1 : 0] != null) {
      return memoize[idx][previousEaten ? 1 : 0];
    }

    // if previous block is eaten we cannot eat current cheese block
    if (previousEaten) {
      memoize[idx][1] = recursiveMemoized(n, cheese, idx + 1, false, memoize);
      return memoize[idx][1];
    }

    // if previous block is not eaten we have two choices
    // 1. eat current cheese block
    memoize[idx][0] =
        // 1. eat current cheese block
        Math.max(recursiveMemoized(n, cheese, idx + 1, true, memoize) + cheese[idx],
            // 2. ignore current cheese block
            recursiveMemoized(n, cheese, idx + 1, false, memoize));
    return memoize[idx][0];
  }


  private static int maxCheese(int n, int[] cheese) {
    return Math.max(recursive(n, cheese, 0, false),
        recursive(n, cheese, 0, true));
  }

  private static int recursive(int n, int[] cheese, int idx, boolean previousEaten) {
    // base condition
    if (idx == cheese.length) {
      return 0;
    }

    // if previous block is eaten we cannot eat current cheese block
    if (previousEaten) {
      return recursive(n, cheese, idx + 1, false);
    }

    // if previous block is not eaten we have two choices
    return
        Math.max(recursive(n, cheese, idx + 1, true) + cheese[idx], // 1. eat current cheese block
            recursive(n, cheese, idx + 1, false)); // 2. ignore current cheese block
        /*
    Since we have two recursive calls, the time complexity is 2^n which can be brought down to n^2
    using memoization(top-down) or iteration(bottom-up) approach
     */
  }


}
