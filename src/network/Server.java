package network;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

import javax.swing.JOptionPane;

import model.DBConnect;


public class Server {								// Server to interact with the DB 
	
		private ObjectInputStream input;
		private ObjectOutputStream output;
		private ServerSocket ssock;
		private Socket conn;
		private DBConnect DBM;
		
		public Server(){
			DBM = new DBConnect();
			this.createConnection();
			this.waitForRequest();
				
			
		}
		public void createConnection(){					// initializes the connection
			
			try{
				
				this.ssock = new ServerSocket(8912,1);
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
			
			String choice = "";			// determines what client is requesting
			try{ 
				while(true){
					
					conn = ssock.accept();
					this.getStreams();
					
					do{
						try{
						
							choice = (String) input.readObject();			// reading the action/choice string
							//options 
							if(choice.equals("add drink")){				// adding a drink
								
							}
							else if(choice.equals("modify drink")){		// changing a drink's details
								
							}
							else if(choice.equals("delete drink")){		// deleting a drink
								
							}
									
											
						}
						catch(EOFException ex){
							JOptionPane.showMessageDialog(null,"connection terminated","Network Message",JOptionPane.PLAIN_MESSAGE);
							ex.printStackTrace();
						}
						catch(Exception ex){
							JOptionPane.showMessageDialog(null,"general exception wait for request","EXCEPTION",JOptionPane.ERROR_MESSAGE);
							ex.printStackTrace();
						}
						
					}while(!choice.equals("exit"));
					this.closeConnection();
					
				}
			
			}
			catch(Exception ex){
				
			}
		}
		
	
		public void closeConnection(){			// terminates the server connection
			try{
				input.close();
				output.close();
				conn.close();
			}
			catch(IOException ex){
				
			}
			catch(Exception ex){
				
			}
		}
		
		
		public void getStreams(){
			try{
				output = new ObjectOutputStream(conn.getOutputStream());
				input = new ObjectInputStream(conn.getInputStream());
			}
			catch(IOException ex){
				JOptionPane.showMessageDialog(null,"IO exception in getting streams","EXCEPTION",JOptionPane.ERROR_MESSAGE);
				ex.printStackTrace();
			}
			catch(Exception ex){
				JOptionPane.showMessageDialog(null,"general exception in getting streams","EXCEPTION",JOptionPane.ERROR_MESSAGE);
				ex.printStackTrace();
			}
				
			}
		
		
		
	}
		

