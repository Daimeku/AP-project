package network;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

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
			
			String choice =null;			// determines what client is requesting
			try{ 
				while(true){
					
					conn = ssock.accept();
					this.getStreams();
					
					do{
						try{
							
							choice = (String) input.readObject();			// reading the action/choice string
							//options 
							
							if(choice.equals("add drink")){				// adding a drink
								System.out.println("Request recieved");
								output.writeObject(true);
								Drink tempDrink = new Drink();
								tempDrink = (Drink) input.readObject();
								boolean res = DBM.addDrink(tempDrink) ;
								output.writeObject(res);
								System.out.println("Drink added: "+res);
																								
							}
							else if(choice.equals("modify drink")){		// changing a drink's details
								System.out.println("Request recieved");
								output.writeObject(true);
								Drink tempDrink = new Drink();
								tempDrink = (Drink) input.readObject();
								output.writeObject(DBM.addDrink(tempDrink));
								System.out.println("Drink added");
							}
							else if(choice.equals("delete drink")){		// deleting a drink
								
							}
							else if(choice.equals("staff login")){			// logging in
								output.writeObject(true);
								System.out.println("Reading manager");
								Manager man = new Manager(); 
								
								man = (Manager) input.readObject();
								System.out.println("Read manager: "+man.getName()+" "+man.getPassword());
								boolean res = DBM.staffLogin(man.getName(),man.getPassword());
								System.out.println("logged in: "+res);
								output.writeObject(res);
							}							
							else if(choice.equals("drink table")){
								DrinkAdapter da = new DrinkAdapter();
								output.writeObject(da.getTableModel());
							}
							else if(choice.equals("add order")){
								
								output.writeObject(true);
								System.out.println("reading order");
								Order order = (Order) input.readObject();
								System.out.println("Read order, now adding it");
								boolean added = DBM.addOrder(order);
								System.out.println("order added: "+added);
								output.writeObject(added);
							}
									
											
						}
						catch(EOFException ex){
							JOptionPane.showMessageDialog(null,"connection terminated","Network Message",JOptionPane.PLAIN_MESSAGE);
							//ex.printStackTrace();
							break;
						}
						catch(SocketException ex){
							JOptionPane.showMessageDialog(null,"connection terminated: socket exception","Network Message",JOptionPane.PLAIN_MESSAGE);
							//ex.printStackTrace();
							break;
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
				JOptionPane.showMessageDialog(null,"general exception wait for request","EXCEPTION",JOptionPane.ERROR_MESSAGE);
				ex.printStackTrace();
			}
		}
		
	
		public void closeConnection(){			// terminates the server connection
			try{
				input.close();
				output.close();
				conn.close();
			}
			catch(IOException ex){
				ex.printStackTrace();
			}
			catch(Exception ex){
				ex.printStackTrace();
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
		

