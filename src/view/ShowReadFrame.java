package view;

import solve.Solve;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.lang.Thread;

public class ShowReadFrame extends JFrame {
    private static ArrayList<Integer> numbers = new ArrayList<>();
    private ArrayList<Integer> relation = new ArrayList<>();
    private ArrayList<String> size = new ArrayList<>();
    private static ArrayList<JLabel> labelList = new ArrayList<>();
    private boolean b;
    private static int item;
    private static int stepNum;
    private static ArrayList<String> out = new ArrayList<>();

    public void setOut(ArrayList<String> t){
        for (int i = 0; i < t.size(); i++){
            String s = t.get(i);
            out.add(s);
        }
    }

    public ArrayList<String> getOut(){
        return out;
    }

    public void setItem(int t){
        item = t;
    }

    public int getItem(){
        return item;
    }

    public void setStepNum(int t){
        stepNum = t;
    }

    public int getStepNum(){
        return stepNum;
    }

    public ShowReadFrame(Solve solve) throws InterruptedException {
        Random random = new Random();
        this.setTitle("Numeric Klotski");
        this.setLayout(null);
        this.setSize(1080, 540);
        this.setLocationRelativeTo(null);

        try (PrintWriter writer = new PrintWriter("./src/util/output.txt")){
            ArrayList<String> ans = solve.getAns();
            if (ans.size() == 0){
                System.out.println("No");
                writer.println("No");
            } else {
                System.out.println("Yes");
                writer.println("Yes");
                System.out.println(ans.size());
                writer.println(ans.size());
                for (int i = 0; i < ans.size(); i++) {
                    System.out.println(ans.get(i));
                    writer.println(ans.get(i));
                }
            }
        } catch ( IOException ex ) {
            ex.printStackTrace();
        }

        try {
            ArrayList<String> in = ReadIn.read("input");
            int m = Integer.parseInt(in.get(0));
            int n = Integer.parseInt(in.get(1));
            ReadIn.setPieceSizeX(800 / n);
            ReadIn.setPieceSizeY(480 / m);
            int N = m*n;
            int number;
            int x = 0;
            int y = 10;
            JLabel[] labels = new JLabel[N];
            for (int i = 2; i < N + 2; i++){
                number=Integer.parseInt(in.get(i));
                numbers.add(number);

                JLabel p=new JLabel(String.valueOf(number),JLabel.CENTER);
                p.setSize(ReadIn.getPieceSizeX(), ReadIn.getPieceSizeY());
                p.setLocation(x,y);
                p.setLayout(null);
                p.setVisible(true);
                p.setFont(new Font("Calibri", Font.BOLD, 30));
                p.setOpaque(true);
                p.setBackground(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
                if (number==0){
                    p.setOpaque(false);
                    p.setText("");
                }
                add(p);
                labels[i-2]=p;

                x+= ReadIn.getPieceSizeX();
                if (x+ ReadIn.getPieceSizeX()>800){
                    x=0;
                    y+= ReadIn.getPieceSizeY();
                }
            }

            for (int j = 0; j < labels.length; j++){
                labelList.add(labels[j]);
            }

            int N2=Integer.parseInt(in.get(N + 2));
            int r;
            for (int j = 0; j < N2; j++){
                r=Integer.parseInt(in.get(N+3+j*2));
                relation.add(r);
                size.add(in.get(N+j*2+4));

                if(in.get(N+j*2+4).equals("2*1")){
                    labels[numbers.indexOf(r)].setBackground(labels[numbers.indexOf(r)+n].getBackground());
                }
                if(in.get(N+j*2+4).equals("1*2")){
                    labels[numbers.indexOf(r)].setBackground(labels[numbers.indexOf(r)+1].getBackground());
                }
                if(in.get(N+j*2+4).equals("2*2")){
                    labels[numbers.indexOf(r)].setBackground(labels[numbers.indexOf(r)+n].getBackground());
                    labels[numbers.indexOf(r)+1].setBackground(labels[numbers.indexOf(r)+n].getBackground());
                    labels[numbers.indexOf(r)+n+1].setBackground(labels[numbers.indexOf(r)+n].getBackground());
                }
            }


            ImageIcon beginPic = new ImageIcon("./src/pic/beginShow.png");
            JLabel beginBtn = new JLabel(beginPic);
            beginBtn.setSize(250, 120);
            beginBtn.setLocation(800,10);
            beginBtn.setVisible(true);
            add(beginBtn);

            ImageIcon nextPic = new ImageIcon("./src/pic/next.png");
            JLabel nextBtn = new JLabel(nextPic);
            nextBtn.setSize(300, 120);
            nextBtn.setLocation(800,10);
            nextBtn.setVisible(false);
            add(nextBtn);

            beginBtn.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {

                    try {
                        ArrayList<String> t = ReadIn.read("output");
                        setOut(t);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                    if(getOut().get(0).equals("No")){
                        JOptionPane.showMessageDialog(null,"No Solution");
                        System.exit(0);
                    } else if (getOut().get(0).equals("Yes")){
                        setStepNum(Integer.parseInt(getOut().get(1)));
                        setItem(2);
                        int p = numbers.indexOf(Integer.parseInt(getOut().get(getItem())));
                        int q = numbers.indexOf(Integer.parseInt(getOut().get(getItem())))+n;
                        String s = out.get(getItem() + 1);
                        if (relation.contains(Integer.parseInt(getOut().get(getItem())))){
                            if (size.get(relation.indexOf(Integer.parseInt(getOut().get(getItem())))).equals("2*1")){
                                move(labels[p],labels[q],s);
                            }else if (size.get(relation.indexOf(Integer.parseInt(getOut().get(getItem())))).equals("1*2")){
                                move(labels[p],labels[p + 1],out.get(getItem() + 1));
                            }else if (size.get(relation.indexOf(Integer.parseInt(getOut().get(getItem())))).equals("2*2")){
                                move(labels[p],labels[q],labels[p + 1],labels[q + 1],out.get(getItem() + 1));
                            }
                        }else {
                            move(labels[p],out.get(getItem() + 1));
                        }
                    }
                    beginBtn.setVisible(false);
                    nextBtn.setVisible(true);
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

            nextBtn.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    try {
                        setItem(getItem()+2);
                        int p=numbers.indexOf(Integer.parseInt(getOut().get(getItem())));
                        int q=numbers.indexOf(Integer.parseInt(getOut().get(getItem())))+n;
                        String s=out.get(getItem()+1);
                        if (relation.contains(Integer.parseInt(getOut().get(getItem())))){
                            if (size.get(relation.indexOf(Integer.parseInt(getOut().get(getItem())))).equals("2*1")){
                                move(labels[p],labels[q],s);
                            }else if (size.get(relation.indexOf(Integer.parseInt(getOut().get(getItem())))).equals("1*2")){
                                move(labels[p],labels[p+1],out.get(getItem()+1));
                            }else if (size.get(relation.indexOf(Integer.parseInt(getOut().get(getItem())))).equals("2*2")){
                                move(labels[p],labels[q],labels[p+1],labels[q+1],out.get(getItem()+1));
                            }
                        }else {
                            move(labels[p],out.get(getItem()+1));
                        }
                        if (getItem()==getStepNum()*2){
                            nextBtn.setVisible(false);
                        }
                    } catch (Exception ex){
                        nextBtn.setVisible(false);
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

            ImageIcon backgroundPicture = new ImageIcon("./src/pic/background2.png");
            JLabel backgroundLabel = new JLabel(backgroundPicture);
            backgroundLabel.setSize(1080, 500);
            backgroundLabel.setLocation(0,0);
            add(backgroundLabel);

            this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static Thread thread;
    public void move(JLabel label,String direction){
            thread=new Thread(new Runnable() {
                @Override
                public void run() {
                    int x=label.getBounds().x;
                    int y=label.getBounds().y;
                    if (direction.equals("R")){
                        while (label.getBounds().x<x+ ReadIn.getPieceSizeX()){
                            label.setLocation(label.getBounds().x+1,label.getBounds().y);
                            try {
                                Thread.sleep(1);
                            }catch (InterruptedException e){
                                e.printStackTrace();
                            }
                        }
                    }
                    if (direction.equals("L")){
                        while (label.getBounds().x>x- ReadIn.getPieceSizeX()){
                            label.setLocation(label.getBounds().x-1,label.getBounds().y);
                            try {
                                Thread.sleep(1);
                            }catch (InterruptedException e){
                                e.printStackTrace();
                            }
                        }
                    }
                    if (direction.equals("U")){
                        while (label.getBounds().y>y- ReadIn.getPieceSizeY()){
                            label.setLocation(label.getBounds().x,label.getBounds().y-1);
                            try {
                                Thread.sleep(1);
                            }catch (InterruptedException e){
                                e.printStackTrace();
                            }
                        }
                    }
                    if (direction.equals("D")){
                        while (label.getBounds().y<y+ ReadIn.getPieceSizeY()){
                            label.setLocation(label.getBounds().x,label.getBounds().y+1);
                            try {
                                Thread.sleep(1);
                            }catch (InterruptedException e){
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });
            thread.start();
        }
    public void move(JLabel label1,JLabel label2,String direction){
        thread=new Thread(new Runnable() {
            @Override
            public void run() {
                int x1=label1.getBounds().x;
                int y1=label1.getBounds().y;
                if (direction.equals("R")){
                    while (label1.getBounds().x<x1+ ReadIn.getPieceSizeX()){
                        label1.setLocation(label1.getBounds().x+1,label1.getBounds().y);
                        label2.setLocation(label2.getBounds().x+1,label2.getBounds().y);
                        try {
                            Thread.sleep(1);
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                    }
                    b=true;
                }
                if (direction.equals("L")){
                    while (label1.getBounds().x>x1- ReadIn.getPieceSizeX()){
                        label1.setLocation(label1.getBounds().x-1,label1.getBounds().y);
                        label2.setLocation(label2.getBounds().x-1,label2.getBounds().y);
                        try {
                            Thread.sleep(1);
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                    }
                }
                if (direction.equals("U")){
                    while (label1.getBounds().y>y1- ReadIn.getPieceSizeY()){
                        label1.setLocation(label1.getBounds().x,label1.getBounds().y-1);
                        label2.setLocation(label2.getBounds().x,label2.getBounds().y-1);
                        try {
                            Thread.sleep(1);
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                    }
                }
                if (direction.equals("D")){
                    while (label1.getBounds().y<y1+ ReadIn.getPieceSizeY()){
                        label1.setLocation(label1.getBounds().x,label1.getBounds().y+1);
                        label2.setLocation(label2.getBounds().x,label2.getBounds().y+1);
                        try {
                            Thread.sleep(1);
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        thread.start();
    }
    public void move(JLabel label1,JLabel label2,JLabel label3,JLabel label4,String direction){
        thread=new Thread(new Runnable() {
            @Override
            public void run() {
                int x1=label1.getBounds().x;
                int y1=label1.getBounds().y;
                if (direction.equals("R")){
                    while (label1.getBounds().x<x1+ ReadIn.getPieceSizeX()){
                        label1.setLocation(label1.getBounds().x+1,label1.getBounds().y);
                        label2.setLocation(label2.getBounds().x+1,label2.getBounds().y);
                        label3.setLocation(label3.getBounds().x+1,label3.getBounds().y);
                        label4.setLocation(label4.getBounds().x+1,label4.getBounds().y);
                        try {
                            Thread.sleep(1);
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                    }
                }
                if (direction.equals("L")){
                    while (label1.getBounds().x>x1- ReadIn.getPieceSizeX()){
                        label1.setLocation(label1.getBounds().x-1,label1.getBounds().y);
                        label2.setLocation(label2.getBounds().x-1,label2.getBounds().y);
                        label3.setLocation(label3.getBounds().x-1,label3.getBounds().y);
                        label4.setLocation(label4.getBounds().x-1,label4.getBounds().y);
                        try {
                            Thread.sleep(1);
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                    }
                }
                if (direction.equals("U")){
                    while (label1.getBounds().y>y1- ReadIn.getPieceSizeY()){
                        label1.setLocation(label1.getBounds().x,label1.getBounds().y-1);
                        label2.setLocation(label2.getBounds().x,label2.getBounds().y-1);
                        label3.setLocation(label3.getBounds().x,label3.getBounds().y-1);
                        label4.setLocation(label4.getBounds().x,label4.getBounds().y-1);
                        try {
                            Thread.sleep(1);
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                    }
                }
                if (direction.equals("D")){
                    while (label1.getBounds().y<y1+ ReadIn.getPieceSizeY()){
                        label1.setLocation(label1.getBounds().x,label1.getBounds().y+1);
                        label2.setLocation(label2.getBounds().x,label2.getBounds().y+1);
                        label3.setLocation(label3.getBounds().x,label3.getBounds().y+1);
                        label4.setLocation(label4.getBounds().x,label4.getBounds().y+1);
                        try {
                            Thread.sleep(1);
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        thread.start();
    }
}