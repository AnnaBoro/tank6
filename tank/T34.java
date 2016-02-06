package lesson6_9.adapter.tank6.tank;


import lesson6_9.adapter.tank6.ActionField;
import lesson6_9.adapter.tank6.battlefield.Algo2;
import lesson6_9.adapter.tank6.battlefield.BattleField;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class T34 extends AbstractTank {

    private Object[] actions;
    private Object[] actions2;

    private int step = 0;

    public T34(ActionField actionField, BattleField battleField) {

        super(actionField, battleField);
        IMAGE_NAME_UP = "t34_up.png";
        IMAGE_NAME_DOWN = "t34_down.png";
        IMAGE_NAME_LEFT = "t34_lft.png";
        IMAGE_NAME_RIGHT = "t34_rght.png";
        try {
            myTankImageUp = ImageIO.read(new File(IMAGE_NAME_UP));
            myTankImageDown = ImageIO.read(new File(IMAGE_NAME_DOWN));
            myTankImageLeft = ImageIO.read(new File(IMAGE_NAME_LEFT));
            myTankImageRight = ImageIO.read(new File(IMAGE_NAME_RIGHT));
        }
        catch (IOException e) {
            System.err.print("Can't find image ");
        }
        algo2 = new Algo2(battleField.getStringBattleField(), actionField, this);
    }

    public T34(ActionField actionField, BattleField battleField, int x, int y, Direction direction) {

        super(actionField, battleField, x, y, direction);
        IMAGE_NAME_UP = "t34_up.png";
        IMAGE_NAME_DOWN = "t34_down.png";
        IMAGE_NAME_LEFT = "t34_lft.png";
        IMAGE_NAME_RIGHT = "t34_rght.png";
        try {
            myTankImageUp = ImageIO.read(new File(IMAGE_NAME_UP));
            myTankImageDown = ImageIO.read(new File(IMAGE_NAME_DOWN));
            myTankImageLeft = ImageIO.read(new File(IMAGE_NAME_LEFT));
            myTankImageRight = ImageIO.read(new File(IMAGE_NAME_RIGHT));
        }
        catch (IOException e) {
            System.err.print("Can't find image ");
        }
    }

    @Override
    public Action setUp() {

        step = 0;
        algo2 = new Algo2(battleField.getStringBattleField(), actionField, this);
        actions = null;

        if (actions == null) {
            actions2 = null;
            List<int[]> path = algo2.movePath();
            actions = new Object[path.size()];

            for (int i = 0; i < path.size() - 1; i++) {
                if (path.get(i)[0] > path.get(i + 1)[0]) {
                    actions[i] = Direction.UP;
                }
                else if (path.get(i)[0] < path.get(i + 1)[0]) {
                    actions[i] = Direction.DOWN;
                }
                else if (path.get(i)[1] > path.get(i + 1)[1]) {
                    actions[i] = Direction.LEFT;
                }
                else {
                    actions[i] = Direction.RIGHT;
                }
            }

            actions2 = new Object[actions.length * 2 - 1];

            System.out.println("action length " + actions.length);
            System.out.println("action2 length " + actions2.length);
            for (int i = 0, j = 0; i < actions.length && j < actions2.length - 2; i++) {
                actions2[j] = actions[i];
                actions2[j + 1] = Action.MOVE;
                System.out.println("action2[" + j + "]");
                j = j + 2;
            }
            actions2[actions2.length - 1] = Action.FIRE;

            actions = actions2;

            for (Object o : actions) {
                System.out.println(o);
            }
        }

        if (actions.length == step ){
            return null;
        }

        if (!(actions[step] instanceof Action)) {
            turn((Direction) actions[step++]);
        }
        return (Action) actions[step++];
    }

    @Override
    public int getMovePath() {
        return 0;
    }
}

