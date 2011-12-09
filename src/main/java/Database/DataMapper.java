package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import ch.hszt.LanguageDetector.Language;
import ch.hszt.LanguageDetector.Word;

public final class DataMapper {

	private Connection conn;

	public DataMapper() throws ClassNotFoundException, SQLException {

		Class.forName("org.sqlite.JDBC");

		conn = DriverManager.getConnection("jdbc:sqlite:language.db");
	}

	/**
	 * Inserts a new word into table word
	 * 
	 * @param word
	 *            Word to insert
	 * @return int wordID
	 * @throws SQLException
	 */
	public int insertWord(Word word) throws SQLException {

		String sql;
		ResultSet rs = null;
		sql = "INSERT INTO 'word' VALUES (null, \'" + word.getText() + "\', " + word.getHitCount() + ");";
		System.out.println(sql);
		Statement stmt = conn.createStatement();
		stmt.executeUpdate(sql);

		PreparedStatement pstmt = conn
				.prepareStatement("select max(wordID) max from 'word';");
		rs = pstmt.executeQuery();
		if (rs.next()) {
			;
			return rs.getInt("max");
		} else {
			return 0;
		}
	}

	/**
	 * Inserts a new language into table language
	 * 
	 * @param language
	 *            Language to insert
	 * @return int languageID
	 * @throws SQLException
	 */
	public int insertLanguage(String language) throws SQLException {

		String sql;
		ResultSet rs = null;
		sql = "INSERT INTO 'language' VALUES (null, \'" + language + "\');";
		System.out.println(sql);
		Statement stmt = conn.createStatement();
		stmt.executeUpdate(sql);

		PreparedStatement pstmt = conn
				.prepareStatement("select max(languageID) max from 'language';");
		rs = pstmt.executeQuery();
		if (rs.next()) {
			;
			return rs.getInt("max");
		} else {
			return 0;
		}
	}

	/**
	 * Inserts a new word-language-relationshop into table wordLanguage
	 * 
	 * @param wordID
	 *            the ID of the word to connect to a language
	 * @param languageID
	 *            the ID of the language to connect to a word
	 * @throws SQLException
	 */
	public void insertWordLanguage(int wordID, int languageID)
			throws SQLException {

		String sql;
		sql = "INSERT INTO 'wordLanguage' VALUES (null, " + wordID + ", "
				+ languageID + ");";
		System.out.println(sql);
		Statement stmt = conn.createStatement();
		stmt.executeUpdate(sql);
	}

	/**
	 * Deletes all entities in tables word, language and wordLanguage
	 * 
	 * @throws SQLException
	 */
	public void delete() throws SQLException {
		String sql;
		sql = "DELETE * FROM 'word';";
		System.out.println(sql);
		Statement stmt = conn.createStatement();
		stmt.executeUpdate(sql);

		sql = "DELETE * FROM 'language';";
		System.out.println(sql);
		stmt = conn.createStatement();
		stmt.executeUpdate(sql);

		sql = "DELETE * FROM 'wordLanguage';";
		System.out.println(sql);
		stmt = conn.createStatement();
		stmt.executeUpdate(sql);
	}

	/**
	 * Returns all Words with all languages in a Set
	 * 
	 * @return a Set with all words
	 * @throws SQLException
	 */
	public Set<Word> getWordSet() throws SQLException {

		Map<Integer, String> languages = getLanguages();

		String sql;
		sql = "SELECT * FROM 'word';";
		System.out.println(sql);

		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		Set<Word> wordSet = new TreeSet<Word>();

		while (rs.next()) {

			Word word = new Word(rs.getInt("WordID"), rs.getString("word"), rs.getInt("hitCount"));
			addLanguagesToWord(languages, word);

			wordSet.add(word);
		}
		return wordSet;
	}

	private void addLanguagesToWord(Map<Integer, String> map, Word word) {
		String sql;
		sql = "SELECT * FROM 'wordLanguage' WHERE fkWordID = \'" + word.getId() + "\';";
		System.out.println(sql);

		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Language l = new Language(rs.getInt("fkLanguageID"), map.get(rs.getInt("fkLanguageID")));
				word.addLanguage(l);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Map<Integer, String> getLanguages() {
		Map<Integer, String> returnMap = new HashMap<Integer, String>();

		String sql;
		sql = "SELECT * FROM 'language';";
		System.out.println(sql);
		ResultSet rs;
		Statement stmt;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				returnMap
						.put(rs.getInt("languageID"), rs.getString("language"));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return returnMap;

	}
	
	/**
	 * Saves all words, languages and relationships between them in the tables
	 * @param words all Words
	 * @throws SQLException 
	 */
	public void saveAllWords(Set<Word> words, Set<Language> languages) throws SQLException {
		
		for (Language l: languages) {
			insertLanguage(l.getLanguage());
		}
		
		Map<String, Integer> langs = getReverseLanguages();
		
		for (Word word : words) {
			insertWord(word);
			for (Language l: word.getLanguages()) {
				insertWordLanguage(word.getId(), langs.get(l.getLanguage()));
			}
		}
	}
	
	private Map<String, Integer> getReverseLanguages() {
		Map<String, Integer> returnMap = new HashMap<String, Integer>();

		String sql;
		sql = "SELECT * FROM 'language';";
		System.out.println(sql);
		ResultSet rs;
		Statement stmt;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				returnMap
						.put(rs.getString("language"), rs.getInt("languageID"));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return returnMap;

	}

}
