package ch.hszt.LanguageDetector;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

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
        	System.out.println("learn...");
			ld.learn(tfp.parseFile(new File("learningMaterial/darwin_deutsch.txt")), "Deutsch");
			ld.learn(tfp.parseFile(new File("learningMaterial/robinson_english.txt")), "Englisch");
			System.out.println("detect...");
			ld.detectLanguage(tfp.parseFile(new File("learningMaterial/english_text.txt")));
			ld.detectLanguage(tfp.parseFile(new File("learningMaterial/english_text.txt")));

			ld.detectLanguage(tfp.parseFile(new File("learningMaterial/deutsch_text.txt")));

        } catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
