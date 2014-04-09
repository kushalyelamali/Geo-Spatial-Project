// Application written as Part of Home WOrk 2 of Database
// Author : KUSHAL YELAMALI



import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Enumeration;
import java.util.Random;
import java.sql.*;

import java.util.*;
import oracle.sdoapi.OraSpatialManager;
import oracle.sdoapi.geom.*;
import oracle.sdoapi.adapter.*;
import oracle.sdoapi.sref.*;
import oracle.sql.STRUCT;

import javax.imageio.ImageIO;
import javax.swing.*;


class ImageCanvas extends JPanel implements MouseListener ,MouseMotionListener{

	
	Graphics2D double_dimaension;
    int Asyn_radius=0;
    int Asyn_X=0;
    int Asyn_Y=0;
    
    Connection Connection_main = null;
    
    Statement Statement1 = null,Statement2 = null,Statement3 = null,  Statement41 = null, Statement44c = null;
    
    
    private BufferedImage img123;
    private Dimension dimensions123;
    private int [] x_coordinate;
    private int [] y_coordinate;
    
    int [] x_value = new  int[100];
	int [] y_value = new  int[100];
	int counter = 0;
	int ss = 0;
    
    public void setX_array( int [] a){
    	
    	x_coordinate = a;
    }
    
    public void setY_array( int [] b){
    	
    	y_coordinate = b;
    }
    
    
    public ImageCanvas(BufferedImage image_buffer) {
    	
    	addMouseListener(this);
    	addMouseMotionListener(this);
        img123 = image_buffer;
        dimensions123 = new Dimension(img123.getWidth(), img123.getHeight());
                
    }
    
    
    ResultSet mainResult = null, mainResult2 = null, mainResult3 = null, mainResult4 = null, mainResult5 = null;
    
    

    public void paint(Graphics g) {
    	
    	
    	super.paint(g);
        double_dimaension = (Graphics2D) g;
        double_dimaension.drawImage(img123, 0, 0, null);
        
        double_dimaension.setColor(Color.RED);
        if (UI_Swing.m_x != 0){
        	double_dimaension.fillRect(UI_Swing.m_x, UI_Swing.m_y, 5, 5);
        	double_dimaension.drawOval(UI_Swing.m_x-50, UI_Swing.m_y-50, 100,100);
        }	
        

  		
        if (UI_Swing.range_ex){ss=1;}
        
        if(UI_Swing.surrounding_stud){ss=2;}
        
        if (UI_Swing.emerg_query){ss=3;}
       
        
        
       switch (ss)
       {
	   case 1:
		  range();
		 break;
	   case 2:
		 surround();
		 break;
      case 3:
		 emergency();
		 break;
		
	}
    	
    	if (UI_Swing.submit__pressed ){
    		
    		DatabaseConnectivity();
    		
    		if(UI_Swing.emerg_query){ss=1;}
    		
    		
    		if(UI_Swing.surrounding_stud){ss=2;}
    		
    		
    		if (UI_Swing.range_ex){ss=3;} 
    		
    		if(UI_Swing.point_query){ss=4;}
    		
    		if (UI_Swing.region_whole) {ss=5;}
    		
    		  switch (ss) {
    			case 1:
    				submemer();
    				break;
    			case 2:
    				submsurround();
    				break;
    			case 3:
    				submitrange();
    				break;
    			case 4:
    				submitpoint();
    				break;
    			case 5:
    				submitwhole();
    				break;

    			}
    }
    	
    }
    STRUCT point;		
	Geometry geom;     
	oracle.sdoapi.geom.Polygon polygon1=null;

    
    private void submitwhole() {
    	

	    
	    SearchAllTuples();		

	    
        if(UI_Swing.building_name){
        	
		
		try
		{
	        ResultSetMetaData meta = mainResult.getMetaData();
	      
	        int tupleCount=1;
			GeometryAdapter sdoAdapter = OraSpatialManager.getGeometryAdapter("SDO", "9",STRUCT.class, null, null, Connection_main);

 	        while( mainResult.next() )
    	    {
	    	    STRUCT obj = (STRUCT)mainResult.getObject(4);
				geom = sdoAdapter.importGeometry(obj);
      			if ( (geom instanceof oracle.sdoapi.geom.Polygon) )
      			{
      				
      				populate_submit_whole();
      				
      			}

		    
       	    }
        }
		catch( Exception e )
	    { System.out.println(" Error : " + e.toString() ); 
	    
	    }

		
	}		
        
        
        if(UI_Swing.Students){
        	
        
        	STRUCT point;		
    		Geometry geom;     

    		try
    		{
                                    // shows result of the query
    	        ResultSetMetaData meta = mainResult2.getMetaData();

        	
    	        int tupleCount=1;

    	  		GeometryAdapter sdoAdapter = OraSpatialManager.getGeometryAdapter("SDO", "9",STRUCT.class, null, null, Connection_main);

     	        while( mainResult2.next() )
        	    {
    	    	   
    	    	    point = (STRUCT)mainResult2.getObject(2);
    				geom = sdoAdapter.importGeometry( point );
          			if ( (geom instanceof oracle.sdoapi.geom.Point) )
          			{
    					oracle.sdoapi.geom.Point point0 = (oracle.sdoapi.geom.Point) geom;
    					int X = (int) point0.getX();
    					int Y = (int)point0.getY();
    					double_dimaension.setColor(Color.GREEN);
    					double_dimaension.fillRect(X, Y, 10, 10);
    					 
    				}

    		      
           	    }
            }
    		catch( Exception e )
    	    { System.out.println(" Error : " + e.toString() ); }

        	
        	
        }
        
        if(UI_Swing.AnounSys){
        	
        	STRUCT point;		
    		Geometry geom;     

    		try
    		{
                                    // shows result of the query
    	        ResultSetMetaData meta = mainResult3.getMetaData();

        	  
    	        int tupleCount=1;

    	  		GeometryAdapter sdoAdapter = OraSpatialManager.getGeometryAdapter("SDO", "9",STRUCT.class, null, null, Connection_main);

     	        while( mainResult3.next() )
        	    {
    	    	  
    	    	    int Point_ID = mainResult3.getInt( 3 );
    	    	  

    	    	    point = (STRUCT)mainResult3.getObject(2);
    				geom = sdoAdapter.importGeometry( point );
          			if ( (geom instanceof oracle.sdoapi.geom.Point) )
          			{
    					oracle.sdoapi.geom.Point point0 = (oracle.sdoapi.geom.Point) geom;
    					int X = (int)point0.getX();
    					int Y = (int)point0.getY();
    				
    					double_dimaension.setColor(Color.RED);
    					double_dimaension.drawOval(X-Point_ID, Y-Point_ID, Point_ID*2, Point_ID*2);
    					double_dimaension.fillRect(X, Y, 15, 15);
    				}
          			

    		     
           	    }
            }
    		catch( Exception e )
    	    { System.out.println(" Error : " + e.toString() ); }

    	
        	
        	
        }
		
		
	}

