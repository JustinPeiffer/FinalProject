/*Justin Peiffer
 * This app is a Dungeons and dragons dice roller
 * application. You select the amount of die you
 * want and the number of sides you want and you
 * click roll. The app will roll and add the 
 * rolls together and output a value. You can
 * use the menu to add to log, save, and exit.
 * model class: Dice
 * view classes: TextPanel and DrawingPanel
 * controller class: DrawingFrame
 * The type of serialization that is supported is bin and xml
 * In the future I would move the buttons for number of dice
 * and number of sides into the menubar.
 */
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

class Log implements Serializable {
	private ArrayList<Integer> list;
	
	Log() {
		list.add(6);
	}
	
	public void add(int roll) {
		list.add(roll);
	}
}

class Dice {
	private int sides;
	private int number;
	private int rollValue;
	Random rand = new Random();
	
	public Dice() {
		sides = 6;
		number = 1;
	}
	
	public int roll() {
		rollValue = 0;
		int count = 1;
		
		while (count <= number) {
			rollValue += rand.nextInt((sides - 1) + 1) + 1;
			count++;
		}
		
		return rollValue;
	}
	
	public void setSides(int sides) {
		this.sides = sides;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public void setRollvalue(int value) {
		rollValue = value;
	}
	public int getSides( ) {
		return sides;
	}
	public int getNumber( ) {
		return number;
	}
	public int getRollValue( ) {
		return rollValue;
	}
	
	@Override
	public String toString() {
		return number + "d" + sides;
	}
	
}

class DrawingPanel extends JPanel {
	private int rollValue;
	
	public DrawingPanel() {
		rollValue = 0;
	}
	
	public void setRollValue(int value) {
		rollValue = value;
		repaint();
	}
	
	public void paintComponent(Graphics g) { 
		super.paintComponent(g);
		g.drawString(rollValue + "", 100, 100);
	}
}

class TextPanel extends JPanel {
	private int sides;
	private int number;
	
	public TextPanel() {
		sides = 6;
		number = 1;
	}
	
	public void setSides(int sides) {
		this.sides = sides;
		repaint();
	}
	public void setNumber(int number) {
		this.number = number;
		repaint();
	}
	public void paintComponent(Graphics g) { 
		super.paintComponent(g);
		g.drawString("Current Type: " + number + "d" + sides, 10, 10);
	}
}

class DrawingFrame extends JFrame {
	private Dice dice;
	private DrawingPanel panDrawing;
	private TextPanel panText;
	private Log rolls;
	
	public void setupMenu() {
		JMenuBar mbar = new JMenuBar(); 
		JMenu mnuFile = new JMenu("File");
		mbar.add(mnuFile);
		
		JMenuItem miExit = new JMenuItem("Exit");
	    miExit.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            System.exit(0);
	        }
	    });
	    JMenuItem miAddRoll = new JMenuItem("Add roll to log");
	    miAddRoll.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            rolls.add(dice.getRollValue());
	        }
	    });
	    JMenuItem miSave = new JMenuItem("Save");
	    miSave.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        	 String fname;
		         File f;
		         JFileChooser jfc = new JFileChooser();
		         if (jfc.showOpenDialog(DrawingFrame.this) == JFileChooser.APPROVE_OPTION) {
		        	 f = jfc.getSelectedFile();
		        	 fname = f.getName();
		        	 if(fname.endsWith(".xml")) {
		        		  try {
		        			  XMLEncoder xmlenc = new XMLEncoder(new BufferedOutputStream(
		        	                    new FileOutputStream(f.getPath())));
		        	          xmlenc.writeObject(rolls);
		        	          xmlenc.close();
		        			  System.out.print(fname + " was saved.");
		        		  } catch (Exception ex) {
		        			  System.out.print(fname + " could not be saved.");
		        		  }
		        	 }
		        	 if(fname.endsWith(".bin")) {
		        		  try {
		        			  ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f.getPath()));
		        			  oos.writeObject(rolls);
		        			  oos.close();
		        			  System.out.print(fname + " was saved.");
		        		   } catch (Exception ex) {
		        			  System.out.print(fname + " could not be saved.");
		        		   }
		        	   }
		         }
	        }
	    });
	    mnuFile.add(miSave);
	    mnuFile.add(miAddRoll);
	    mnuFile.add(miExit);
	    setJMenuBar(mbar);
	}
	
	public void setupUI() { 
		// generates the frame
		setDefaultCloseOperation(EXIT_ON_CLOSE); 
		setBounds(100,100,700,300);
		setTitle("Dungeons and dragons dice roller");
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		
		panDrawing = new DrawingPanel();
		panText = new TextPanel();
		
		JPanel panBtn = new JPanel();
		
		JButton btnRoll = new JButton("Roll");
		btnRoll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				panDrawing.setRollValue(dice.roll());
			}});
		JButton btn1 = new JButton("1");
		btn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				dice.setNumber(1);
				panText.setNumber(1);
			}});
		JButton btn2 = new JButton("2");
		btn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				dice.setNumber(2);
				panText.setNumber(2);
			}});
		JButton btn3 = new JButton("3");
		btn3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				dice.setNumber(3);
				panText.setNumber(3);
			}});
		JButton btn4 = new JButton("4");
		btn4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				dice.setNumber(4);
				panText.setNumber(4);
			}});
		JButton btn5 = new JButton("5");
		btn5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				dice.setNumber(5);
				panText.setNumber(5);
			}});
		
		JButton btnD4 = new JButton("D4");
		btnD4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				dice.setSides(4);
				panText.setSides(4);
			}});
		JButton btnD6 = new JButton("D6");
		btnD6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				dice.setSides(6);
				panText.setSides(6);
			}});
		JButton btnD8 = new JButton("D8");
		btnD8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				dice.setSides(8);
				panText.setSides(8);
			}});
		JButton btnD10 = new JButton("D10");
		btnD10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				dice.setSides(10);
				panText.setSides(10);
			}});
		JButton btnD12 = new JButton("D12");
		btnD12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				dice.setSides(12);
				panText.setSides(12);
			}});
		JButton btnD20 = new JButton("D20");
		btnD20.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				dice.setSides(20);
				panText.setSides(20);
			}});
		
		
		panBtn.add(btn1);
		panBtn.add(btn2);
		panBtn.add(btn3);
		panBtn.add(btn4);
		panBtn.add(btn5);
		panBtn.add(btnRoll);
		panBtn.add(btnD4);
		panBtn.add(btnD6);
		panBtn.add(btnD8);
		panBtn.add(btnD10);
		panBtn.add(btnD12);
		panBtn.add(btnD20);
	
		c.add(panText,BorderLayout.NORTH);
		c.add(panDrawing,BorderLayout.CENTER);
		c.add(panBtn,BorderLayout.SOUTH);
		setupMenu();
	} 
	
	public DrawingFrame() {
		dice = new Dice();
		setupUI();
	} 
} 

public class FinalProject {

	public static void main(String[] args) {
		DrawingFrame frm = new DrawingFrame(); 
		frm.setVisible(true);

	}

}
