package hellofx;

public class Food {
    private int x, y;
    public Food(int x, int y) { this.x = x; this.y = y; }
    public int getX() { return x; }
    public int getY() { return y; }
    public Point getPoint() { return new Point(x, y); }
}
