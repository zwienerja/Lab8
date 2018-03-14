/*
 * LAb 8 starter code
 * Theater revenue problem
  * This demonstrates a GUI program, with a Model/View/Controller architecture
 *  * comments intentionally left out to shorten code for printout
 * 
 * written by C.Anderson,  CSIS150 fall 2013
 *
 * modified by Sherri Harms, Fall 2017
 *  @author canderson
 * @author harmssk
 * @author zwienerja
 *
 * sources: SimpleEditor.java --> from gui folder
 */

import gui.SimpleEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Creates a pop-up window where user user inputs ticket sales and revenue is calculates based on input
 */
public class TheaterRevenueWindow extends JFrame {
    
    TheaterRevenues theaterRev; //the object that stores the data for this class
    JTextField pricePerAdultTxt, numberAdultTxt, pricePerChildTxt, numberChildTxt;
    JTextField grossAdultRevTxt, netAdultRevTxt, grossChildRevTxt, netChildRevTxt;
    JTextField totalGrossRevTxt, totalNetRevTxt;
    JLabel pricePerAdultLab, numberAdultsLab, pricePerChildLab, numberChildLab;
    JButton calcButton, clearButton, fileButton, helpButton;
    JMenu menuButton;
    JMenuItem exitMenuItem, aboutMenuItem, helpMenuItem;

    /**
     * constructor that creates the revenue window
     */
    public TheaterRevenueWindow()
    {
        theaterRev = new TheaterRevenues();
        initalizeWindow();
        addButtonListener();
     }

    /**
     * sets up the revenue window
     */
    private void initalizeWindow()
    {
        this.setTitle("Theater Revenue Calculator");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.createDisplay();
        pack();
        this.setVisible(true);
    }

    /**
     * Create a menu bar
     *
     * @return The created menu bar
     */
    private JMenuBar buildMenuBar(){
        // Create a menu bar
        JMenuBar menuBar = new JMenuBar();

        // Call "buildFileMenu()" to create a menu and add it into the menu bar
        JMenu fileMenu = buildFileMenu();
        menuBar.add(fileMenu);

        //put space between the File and the Help Menus
        Label whiteSpace = new Label("                        ");
        menuBar.add(whiteSpace);

        JMenu helpMenu = buildHelpMenu();
        menuBar.add(helpMenu);

        return menuBar;
    }

    /**
     * Create the Help menu
     *
     * @return The created help menu
     */
    private JMenu buildHelpMenu(){
        // Create
        JMenu helpMenu = new JMenu("Help");

        // Create the menu items
        helpMenuItem = new JMenuItem("View Help");
        helpMenu.add(helpMenuItem);
        // Hook up the menu items with the listener
        MyListener helpListener = new MyListener();
        helpMenuItem.addActionListener(helpListener);

        return helpMenu;
    }

    /**
     * Create the file menu
     *
     * @return The created file menu
     */
    private JMenu buildFileMenu(){
        // Create
        JMenu fileMenu = new JMenu("File");

        // Create the menu items
        aboutMenuItem = new JMenuItem("About");
        exitMenuItem = new JMenuItem("Exit");

        // Add these menu items into fileMenu
        fileMenu.add(aboutMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);

        // Hook up the menu items with the listener
        MyListener listener = new MyListener();
        exitMenuItem.addActionListener(listener);
        aboutMenuItem.addActionListener(listener);

        return fileMenu;
    }

