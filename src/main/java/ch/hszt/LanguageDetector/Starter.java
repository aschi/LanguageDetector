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
    	/*
        TextFileParser tfp = new TextFileParser();
    	LanguageDetector ld = new LanguageDetector();
        try {
        	System.out.println("learn...");
			//ld.learn(tfp.parseFile(new File("learningMaterial/darwin_deutsch.txt")), new Language("Deutsch"));
			//ld.learn(tfp.parseFile(new File("learningMaterial/robinson_english.txt")), new Language("Englisch"));
			
        	ld.learn(tfp.parseFile(new File("learningMaterial/english_learning.txt")), new Language("Deutsch"));
			ld.learn(tfp.parseFile(new File("learningMaterial/deutsch_learning.txt")), new Language("Englisch"));

        	
        	System.out.println("detect...");
			ld.detectLanguage(tfp.parseFile(new File("learningMaterial/english_text.txt")));
			ld.detectLanguage(tfp.parseFile(new File("learningMaterial/english_text.txt")));

			//ld.detectLanguage(tfp.parseFile(new File("learningMaterial/deutsch_text.txt")));

        } catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
    	new MainGui();
    }
}
