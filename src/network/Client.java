/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package network;
import java.io.*;
import java.net.*;

import model.Drink;
/*
 *
 * @author Rajiv
 */
public class Client{

    /**
     * @param args the command line arguments
     */
	
	private Socket conn;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	
	public Client(){
		this.createConnection();
		this.getStreams();
	}
	                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            
	public void createConnection(){					// initializes the connection
		
		try{
			conn = new Socket( InetAddress.getLocalHost(),8888);
			System.out.println("Connection created");
		}
		catch(IOException ex){
			
		}
		catch(Exception ex){
			
		}
	}

	public void getStreams(){		// gets output and input streams from socket
		try{
			System.out.println("Getting streams");
			input = new ObjectInputStream(conn.getInputStream());
			output = new ObjectOutputStream(conn.getOutputStream());
			System.out.println("Got streams");
		}
		catch(Exception ex){
			
		}
	}
	public void closeConnections(){			// closes all connections
		try{
			input.close();
			output.close();
			conn.close();
		}
		catch(Exception ex){
			
		}
	}
	
	public void sendChoice(String choice){			// sends choice to the server
		try{
			System.out.println("Sending choice");
			output.writeObject(choice);
			System.out.println("sent choice");
		}
		catch(IOException ex){
			ex.printStackTrace();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public boolean recieveResponse(){			// recieves response from server
		boolean response = false;
		try{
			 response = (boolean) input.readObject();
		}
		catch(IOException ex){
			
		}
		catch(Exception ex){
			
		}
		
		return response;
	}
    
	public void sendDrink(Drink drink){			// send a drink to server
		try{
			output.writeObject(drink);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
    public void run() throws Exception{
        
        Socket sk = new Socket(InetAddress.getLocalHost(), 8888);
        PrintStream ps = new PrintStream(sk.getOutputStream());
        
        
        InputStreamReader IR = new InputStreamReader(sk.getInputStream());
        BufferedReader BR = new BufferedReader(IR);
        
        String MESSAGE = BR.readLine();
        System.out.println(MESSAGE);
    }            
    
    
    
}