	private void populate_submit_whole() {

		polygon1 = (Polygon) geom;
		
		for (Enumeration e = polygon1.getRings();e.hasMoreElements();) 
		{
				LineString lineString = (LineString)e.nextElement();
			CoordPoint[] Arraycord = lineString.getPointArray();
			int[] x = new int[Arraycord.length * 2];
				int[] y = new int[Arraycord.length * 2];
			int i=0;
			for (i = 0; i < Arraycord.length; i++) 
			{
			x[i] = (int) Arraycord[i].getX();
			
    	    y[i]= (int) Arraycord[i].getY();
    	
    		
    					
			}
			
			
			for ( i=0; i< x.length/2;i++ ){
				
											
				if(i==(x.length/2)-1){
					
				double_dimaension.setColor(Color.yellow);	
				double_dimaension.drawLine(x[i],y[i],x[0],y[0]);
				
				continue;
				}
				
				double_dimaension.setColor(Color.yellow);	
				double_dimaension.drawLine(x[i],y[i],x[i+1],y[i+1]);
				
			}  
		}				
	
		
	}

	private void submitpoint() {
    	

		
		if(UI_Swing.building_name){
			
			try
	    	{
	         
		        
		        int mouse_coX = UI_Swing.m_x;
		        int mouse_coY = UI_Swing.m_y;
		      
		        int valxplus = mouse_coX+50;
		        int valxminus = mouse_coX-50;
		        int valyplus = mouse_coY+50;
		        int valyminus = mouse_coY-50;
		        String fill = ","; 
		        String mouse_co = mouse_coX+fill+valyminus+fill+valxplus+fill+mouse_coY+fill+mouse_coX+fill+valyplus;
		        
		      
		        mainResult = Statement1.executeQuery( "select * from Buildings build1 where SDO_WITHIN_DISTANCE( build1.building_coordinates, SDO_GEOMETRY(2003,NULL,NULL,SDO_ELEM_INFO_ARRAY(1,1003,4),SDO_ORDINATE_ARRAY("+mouse_co+")),'distance = 0')  = 'TRUE'" );
		        mainResult4 = Statement41.executeQuery("select * from Buildings build1 where SDO_NN( build1.building_coordinates, SDO_GEOMETRY(2001,NULL,SDO_POINT_TYPE("+mouse_coX+","+mouse_coY+", NULL), NULL, NULL),'sdo_num_res=1')= 'TRUE'");
			}
	    	catch( Exception e )
		    { System.out.println( " Error : " + e.toString() ); }
			
					//Structure to handle Geometry Objects
			Geometry geom;     	//Structure to handle Geometry Objects

			try
			{
	                              
		       
		        int tupleCount=1;
		        oracle.sdoapi.geom.Polygon polygon=null;
		        
		  		GeometryAdapter sdoAdapter = OraSpatialManager.getGeometryAdapter("SDO", "9",STRUCT.class, null, null, Connection_main);

		  		
		  		
		  		while( mainResult.next() )
	    	    {
	 	        	

	 	    	    STRUCT obj = (STRUCT)mainResult.getObject(4);
	 				geom = sdoAdapter.importGeometry(obj);
	       			if ( (geom instanceof oracle.sdoapi.geom.Polygon) )
	       			{
	 					polygon = (Polygon) geom;
	 					
	 					for (Enumeration e = polygon.getRings();e.hasMoreElements();) 
	 					{
	        							LineString lineString = (LineString)e.nextElement();
	 						CoordPoint[] coordArray = lineString.getPointArray();
	         				int[] x = new int[coordArray.length * 2];
	  						int[] y = new int[coordArray.length * 2];
	 						int i=0;
	 						for (i = 0; i < coordArray.length; i++) 
	 						{
	 						//g.setColor(Color.yellow);
	 						x[i] = (int) coordArray[i].getX();
	 						
	                 	    y[i]= (int) coordArray[i].getY();
	                 	 		                 		
	                 					
	 						}  
	 						
	 						for ( i=0; i< x.length/2;i++ ){
    							
								
    							if(i==(x.length/2)-1){
    								
    							double_dimaension.setColor(Color.green);	
    							double_dimaension.drawLine(x[i],y[i],x[0],y[0]);
    							
    							continue;
    							}
    							
    							double_dimaension.setColor(Color.green);	
    							double_dimaension.drawLine(x[i],y[i],x[i+1],y[i+1]);
    							
    						}  
	 						
	 					}				
	 				}

	 		    
	       	    }
		  		
		  		 while (mainResult4.next()){
			  			
         			
			  			STRUCT obj = (STRUCT)mainResult4.getObject(3);
		 				geom = sdoAdapter.importGeometry(obj);
		       			if ( (geom instanceof oracle.sdoapi.geom.Polygon) )
		       			{
		 					polygon = (Polygon) geom;
		 					
		 					for (Enumeration e = polygon.getRings();e.hasMoreElements();) 
		 					{
		        							LineString lineString = (LineString)e.nextElement();
		 						CoordPoint[] coordArray = lineString.getPointArray();
		         				int[] x = new int[coordArray.length * 2];
		  						int[] y = new int[coordArray.length * 2];
		 						int i=0;
		 						for (i = 0; i < coordArray.length; i++) 
		 						{
		 					
		 						x[i] = (int) coordArray[i].getX();
		 					
		                 	    y[i]= (int) coordArray[i].getY();
		                 	
		                       }
		 						
		 					for ( i=0; i< x.length/2;i++ ){
	    							
									
	    							if(i==(x.length/2)-1){
	    								
	    							double_dimaension.setColor(Color.yellow);	
	    							double_dimaension.drawLine(x[i],y[i],x[0],y[0]);
	    							
	    							continue;
	    							}
	    							
	    							double_dimaension.setColor(Color.yellow);	
	    							double_dimaension.drawLine(x[i],y[i],x[i+1],y[i+1]);
	    						
	    						}  
		 				    }				
		 				}
			  			
			  		} 
	        }
			catch( Exception e )
		    { System.out.println(" Error : " + e.toString() ); }

		
			
			
			
		} 
		
		
		
		if(UI_Swing.Students){
			int mouse_coX = UI_Swing.m_x;
	        int mouse_coY = UI_Swing.m_y;
	        
	        int valxplus = mouse_coX+50;
	        int valxminus = mouse_coX-50;
	        int valyplus = mouse_coY+50;
	        int valyminus = mouse_coY-50;
	        String fill = ","; 
	        String mouse_co = mouse_coX+fill+valyminus+fill+valxplus+fill+mouse_coY+fill+mouse_coX+fill+valyplus;
	  
			
			try
	    	{
	                   
		        mainResult = Statement1.executeQuery( "select * from students stud where SDO_WITHIN_DISTANCE( stud.point, SDO_GEOMETRY(2003,NULL,NULL,SDO_ELEM_INFO_ARRAY(1,1003,4),SDO_ORDINATE_ARRAY("+mouse_co+")),'distance = 0')  = 'TRUE'" );
		        mainResult5 = Statement44c.executeQuery("select * from students stud where SDO_NN( stud.point, SDO_GEOMETRY(2001,NULL,SDO_POINT_TYPE("+mouse_coX+","+mouse_coY+", NULL), NULL, NULL),'sdo_num_res=1')= 'TRUE'");
			}
	    	catch( Exception e )
		    { System.out.println( " Error : " + e.toString() ); }
			
			
			
			STRUCT point;		//Structure to handle Geometry Objects
			Geometry geom;     	//Structure to handle Geometry Objects

			try
			{
	                            
		        ResultSetMetaData meta = mainResult.getMetaData();
		 
		        int tupleCount=1;
		        oracle.sdoapi.geom.Polygon polygon=null;
		        
		  		GeometryAdapter sdoAdapter = OraSpatialManager.getGeometryAdapter("SDO", "9",STRUCT.class, null, null, Connection_main);
		  		
		  		
		  		
	 	        while( mainResult.next() )
	    	    {
	 	        	
		    	    point = (STRUCT)mainResult.getObject(2);
					geom = sdoAdapter.importGeometry( point );
	      			if ( (geom instanceof oracle.sdoapi.geom.Point) )
	      			{
						oracle.sdoapi.geom.Point point0 = (oracle.sdoapi.geom.Point) geom;
						int X = (int)point0.getX();
						int Y = (int)point0.getY();
					
						double_dimaension.setColor(Color.GREEN);
						double_dimaension.fillRect(X, Y, 10, 10);
					}

			    
	       	    }
	 	        
	 	       while( mainResult5.next() )
	    	    {
	 	        	
	 	        	
	 	      
		    	    point = (STRUCT)mainResult5.getObject(2);
					geom = sdoAdapter.importGeometry( point );
	      			if ( (geom instanceof oracle.sdoapi.geom.Point) )
	      			{
						oracle.sdoapi.geom.Point point0 = (oracle.sdoapi.geom.Point) geom;
						int X = (int) point0.getX();
						int Y = (int) point0.getY();
						double_dimaension.setColor(Color.YELLOW);
					double_dimaension.fillRect(X, Y, 10, 10);
					}

			      
	       	    } 
	        }
			catch( Exception e )
		    { System.out.println(" Error : " + e.toString() ); }

			System.out.println();
			
			
			
			
		} // end of students
		
		
		if(UI_Swing.AnounSys){
			

			int mouse_coX = UI_Swing.m_x;
	        int mouse_coY = UI_Swing.m_y;
	       
	        int valxplus = mouse_coX+50;
	        int valxminus = mouse_coX-50;
	        int valyplus = mouse_coY+50;
	        int valyminus = mouse_coY-50;
	        String fill = ","; 
	        String mouse_co = mouse_coX+fill+valyminus+fill+valxplus+fill+mouse_coY+fill+mouse_coX+fill+valyplus;
	        
			
			try
	    	{
	                           
		       
		        mainResult = Statement1.executeQuery( "select * from AnounSys anoun where SDO_WITHIN_DISTANCE( anoun.Anoun_coordinate, SDO_GEOMETRY(2003,NULL,NULL,SDO_ELEM_INFO_ARRAY(1,1003,4),SDO_ORDINATE_ARRAY("+mouse_co+")),'distance = 0')  = 'TRUE'" );
		        mainResult5 = Statement44c.executeQuery("select * from AnounSys anoun where SDO_NN( anoun.Anoun_coordinate, SDO_GEOMETRY(2001,NULL,SDO_POINT_TYPE("+mouse_coX+","+mouse_coY+", NULL), NULL, NULL),'sdo_num_res=1')= 'TRUE'");
			}
	    	catch( Exception e )
		    { System.out.println( " Error : " + e.toString() ); }
			
			
			
			STRUCT point;		//Structure to handle Geometry Objects
			Geometry geom;     	//Structure to handle Geometry Objects

			try
			{
	                             
		        ResultSetMetaData meta = mainResult.getMetaData();
		  
	    	 
		        int tupleCount=1;
		        oracle.sdoapi.geom.Polygon polygon=null;
		        
		  		GeometryAdapter sdoAdapter = OraSpatialManager.getGeometryAdapter("SDO", "9",STRUCT.class, null, null, Connection_main);
		  	
		  		
		  		
	 	        while( mainResult.next() )
	    	    {
	 	        	
		    	    point = (STRUCT)mainResult.getObject(2);
					geom = sdoAdapter.importGeometry( point );
	      			if ( (geom instanceof oracle.sdoapi.geom.Point) )
	      			{
						oracle.sdoapi.geom.Point point0 = (oracle.sdoapi.geom.Point) geom;
						int X = (int)point0.getX();
						int Y = (int)point0.getY();
						double_dimaension.setColor(Color.GREEN);
						double_dimaension.fillRect(X, Y, 15, 15);
					}

	       	    }
	 	        
	 	       while( mainResult5.next() )
	    	    {
	 	        	
	 	        	

		    	    point = (STRUCT)mainResult5.getObject(2);
					geom = sdoAdapter.importGeometry( point );
	      			if ( (geom instanceof oracle.sdoapi.geom.Point) )
	      			{
						oracle.sdoapi.geom.Point point0 = (oracle.sdoapi.geom.Point) geom;
						int X = (int) point0.getX();
						int Y = (int) point0.getY();
						double_dimaension.setColor(Color.YELLOW);
					double_dimaension.fillRect(X, Y, 15, 15);
					}

	       	    } 
	        }
			catch( Exception e )
		    { System.out.println(" Error : " + e.toString() ); }

			System.out.println();
			
			
			
			
		}
		
	
		// TODO Auto-generated method stub
		
	}

