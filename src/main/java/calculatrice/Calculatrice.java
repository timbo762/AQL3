package calculatrice;


public class Calculatrice {
    private int result;

    public int additionner(int x, int y) {
        result = x + y;
        return result;
    }

    public int getResult() {
        return result;
    }
}