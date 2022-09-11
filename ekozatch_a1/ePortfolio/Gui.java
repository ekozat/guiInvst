package ePortfolio;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import ePortfolio.Portfolio;

import java.util.ArrayList;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * A GUI object.
 * Child to the JFrame class. Helps build the interface with a
 * Swing application. The object holds all choice panels as well as
 * functions for each interaction between the user and GUI. The class
 * implements event-driven programming to display data dynamically.
 * Connects to Portfolio Class functionalities.
 */
public class Gui extends JFrame{
    
    public static final int WIDTH = 700;
    public static final int HEIGHT = 500;

    private JPanel introPanel, buyPanel, sellPanel,
                   updatePanel, getGainPanel, searchPanel;
    private Portfolio portfolio;
    private ArrayList<Investment> arr;

    private boolean notEmpty = false;
    private int index = 0;
    private JTextField upSymbolField, upNameField, gainField;

    private String areaLabel = "Messages";

    /**
     * Gui constructor.
     * Implements prep and functionalities.
     */
    public Gui(){
        super("Investments Gui");
        portfolio = new Portfolio();
        prepGui();
    }

    /**
     * Main class.
     * The user interface is stored/executed where user
     * will choose interaction with the two arrayLists storing all 
     * investment objects.
     * 
     * @author Emily Kozatchiner (1149665)
     * @date Nov 30, 2021
     */
    public static void main (String[] args){
        Gui window = new Gui();
        window.setVisible(true); 
    }
    
