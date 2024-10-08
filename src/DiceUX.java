import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import java.util.Arrays;
import java.util.LinkedList;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;

public class DiceUX extends Canvas {

    /*
     * JFrame that contains JPanels for interacting with the game and for the rules
     */
    private JFrame frame;

    /* JPanel that lets user interact with the game */
    private JPanel gamePanel;
    /* JPanel that shows user the rules of the game */
    private JPanel rulesPanel;

    /* JLabel that titles the game panel */
    private JLabel panelLabel;
    /* JLabel that identifies the rules panel */
    private JLabel rulesLabel;
    /* JLabel that identifies which JTextField is for the game index */
    private JLabel gameIndexLabel;
    /*
     * JLabel that shows user info about the longest game, what game they are
     * viewing and any errors that pop up
     */
    private JLabel longestGame;
    /* JLabel that identifies which JTextField is for the number of games */
    private JLabel numGamesLabel;
    /* JLabel that identifies which JTextField is for the start dice */
    private JLabel startDiceLabel;

    /* JTextField for inputting the index of the desired game */
    private JTextField gameIndexInput;
    /* JTextField for inputting the number of games desired */
    private JTextField numGamesInput;
    /* JTextField for inputting the number of start dice desired */
    private JTextField startDiceInput;

    /* JScrollPane that contains the outputArea JTextArea */
    private JScrollPane outputScroll;

    /*
     * JTextArea that either shows the summary of each game or each game round by
     * round depending on what is wanted
     */
    private JTextArea outputArea;
    /*
     * JTextArea that shows the user the basic rules of Dice Game and information
     * about the gamePanel's layout
     */
    private JTextArea rules;

    /* JButton that returns user to gamePanel */
    private JButton backButton;
    /* JButton that clears the gamePanel and all information about previous games */
    private JButton clearButton;
    /* JButton that shows the desired game round by round in outputArea */
    private JButton gameSearch;
    /* JButton that navigates user to rulesPanel */
    private JButton instructions;
    /* JButton that returns user to viewing the game summaries in outputArea */
    private JButton returnToGames;
    /* JButton that runs all the games */
    private JButton rollButton;

    // defines the way that JComponents are layed out on the JPanels
    private GridBagConstraints c;

    // stores each individual game
    private LinkedList<Dice> games;

    // String concatination of the game summaries
    private String game = "";
    // the starting text for longestGame JLabel
    private String longGame = "Longest Game at Game: Rounds:";
    // the text in longestGame JLabel
    private String longestGameText = longGame;
    // the text in numGamesInput
    private String numGamesText = "1000";
    // the text in startDiceInput
    private String startDiceText = "2";

    // the total number of possible winners
    private final int NUMBER_OF_WINNERS = 7;
    // the integer value for a center anchor for GridBagConstraints
    private final int CENTER = GridBagConstraints.CENTER;
    // the integer value for a left anchor for GridBagConstraints
    private final int LEFT = GridBagConstraints.LINE_START;
    // the integer value for a right anchor for GridBagConstraints
    private final int RIGHT = GridBagConstraints.LINE_END;
    // the integer value for a top anchor for GridBagConstraints
    // private final int TOP = GridBagConstraints.PAGE_START;
    // the integer value for a bottom anchor for GridBagConstraints
    // private final int BOTTOM = GridBagConstraints.PAGE_END;
    // the integer value for a horizontal fill for GridBagConstraints
    // private final int HORIZ = GridBagConstraints.HORIZONTAL;
    // the integer value for a vertical fill for GridBagConstraints
    // private final int VERT = GridBagConstraints.VERTICAL;
    // the integer value for both a vertical and a horizontal fill for
    // GridBagConstraints
    private final int BOTH = GridBagConstraints.BOTH;
    // the integer value for neither a vertical and a horizontal fill for
    // GridBagConstraints
    private final int NONE = GridBagConstraints.NONE;

    // the index of the game currently being viewed
    private int gameIndex = 0;

    // whether the user was viewing a specific game or not
    private boolean viewingGameHistory = false;

    /**
     * the constructor sets the values of the JFrame, GridBagConstraints, and
     * LinkedList, as well as setting the size of the JFrame
     * 
     * @param f - JFrame that is the frame for both panels
     */
    public DiceUX(JFrame f) {
        frame = f;
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());

