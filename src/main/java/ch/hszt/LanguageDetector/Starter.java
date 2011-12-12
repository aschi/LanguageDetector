package ch.hszt.LanguageDetector;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import ch.hszt.LanguageDetector.backend.Language;
import ch.hszt.LanguageDetector.backend.LanguageDetector;
import ch.hszt.LanguageDetector.gui.MainGui;
import ch.hszt.LanguageDetector.input.TextFileParser;

/**
 * Hello world!
 *
 */
public class Starter 
{
    public static void main( String[] args )
    {
    	
        TextFileParser tfp = new TextFileParser();
    	LanguageDetector ld = new LanguageDetector();
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
    	new MainGui(ld);
    }
}
