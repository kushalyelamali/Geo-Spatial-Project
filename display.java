// Author : KUSHAL YELAMALI

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class display {
    private Connection mainCon;
    String             building;
    String             TABLE1="buildings";
    
    String             TABLE2="students";
    String             TABLE3="AnounSys";

    public  Statement   StatementCreation;
    public  String      insertion;
   
    public display() {}

   

	
    public void Buildings(String building) throws SQLException {
        FileInputStream fstream123 = null;
        String[]        Liline123   = null;

        try {
        
     		StatementCreation = this.mainCon.createStatement();
        	StatementCreation.executeUpdate("DELETE "+ TABLE1);
        	StatementCreation.executeUpdate("COMMIT");
        	
            fstream123 = new FileInputStream(building);

           

            DataInputStream in = new DataInputStream(fstream123);
            BufferedReader  br = new BufferedReader(new InputStreamReader(in));
            String          strLine;


 
          
		  while((strLine = br.readLine()) != null) 
		  {
                Liline123 = strLine.split(", ");
                String     buildingName  = Liline123[1];
                int        VerticesNumber  = Integer.parseInt(Liline123[2]);
                String[][] vertices      = new String[VerticesNumber][2];
                String     buildingNumber    = Liline123[0];
                
                int        value             = 3;
                String     vertices_given = "";

               
                for (int i = 0; i < VerticesNumber; i++) {
                    for (int j = 0; j < 2; j++) {
                        vertices[i][j] = Liline123[value];
                        value++;
                    }
                }

                for (int i = 0; i < VerticesNumber; i++) {
                    for (int j = 0; j < 2; j++) {

                    
                        vertices_given = vertices_given + vertices[i][j];
                        vertices_given = vertices_given.concat(",");
                    }

                  
                }

                 vertices_given = vertices_given.substring(0, vertices_given.length() - 1);

                
                 insertbuilding(buildingNumber, buildingName, VerticesNumber, vertices_given);
            }

         
            in.close();
        } catch (IOException ex) {
            Logger.getLogger(display.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fstream123.close();
            } catch (IOException ex) {
                Logger.getLogger(display.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void connectDB() {
        try {

           
            DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
          

            String url      = "jdbc:oracle:thin:@localhost:1522:SYSADM";
            String userId   = "scott";
            String password = "tiger";

            mainCon = DriverManager.getConnection(url, userId, password);
            
        } catch (Exception e) {
            System.out.println("Error while connecting to DB: " + e.toString());
            e.printStackTrace();
            System.exit(-1);
        }
    }


    public void insertbuilding(String buildingno, String buildingName, int noofvertices, String inputvertices) {
        try {
             StatementCreation = this.mainCon.createStatement();
         
          
          insertion = "INSERT INTO Buildings VALUES('" + buildingno + "'" + ",'" + buildingName + "','" + noofvertices
                     + "'," + "SDO_GEOMETRY(2003,NULL,NULL," + "SDO_ELEM_INFO_ARRAY(1,1003,1),"
                     + "SDO_ORDINATE_ARRAY(" + inputvertices + ")))"; 

     
            StatementCreation.executeQuery(insertion);
        } catch (SQLException ex) {
            Logger.getLogger(display.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
  
    
  
    public void announcement(String anooun) throws SQLException {
        FileInputStream fstream123 = null;
        String[]        line_abc   = null;

        try {
        	StatementCreation = this.mainCon.createStatement();
        	StatementCreation.executeUpdate("DELETE "+TABLE3);
        	StatementCreation.executeUpdate("COMMIT");
        	
            fstream123 = new FileInputStream(anooun);

            DataInputStream in = new DataInputStream(fstream123);
            BufferedReader  br = new BufferedReader(new InputStreamReader(in));
            String          strLine;

          
            while ((strLine = br.readLine()) != null) {
                String inputvertices = "";

                line_abc = strLine.split(", ");

                String anoun_id     = line_abc[0];
                String inputverticeX = line_abc[1];
                String inputverticeY = line_abc[2];
                String radius = line_abc[3];

                inputvertices = inputvertices + inputverticeX + "," + inputverticeY;

                insertanoun(anoun_id , inputvertices,radius);
            }

            in.close();
        } catch (IOException ex) {
            Logger.getLogger(display.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fstream123.close();
            } catch (IOException ex) {
                Logger.getLogger(display.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void insertanoun(String anounid, String inputvertices,String radius) {
        try {
            StatementCreation = this.mainCon.createStatement();

           
            insertion = "INSERT INTO AnounSys VALUES('" + anounid + "'" + "," + "SDO_GEOMETRY(2001, NULL ,"
                    + "SDO_POINT_TYPE (" + inputvertices + ",NULL), NULL, NULL),'" + radius + "')";
           
            StatementCreation.executeQuery(insertion);
        } catch (SQLException ex) {
            Logger.getLogger(display.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void student(String student) throws SQLException {
        FileInputStream fstream = null;
        String[]        Line1   = null;

        try {
        	StatementCreation = this.mainCon.createStatement();
        	StatementCreation.executeUpdate("DELETE "+TABLE2);
        	StatementCreation.executeUpdate("COMMIT");
            fstream = new FileInputStream(student);

            DataInputStream in = new DataInputStream(fstream);
            BufferedReader  br = new BufferedReader(new InputStreamReader(in));
            String          strLine;

         
            while ((strLine = br.readLine()) != null) {
                String verticesgiven = "";

                Line1 = strLine.split(", ");

                String studentNo     = Line1[0];
                String inputverticeX = Line1[1];
                String inputverticeY = Line1[2];

                verticesgiven = verticesgiven + inputverticeX + "," + inputverticeY;


                insertStudent(studentNo, verticesgiven);
            }

           
            in.close();
        } catch (IOException ex) {
            Logger.getLogger(display.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fstream.close();
            } catch (IOException ex) {
                Logger.getLogger(display.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

  
    public void insertStudent(String studentno, String inputvertices) {
        try {
            StatementCreation = this.mainCon.createStatement();

           
            insertion = "INSERT INTO students VALUES('" + studentno + "'" + "," + "SDO_GEOMETRY(2001, NULL ,"
                     + "SDO_POINT_TYPE (" + inputvertices + ",NULL), NULL, NULL))";

           
            StatementCreation.executeQuery(insertion);
        } catch (SQLException ex) {
            Logger.getLogger(display.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
  
       
    public static void main(String[] args) throws SQLException {

        display pop = new display();
         
        pop.connectDB();
		pop.Buildings(args[0]);
        pop.student(args[1]);
        pop.announcement(args[2]);
		
        
         
    }
}




