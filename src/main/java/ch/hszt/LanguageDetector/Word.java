package ch.hszt.LanguageDetector;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Word implements Comparable<Word>{
	private int id;
	private Set<Language> languages;
	private String text;
	
	public Word(String text){
		languages = new TreeSet<Language>();
		this.text = text;
	}
	
	public String getText(){
		return text;
	}
	
	public void addLanguage(Language language){
		languages.add(language);
	}
	
	public Set<Language> getLanguages(){
		return languages;
	}

	public boolean equals(Object o){
		if(o instanceof Word){
			return getText().equals(((Word)o).getText());
		}else{
			return false;
		}
	}
	
	/**
	 * Generate Word List from String Array (each entry is a word). Words will not have any languages assigned.
	 * @param input String array
	 * @return List containing the words
	 */
	public static List<Word> generateWordListFromArray(String[] input){
		List<Word> output = new ArrayList<Word>();
		for(String w : input){
			if(!w.trim().equals("")){
				output.add(new Word(w));	
			}
		}
		return output;
	}

	/**
	 * Compare using the String
	 */
	@Override
	public int compareTo(Word o) {
		return getText().compareTo(o.getText());
	}
}
