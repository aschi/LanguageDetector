package ch.hszt.LanguageDetector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LanguageDetector {
	private List<Word> neuronalNetwork;
	
	public LanguageDetector() {
		neuronalNetwork = new ArrayList<Word>();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Train our neuronal network.
	 * @param input List of Words that should be trained
	 * @param language Language associated with the given words
	 */
	public void learn(List<Word> input, String language){
		for(Word in : input){
			int i = neuronalNetwork.indexOf(in);
			if(i == -1){
				//Add new word to our network
				in.addLanguage(language);
				neuronalNetwork.add(in);
			}else{
				//Check if language is already registered. if not add new connection to the language
				if(neuronalNetwork.get(i).getLanguages().indexOf(language) == -1){
					neuronalNetwork.get(i).addLanguage(language);
				}
			}
		}
	}
	
	/**
	 * Detects the language using the neuronalNetwork
	 * @param input words of input text. Use TextFileParser or Word.generateWordListFromArray to get a proper List
	 * @return a map language -> ratio of words in this language
	 */
	public Map<String, Double> detectLanguage(List<Word> input){
		Map<String, Integer> languageWordCounter = new HashMap<String, Integer>();
		Map<String, Double> output = new HashMap<String, Double>();
		int found = 0;
		
		System.out.println(input.size() + " words to detect..");
		
		for(Word w : input){
			//search for word
			int i = neuronalNetwork.indexOf(w);
			if(i != -1){
				found++;
				//read languages of word
				for(String l : neuronalNetwork.get(i).getLanguages()){
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
		for(String k : languageWordCounter.keySet()){
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
}
