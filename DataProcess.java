package interfacepack;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataProcess {
	/*
	 * Actual query and fetching Data works here
	 * */
	
	JScrollPane jp; 
	// // 1) test with user input
	// String SQLquery = "";
	
	
	// 2) test with assigned query
	String SQLquery = "SELECT IP.IND_NAME "+"FROM INDICIA_PUBLISHER IP, COUNTRY C "+"WHERE C.C_NAME = \'Belgium\' AND IP.C_ID = C.C_ID";
	//
	
	String InsDelQuery = "";
	
	String MainSearchQuery = "";
	String searchbuffer;
	
	String queryResult = "";
	Font font;
	
	int row = 20;
	int column = 18;
	int dataType[][] = new int[row][column];
	//0:integer, 1: string, 2: float, 3: date, 5: null
	
	String tableName[] = new String[row];
	String TableColumn[][] = new String[row][column];
	
	
	String insertMessage = "";
	String deleteMessage = "";
	
	
	public DataProcess(){
		dataTypeInits();
	}
	
	public void dataTypeInits(){
		//artist
		dataType[0][0] = 0;
		dataType[0][1] = 1;
		for(int i = 2; i <= 17; i++){
			dataType[0][i] = 5;
		}

		
		//brand group
		dataType[1][0] = 0;
		dataType[1][1] = 1;
		dataType[1][2] = 0;
		dataType[1][3] = 0;
		dataType[1][4] = 1;
		dataType[1][5] = 1;
		dataType[1][6] = 0;
		for(int i = 7; i <= 17; i++){
			dataType[1][i] = 5;
		}


		
		//chara in story
		dataType[2][0] = 0;
		dataType[2][1] = 0;
		for(int i = 2; i <= 17; i++){
			dataType[2][i] = 5;
		}


		//character
		dataType[3][0] = 0;
		dataType[3][1] = 1;
		for(int i = 2; i <= 17; i++){
			dataType[3][i] = 5;
		}


		//color by
		dataType[4][0] = 0;
		dataType[4][1] = 0;
		for(int i = 2; i <= 17; i++){
			dataType[4][i] = 5;
		}


		//country
		dataType[5][0] = 0;
		dataType[5][1] = 1;
		dataType[5][2] = 1;
		for(int i = 3; i <= 17; i++){
			dataType[5][i] = 5;
		}


		//feature
		dataType[6][0] = 0;
		dataType[6][1] = 0;
		for(int i = 2; i <= 17; i++){
			dataType[6][i] = 5;
		}


		//indicia publisher
		dataType[7][0] = 0;
		dataType[7][1] = 1;
		dataType[7][2] = 0;
		dataType[7][3] = 0;
		dataType[7][4] = 0;
		dataType[7][5] = 0;
		dataType[7][6] = 0;
		dataType[7][7] = 1;
		dataType[7][8] = 1;
		for(int i = 9; i <= 17; i++){
			dataType[7][i] = 5;
		}


		//ink by
		dataType[8][0] = 0;
		dataType[8][1] = 0;
		for(int i = 2; i <= 17; i++){
			dataType[8][i] = 5;
		}


		
		//issue
		dataType[9][0] = 0;
		dataType[9][1] = 1;
		dataType[9][2] = 0;
		dataType[9][3] = 0;
		dataType[9][4] = 1;
		dataType[9][5] = 1;
		dataType[9][6] = 2;
		dataType[9][7] = 1;
		dataType[9][8] = 1;
		dataType[9][9] = 1;
		dataType[9][10] = 1;
		dataType[9][11] = 1;
		dataType[9][12] = 1;
		dataType[9][13] = 1;
		dataType[9][14] = 1;
		dataType[9][15] = 1;
		dataType[9][16] = 5;
		dataType[9][17] = 5;

		
		//issue reprint
		dataType[10][0] = 0;
		dataType[10][1] = 0;
		for(int i = 2; i <= 17; i++){
			dataType[10][i] = 5;
		}


		
		//lang
		dataType[11][0] = 0;
		dataType[11][1] = 1;
		dataType[11][2] = 1;
		for(int i = 3; i <= 17; i++){
			dataType[11][i] = 5;
		}

		
		//pencil by
		dataType[12][0] = 0;
		dataType[12][1] = 0;
		for(int i = 2; i <= 17; i++){
			dataType[12][i] = 5;
		}
		
		//publisher
		dataType[13][0] = 0;
		dataType[13][1] = 1;
		dataType[13][2] = 0;
		dataType[13][3] = 0;
		dataType[13][4] = 0;
		dataType[13][5] = 1;
		dataType[13][6] = 1;
		for(int i = 7; i <= 17; i++){
			dataType[13][i] = 5;
		}
		
		//script by
		dataType[14][0] = 0;
		dataType[14][1] = 0;
		for(int i = 2; i <= 17; i++){
			dataType[14][i] = 5;
		}
		
		//series
		dataType[15][0] = 0;
		dataType[15][1] = 1;
		dataType[15][2] = 1;
		dataType[15][3] = 3;
		dataType[15][4] = 3;
		dataType[15][5] = 1;
		dataType[15][6] = 0;
		dataType[15][7] = 0;
		dataType[15][8] = 0;
		dataType[15][9] = 0;
		dataType[15][10] = 0;
		dataType[15][11] = 1;
		dataType[15][12] = 1;
		dataType[15][13] = 1;
		dataType[15][14] = 1;
		dataType[15][15] = 1;
		dataType[15][16] = 1;
		dataType[15][17] = 0;
		
		//series publication type
		dataType[16][0] = 0;
		dataType[16][1] = 1;
		for(int i = 2; i <= 17; i++){
			dataType[16][i] = 5;
		}
		
		//story
		dataType[17][0] = 0;
		dataType[17][1] = 1;
		dataType[17][2] = 1;
		dataType[17][3] = 1;
		dataType[17][4] = 1;
		dataType[17][5] = 1;
		dataType[17][6] = 1;
		dataType[17][7] = 1;
		dataType[17][8] = 1;
		dataType[17][9] = 1;
		dataType[17][10] = 1;
		dataType[17][11] = 0;
		dataType[17][12] = 0;
		for(int i = 13; i <= 17; i++){
			dataType[17][i] = 5;
		}
		
		//story type
		dataType[18][0] = 0;
		dataType[18][1] = 1;
		for(int i = 2; i <= 17; i++){
			dataType[18][i] = 5;
		}
		
		//story reprint
		dataType[19][0] = 0;
		dataType[19][1] = 0;
		for(int i = 2; i <= 17; i++){
			dataType[19][i] = 5;
		}
	}

	
	public void setSearchQuery(String q){
		//temporary function
		SQLquery = q;
	}
	
	public void setTableNameArray(String []arr){
		tableName = arr;
	}
	
	public void processQuery(){
		/* Queries for Predefined */
		DBMain.connectDB();	//connect to oracle DB
		PreparedStatement pstmt;
		try {
			pstmt = DBMain.connection.prepareStatement(SQLquery);		
			ResultSet rset = pstmt.executeQuery();
			printResults1(rset);
			rset.close();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try{
				DBMain.connection.close();
				System.out.println("DB connection closed.");
			}catch(SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
	}
	

	
	public void processInsertQuery(String queryhead, int caseNumber, String[] jtArray){
		/* .. */
		DBMain.connectDB();	//connect to oracle DB
		PreparedStatement pstmt;
		ResultSet rset;
		
		try {
			switch(caseNumber){
			
			case 0:
				InsDelQuery = queryhead + "(?,?)";
				pstmt = DBMain.connection.prepareStatement(InsDelQuery);
				
				pstmt.setInt(1, Integer.parseInt(jtArray[0]));
				pstmt.setString(2, jtArray[1]);

				rset = pstmt.executeQuery();
				rset.close();
				pstmt.close();
				break;
			
			case 1:
				InsDelQuery = queryhead + "(?,?)";
				pstmt = DBMain.connection.prepareStatement(InsDelQuery);
				
				pstmt.setInt(1, Integer.parseInt(jtArray[0]));
				pstmt.setInt(2, Integer.parseInt(jtArray[1]));
				
				rset = pstmt.executeQuery();
				rset.close();
				pstmt.close();
				break;
			
			case 2:
				//brand group
				InsDelQuery = queryhead + "(?,?,?,?,?,?,?)";
				pstmt = DBMain.connection.prepareStatement(InsDelQuery);
				
				pstmt.setInt(1, Integer.parseInt(jtArray[0]));
				pstmt.setString(2, jtArray[1]);
				pstmt.setInt(3, Integer.parseInt(jtArray[2]));
				pstmt.setInt(4, Integer.parseInt(jtArray[3]));
				pstmt.setString(5, jtArray[4]);
				pstmt.setString(6, jtArray[5]);
				pstmt.setInt(7, Integer.parseInt(jtArray[6]));
				
				rset = pstmt.executeQuery();
				rset.close();
				pstmt.close();
				break;
			
			case 3:
				//publisher
				InsDelQuery = queryhead + "(?,?,?,?,?,?,?)";
				pstmt = DBMain.connection.prepareStatement(InsDelQuery);
				
				pstmt.setInt(1, Integer.parseInt(jtArray[0]));
				pstmt.setString(2, jtArray[1]);
				pstmt.setInt(3, Integer.parseInt(jtArray[2]));
				pstmt.setInt(4, Integer.parseInt(jtArray[3]));
				pstmt.setInt(5, Integer.parseInt(jtArray[4]));
				pstmt.setString(6, jtArray[5]);
				pstmt.setString(7, jtArray[6]);
				
				rset = pstmt.executeQuery();
				rset.close();
				pstmt.close();
				break;
			
			case 4://country and lang. WORKS
				InsDelQuery = queryhead + "(?,?,?)";
				pstmt = DBMain.connection.prepareStatement(InsDelQuery);
				
				pstmt.setInt(1, Integer.parseInt(jtArray[0]));
				pstmt.setString(2, jtArray[1]);
				pstmt.setString(3, jtArray[2]);
				
				rset = pstmt.executeQuery();
				rset.close();
				pstmt.close();
				break;
			
			case 5:
				//indicia publisher
				InsDelQuery = queryhead + "(?,?,?,?,?,?,?,?,?)";
				pstmt = DBMain.connection.prepareStatement(InsDelQuery);
				
				pstmt.setInt(1, Integer.parseInt(jtArray[0]));
				pstmt.setString(2, jtArray[1]);
				pstmt.setInt(3, Integer.parseInt(jtArray[2]));
				pstmt.setInt(4, Integer.parseInt(jtArray[3]));
				pstmt.setInt(5, Integer.parseInt(jtArray[4]));
				pstmt.setInt(6, Integer.parseInt(jtArray[5]));
				pstmt.setInt(7, Integer.parseInt(jtArray[6])); //0 or 1 (surrogate
				pstmt.setString(8, jtArray[5]);
				pstmt.setString(9, jtArray[6]);
				
				rset = pstmt.executeQuery();
				rset.close();
				pstmt.close();
				break;
			
			case 6:
				//issue
				InsDelQuery = queryhead + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				pstmt = DBMain.connection.prepareStatement(InsDelQuery);
				
				pstmt.setInt(1, Integer.parseInt(jtArray[0]));
				pstmt.setString(2, jtArray[1]);
				pstmt.setInt(3, Integer.parseInt(jtArray[2]));
				pstmt.setInt(4, Integer.parseInt(jtArray[3]));
				pstmt.setString(5, jtArray[4]);
				pstmt.setString(6, jtArray[5]);
				pstmt.setFloat(7, Float.parseFloat(jtArray[6])); //float page count
				pstmt.setString(8, jtArray[7]);
				pstmt.setString(9, jtArray[8]);
				pstmt.setString(10, jtArray[9]);
				pstmt.setString(11, jtArray[10]);
				pstmt.setString(12, jtArray[11]);
				pstmt.setString(13, jtArray[12]);
				pstmt.setString(14, jtArray[13]);
				pstmt.setString(15, jtArray[14]);
				pstmt.setString(16, jtArray[15]);
							
				rset = pstmt.executeQuery();
				rset.close();
				pstmt.close();
				break;
			
			case 7:
				//series
				InsDelQuery = queryhead + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				pstmt = DBMain.connection.prepareStatement(InsDelQuery);
								
//				SimpleDateFormat parser = new SimpleDateFormat("dd-MMM-yy");
//				Date date1 = parser.parse(jtArray[3].substring(0, 9));
//				Date date2 = parser.parse(jtArray[4].substring(0, 9));
				
				pstmt.setInt(1, Integer.parseInt(jtArray[0]));
				pstmt.setString(2, jtArray[1]);
				pstmt.setString(3, jtArray[2]);
				pstmt.setDate(4, java.sql.Date.valueOf(jtArray[3]));	//"dd-MMM-yy"
				pstmt.setDate(5, java.sql.Date.valueOf(jtArray[4]));
				pstmt.setString(6, jtArray[5]);
				pstmt.setInt(7, Integer.parseInt(jtArray[6])); 
				pstmt.setInt(8, Integer.parseInt(jtArray[7]));
				pstmt.setInt(9, Integer.parseInt(jtArray[8]));
				pstmt.setInt(10, Integer.parseInt(jtArray[9]));
				pstmt.setInt(11, Integer.parseInt(jtArray[10]));
				pstmt.setString(12, jtArray[11]);
				pstmt.setString(13, jtArray[12]);
				pstmt.setString(14, jtArray[13]);
				pstmt.setString(15, jtArray[14]);
				pstmt.setString(16, jtArray[15]);
				pstmt.setString(17, jtArray[16]);
				pstmt.setInt(18, Integer.parseInt(jtArray[17]));
							
				rset = pstmt.executeQuery();
				rset.close();
				pstmt.close();
				break;
			
			case 8:
				//story
				InsDelQuery = queryhead + "(?,?,?,?,?,?,?,?,?,?,?,?)";
				pstmt = DBMain.connection.prepareStatement(InsDelQuery);
				
				pstmt.setInt(1, Integer.parseInt(jtArray[0]));
				pstmt.setString(2, jtArray[1]);
				pstmt.setString(3, jtArray[2]);
				pstmt.setString(4, jtArray[3]);
				pstmt.setString(5, jtArray[4]);
				pstmt.setString(6, jtArray[5]);
				pstmt.setString(7, jtArray[6]); 
				pstmt.setString(8, jtArray[7]);
				pstmt.setString(9, jtArray[8]);
				pstmt.setString(10, jtArray[9]);
				pstmt.setInt(11, Integer.parseInt(jtArray[10]));
				pstmt.setInt(12, Integer.parseInt(jtArray[11]));	
							
				rset = pstmt.executeQuery();
				rset.close();
				pstmt.close();
				
				break;
			}
			
			insertMessage = "Insertion sucessful!";
			
		} catch (Exception e) {
			insertMessage = e.getCause() + " " +e.getMessage();
			e.printStackTrace();
		}
		finally{
			try{
				DBMain.connection.close();
				System.out.println("DB connection closed.");
			}catch(SQLException e) {
				// TODO Auto-generated catch block
				
				e.printStackTrace();
			}
			
		}	
	}
	
	
	public void wrapSetPstmt(PreparedStatement pstmt, int type, int parameterIndex, String segment){	
		// Used in Delete Query.
		// Wraps setInt/setString/setFloat/setDate
		
		switch(type){
		case 0:	//int
			try {
				pstmt.setInt(parameterIndex, Integer.parseInt(segment));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 1:	//string
			try {
				pstmt.setString(parameterIndex, segment);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 2:	//float
			try {
				pstmt.setFloat(parameterIndex, Float.parseFloat(segment));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 3:	//date
			try {
				pstmt.setDate(parameterIndex, java.sql.Date.valueOf(segment));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
	}
	
	public void processDeleteQuery(String initialQuery, int tableNum, int[] posArray, String[] inputArray){
		/*
			Tested only with Country table.
		 * 
		 * */
		DBMain.connectDB();	//connect to oracle DB
		PreparedStatement pstmt;
		ResultSet rset;
		try{
			InsDelQuery = initialQuery;
			pstmt = DBMain.connection.prepareStatement(InsDelQuery);
			
			for(int i = 0; i < posArray.length; i++){
				int getType = dataType[tableNum][posArray[i]];
				wrapSetPstmt(pstmt, getType, i+1, inputArray[i]);					
			}
			
			
			rset = pstmt.executeQuery();
			rset.close();
			pstmt.close();
			
			deleteMessage = "Deletion Successful!";
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			deleteMessage = e.getCause() + " " + e.getMessage();
			e.printStackTrace();
		}
		finally{
			try{
				DBMain.connection.close();
				System.out.println("DB connection closed.");
			}catch(SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}	
	}
	
	
	public void changeQuery(String newOne){
		//?
		this.SQLquery = newOne;
	}
	
	public String exportResult(){
		return this.queryResult;
	}
	
	
	//**************
	void printResults1 (ResultSet rset) throws SQLException
	{
		//copied from URL: https://docs.oracle.com/cd/B28359_01/java.111/b31225/cheight.htm#CHDJJDGH 
		String buffer = "";
		try
		{
		  ResultSetMetaData meta = rset.getMetaData();
		  int cols = meta.getColumnCount(); 
		  int rows = 0;
		  for (int i = 1; i <= cols; i++)
		  {
			  int size = meta.getPrecision(i);
		      String label = meta.getColumnLabel(i);
		      if (label.length() > size){
		    	  size = label.length();
		      }
		      
		      while (label.length() < size){
		    	  label += " ";
		      }
		      
		      
		      buffer = buffer + label + " ";
		  }
		  
		  StringBuffer sb = new StringBuffer("");
		  for(int i = 0; i<buffer.length(); i++){
			  sb.append("-");
		  }
		  String addStringBuffer = sb.toString();
		  buffer = buffer + String.format("\n%s\n", addStringBuffer);
		  
		  //buffer = buffer + "\n";
		  // column names
		  
		  
		  while (rset.next())
		  {
			  //print actual data here
			  rows++;
			  for (int i = 1; i <= cols; i++)
			  {
				  int size = meta.getPrecision(i);
				  String label = meta.getColumnLabel(i);
				  String value = rset.getString(i);
				  if (label.length() > size){ 
					  size = label.length();
				  }
				  
				  try{
					  while (value.length() < size){
						  value += " ";
					  }					  
				  }catch(NullPointerException e){
					  //value += "(null)";
				  }
				  
				  buffer = buffer + value + " ";
			  }
			  buffer = buffer + "\n";
		  }
		  if (rows == 0){
			  buffer = "No data found!\n";			  
		  }
		  System.out.println(buffer);
		  queryResult = buffer;
	}
    catch (SQLException e)
    {
      System.err.println(e.getMessage());
    }
	}

	
	public String getInsertMessage(){
		return this.insertMessage;
	}
	
	public String getDeleteMessage(){
		return this.deleteMessage;
	}
	
	
	
	public void setTableColumns(String[][] TableColumns){
		this.TableColumn = TableColumns;	//get this info from mainwindowframe
	}
	
	
	
	public void processMainSearchQuery(String searchkey, boolean isAdvanced, ArrayList<Integer> checkedTableArr){
		/* 
		 * This function basically searches for a specific string in all columns in all tables, using for loop.
		 * 
		 * */
		
		
		DBMain.connectDB();	//connect to oracle DB
		PreparedStatement pstmt;
		ResultSet rset;
		
		String regexSearch = "lower(\'%"+searchkey+"%\')";		
				
		searchbuffer = "";//flush the buffer every time you click 'submit' button on the first tab.
		
		try {
			if(isAdvanced){
				// do search only for the selected table
				for(int i : checkedTableArr){
					String targetTable = tableName[i];
					ArrayList<Integer> strCnt = new ArrayList<Integer>();
					
					for(int k = 0; k < column; k++){
						if(dataType[i][k] == 1){
							strCnt.add(k);
						}
					}
					if(strCnt.size() > 0){//if at least one datatype string column exists, proceed
						String whereClause = "where ";
						for(int j = 0; j < strCnt.size(); j++){
							whereClause += (String.format("lower(%s.%s) like %s ", targetTable, TableColumn[i][strCnt.get(j)], regexSearch));
							if(j < strCnt.size() - 1){
								whereClause += "or ";
							}
						}
						
						MainSearchQuery = String.format("select * from %s %s", targetTable, whereClause);
						
						System.out.println(MainSearchQuery);//checking.
						
						pstmt = DBMain.connection.prepareStatement(MainSearchQuery);		
						rset = pstmt.executeQuery();
						printResults2(rset);
						rset.close();
						pstmt.close();				
					}
					else{//no String data type column in the table.
						
						MainSearchQuery = String.format("select * from %s ", targetTable);
						pstmt = DBMain.connection.prepareStatement(MainSearchQuery);		
						rset = pstmt.executeQuery();
						printResults3(rset);
						rset.close();
						pstmt.close();
					}
				}//for "i"
			}
			else{
				// plain search : takes time.
				for(int i = 0; i < row; i++){
					String targetTable = tableName[i];
					ArrayList<Integer> strCnt = new ArrayList<Integer>();
					
					for(int k = 0; k < column; k++){
						if(dataType[i][k] == 1){
							strCnt.add(k);
						}
					}
					if(strCnt.size() > 0){//if at least one datatype string column exists, proceed
						String whereClause = "where ";
						for(int j = 0; j < strCnt.size(); j++){
							whereClause += (String.format("lower(%s.%s) like %s ", targetTable, TableColumn[i][strCnt.get(j)], regexSearch));
							if(j < strCnt.size() - 1){
								whereClause += "or ";
							}
						}
						
						MainSearchQuery = String.format("select * from %s %s", targetTable, whereClause);
						
						System.out.println(MainSearchQuery);//checking.
						
						pstmt = DBMain.connection.prepareStatement(MainSearchQuery);		
						rset = pstmt.executeQuery();
						printResults2(rset);
						rset.close();
						pstmt.close();				
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try{
				DBMain.connection.close();
				System.out.println("DB connection closed.");
			}catch(SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
	}
	
	
	
	void printResults3(ResultSet rset){
		String tempbuffer = "\n";
		try
		{
		  ResultSetMetaData meta = rset.getMetaData();
		  int cols = meta.getColumnCount(); 
		  int rows = 0;
		  for (int i = 1; i <= cols; i++)
		  {
			  int size = meta.getPrecision(i);
		      String label = meta.getColumnLabel(i);
		      if (label.length() > size){
		    	  size = label.length();
		      }
		      
		      while (label.length() < size){
		    	  label += " ";
		      }
		      
		      
		      tempbuffer = tempbuffer + label + "  ||  ";
		  }
		  
		  StringBuffer sb = new StringBuffer("");
		  for(int i = 0; i<tempbuffer.length(); i++){
			  sb.append("-");
		  }

		  String addStringBuffer = sb.toString();
		  tempbuffer = tempbuffer + String.format("\n%s\n", addStringBuffer);
		  // column names
		  if (rows == 0){
			  //skip.
			  searchbuffer = searchbuffer + tempbuffer + "No data found!\n\n";			  
		  }

		  queryResult = searchbuffer;
	}
    catch (SQLException e)
    {
      System.err.println(e.getMessage());
    }
	}
	
	
	void printResults2 (ResultSet rset) throws SQLException
	{
		// ReferencedURL: https://docs.oracle.com/cd/B28359_01/java.111/b31225/cheight.htm#CHDJJDGH 
		// Modified by me
		
		String tempbuffer = "\n";
		try
		{
		  ResultSetMetaData meta = rset.getMetaData();
		  int cols = meta.getColumnCount(); 
		  int rows = 0;
		  for (int i = 1; i <= cols; i++)
		  {
			  int size = meta.getPrecision(i);
		      String label = meta.getColumnLabel(i);
		      if (label.length() > size){
		    	  size = label.length();
		      }
		      
		      while (label.length() < size){
		    	  label += " ";
		      }
		      
		      tempbuffer = tempbuffer + label + "  ||  ";//testing
		  }
		  
		  StringBuffer sb = new StringBuffer("");
		  for(int i = 0; i<tempbuffer.length(); i++){
			  sb.append("-");
		  }
		  String addStringBuffer = sb.toString();
		  searchbuffer = searchbuffer + tempbuffer + String.format("\n%s\n", addStringBuffer);
		  // column names
		  
		  
		  while (rset.next())
		  {
			  //print actual data here
			  rows++;
			  for (int i = 1; i <= cols; i++)
			  {
				  int size = meta.getPrecision(i);
				  //int size = 100;//set arbitrary number to reduce space.
				  String label = meta.getColumnLabel(i);
				  String value = rset.getString(i);
				  
				  if (label.length() > size){ 
					  size = label.length();
				  }
				  
				  //
				  try{
					  while (value.length() < size){
						  value += " ";
					  }					  
					  					  
				  }catch(NullPointerException e){
					  value = "*NULL*";
					  while (value.length() < size){
						  value += " ";
					  }					  
				  }
				  //
				  
				  searchbuffer = searchbuffer + value + "  ||  ";
			  }

			  searchbuffer = searchbuffer + "\n\n";

		  }
		  if (rows == 0){
			  //skip.
			  
			  searchbuffer = searchbuffer + "No data found!\n\n";
			  
		  }
		  System.out.println(searchbuffer);
		  queryResult = searchbuffer;
	}
    catch (SQLException e)
    {
      System.err.println(e.getMessage());
    }
	}
	
	
	
	
	
	public void processSingleStmt(String q){
		// Doesn't print out the query result.
		
		DBMain.connectDB();	//connect to oracle DB
		PreparedStatement pstmt;
		try {
			pstmt = DBMain.connection.prepareStatement(q);		
			ResultSet rset = pstmt.executeQuery();
			//printResults1(rset);	//printing not needed
			rset.close();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try{
				DBMain.connection.close();
				System.out.println("DB connection closed.");
			}catch(SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
	}
	
public void processSingleStmt2(String q){
		// Used for PROCEDURE
		DBMain.connectDB();	//connect to oracle DB
		PreparedStatement pstmt;
		try {
			pstmt = DBMain.connection.prepareStatement(q);		
			boolean rset = pstmt.execute();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try{
				DBMain.connection.close();
				System.out.println("DB connection closed.");
			}catch(SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
	}
	
	
	
	
	
	public void saveResultHistory() throws IOException {
		/** FileWriter save **/
		
		String saveString = "\n--Result Log--\n"+queryResult+"\n";
		//System.out.println(saveString);
		
		
		FileWriter fw = new FileWriter("./DBresult.txt", true);
		fw.write(saveString);
		fw.close();
	}
	
}