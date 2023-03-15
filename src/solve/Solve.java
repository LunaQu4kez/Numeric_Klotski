package solve;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Stack;

public class Solve {
    public int[][] arr;
    public final int m;
    public final int n;
    public final boolean[] one_one;
    public final boolean[] one_two;
    public final boolean[] two_one;
    public final boolean[] two_two;
    public int[] xs;
    public int[] ys;

    Stack<Step> steps;
    HashMap<Pair, Integer> hasChecked;
    boolean isSolved;

    int cnt = 1;

    public Solve(int[][] arr, int m, int n, boolean[] one_one, boolean[] one_two, boolean[] two_one, boolean[] two_two, int[] xs, int[] ys) {
        this.arr = arr;
        this.m = m;
        this.n = n;
        this.one_one = one_one;
        this.one_two = one_two;
        this.two_one = two_one;
        this.two_two = two_two;
        this.xs = xs;
        this.ys = ys;

        steps = new Stack<>();
        hasChecked = new HashMap<>();
        isSolved = false;
    }

    private void search(){
        ArrayList<Pair> list = new ArrayList<>();
        list.add(new Pair(arr, null, null));
        bfsSearch(list);
    }

    private void bfsSearch(ArrayList<Pair> list){
        if (isSolved) return;
        ArrayList<Pair> newList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            arr = list.get(i).arr;
            for (int j = 0; j < m; j++) {
                for (int k = 0; k < n; k++) {
                    int num = arr[j][k];
                    xs[num] = j;
                    ys[num] = k;
                }
            }
            ArrayList<Step> canMoveStep = getCanMoveSteps();
            for (int j = 0; j < canMoveStep.size(); j++) {
                Step step = canMoveStep.get(j);
                move(step);
                steps.push(step);
                if (isSolved()){
                    isSolved = true;
                    Pair temp = list.get(i);
                    while (true){
                        if (temp.step != null){
                            steps.push(temp.step);
                        }
                        temp = temp.pre;
                        if (temp == null || temp.pre == null){
                            break;
                        }
                    }
                    return;
                } else {
                    if (!hasChecked()){
                        newList.add(new Pair(arrayClone(arr), step, list.get(i)));
                        hasChecked.put(new Pair(arrayClone(arr), null, null), 1);
                    }
                    moveBack(step);
                    steps.pop();
                }
            }
        }

//        System.out.println(hasChecked.size());
//        System.out.println(cnt++);

