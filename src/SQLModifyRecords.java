import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.*;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * This class can modify .csv or .txt records and insert records into a SQL database from a .csv or .txt file.
 * @author Michael Zhang
 */
public class SQLModifyRecords {
	/* database links */
	private static String JDBC_DRIVER = "";
	private static String DB_URL = "";

	/* database credentials */
	private static String USER = "";
	private static String PASS = "";

	/* file location */
	private static String FILE_LOCATION = "";

	/* table name */
	private static String TABLE_NAME = "";

	/**
	 * Inserts the records from a .csv or .txt file into a table.
	 */
	public static void insertToSQL() {
		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName(JDBC_DRIVER);

			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			BufferedReader br = new BufferedReader(new FileReader(FILE_LOCATION));

			stmt = conn.createStatement();

			String line = "";
			String ins = String.format("INSERT INTO %s ", TABLE_NAME);
			String sql = "";
			while ((line = br.readLine()) != null) {
				String[] r = line.split(",");
				/*
				 * sql = ins + String.format("VALUES ('%1$s', '%2$s', 'default', CURRENT_DATE)",
				 * r[0], r[1]);
				 */
				/*
				 * sql = ins +
				 * String.format("VALUES ('%1$s', '%2$s', '%3$s', TO_DATE('%4$s','YYYY-MM-DD'))"
				 * , r[0], r[1], r[2], r[3]);
				 */
				stmt.executeUpdate(sql);
			}
			br.close();
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					conn.close();
			} catch (SQLException se) {
				// do nothing
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
	}

	/**
	 * Duplicates the records of a .csv or .txt file by splicing together a record and an int.
	 */
	public static void duplicateRecords() {
		// String filename = JOptionPane.showInputDialog("Enter filepath to .csv or
		// .txt: ");
		// would need to import javax.swing.JOptionPane

		int upperBound = 9999; // number of times each record is to be repeated
		int index = 0; // the index to be repeated
		try {
			// Obtain file path.
			String filename = "";
			JFileChooser fc = new JFileChooser();
			int response = fc.showOpenDialog(null);
			if (response == JFileChooser.APPROVE_OPTION) {
				filename = fc.getSelectedFile().toString();
			} else {
				System.out.println("File operation cancelled.");
				return;
			}

			System.out.println("Please wait while records are updated (this may take awhile).");

			// Read contents of file.
			FileReader fr = new FileReader(filename);
			;
			BufferedReader reader = new BufferedReader(fr);
			ArrayList<String[]> linelist = new ArrayList<String[]>();
			String str;
			while ((str = reader.readLine()) != null) {
				linelist.add(str.split(","));
			}
			reader.close();
			fr.close();

			// Clear contents of file and re-populate with repeated records.
			FileWriter fw = new FileWriter(filename);
			PrintWriter writer = new PrintWriter(filename);
			writer.flush();
			for (int i = 0; i < linelist.size(); i++) {
				String[] row = linelist.get(i);
				for (int j = 1; j <= upperBound; j++) {
					row[index] = String.format("%1$s %2$s", j, row[index]);
					String insLine = String.join(",", row);
					writer.println(insLine);
				}
			}
			writer.close();
			fw.close();
			System.out.println("Records modified successfully!");
		} catch (IOException e) {
			System.out.println("Filepath is invalid.");
		}
	}

	/**
	 * Main method.
	 * @param args	arguments
	 */
	public static void main(String[] args) {
		SQLModifyRecords.insertToSQL();
		SQLModifyRecords.duplicateRecords();
	}

	/**
	 * Retrieves the access links required to link to a database.
	 * @return	an array of the entered { jdbc driver, database url }
	 */
	public static String[] dbCred() {
		String driver = JOptionPane.showInputDialog("Insert JDBC driver name");
		String url = JOptionPane.showInputDialog("Insert database URL");
		String[] r = { driver, url };
		return r;
	}

	/**
	 * Retrieves the credentials for the database (username and password).
	 * @return	an array of the entered { username, password }
	 */
	public static String[] userCred() {
		String username = JOptionPane.showInputDialog("Insert database username");
		String password = JOptionPane.showInputDialog("Insert database password");
		String[] s = { username, password };
		return s;
	}

	/**
	 * Retrieves the file location from user via a GUI.
	 * @return	the file path
	 */
	public static String getFileLocation() {
		String filename = "";
		JFileChooser fc = new JFileChooser();
		int response = fc.showOpenDialog(null);
		if (response == JFileChooser.APPROVE_OPTION) {
			filename = fc.getSelectedFile().toString();
		} else {
			System.out.println("File operation cancelled.");
			return null;
		}
		return filename;
	}

	/**
	 * Returns the contents of a .csv or .txt file as an ArrayList of String[] arrays.
	 * @return	the contents of a comma separated file
	 */
	public static ArrayList<String[]> getFileStuff() {
		String fileLocation = getFileLocation();
		ArrayList<String[]> file = new ArrayList<String[]>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileLocation));
			String line;
			while ((line = br.readLine()) != null) {
				// use comma as separator
				file.add(line.split(","));
			}
			br.close();
		} catch (Exception e) {
			System.out.println("Error");
		}
		return file;
	}

	/**
	 * Retrieves the table name from user via a GUI.
	 * @return	the table's name
	 */
	public static String getTablename() {
		String n = JOptionPane.showInputDialog("Insert table name");
		return n;
	}
}
