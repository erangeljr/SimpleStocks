import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class SimpleStocks extends JApplet implements ActionListener{


	JButton buttonArray[] = { new JButton("Generate"), 
				new JButton("Sort"), new JButton("Shuffle") };
	JPanel  southPanel = new JPanel();
	static  Canvas  drawingBoard = new Canvas();
    int	   data[] = new int [SortCons.ARRAY_SIZE];
	
    GenerateDataThread generateDataThread = null;
    
    public SimpleStocks(){
    	//Default Constructor
    }
    
    //Initializes components
    public void init() {
    	Container c = getContentPane();
    	c.setSize(650, 500);

    	c.setLayout(new BorderLayout() );
    	for ( int i = 0; i < buttonArray.length; i ++ ) {
    		buttonArray[i].addActionListener(this);
    		if ( i > 0 )  buttonArray[i].setEnabled(false);
    		southPanel.add(buttonArray[i]);
    	}
    	c.add(southPanel, BorderLayout.SOUTH);
    	c.add(drawingBoard, BorderLayout.CENTER);
    	drawingBoard.setBackground(Color.black);
       }

       public void start() {
       }
    
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}

class GenerateDataThread{
	
}