	private void submitrange() {

		DatabaseConnectivity();
		
		String fill = ",";
		String co_od = "";
		for(int i = 1 ; i< UI_Swing.point_num;i++){
			
		    co_od = co_od + UI_Swing.range1[i] + fill + UI_Swing.range2[i];
		    
		    if(i!=(UI_Swing.point_num-1) )
			co_od = co_od + fill;
		}
		
		co_od = co_od + fill + UI_Swing.range1[1] + fill + UI_Swing.range2[1]; 
		

		
		if(UI_Swing.building_name){
			
			
			DatabaseConnectivity();
			try
	    	{
	          
		        
		        mainResult = Statement1.executeQuery("Select * from Buildings stud where SDO_INSIDE(stud.building_coordinates, SDO_GEOMETRY(2003, NULL, NULL, SDO_ELEM_INFO_ARRAY(1,1003,1), SDO_ORDINATE_ARRAY("+co_od+") )) = 'TRUE'");
		        
			}
	    	catch( Exception e )
		    { System.out.println( " Error : " + e.toString() ); }
			
					
			Geometry geom;     	

			try
			{
	                                
		       
		        int tupleCount=1;
		        oracle.sdoapi.geom.Polygon polygon=null;
		        
		  		GeometryAdapter sdoAdapter = OraSpatialManager.getGeometryAdapter("SDO", "9",STRUCT.class, null, null, Connection_main);

		  		
		  		
		  		while( mainResult.next() )
	    	    {
	 	        	

	 	    	    STRUCT obj = (STRUCT)mainResult.getObject(4);
	 				geom = sdoAdapter.importGeometry(obj);
	       			if ( (geom instanceof oracle.sdoapi.geom.Polygon) )
	       			{
	 					polygon = (Polygon) geom;
	 					
	 					for (Enumeration e = polygon.getRings();e.hasMoreElements();) 
	 					{
	        							LineString lineString = (LineString)e.nextElement();
	 						CoordPoint[] coordArray = lineString.getPointArray();
	         				int[] x = new int[coordArray.length * 2];
	  						int[] y = new int[coordArray.length * 2];
	 						int i=0;
	 						for (i = 0; i < coordArray.length; i++) 
	 						{
	 						
	 						x[i] = (int) coordArray[i].getX();
	 						
	                 	    y[i]= (int) coordArray[i].getY();
	                 		
	 						}  
	 						
	 						for ( i=0; i< x.length/2;i++ ){
    							
								
    							if(i==(x.length/2)-1){
    								
    							double_dimaension.setColor(Color.yellow);	
    							double_dimaension.drawLine(x[i],y[i],x[0],y[0]);
    							
    							continue;
    							}
    							
    							double_dimaension.setColor(Color.yellow);	
    							double_dimaension.drawLine(x[i],y[i],x[i+1],y[i+1]);
    							
    						}  
	 						
	 					}				
	 				}

	 		   
	       	    }
		  		 
	        }
			catch( Exception e )
		    { System.out.println(" Error : " + e.toString() ); }

			

		}  // end of buildings
		  			
		
		if(UI_Swing.Students){
			
			try
	    	{
	            
		        mainResult = Statement1.executeQuery( "Select * from students s where SDO_INSIDE(s.point, SDO_GEOMETRY(2003, NULL, NULL, SDO_ELEM_INFO_ARRAY(1,1003,1), SDO_ORDINATE_ARRAY("+co_od+"))) = 'TRUE'" );
		        
			}
	    	catch( Exception e )
		    { System.out.println( " Error : " + e.toString() ); }
			
			
			
			STRUCT point;		
			Geometry geom;     

			try
			{
	                               
		        ResultSetMetaData meta = mainResult.getMetaData();
		   
	    	  
		        int tupleCount=1;
		        oracle.sdoapi.geom.Polygon polygon=null;
		        
		  		GeometryAdapter sdoAdapter = OraSpatialManager.getGeometryAdapter("SDO", "9",STRUCT.class, null, null, Connection_main);
		  		
		  		
		  		while( mainResult.next() )
	    	    {
	 	        	
		    	    point = (STRUCT)mainResult.getObject(2);
					geom = sdoAdapter.importGeometry( point );
	      			if ( (geom instanceof oracle.sdoapi.geom.Point) )
	      			{
						oracle.sdoapi.geom.Point point0 = (oracle.sdoapi.geom.Point) geom;
						int X = (int)point0.getX();
						int Y = (int)point0.getY();
						
						double_dimaension.setColor(Color.GREEN);
						double_dimaension.fillRect(X,Y,10,10);
					}

			      
	       	    } 
	        }
			catch( Exception e )
		    { System.out.println(" Error : " + e.toString() ); }

			
			
			
			
			
		} // end of students
		
	if(UI_Swing.AnounSys ){
		
		
		try
    	{
                             
	       
	        mainResult = Statement1.executeQuery( "select * from AnounSys s where SDO_INSIDE(s.Anoun_coordinate, SDO_GEOMETRY(2003, NULL, NULL, SDO_ELEM_INFO_ARRAY(1,1003,1),   SDO_ORDINATE_ARRAY("+co_od+"))) = 'TRUE'" );
	        
		}
    	catch( Exception e )
	    { System.out.println( " Error : " + e.toString() ); }
		
		
		
		STRUCT point;		//Structure to handle Geometry Objects
		Geometry geom;     	//Structure to handle Geometry Objects

		try
		{
                                
	        ResultSetMetaData meta = mainResult.getMetaData();
	   
    	
	        int tupleCount=1;
	        oracle.sdoapi.geom.Polygon polygon=null;
	        
	  		GeometryAdapter sdoAdapter = OraSpatialManager.getGeometryAdapter("SDO", "9",STRUCT.class, null, null, Connection_main);
	  		
	  		
	  		while( mainResult.next() )
    	    {
 	        	
	    	    point = (STRUCT)mainResult.getObject(2);
				geom = sdoAdapter.importGeometry( point );
      			if ( (geom instanceof oracle.sdoapi.geom.Point) )
      			{
					oracle.sdoapi.geom.Point point0 = (oracle.sdoapi.geom.Point) geom;
					int X = (int)point0.getX();
					int Y = (int)point0.getY();
					
					double_dimaension.setColor(Color.RED);
					double_dimaension.fillRect(X,Y,15,15);
				}

		        System.out.println();  
       	    } 
        }
		catch( Exception e )
	    { System.out.println(" Error : " + e.toString() ); }

		System.out.println();
	}
		
		
	
		// TODO Auto-generated method stub
		
	}

