package lesson6_9.adapter.tank6.tank;


import lesson6_9.adapter.tank6.battlefield.Destroyable;
import lesson6_9.adapter.tank6.battlefield.Drawable;

import java.awt.*;

public class Bullet implements Drawable, Destroyable {

    private int x = -100;
    private int y = -100;

    private int speed = 5;
    private Direction direction;

    public Bullet(int x, int y, Direction direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSpeed() {
        return speed;
    }

    public Direction getDirection() {
        return direction;
    }

    public void updateX(int i) {

        x += i;
    }

    public void updateY(int i) {

        y += i;
    }

    public void destroy() {

        x = -100;
        y = -100;
    }

    @Override
    public boolean isDestroyed() {
        return false;
    }

    @Override
    public void draw(Graphics g) {

        g.setColor(new Color(255, 255, 0));
        g.fillRect(this.getX(), this.getY(), 14, 14);
    }
}