    /**
     * creates the nexted panels that make up the display, than adds the components to them
     */
    private void createDisplay()
    {
        Font boldFont = new Font("Arial", Font.BOLD, 14);
        Font norFont = new Font("Verdana", Font.PLAIN, 4);

        //create menuBar
        JMenuBar bar = buildMenuBar();
        setJMenuBar(bar);

        // ticket panel
        JPanel tickPanel = new JPanel(new GridLayout(5,2));
        
        JLabel lab = new JLabel();
        lab = new JLabel();
        
        lab.setFont(boldFont);
        lab.setText("Tickets sold");
        tickPanel.add(lab);
        lab = new JLabel();
        tickPanel.add(lab);
        
        pricePerAdultLab = new JLabel("   Adult ticket price");
        tickPanel.add(pricePerAdultLab);
        pricePerAdultTxt = new JTextField(""+theaterRev.getDefaultAdultPrice());
        pricePerAdultTxt.setToolTipText("Price per adult ticket, must be whole positive number!\n");
        tickPanel.add(pricePerAdultTxt);
        numberAdultsLab = new JLabel("   Number adult tickets sold");
        tickPanel.add(numberAdultsLab);
        numberAdultTxt = new JTextField(""+theaterRev.getDefaultAdultTickets());
        tickPanel.add(numberAdultTxt);
        
        pricePerChildLab = new JLabel("   Child ticket price");
        tickPanel.add(pricePerChildLab);
        pricePerChildTxt = new JTextField(""+theaterRev.getDefaultChildPrice());
        tickPanel.add(pricePerChildTxt);
        numberChildLab = new JLabel("   Number child tickets sold");
        tickPanel.add(numberChildLab);
        numberChildTxt = new JTextField(""+theaterRev.getDefaultAdultTickets());
        tickPanel.add(numberChildTxt);
        tickPanel.setBorder(BorderFactory.createBevelBorder(1));
        
        this.getContentPane().add(tickPanel,BorderLayout.WEST);
        
        // revenu panel
        JPanel revPanel = new JPanel(new GridLayout(5,2));
        lab = new JLabel("         Revenues");
        lab.setFont(boldFont);
        revPanel.add(lab);
        clearButton = new JButton("Clear");
        revPanel.add(clearButton);
        
        // adult rev
        lab = new JLabel("      Gross Rev. Adult");
        revPanel.add(lab);
        grossAdultRevTxt = new JTextField();
        grossAdultRevTxt.setEditable(false);
        revPanel.add(grossAdultRevTxt);
        lab = new JLabel("      Net Rev. Adult");
        revPanel.add(lab);
        netAdultRevTxt = new JTextField();
        netAdultRevTxt.setEditable(false);
        revPanel.add(netAdultRevTxt);
        
        // child rev
        lab = new JLabel("      Gross Revenues Child");
        revPanel.add(lab);
        grossChildRevTxt = new JTextField();
        grossChildRevTxt.setEditable(false);
        revPanel.add(grossChildRevTxt);
        lab = new JLabel("      Net Rev. Child");
        revPanel.add(lab);
        netChildRevTxt = new JTextField();
        netChildRevTxt.setEditable(false);
        revPanel.add(netChildRevTxt);
  //      revPanel.setBorder(BorderFactory.createEtchedBorder(1));
        this.getContentPane().add(revPanel,BorderLayout.EAST);
        
        JPanel southPanel = new JPanel();
        calcButton = new JButton("Calculate Revenues");
        southPanel.add(calcButton);
      
        lab = new JLabel("      Total Gross Rev:");
        southPanel.add(lab);
        totalGrossRevTxt = new JTextField(8);
        totalGrossRevTxt.setEditable(false);
        southPanel.add(totalGrossRevTxt);
        lab = new JLabel("      Total Net Rev:");
        southPanel.add(lab);
        totalNetRevTxt = new JTextField(8);
        totalNetRevTxt.setEditable(false);
        southPanel.add(totalNetRevTxt);
    //    southPanel.setBorder(BorderFactory.createEtchedBorder());
        
        this.getContentPane().add(southPanel,BorderLayout.SOUTH);
    }

    /**
     * adds a buttonListener to listen for the calculate and clear buttons
     */
    private void addButtonListener()
    {
        // Connect the buttons with the event listener
        MyListener listener = new MyListener();
        calcButton.addActionListener(listener);
        clearButton.addActionListener(listener);
    }

    /**
     * This methiod needs to be called if the clear button is clicked.
     */
    private void clearAllFields()
    {
        pricePerAdultTxt.setText(""+theaterRev.getDefaultAdultPrice());
        numberAdultTxt.setText(""+theaterRev.getDefaultAdultTickets());
        pricePerChildTxt.setText(""+theaterRev.getDefaultChildPrice());
        numberChildTxt.setText(""+theaterRev.getDefaultChildTickets());
        grossAdultRevTxt.setText("");
        netAdultRevTxt.setText("");
        grossChildRevTxt.setText("");
        netChildRevTxt.setText("");
        totalGrossRevTxt.setText("");
        totalNetRevTxt.setText("");
    }

