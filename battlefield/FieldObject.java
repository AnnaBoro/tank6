package lesson6_9.adapter.tank6.battlefield;


import java.awt.*;

public class FieldObject implements Drawable, Destroyable {

    protected int x;
    protected int y;

    protected boolean destroyed;

    public FieldObject(int y, int x) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public void destroy() {
        destroyed = true;
    }

    @Override
    public boolean isDestroyed() {
        return destroyed;
    }

    @Override
    public void draw(Graphics g) {

    }
}
