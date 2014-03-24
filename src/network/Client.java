/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package network;
import java.io.*;
import java.net.*;
/*
 *
 * @author Rajiv
 */
public class Client{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        Client cl = new Client();
        cl.run();
        
    }
    
    public void run() throws Exception{
        
        Socket sk = new Socket("Local Host", 3306);
        PrintStream ps = new PrintStream(sk.getOutputStream());
        
        InputStreamReader IR = new InputStreamReader(sk.getInputStream());
        BufferedReader BR = new BufferedReader(IR);
        
        String MESSAGE = BR.readLine();
        System.out.println(MESSAGE);
    }            
    
}
