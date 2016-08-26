/**
 * Create an panel that handle the converting options
 * @author Shamir & Alexander
 */

package shamir.alex.project;
import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import org.apache.log4j.*;

public class ConverterPanel extends JPanel implements ActionListener,KeyListener 
{
	final static Logger logger = Logger.getLogger(ConverterPanel.class);
	private static final long serialVersionUID = 1L;
	// declare of GUI components
	private JTextField textFieldAmount;
	private JComboBox<String> destinationCombo;
	private JComboBox<String> sourceCombo;
	private Currency source;
	private Currency destination;
	private HashMap<String, Currency> map;
	private CurrencyModule currMod;
	private JLabel lblResult;
	private JLabel lblTitle;
	private JLabel lblFrom;
	private JLabel lblAmount;
	private JLabel lblTo;
	private JButton btnCalculate;
	private JLabel lblTxtRes;

	/**
	 * Create the panel, get the CurrencyModule object that contain the data
	 * 
	 * @param map
	 * @param currMod
	 */
	public ConverterPanel(CurrencyModule currMod) {
		// set vars
		this.currMod = currMod;
		this.map = currMod.getCurrencies(currMod.getDoc());
		
		// set the panel layout.
		//setPreferredSize(new Dimension(200, 100));
		setLayout(new GridLayout(15, 1, 0, 0));
		//setLayout(new SpringLayout());
		setBorder(new EmptyBorder(0, 10, 0, 10) );
		
		// set params for title label
		JPanel converterTitle = new JPanel();
		converterTitle.setLayout(new GridLayout(1, 1));
		converterTitle.setBackground(Color.getColor("grey"));
		
		lblTitle = new JLabel("Converter");
		lblTitle.setOpaque(true);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setBackground(Color.GRAY);
		converterTitle.add(lblTitle);
		add(converterTitle);

		//from
		JPanel from = new JPanel();
		from.setLayout(new GridLayout(1, 2));
		from.setBackground(Color.WHITE);
		
		// set params for from label
		lblFrom = new JLabel("From:");
		lblFrom.setFont(new Font("Arial", Font.PLAIN, 10));
		lblFrom.setHorizontalAlignment(SwingConstants.CENTER);
		from.add(lblFrom);
		
		//from set params for combobox control
		sourceCombo = new JComboBox<String>();
		sourceCombo.addActionListener(this);
		from.add(sourceCombo);
		add(from);
		
		//to
		JPanel to = new JPanel();
		to.setLayout(new GridLayout(1, 2));
		to.setBackground(Color.WHITE);
				
		// set params for to label
		lblTo = new JLabel("To:");
		lblTo.setFont(new Font("Arial", Font.PLAIN, 10));
		lblTo.setHorizontalAlignment(SwingConstants.CENTER);
		to.add(lblTo);
				
		//to set params for combobox control
		destinationCombo = new JComboBox<String>();
		destinationCombo.addActionListener(this);
		to.add(destinationCombo);
		add(to);
		
		

		JPanel amount = new JPanel();
		amount.setLayout(new GridLayout(1, 2));
		amount.setBackground(Color.WHITE);

		// set params for amount label
		lblAmount = new JLabel("Amount:");
		lblAmount.setFont(new Font("Arial", Font.PLAIN, 10));
		lblAmount.setHorizontalAlignment(SwingConstants.CENTER);
		amount.add(lblAmount);

		// set params for amount txtfiled
		textFieldAmount = new JTextField();
		textFieldAmount.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textFieldAmount.setText("0");
		textFieldAmount.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldAmount.addKeyListener(this);
		textFieldAmount.setColumns(10);
		amount.add(textFieldAmount);

		add(amount);


		JPanel calcBtn = new JPanel();
		calcBtn.setLayout(new BorderLayout());
		calcBtn.add(new JLabel());
		// set params for button
		btnCalculate = new JButton("Calc");
		btnCalculate.addActionListener(this);
		calcBtn.add(btnCalculate);
		calcBtn.setBackground(Color.WHITE);

		add(calcBtn);
		
		//result
		JPanel res = new JPanel();
		res.setLayout(new GridLayout(1, 2));
		res.setBackground(Color.WHITE);
		
		// set params for result label
		lblTxtRes = new JLabel("Result:");
		lblTxtRes.setFont(new Font("Arial", Font.PLAIN, 10));
		lblTxtRes.setHorizontalAlignment(SwingConstants.CENTER);
		res.add(lblTxtRes);
		
		// set params for result label
		lblResult = new JLabel("0.0");
		lblResult.setHorizontalAlignment(SwingConstants.CENTER);
		lblResult.setFont(new Font("Tahoma", Font.BOLD, 14));
		res.add(lblResult);

		add(res);
		// populate data to comboboxes

		populateCombos();

	}

	/**
	 * Populate the data to the combo boxes
	 */
	private void populateCombos() {
		// add ILS to the map
		map.put("ILS", new Currency("New Shekel", 1.0, "ILS", "Israel", 1.0,
				0.0));

		// foreach loop to add the string to the combo box
		Set<String> list = map.keySet();
		for (String str : list) {
			sourceCombo.addItem(map.get(str).currencyCode);
			destinationCombo.addItem(map.get(str).currencyCode);

		}
	}

	@Override
	public void actionPerformed(ActionEvent act) {
		// Get currency - if user selects a value from the destination combo box
		// than the link is objected to it
		if (act.getSource() == destinationCombo
				&& act.getActionCommand() == "comboBoxChanged") {
			destination = map.get(destinationCombo.getSelectedItem().toString());
		}
		// Get currency - if user selects a value from the destination combo box
		// than the link is objected to it
		if (act.getSource() == sourceCombo
				&& act.getActionCommand() == "comboBoxChanged") {
			source = map.get(sourceCombo.getSelectedItem().toString());
		}
		if (act.getSource() == textFieldAmount) {
			// source = map.get(sourceCombo.getSelectedItem().toString());
		}
		// when clicked on calc button
		if (act.getActionCommand() == "Calc") {
			if (source != null && destination != null) {
				// convert currencies, put result in label
				try {
					logger.info("Calculate Exchange rate");
					double result = currMod.currencyConvert(
							Double.parseDouble(textFieldAmount.getText()),
							source, destination);
					result = round(result, 2, BigDecimal.ROUND_HALF_UP);
					lblResult.setText(result + "");
				} catch (NumberFormatException ex) {
					logger.error("parseDouble Function Crashed while Calculating Exchange rates.");
					logger.error("Source= "+source + " Destination= "+destination);
					textFieldAmount.setForeground(Color.RED);
					textFieldAmount.setFont(new Font("Tahoma", Font.BOLD, 12));
				}
			}
		}
	}

	/**
	 * Function that rounds result value to a double number with the decided precision
	 * 
	 * @param unrounded
	 * @param precision
	 * @param roundingMode
	 * @return
	 */
	public static double round(double unrounded, int precision, int roundingMode) {
		BigDecimal bd = new BigDecimal(unrounded);
		BigDecimal rounded = bd.setScale(precision, roundingMode);
		return rounded.doubleValue();
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		if (arg0.getSource() == textFieldAmount) {
			textFieldAmount.setForeground(Color.BLACK);
			textFieldAmount.setFont(new Font("Tahoma", Font.PLAIN, 12));
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}
}
