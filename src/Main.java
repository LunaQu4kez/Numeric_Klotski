import solve.Solve;
import view.BeginFrame;

import javax.swing.*;
import java.io.IOException;
import java.io.PrintWriter;

public class Main {
    public static void main(String[] args) {
        Solve s = readIn(args);
        SwingUtilities.invokeLater(() -> {
            BeginFrame beginFrame = new BeginFrame(s);
            beginFrame.setVisible(true);
        });
    }

    private static Solve readIn(String[] strs){
        try (PrintWriter out = new PrintWriter("./src/util/input.txt")){
            int m = Integer.parseInt(strs[0]);
            int n = Integer.parseInt(strs[1]);
            out.println(m + " " + n);
            int[][] arr = new int[m][n];
            boolean[] one_one = new boolean[m*n];
            boolean[] one_two = new boolean[m*n];
            boolean[] two_one = new boolean[m*n];
            boolean[] two_two = new boolean[m*n];
            int[] xs = new int[m*n];
            for (int i = 0; i < xs.length; i++) {
                xs[i] = -1;
            }
            int[] ys = new int[m*n];
            for (int i = 0; i < ys.length; i++) {
                ys[i] = -1;
            }

            int cnt = 0;
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    int num = Integer.parseInt(strs[2 + cnt]);
                    cnt++;
                    arr[i][j] = num;
                    xs[num] = i;
                    ys[num] = j;
                    if (num != 0) one_one[num] = true;
                    out.print(num + " ");
                }
                out.println();
            }

            int w = Integer.parseInt(strs[2 + m*n]);
            out.println(w);
            for (int i = 0; i < w; i++) {
                int num = Integer.parseInt(strs[2*i + 3 + m*n]);
                String s = strs[2*i + 4 + m*n];
                out.println(num + " " + s);
                switch (s){
                    case "1*2":
                        one_two[num] = true;
                        one_one[arr[xs[num]][ys[num]]] = false;
                        one_one[arr[xs[num]][ys[num] + 1]] = false;
                        break;
                    case "2*1":
                        two_one[num] = true;
                        one_one[arr[xs[num]][ys[num]]] = false;
                        one_one[arr[xs[num] + 1][ys[num]]] = false;
                        break;
                    case "2*2":
                        two_two[num] = true;
                        one_one[arr[xs[num]][ys[num]]] = false;
                        one_one[arr[xs[num]][ys[num] + 1]] = false;
                        one_one[arr[xs[num] + 1][ys[num]]] = false;
                        one_one[arr[xs[num] + 1][ys[num] + 1]] = false;
                        break;
                }
            }
            return new Solve(arr, m, n, one_one, one_two, two_one, two_two, xs, ys);
        } catch ( IOException e ) {
            e.printStackTrace();
        }
        return null;
    }

    private static void check(Solve solve){
        int m = solve.m;
        int n = solve.n;
        System.out.println("m = " + m);
        System.out.println("n = " + n);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(solve.arr[i][j] + " ");
            }
            System.out.println();
        }

        System.out.print("one_one ");
        for (int i = 0; i < m*n; i++) {
            if (solve.one_one[i]) System.out.print(i + " ");
        }
        System.out.println();
        System.out.print("one_two ");
        for (int i = 0; i < m*n; i++) {
            if (solve.one_two[i]) System.out.print(i + " ");
        }
        System.out.println();
        System.out.print("two_one ");
        for (int i = 0; i < m*n; i++) {
            if (solve.two_one[i]) System.out.print(i + " ");
        }
        System.out.println();
        System.out.print("two_two ");
        for (int i = 0; i < m*n; i++) {
            if (solve.two_two[i]) System.out.print(i + " ");
        }
        System.out.println();

        for (int i = 1; i < m*n; i++) {
            if (solve.xs[i] != -1 && solve.ys[i] != -1){
                System.out.println(i + " (" + solve.xs[i] + ", " + solve.ys[i] + ")");
            }
        }

        System.out.println("================");
    }
}
