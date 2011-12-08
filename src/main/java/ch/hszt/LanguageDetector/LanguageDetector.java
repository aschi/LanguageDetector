package ch.hszt.LanguageDetector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;


public class LanguageDetector {
	private Set<Word> neuronalNetwork;
	
	public LanguageDetector() {
		neuronalNetwork = new TreeSet<Word>();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Train our neuronal network.
	 * @param input List of Words that should be trained
	 * @param language Language associated with the given words
	 */
	public void learn(List<Word> input, Language language){
		for(Word in : input){
			neuronalNetwork.add(in);
			in.addLanguage(language);
		}
	}
	
	/**
	 * Detects the language using the neuronalNetwork
	 * @param input words of input text. Use TextFileParser or Word.generateWordListFromArray to get a proper List
	 * @return a map language -> ratio of words in this language
	 */
	public Map<Language, Double> detectLanguage(List<Word> input){
		Map<Language, Integer> languageWordCounter = new HashMap<Language, Integer>();
		Map<Language, Double> output = new HashMap<Language, Double>();
		int found = 0;
		
		System.out.println(input.size() + " words to detect..");
		
		for(Word w : input){
			//search for word
			if(neuronalNetwork.contains(w)){
				found++;
				//read languages of word
				for(Language l : searchWord(w).getLanguages()){
					Integer count = null;
					if(languageWordCounter.containsKey(l)){
						count = languageWordCounter.get(l);
						count = new Integer(count.intValue()+1);
						languageWordCounter.remove(l);
					}else{
						//add counter
						count = new Integer(1);
					}					
					languageWordCounter.put(l, count);
				}
			}
		}
		
		double foundRatio = (double)((double)found/(double)input.size());
		System.out.println("detected "+ found + " of " + input.size() + " words (" + foundRatio+ ")");

		
		//Generate output
		for(Language k : languageWordCounter.keySet()){
			double ratio = (double)((double)languageWordCounter.get(k)/(double)found);
			output.put(k, new Double(ratio));
			
						
			//Print output
			System.out.println(k + ": " + ratio +"(" +languageWordCounter.get(k) +" words)");
			
			//Use this text to train the language if most of this text is detectable
			if(ratio > 0.6 && foundRatio > 0.7){
				learn(input, k);
			}
		}
		
		return output;
	}
	
	private Word searchWord(Word comp){
		for(Word w : neuronalNetwork){
			if(w.equals(comp)){
				return w;
			}
		}
		return null;
	}
}
