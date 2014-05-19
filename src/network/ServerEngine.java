package network;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import javax.swing.JOptionPane;

import model.ArmBand;
import model.DBConnect;
import model.Drink;
import model.Guest;
import model.Manager;
import model.Order;

import org.apache.log4j.Logger;

public class ServerEngine implements Runnable{

	private ObjectInputStream input;
	private ObjectOutputStream output;
	private Socket conn;
	private DBConnect DBM;
	private Logger log;
	
	public ServerEngine(Socket conn){
		DBM = new DBConnect();
		log = Logger.getLogger(getClass());
		this.conn = conn;
		(new Thread(this)).start();
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
			JOptionPane.showMessageDialog(null,"IO exception in getting streams","EXCEPTION-SERVERENGINE",JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		}
		catch(Exception ex){
			JOptionPane.showMessageDialog(null,"general exception in getting streams","EXCEPTION-SERVERENGINE",JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		}
			
		}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		String choice =null;
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
					log.info("Request recieved");
					output.writeObject(true);
					Drink tempDrink = new Drink();
					tempDrink = (Drink) input.readObject();
					boolean res = DBM.updateDrink(tempDrink);
					output.writeObject(res);
					log.info("Drink changed? "+res);
				}
				else if(choice.equals("delete drink")){		// deleting a drink
					log.info("remove drink");
					output.writeObject(true);
					Drink drink = (Drink) input.readObject();
					log.info(drink.getName());
					boolean res = DBM.deleteDrink(drink.getName());
					output.writeObject(res);
					log.info("drink deleted? "+res);
				}
				else if(choice.equals("staff login")){			// logging in
					output.writeObject(true);
					log.info("Reading manager");
					Manager man = new Manager(); 
					
					man = (Manager) input.readObject();
					log.info("Read manager: "+man.getName()+" "+man.getPassword());
					boolean res = DBM.staffLogin(man.getName(),man.getPassword());
					log.info("logged in: "+res);
					output.writeObject(res);
				}
				else if(choice.equals("guest login")){
					output.writeObject(true);
					log.info("Reading guest");
					ArmBand ab = new ArmBand();
					Guest man = new Guest(ab); 
					
					man = (Guest) input.readObject();
					log.info("Reading guest");
					boolean res = DBM.guestLogin(man.getName(),man.getPassword());
					log.info("Logged in");
					output.writeObject(res);
					output.writeObject(DBM.getGuest(man.getName()));
				}
				else if(choice.equals("drink table guest")){
					
					Guest use = (Guest) input.readObject();
					output.writeObject(DrinkAdapter.getTableModelForGuest(use));
				}
				else if(choice.equals("drink table")){
					output.writeObject(DrinkAdapter.getTableModel());
				}
				else if(choice.equals("order table")){
					 
					output.writeObject(OrderAdapter.getTableModel(null));
				}
				else if(choice.equals("report table")){
					java.sql.Date d = new java.sql.Date(new java.util.Date().getTime());
					output.writeObject(OrderAdapter.getTableModel(d));					
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
				//JOptionPane.showMessageDialog(null,"connection terminated","Network Message",JOptionPane.PLAIN_MESSAGE);
				ex.printStackTrace();
				
			}
			catch(SocketException ex){
			//	JOptionPane.showMessageDialog(null,"connection terminated: socket exception","Network Message",JOptionPane.PLAIN_MESSAGE);
				ex.printStackTrace();
				
			}
			catch(Exception ex){
				//JOptionPane.showMessageDialog(null,"general exception wait for request","EXCEPTION-SERVERENGINE",JOptionPane.ERROR_MESSAGE);
				ex.printStackTrace();
			}
			
		}while(!choice.equals("exit"));
		
		this.closeConnection();
		
		
	}
}
