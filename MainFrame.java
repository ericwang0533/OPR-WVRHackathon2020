import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class MainFrame extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static JButton button2020;
	public static JLabel WVRlabel;
	public static JLabel WVRScouting;
	//private static JLabel appName;
	public JFrame frame;
	public int teamnum;
	public ImageIcon backpic;
	public static JLabel FRClogo;
	
	
	public MainFrame(JFrame indexFrames, int teamnumIndex) {
		if (indexFrames == null) {
			frame = new JFrame();
			teamnum = 33;
		
			} else {
			
			frame = indexFrames;
			teamnum = teamnumIndex;
			
		}
			
			backpic = new ImageIcon("fieldimg.png");
			
			button2020 = new JButton();
			button2020.setBounds(500, 400, 200, 100);
			button2020.setText("2020");
			button2020.addActionListener(this);
			button2020.setFocusable(false);
			
			
			ImageIcon WVRicon = new ImageIcon(new ImageIcon("WVRshrinked.png").getImage().getScaledInstance(600, 300, Image.SCALE_DEFAULT));
			
			WVRlabel = new JLabel();
			WVRlabel.setIcon(WVRicon);
			WVRlabel.setBounds(345, 10, 520, 250);
			
			WVRlabel.setFocusable(false);
			WVRlabel.setHorizontalAlignment(JLabel.CENTER);
			WVRlabel.setForeground(new Color(0xFFFFFF));
			WVRlabel.setVerticalAlignment(JLabel.CENTER);
			WVRScouting = new JLabel();
			WVRScouting.setBounds(400, 250, 400, 150);
			WVRScouting.setText("FRC Scouting App");
			WVRScouting.setFocusable(false);
			WVRScouting.setHorizontalAlignment(JLabel.CENTER);
			WVRScouting.setFont(new Font("MV Boli", Font.PLAIN,40));
			WVRScouting.setForeground(new Color(0xFFFFFF));
			WVRScouting.setVerticalAlignment(JLabel.CENTER);
			
			ImageIcon FRCicon = new ImageIcon(new ImageIcon("1200px-FRC_Logo.svg.png").getImage().getScaledInstance(120, 80, Image.SCALE_DEFAULT));
			
			FRClogo = new JLabel();
			FRClogo.setIcon(FRCicon);
			FRClogo.setBounds(1030, 50, 120, 80);
			
			FRClogo.setFocusable(false);
			FRClogo.setHorizontalAlignment(JLabel.CENTER);
			FRClogo.setForeground(new Color(0xFFFFFF));
			FRClogo.setVerticalAlignment(JLabel.CENTER);
			frame.add(WVRlabel);
			frame.add(button2020);
			frame.add(WVRScouting);
			frame.add(FRClogo);
		if (indexFrames == null) {
			
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // When the X is clicked, the window closes
			frame.setResizable(false); // Keeps a consistent window size.
			frame.setSize(1200,600);  // In pixels, the size of the widow. (width x height)
			Color customColor = new Color(108, 211, 235); //Creates a background color
			frame.getContentPane().setBackground(customColor);
			frame.setLayout(null);
			
			
		}
		frame.setTitle("WVR Scouting App: Main Frame"); // Titles the Window
		frame.setVisible(false);
		frame.setVisible(true); // Makes the frame visible
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if (e.getSource()==button2020) {
		
			frame.remove(button2020);
			frame.remove(WVRScouting);
			frame.remove(WVRlabel);
			frame.remove(FRClogo);
			
			new IndexFrame(frame);
		} 
	}
}