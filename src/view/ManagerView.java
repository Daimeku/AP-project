package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.JTableHeader;

import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.ImageIcon;

import java.awt.Color;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JSeparator;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.ListSelectionModel;

import model.Drink.*;

public class ManagerView extends JFrame {

	private JPanel contentPane;
	private JPanel toolBarPanel;
	private static JTable table;
	private JMenuItem mntmExit;
	private JMenuItem mntmAbout;

	/**
	 * Create the frame.
	 */
	public ManagerView() {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(ManagerView.class.getResource("/resources/drink.png")));
		setTitle("AP-Project v0.1.1");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 720, 576);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent aEvent) {
				System.exit(EXIT_ON_CLOSE);
			}
		});
		mntmExit.setIcon(new ImageIcon(ManagerView.class.getResource("/resources/exit.png")));
		mnFile.add(mntmExit);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				About abt = new About();
				
				JOptionPane.showMessageDialog(ManagerView.this, abt.getEditorPane(),"About AP-Project", JOptionPane.PLAIN_MESSAGE);
			}
		});
		mntmAbout.setIcon(new ImageIcon(ManagerView.class.getResource("/resources/about.png")));
		mnHelp.add(mntmAbout);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(46, 139, 87));
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		
		toolBarPanel = new JPanel();
		toolBarPanel.setBackground(new Color(139, 0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(toolBarPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 714, Short.MAX_VALUE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(toolBarPanel, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 515, GroupLayout.PREFERRED_SIZE))
		);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setFocusable(false);
		toolBar.setFloatable(false);
		toolBar.setBackground(new Color(139, 0, 0));
		GroupLayout gl_toolBarPanel = new GroupLayout(toolBarPanel);
		gl_toolBarPanel.setHorizontalGroup(
			gl_toolBarPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_toolBarPanel.createSequentialGroup()
					.addGap(131)
					.addComponent(toolBar, GroupLayout.DEFAULT_SIZE, 407, Short.MAX_VALUE)
					.addGap(176))
		);
		gl_toolBarPanel.setVerticalGroup(
			gl_toolBarPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_toolBarPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(toolBar, GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)
					.addGap(6))
		);
		
		JButton btnAddDrink = new JButton("Add Drink ");
		btnAddDrink.setFocusable(false);
		btnAddDrink.setForeground(new Color(255, 255, 255));
		btnAddDrink.setBackground(new Color(139, 0, 0));
		btnAddDrink.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent ae) {
				AddDrinkView newDrink = new AddDrinkView();
				newDrink.show();
			}
		});
		btnAddDrink.setIcon(new ImageIcon(ManagerView.class.getResource("/resources/add_drink.png")));
		toolBar.add(btnAddDrink);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(new Color(139, 0, 0));
		separator.setBackground(new Color(139, 0, 0));
		toolBar.add(separator);
		
		JButton btnRemoveDrink = new JButton("Remove Drink");
		btnRemoveDrink.setFocusable(false);
		btnRemoveDrink.setForeground(new Color(255, 255, 255));
		btnRemoveDrink.setBackground(new Color(139, 0, 0));
		btnRemoveDrink.setIcon(new ImageIcon(ManagerView.class.getResource("/resources/rem_drink.png")));
		toolBar.add(btnRemoveDrink);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBackground(new Color(139, 0, 0));
		separator_1.setForeground(new Color(139, 0, 0));
		toolBar.add(separator_1);
		
		JButton btnModifyDrink = new JButton("Modify Drink ");
		btnModifyDrink.setFocusable(false);
		btnModifyDrink.setForeground(new Color(255, 255, 255));
		btnModifyDrink.setBackground(new Color(139, 0, 0));
		btnModifyDrink.setIcon(new ImageIcon(ManagerView.class.getResource("/resources/mod_drink.png")));
		toolBar.add(btnModifyDrink);
		toolBarPanel.setLayout(gl_toolBarPanel);
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setSelectionBackground(new Color(46, 139, 87));
		table.setRowMargin(0);
		table.setFont(new Font("Serif", Font.PLAIN, 12));
		table.setRowHeight(27);
		table.setGridColor(new Color(0, 0, 0));
		table.setBackground(new Color(211, 211, 211));
		table.setModel(DrinkAdapter.getTableModel());
		
		scrollPane.setViewportView(table);
		contentPane.setLayout(gl_contentPane);
	}
	
	public static JTable getTable(){
		return table;
	}
}