    /**
     * Private Listener Class.
     * When the buyChoice menu item is accessed, the Gui is
     * changed following the event.
     */
    private class BuyListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            setVisFalse();
            buyPanel.setVisible(true);
        }
    }

    /**
     * Private Listener Class.
     * When the sellChoice menu item is accessed, the Gui is
     * changed following the event.
     */
    private class SellListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            setVisFalse();
            sellPanel.setVisible(true);
        }
    }

    /**
     * Private Listener Class.
     * When the updateChoice menu item is accessed, the Gui is
     * changed following the event.
     */
    private class UpdateListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            setVisFalse();
            updatePanel.setVisible(true);

            if (notEmpty){
                arr = portfolio.getArr();
                upNameField.setText(arr.get(index).getName());
                upSymbolField.setText(arr.get(index).getSmbl());
            }
        }
    }

    /**
     * Private Listener Class.
     * When the getGainChoice menu item is accessed, the Gui is
     * changed following the event.
     */
    private class GetGainListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            setVisFalse();
            getGainPanel.setVisible(true);

            JTextArea display = new JTextArea(100, 100);
            getGainPanel.add(displayTextArea(display, areaLabel));
            displayGain(display);
        }
    }

    /**
     * Private Listener Class.
     * When the searchChoice menu item is accessed, the Gui is
     * changed following the event.
     */
    private class SearchListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            setVisFalse();
            searchPanel.setVisible(true);
        }
    }

    /**
     * Private Listener Class.
     * When the quitChoice menu item is accessed, the Gui is
     * exited out of and the program stops.
     */
    private class QuitListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            System.exit(0);
        }
    }

    /**
     * Private Listener Class.
     * When a button event is triggered, the Gui/data
     * is changed following the event.
     * Encasing for Anonymous Listener Classes.
     */
    private class ButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
        }
    }

    /**
     * Interface Functionalities.
     * The function sets up the front-end interactions
     * with the user through keyboard and mouse input.
     */
    private void prepGui(){
        setSize(WIDTH, HEIGHT); //resolution
        setTitle("ePortfolio"); //title
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));

        //Adding Options to the Command Menu 
        JMenu commandMenu = new JMenu("Commands");

        JMenuItem buyChoice = new JMenuItem("Buy");
        buyChoice.addActionListener(new BuyListener( ));
        commandMenu.add(buyChoice);

        JMenuItem sellChoice = new JMenuItem("Sell");
        sellChoice.addActionListener(new SellListener( ));
        commandMenu.add(sellChoice);

        JMenuItem updateChoice = new JMenuItem("Update");
        updateChoice.addActionListener(new UpdateListener( ));
        commandMenu.add(updateChoice);

        JMenuItem getGainChoice = new JMenuItem("Get Gain");
        getGainChoice.addActionListener(new GetGainListener( ));
        commandMenu.add(getGainChoice);

        JMenuItem searchChoice = new JMenuItem("Search");
        searchChoice.addActionListener(new SearchListener( ));
        commandMenu.add(searchChoice);

        JMenuItem quitChoice = new JMenuItem("Quit");
        quitChoice.addActionListener(new QuitListener( ));
        commandMenu.add(quitChoice);

        JMenuBar bar = new JMenuBar( );
        bar.add(commandMenu);
        setJMenuBar(bar);

        //start user off with the initial panel
        initialPanel();

        buyPanel();
        sellPanel();
        updatePanel();
        getGainPanel();
        searchPanel();

        setVisFalse();
        introPanel.setVisible(true);
    }

    /**
     * Introduction Panel set up.
     * The private function creates the opening panel for introduction
     * to the program's purpose and usage.
     */
    private void initialPanel () {
        introPanel = new JPanel();
        introPanel.setLayout(new GridLayout(2, 1));
        add(introPanel);

        JLabel introLabel = new JLabel("   " + "Welcome to ePortfolio");
        introPanel.add(introLabel);

        JTextArea guideLabel = new JTextArea();
        guideLabel.setText("   Choose a command from the \"Commands\" menu to buy or sell\n   " +
                        "an investment, update prices for all investments, get gain for the\n   " +
                        "the portfolio, search for relevant investements, or quit the program.");
        guideLabel.setBackground(getBackground());
        guideLabel.setFont(new Font("Dialog", Font.BOLD, 12));
        guideLabel.setLineWrap(true);
        guideLabel.setEditable(false);
        introPanel.add(guideLabel);
    }

    /**
     * Buy Panel set up.
     * The private function creates the buy interface panel including
     * textfield input, button input, and textarea output.
     */
    private void buyPanel(){
        buyPanel = new JPanel();
        buyPanel.setLayout(new GridLayout(2,1));
        add(buyPanel);

        JPanel splitPanel = new JPanel();
        splitPanel.setLayout(new GridLayout(1,2));

        JPanel inputs = new JPanel();
        inputs.setLayout(new GridLayout(6,2));
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(3,1));

        JLabel title = new JLabel("   Buying an investment");
        inputs.add(title);
        JLabel buffer = new JLabel("");
        inputs.add(buffer);

        JLabel sType = new JLabel("\t\t\tType");
        inputs.add(sType);

        String[] type = {"stock", "mutualfund"};
        JComboBox<String> typeList = new JComboBox<>(type);
        inputs.add(typeList);

        JLabel sSymbol = new JLabel("\t\t\tSymbol");
        inputs.add(sSymbol);
        JTextField symbolField = new JTextField(5);
        inputs.add(symbolField);

        JLabel sName = new JLabel("\t\t\tName");
        inputs.add(sName);
        JTextField nameField = new JTextField(30);
        inputs.add(nameField);

        JLabel sQuant = new JLabel("\t\t\tQuantity");
        inputs.add(sQuant);
        JTextField quantField = new JTextField(5);
        inputs.add(quantField); 

        JLabel sPrice = new JLabel("\t\t\tPrice");
        inputs.add(sPrice);
        JTextField priceField = new JTextField(5);
        inputs.add(priceField);

        JTextArea display = new JTextArea(100, 100); 

        //Create seperate panel for button resizing
        JPanel reset = new JPanel();
        reset.setLayout(new FlowLayout());
        //Creates button
        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(new ButtonListener( ){
            public void actionPerformed(ActionEvent e){
                typeList.setSelectedIndex(0);
                symbolField.setText("");
                nameField.setText("");
                quantField.setText("");
                priceField.setText("");
                display.setText("");
            }
        });
        resetButton.setMaximumSize(new Dimension(300, 200));

        JPanel buy = new JPanel();
        buy.setLayout(new FlowLayout());
        JButton buyButton = new JButton("Buy");
        buyButton.addActionListener(new ButtonListener( ){
            public void actionPerformed(ActionEvent e){
                String err = portfolio.buyInvst((String)typeList.getSelectedItem(), symbolField.getText(),
                nameField.getText(), quantField.getText(), priceField.getText());
                display.setText(err);

                if (err.compareTo("") == 0){
                    notEmpty = true;
                }
            }
        });
        buyButton.setMaximumSize(new Dimension(300, 200));

        reset.add(resetButton);
        buy.add(buyButton);

        JLabel buffer2 = new JLabel("");
        buttons.add(buffer2);

        buttons.add(reset);
        buttons.add(buy);

        buyPanel.add(splitPanel);
        splitPanel.add(inputs);
        splitPanel.add(buttons);

        buyPanel.add(displayTextArea(display, areaLabel));
    }

    /**
     * Sell Panel set up.
     * The private function creates the sell interface panel including
     * textfield input, button input, and textarea output.
     */
    private void sellPanel(){
        sellPanel = new JPanel();
        sellPanel.setLayout(new GridLayout(2,1));
        add(sellPanel);

        JPanel splitPanel = new JPanel();
        splitPanel.setLayout(new GridLayout(1,2));

        JPanel inputs = new JPanel();
        inputs.setLayout(new GridLayout(4,2)); 
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(3,1));

        JLabel title = new JLabel("   Selling an investment");
        inputs.add(title);
        JLabel buffer = new JLabel("");
        inputs.add(buffer);

        JLabel sSymbol = new JLabel("\t\t\tSymbol");
        inputs.add(sSymbol);
        JTextField symbolField = new JTextField(5);
        inputs.add(symbolField);

        JLabel sQuant = new JLabel("\t\t\tQuantity");
        inputs.add(sQuant);
        JTextField quantField = new JTextField(5);
        inputs.add(quantField); 

        JLabel sPrice = new JLabel("\t\t\tPrice");
        inputs.add(sPrice);
        JTextField priceField = new JTextField(5);
        inputs.add(priceField);

        JTextArea display = new JTextArea(100, 100);

        //Create seperate panel for button resizing
        JPanel reset = new JPanel();
        reset.setLayout(new FlowLayout());
        //Creates button
        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(new ButtonListener( ){
            public void actionPerformed(ActionEvent e){
                symbolField.setText("");
                quantField.setText("");
                priceField.setText("");
                display.setText("");
            }
        });
        resetButton.setMaximumSize(new Dimension(300, 200));

        JPanel sell = new JPanel();
        sell.setLayout(new FlowLayout());
        JButton sellButton = new JButton("Sell");
        sellButton.addActionListener(new ButtonListener( ){
            public void actionPerformed(ActionEvent e){
                String err = portfolio.sellInvst(symbolField.getText(), quantField.getText(), 
                            priceField.getText());
                display.setText(err);
            }
        });
        sellButton.setMaximumSize(new Dimension(300, 200));

        reset.add(resetButton);
        sell.add(sellButton);

        JLabel buffer2 = new JLabel("");
        buttons.add(buffer2);

        buttons.add(reset);
        buttons.add(sell);

        sellPanel.add(splitPanel);
        splitPanel.add(inputs);
        splitPanel.add(buttons);

        sellPanel.add(displayTextArea(display, areaLabel));
    }

    /**
     * Update Panel set up.
     * The private function creates the update interface panel including
     * textfield input, button input, and textarea output.
     */
    private void updatePanel(){

        updatePanel = new JPanel();
        updatePanel.setLayout(new GridLayout(2,1));
        add(updatePanel);

        JPanel splitPanel = new JPanel();
        splitPanel.setLayout(new GridLayout(1,2));

        JPanel inputs = new JPanel();
        inputs.setLayout(new GridLayout(4,2));
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(4,1));

        JLabel title = new JLabel("   Updating investments");
        inputs.add(title);
        JLabel buffer = new JLabel("");
        inputs.add(buffer);

        JLabel sSymbol = new JLabel("\t\t\tSymbol");
        inputs.add(sSymbol);
        upSymbolField = new JTextField(5);
        inputs.add(upSymbolField);
        upSymbolField.setEditable(false);

        JLabel sName = new JLabel("\t\t\tName");
        inputs.add(sName);
        upNameField = new JTextField(30);
        inputs.add(upNameField);
        upNameField.setEditable(false);

        JLabel sPrice = new JLabel("\t\t\tPrice");
        inputs.add(sPrice);
        JTextField priceField = new JTextField(5);
        inputs.add(priceField);

        JTextArea display = new JTextArea(100, 100);

        //Create seperate panel for button resizing
        JPanel prev = new JPanel();
        prev.setLayout(new FlowLayout());
        //Creates button
        JButton prevButton = new JButton("Prev");
        prevButton.addActionListener(new ButtonListener( ){
            public void actionPerformed(ActionEvent e){
                ArrayList<Investment> arr = portfolio.getArr();
                String err = "";
                
                if (arr.size() > 0){
                    if (index > 0){
                        index--;
                    }
                    else{
                        err = "Reached beginning of investments";
                    }
                    upNameField.setText(arr.get(index).getName());
                    upSymbolField.setText(arr.get(index).getSmbl());
                }
                else{
                    err = "No investments found.";
                }
                display.setText(err);
            }
        });
        prevButton.setMaximumSize(new Dimension(300, 200));

        JPanel next = new JPanel();
        next.setLayout(new FlowLayout());
        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(new ActionListener( ){
            public void actionPerformed(ActionEvent e){
                ArrayList<Investment> arr = portfolio.getArr();
                String err = "";
                
                if (arr.size() > 0){
                    if (index < arr.size()-1){
                        index++;
                    }
                    else{
                        err = "Reached end of investments";
                    }
                    upNameField.setText(arr.get(index).getName());
                    upSymbolField.setText(arr.get(index).getSmbl());
                }
                else{
                    err = "No investments found.";
                }
                display.setText(err);
            }
        });
        nextButton.setMaximumSize(new Dimension(300, 200));

        JPanel save = new JPanel();
        save.setLayout(new FlowLayout());
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ButtonListener( ){
            public void actionPerformed(ActionEvent e){
                ArrayList<Investment> arr = portfolio.getArr();
                String err = "";

                if (arr.size() > 0){
                    err = portfolio.updateInvst(priceField.getText(), index);
                    priceField.setText("");
                }
                else{
                    err = "No investments found.";
                }
                display.setText(err);
            }
        });
        saveButton.setMaximumSize(new Dimension(300, 200));

        prev.add(prevButton);
        next.add(nextButton);
        save.add(saveButton);

        JLabel buffer2 = new JLabel("");
        buttons.add(buffer2);

        buttons.add(prev);
        buttons.add(next);
        buttons.add(save);

        updatePanel.add(splitPanel);
        splitPanel.add(inputs);
        splitPanel.add(buttons);

        updatePanel.add(displayTextArea(display, areaLabel));
    }

    /**
     * GetGain Panel set up.
     * The private function creates the total gain interface panel including
     * textfield and textarea output via calculation of data.
     */
    private void getGainPanel(){
        getGainPanel = new JPanel();
        getGainPanel.setLayout(new BorderLayout());
        add(getGainPanel);

        JPanel splitPanel = new JPanel();
        splitPanel.setLayout(new GridLayout(1,2));

        JPanel inputs = new JPanel();
        inputs.setLayout(new GridLayout(4,2));

        JLabel title = new JLabel("  Getting total gain");
        inputs.add(title);
        JLabel buffer = new JLabel("");
        inputs.add(buffer);

        JLabel space = new JLabel("");
        inputs.add(space);
        inputs.add(space);
        JLabel space2 = new JLabel("");
        inputs.add(space2);
        inputs.add(space2);

        JLabel sTotalGain = new JLabel("        Total gain");
        inputs.add(sTotalGain);
        gainField = new JTextField(5);
        inputs.add(gainField);
        gainField.setEditable(false);

        JLabel buffer2 = new JLabel("");

        splitPanel.add(inputs);
        splitPanel.add(buffer2);
        getGainPanel.add(splitPanel, BorderLayout.NORTH);
        
    }

    /**
     * Output of Gain Data.
     * The private function outputs each investment gain through a 
     * textarea and a total investment gain in the above textfield.
     */
    private void displayGain(JTextArea display){
        double ttlGain = 0;
        String strDisplay = "";
        ArrayList<Investment> arr = portfolio.getArr();

        for (int i = 0; i < arr.size(); i++){
            strDisplay += "Investment: " + arr.get(i).getSmbl() + 
                            "\nGain: $" + arr.get(i).getGain() +
                            "\n";
            ttlGain += arr.get(i).getGain();
        }

        display.setText(strDisplay);
        gainField.setText("$ " + ttlGain);
    }

    /**
     * Search Panel set up.
     * The private function creates the search interface panel including
     * textfield input, button input, and textarea output.
     */
    private void searchPanel(){
        searchPanel = new JPanel();
        searchPanel.setLayout(new GridLayout(2,1));
        add(searchPanel);

        JPanel splitPanel = new JPanel();
        splitPanel.setLayout(new GridLayout(1,2));

        JPanel inputs = new JPanel();
        inputs.setLayout(new GridLayout(5,2));
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(3,1));

        JLabel title = new JLabel("   Searching investments");
        inputs.add(title);
        JLabel buffer = new JLabel("");
        inputs.add(buffer);

        JLabel sSymbol = new JLabel("\t\t\tSymbol");
        inputs.add(sSymbol);
        JTextField symbolField = new JTextField(5);
        inputs.add(symbolField);

        JLabel sName = new JLabel("<html>Name<br>keywords</html>");
        inputs.add(sName);
        JTextField nameField = new JTextField(30);
        inputs.add(nameField);

        JLabel sLowPrice = new JLabel("\t\t\tLow price");
        inputs.add(sLowPrice);
        JTextField lowField = new JTextField(5);
        inputs.add(lowField);

        JLabel sHighPrice = new JLabel("\t\t\tHigh price");
        inputs.add(sHighPrice);
        JTextField highField = new JTextField(5);
        inputs.add(highField);

        JTextArea display = new JTextArea(100, 100);

        //Create seperate panel for button resizing
        JPanel reset = new JPanel();
        reset.setLayout(new FlowLayout());
        //Creates button
        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(new ButtonListener( ){
            public void actionPerformed(ActionEvent e){
                symbolField.setText("");
                nameField.setText("");
                lowField.setText("");
                highField.setText("");
                display.setText("");
            }    
        });
        resetButton.setMaximumSize(new Dimension(300, 200));
        reset.add(resetButton);

        JPanel search = new JPanel();
        search.setLayout(new FlowLayout());
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ButtonListener( ){
            public void actionPerformed(ActionEvent e){
                ArrayList<Investment> tempArr;
                double lprice = -1;
                double hprice = -1;
                String err = "";

                try{
                    if (lowField.getText().compareTo("") != 0){
                        lprice = Double.parseDouble(lowField.getText());
                    }
                    if (highField.getText().compareTo("") != 0){
                        hprice = Double.parseDouble(highField.getText());
                    }
                } catch (NumberFormatException f){ 
                    err = "Inputted numeric value could not be converted.";
                }

                tempArr = portfolio.searchInvst(symbolField.getText(), nameField.getText(),
                                         lprice, hprice);
                if (err.compareTo("") == 0){
                    for (int i = 0; i < tempArr.size(); i++){
                        err += tempArr.get(i).toString();
                    }
                }

                display.setText(err);
            }
        });
        searchButton.setMaximumSize(new Dimension(300, 200));
        search.add(searchButton);

        JLabel buffer2 = new JLabel("");
        buttons.add(buffer2);

        buttons.add(reset);
        buttons.add(search);

        searchPanel.add(splitPanel);
        splitPanel.add(inputs);
        splitPanel.add(buttons);

        searchPanel.add(displayTextArea(display, "Search Results"));
    }

    /**
     * Dislay Panel set up.
     * The private function creates the display interface sub-panel including
     * the scrollable textarea used for output throughout the program.
     */
    private JPanel displayTextArea(JTextArea displayArea, String s){
        JPanel message = new JPanel();
        message.setLayout(new BorderLayout());

        JLabel sMessage = new JLabel(s); 
        message.add(sMessage, BorderLayout.NORTH);

        displayArea.setEditable(false);
        displayArea.setBackground(Color.WHITE);

        JScrollPane scrolledText = new JScrollPane(displayArea);
        scrolledText.setHorizontalScrollBarPolicy(
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrolledText.setVerticalScrollBarPolicy(
                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        message.add(scrolledText, BorderLayout.CENTER);

        //create border around text area
        message.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        return message;
    }

    /**
     * Reset Panels.
     * The private function makes all Panels invisible in order to switch
     * from the previous visible panel to a new panel.
     */
    private void setVisFalse(){
        introPanel.setVisible(false);
        buyPanel.setVisible(false);
        sellPanel.setVisible(false);
        updatePanel.setVisible(false);
        getGainPanel.setVisible(false);
        searchPanel.setVisible(false);
    }
}
