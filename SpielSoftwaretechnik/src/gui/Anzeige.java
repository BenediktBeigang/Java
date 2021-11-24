package gui;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JProgressBar;
import javax.swing.border.EtchedBorder;

public class Anzeige extends JProgressBar{
	private static final long serialVersionUID = 1L;
	
	public Anzeige(int maxValue, int startValue, Color c){
		this.setBounds(-10,0,8,50);
		this.setVisible(true);
		this.setOrientation(JProgressBar.VERTICAL);
		this.setForeground(c);
		this.setBackground(Color.gray);
		this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		this.setMaximum(maxValue);
		this.setValue(startValue);
	}
		
	public void changeValueBy(int value) {
		this.setValue(this.getValue()+value);
	}
}
