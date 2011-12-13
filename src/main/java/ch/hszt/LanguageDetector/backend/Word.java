package ch.hszt.LanguageDetector.backend;

import java.util.ArrayList;
import java.util.List;

public class Word implements Comparable<Word>{
	private String text;
	
	
	public Word(String text){
		this.text = text;
	}

	public String getText(){
		return text;
	}

	public boolean equals(Object o){
		if(o instanceof Word){
			return getText().equals(((Word)o).getText());
		}else{
			return false;
		}
	}
	
	public boolean isSimilar(Word w){
		if(w == null){
			return false;
		}
		
		int charErrCount = 0;
		
		char[] thisCharArray = getText().toCharArray();
		char[] compCharArray = w.getText().toCharArray();
		
		//Check length
		//Could be "similar" if the length difference is less then "1+20% of word length" (10 chars => 1+10*0.2 = 3 chars) 
		if(Math.abs((thisCharArray.length-compCharArray.length)) > (1+(thisCharArray.length/5))){
			return false;
		}
		
		//Switch arrays to have "thisCharArray <= compCharArray"
		if(thisCharArray.length > compCharArray.length){
			 char[] tmpArr = thisCharArray;
			 compCharArray = thisCharArray;
			 thisCharArray = tmpArr;
		}
		
		//Loop through arrays and count diverse characters
		for(int i = 0;i < thisCharArray.length;i++){
			if(thisCharArray[i] != compCharArray[i]){
				charErrCount++;
			}
		}
		
		if(charErrCount < (1+(getText().length()/5))){
			return true;
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
	
	public int hashCode(){
		return getText().hashCode();
	}
	
	public String toString(){
		return text;
	}
	
}
