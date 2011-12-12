package ch.hszt.LanguageDetector.backend;

import java.util.Set;

public class Neuron <S, T>{
	private S source;
	private T target;
	private double emphasis;
	
	//Hit count includes emphasis of hits
	private double hitCount;
	
	//used to recalculate emphasis
	private double emphasisFactor;
	
	public Neuron(S source, T target, double emphasis) {
		super();
		this.source = source;
		this.target = target;
		this.emphasis = emphasis;
	}
	
	public Neuron(S source, T target) {
		super();
		this.source = source;
		this.target = target;
	}
	
	public S getSource() {
		return source;
	}

	public void setSource(S source) {
		this.source = source;
	}

	public T getTarget() {
		return target;
	}

	public void setTarget(T target) {
		this.target = target;
	}
	
	public double getHitCount(){
		return hitCount;
	}
	
	public void addToNetwork(double emphasis){
		//recalculate hitCount + emphasisFactor
		hitCount+=emphasis;
		emphasisFactor = ((emphasisFactor*(hitCount-emphasis)+emphasis)/hitCount);
	}
	
	public void recalculateEmphasis(Set<Neuron<S, T>>subset){
		double hitSum = 0;
		
		//Sum up hit counts
		for(Neuron<S, T> n : subset){
			hitSum  += n.getHitCount();
		}
		
		emphasis = (hitCount / hitSum) * emphasisFactor;
	}
	
	public double getEmphasis() {
		return emphasis;
	}
	public void setEmphasis(double emphasis) {
		this.emphasis = emphasis;
	}
	
	public boolean equals(Object o){
		if(o instanceof Neuron){
			//Compare word + language 
			return getSource().equals(((Neuron<S, T>) o).getSource()) && getTarget().equals(((Neuron<S, T>) o).getTarget());
		}else{
			return false;
		}
	}
	
	public int hashCode(){
		int hash = 7;
        hash = hash * 17 + target.hashCode();
        hash = hash * 17 + source.hashCode();
        return hash;
	}
	
	public String toString(){
		return source + "->" + target;
	}
	
}
