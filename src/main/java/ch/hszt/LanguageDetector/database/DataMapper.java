package ch.hszt.LanguageDetector.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;
import java.util.TreeSet;

import ch.hszt.LanguageDetector.backend.Language;
import ch.hszt.LanguageDetector.backend.Neuron;
import ch.hszt.LanguageDetector.backend.NeuronalNetwork;
import ch.hszt.LanguageDetector.backend.Word;

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
	 * @return neuronID
	 * @throws SQLException
	 */
	public int insertNeuron(Neuron<Word, Language> neuron) throws SQLException {
		String sql;
		ResultSet rs = null;
		sql = "INSERT INTO 'neuron' VALUES (null, \'" + 
				neuron.getSource().getText() + "\', " + "\'" + 
				neuron.getTarget().getLanguage() + "\', " +
				neuron.getEmphasis() + ", " +
				neuron.getHitCount() + ", " +
				neuron.getEmphasisFactor() + ");";
		System.out.println(sql);
		Statement stmt = conn.createStatement();
		stmt.executeUpdate(sql);

		PreparedStatement pstmt = conn
				.prepareStatement("select max(neuronID) max from 'neuron';");
		rs = pstmt.executeQuery();
		if (rs.next()) {
			;
			return rs.getInt("max");
		} else {
			return 0;
		}
	}

	/**
	 * Deletes all entities in table  neuron
	 * 
	 * @throws SQLException
	 */
	public void delete() throws SQLException {
		String sql;
		sql = "DELETE * FROM 'neuron';";
		System.out.println(sql);
		Statement stmt = conn.createStatement();
		stmt.executeUpdate(sql);
	}

	/**
	 * Returns all neurons as a neuralNetwork
	 * 
	 * @return neuronalNetwork
	 * @throws SQLException
	 */
	public NeuronalNetwork<Word, Language> getNeuronalNetwork() throws SQLException {
		
		NeuronalNetwork<Word, Language> neuronalNetwork = null;

		Set<Neuron<Word, Language>> neurons = new TreeSet<Neuron<Word, Language>>();

		String sql;
		sql = "SELECT * FROM 'neuron';";
		System.out.println(sql);

		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);

		while (rs.next()) {
			Neuron<Word, Language> neuron = new Neuron<Word, Language>(new Word(rs.getString("word")), new Language(rs.getString("Language")), rs.getDouble("emphasis"),
					rs.getDouble("hitCount"), rs.getDouble("emphasisFactor"));
			neurons.add(neuron);

		}
		neuronalNetwork = new NeuronalNetwork<Word, Language>(neurons);
		return neuronalNetwork;
	}

	
	/**
	 * Saves all neurons
	 * @param neuronalNetwork neuronalNetwork with all neurons to be saved
	 * @throws SQLException 
	 */
	public void saveNeuronalNetwork(NeuronalNetwork<Word, Language> neuronalNetwork) throws SQLException {
		
		//delete old neuronalNetwork from Table neurons
		delete();
		
		for (Neuron<Word, Language> neuron : neuronalNetwork.getNeuronSet()) {
			insertNeuron(neuron);
		}
	}

}
