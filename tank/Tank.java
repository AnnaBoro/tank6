package tankproject.tank;


import tankproject.battlefield.Destroyable;
import tankproject.battlefield.Drawable;

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