	private void submsurround() {
    	

		
	
		
		int valxplus1 = Asyn_X+Asyn_radius;
        int valxminus1 = Asyn_X-Asyn_radius;
        int valyplus1 = Asyn_Y+Asyn_radius;
        int valyminus1 = Asyn_Y-Asyn_radius;
        String fill1 = ","; 
        String mouse_co1 = Asyn_X+fill1+valyminus1+fill1+valxplus1+fill1+Asyn_Y+fill1+Asyn_X+fill1+valyplus1;
     
        try
    	{
            DatabaseConnectivity();                    
	      
	        mainResult = Statement1.executeQuery("Select * from students s where SDO_INSIDE(s.point, SDO_GEOMETRY( 2003, NULL, NULL, SDO_ELEM_INFO_ARRAY(1,1003,4), SDO_ORDINATE_ARRAY("+mouse_co1+") )) = 'TRUE'");
	        
		}
    	catch( Exception e )
	    { System.out.println( " Error : " + e.toString() ); }
		
		
		
		STRUCT point;		//Structure to handle Geometry Objects
		Geometry geom;     	//Structure to handle Geometry Objects

		try
		{
                                // shows result of the query
	        ResultSetMetaData meta = mainResult.getMetaData();
	  
    	 
	        int tupleCount=1;
	        oracle.sdoapi.geom.Polygon polygon=null;
	        
	  		GeometryAdapter sdoAdapter = OraSpatialManager.getGeometryAdapter("SDO", "9",STRUCT.class, null, null, Connection_main);
	  		
	  		
	  		while( mainResult.next() )
    	    {
 	        	
	    	    point = (STRUCT)mainResult.getObject(2);
				geom = sdoAdapter.importGeometry( point );
      			if ( (geom instanceof oracle.sdoapi.geom.Point) )
      			{
					oracle.sdoapi.geom.Point point0 = (oracle.sdoapi.geom.Point) geom;
					int X = (int)point0.getX();
					int Y = (int)point0.getY();
				
					double_dimaension.setColor(Color.yellow);
					double_dimaension.fillRect(X,Y, 10, 10);
				}

		      
       	    } 
        }
		catch( Exception e )
	    { System.out.println(" Error : " + e.toString() ); }

		System.out.println();
		
		
		
		
		
		
		
	
		// TODO Auto-generated method stub
		
	}

