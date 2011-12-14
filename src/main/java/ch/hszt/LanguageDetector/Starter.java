package ch.hszt.LanguageDetector;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import ch.hszt.LanguageDetector.backend.Language;
import ch.hszt.LanguageDetector.backend.LanguageDetector;
import ch.hszt.LanguageDetector.backend.NeuronalNetwork;
import ch.hszt.LanguageDetector.backend.Word;
import ch.hszt.LanguageDetector.database.DataMapper;
import ch.hszt.LanguageDetector.gui.MainGui;
import ch.hszt.LanguageDetector.input.TextFileParser;

/**
 * Hello world!
 *
 */
public class Starter 
{
	
	private static DataMapper dm = null;
	private static NeuronalNetwork<Word, Language> neuronalNetwork = null;
	private static LanguageDetector ld = null;
	
    public static void main( String[] args )
    {
    	try {
			dm = new DataMapper();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        TextFileParser tfp = new TextFileParser();
		try {
			neuronalNetwork = dm.getNeuronalNetwork();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		if (neuronalNetwork == null || neuronalNetwork.getNeuronSet().size() == 0) {
			System.out.println("No words in Database found, learn some Texts...");
			ld = new LanguageDetector();
	    	
	        try {
	        	ld.learn(tfp.parseFile(new File("learningMaterial/corriere_italienisch.txt")), new Language("Italienisch"));
				ld.learn(tfp.parseFile(new File("learningMaterial/elMundo_spanisch.txt")), new Language("Spanisch"));
				ld.learn(tfp.parseFile(new File("learningMaterial/independent_englisch.txt")), new Language("Englisch"));
				ld.learn(tfp.parseFile(new File("learningMaterial/leMonde_französisch.txt")), new Language("Französisch"));
				ld.learn(tfp.parseFile(new File("learningMaterial/nzzArtikel_deutsch.txt")), new Language("Deutsch"));
	        } catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out.println("Loading words from Database");
			ld = new LanguageDetector(neuronalNetwork);
		}
    	new MainGui(ld);
    	
    	/*
    	Runtime.getRuntime().addShutdownHook(new Thread() {
    	    public void run() {
    	    	try {
					dm.saveNeuronalNetwork(ld.getNeuronalNetwork());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    	        System.out.println("LanguageDetector is shutting down!");
    	    }
    	});
    	*/
    }
}