        if (newList.isEmpty()) return;
        bfsSearch(newList);
    }

    private ArrayList<Step> getCanMoveSteps(int[][] arr){
        int m = arr.length;
        int n = arr[0].length;
        ArrayList<Step> list = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int num = arr[i][j];
                if (num == 0) continue;
                if (canMoveUp(num)){
                    Step step = new Step(num, 'U');
                    move(step);
                    if (!hasChecked()){
                        list.add(step);
                    }
                    moveBack(step);
                }
                if (canMoveDown(num)){
                    Step step = new Step(num, 'D');
                    move(step);
                    if (!hasChecked()){
                        list.add(step);
                    }
                    moveBack(step);
                }
                if (canMoveLeft(num)){
                    Step step = new Step(num, 'L');
                    move(step);
                    if (!hasChecked()){
                        list.add(step);
                    }
                    moveBack(step);
                }
                if (canMoveRight(num)){
                    Step step = new Step(num, 'R');
                    move(step);
                    if (!hasChecked()){
                        list.add(step);
                    }
                    moveBack(step);
                }
            }
        }
        return list;
    }

    private ArrayList<Step> getCanMoveSteps(){
        return getCanMoveSteps(arr);
    }

    private boolean canMoveUp(int num) {
        int x = xs[num];
        int y = ys[num];
        Type type = getType(num);
        if (x <= 0){
            return false;
        }
        if (type == Type.ONE_ONE || type == Type.TWO_ONE){
            return arr[x - 1][y] == 0;
        }
        if (type == Type.ONE_TWO || type == Type.TWO_TWO){
            return arr[x - 1][y] == 0 && arr[x - 1][y + 1] == 0;
        }
        return false;
    }

    private boolean canMoveDown(int num) {
        int x = xs[num];
        int y = ys[num];
        Type type = getType(num);
        if (x >= m - 1){
            return false;
        }
        if (type == Type.ONE_ONE){
            return arr[x + 1][y] == 0;
        }
        if (type == Type.TWO_ONE){
            if (x >= m - 2){
                return false;
            }
            return arr[x + 2][y] == 0;
        }
        if (type == Type.ONE_TWO){
            return arr[x + 1][y] == 0 && arr[x + 1][y + 1] == 0;
        }
        if (type == Type.TWO_TWO){
            if (x >= m - 2){
                return false;
            }
            return arr[x + 2][y] == 0 && arr[x + 2][y + 1] == 0;
        }
        return false;
    }

    private boolean canMoveLeft(int num) {
        int x = xs[num];
        int y = ys[num];
        Type type = getType(num);
        if (y <= 0){
            return false;
        }
        if (type == Type.ONE_ONE || type == Type.ONE_TWO){
            return arr[x][y - 1] == 0;
        }
        if (type == Type.TWO_ONE || type == Type.TWO_TWO){
            return arr[x][y - 1] == 0 && arr[x + 1][y - 1] == 0;
        }
        return false;
    }

    private boolean canMoveRight(int num) {
        int x = xs[num];
        int y = ys[num];
        Type type = getType(num);
        if (y >= n - 1){
            return false;
        }
        if (type == Type.ONE_ONE){
            return arr[x][y + 1] == 0;
        }
        if (type == Type.ONE_TWO){
            if (y >= n - 2){
                return false;
            }
            return arr[x][y + 2] == 0;
        }
        if (type == Type.TWO_ONE){
            return arr[x][y + 1] == 0 && arr[x + 1][y + 1] == 0;
        }
        if (type == Type.TWO_TWO){
            if (y >= n - 2){
                return false;
            }
            return arr[x][y + 2] == 0 && arr[x + 1][y + 2] == 0;
        }
        return false;
    }

    private void move(Step s){
        int num = s.num;
        char direction = s.direction;
        if (direction == 'U'){
            moveUp(num);
        } else if (direction == 'D'){
            moveDown(num);
        } else if (direction == 'L'){
            moveLeft(num);
        } else if (direction == 'R'){
            moveRight(num);
        }
        //Main.check(this);
    }

    private void moveUp(int num) {
        int x = xs[num];
        int y = ys[num];
        Type type = getType(num);
        if (type == Type.ONE_ONE){
            exch(arr[x][y], x - 1, y);
        }
        if (type == Type.TWO_ONE){
            exch(arr[x][y], x - 1, y);
            exch(arr[x + 1][y], x, y);
        }
        if (type == Type.ONE_TWO){
            exch(arr[x][y], x - 1, y);
            exch(arr[x][y + 1], x - 1, y + 1);
        }
        if (type == Type.TWO_TWO){
            exch(arr[x][y], x - 1, y);
            exch(arr[x + 1][y], x, y);
            exch(arr[x][y + 1], x - 1, y + 1);
            exch(arr[x + 1][y + 1], x, y + 1);
        }
    }

    private void moveDown(int num) {
        int x = xs[num];
        int y = ys[num];
        Type type = getType(num);
        if (type == Type.ONE_ONE){
            exch(arr[x][y], x + 1, y);
        }
        if (type == Type.TWO_ONE){
            exch(arr[x + 1][y], x + 2, y);
            exch(arr[x][y], x + 1, y);
        }
        if (type == Type.ONE_TWO){
            exch(arr[x][y], x + 1, y);
            exch(arr[x][y + 1], x + 1, y + 1);
        }
        if (type == Type.TWO_TWO){
            exch(arr[x + 1][y], x + 2, y);
            exch(arr[x][y], x + 1, y);
            exch(arr[x + 1][y + 1], x + 2, y + 1);
            exch(arr[x][y + 1], x + 1, y + 1);
        }
    }

    private void moveLeft(int num) {
        int x = xs[num];
        int y = ys[num];
        Type type = getType(num);
        if (type == Type.ONE_ONE){
            exch(arr[x][y], x, y - 1);
        }
        if (type == Type.TWO_ONE){
            exch(arr[x][y], x, y - 1);
            exch(arr[x + 1][y], x + 1, y - 1);
        }
        if (type == Type.ONE_TWO){
            exch(arr[x][y], x, y - 1);
            exch(arr[x][y + 1], x, y);
        }
        if (type == Type.TWO_TWO){
            exch(arr[x][y], x, y - 1);
            exch(arr[x][y + 1], x, y);
            exch(arr[x + 1][y], x + 1, y - 1);
            exch(arr[x + 1][y + 1], x + 1, y);
        }
    }

    private void moveRight(int num) {
        int x = xs[num];
        int y = ys[num];
        Type type = getType(num);
        if (type == Type.ONE_ONE){
            exch(arr[x][y], x, y + 1);
        }
        if (type == Type.TWO_ONE){
            exch(arr[x][y], x, y + 1);
            exch(arr[x + 1][y], x + 1, y + 1);
        }
        if (type == Type.ONE_TWO){
            exch(arr[x][y + 1], x, y + 2);
            exch(arr[x][y], x, y + 1);
        }
        if (type == Type.TWO_TWO){
            exch(arr[x][y + 1], x, y + 2);
            exch(arr[x][y], x, y + 1);
            exch(arr[x + 1][y + 1], x + 1, y + 2);
            exch(arr[x + 1][y], x + 1, y + 1);
        }
    }

    private void exch(int a, int x, int y) {
        arr[xs[a]][ys[a]] = 0;
        arr[x][y] = a;
        xs[a] = x;
        ys[a] = y;
    }

    private void moveBack(Step s){
        int num = s.num;
        char direction = s.direction;
        if (direction == 'U'){
            move(new Step(num, 'D'));
        } else if (direction == 'D'){
            move(new Step(num, 'U'));
        } else if (direction == 'L'){
            move(new Step(num, 'R'));
        } else if (direction == 'R'){
            move(new Step(num, 'L'));
        }
        //Main.check(this);
    }

    private boolean hasChecked(){
        int[][] arr = new int[this.arr.length][this.arr[0].length];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                arr[i][j] = this.arr[i][j];
            }
        }
        Pair p = new Pair(arr, null, null);
        //System.out.println(hasChecked.get(p) != null);
        return hasChecked.get(p) != null;
    }

    private static boolean twoDEquals(int[][] a1, int[][] a2){
        for (int i = 0; i < a1.length; i++) {
            for (int j = 0; j < a1[0].length; j++) {
                if (a1[i][j] != a2[i][j]){
                    return false;
                }
            }
        }
        return true;
    }

    public ArrayList<String> getAns(){
        search();
        ArrayList<String> ans = new ArrayList<>();
        int size = steps.size();
        for (int i = 0; i < size; i++) {
            ans.add(steps.pop().toString());
        }
        return ans;
    }

    private boolean isSolved(){
        boolean isZero = false;
        int cnt = 0;
        int[] test = new int[m * n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                test[cnt] = arr[i][j];
                cnt++;
            }
        }

        for (int i = 0; i < m * n; i++) {
            if (!isZero){
                if (test[i] == 0){
                    isZero = true;
                    continue;
                }
                if (test[i] != i + 1){
                    return false;
                }
            } else {
                if (test[i] != 0){
                    return false;
                }
            }
        }
        return true;
    }

    private Type getType(int num){
        if (one_one[num]) return Type.ONE_ONE;
        if (one_two[num]) return Type.ONE_TWO;
        if (two_one[num]) return Type.TWO_ONE;
        if (two_two[num]) return Type.TWO_TWO;
        return null;
    }

    private void print2D(int[][] arr){
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
    }

    private int[][] arrayClone(int[][] arr){
        int[][] newArr = new int[arr.length][arr[0].length];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                newArr[i][j] = arr[i][j];
            }
        }
        return newArr;
    }

    public void shuffle(int x, int y, int z){
        Random r = new Random();
        int step = r.nextInt(4) + 31;
        for (int i = 0; i < step; i++) {
            ArrayList<Step> canMoveSteps = getCanMoveSteps();
            int length = canMoveSteps.size();
            move(canMoveSteps.get(r.nextInt(length)));
        }
        try (PrintWriter out = new PrintWriter("./src/util/input.txt")) {
            out.println("4 4");
            for (int i = 0; i < 4; i++) {
                out.println(arr[i][0] + " " + arr[i][1] + " " + arr[i][2] + " " + arr[i][3]);
            }
            out.println(3);
            out.println(x + " 2*2");
            out.println(y + " 1*2");
            out.println(z + " 2*1");
        } catch (Exception e) { }
    }
}
