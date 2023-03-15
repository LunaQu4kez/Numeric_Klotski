package solve;

public class Pair implements Comparable<Pair>{
    int[][] arr;
    Step step;
    Pair pre;

    public Pair(int[][] arr, Step step, Pair pre) {
        this.arr = arr;
        this.step = step;
        this.pre = pre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair pair = (Pair) o;
        return this.compareTo(pair) ==  0;
    }

    @Override
    public int hashCode() {
        int h = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                h ^= arr[i][j] ^ (h << 8) + h;
            }
        }
        return h;
    }

    @Override
    public int compareTo(Pair o) {
        int m = arr.length;
        int n = arr[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (arr[i][j] < o.arr[i][j]) return -1;
                if (arr[i][j] > o.arr[i][j]) return 1;
            }
        }
        return 0;
    }
}
