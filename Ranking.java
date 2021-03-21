
import java.util.*;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Ranking implements ActionListener{
	private static final long serialVersionUID = 1L;
	JButton backb;
	private JLabel title;
    private JFrame frame;
	private JLabel ranking;
	private JLabel blank;
	int[] top30;
	
	public Ranking(JFrame a) throws IOException{
		frame = a;
		title = new JLabel();
		ranking = new JLabel();
		blank = new JLabel();
		
		Color customColor = new Color(108, 211, 235); //Creates a background color
        frame.setTitle("WVR Scouting App: Point Rankings"); // Titles the Window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // When the X is clicked, the window closes
	  	frame.setResizable(false); // Keeps a consistent window size.
	  	frame.setSize(1200,600);  // In pixels, the size of the widow. (width x height)
	  	frame.getContentPane().setBackground(customColor);
        
	  	backb = new JButton();
	    backb.setBounds(20, 20, 100, 25);
	    backb.setText("Back");
	    backb.addActionListener(this);
		frame.add(backb);
		
		title.setFont(new Font("MV Boli", Font.BOLD,40));
		title.setText("Top 30 Average Points Scored");
		title.setForeground(Color.WHITE);
		title.setBounds(260, -206, 852, 461);
		frame.add(title);
		
		ranking.setFont(new java.awt.Font("TimesRoman",Font.BOLD, 12));
		ranking.setForeground(Color.WHITE);
		ranking.setBounds(450, -100, 852, 800);
		
		blank.setFont(new java.awt.Font("TimesRoman",Font.PLAIN, 12));
		blank.setForeground(Color.WHITE);
		blank.setBounds(500, 50, 852, 461);
		
        String file2 = "oprmaster.xlsx";
        OPCPackage fs2 = null;
        try {
          fs2 = OPCPackage.open(new File(file2));
        } catch (InvalidFormatException e) {
        // TODO Auto-generated catch block
        	e.printStackTrace();
        }
        XSSFWorkbook wb2 = new XSSFWorkbook(file2);
        XSSFSheet sheet2 = wb2.getSheetAt(0);
     
       
        
	    String output = "<html>";
	    for(int i = 0; i < 30; i++) {
	    	output += (int)(i + 1) + ". Team#: " + (int) sheet2.getRow(i+1).getCell(0).getNumericCellValue() + ",  Avg. Points per Match: " + (int)sheet2.getRow(i+1).getCell(3).getNumericCellValue() + "<br>";
	    }
	    output += "</html>";
	    ranking.setText(output);
	    
        
	    frame.add(ranking);
	    blank.setText("");
	    frame.add(blank);
	    frame.setVisible(true);
        }
	
	public int findTeam(int a) {
		for(int i = 0;i<38;i++) {
			if(top30[i]==a) {
				return i;
			}
		}
		return 0;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==backb) {
			frame.remove(backb);
		    frame.remove(title);
		    frame.remove(blank);
		    frame.remove(ranking);
            new IndexFrame(frame);
		}
	}
}