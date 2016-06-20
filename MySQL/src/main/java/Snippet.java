
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Snippet { 
	/**
	 * @param args
	 */
	public static void main(String[] args) { 

		String ServerName = "75.126.155.213";
		int PortNumber = 50001;
		String DatabaseName = "I_849959";
		String user = "qzvtlfgm";
		String userPassword = "hzgkjmoezwmg";

		java.util.Properties properties = new java.util.Properties();

		properties.put("user", user);
		properties.put("password", userPassword);
		properties.put("sslConnection", "true");

		String url = "jdbc:db2://" + ServerName + ":"+ PortNumber + "/" +
				DatabaseName + ":traceFile=foobar.txt;traceLevel="+ 0xFFFFFFFF+ ";";

		// Connect to Database using JDBC URL
		Statement stmt = null;		

		java.sql.Connection con = null; 
		try
		{
			Class.forName("com.ibm.db2.jcc.DB2Driver").newInstance(); 
		}
		catch ( Exception e )
		{
			System.out.println("Error: Failed to Db2 jcc driver."); 
		}

		try
		{
			System.out.println("url: " + url);
			con = java.sql.DriverManager.getConnection(url, properties);
			stmt = con.createStatement();
			System.out.println("Successfully connected the Database" + DatabaseName);

		}catch (SQLException e) {
			System.out.println("Error creating schema: " + e);
		}

		

		// It is recommend NOT to use the default schema since it is correlated
		// to the generated user ID
		String schemaName = "ECODCNCSQL_EXTERNAL";
		// create a unique table name to make sure we deal with our own table
		// If another version of this sample app binds to the same database, 
		// this gives us some level of isolation
		String tableName = schemaName + "." + "PAYROLL" + System.currentTimeMillis();
		String sqlStatement = "CREATE SCHEMA " + schemaName;
		try {
			stmt = con.createStatement();
			// Create the CREATE SCHEMA SQL statement and execute it			
			stmt.executeUpdate(sqlStatement);
			System.out.println("Successfully Executed the Statement:" + sqlStatement);
		} catch (SQLException e) {
			System.out.println("Error creating schema: " + e);
		}

		// Create the CREATE TABLE SQL statement and execute it
		sqlStatement = "CREATE TABLE " + tableName
				+ " (NAME VARCHAR(20), AGE INTEGER)";
		// create a table
		try {
			stmt = con.createStatement();
			stmt.executeUpdate(sqlStatement);
			System.out.println("Successfully Executed the Statement:" + sqlStatement);
		} catch (SQLException e) {
			System.out.println("Error creating table: " + e);
		}

		// Execute some SQL statements on the table: Insert, Select and Delete
		try {
			sqlStatement = "INSERT INTO " + tableName
					+ " VALUES (\'John Smith\', 52)";

			stmt.executeUpdate(sqlStatement);
			System.out.println("Successfully Executed the Statement: " + sqlStatement);
			sqlStatement = "SELECT * FROM " + tableName
					+ " WHERE NAME LIKE \'John%\'";
			ResultSet rs = stmt.executeQuery(sqlStatement);
			System.out.println("Successfully Executed the Statement: " + sqlStatement);

			// Process the result set
			String empNo;
			while (rs.next()) {
				empNo = rs.getString(1);
				System.out.println("  Found Employee: " + empNo);
			}
			// Close the ResultSet
			rs.close();

			// Delete the record
			sqlStatement = "DELETE FROM " + tableName
					+ " WHERE NAME = \'John Smith\'";
			
			// Update the record
			// It will be similar to the Delete
			
			stmt.executeUpdate(sqlStatement);
			System.out.println("Successfully Executed the Statement: " + sqlStatement);
		} catch (SQLException e) {
			System.out.println("Error executing:" + sqlStatement);
			System.out.println("SQL Exception: " + e);
		}

		// Remove the table from the database
		try {
			sqlStatement = "DROP TABLE " + tableName;

			stmt.executeUpdate(sqlStatement);
			System.out.println("Successfully Executed the Statement: " + sqlStatement);

		} catch (SQLException e) {
			System.out.println("Error dropping table: " + e);
		}

		// Remove the schema from the database
		try {
			sqlStatement = "DROP SCHEMA " + schemaName + " RESTRICT";

			stmt.executeUpdate(sqlStatement);
			System.out.println("Successfully Executed the Statement: " + sqlStatement);
		} catch (SQLException e) {
			System.out.println("Error Dropping schema: " + e);
		}

		// Close everything off
		try {
			// Close the Statement
			stmt.close();
			// Connection must be on a unit-of-work boundary to allow close
			con.commit();
			// Close the connection
			con.close();
			System.out.println("Finished");

		} catch (SQLException e) {
			System.out.println("Error closing things off");
			System.out.println("SQL Exception: " + e);
		}
	}


}