        c = new GridBagConstraints();

        games = new LinkedList<Dice>();
    }

    /**
     * creates the panel that runs the games and is the way to alter start dice, the
     * number of games played, whether or not and which game is being viewed
     */
    public void uxPanel() {

        gamePanel = new JPanel(new GridBagLayout());
        frame.setContentPane(gamePanel);
        gamePanel.setVisible(true);
        c.weightx = 0.1;
        c.weighty = 0.1;
        gamePanel.setFocusable(true);

        panelLabel = new JLabel("Dice Game");
        c.gridx = 0;
        c.gridwidth = 9;
        c.gridy = 0;
        c.gridheight = 1;
        c.anchor = CENTER;
        c.fill = NONE;
        c.ipadx = 100;
        c.ipady = 100;
        gamePanel.add(panelLabel, c);
        panelLabel.setFont(new Font("Cambria", Font.BOLD, 30));
        panelLabel.setVisible(true);

        instructions = new JButton("Rules of the Game");
        c.gridx = 2;
        c.gridwidth = 2;
        c.gridy = 6;
        c.gridheight = 1;
        c.anchor = CENTER;
        c.fill = NONE;
        c.ipadx = 0;
        c.ipady = 0;
        gamePanel.add(instructions, c);
        instructions.setFocusable(false);

        numGamesLabel = new JLabel("Number of Games:");
        c.gridx = 0;
        c.gridwidth = 1;
        c.gridy = 1;
        c.gridheight = 1;
        c.anchor = RIGHT;
        c.fill = NONE;
        c.ipadx = 0;
        c.ipady = 0;
        gamePanel.add(numGamesLabel, c);
        numGamesLabel.setVisible(true);

        numGamesInput = new JTextField(numGamesText);
        c.gridx = 1;
        c.gridwidth = 1;
        c.gridy = 1;
        c.gridheight = 1;
        c.anchor = LEFT;
        c.fill = NONE;
        c.ipadx = 100;
        gamePanel.add(numGamesInput, c);
        numGamesInput.setVisible(true);
        numGamesInput.setForeground(Color.gray);

        startDiceLabel = new JLabel("Number of Dice at Begininning of Game:");
        c.gridx = 0;
        c.gridwidth = 1;
        c.gridy = 2;
        c.gridheight = 1;
        c.anchor = RIGHT;
        c.fill = NONE;
        c.ipadx = 0;
        gamePanel.add(startDiceLabel, c);
        startDiceLabel.setVisible(true);

        startDiceInput = new JTextField(startDiceText);
        c.gridx = 1;
        c.gridwidth = 1;
        c.gridy = 2;
        c.gridheight = 1;
        c.anchor = LEFT;
        c.fill = NONE;
        c.ipadx = 100;
        gamePanel.add(startDiceInput, c);
        startDiceInput.setVisible(true);

        gameIndexLabel = new JLabel("See Game Number:");
        c.gridx = 2;
        c.gridwidth = 1;
        c.gridy = 1;
        c.gridheight = 2;
        c.anchor = RIGHT;
        c.fill = NONE;
        c.ipadx = 0;
        gamePanel.add(gameIndexLabel, c);
        gameIndexLabel.setVisible(true);

        gameIndexInput = new JTextField();
        c.gridx = 3;
        c.gridwidth = 1;
        c.gridy = 1;
        c.gridheight = 2;
        c.anchor = LEFT;
        c.fill = NONE;
        c.ipadx = 100;
        gamePanel.add(gameIndexInput, c);
        gameIndexInput.setVisible(true);

        rollButton = new JButton("Roll");
        c.gridx = 0;
        c.gridwidth = 2;
        c.gridy = 3;
        c.gridheight = 1;
        c.anchor = CENTER;
        c.fill = NONE;
        c.ipadx = 30;
        c.ipady = 30;
        gamePanel.add(rollButton, c);
        rollButton.setVisible(true);
        rollButton.setFocusable(false);

        gameSearch = new JButton("Find Game");
        c.gridx = 2;
        c.gridwidth = 2;
        c.gridy = 3;
        c.gridheight = 1;
        c.anchor = CENTER;
        c.fill = NONE;
        c.ipadx = 30;
        c.ipady = 30;
        gamePanel.add(gameSearch, c);
        gameSearch.setVisible(true);
        gameSearch.setFocusable(false);

        longestGame = new JLabel(longestGameText);
        c.gridx = 0;
        c.gridwidth = 4;
        c.gridy = 4;
        c.gridheight = 1;
        c.anchor = CENTER;
        c.fill = NONE;
        c.ipadx = 0;
        c.ipady = 0;
        gamePanel.add(longestGame, c);
        longestGame.setVisible(true);

        returnToGames = new JButton("Back to Games");
        c.gridx = 0;
        c.gridwidth = 4;
        c.gridy = 5;
        c.gridheight = 1;
        c.anchor = CENTER;
        c.fill = NONE;
        c.ipadx = 30;
        c.ipady = 30;
        gamePanel.add(returnToGames, c);
        returnToGames.setVisible(false);
        returnToGames.setFocusable(false);

        clearButton = new JButton("Clear");
        c.gridx = 0;
        c.gridwidth = 2;
        c.gridy = 6;
        c.gridheight = 1;
        c.anchor = CENTER;
        c.fill = NONE;
        c.ipadx = 0;
        c.ipady = 0;
        gamePanel.add(clearButton, c);
        clearButton.setVisible(true);
        clearButton.setFocusable(false);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        outputArea.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        outputArea.setMargin(new Insets(30, 30, 30, 30));
        outputArea.setVisible(true);

        outputScroll = new JScrollPane(outputArea);
        c.gridx = 5;
        c.gridwidth = 1;
        c.gridy = 1;
        c.gridheight = 6;
        c.anchor = CENTER;
        c.fill = BOTH;
        c.ipadx = 200;
        c.ipady = 0;
        c.weightx = 0.5;
        c.weighty = 0.5;
        gamePanel.add(outputScroll, c);
        outputScroll.setVisible(true);
        outputScroll.setFocusable(false);

        rollButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                runGames();
            }

        });

        gameSearch.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                findGame();
            }
        });

        returnToGames.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                backToGames();
            }

        });

        instructions.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                gamePanel.setVisible(false);
                rules();
            }

        });

        clearButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                clear();
            }

        });

        numGamesInput.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
                if (numGamesInput.getText().equals("1000") && numGamesInput.getForeground().equals(Color.gray)) {
                    numGamesInput.setText("");
                }
                numGamesInput.setForeground(Color.black);
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (numGamesInput.getText().equals("")) {
                    numGamesInput.setForeground(Color.gray);
                    numGamesInput.setText("1000");
                } else {
                    numGamesInput.setForeground(Color.black);
                }
            }

        });

        startDiceInput.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
                if (startDiceInput.getText().equals("2") && startDiceInput.getForeground().equals(Color.gray)) {
                    startDiceInput.setText("");
                }
                startDiceInput.setForeground(Color.black);
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (startDiceInput.getText().equals("")) {
                    startDiceInput.setForeground(Color.gray);
                    startDiceInput.setText("2");
                } else {
                    startDiceInput.setForeground(Color.black);
                }
            }

        });

        // Methods that the keybindings use

        // Action doNothing = new AbstractAction() {

        // @Override
        // public void actionPerformed(ActionEvent e) {
        // // does nothing
        // }

        // };

        Action runGames = new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                runGames();
            }

        };

        Action findGames = new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                findGame();
            }

        };

        Action info = new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                toRules();
            }

        };

        Action backToGames = new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                backToGames();
            }

        };

        Action clear = new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                clear();
            }

        };

        // Key bindings

        startDiceInput.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "runGames");
        startDiceInput.getActionMap().put("runGames", runGames);

        numGamesInput.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "runGames");
        numGamesInput.getActionMap().put("runGames", runGames);

        gameIndexInput.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "findGames");
        gameIndexInput.getActionMap().put("findGames", findGames);

        gamePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
                "runGames");
        gamePanel.getActionMap().put("runGames", runGames);

        gamePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('?'), "info");
        gamePanel.getActionMap().put("info", info);

        gamePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0),
                "clear");
        gamePanel.getActionMap().put("clear", clear);

        gamePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                "returnToGames");
        gamePanel.getActionMap().put("returnToGames", backToGames);

        // restores the panel to what it was before the user viewed the rules panel
        if (viewingGameHistory) {
            findGame(gameIndex);
        } else {
            outputArea.setText(game);
        }

        if (startDiceInput.getText().equals("2")) {
            startDiceInput.setForeground(Color.gray);
        } else {
            startDiceInput.setForeground(Color.black);
        }

        if (numGamesInput.getText().equals("1000")) {
            numGamesInput.setForeground(Color.gray);
        } else {
            numGamesInput.setForeground(Color.black);
        }

        frame.setVisible(true);
    }

    /**
     * read in inputs for number of start dice and games, then instantiates Dice
     * class to run each game and saves the results: history to a linked list of
     * strings, and results to a single string. It also finds the longest game
     * played (by rounds) and saves information on that to a string as well
     */
    public void runGames() {
        int[] winners = new int[NUMBER_OF_WINNERS];
        int startDice;
        int numGames;
        int maxRoundIndex = 0;
        game = "";
        games.clear();
        startDiceText = startDiceInput.getText();
        numGamesText = numGamesInput.getText();
        try {
            startDice = Integer.parseInt(startDiceText);
        } catch (Exception e) {
            startDice = 2;
            startDiceInput.setText("2");
            startDiceInput.setForeground(Color.gray);
        }
        try {
            numGames = Integer.parseInt(numGamesText);
        } catch (Exception e) {
            numGames = 1000;
            numGamesInput.setText("1000");
            numGamesInput.setForeground(Color.gray);
        }
        for (int i = 0; i < numGames; i++) {
            System.out.println(i);
            Dice roll = new Dice(startDice);
            int victor = roll.game();
            game += "Game: " + (i + 1) + "\tWinner: " + roll.getWinner() + "\tRounds: " + roll.getRound()
                    + "\n";
            winners[victor]++;
            games.add(roll);
            if (roll.getRound() > games.get(maxRoundIndex).getRound()) {
                maxRoundIndex = i;
            }
        }
        game += "\n" + Arrays.toString(winners);
        outputArea.setText(game);
        longGame = "Longest Game at Game: " + (maxRoundIndex + 1) + "\nRounds: "
                + games.get(maxRoundIndex).getRound();
        longestGameText = longGame;
        longestGame.setText(longestGameText);
        returnToGames.setVisible(false);
        viewingGameHistory = false;
    }

    /**
     * finds a game based on an index given by the user by reading in the input on
     * the gameIndex textField after the user hits enter or clicks the find game
     * button
     */
    public void findGame() {
        try {
            gameIndex = Integer.parseInt(gameIndexInput.getText());
            outputArea.setText(games.get(gameIndex - 1).getHistory());
            longestGameText = "You are viewing game " + gameIndex;
            longestGame.setText(longestGameText);
            returnToGames.setVisible(true);
            viewingGameHistory = true;
        } catch (Exception e) {
            longestGameText = "Enter valid game index";
            longestGame.setText(longestGameText);
        }
        gameIndexInput.setText("");
    }

    /**
     * finds a game based on an index, only called when the user returns to the game
     * panel and was already viewing the game before looking at the rules
     * 
     * @param gameIndex - the index of the game that was already being viewed by the
     *                  user when they switched to the rules panel
     */
    public void findGame(int gameIndex) {
        try {
            outputArea.setText(games.get(gameIndex - 1).getHistory());
            longestGameText = "You are viewing game " + gameIndex;
            longestGame.setText(longestGameText);
            returnToGames.setVisible(true);
            viewingGameHistory = true;
        } catch (Exception e) {
            longestGameText = "Enter valid game index";
            longestGame.setText(longestGameText);
        }
        gameIndexInput.setText("");
    }

    /**
     * clears/resets all the stored information, text areas, output, and visuals on
     * screen to their original state
     */
    public void clear() {
        game = "";
        outputArea.setText(game);
        longestGameText = "Longest Game at Game: Rounds:";
        longestGame.setText(longestGameText);
        startDiceText = "2";
        startDiceInput.setText(startDiceText);
        startDiceInput.setForeground(Color.gray);
        numGamesText = "1000";
        numGamesInput.setText(numGamesText);
        numGamesInput.setForeground(Color.gray);
        gameIndex = 0;
        viewingGameHistory = false;
        gameIndexInput.setText("");
        games.clear();
    }

    /**
     * switches panel from game panel to rules panel
     */
    public void toRules() {
        gamePanel.setVisible(false);
        rules();
    }

    /**
     * returns user from the game they are currently viewing to the summary of each
     * game
     */
    public void backToGames() {
        viewingGameHistory = false;
        outputArea.setText(game);
        returnToGames.setVisible(false);
        longestGame.setText(longGame);
    }

    /**
     * GUI for the rules panel that explains the rules of DiceGame
     */
    public void rules() {

        rulesPanel = new JPanel(new GridBagLayout());
        rulesPanel.setVisible(true);
        frame.setContentPane(rulesPanel);

        JLabel lineStart = new JLabel();
        c.gridx = 0;
        c.gridwidth = 1;
        c.gridy = 0;
        c.gridheight = 3;
        c.anchor = CENTER;
        c.fill = BOTH;
        rulesPanel.add(lineStart, c);

        JLabel lineEnd = new JLabel();
        c.gridx = 2;
        c.gridwidth = 1;
        c.gridy = 0;
        c.gridheight = 3;
        c.anchor = CENTER;
        c.fill = BOTH;
        rulesPanel.add(lineEnd, c);

        rulesLabel = new JLabel("Rules of 'Dice Roller'");
        rulesLabel.setFont(new Font("Cambria", Font.BOLD, 30));
        c.gridx = 1;
        c.gridwidth = 1;
        c.gridy = 0;
        c.gridheight = 1;
        c.anchor = CENTER;
        c.fill = NONE;
        c.ipadx = 30;
        c.ipady = 30;
        rulesPanel.add(rulesLabel, c);
        rulesLabel.setVisible(true);

        rules = new JTextArea();
        rules.setLineWrap(true);
        rules.setWrapStyleWord(true);
        rules.setFont(new Font("Times New Roman", Font.PLAIN, 24));
        rules.setMargin(new Insets(30, 30, 30, 30));
        c.gridx = 1;
        c.gridwidth = 1;
        c.gridy = 1;
        c.gridheight = 1;
        c.anchor = CENTER;
        c.fill = BOTH;
        c.ipadx = 0;
        c.ipady = 0;
        rulesPanel.add(rules, c);
        rules.setVisible(true);
        rules.setEditable(false);
        rules.setText(
                "In 'Dice Game' there are 6 types of dice: d4, d6, d8, d10, d12, and d20 (number "
                        + "corresponds to number of sides on the dice). A game consists of rounds where"
                        + " each die is rolled and if the die lands on a 1 it is removed. If that die, "
                        + "however, were to land on the maximum possible number for its type, another die"
                        + " of that type is added to the game. A game begins with each type having the same"
                        + " number of dice, and lasts until there is at most 1 type of dice remaining. In "
                        + "the output text area there are 2 items of interest: a game summary for each "
                        + "game--with the game number, winner, and number of rounds in the game--and at the"
                        + " very bottom an array of the total number of times a type won. There are seven "
                        + "numbers in the array because ties occur--one round removes all remaining types of"
                        + " dice--and must thus be counted as a \"winner\", denoted as a 0 in the winner "
                        + "column of the game summary.");

        backButton = new JButton("Back");
        c.gridx = 1;
        c.gridwidth = 1;
        c.gridy = 2;
        c.gridheight = 1;
        c.anchor = CENTER;
        c.fill = NONE;
        c.ipadx = 50;
        c.ipady = 50;
        rulesPanel.add(backButton, c);

        backButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                retruntoMainPanel();
            }

        });

        Action goBack = new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                retruntoMainPanel();
            }

        };
        rulesPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                "goBack");
        rulesPanel.getActionMap().put("goBack", goBack);

        frame.setVisible(true);
    }

    /**
     * switches panel from rules back to game panel
     */
    public void retruntoMainPanel() {
        rulesPanel.setVisible(false);
        uxPanel();
    }
}
