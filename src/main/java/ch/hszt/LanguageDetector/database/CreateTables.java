package ch.hszt.LanguageDetector.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Diese Klasse erstellt die Tabelle Kunde. Achtung!!!!!! Sollten die
 * entsprechende Tabelle bereits vorhanden sein, wird diese gelöscht und neu
 * erstellt. Deshalb sollte diese Klasse mit Bedacht ausgeführt werden.
 */
public class CreateTables {

	/**
	 * Diese Methode erstellt die Tabellen Wort und Sprache. Achtung!!!!!!
	 * Sollten die entsprechende Tabelle bereits vorhanden sein, werden diese
	 * gelöscht und neu erstellt. Deshalb sollte diese Klasse mit Bedacht
	 * ausgeführt werden.
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws Exception
	 *             wenn die Connection mit der language-Datenbank nicht
	 *             erfolgreich ist
	 */
	public void createTable() throws ClassNotFoundException, SQLException {

		Class.forName("org.sqlite.JDBC");

		Connection conn = DriverManager
				.getConnection("jdbc:sqlite:language.db");
		delete(conn);
		create(conn);

	}

	public void delete(Connection conn) throws SQLException {

		try {
			Statement stmt = conn.createStatement();
			String sql = "DROP TABLE 'neuron'";
			stmt.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void create(Connection conn) throws SQLException {

		try {
			Statement stmt = conn.createStatement();
			String sql = "CREATE TABLE 'neuron' "
					+ " (neuronID integer PRIMARY KEY autoincrement, "
					+ " word varchar(100)," 
					+ " language varchar(50),"
					+ " emphasis double," 
					+ " hitCount double," 
					+ " emphasisFactor double);";
			stmt.execute(sql);
		} finally {
			if (conn != null)
				conn.close();
		}

	}

	/**
	 * Führt createTablesInTierverwaltungDatabase aus. Siehe Javadoc von
	 * createTablesInTierverwaltungDatabase.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		CreateTables db = new CreateTables();
		try {
			db.createTable();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("fehlerli");
		}
	}

}