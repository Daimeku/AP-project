package network;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import org.apache.log4j.Logger;

import model.DBConnect;

public  class OrderAdapter{
	
	private static Logger log;
	
	/**
	 * Get the orders table.
	 * @return DefaultTableModel
	 */
	public static DefaultTableModel getTableModel(){
		
		Vector<Object> orders = new Vector<Object>(); // to return
		Vector<Object> orderRow = new Vector<Object>(); // a row
		Vector<Object> columns = new Vector<Object>(); // columns
		
		int i = 0; // reusable iterator
		
		try {
			Connection conn = DBConnect.getConnection();
			ResultSet orderSet = conn.prepareStatement("SELECT * from orders").executeQuery(), typeSet;
			ResultSetMetaData meta = orderSet.getMetaData();
	        int columnCount = meta.getColumnCount();
	        
	        //store column names  
	        for (i = 2; i <= columnCount; i++) {
	            columns.add((meta.getColumnName(i).substring(0, 1).toUpperCase() + meta.getColumnName(i).substring(1)));
	            
	        }
	        columns.add("Selected");
	        
			while(orderSet.next()){
				// get row content (order)
				orderRow = new Vector<Object>();
				{
					i = 0;
					// add name and price
					orderRow.addElement( (String) orderSet.getObject(i+2).toString() );
					orderRow.addElement( (Double) Double.parseDouble(orderSet.getObject(i+3).toString()) );
				}
				
				orders.addElement(orderRow);
			}
			
			// close connection
			conn.close();
		}catch(SQLException e){
			log.error("SQLException: "+e.getCause());
		}catch(NumberFormatException e){
			log.error("NumberFormatException: "+e.getCause());
		}finally{
			// keep going...
		}
		DefaultTableModel tableModel = new DefaultTableModel(orders, columns);
		
		return (tableModel);
	}
}