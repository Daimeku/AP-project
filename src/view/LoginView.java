package view;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import network.Server;

import model.Guest;
import model.Staff;

public class LoginView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textUserName;
	private JLabel lblUserName;
	private JLabel lblPassword;
	private JButton btnLogin;
	private JLabel lblBackground;
	private JLabel lblBackground_btm;
	private JLabel lblWineBg;
	private JPasswordField passwordField;
	
	
	public LoginView() {
		setTitle("AP-Project");
		
		initializeComponents();
		setLayout();
		registerListeners();
		addComponentsToWindow();
		setWindowProperties();
		
	}
	
	
	public void initializeComponents(){
		contentPane = new JPanel();
		lblUserName = new JLabel("User Name:");
		lblPassword = new JLabel("Password:");
		textUserName = new JTextField("User Name");
		passwordField = new JPasswordField("Password");
		btnLogin = new JButton("Login");
		lblBackground = new JLabel("bg");
		lblWineBg = new JLabel("");
		lblBackground_btm = new JLabel("bgBottom");
		
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		
		lblBackground.setBounds(0, 0, 454, 476);
		lblBackground.setIcon(new ImageIcon(LoginView.class.getResource("/resources/shattered.png")));
		
		lblWineBg.setHorizontalAlignment(SwingConstants.CENTER);
		lblWineBg.setIcon(new ImageIcon(LoginView.class.getResource("/resources/login.png")));
		lblWineBg.setBounds(92, 30, 258, 278);
		
		lblUserName.setBounds(102, 332, 63, 14);
		
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblPassword.setBounds(102, 360, 63, 14);
		
		textUserName.setForeground(new Color(128, 128, 128));
		textUserName.setCaretPosition(0);
		textUserName.setBounds(167, 329, 193, 20);
		textUserName.setColumns(10);
		
		passwordField.setEchoChar((char)0);
		passwordField.setForeground(new Color(128, 128, 128));
		passwordField.setBounds(167, 357, 193, 20);
		
		btnLogin.setFocusPainted(false);
		btnLogin.setBackground(new Color(139, 0, 0));
		btnLogin.setBounds(343, 493, 89, 23);
		
		lblBackground_btm.setIcon(new ImageIcon(LoginView.class.getResource("/resources/redwine.png")));
		lblBackground_btm.setBounds(0, 476, 454, 56);
	}
	
	
	public void setLayout(){
		contentPane.setLayout(null);
	}
	
	
	public void registerListeners(){
		textUserName.addFocusListener(new FocusListener(){
			@Override
			public void focusGained(FocusEvent event){
				if(textUserName.getText().equals("User Name")){
					textUserName.setText("");
					textUserName.setForeground(new Color(0, 0, 0));
					textUserName.setBorder(BorderFactory.createCompoundBorder(new LineBorder(new Color(128, 0, 0)),
							BorderFactory.createEmptyBorder(0, 2, 0, 0)));
				}
			}
			
			@Override
			public void focusLost(FocusEvent event){
				if(textUserName.getText().equals("User Name") || textUserName.getText().isEmpty()){
					textUserName.setBorder( UIManager.getBorder("TextField.border") );
					textUserName.setForeground(new Color(128, 128, 128));
					textUserName.setText("User Name");
				}
			}
		});
		
		passwordField.addFocusListener(new FocusListener(){
			@SuppressWarnings("deprecation")
			@Override
			public void focusGained(FocusEvent event){
				if(passwordField.getText().equals("Password")){
					passwordField.setText("");
					passwordField.setEchoChar('\u2022');
					passwordField.setForeground(new Color(0, 0, 0));
					passwordField.setBorder(BorderFactory.createCompoundBorder(new LineBorder(new Color(128, 0, 0)),
							BorderFactory.createEmptyBorder(0, 2, 0, 0)));
				}
			}
			
			@SuppressWarnings("deprecation")
			@Override
			public void focusLost(FocusEvent event){
				if("".equalsIgnoreCase(passwordField.getText().trim())){
					passwordField.setBorder( UIManager.getBorder("TextField.border") );
					passwordField.setEchoChar((char)0);
					passwordField.setForeground(new Color(128, 128, 128));
					passwordField.setText("Password");
				}
			}
		});
		
		btnLogin.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent ae) {
				
				/* PR:
				 * Attempt to login...
				 */
				try {
					if(Staff.login(textUserName.getText().trim(), passwordField.getText().trim())){
						ManagerView manager = new ManagerView();
						manager.setVisible(true);;
						dispose();
					}else if(Guest.login(textUserName.getText().trim(), passwordField.getText().trim())){
					
					}else
						JOptionPane.showMessageDialog(null, "The credentials you entered were invalid, please try again.","Invalid Login",
							    JOptionPane.ERROR_MESSAGE);
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	public void addComponentsToWindow(){
		contentPane.add(lblWineBg);
		contentPane.add(lblUserName);
		contentPane.add(lblPassword);
		contentPane.add(textUserName);
		contentPane.add(passwordField);
		contentPane.add(lblBackground);
		contentPane.add(btnLogin);
		contentPane.add(lblBackground_btm);
	}
	
	
	public void setWindowProperties(){
		setIconImage(Toolkit.getDefaultToolkit().getImage(LoginView.class.getResource("/resources/drink.png")));
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 460, 560);
	}
}
