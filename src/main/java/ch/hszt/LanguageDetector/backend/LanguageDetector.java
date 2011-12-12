package ch.hszt.LanguageDetector.backend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;



public class LanguageDetector {
	
	private NeuronalNetwork<Word, Language> neuronalNetwork;
	private Set<Word> wordList;
	
	public LanguageDetector() {
		neuronalNetwork = new NeuronalNetwork<Word, Language>();
		wordList = new TreeSet<Word>();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Learn a language. 
	 * @param input List of words that should be trained
	 * @param language Language associated with the given words
	 * @param emphasis Factor of how sure we are of the language (between 0 and 1)
	 */
	public void learn(List<Word> input, Language language, double emphasis){
		for(Word in : input){
			neuronalNetwork.addNeuron(in, language, emphasis);
		}
	}
	
	/**
	 * Learn a language. Used for initial training (emphasis 1)
	 * @param input List of Words that should be trained
	 * @param language Language associated with the given words
	 */
	public void learn(List<Word> input, Language language){
		for(Word in : input){
			neuronalNetwork.addNeuron(in, language);
		}
	}
	
	/**
	 * Detects the language using the neuronalNetwork
	 * @param input words of input text. Use TextFileParser or Word.generateWordListFromArray to get a proper List
	 * @return a map language -> ratio of words in this language
	 */
	public Map<Language, Double> detectLanguage(List<Word> input){
		Map<Language, Double> languageWordCounter = new HashMap<Language, Double>();
		Map<Language, Double> output = new HashMap<Language, Double>();
		int found = 0;
		
		System.out.println(input.size() + " words to detect..");
		
		for(Word w : input){
			Set<Neuron<Word, Language>> subset = neuronalNetwork.getNeuronsFromSource(w);
			if(!subset.isEmpty()){
				found++;
				for(Neuron<Word, Language> n : subset){
					Double count; //count contains emphasis of neurons
					if(languageWordCounter.containsKey(n.getTarget())){
						count = languageWordCounter.get(n.getTarget());
						count = new Double(count.doubleValue()+n.getEmphasis());
						languageWordCounter.remove(n.getTarget());
					}else{
						count = new Double(n.getEmphasis());
					}
					languageWordCounter.put(n.getTarget(), count);
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
			System.out.println(k + ": " + ratio);
			
			//Learn language considering emphasis
			learn(input, k, ratio);
			
			neuronalNetwork.printNetwork();
		}
		
		return output;
	}
	
	//TODO: Removal
	private void removeIrrelevantWords(){
		Word last = null;
		
		for(Word w : wordList){
			if(w.isSimilar(last)){
				
			}
			last = w;
		}
	}

	public NeuronalNetwork<Word, Language> getNeuronalNetwork() {
		return neuronalNetwork;
	}
	
	/*
	private Word searchWord(Word comp){
		for(Word w : wordList){
			if(w.equals(comp)){
				return w;
			}
		}
		return null;
	}*/
}
