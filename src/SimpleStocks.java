import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class SimpleStocks extends JApplet implements ActionListener{


	JButton generateButton = new JButton("Generate");
	JPanel  southPanel = new JPanel();
	static  Canvas  drawingBoard = new Canvas();
    int	   data[] = new int [SortCons.ARRAY_SIZE];
	
    GenerateDataThread generateDataThread = null;
    StockDataThreadOne stockDataThreadOne = null;
    StockDataThreadTwo stockDataThreadTwo = null;
    
    
//    ShuffleDataThread  shuffleDataThread = null;
//    MergeSortThread    mergeSortThread = null;
    
    public SimpleStocks(){
    	//Default Constructor
    }
    
    //Initializes components
    public void init() {
    	Container c = getContentPane();
    	c.setSize(650, 500);

    	c.setLayout(new BorderLayout() );
    	generateButton.addActionListener(this);
    	generateButton.setEnabled(true);
    	southPanel.add(generateButton);
    	
    	c.add(southPanel, BorderLayout.SOUTH);
    	c.add(drawingBoard, BorderLayout.CENTER);
    	drawingBoard.setBackground(Color.black);
       }

       public void start() {
       }
    
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		
		if(o.equals(generateButton)){
			generateDataThread = new GenerateDataThread(
			stockDataThreadOne, stockDataThreadTwo, data);
			generateDataThread.start();
		}
	}
	
	 public static void main( String argv[] ) {
			JFrame fmObj = new JFrame("Simple Stock Simulation");
			fmObj.addWindowListener( new  WindowAdapter() {
					public void windowClosing( WindowEvent e ) { System.exit(0); } } );
			fmObj.setSize(600, 500);
			SimpleStocks apObj = new SimpleStocks();
			apObj.init();
			fmObj.getContentPane().add(apObj);
			fmObj.setVisible(true);
			apObj.start();
		   }

}

class GenerateDataThread extends Thread {
	int [] data;
	   Thread thrd1 = null, thrd2 = null;

	   public GenerateDataThread(Thread t1, Thread t2, int [] d ) {
		   thrd1 = t1; 
		   thrd2 = t2; 
		   data = d;
	   }

	   public void run() {
		Random rnd = new Random();
		try { 
			if ( thrd1 != null ) 
				thrd1.join(); 
			if (thrd2 != null ) 
				thrd2.join();  }
		catch (InterruptedException e) {}
		for ( int i = 0; i < data.length; i ++ )
			data[i] = rnd.nextInt( SortCons.MAX_INT );
		DrawLinesThread t = new DrawLinesThread( data, 0, data.length );
		t.start();
		try { 
			t.join(); 
		} 
		catch (InterruptedException e) {}
	   }
}

class StockDataThreadOne extends Thread {
	int [] data;
	Thread thrd1 = null, thrd2 = null;
	
	public StockDataThreadOne(Thread t1, Thread t2, int [] d ){
		thrd1 = t1; 
		thrd2 = t2; 
		data = d;
		
	}
	
	public void run() {
		Random rnd = new Random();
		try { 
			if ( thrd1 != null ) 
				thrd1.join(); 
			if (thrd2 != null ) 
				thrd2.join();  }
		catch (InterruptedException e) {}
		for ( int i = 0; i < data.length; i ++ )
			data[i] = rnd.nextInt( SortCons.MAX_INT );
		DrawLinesThread t = new DrawLinesThread( data, 0, data.length );
		t.start();
		try { 
			t.join(); 
		} 
		catch (InterruptedException e) {}
	   }
	
	
}

class StockDataThreadTwo extends Thread {
	int [] data;
	Thread thrd1 = null, thrd2 = null;
	
	public StockDataThreadTwo(Thread t1, Thread t2, int [] d ){
		thrd1 = t1; 
		thrd2 = t2; 
		data = d;
	}
	
	public void run() {
		Random rnd = new Random();
		try { 
			if ( thrd1 != null ) 
				thrd1.join(); 
			if (thrd2 != null ) 
				thrd2.join();  }
		catch (InterruptedException e) {}
		for ( int i = 0; i < data.length; i ++ )
			data[i] = rnd.nextInt( SortCons.MAX_INT );
		DrawLinesThread t = new DrawLinesThread( data, 0, data.length );
		t.start();
		try { 
			t.join(); 
		} 
		catch (InterruptedException e) {}
	   }
}

class DrawLinesThread extends Thread {
	   int data[], lb, ub;
	   Color rectColor = Color.yellow, lineColor = Color.white, backgroundColor = Color.black;
	   Canvas cvs = SimpleStocks.drawingBoard;
	   Graphics2D g2d;

	   String title = "Simulate Stock Market Data";
	   FontMetrics  fm;

	   DrawLinesThread( int [] d, int l, int u ) {
		data = d; lb = l; ub = u; g2d = (Graphics2D) cvs.getGraphics();
		g2d.setFont( new Font("TimesRoman", Font.BOLD, 14));
	   }

	   public void run() {
		int cvsWidth = cvs.getWidth(), cvsHeight = cvs.getHeight();
		int drawingWidth = cvs.getWidth() - SortCons.RIGHT_INSET - SortCons.LEFT_INSET;
	 	int drawingHeight = cvs.getHeight() - SortCons.TOP_INSET - SortCons.BOTTOM_INSET;
		float gap = (float) drawingWidth / (SortCons.ARRAY_SIZE + 1);

		// g2d.clearRect(0, 0, drawingWidth, drawingHeight);
		g2d.setColor(rectColor);
		g2d.drawRect( SortCons.LEFT_INSET, SortCons.TOP_INSET, drawingWidth, drawingHeight);
		fm = g2d.getFontMetrics();
		g2d.drawString(title, (cvsWidth - fm.stringWidth(title)) / 2, SortCons.TOP_INSET-5);
//		if ( lb == 0 && ub == SortCons.ARRAY_SIZE ) 
//			lineColor = generateColor();
//		else 
//			lineColor = Color.white;

		for ( int i = lb; i < ub; i++ ) {
			// Erase a line
			g2d.setColor(backgroundColor);
			g2d.drawLine((int ) ( (i + 1)  * gap + SortCons.LEFT_INSET),
				cvsHeight - SortCons.BOTTOM_INSET,
				(int ) ( (i + 1)  * gap + SortCons.LEFT_INSET), 
				cvsHeight - SortCons.BOTTOM_INSET -  drawingHeight + 1 );
			// draw a line.
			if ( (lb != 0 || ub != SortCons.ARRAY_SIZE) && ( i == ub - 1 || i == SortCons.ARRAY_SIZE - 1))
				g2d.setColor(Color.magenta);
			else 
				g2d.setColor( generateColor());

			g2d.drawLine((int ) ( (i + 1)  * gap + SortCons.LEFT_INSET),
				cvsHeight - SortCons.BOTTOM_INSET,
				(int ) ( (i + 1)  * gap + SortCons.LEFT_INSET), 
				cvsHeight - SortCons.BOTTOM_INSET - 
				(int) ((float) data[i] / SortCons.MAX_INT * drawingHeight) );
			try { 
				sleep( data.length * 5 / (ub - lb)); 
			} 
			catch (InterruptedException e) {}
		}
	   }

	   Color generateColor() {
		float r = 0, g = 0, b = 0;
		while ( r+g+b < 0.5 )  {
			r = (float) Math.random(); g = (float) Math.random(); b = (float) Math.random(); }
		return new Color(r, g, b);
	  }
		
	}
