package tankproject;


import tankproject.battlefield.BattleField;
import tankproject.battlefield.Brick;
import tankproject.battlefield.ClearField;
import tankproject.battlefield.Eagle;
import tankproject.tank.Action;
import tankproject.tank.*;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class ActionField extends JPanel {

    private boolean COLORDED_MODE = false;

    private BattleField bf;
    private AbstractTank defender;
    private AbstractTank agressor;
    private AbstractTank defender2;
    private int[][] randomArr = {{320, 192}, {64, 64}, {448, 64}};
//    {{64, 64}, {64, 448}, {448, 64}};
    private int randomPosition = -1;
    private JFrame frame;
    private ActionFieldUI actionFieldUI;
    private int yourScore;
    private int enemyScore;
    private SaveGame saveGame;

    public ActionField() throws Exception {

        saveGame = new SaveGame();
        frame = new JFrame("BATTLE FIELD, Lesson 5");
        actionFieldUI = new ActionFieldUI(this, frame);
    }

    public void initFrame() throws Exception {

        bf = new BattleField();

        frame.setLocation(750, 150);
        frame.setMinimumSize(new Dimension(bf.getBF_WIDTH(), bf.getBF_HEIGHT() + 22));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().remove(this);
        frame.getContentPane().add(this);

        actionFieldUI.setChooseTankPanel();

        int[] xy = randomArr[getRandomNum()];
        int y2 = xy[1];
        int x2 = xy[0];
        agressor = null;
        agressor = new Tiger(this, bf, x2, y2, Direction.DOWN);
        defender = new T34(this, bf, 0, 0, Direction.LEFT);
        defender2 = new BT7(this, bf, 320, 512, Direction.UP);

        frame.pack();
        frame.setVisible(true);
    }

    public void runTheGame() throws Exception {

        initFrame();
        while (!stopGameCondition()) {

            processAction(agressor.setUp(), agressor);
            //processAction(defender.setUp(), defender);
            processAction(defender2.setUp(), defender2);

        }
        System.out.println("finish");

        actionFieldUI.setViewScorePanel();

        if (actionFieldUI.isReRun()) {
            runTheGame();
        }
    }

    public boolean stopGameCondition() {

        boolean b = defender.isDestroyed() || agressor.isDestroyed() || bf.getBattleField()[8][4].isDestroyed();

        return b;
    }

    public void processAction(Action a, AbstractTank t) throws InterruptedException {

        SaveGame.saveAction(t.getClass().getSimpleName() + " "  + t.getDirection() + " " + a);

        if (a == Action.MOVE) {
            processMove(t);
        }

        else if (a == Action.FIRE) {
            t.fire();
        }
    }

    private boolean processInterception(AbstractTank tank) throws InterruptedException {

        if (isOnTheField(tank.getBullet())) {

            if (removeBrick(false, tank)) {
                tank.getBullet().destroy();
            }

            else if (removeTank(tank)) {
                agressor.destroy();
                tank.getBullet().destroy();
                repaint();
                Thread.sleep(3000);
                int[] xy = randomArr[getRandomNum()];
                agressor.updateX(xy[0]);
                agressor.updateY(xy[1]);
                repaint();
                ((Tiger)agressor).setArmor(1);
            }

            else if (tank instanceof Tiger && removeEagle()) {
                tank.getBullet().destroy();
            }
            return false;
        }
        return true;
    }

    public boolean isOnTheField(Bullet bullet) {

        if (bullet != null && (bullet.getX() > 0 &&  bullet.getX() < 575)
                && (bullet.getY() > 0 &&  bullet.getY() < 575)) {
            return true;
        }
        return false;
    }

    public boolean removeBrick(boolean removeType, AbstractTank tank) {

        String quadrant;

        if (removeType) {
            quadrant = getQuadrant(tank.getX(), tank.getY());
        }
        else quadrant = getQuadrant(tank.getBullet().getX(), tank.getBullet().getY());

        int i = Integer.parseInt(quadrant.substring(0, quadrant.indexOf("_")));
        int j = Integer.parseInt(quadrant.substring(quadrant.indexOf("_") + 1, quadrant.length()));

        if (bf.scanQuadrant(i, j) instanceof Brick) {
            bf.updateQuadrant(i, j, new ClearField(i, j));
            repaint();
            return true;
        }
        return false;
    }

    public boolean removeEagle() {

        String quadrant = getQuadrant(agressor.getBullet().getX(), agressor.getBullet().getY());

        int i = Integer.parseInt(quadrant.substring(0, quadrant.indexOf("_")));
        int j = Integer.parseInt(quadrant.substring(quadrant.indexOf("_") + 1, quadrant.length()));

        if (bf.scanQuadrant(i, j) instanceof Eagle) {
            bf.updateQuadrant(i, j, new ClearField(i, j));
            bf.getBattleField()[8][4].destroy();
            return true;
        }
        return false;
    }

    public boolean removeTank(AbstractTank tank) throws InterruptedException {

        if (tank instanceof Tiger) {

            return false;
        }
        Bullet bullet1 = tank.getBullet();

        if (bullet1 != null) {
            String quadrant = getQuadrant(bullet1.getX(), bullet1.getY());
            String quadrant2 = getQuadrant(agressor.getX(), agressor.getY());

            if (quadrant.equalsIgnoreCase(quadrant2)) {
                if (((Tiger)agressor).getArmor() == 1) {
                    ((Tiger) agressor).setArmor(((Tiger) agressor).getArmor() - 1);
                    bullet1.destroy();
                    repaint();
                    tank.fire();
                    return false;
                } else if (((Tiger)agressor).getArmor() == 0) {
                    ((Tiger) agressor).setArmor(((Tiger) agressor).getArmor() - 1);
                    agressor.destroy();
                    return true;
                }
            }
        }
        return false;
    }

    public String getQuadrant(int v, int h) {

        int x = v / 64;
        int	y = h / 64;

        return y + "_" + x;
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        int i = 0;
        Color cc;
        for (int v = 0; v < 9; v++) {
            for (int h = 0; h < 9; h++) {
                if (COLORDED_MODE) {
                    if (i % 2 == 0) {
                        cc = new Color(252, 241, 177);
                    } else {
                        cc = new Color(233, 243, 255);
                    }
                } else {
                    cc = new Color(180, 180, 180);
                }
                i++;
                g.setColor(cc);
                g.fillRect(h * 64, v * 64, 64, 64);
            }
        }

        bf.draw(g);
        defender.draw(g);
        agressor.draw(g);
        defender2.draw(g);

        if (defender.getBullet() != null) {
            defender.getBullet().draw(g);
        }
        if (agressor.getBullet() != null) {
            agressor.getBullet().draw(g);
        }
        if (defender2.getBullet() != null) {
            defender2.getBullet().draw(g);
        }
    }

    public void processMove(AbstractTank tank) throws InterruptedException {

        for (int i = 0; i < 64; i++) {

            if (tank.getDirection().getId() == 1) {

                if (tank.getY() !=0) {
                    tank.updateY(-1);
                }
                else System.out.println("Wrong direction");
            }
            else if (tank.getDirection().getId() == 2) {

                if (tank.getY() != 512) {
                    tank.updateY(1);
                }
                else System.out.println("Wrong direction");
            }
            else if (tank.getDirection().getId() == 3) {

                if (tank.getX() != 0) {
                    tank.updateX(- 1);
                }
                else System.out.println("Wrong direction");
            }
            else if (tank.getDirection().getId() == 4) {

                if (tank.getX() != 512) {
                    tank.updateX(1);
                }
                else System.out.println("Wrong direction");
            }
            processTankInterception(tank);
            repaint();
            Thread.sleep(tank.getSpeed()/2);

        }
        this.removeBrick(true, tank);
    }

    private void processTankInterception(AbstractTank tank) throws InterruptedException {

        if (tank.getDirection() == Direction.UP) {
            if (tank.getX() == getAgressor().getX() && ( (tank.getY() - 64) >= getAgressor().getY())) {
                tank.fire();
            }
        }
        else if (tank.getDirection() == Direction.DOWN) {
            if (tank.getX() == getAgressor().getX() && (tank.getY() + 64) <= getAgressor().getY()) {
                tank.fire();
            }
        }
        else if (tank.getDirection() == Direction.LEFT) {
            if (tank.getY() == getAgressor().getY() && (tank.getX() - 64) >= getAgressor().getX()) {
                tank.fire();
            }
        }
        else if (tank.getDirection() == Direction.RIGHT) {
            if (tank.getY() == getAgressor().getY() && (tank.getX() + 64) <= getAgressor().getX() ) {
                tank.fire();
            }
        }
    }

    public void processTurn(AbstractTank tank) {

        repaint();
    }

    public void processFire(AbstractTank tank) throws InterruptedException {

        SaveGame.saveAction(tank.getClass().getSimpleName() + " " + tank.getDirection() + " " + "FIRE");

        while (isOnTheField(tank.getBullet())) {
            for (int i = 0; i < 64; ) {

                if (tank.getDirection().getId() == 1) {
                    tank.getBullet().updateY(-1);
                }
                else if (tank.getDirection().getId() == 2) {
                    tank.getBullet().updateY(1);
                }
                else if (tank.getDirection().getId() == 3) {
                    tank.getBullet().updateX(-1);
                }
                else if (tank.getDirection().getId() == 4) {
                    tank.getBullet().updateX(1);
                }
                processInterception(tank);
                repaint();
                Thread.sleep(tank.getBullet().getSpeed());
                break;
            }
        }
    }

    public int getRandomNum() {

        Random random = new Random();
        int randomInt = random.nextInt(3);
        if (randomPosition == randomInt) {
            return getRandomNum();
        }
        randomPosition = randomInt;
        return randomInt;
    }

    public JFrame getFrame() {
        return frame;
    }

    public AbstractTank getTank() {
        return defender;
    }

    public AbstractTank getAgressor() {
        return agressor;
    }
}
