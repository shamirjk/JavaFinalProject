/**
 * ClientGUI file, create and draw the frame windows
 * @author Shamir & Alexander
 */

package shamir.alex.project;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.apache.log4j.Logger;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.Date;

public class GUI implements WindowConstants, ActionListener
{	
	final static Logger logger = Logger.getLogger(GUI.class);
	//Map collection of objects - can contains Online/Offline data
	private HashMap<String,CurrencyModule> currencies = new HashMap<String,CurrencyModule>();
		
	//declaration of the GUI components
	private JFrame frmCurrencyManager;
	private JMenuBar menuBar;
	
	private JMenu mntmNewMenuOptions;//////////////////////////////////////////////////
	private JMenu mntmNewMenuHelp;/////////////////////////////////////////////
	private JMenuItem mntmExit;////////////////////
	private JFrame frmAbout;
	
	private JMenuItem mntmRefresh;
	private JMenuItem mntmLoadFromFile;
	private JMenuItem mntmCalcOperator;
	private JMenuItem mntmAbout;
	private JLabel mntmRefreshTime;
	private JTabbedPane tabbedPane;
	private CalculatorWindow calcWindow;
	
	/**
	 * Create the application.
	 * @throws Exception 
	 */
	//public GUI(ConverterPanel calcWindow)
	public GUI()
	{
		initialize();
		frmCurrencyManager.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 */
	private void initialize()
	{
		logger.info("start draw GUI");
		//The main form of init and set.
		frmCurrencyManager = new JFrame();
		frmCurrencyManager.setTitle("Currency Manager By Shamir & Alex");
		frmCurrencyManager.setBounds(100, 100, 640, 480);
		frmCurrencyManager.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCurrencyManager.setMinimumSize(new Dimension(500, 300));
		frmCurrencyManager.setSize(800, 500);
		frmCurrencyManager.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                logger.info("Application Closed By User");
                e.getWindow().dispose();
            }
        });
		
		//The Calculator init and set
		calcWindow = new CalculatorWindow();
		calcWindow.setTitle("Currency Convertor");
		calcWindow.setBounds(100, 100, 640, 480);
		calcWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		calcWindow.setSize(300, 300);
		calcWindow.setResizable(false);
		calcWindow.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                logger.info("Convertor Window Closed");
                e.getWindow().dispose();
            }
        });
		
		
		//The Tab pane init and set.
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setTabPlacement(JTabbedPane.TOP);
		tabbedPane.setFont(new Font("Tahoma", Font.PLAIN, 15));
		frmCurrencyManager.add(tabbedPane, BorderLayout.CENTER);
		
		ChangeListener changeListener = new ChangeListener() {
		      public void stateChanged(ChangeEvent changeEvent) {
		        JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
		        int index = sourceTabbedPane.getSelectedIndex();
		        calcWindow.setCurrencyModule(currencies.get(sourceTabbedPane.getTitleAt(index)));
		        calcWindow.refreshCombo();
		        logger.info("Tab changed to: " + sourceTabbedPane.getTitleAt(index));
		      }
		    };
		    
		tabbedPane.addChangeListener(changeListener);
		
		//The menu Bar init
		menuBar = new JMenuBar();
		menuBar.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		frmCurrencyManager.setJMenuBar(menuBar);
		
		//////////////////////////////////////////////////////////////////////////////
		mntmNewMenuOptions = new JMenu("Options");
		mntmNewMenuOptions.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuBar.add(mntmNewMenuOptions);
		
		mntmNewMenuHelp = new JMenu("Help");
		mntmNewMenuHelp.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuBar.add(mntmNewMenuHelp);
		
		//The menu Bar Items set
		mntmAbout = new JMenuItem("About Application");
		mntmAbout.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmAbout.addActionListener(this);
		mntmNewMenuHelp.add(mntmAbout);
		
		
		//////////////////////////////////////////////////////////////////////////////
		
		//The menu Bar Items set
		mntmRefresh = new JMenuItem("Refresh Data");
		mntmRefresh.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmRefresh.addActionListener(this);
		mntmNewMenuOptions.add(mntmRefresh);
		
		//The menu Bar Items set
		mntmLoadFromFile = new JMenuItem("Load File");
		mntmLoadFromFile.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmLoadFromFile.addActionListener(this);
		mntmNewMenuOptions.add(mntmLoadFromFile);
		
		mntmCalcOperator = new JMenuItem("Currency Convertor");
		mntmCalcOperator.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmCalcOperator.addActionListener(this);
		mntmNewMenuOptions.add(mntmCalcOperator);
		
		mntmNewMenuOptions.addSeparator();
		mntmExit = new JMenuItem("Exit");
		mntmExit.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmExit.addActionListener(this);
		mntmNewMenuOptions.add(mntmExit);
		
	
		
		mntmRefreshTime = new JLabel();
		mntmRefreshTime.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		menuBar.add(mntmRefreshTime);
		
		//////////////////////////////////////////////
		frmAbout = new JFrame();
		frmAbout.setTitle("About");
		frmAbout.setBounds(100, 100, 320, 240);
		frmAbout.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmAbout.setSize(355, 370);
		frmAbout.setResizable(false);
		frmAbout.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                logger.info("About Window Closed");
                e.getWindow().dispose();
            }
        });
		
		JPanel pInfo;
		pInfo = new JPanel();
		
		JLabel aboutLabel = new JLabel("Currency Exchange Rate Application");
		
		
		pInfo.add(aboutLabel);
		
		JTextArea textArea = new JTextArea(13, 30);
		JScrollPane scrollPane = new JScrollPane(textArea); 
		textArea.setEditable(false);
		textArea.setText("   This Application Made by: \n"
				+ "   Alexander Djura ID 321774556\n"
				+ "                & \n"
				+ "   Shamir Kritzler ID 052993060 \n"
				+ "\n"
				+ "   Final Project in 2016 Java Course \n"
				+ "   Shenkar College of Design & Engineering \n"
				+ "   Software Engineering Department \n"
				+ "   Lecturer: Haim Michael \n"
				+ "\n"
				+ "   Version: 8.0 \n"
				+ "   \u00a9 2016 All Rights Reserved \n");
		
		pInfo.add(scrollPane);
		frmAbout.add(pInfo);
		//
	}

	/**
	 * Handling the events of the program.
	 * */
	@Override
    public void actionPerformed(ActionEvent action)
	{
		switch(action.getActionCommand().toString())
		{
			//Handle the online loading of the XML
			case "Refresh Data":
				logger.info("User ask to Refresh Data");
				loadFromOnline();
				break;
				
			case "Load File":
				logger.info("User ask to Load Data From File");
				loadFromOffLine();
				break;
			case "Currency Convertor":
				if (calcWindow.isVisible()==true) {
					logger.info("User Ask to hide Currency Convertor Window");
					calcWindow.setVisible(false);
				} else {
					logger.info("User Ask to show Currency Convertor Window");
					calcWindow.setVisible(true);
				}
				break;
			case "Add New Currency":
				logger.info("User ask to Add new Currency");	
				break;
				
			case "Exit":
				logger.info("User ask to Close The Application");	
				System.exit(DISPOSE_ON_CLOSE);
				break;		
			case "About Application":
				logger.info("User ask to Close The Application");	
				frmAbout.setVisible(true);
				break;		
		}
		
	}
	/**
	 * This function  return a strings collection that
	 * represent the column name inside the headline layout;
	 */
	private ArrayList<String> getColumnNames()
	{
		ArrayList<String> colNames = new ArrayList<String>();
		Collections.addAll(colNames,"Name","Unit","Code","Country","Rate","Change");
		return colNames;
	}
	
	/**
	 * This function paint the data that is on the screen
	 * @param currenciesMap
	 * @param Date
	 * @throws IOException 
	 */
	private void drawCurrenciesPanels(HashMap<String, Currency> currenciesMap,String date)
    {
		//This is the new panel - it contains the data
		JPanel containPanel = new JPanel();
		//Get the column names of the headline layout
		ArrayList<String> colName = getColumnNames();
		
		JPanel headLine = new JPanel();
		JLabel tagArr[] = new JLabel[colName.size()];
		
		//Set the grid layouts of the contained panel and head line panel
		containPanel.setLayout(new BorderLayout());
		
		 //Adds the converter panel into the bottom of the panel
		if(currencies.size() == 1){
			calcWindow.setCurrencyModule(currencies.get(date));
		}
        JPanel resultsPanel = new JPanel();
        resultsPanel.setLayout(new GridLayout(15,1));
        
		headLine.setLayout(new GridLayout(1, 6));
		
		//System.setProperty("titleColor", "0X328AA4");
		//Color c = Color.getColor("titleColor");
		
		System.setProperty("red", "0Xfb6542");
		System.setProperty("green", "0X36A930");
		
		
		
		headLine.setBackground(Color.GRAY);
		
		
		//Load column names to the array of labels 
		for(int i=0;i<colName.size();i++)
		{
			tagArr[i] = new JLabel(colName.get(i),SwingConstants.CENTER);
			tagArr[i].setFont(new Font("Thaoma", Font.BOLD, 14));
			tagArr[i].setForeground(Color.WHITE);
			headLine.add(tagArr[i]);
		}
		
		//Adds the headline in to the layout
		resultsPanel.add(headLine);
		
		//Paint the data of the currency
        Set<String> keys = currenciesMap.keySet();
        int counter = 0;
        for (String key : keys)
        {
        	//Gets the next currency
            Currency tmpCurr = currenciesMap.get(key);
            Color txtColor = Color.WHITE;
            //creates a new panel that will contain row data 
            JPanel panel = new JPanel();       					
            panel.setLayout(new GridLayout(1, 8));
            
            Color changeColor = Color.decode("#4CC552");
            //Sets the background label color
            if(counter%2 == 0)
            {
            	txtColor = Color.BLACK;
            }
            else
            {
            	panel.setBackground(Color.WHITE);
            	txtColor = Color.BLACK;
            }
            counter++;
          
            //Create JLable array
            JLabel arr[] = new JLabel[6];       
            //Create name JLabel
            arr[0] = new JLabel(tmpCurr.name, SwingConstants.CENTER);                
            arr[0].setFont(new Font("TimesRoman", Font.PLAIN, 14));
            arr[0].setForeground(txtColor);
            //Create unit JLabel
            arr[1] = new JLabel(String.valueOf(tmpCurr.unit), SwingConstants.CENTER);        
            arr[1].setFont(new Font("TimesRoman", Font.PLAIN, 14));
            arr[1].setForeground(txtColor);
            //Create code JLabel
            arr[2] = new JLabel(tmpCurr.currencyCode, SwingConstants.CENTER);                    
            arr[2].setFont(new Font("TimesRoman", Font.PLAIN, 14));
            arr[2].setForeground(txtColor);
            //Create country JLabel
            arr[3] = new JLabel(tmpCurr.country, SwingConstants.CENTER);                            
            arr[3].setFont(new Font("TimesRoman", Font.PLAIN, 14));
            arr[3].setForeground(txtColor);
            //Create rate JLabel
            arr[4] = new JLabel(String.valueOf(tmpCurr.rate), SwingConstants.CENTER);    
            arr[4].setFont(new Font("TimesRoman", Font.PLAIN, 14));
            arr[4].setForeground(txtColor);
            //Create change JLabel
            arr[5] = new JLabel(String.valueOf(tmpCurr.change), SwingConstants.CENTER);    
            arr[5].setFont(new Font("TimesRoman", Font.BOLD, 16));
            if(tmpCurr.change < 0){
            	changeColor = Color.RED;
            }
            arr[5].setForeground(changeColor);
            
            //For each iteration, add label to row panel
            for (JLabel label : arr) 
            {
                panel.add(label);
            }
         
            resultsPanel.add(panel);
        }

        containPanel.add(resultsPanel, BorderLayout.CENTER);
        //Add the panel to the tab pane
        //In Order to create new tab with date title
        tabbedPane.add(date,containPanel);
	}


	public void loadFromOnline(){
		//Creates online object that will get the file from server
		CurrencyModule on = new CurrencyModule();
		boolean exist = false;
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

		// Get the date today using Calendar object.
		Date today = Calendar.getInstance().getTime();        
		// Using DateFormat format method we can create a string 
		// representation of a date with the defined format.
		String reportDate = df.format(today);
		String date;

		try
		{
			on.onlineData();
			//Get time of the XML 
		}
			//Puts the object from online into the objects map
		catch (IOException e)
		{
			logger.error("Can't Load Data From Server");
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "There was a problem to load data from server.\n\nCheck for internet connection and try again.","Error",JOptionPane.OK_OPTION);
			
		}
		
		try{
			on.offlineData("Offline.xml");
			//Gets current time of the file
		}
		catch (IOException o){
			logger.error("Can't Load Data From Selected File");
			o.printStackTrace();
			JOptionPane.showMessageDialog(null, "There was a problem to load data from the selected file,The file may be corrupted.\n\nPlease try again or choose different file.","Error",JOptionPane.OK_OPTION);
		}	
		date = on.getUpdateTime(on.getDoc());	
		
		for(Entry<String, CurrencyModule> entry : currencies.entrySet()) {
	    	String key = entry.getKey();
	    	if(key.equals(date)){
	    		exist = true;
	    	}
	    }
			
		if(!exist){
			currencies.put(date, on);
			//Paint the data from server into a tab
			drawCurrenciesPanels(on.getCurrencies(on.getDoc()),date);
		}
		mntmRefreshTime.setText(" Last refresh from: " + reportDate);
	}
	
	public void loadFromOffLine(){
		
		boolean exist = false;
		String date;
		
		//opens choose the file frame
		JFileChooser fileChooser=new JFileChooser();
		
		if(fileChooser.showDialog(frmCurrencyManager, "Open File") == JFileChooser.APPROVE_OPTION)
		{
			//Create offline object that handles the local XML
			CurrencyModule off = new CurrencyModule();
			try
			{
				off.offlineData(fileChooser.getSelectedFile().getPath());
				//Gets current time of the file 
				date = off.getUpdateTime(off.getDoc());
				
				for(Entry<String, CurrencyModule> entry : currencies.entrySet()) {
			    	String key = entry.getKey();
			    	if(key.equals(date)){
			    		exist = true;
			    	}
			    }
				if(!exist){	
					//Put the offline object inside the objects map
					currencies.put(date, off);
					//paint a tab with the data from file
					drawCurrenciesPanels(off.getCurrencies(off.getDoc()),date);
				}
			}
			catch (IOException e)
			{
				logger.error("Can't Load Data From Selected File");
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "There was a problem to load data from the selected file,The file may be corrupted.\n\nPlease try again or choose different file.","Error",JOptionPane.OK_OPTION);
			}
		}
	}
	
	public void autoUpdate(){
		new Thread()
		{
		    public void run() {
		    	for(;;){
		    		try {
						sleep(3600000);//choose time of updates
					} catch (InterruptedException e1){
						logger.error("Problem with sleep mode");
						e1.printStackTrace();
					}
		    		loadFromOnline();
		    		logger.info("Start auto Update");
		    	}
		    }
		}.start();
	}
	
	public HashMap<String, Currency> getFilteredCurrency(String date, String currencyCode){
		
		HashMap<String, Currency> temp = new HashMap<String, Currency>();
		temp.put(currencyCode, currencies.get(date).getCurrencies(currencies.get(date).getDoc()).get(currencyCode));
		
		return temp;
	}	
}