package view;

import solve.Solve;

import javax.swing.*;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

public class BeginFrame extends JFrame{
    private int model;
    private Solve solve;

    public BeginFrame(Solve s){
        solve = s;
        model = 1;

        this.setTitle("Numeric Klotski");
        this.setLayout(null);
        this.setSize(1080, 540);
        this.setLocationRelativeTo(null);

        ImageIcon beginPic = new ImageIcon("./src/pic/beginShow.png");
        JLabel beginBtn = new JLabel(beginPic);
        beginBtn.setSize(250, 120);
        beginBtn.setLocation(550,150);
        add(beginBtn);

        ImageIcon generatePic = new ImageIcon("./src/pic/generateData.png");
        JLabel generateBtn = new JLabel(generatePic);
        generateBtn.setSize(250, 120);
        generateBtn.setLocation(200,150);
        add(generateBtn);

        beginBtn.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (model == 1){
                    try {
                        ShowReadFrame showReadFrame = new ShowReadFrame(solve);
                        showReadFrame.setVisible(true);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                } else if (model == 2){
                    try {
                        ShowReadFrame showReadFrame = new ShowReadFrame(solve);
                        showReadFrame.setVisible(true);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        generateBtn.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                random();
                model = 2;
                beginBtn.setVisible(true);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        ImageIcon backgroundPicture = new ImageIcon("./src/pic/background2.png");
        JLabel backgroundLabel = new JLabel(backgroundPicture);
        backgroundLabel.setSize(1080, 500);
        backgroundLabel.setLocation(0,0);
        add(backgroundLabel);

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void random(){
        Random r = new Random();
        int[] can2_2 = new int[]{1, 2, 3, 5, 6, 7};
        int[] can1_2 = new int[]{1, 2, 3, 5, 6, 7, 9, 10, 11};
        int[] can2_1 = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        boolean[] can = new boolean[14];
        for (int i = 0; i < can.length; i++) {
            can[i] = true;
        }
        int x = can2_2[r.nextInt(6)];
        can[x] = false;
        can[x + 1] = false;
        can[x + 4] = false;
        can[x + 5] = false;

        int y;
        while (true){
            y = can1_2[r.nextInt(9)];
            if (can[y] && can[y + 1]){
                can[y] = false;
                can[y + 1] = false;
                break;
            }
        }

        int z;
        while (true){
            z = can2_1[r.nextInt(9)];
            if (can[z] && can[z + 4]){
                can[z] = false;
                can[z + 4] = false;
                break;
            }
        }

        String[] strs = new String[]{
                "4", "4",
                "1", "2", "3", "4",
                "5", "6", "7", "8",
                "9", "10", "11", "12",
                "13", "0", "0", "0",
                "3",
                x + "", "2*2",
                y + "", "1*2",
                z + "", "2*1"
        };

        Solve solve = readIn(strs);
        solve.shuffle(x, y, z);
        this.solve = solve;
    }

    private static Solve readIn(String[] strs){
        int m = Integer.parseInt(strs[0]);
        int n = Integer.parseInt(strs[1]);
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
            }
        }
        int w = Integer.parseInt(strs[2 + m*n]);
        for (int i = 0; i < w; i++) {
            int num = Integer.parseInt(strs[2*i + 3 + m*n]);
            String s = strs[2*i + 4 + m*n];
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
    }
}


