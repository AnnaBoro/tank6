package tankproject;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionFieldUI implements ActionListener {

    private boolean reRun = false;
    private JFrame frameDialog;
    public JDialog dialog;
    private ButtonGroup buttonGroup;
    private JRadioButton button1;
    private JRadioButton button2;
    private JRadioButton button3;
    private JButton okButton;
    private JPanel chooseTankPanel;
    private JPanel viewScorePanel;
    private JButton newGameButton;
    private JButton exitButton;
    private final ActionField actionField;

    public ActionFieldUI(ActionField actionField, JFrame frame) {

        this.actionField = actionField;
        this.frameDialog = frame;
    }

    public boolean isReRun() {
        return reRun;
    }

    private void initDialog(){

        dialog = new JDialog(frameDialog, true);
        dialog.setSize(new Dimension(400, 400));
        dialog.setLocation(750, 150);
        dialog.setModal(true);

    }

    public void setChooseTankPanel() {

        if(dialog == null) {
            initDialog();
        }
        dialog.getContentPane().removeAll();
        dialog.getContentPane().add(createChooseTankPanel());
        dialog.setVisible(true);
    }

    public void setViewScorePanel() {

        if(dialog == null)
            initDialog();

        dialog.getContentPane().removeAll();
        dialog.getContentPane().add(createScorePanel());
        dialog.setVisible(true);
    }

    public JPanel createChooseTankPanel() {

        chooseTankPanel = new JPanel();
        GridBagLayout gbl = new GridBagLayout();
        chooseTankPanel.setLayout(gbl);

        GridBagConstraints c = new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0);
        JLabel label = new JLabel("What tank would you like to play?");
        gbl.setConstraints(label, c);

        buttonGroup = new ButtonGroup();
        button1 = new JRadioButton("T34", true);
        button2 = new JRadioButton("Tiger");
        button3 = new JRadioButton("BT7");

        button1.setActionCommand(button1.getText());
        button2.setActionCommand(button2.getText());
        button3.setActionCommand(button3.getText());

        buttonGroup.add(button1);
        buttonGroup.add(button2);
        buttonGroup.add(button3);

        c = new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0);
        gbl.setConstraints(button1, c);
        c = new GridBagConstraints(0, 2, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0);
        gbl.setConstraints(button2, c);
        c = new GridBagConstraints(0, 3, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0);
        gbl.setConstraints(button3, c);

        button1.addActionListener(this);
        button2.addActionListener(this);
        button3.addActionListener(this);

        okButton = new JButton("Ok");
        c = new GridBagConstraints(0, 4, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0);
        gbl.setConstraints(okButton, c);

        chooseTankPanel.add(label);
        chooseTankPanel.add(button1);
        chooseTankPanel.add(button2);
        chooseTankPanel.add(button3);

        chooseTankPanel.add(okButton);
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reRun = false;
                dialog.getContentPane().remove(chooseTankPanel);
                dialog.setVisible(false);
                dialog = null;
            }
        });

        return chooseTankPanel;
    }

    public JPanel createScorePanel() {

        viewScorePanel = new JPanel();
        GridBagLayout gbl = new GridBagLayout();
        viewScorePanel.setLayout(gbl);

        GridBagConstraints c = new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0);
        JLabel label1 = new JLabel("Score: ");
        gbl.setConstraints(label1, c);

        c = new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0);
        JLabel label2 = new JLabel("2 : 1");
        gbl.setConstraints(label2, c);

        c = new GridBagConstraints(0, 2, 1, 1, 0, 0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0);
        newGameButton = new JButton("New Game");
        gbl.setConstraints(newGameButton, c);

        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    dialog.getContentPane().remove(viewScorePanel);
                    dialog.setVisible(false);
                    reRun = true;
                    dialog = null;
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        c = new GridBagConstraints(1, 2, 1, 1, 0, 0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0);
        exitButton = new JButton("EXIT");
        gbl.setConstraints(exitButton, c);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        viewScorePanel.add(label1);
        viewScorePanel.add(label2);
        viewScorePanel.add(newGameButton);
        viewScorePanel.add(exitButton);
        viewScorePanel.setVisible(true);

        return viewScorePanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (buttonGroup.getSelection() != null) {

            String str = buttonGroup.getSelection().getActionCommand();
            System.out.println(str);
        }
    }
}