	private void submemer() {
    			

		
		STRUCT point;		//Structure to handle Geometry Objects
		Geometry geom;     	//Structure to handle Geometry Objects

		try
		{
   
	  		for(int i=0;i<counter;i++){
	  			
	  	
	  			
	  			mainResult = Statement1.executeQuery("select * from AnounSys asp where SDO_NN( asp.Anoun_coordinate, SDO_GEOMETRY(2001,NULL,SDO_POINT_TYPE("+x_value[i]+","+y_value[i]+", NULL), NULL, NULL),'sdo_num_res=2')= 'TRUE'");  			
	  			
	  			GeometryAdapter sdoAdapter2 = OraSpatialManager.getGeometryAdapter("SDO", "9",STRUCT.class, null, null, Connection_main);
	  			
	  			
	  			while( mainResult.next() )
	    	    {
	 	        	
		    	    point = (STRUCT)mainResult.getObject(2);
		    	    int RADIUS = mainResult.getInt( 3 );
					geom = sdoAdapter2.importGeometry( point );
	      			if ( (geom instanceof oracle.sdoapi.geom.Point) )
	      			{
						oracle.sdoapi.geom.Point point0 = (oracle.sdoapi.geom.Point) geom;
						int X = (int)point0.getX();
						int Y = (int)point0.getY();
						if(Asyn_X == X && Asyn_Y ==Y)
							continue;
						
						double_dimaension.setColor(UI_Swing.AnounSys_color(X));
						double_dimaension.drawOval(X-RADIUS, Y-RADIUS, RADIUS*2, RADIUS*2);
						double_dimaension.fillRect(x_value[i], y_value[i], 5, 5);
						double_dimaension.fillRect(X,Y, 5, 5);
						
					}

			      
	       	    }
	  			
	  			
	  		}
	  		
	  		
	  		
        }
		catch( Exception e )
	    { System.out.println(" Error : " + e.toString() ); }

		System.out.println();
		
		
		
	
		// TODO Auto-generated method stub
		
	}

	private void emergency() {
    	

 	   
 	   

       	double_dimaension.fillRect(UI_Swing.m_EQ_x, UI_Swing.m_EQ_y, 5, 5);
       	
       	try
       	{
       		DatabaseConnectivity();                 
       		 
   	        mainResult3 = Statement3.executeQuery("select * from AnounSys asp where SDO_NN( asp.Anoun_coordinate, SDO_GEOMETRY(2001,NULL,SDO_POINT_TYPE("+UI_Swing.m_EQ_x+","+UI_Swing.m_EQ_y+", NULL), NULL, NULL),'sdo_num_res=1')= 'TRUE'");
   		}
       	catch( Exception e )
   	    { System.out.println( " Error : " + e.toString() ); }

       	STRUCT point;		//Structure to handle Geometry Objects
   		Geometry geom;     	//Structure to handle Geometry Objects

   		try
   		{
   			                  // shows result of the query
   	        ResultSetMetaData meta = mainResult3.getMetaData();

       	  
   	        int tupleCount=1;

   	  		GeometryAdapter sdoAdapter = OraSpatialManager.getGeometryAdapter("SDO", "9",STRUCT.class, null, null, Connection_main);

    	        while( mainResult3.next() )
       	    {
   	    	   

   	    	    int Point_ID = mainResult3.getInt( 3 );
   	    	    Asyn_radius = Point_ID;
   	    	    
   	    	    

   	    	    point = (STRUCT)mainResult3.getObject(2);
   				geom = sdoAdapter.importGeometry( point );
         			if ( (geom instanceof oracle.sdoapi.geom.Point) )
         			{
   					oracle.sdoapi.geom.Point point0 = (oracle.sdoapi.geom.Point) geom;
   					int X = (int)point0.getX();
   					int Y = (int)point0.getY();
   					Asyn_X = X;
   					Asyn_Y = Y;
   					
   					double_dimaension.setColor(Color.RED);
   					double_dimaension.drawOval(X-Point_ID, Y-Point_ID, Point_ID*2, Point_ID*2);
   				}
         			

   		      
          	    }
           
   		
   		
   		
   		}
   		catch( Exception e )
   	    { System.out.println(" Error : " + e.toString() ); }

   		
       	
   		int valxplus1 = Asyn_X+Asyn_radius;
		  //      int valxminus1 = AS_X-AS_radius;
		        int valyplus1 = Asyn_Y+Asyn_radius;
		        int valyminus1 = Asyn_Y-Asyn_radius;
		        String fill1 = ","; 
		        String mouse_co1 = Asyn_X+fill1+valyminus1+fill1+valxplus1+fill1+Asyn_Y+fill1+Asyn_X+fill1+valyplus1;
		       
		        
		        try
		    	{
		            DatabaseConnectivity();                    // searches for all tuples
			   
			        mainResult = Statement1.executeQuery("Select * from students s where SDO_INSIDE(s.point, SDO_GEOMETRY( 2003, NULL, NULL, SDO_ELEM_INFO_ARRAY(1,1003,4), SDO_ORDINATE_ARRAY("+mouse_co1+") )) = 'TRUE'");
			        
				}
		    	catch( Exception e )
			    { System.out.println( " Error : " + e.toString() ); }
				
				
		        
		        
		        
		        try
				{
		                                // shows result of the query
			        ResultSetMetaData meta = mainResult.getMetaData();
			   
		    	  
			        int tupleCount=1;
			        oracle.sdoapi.geom.Polygon polygon=null;
			        
			  		GeometryAdapter sdoAdapter = OraSpatialManager.getGeometryAdapter("SDO", "9",STRUCT.class, null, null, Connection_main);
			  		
			  		
			  		
			  		while( mainResult.next() )
		    	    {
		 	        	
			    	    point = (STRUCT)mainResult.getObject(2);
						geom = sdoAdapter.importGeometry( point );
		      			if ( (geom instanceof oracle.sdoapi.geom.Point) )
		      			{
							oracle.sdoapi.geom.Point point0 = (oracle.sdoapi.geom.Point) geom;
							int X = (int)point0.getX();
							int Y = (int)point0.getY();
						
							double_dimaension.setColor(Color.yellow);
							double_dimaension.fillRect(X,Y, 5, 5);
							x_value[counter] = X;
							y_value[counter] = Y;
							counter++;
							
							
						}

				     
		       	    }
			  		
			  	
				}
		        
		        catch( Exception e )
			    { System.out.println(" Error : " + e.toString() ); }
    	   
    	   
       
		// TODO Auto-generated method stub
		
	}

