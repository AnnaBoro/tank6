package lesson6_9.adapter.tank6.tank;


import lesson6_9.adapter.tank6.battlefield.Destroyable;
import lesson6_9.adapter.tank6.battlefield.Drawable;

public interface Tank extends Drawable, Destroyable {

    public Action setUp();

    public void move() throws InterruptedException;

    public Bullet fire() throws InterruptedException;

    public int getX();

    public int getY();

    public Direction getDirection();

    public void updateX(int x);

    public void updateY(int y);

    public int getSpeed();

    public int getMovePath();

}