package solve;

public class Step {
    int num;
    char direction;

    public Step(int num, char direction) {
        this.num = num;
        this.direction = direction;
    }

    @Override
    public String toString() {
        return num + " " + direction;
    }
}