	private void surround() {
		

    	double_dimaension.fillRect(UI_Swing.m_ss_x, UI_Swing.m_ss_y, 5, 5);
    	
    	try
    	{
    		DatabaseConnectivity();                 
    		
	        mainResult3 = Statement3.executeQuery("select * from AnounSys asp where SDO_NN( asp.Anoun_coordinate, SDO_GEOMETRY(2001,NULL,SDO_POINT_TYPE("+UI_Swing.m_ss_x+","+UI_Swing.m_ss_y+", NULL), NULL, NULL),'sdo_num_res=1')= 'TRUE'");
		}
    	catch( Exception e )
	    { System.out.println( " Error : " + e.toString() ); }

    	STRUCT point;		//Structure to handle Geometry Objects
		Geometry geom;     	//Structure to handle Geometry Objects

		try
		{
			                  // shows result of the query
	        ResultSetMetaData meta = mainResult3.getMetaData();

    	
	        int tupleCount=1;

	  		GeometryAdapter sdoAdapter = OraSpatialManager.getGeometryAdapter("SDO", "9",STRUCT.class, null, null, Connection_main);

 	        while( mainResult3.next() )
    	    {
	    	   

	    	    int Point_ID = mainResult3.getInt( 3 );
	    	    Asyn_radius = Point_ID;
	    	
	    	    

	    	    point = (STRUCT)mainResult3.getObject(2);
				geom = sdoAdapter.importGeometry( point );
      			if ( (geom instanceof oracle.sdoapi.geom.Point) )
      			{
					oracle.sdoapi.geom.Point point0 = (oracle.sdoapi.geom.Point) geom;
					int X = (int)point0.getX();
					int Y = (int)point0.getY();
					Asyn_X = X;
					Asyn_Y = Y;
					
					double_dimaension.setColor(Color.RED);
					double_dimaension.fillRect(X, Y, 15, 15);
					double_dimaension.drawOval(X-Point_ID, Y-Point_ID, Point_ID*2, Point_ID*2);
				}
      			

		     
       	    }
        }
		catch( Exception e )
	    { System.out.println(" Error : " + e.toString() ); }

		System.out.println();
    	
    	
    	
    	
    
		// TODO Auto-generated method stub
		
	}

	private void range() {
		// TODO Auto-generated method stub

    	
    	
    	for ( int i=1; i<=UI_Swing.point_num ;i++ ){

    		if(i==1){
    			
    			double_dimaension.setColor(Color.red);	
    			double_dimaension.drawOval(UI_Swing.range1[i]-1, UI_Swing.range2[i]-1,2,2);
    			continue;
    		}
			
			double_dimaension.setColor(Color.red);	
			double_dimaension.drawLine(UI_Swing.range1[i-1],UI_Swing.range2[i-1],UI_Swing.range1[i],UI_Swing.range2[i]);
		
		}  
    	
    	
    
		
	}

	public void DatabaseConnectivity()
    {
    	try
		{
			// loading Oracle Driver
    		System.out.print("Looking for Oracle's jdbc-odbc driver ... ");
	    	DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
	    	System.out.println(", Loaded.");

		    String URL = "jdbc:oracle:thin:@localhost:1522:SYSADM";
	    	String userName = "scott";
	    	String password = "tiger";

	    	System.out.print("Connecting to DB...");
	    	Connection_main = DriverManager.getConnection(URL, userName, password);
	    	System.out.println(", Connected!");

    		Statement1 = Connection_main.createStatement();
    		Statement2 = Connection_main.createStatement();
    		Statement3 = Connection_main.createStatement();
    		
    		Statement41 = Connection_main.createStatement();
    		Statement44c = Connection_main.createStatement();

   		}
   		catch (Exception e)
   		{
     		System.out.println( "Error while connecting to DB: "+ e.toString() );
     		e.printStackTrace();
     		System.exit(-1);
   		}
    }
    
    public void SearchAllTuples()
    {
		try
    	{
	        if(UI_Swing.building_name)
	        mainResult = Statement1.executeQuery( "select * from Buildings " );
	        if(UI_Swing.Students)
	        mainResult2 = Statement2.executeQuery( "select * from students " );
	        if(UI_Swing.AnounSys)
	        mainResult3 = Statement3.executeQuery( "select * from AnounSys " );
		}
    	catch( Exception e )
	    { System.out.println( " Error : " + e.toString() ); }
    }

   
    public void ShowAllTuples()
    {
		STRUCT point;		//Structure to handle Geometry Objects
		Geometry geom;     	//Structure to handle Geometry Objects
		oracle.sdoapi.geom.Polygon polygon=null;

		try
		{
                                // shows result of the query
	        ResultSetMetaData meta = mainResult.getMetaData();

    	   
	        int tupleCount=1;
	        

	  		@SuppressWarnings("deprecation")
			GeometryAdapter sdoAdapter = OraSpatialManager.getGeometryAdapter("SDO", "9",STRUCT.class, null, null, Connection_main);

 	        while( mainResult.next() )
    	    {
	    	   
	    	    STRUCT obj = (STRUCT)mainResult.getObject(4);
				geom = sdoAdapter.importGeometry(obj);
      			if ( (geom instanceof oracle.sdoapi.geom.Polygon) )
      			{
					polygon = (Polygon) geom;
					for (Enumeration e = polygon.getRings();e.hasMoreElements();) 
					{
       							LineString lineString = (LineString)e.nextElement();
						CoordPoint[] coordArray = lineString.getPointArray();
        				int[] x = new int[coordArray.length * 2];
 						int[] y = new int[coordArray.length * 2];
						int i=0;
						for (i = 0; i < coordArray.length; i++) 
						{
						//g.setColor(Color.yellow);
						x[i] = (int) coordArray[i].getX();
						
                					y[i]= (int) coordArray[i].getY();
                					
						}
					}				
				}

		        System.out.println();
       	    }
        }
		catch( Exception e )
	    { System.out.println(" Error : " + e.toString() ); }

		System.out.println();
    }


    