    /**
     * This method needs to be called if the calculate button is clicked.
     */
    private void calcAdultRevs()
    {
         //set the TicketReveune object values from the textfield values
        // call the TicketRevenue methods to calculate revenue
        // update the window's textfeilds to show the revenue values
        theaterRev.setAdultPrice(Double.parseDouble(pricePerAdultTxt.getText()));
        theaterRev.setAdultTickets(Integer.parseInt(numberAdultTxt.getText()));

        grossAdultRevTxt.setText(String.format("%8.2f", theaterRev.calcGrossAdultSales()));
        netAdultRevTxt.setText(String.format("%8.2f", theaterRev.calcNetAdultSales()));

    }

    /**
     * This method needs to be called if the calculate button is clicked.
     */
    private void calcChildRevs()
    {
        theaterRev.setChildPrice(Double.parseDouble(pricePerChildTxt.getText()));
        theaterRev.setChildTickets(Integer.parseInt(numberChildTxt.getText()));

        grossChildRevTxt.setText(String.format("%8.2f", theaterRev.calcGrossChildSales()));
        netChildRevTxt.setText(String.format("%8.2f", theaterRev.calcNetChildSales()));
        //See the caldAdultRevs for comments on what goes in this method
    }

    /**
     * This method needs to be called if the calculate button is clicked.
     */
    private void calcRevs()
    {
        calcAdultRevs();
        calcChildRevs();
        totalGrossRevTxt.setText(String.format("%8.2f", theaterRev.calcTotalGross()));
        totalNetRevTxt.setText(String.format("%8.2f", theaterRev.calcTotalNet()));
        //adds all of the revenue together
    }

    /**
     * this method is called first when the calculate button is clicked
     * @return true if all of the user input is valid
     */
    private boolean validateFields()
    {   
        String errorMessage = "";
        errorMessage += validateAdultPrice();
        errorMessage += validateNumberAdult();
        
        errorMessage += validateChildPrice();
        errorMessage += validateNumberChild();
       
        if(!errorMessage.equals(""))
        {  
            JOptionPane.showMessageDialog(null, "Error in Data Entry:\n"+
                        errorMessage,"Data Entry Errors",1);
            return false;
        }
        return true;
    }

    /**
     * validates the number of children tickets sold
     * @return error message
     */
    private String validateNumberChild()
    {  
        int cTicks = 0;
        String errorMessage = "";
        numberChildLab.setForeground(Color.black);
        if(numberChildTxt.getText().equals(""))
        {   numberChildLab.setForeground(Color.red);
            errorMessage+="\nChild tickets field needs a value:\n"+
                    "      positive whole number ";
        }     
        else
        {   try
            {   cTicks = Integer.parseInt(numberChildTxt.getText());
                if (cTicks < 0) 
                {   numberChildLab.setForeground(Color.red);
                    errorMessage += "\nNumber in child tickets field is"+
                            " negative\n"
                            + "     enter a positive whole number \n";           
                }
            }catch(NumberFormatException nme)
            {   numberChildLab.setForeground(Color.red);
                errorMessage+="\nNumber in child tickets field contains an"+
                        " invalid 'integer'\n"+
                        "     enter a positive whole number \n";     
            }
        }
        return errorMessage;
     }

    /**
     * validates the number of adult tickets sold
     * @return error message
     */
    private String validateNumberAdult()
     {  
        int aTicks = 0;
        String errorMessage = "";
        numberAdultsLab.setForeground(Color.black);
        if(numberAdultTxt.getText().equals(""))
        {   numberAdultsLab.setForeground(Color.red);
            errorMessage+="\nAdult tickets field needs a value:\n"+""
                    + "      enter a positive whole number\n";
        }     
        else
        {   try
            {   aTicks = Integer.parseInt(numberAdultTxt.getText());
                if (aTicks < 0) 
                {   numberAdultsLab.setForeground(Color.red);
                    errorMessage += "\nNumber in adult tickets field is negative\n"
                        + "     enter a positive whole number \n";
                }
            }catch(NumberFormatException nme)
            {   numberAdultsLab.setForeground(Color.red);
                errorMessage+="\nNumber in adult tickets field contains an"+""
                        + " invalid 'integer'\n"+
                        "     enter a positive whole number \n";
            }
        }
        return errorMessage;
     }

