package view;

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JEditorPane;
import javax.swing.SwingUtilities;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

public class About {

	private JEditorPane editorPane;
	
	public About(){
		// anti-aliasing on
	    System.setProperty("awt.useSystemAAFontSettings", "on");
	    editorPane = new JEditorPane();

	    editorPane.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, Boolean.TRUE);  
	    editorPane.setFont(new Font("Arial", Font.BOLD, 13));
	
	    editorPane.setPreferredSize(new Dimension(540,300));
	    editorPane.setEditable(false);
	    editorPane.setContentType("text/html");
	    editorPane.setText(
	            "<html>"
	            + "<body>"
	            + "<table border='0px' cxellpadding='10px' height='100%'>"
	            + "<tr>"
	            + "<td valign='center'>"
	            + "<br /><img src='"
	            + About.class.getResource("/resources/Utech-logo.png").toExternalForm()
	            + "'>"
	            + "</td>"
	            + "<td>"
	            + "<h2>"
	            + "Advanced Programming Using Java"
	            +"</h2>"
	            + "Group Project issued by the University of Technology, Jamaica <br /><br />"
	            + "Group Members: <br />"
	            + "- Ashani Kentish <br />-"
	            + "<a href=\"http://www.reliqartz.com/\"><b> Patrick Reid</b></a><br />"
	            + "- Brandon Franklyn <br />"
	            + "- Rajiv Manderson <br />"
                    + "- Warren Harding <br />"       
	            + "<br />"
	            + "Tutor:"
	            + "<br />"
	            + "<a href=\"jm.linkedin.com/pub/julian-jarrett/7a/a6/715\"><b>Julian Jarrett</b></a>"
	            + "<br /><br />Github Repository"
	            + "<br />"
	            + "<a href=\"https://github.com/Yondaimeku/AP-project\"><b>AP-Project</b></a>"
	            + "</td>"
	            + "</tr>"
	            + "</table>"
	            + "</body>"
	            + "</html>"
	            );
	
	    editorPane.addHierarchyListener(new HierarchyListener() {
	        public void hierarchyChanged(HierarchyEvent e) {
	            Window window = SwingUtilities.getWindowAncestor(editorPane);
	            if (window instanceof Dialog) {
	                Dialog dialog = (Dialog)window;
	                if (dialog.isResizable()) {
	                    dialog.setResizable(false);
	                }
	            }
	        }
	    });

	    editorPane.addHyperlinkListener(new HyperlinkListener() {
	        public void hyperlinkUpdate(final HyperlinkEvent e) {
	            if (e.getEventType() == HyperlinkEvent.EventType.ENTERED) {
	                EventQueue.invokeLater(new Runnable() {
	                    public void run() {
	                    	
	                        SwingUtilities.getWindowAncestor(editorPane).setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	
	                       // editorPane.setToolTipText(e.getURL().toExternalForm());
	                    }
	                });
	            } else if (e.getEventType() == HyperlinkEvent.EventType.EXITED) {
	                EventQueue.invokeLater(new Runnable() {
	                    public void run() {
	                    	
	                        SwingUtilities.getWindowAncestor(editorPane).setCursor(Cursor.getDefaultCursor());
	                       // editorPane.setToolTipText(null);
	                    }
	                });
	            } else if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
	                if (Desktop.isDesktopSupported()) {
	                    try {
	                        Desktop.getDesktop().browse(e.getURL().toURI());
	                    } catch (Exception ex) {
	                        Logger.getLogger(About.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
	                    }
	                }
	            }
	        }
	    });
	}

	public JEditorPane getEditorPane() {
		return editorPane;
	}

	public void setEditorPane(JEditorPane editorPane) {
		this.editorPane = editorPane;
	}
	

        
}