import java.util.*;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class DetailFrame extends MainFrame implements ActionListener{ //implements ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JButton backb;
    JFrame frame;
    private int teamnum;
	private JLabel def;
	private JLabel tar;
	private JLabel avg;
	private JLabel opr;
	private JLabel blank;
	private JLabel fieldpic;
	private JLabel title;
	
	public DetailFrame(JFrame a, int b) throws IOException{
		super(a,b);
		frame = a; // Creates get global frame variable, and sets it to a local variable
		teamnum = b;
		frame.remove(button2020);
		frame.remove(WVRScouting);
		frame.remove(WVRlabel);
		ArrayList<Integer> rowToGet = new ArrayList<Integer>(); // Stores the rows where the team's info is.
        int totalpoints = 0; // Counts how many points overall gained
        String defense = "No"; // Shows what type of defense
        String target = "No"; // Shows what type of target
        int numgames = 0; // Counts number of games played by the team number
	  	boolean isMultiDef = false; // Shows that the robot can change defense style
        boolean isMultiTar = false; // Shows that the robot faced different defenses
        def= new JLabel();
        tar= new JLabel();
        avg= new JLabel();
        opr = new JLabel();
        blank = new JLabel();
        title = new JLabel();
        
        ImageIcon backpic = new ImageIcon(new ImageIcon("data.png").getImage().getScaledInstance(400, 250, Image.SCALE_DEFAULT));
        fieldpic = new JLabel();
		fieldpic.setIcon(backpic);//Puts the Label as the ImageIcon
		fieldpic.setBounds(750, 15, 400, 250);
		frame.add(fieldpic);
        
		title.setText("Team " + teamnum + " Analysis");
		title.setBounds(250, 50, 500, 200);
		title.setFont(new Font("MV Boli", Font.BOLD,40));
		title.setForeground(Color.white);
		
        Color customColor = new Color(108, 211, 235); //Creates a background color
        frame.setTitle("WVR Scouting App: " + teamnum + " Analytics"); // Titles the Window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // When the X is clicked, the window closes
	  	frame.setResizable(false); // Keeps a consistent window size.
	  	frame.setSize(1200,600);  // In pixels, the size of the widow. (width x height)
	  	frame.getContentPane().setBackground(customColor);
        
	  	backb = new JButton();
	    backb.setBounds(20, 20, 100, 25);
	    backb.setText("Back");
	    backb.addActionListener(this);
		frame.add(backb);
		
		def.setFont(new java.awt.Font("TimesRoman",Font.PLAIN, 30));
		def.setForeground(Color.WHITE);
		def.setBounds(250, 0, 852, 461);
		
		tar.setFont(new java.awt.Font("TimesRoman",Font.PLAIN, 30));
		tar.setForeground(Color.WHITE);
		tar.setBounds(250, 60, 852, 461);
		
		avg.setFont(new java.awt.Font("TimesRoman",Font.PLAIN, 30));
		avg.setForeground(Color.WHITE);
		avg.setBounds(250, 120, 852, 461);
		
		opr.setFont(new java.awt.Font("TimesRoman",Font.PLAIN, 30));
		opr.setForeground(Color.WHITE);
		opr.setBounds(250, 180, 852, 461);
		
		blank.setFont(new java.awt.Font("TimesRoman", Font.PLAIN, 30));
		blank.setForeground(customColor);
		blank.setBounds(250, 240, 852, 461);
		
		
	  	
		int teams = 38; 	//sometimes known as r1
		String finale = "blueAllianceData.xlsx";
		OPCPackage finalefs = null;
		try {
			finalefs = OPCPackage.open(new File(finale));
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    XSSFWorkbook finalewb = new XSSFWorkbook(finale);
	    XSSFSheet finalesheet = finalewb.getSheetAt(0);
	    XSSFRow finalerow; 
	    XSSFCell finalecell; 

	    int finalerows;  // No of rows
	    finalerows = finalesheet.getPhysicalNumberOfRows();
	    int finalecols = 0;
	    int finaletmp = 0;
	    
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
	    
	    for(int i = 0; i < 10 || i < finalerows; i++) {
	        finalerow = finalesheet.getRow(i);
	        if(finalerow != null) {
	            finaletmp = finalesheet.getRow(i).getPhysicalNumberOfCells();
	            if(finaletmp > finalecols) finalecols = finaletmp;
	        }
	    }	
	    
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
        
        for(int r = 0; r < rowToGet.size()-1; r++) {
            row = sheet.getRow(rowToGet.get(r));
            if(row != null) {
                for(int c = 0; c < cols; c++) {
                    cell = row.getCell((short)c);
                    if(cell != null) {
	                	if(CellType.NUMERIC == cell.getCellType()) {
	                		if(c==3) {
	                			totalpoints += (cell.getNumericCellValue()*2);//Scored in the Low Set of Goals and Auto giving 2 points
	                		}
	                    else if(c >= 4 && c <= 8) {
	                    	totalpoints += (cell.getNumericCellValue()*4); //Scored in the High Set of Goals and Auto giving 4 points
	                    }
	                    else if(c==11) {
	                    	totalpoints += (cell.getNumericCellValue());//Scored in the Low Set of Goals and Telop giving 1 point
	                    }
	                    else if(c>=12 && c<=16) {
	                    	totalpoints += (cell.getNumericCellValue()*2);//Scored in the High Set of Goals and Telop giving 2 point
	                    }
	                    else if(c == 17 && cell.getNumericCellValue()!=0) {
	                        totalpoints += 10; //Scored Spinning the wheel 10 points
	                    }
	                    else if(c == 18 && cell.getNumericCellValue()!=0) {
	                        totalpoints += 20;//Scored Turning the wheel to a certain color 20 points
	                    }
	                  }
	                  else if(CellType.STRING == cell.getCellType()) {
	                      if(c == 19) {
	                    	  if(!"None".equals(cell.getStringCellValue())){
	                    		  defense = cell.getStringCellValue();
	                              	if(!defense.equals(defense)){
	                              		isMultiDef = true;
	                                }
	                          }
	                    }
	                    else if (c == 20) {
	                    	if(!"None".equals(cell.getStringCellValue())){
	                    		target = cell.getStringCellValue();
	                            if(!target.equals(target)){
	                            	isMultiTar = true;
	                            }
	                        }
	                    }
                      }
                    }
                }
            }
        }
	    
        double averagepoints= (totalpoints/(double)numgames);
        int roundedavgpoints= (int)Math.round(averagepoints);
        System.out.println(totalpoints);
        if (isMultiDef==true){
        	System.out.println("This robot can play both Heavy and Light defense");
        	def.setText("This robot can play both Heavy and Light defense");
        }
        else {
        	System.out.println("This robot can play "+defense+" defense");
        	def.setText("This robot can play "+defense+" defense");
        }
        frame.add(def);
        if (isMultiTar==true){
        	System.out.println("This robot has played against both Heavy and Light defense");
        	tar.setText("This robot has played against both Heavy and Light defense");
        }
        else {
        	System.out.println("This robot has played against "+target+" defense");
        	tar.setText("This robot has played against "+target+" defense");
        }
        frame.add(tar);
        System.out.println("This robot has an individual rounded average score of about " + roundedavgpoints + " points");
        avg.setText("This robot has an individual rounded average score of about " + roundedavgpoints + " points");
        frame.add(avg);
       
        
	    ArrayList<Integer> teamsList = new ArrayList<Integer>(Arrays.asList(
	            33, 94, 240, 247, 548, 573, 835, 1481, 2591, 3096, 3414, 3538, 4130, 4758, 4768, 4840, 4854, 5090, 5197, 5214, 5498, 5531, 5532, 5577, 5695, 5756, 6013, 6120, 6742, 7145, 7191, 7232, 7692, 7716, 7856, 7865, 8179, 8280
	        ));
	    
	    // Everything above is setup variables and sheets etc
	    // Everything below is matrix setups
	    
	    double scores[][] = new double[teams][teams]; // main matrix
	    double avgScores[] = new double[teams]; 	  // total score in all matches a team played in
        

        for(int i = 0; i < 76; i++){    //76 games/matches played
        	ArrayList<Integer> tmparr = new ArrayList<Integer>();
        	for(int j = 0; j < 3; j++) {
                int index = teamsList.indexOf((int) finalesheet.getRow(i).getCell(j).getNumericCellValue()); //replace j with team number from spreadsheet IMPORTANT IMPORTANT IMPORTANT IMPORTANT
                tmparr.add(index);
            }
        	for(int ab = 0; ab < tmparr.size(); ab++) {
        		for(int bc = 0; bc < tmparr.size(); bc++) {
        			scores[tmparr.get(ab)][tmparr.get(bc)]++;
        		}
        		avgScores[tmparr.get(ab)] += finalesheet.getRow(i).getCell(6).getNumericCellValue();
        	}
        	
        	ArrayList<Integer> tmparr1 = new ArrayList<Integer>();
        	
        	for(int six = 3; six < 6; six++) {
        		int index = teamsList.indexOf((int) finalesheet.getRow(i).getCell(six).getNumericCellValue()); //replace j with team number from spreadsheet IMPORTANT IMPORTANT IMPORTANT IMPORTANT
                tmparr1.add(index);
        	}
        	
        	for(int ab = 0; ab < tmparr1.size(); ab++) {
        		for(int bc = 0; bc < tmparr1.size(); bc++) {
        			scores[tmparr1.get(ab)][tmparr1.get(bc)]++;
        		}
        		avgScores[tmparr1.get(ab)] += finalesheet.getRow(i).getCell(7).getNumericCellValue();
        	}
        	
        }

        
        double inverse[][] = invert(scores);
        
        double[][] product = multiplyMatrices(inverse, avgScores, teams);
     
        
        
        if(numgames==0) {
        	frame.remove(fieldpic);
		    frame.remove(def);
		    frame.remove(avg);
		    frame.remove(tar);
		    frame.remove(opr);
		    frame.remove(blank);
		    frame.remove(backb);
		    frame.remove(title);
        	JOptionPane.showMessageDialog(null, "We do not have that team number", "InfoBox: " + "Error", JOptionPane.INFORMATION_MESSAGE);
        	new IndexFrame(frame);
        }
        else {
        	double teamopr = product[teamsList.indexOf(teamnum)][0];
            teamopr = Math.floor(teamopr * 10000) / 10000.0;
            System.out.println("teams opr: " + teamopr);
            opr.setText("This teams opr is: " + teamopr);
        	frame.add(opr);
            blank.setText(".");
          
            frame.add(def);
		    frame.add(avg);
		    frame.add(tar);
		    frame.add(opr);
		    frame.add(blank);
		    frame.add(backb);
		    frame.add(title);
            frame.setVisible(true); // Makes the frame visible with all of it's stuff
           
        }
        
	}	        
	
	//------------------------------------------------------------------------------------------------------
	
	public static double[][] invert(double a[][]){
		int n = a.length;
        double x[][] = new double[n][n];
        double b[][] = new double[n][n];
        int index[] = new int[n];
        for (int i=0; i<n; ++i){
            b[i][i] = 1;
        }
 
        // Transform the matrix into an upper triangle
        gaussian(a, index);
 
        // Update the matrix b[i][j] with the ratios stored
        for (int i=0; i<n-1; ++i){
            for (int j=i+1; j<n; ++j){
                for (int k=0; k<n; ++k){
                    b[index[j]][k] -= a[index[j]][i]*b[index[i]][k];
                }
            }
        }
        // Perform backward substitutions
        for (int i=0; i<n; ++i){
            x[n-1][i] = b[index[n-1]][i]/a[index[n-1]][n-1];
            for (int j=n-2; j>=0; --j){
                x[j][i] = b[index[j]][i];
                for (int k=j+1; k<n; ++k){
                    x[j][i] -= a[index[j]][k]*x[k][i];
                }
                x[j][i] /= a[index[j]][j];
            }
        }
        return x;
    }
 
    // Method to carry out the partial-pivoting Gaussian
    // elimination.  Here index[] stores pivoting order.
 
    public static void gaussian(double a[][], int index[]){
        int n = index.length;
        double c[] = new double[n];
 
        // Initialize the index
        for (int i=0; i<n; ++i){ 
            index[i] = i;
        }
 
        // Find the rescaling factors, one from each row
        for (int i=0; i<n; ++i){
            double c1 = 0;
            for (int j=0; j<n; ++j){
                double c0 = Math.abs(a[i][j]);
                if (c0 > c1) c1 = c0;
            }
            c[i] = c1;
        }
 
        // Search the pivoting element from each column
        int k = 0;
        for (int j=0; j<n-1; ++j){
            double pi1 = 0;
            for (int i=j; i<n; ++i){
                double pi0 = Math.abs(a[index[i]][j]);
                pi0 /= c[index[i]];
                if (pi0 > pi1){
                    pi1 = pi0;
                    k = i;
                }
            }
 
            // Interchange rows according to the pivoting order
            int itmp = index[j];
            index[j] = index[k];
            index[k] = itmp;
            for (int i=j+1; i<n; ++i){
                double pj = a[index[i]][j]/a[index[j]][j];
 
                // Record pivoting ratios below the diagonal
                a[index[i]][j] = pj;
 
                // Modify other elements accordingly
                for (int l=j+1; l<n; ++l){
                    a[index[i]][l] -= pj*a[index[j]][l];
                }
            }
        }
        
	}
    
    public static double[][] multiplyMatrices(double[][] firstMatrix, double[] secondMatrix, int r1) {
        double[][] product = new double[r1][1];
        for(int i = 0; i < r1; i++){
            for(int j = 0; j < r1; j++){
                product[i][0] += firstMatrix[i][j] * secondMatrix[j];
            }
        }

        return product;
    }
   
    @Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==backb) {
			frame.remove(fieldpic);
		    frame.remove(def);
		    frame.remove(avg);
		    frame.remove(tar);
		    frame.remove(title);
		    frame.remove(opr);
		    frame.remove(blank);
		    frame.remove(backb);
            new IndexFrame(frame);
		}
	}
}