    //ths method will colour a pixel white
    public boolean drawmine(int x, int y,boolean flag) {

        if (x > dimensions123.getHeight() || y > dimensions123.getWidth()) {
            return false;
        }
       
        img123.setRGB(x, y,  0xFFFFFFFF);//white

        if (flag)
           repaint();
        return true;
    }
   

    @Override
    public Dimension getPreferredSize() {
        return dimensions123;
    }

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {

       
        UI_Swing.text_box.setText(""+e.getX()+","+e.getY()+"");
        UI_Swing.set(e.getX(), e.getY());
        
        
        if(UI_Swing.emerg_query){
        
        	UI_Swing.m_EQ_x = e.getX();
        	UI_Swing.m_EQ_y = e.getY();
        	drawmine(10, 10, true);
        } else {
        	UI_Swing.m_EQ_x = 0;
        	UI_Swing.m_EQ_y = 0;
        	
        }
        
        
        
        if(UI_Swing.surrounding_stud){
        	
        	UI_Swing.m_ss_x = e.getX();
        	UI_Swing.m_ss_y = e.getY();
        	drawmine(10, 10, true);
        } else {
        	UI_Swing.m_ss_x = 0;
        	UI_Swing.m_ss_y = 0;
        	
        }
        
        
        
        if(UI_Swing.point_query){
        
        	UI_Swing.m_x = e.getX();
        	UI_Swing.m_y = e.getY();
        	drawmine(10, 10, true);
        } else {
        	UI_Swing.m_x = 0;
        	UI_Swing.m_y = 0;
      
        }
        
        if(UI_Swing.range_ex){
        	
        if (!SwingUtilities.isRightMouseButton(e)){
        	UI_Swing.point_num=UI_Swing.point_num+1;
        	UI_Swing.range1[UI_Swing.point_num] = e.getX();
        	UI_Swing.range2[UI_Swing.point_num] = e.getY();
        	drawmine(10, 10, true);
        }
        	
        } else {
        	
        	UI_Swing.point_num=0;
    		Arrays.fill(UI_Swing.range1, 0);
    		Arrays.fill(UI_Swing.range2,0);
    		drawmine(10, 10, true);
        	
        }
        
        
        if(SwingUtilities.isRightMouseButton(e)){
        	
        	if(UI_Swing.range_ex){
        
        	UI_Swing.point_num=UI_Swing.point_num+1;
        	UI_Swing.range1[UI_Swing.point_num] = UI_Swing.range1[1];
        	UI_Swing.range2[UI_Swing.point_num] = UI_Swing.range2[1];
        	drawmine(10, 10, true);
        	
        	} else {
        		UI_Swing.point_num=0;
        		Arrays.fill(UI_Swing.range1, 0);
        		Arrays.fill(UI_Swing.range2,0);
        		
        	}
        }
        
    
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		UI_Swing.text_box1.setText("X:"+e.getX()+","+e.getY());
		// TODO Auto-generated method stub
		
	}
}




public class UI_Swing extends JFrame implements ActionListener{
	

	public static boolean AnounSys;
	public static boolean building_name;
	public static boolean Students;
	public static int m_x;
	public static int m_y;
	public static int m_ss_x;
	public static int m_ss_y;
	public static int m_EQ_x;
	public static int m_EQ_y;

	static int [] range1 = new  int[100]; 
	static int [] range2 = new  int[100]; 
	public static int point_num = 0; 
	
