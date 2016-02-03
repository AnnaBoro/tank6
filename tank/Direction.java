package lesson6_9.adapter.tank6.tank;


public enum Direction {

    UP(1), DOWN(2), LEFT(3), RIGHT(4);

    private int id;

    private Direction(int id) {

        this.id = id;
    }

    public int getId() {
        return id;
    }
}
