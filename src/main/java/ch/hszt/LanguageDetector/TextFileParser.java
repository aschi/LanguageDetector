package ch.hszt.LanguageDetector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class TextFileParser {

	public List<Word> parseFile(File f) throws IOException,
			FileNotFoundException {
		BufferedReader input = new BufferedReader(new FileReader(f));
		StringBuffer contents = new StringBuffer();

		try {
			String line = null; // not declared within while loop
			while ((line = input.readLine()) != null) {
				contents.append(line);
				contents.append(" ");
			}
		} finally {
			input.close();
		}
		
		//Remove punctuations / generate word list
		return Word.generateWordListFromArray(contents.toString().toLowerCase().replaceAll("\\p{Punct}", " ").replaceAll("  ", " ").split(" "));
	}

}