	int x,y;


public static final JLabel text_box = new JLabel();
public static final JLabel text_box1 = new JLabel();


public int getXCord(){
	
	return this.x;
}

public int getYCord(){
	
	return this.y;
}

public static void set(int a,int b){
	
	int x = a;
	int y = b;
}

public void setXCord(int a){
	
	this.x = a;
}

public void setYCord(int b){
	
	this.y = b;
}


public static JPanel outer_panel = new JPanel();
public static final JRadioButton RButton1 = new JRadioButton();
public static final JRadioButton RButton2 = new JRadioButton();
public static final JRadioButton RButton3 = new JRadioButton();
public static final JRadioButton RButton4 = new JRadioButton();
public static final JRadioButton RButton5 = new JRadioButton();
public static ImageCanvas image_Panel = null;
public static int previous = 0;


public static ActionListener hii = new java.awt.event.ActionListener() {
    public void actionPerformed(java.awt.event.ActionEvent evt) {
        jRadioButton1ActionPerformed(evt);
    }

    private void jRadioButton1ActionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
    	
    	UI_Swing.submit__pressed = false;
    	
		if(RButton1.isSelected() && previous != 1){
			previous = 1;
			if(RButton1.isSelected())
				region_whole = true;
				else{
					region_whole = false;
					image_Panel.drawmine(10, 10, true);
				}
			RButton5.setSelected(false);
	        RButton2.setSelected(false);
	        RButton3.setSelected(false);
	        RButton4.setSelected(false);
	        point_query = false;
	    	range_ex = false;
	    	surrounding_stud = false;
	    	emerg_query = false;
	    	
	    	
	    		
			
			image_Panel.drawmine(1, 1,true);
		
		}else if( previous != 1){
			region_whole = false;
			image_Panel.drawmine(10, 10, true);
		}
		
		if(RButton2.isSelected() && previous != 2){
			previous = 2;
		if(RButton2.isSelected())
			point_query = true;
			else{
				point_query = false;
				m_x =0;
				m_y =0;
				image_Panel.drawmine(10, 10, true);
			}
			
			RButton5.setSelected(false);
	        RButton1.setSelected(false);
	        RButton3.setSelected(false);
	        RButton4.setSelected(false);
	        region_whole = false;
	    	range_ex = false;
	    	surrounding_stud = false;
	    	emerg_query = false;
	    	
	    	
	    	
	}else if( previous != 2){
		point_query = false;
		m_x =0;
		m_y =0;
		image_Panel.drawmine(10, 10, true);
	}
		
		if(RButton3.isSelected() && previous != 3){
			previous = 3;
			if(RButton3.isSelected())
				range_ex = true;
				else{
					range_ex = false;
				
					image_Panel.drawmine(10, 10, true);
				}
			RButton5.setSelected(false);
	        RButton1.setSelected(false);
	        RButton2.setSelected(false);
	        RButton4.setSelected(false);
	        region_whole = false;
	    	point_query = false;
	    	surrounding_stud = false;
	    	emerg_query = false;
			
		
		}else if( previous != 3){
			range_ex = false;
	
			image_Panel.drawmine(10, 10, true);
		}
		
		if(RButton4.isSelected() && previous != 4){
			previous = 4;
			if(RButton4.isSelected())
				surrounding_stud = true;
				else{
					surrounding_stud = false;
					m_ss_x =0;
					m_ss_y =0;
					image_Panel.drawmine(10, 10, true);
				}
			RButton5.setSelected(false);
	        RButton1.setSelected(false);
	        RButton2.setSelected(false);
	        RButton3.setSelected(false);
	        region_whole = false;
	    	point_query = false;
	    	range_ex = false;
	    	emerg_query = false;
		}else if( previous != 4){
			surrounding_stud = false;
			m_ss_x =0;
			m_ss_y =0;
			image_Panel.drawmine(10, 10, true);
		}

		if(RButton5.isSelected() && previous != 5){
			previous = 5;
			if(RButton5.isSelected())
				emerg_query = true;
				else{
					emerg_query = false;
					m_EQ_x =0;
					m_EQ_y =0;
					image_Panel.drawmine(10, 10, true);
				}
				
				RButton4.setSelected(false);
		        RButton1.setSelected(false);
		        RButton2.setSelected(false);
		        RButton3.setSelected(false);
		        region_whole = false;
		    	point_query = false;
		    	range_ex = false;
		    	surrounding_stud = false;
		}else if( previous != 5){
			emerg_query = false;
			m_EQ_x =0;
			m_EQ_y =0;
			image_Panel.drawmine(10, 10, true);
		}
		
	}
};

public static boolean submit__pressed;

public static Color AnounSys_color(int col){

	
	switch(col)
	{
	case 90: return Color.green;
	        
	case 227:return Color.pink;
	case 284:return Color.MAGENTA;
	case 438:return Color.ORANGE;
	case 390:return Color.BLUE;
	case 263:return Color.CYAN;
	case 448:return Color.WHITE;
	}
	return null;
	
	
}

    public static void main(String[] args) throws IOException {
    	
    	   JFrame frame_main = new JFrame();
    	String image_path = "map.jpg";
        File image_file = new File(image_path);
        final BufferedImage image123 = ImageIO.read(image_file);
        image_Panel = new ImageCanvas(image123);
        
        JLabel label = new JLabel(new ImageIcon(image123));
        label.setAlignmentX(5);
       
        JPanel panel = new JPanel();
        
        JPanel panel_left = new JPanel();
        BorderLayout left = new BorderLayout();
        panel_left.setLayout(left);
        
     
              
          
        panel_left.add(image_Panel);
      
        panel.add(panel_left,BorderLayout.WEST);
        
      
        JPanel bottom_window = new JPanel();
      
    
        JPanel checkbox_panel = new JPanel();
        final JCheckBox CheckBox1 = new JCheckBox();
        final JCheckBox CheckBox2 = new JCheckBox();
        final JCheckBox CheckBox3 = new JCheckBox();
        CheckBox1.setText("AS");
        CheckBox2.setText("Students");
        CheckBox3.setText("Buildings");
        
        GridLayout layout = new GridLayout(2,2);
        checkbox_panel.setLayout(layout);
        checkbox_panel.add(CheckBox1);
        checkbox_panel.add(CheckBox2);
        checkbox_panel.add(CheckBox3);
       

        
        RButton1.setText("Whole Region");
        RButton1.addActionListener(hii);
        RButton2.setText("Point Query");
        RButton2.addActionListener(hii);
        RButton3.setText("Range Query");
        RButton3.addActionListener(hii);
        RButton4.setText("Surrounding Student");
        RButton4.addActionListener(hii);
        RButton5.setText("Emergency Query");
        RButton5.addActionListener(hii);
        
       
        
        BoxLayout boxlayout_right_middle = new BoxLayout(outer_panel, BoxLayout.Y_AXIS );
        outer_panel.setLayout(boxlayout_right_middle);
        outer_panel.add(RButton1);
        outer_panel.add(RButton2);
        outer_panel.add(RButton3);
        outer_panel.add(RButton4);
        outer_panel.add(RButton5);
        outer_panel.setPreferredSize(new Dimension(0,200));
  
          
        
        
        JPanel right_panel = new JPanel();
        BoxLayout boxlayout = new BoxLayout(right_panel, BoxLayout.Y_AXIS );
        
        right_panel.setLayout(boxlayout);
        
        
        right_panel.add(checkbox_panel);
        right_panel.add(outer_panel);
        
        JButton Panel_submit = new JButton("Submit Query");
        Panel_submit.setMinimumSize(new Dimension(100, 40));
        Panel_submit.setPreferredSize(new Dimension(100, 40));
   
        Panel_submit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }

			private void jRadioButton1ActionPerformed(ActionEvent evt) {
				// TODO Auto-generated method stub
				
				submit__pressed = true;
				if(CheckBox1.isSelected())
		            AnounSys = true;
				else
					AnounSys = false;
		        if(CheckBox2.isSelected())
		            Students = true;
		        else
					Students = false;
		        if(CheckBox3.isSelected())
		            building_name = true;
		        else
					building_name = false;
		        image_Panel.drawmine(1,1,true);
				
			}
        });
        
        right_panel.add(Panel_submit);

        right_panel.add(text_box1);

        JPanel last_right = new JPanel();
        
        
        JTextArea ta = new JTextArea(10,25);
        ta.setEditable ( false );
        JScrollPane scroll = new JScrollPane ( ta );
        scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );

        
        
        right_panel.add(last_right);
   
        
        panel.add(right_panel,BorderLayout.EAST);
      
        frame_main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame_main.getContentPane().add(panel).repaint();
        frame_main.pack();
        frame_main.setResizable(false);
        frame_main.setVisible(true);
     
    }


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	public static boolean region_whole;
	public static boolean point_query;
	public static boolean range_ex;
	public static boolean surrounding_stud;
	public static boolean emerg_query;

 }

