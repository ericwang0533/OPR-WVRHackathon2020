import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class VisualFrame extends MainFrame implements ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton backb;
	JFrame frame;
	int teamnum;
	int totTELOPscore;
	int totAUTOscore;
	int[] zoneAUTOscore;
	int[] zoneTELOPscore;
	JPanel p;
	
	
	public VisualFrame(JFrame a, int b) throws IOException{
		super(a,b);
		frame = a; // Creates get global frame variable, and sets it to a local variable
		teamnum = b;
		frame.remove(button2020);
		frame.remove(WVRScouting);
		frame.remove(WVRlabel);
		
		//Actual Data From Execel Sheet
		ArrayList<Integer> rowToGet = new ArrayList<Integer>(); // Stores the rows where the team's info is.
		int wheelComplete = 0;// Stores the a value which says if they did color wheel(0 = No Color Wheel, 1 = Color Wheel Spun, 2 = Color Wheel Spun to Color)
		totAUTOscore = 0; //Counts how many points scored during AUTONOMOUS
		totTELOPscore = 0; //Counts how many points scored during TELEOP
		zoneAUTOscore = new int[5];
		zoneTELOPscore = new int[5];
		int numgames = 0; // Counts number of games played by the team number
		
		Color customColor = new Color(108, 211, 235); //Creates a background color
		
		frame.setTitle("WVR Scouting App: Visual Data"); // Titles the Window
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // When the X is clicked, the window closes
		frame.setResizable(false); // Keeps a consistent window size.
		frame.setSize(1200,600);  // In pixels, the size of the widow. (width x height)
		frame.getContentPane().setBackground(customColor);
		
		String file = "3538_2020misou.xlsx";
		OPCPackage fs = null;
		try {
			fs = OPCPackage.open(new File(file));
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    XSSFWorkbook wb = new XSSFWorkbook(file);
	    XSSFSheet sheet = wb.getSheetAt(0);
	    XSSFRow row;
	    XSSFCell cell;

	    int rows; // No of rows
	    rows = sheet.getPhysicalNumberOfRows();

	    int cols = 0; // No of columns
	    int tmp = 0;

	    // This trick ensures that we get the data properly even if it doesn't start from first few rows
	    for(int i = 0; i < 10 || i < rows; i++) {
	        row = sheet.getRow(i);
	        if(row != null) {
	            tmp = sheet.getRow(i).getPhysicalNumberOfCells();
	            if(tmp > cols) cols = tmp;
	        }
	    }
	    for(int r = 0; r < rows; r++) {
	        row = sheet.getRow(r);
	        if(row != null) {
	            for(int c = 0; c < cols; c++) {
	                cell = row.getCell((short)c);
	                if(cell != null) {
	                	if(CellType.NUMERIC == cell.getCellType()) {
	                		if(c == 1 && cell.getNumericCellValue() == teamnum) {
	                			numgames++;
	                			rowToGet.add(r);	
	                		}
	                	}
	                }
	            }
	        }
	    }
	    if(numgames==0) {
	    	new IndexFrame(frame);
	    	JOptionPane.showMessageDialog(null, "We do not have that team number", "InfoBox: " + "Error", JOptionPane.INFORMATION_MESSAGE);
	    }
	    else {
	    	System.out.println("Games Played: " + numgames);
		    for(int i = 0; i< rowToGet.size(); i++) {
		    	System.out.println("Team# in Row: " + (rowToGet.get(i)+1)); //This shows the row + 1 since the index of rows starts with 0
		    }
		    
		    /**
		     * 
		     */
		    for(int r = 0; r < rowToGet.size(); r++) {
		        row = sheet.getRow(rowToGet.get(r));
		        if(row != null) {
		            for(int c = 0; c < cols; c++) {
		                cell = row.getCell((short)c);
		                if(cell != null) {
		                	if(CellType.NUMERIC == cell.getCellType()) {
		                		if(c >= 3 && c <= 8) {
		                			totAUTOscore += cell.getNumericCellValue();
		                			if(cell.getNumericCellValue() != 0) {
		                				zoneAUTOscore[c-3] += (int) cell.getNumericCellValue(); 
		                			}
		                		}
		                		else if(c>=11 && c<=16) {
		                			totTELOPscore += cell.getNumericCellValue();
		                			if(cell.getNumericCellValue() != 0) {
		                				zoneTELOPscore[c-11] += (int) cell.getNumericCellValue(); 
		                			}
		                		}
		                		else if(c == 17) {
		                			wheelComplete = 1;
		                		}
		                		else if(c == 18) {
		                			wheelComplete = 2;
		                		}
		                	}
		                }
		            }
		        }
		    }
		    
		    p = new JPanel() {
		        @Override
		        protected void paintComponent(Graphics g) {
			        super.paintComponent(g);
			        g.setColor(new Color(255, 4, 0, (int)(255*((zoneTELOPscore[0]+zoneAUTOscore[0])/((double)(totAUTOscore+totTELOPscore))))));
			        g.fillOval(775, 300, 50,50);//Makes circle
			        g.setColor(new Color(255, 4, 0, (int)(255*((zoneTELOPscore[1]+zoneAUTOscore[1])/((double)(totAUTOscore+totTELOPscore))))));
		        	g.fillOval(570, 50, 170,400);//Makes circle
		        	g.setColor(new Color(255, 4, 0, (int)(255*((zoneTELOPscore[2]+zoneAUTOscore[2])/((double)(totAUTOscore+totTELOPscore))))));
		        	g.fillOval(370,385, 200,50);//Makes circle
		        	g.setColor(new Color(255, 4, 0, (int)(255*((zoneTELOPscore[3]+zoneAUTOscore[3])/((double)(totAUTOscore+totTELOPscore))))));
		        	g.fillOval(270,385, 50, 50);//Makes circle
		        	g.setColor(new Color(255, 4, 0, (int)(255*((zoneTELOPscore[4]+zoneAUTOscore[4])/((double)(totAUTOscore+totTELOPscore))))));
		        	g.fillOval(75,75, 400,300);//Makes circle
		        }
		    };
		    p.setBackground(new Color(0,0, 0, .01f));//Makes a transparent background
	        
	        
		    
		    frame.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("field2020.png"))))); // Sets Background to field img
		    frame.setBackground(customColor);
		    p.setBounds(174, 50, 852, 461);
		    p.setBackground(new Color(0,0, 0, .01f));//Makes a transparent background
		    p.setForeground(new Color(0,100,100));//Colors the actual oval
		    frame.add(p);
		    
		    backb = new JButton();
			backb.setBounds(20, 20, 100, 25);
			backb.addActionListener(this);
			backb.setText("Back");
			frame.add(backb);
			
		    
		    System.out.println("Total Autonomous Points: " + totAUTOscore);
		    System.out.println("Total Teloperational Points: " + totTELOPscore);
		    
		    System.out.println("Teleoperation Points at Zone 1: " + (zoneTELOPscore[0]));
	    	System.out.println("Autonomous Points at Zone 1: " + (zoneAUTOscore[0]));
	    	System.out.println("Teleoperation Points at Zone 2/3: " + (zoneTELOPscore[1]));
	    	System.out.println("Autonomous Points at Zone 2/3: " + (zoneAUTOscore[1]));
	    	
		    for(int i=2; i<zoneTELOPscore.length;i++){
		    	System.out.println("Teleoperation Points at Zone " + (i+2) + ": " + (zoneTELOPscore[i]));
		    	System.out.println("Autonomous Points at Zone " + (i+2) + ": " + (zoneAUTOscore[i]));
		    }
		    
	    	
		    if(wheelComplete == 0) {
		    	System.out.println("Team#: "+ teamnum + " did not turn the wheel.");
		    }
		    else if(wheelComplete == 1) {
		    	System.out.println("Team#: "+ teamnum + " spun the wheel a certain number of times.");
		    }
		    else if(wheelComplete == 2) {
		    	System.out.println("Team#: "+ teamnum + " spun the wheel to a certain color.");
		    }
		    else {
		    	System.out.println("Error: wheelComplete Logic");
		    }
		    
		   
			frame.setVisible(true); // Makes the frame visible with all of it's stuff
			JOptionPane.showMessageDialog(null, "Zones with red colors indicate points scored from that zone. Darker reds = More points scored at that location.", "Tip: " + "Color Guide", JOptionPane.INFORMATION_MESSAGE);
			
	    }
	    
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==backb) {
			frame.remove(backb);
			frame.remove(p);
			JPanel refreshpane = new JPanel();
			refreshpane.setBackground(new Color(108, 211, 235));
			frame.setContentPane(refreshpane);
			new IndexFrame(frame);
		}
		
	}
	

}
