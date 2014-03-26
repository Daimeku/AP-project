import java.awt.EventQueue;

import javax.swing.UIManager;

import network.Server;

import view.LoginView;
import view.ManagerView;


public class Driver {
	
	//launch application

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			
			public void run() {
				
				try {					
					LoginView frame = new LoginView();
					frame.setVisible(true);
					Server server = new Server();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
