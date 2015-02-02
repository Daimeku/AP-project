package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Toolkit;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.DefaultComboBoxModel;

import model.Drink;
//import model.Drink.DrinkAdapter;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class AddDrinkView extends JDialog {
	
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JLabel lblName;
	private JLabel lblType;
	private JLabel lblPrice;
	private JTextField tfName;
	private JComboBox<String> comboBoxType;
	private JSpinner spinnerPrice;

	/**
	 * Create the dialog.
	 */
	public AddDrinkView() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(AddDrinkView.class.getResource("/resources/add_drink.png")));
		setTitle("Add Drink");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		lblName = new JLabel("Name:");
		
		tfName = new JTextField();
		tfName.setColumns(10);
		
		lblType = new JLabel("Type:");
		
		lblPrice = new JLabel("Price:");
		
		spinnerPrice = new JSpinner();
		
		comboBoxType = new JComboBox<String>();
		comboBoxType.setModel(new DefaultComboBoxModel<String>(new String[] {"-- select --", Drink.TYPES[0], Drink.TYPES[1]}));
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(lblName)
							.addGap(10)
							.addComponent(tfName, GroupLayout.PREFERRED_SIZE, 279, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(lblType)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(comboBoxType, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(lblPrice)
							.addGap(10)
							.addComponent(spinnerPrice, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(102, Short.MAX_VALUE))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(41)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(3)
							.addComponent(lblName))
						.addComponent(tfName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(35)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblType)
						.addComponent(comboBoxType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(38)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(6)
							.addComponent(lblPrice))
						.addComponent(spinnerPrice, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(49, Short.MAX_VALUE))
		);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent arg0) {
						// get input values
						String drinkName = tfName.getText().trim();
						String drinkTypeString = comboBoxType.getSelectedItem().toString().trim();
						Double drinkPrice = Double.parseDouble(spinnerPrice.getValue().toString());
						// set drinkType integer
						int drinkType = (drinkTypeString.equals(Drink.TYPES[0]))?1:2;
						
						// if everything is A-OK create Drink object and attempt to save
						if(drinkName.length() > 2 && drinkPrice >= Drink.MIN_PRICE){
							Drink aDrink = new Drink(drinkName, drinkType, drinkPrice);
							
							try {
								if(aDrink.save()){
									JOptionPane.showMessageDialog(null, drinkName+" drink added!", "Drink Added",
										    JOptionPane.INFORMATION_MESSAGE);
									//ManagerView.getTable().setModel(DrinkAdapter.getTableModel());
									clearFields(); // or dispose();
								}
							} catch (SQLException e) {
								e.printStackTrace();
							}
							
						}else
							JOptionPane.showMessageDialog(null, "The drink information you entered is invalid, please try again.","Invalid Drink",
								    JOptionPane.ERROR_MESSAGE);
					}
					
				});
				okButton.setFocusable(false);
				okButton.setIcon(new ImageIcon(AddDrinkView.class.getResource("/resources/accept.gif")));
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						dispose();
					}
				});
				cancelButton.setFocusable(false);
				cancelButton.setIcon(new ImageIcon(AddDrinkView.class.getResource("/resources/cancel.png")));
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
	}
	
	private void clearFields(){
		this.comboBoxType.setSelectedIndex(0);
		this.spinnerPrice.setValue(0);
		this.tfName.setText(null);
	}
}
