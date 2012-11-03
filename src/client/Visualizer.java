package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

/**
 * This class helps to produce a visualization of the different tasks
 */
class Visualizer {
	
	
	private static int N_PIXELS;
	// A Map to store the Color object for each value of k that is computed in MandelbrotSet task
	private static Map<Integer, Color> colorMap = new HashMap<Integer, Color>();	
	
	public static void visualize(int[][] counts, int numPixels, int[] tour, double[][] cities)
	{ 		
		N_PIXELS = numPixels;
		
	    JLabel euclideanTspLabel = displayEuclideanTspTaskReturnValue( cities, tour );
	    
	    JLabel mandelbrotLabel = displayMandelbrotSetTaskReturnValue( counts );

	    // display JLabels: graphic images
	    JFrame frame = new JFrame( "Result Visualizations" );
	    frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	    Container container = frame.getContentPane();
	    container.setLayout( new BorderLayout() );
	    container.add( new JScrollPane( euclideanTspLabel ), BorderLayout.EAST );
	    container.add( new JScrollPane( mandelbrotLabel ), BorderLayout.WEST );
	    frame.pack();
	    frame.setVisible( true );
	}


	private static JLabel displayEuclideanTspTaskReturnValue( double[][] cities, int[] tour )
	{
	    System.out.print( "Tour: ");
	    for ( int city: tour )
	    {
	        System.out.print( city + " ");
	    }
	    System.out.println("");

	    // display the graph graphically, as it were
	    // get minX, maxX, minY, maxY, assuming they 0.0 <= mins
	    double minX = cities[0][0], maxX = cities[0][0];
	    double minY = cities[0][1], maxY = cities[0][1];
	    for ( int i = 0; i < cities.length; i++ )
	    {
	        if ( cities[i][0] < minX ) minX = cities[i][0];
	        if ( cities[i][0] > maxX ) maxX = cities[i][0];
	        if ( cities[i][1] < minY ) minY = cities[i][1];
	        if ( cities[i][1] > maxY ) maxY = cities[i][1];
	    }
		
	    // scale points to fit in unit square
	    double side = Math.max( maxX - minX, maxY - minY );
	    double[][] scaledCities = new double[cities.length][2];
	    for ( int i = 0; i < cities.length; i++ )
	    {
	        scaledCities[i][0] = ( cities[i][0] - minX ) / side;
	        scaledCities[i][1] = ( cities[i][1] - minY ) / side;
	    }

	    Image image = new BufferedImage( N_PIXELS, N_PIXELS, BufferedImage.TYPE_INT_ARGB );
	    Graphics graphics = image.getGraphics();

	    int margin = 10;
	    int field = N_PIXELS - 2*margin;
	    // draw edges
	    graphics.setColor( Color.BLUE );
	    int x1, y1, x2, y2;
	    int city1 = tour[0], city2;
	    x1 = margin + (int) ( scaledCities[city1][0]*field );
	    y1 = margin + (int) ( scaledCities[city1][1]*field );
	    for ( int i = 1; i < cities.length; i++ )
	    {
	        city2 = tour[i];
	        x2 = margin + (int) ( scaledCities[city2][0]*field );
	        y2 = margin + (int) ( scaledCities[city2][1]*field );
	        graphics.drawLine( x1, y1, x2, y2 );
	        x1 = x2;
	        y1 = y2;
	    }
	    city2 = tour[0];
	    x2 = margin + (int) ( scaledCities[city2][0]*field );
	    y2 = margin + (int) ( scaledCities[city2][1]*field );
	    graphics.drawLine( x1, y1, x2, y2 );

	    // draw vertices
	    int VERTEX_DIAMETER = 6;
	    graphics.setColor( Color.RED );
	    for ( int i = 0; i < cities.length; i++ )
	    {
	        int x = margin + (int) ( scaledCities[i][0]*field );
	        int y = margin + (int) ( scaledCities[i][1]*field );
	        graphics.fillOval( x - VERTEX_DIAMETER/2,
	                           y - VERTEX_DIAMETER/2,
	                          VERTEX_DIAMETER, VERTEX_DIAMETER);
	    }
	    ImageIcon imageIcon = new ImageIcon( image );
	    return new JLabel( imageIcon );
	}
	
	
	private static JLabel displayMandelbrotSetTaskReturnValue( int[][] counts )
	{
	    Image image = new BufferedImage( N_PIXELS, N_PIXELS, BufferedImage.TYPE_INT_ARGB );
	    Graphics graphics = image.getGraphics();
	    for ( int i = 0; i < counts.length; i++ )
	    for ( int j = 0; j < counts.length; j++ )
	    {
	        graphics.setColor( getColor( counts[i][j] ) );
	        graphics.fillRect(i, j, 1, 1);
	    }
	    ImageIcon imageIcon = new ImageIcon( image );
	    return new JLabel( imageIcon );
	}

	private static Color getColor( int i )
	{		
	    if ( i == 64 ){
	    	return Color.BLACK;
	    }
	    Random rand = new Random();
	    Color color = colorMap.get(i);
	    if(color == null){
	    	color = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
		    colorMap.put(i, color);		    
	    }
	    return color;

	}
	
	
}
