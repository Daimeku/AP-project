package network;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.sql.Time;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import model.*;


public class Server {								// Server to interact with the DB 
	
		private ObjectInputStream input;
		private ObjectOutputStream output;
		private ServerSocket ssock;
		private Socket conn;
		private DBConnect DBM;
		private Logger log;
		
		public Server(){
			log =  Logger.getLogger(getClass());
			DBM = new DBConnect();
			this.createConnection();
			this.waitForRequest();
				
			
		}
		
		public void createConnection(){					// initializes the connection
			
			try{
				
				this.ssock = new ServerSocket(8888,1);
				JOptionPane.showMessageDialog(null,"Server connection setup","Success",JOptionPane.INFORMATION_MESSAGE);
				
			}
			catch(IOException ex){			
				JOptionPane.showMessageDialog(null,"IO exception","EXCEPTION",JOptionPane.ERROR_MESSAGE);
				ex.printStackTrace();
			}
			catch(Exception ex){
				JOptionPane.showMessageDialog(null,"general exception creating connection","EXCEPTION",JOptionPane.ERROR_MESSAGE);
				ex.printStackTrace();
			}
		}
		
		public void waitForRequest(){		// waits for client requests
			
						// determines what client is requesting
			try{ 
				while(true){
					
					conn = ssock.accept();
					new ServerEngine(conn);
				}
			
			}
			catch(Exception ex){
				JOptionPane.showMessageDialog(null,"general exception wait for request","EXCEPTION-SERVER",JOptionPane.ERROR_MESSAGE);
				ex.printStackTrace();
			}
		}
		
		
	
	

		
		
		
		
	}
		

