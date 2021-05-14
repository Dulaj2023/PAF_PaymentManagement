package com;

import java.sql.*;

public class Payment 
{
	
	//A common method to connect to the DB
	private Connection connect()
	{
		Connection con = null;
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			
			//Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/gadgetbadget_db", "root", "");
			
		}
		catch (Exception e)
		{e.printStackTrace();}
		
		return con;
	}
	
	public String insertPayment(String date, String amount, String desc)
	{
		String output = "";
		try
		{
			Connection con = connect();
			if (con == null)
			{return "Error while connecting to the database for inserting."; }
			
			// create a prepared statement
			String query = " insert into payments (`payment_id`,`date`,`payment_amount`,`payment_description`)"
					+ " values (?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, date);
			preparedStmt.setDouble(3, Double.parseDouble(amount));
			preparedStmt.setString(4, desc);
			
			// execute the statement3
			preparedStmt.execute();
			con.close();
			
			String newPayments = readPayment();
			output = "{\"status\":\"success\", \"data\": \"" +
					newPayments + "\"}";
			
		}
		catch (Exception e)
		{
			
			output = "{\"status\":\"error\", \"data\":\"Error while inserting the payments.\"}";
			System.err.println(e.getMessage());
			
		}
		
		return output;
	}
	
	public String readPayment()
	{
		String output = "";
		try
		{
			Connection con = connect();
			if (con == null)
			{return "Error while connecting to the database for reading."; }
			
			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>Paymont Date</th>"+ 
					"<th>Paymont Amount</th>" +
					"<th>Payment Description</th>" +
					"<th>Update</th><th>Remove</th></tr>";
			
			String query = "select * from payments";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			// iterate through the rows in the result set
			while (rs.next())
			{
				String payment_id = Integer.toString(rs.getInt("payment_id"));
				String date = rs.getString("date");
				String payment_amount = Double.toString(rs.getDouble("payment_amount"));
				String payment_description = rs.getString("payment_description");
				
				// Add into the html table
				output += "<tr><td><input id='hidPaymentIDUpdate' name='hidItemIDUpdate' type='hidden' value='" + payment_id + "'>"
						+ date + "</td>";
				output += "<td>" + payment_amount + "</td>";
				output += "<td>" + payment_description + "</td>";
				// buttons
				output += "<td><input name='btnUpdate' type='button' value='Update' "
						+ "class='btnUpdate btn btn-secondary' data-paymentid='" + payment_id + "'></td>"
						+ "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-paymentid='" + payment_id + "'>" +"</td></tr>";
			}
			
			con.close();
			// Complete the html table
			output += "</table>";
		}
		catch (Exception e)
		{
			
			output = "Error while reading the payments.";
			System.err.println(e.getMessage());
			
		}
		
		return output;
	}
	
	public String updatePayment(String id, String date, String amount, String desc)
	{
		String output = "";
		try
		{
			Connection con = connect();
			if (con == null)
			{return "Error while connecting to the database for updating."; }
			
			// create a prepared statement
			String query = "UPDATE payments SET date=?,payment_amount=?,payment_description=? WHERE payment_id=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
							
			// binding values
			preparedStmt.setString(1, date);
			preparedStmt.setDouble(2, Double.parseDouble(amount));
			preparedStmt.setString(3, desc);
			preparedStmt.setInt(4, Integer.parseInt(id));
			
			// execute the statement
			preparedStmt.execute();
			con.close();
			
			String newPayments = readPayment();
			output = "{\"status\":\"success\", \"data\": \"" +
					newPayments + "\"}";
			
		}
		catch (Exception e)
		{
			
			output = "{\"status\":\"error\", \"data\":\"Error while updating the payments.\"}";
			System.err.println(e.getMessage());
			
		}
		
		return output;
	}
	
	public String deletePayment(String payment_id)
	{
		String output = "";
		try
		{
			Connection con = connect();
			if (con == null)
			{return "Error while connecting to the database for deleting."; }
			
			// create a prepared statement
			String query = "delete from payments where payment_id=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(payment_id));
			
			// execute the statement
			preparedStmt.execute();
			con.close();
			
			String newPayments = readPayment();
			output = "{\"status\":\"success\", \"data\": \"" +
					newPayments + "\"}";
		}
		catch (Exception e)
		{
			output = "{\"status\":\"error\", \"data\":\"Error while deleting the payments.\"}";
			System.err.println(e.getMessage());
	}
	return output;
	}

}
