package hellofx;

import java.util.LinkedList;
import java.util.List;

public class Snake {

    private final LinkedList<Point> body = new LinkedList<>();
    private int dirX = 1, dirY = 0; // initial right
    private boolean growNext = false;

    public Snake(int startX, int startY) {
        body.clear();
        body.add(new Point(startX, startY));
        body.add(new Point(startX - 1, startY));
        body.add(new Point(startX - 2, startY));
    }

    public List<Point> getBody() { return body; }
    public Point getHead() { return body.getFirst(); }

    public void setDirection(int x, int y) {
        // prevent reverse
        if (x == -dirX && y == -dirY) return;
        this.dirX = x; this.dirY = y;
    }

    public void grow() { growNext = true; }

    public boolean occupies(int x, int y) {
        for (Point p : body) if (p.x == x && p.y == y) return true;
        return false;
    }

    public boolean move(int width, int height, boolean wrap, java.util.List<Obstacle> obstacles) {
        Point head = getHead();
        int nx = head.x + dirX;
        int ny = head.y + dirY;

        // wall
        if (!wrap) {
            if (nx < 0 || ny < 0 || nx >= width || ny >= height) {
                return false;
            }
        } else {
            if (nx < 0) nx = width - 1;
            if (ny < 0) ny = height - 1;
            if (nx >= width) nx = 0;
            if (ny >= height) ny = 0;
        }

        // self collision
        for (Point p : body) {
            if (p.x == nx && p.y == ny) return false;
        }

        // obstacles
        if (obstacles != null) {
            for (Obstacle o : obstacles) {
                if (o.getX() == nx && o.getY() == ny) return false;
            }
        }

        body.addFirst(new Point(nx, ny));
        if (!growNext) body.removeLast();
        else growNext = false;
        return true;
    }
}
