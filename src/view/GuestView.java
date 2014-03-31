package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableColumn;

import model.Drink.DrinkAdapter;

import java.awt.Component;

public class GuestView extends JFrame {

	private JPanel contentPane;
	private JPanel toolBarPanel;
	private static JTable table;
	private JMenuItem mntmExit;
	private JMenuItem mntmAbout;
	private JButton btnOrder;

	/**
	 * Create the frame.
	 */
	public GuestView() {
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
				
				JOptionPane.showMessageDialog(GuestView.this, abt.getEditorPane(),"About AP-Project", JOptionPane.PLAIN_MESSAGE);
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
				.addComponent(toolBarPanel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 714, Short.MAX_VALUE)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 714, Short.MAX_VALUE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 454, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(toolBarPanel, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE))
		);
		toolBarPanel.setLayout(null);
		
		btnOrder = new JButton("Submit Order");
		btnOrder.setBounds(274, 21, 150, 35);
		toolBarPanel.add(btnOrder);
		btnOrder.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnOrder.setFocusable(false);
		btnOrder.setForeground(Color.BLACK);
		btnOrder.setBackground(new Color(139, 0, 0));
		btnOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JOptionPane.showConfirmDialog(null, "Your Order:", "Confirm Order", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		table = new JTable();
		table.setAutoscrolls(false);
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