    /**
     * validates the price of an adult ticket
     * @return errorMessage
     */
    private String validateAdultPrice()
    {   
        double aPrice = 0;
        String errorMessage = "";
        pricePerAdultLab.setForeground(Color.black);
        if(pricePerAdultTxt.getText().equals(""))
        {   pricePerAdultLab.setForeground(Color.red);
            errorMessage+="\nAdult price fields needs a value:\n    positive"+""
                    + " dollar amount\n";
        }     
        else
        {   try
            {   aPrice = Double.parseDouble(pricePerAdultTxt.getText());
               
                if (aPrice < 1) 
                {   pricePerAdultLab.setForeground(Color.red);
                    errorMessage += "\nprice of adult tickets field is zero"+""
                            + " or less\n"
                            + "     enter a positive whole number \n";
                }
            }catch(NumberFormatException nme)
            {   errorMessage+="\nAdult price fields contains an invalid"+""
                    + " dollar amount\n"+
                        "      enter a positive dollar amount \n";
                pricePerAdultLab.setForeground(Color.red);
            }
        }
        return errorMessage;
    }

    /**
     * validates the price of a children's ticket
     * @return errorMessage
     */
    private String validateChildPrice()
    {   
        double cPrice = 0;
        String errorMessage = "";
        pricePerChildLab.setForeground(Color.black);
        if(pricePerChildTxt.getText().equals(""))
        {   pricePerChildLab.setForeground(Color.red);
            errorMessage+="\nChild price fields needs a value:\n"+
                    "     positive dollar amount\n";
        }     
        else
        {   try
            {   cPrice = Double.parseDouble(pricePerChildTxt.getText());
                if (cPrice < 1) 
                {   pricePerChildLab.setForeground(Color.red);
                    errorMessage += "\nprice of child tickets field is"+
                            " zero or less\n"
                            + "     enter a positive whole number \n";
                }
            }catch(NumberFormatException nme)
            {   pricePerChildLab.setForeground(Color.red);
                errorMessage+="\nChild price fields contains an invalid"+""
                        + " dollar amount\n"+
                        "     enter a positive dollar amount \n";
            }
        }
        return errorMessage;
    }

    /**
     * The listener to respond to  button events
        *     */
    private class MyListener implements ActionListener{
        public void actionPerformed (ActionEvent e){
            if(e.getSource() == calcButton)
            {
                if (validateFields()) {
                    calcRevs();
                }
                // validate the fields - call validateFields
                // calculate revenues
                // update text fields appropriately
            } else if(e.getSource() == clearButton){
                // clear the fields - call clearAllFields method
                clearAllFields();
            }
            else if (e.getSource() == exitMenuItem) {
                System.exit(0);
            }
            else if(e.getSource() == helpMenuItem){
                // Show a new window for the help info
                JFrame helpFrame = new JFrame();  //set starting point
                helpFrame.setTitle("Example Help");
                helpFrame.setSize(300, 100);
                // close this window on exit, but do not close the whole program
                helpFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                JTextArea helpText = new JTextArea();
                helpText.append("User must enter the price of adult and child \ntickets along with the number of each sold.  \nThen click the Calculate Revenues button.");
                helpFrame.add(helpText);
                //helpFrame.pack();
                helpFrame.setVisible(true);
            }
            else if(e.getSource() == aboutMenuItem){
                //finish this "about this program" here
                JFrame helpFrame = new JFrame();  //set starting point
                helpFrame.setTitle("About:");
                helpFrame.setSize(400, 100);
                // close this window on exit, but do not close the whole program
                helpFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                JTextArea helpText = new JTextArea();
                helpText.append("This project requires the user to input ticket sales and ticket \nprices. It will then calculate the gross and net revenues \nbased on the numbers entered.");
                helpFrame.add(helpText);
                //helpFrame.pack();
                helpFrame.setVisible(true);
            }
        }
    }

    /**
     * Main method, calls methods to create the theaterRevenueWindow
     * @param agrs
     */
    public static void main(String[] agrs)
    {
        TheaterRevenueWindow trw = new TheaterRevenueWindow();
    }
    
}
