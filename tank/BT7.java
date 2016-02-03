package lesson6_9.adapter.tank6.tank;


import lesson6_9.adapter.tank6.ActionField;
import lesson6_9.adapter.tank6.battlefield.Algo3;
import lesson6_9.adapter.tank6.battlefield.BattleField;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class BT7 extends AbstractTank {

    private int speed;

    List<int[]> path = null;

    public BT7(ActionField actionField, BattleField battleField, int x, int y, Direction direction) {
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
        speed = super.getSpeed() / 2;
        algo3 = new Algo3(battleField.getStringBattleField(), this);
    }

    public Object[] actions;

    private int step = 0;

    @Override
    public Action setUp() {

        if (actions != null && step == actions.length) {
            step = 0;
            actions = null;
            Collections.reverse(path);
        }
        if (actions == null) {
            if (path == null) {
                path = algo3.movePath();
            }
            actions = new Object[path.size() - 1];

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
            Object[] actions2 = new Object[actions.length * 2];
            for (int i = 0, j = 0; i < actions.length && j < actions2.length - 1; i++) {
                actions2[j] = actions[i];
                actions2[j + 1] = Action.MOVE;
                j = j + 2;
            }
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
