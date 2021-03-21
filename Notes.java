import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.*;
public class Notes extends MainFrame implements ActionListener {

	public static String notes = "";
	public JFrame frame1;
	public int teamnum;
	
	
	
	public Notes(JFrame frame, int teamNumber) throws IOException, InvalidFormatException{
		super(frame, teamNumber);
		FileInputStream file = new FileInputStream(new File("notesData.xlsx")); //3538_2020misou.xlsx updatedOne.xlsx
		frame1 = frame;
		teamnum = teamNumber;
		frame.remove(button2020);
		frame.remove(WVRScouting);
		frame.remove(WVRlabel);
		
		
		int rowIndex = 1;
		
		XSSFWorkbook workbook = new XSSFWorkbook(file);

        XSSFSheet sheet = workbook.getSheetAt(0);
        XSSFRow row = sheet.getRow(rowIndex);
		
        file.close();
        
        boolean teamNumberThere = false;
        
        for(int i = 1; i < 39; i++) {
        	row = sheet.getRow(i);
        	XSSFCell teamCell = row.getCell(0, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
        	if ((int) teamCell.getNumericCellValue() == teamNumber) {
        		rowIndex = i;
        		teamNumberThere = true;
        	}
        }
        
        row = sheet.getRow(rowIndex);
        XSSFCell cell = row.getCell(1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
		frame.setTitle("WVR Scouting App: Notes"); // Titles the Window
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // When the X is clicked, the window closes
		frame.setResizable(false); // Keeps a consistent window size.
		frame.setSize(1200,600);  // In pixels, the size of the widow. (width x height) 
		
		final JTextArea displayNotes = new JTextArea();
		displayNotes.setBounds(50,50,600,450);
		displayNotes.setText(cell.getStringCellValue());
		
		final JButton backButton = new JButton("Back");
		backButton.setBounds(20, 20, 100, 25);
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				notes = displayNotes.getText();
				cell.setCellValue(notes);
				try (FileOutputStream outputStream = new FileOutputStream("notesData.xlsx")) {
		            workbook.write(outputStream);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				frame.remove(displayNotes);
				frame.remove(backButton);
				frame.setVisible(false);
				new IndexFrame(frame1);
			}
		});
		
		frame.add(backButton);
		frame.add(displayNotes);
		frame.setLayout(null);
		frame.setVisible(true); // Makes the frame visible
		if (teamNumberThere == false){
        	JOptionPane.showMessageDialog(null, "We do not have that team number", "InfoBox: " + "Error", JOptionPane.INFORMATION_MESSAGE);
			frame.remove(displayNotes);
			frame.remove(backButton);
			frame.setVisible(false);
        	new IndexFrame(frame1);
		}
		
	}

}