package tankproject;


import tankproject.tank.Action;
import tankproject.tank.Direction;
import tankproject.tank.Tank;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ActionUtility {

    private String stringAction;
    private List<Object> listTank1;
    private List<Object> listTank2;
    private int[] tankCoordinates;
    private int numActionTank1;
    private int numActionTank2;

    public ActionUtility() {

        listTank1 = new ArrayList<>();
        listTank2 = new ArrayList<>();
        tankCoordinates = new int[2];
    }

    public void getGameRepeat() {

        numActionTank1 = 0;
        numActionTank2 = 0;
        listTank1.clear();;
        listTank2.clear();
        stringAction = SaveGame.getSaveGame();
        String[] tempArray = stringAction.split("/");

        for (String s : tempArray) {
            if (s.startsWith("BT7")) {
                String[] tempArray2 = (s.substring(4)).split(" ");
                for (String s1 : tempArray2) {

                    if (isDirection(s1)) {
                        listTank1.add(getDirection(s1));
                    }
                    else {
                        listTank1.add(getAction(s1));
                        numActionTank1++;
                    }
                }
            }
            else if (s.startsWith("Tiger")) {
                String[] tempArray3 = (s.substring(6)).split(" ");
                for (String s1 : tempArray3) {

                    if (isDirection(s1)) {
                        listTank2.add(getDirection(s1));
                    }
                    else {
                        listTank2.add(getAction(s1));
                        numActionTank2++;
                    }
                }
            }
        }
    }

    public boolean isDirection(String s){

        if (s.equalsIgnoreCase("down") || s.equalsIgnoreCase("up") || s.equalsIgnoreCase("left") ||
                s.equalsIgnoreCase("right")) {

            return true;
        }
        return false;
    }

    public Direction getDirection(String str) {

        if (str.equalsIgnoreCase("down")) {
            return Direction.DOWN;
        }
        else if (str.equalsIgnoreCase("up")) {
            return Direction.UP;
        }
        else if (str.equalsIgnoreCase("left")) {
            return Direction.LEFT;
        }
        else if (str.equalsIgnoreCase("right")) {
            return Direction.RIGHT;
        }
        return null;
    }

    public Action getAction(String str) {

        if (str.equalsIgnoreCase("move")) {
            return Action.MOVE;
        }
        else
            return Action.FIRE;
    }

        public int[] getTankCoordinates(Tank tank) {

        return tankCoordinates;
    }

    public int getMaxNumAction() {

        if (numActionTank1 < numActionTank2) {
            return numActionTank1;
        }
        return numActionTank2;
    }

    public List<Object> getListTank1() {
        return listTank1;
    }

    public List<Object> getListTank2() {
        return listTank2;
    }
}
