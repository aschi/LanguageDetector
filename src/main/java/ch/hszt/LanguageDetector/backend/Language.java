package ch.hszt.LanguageDetector.backend;

public class Language implements Comparable<Language>{
	private String language;

	public Language(String language) {
		super();
		this.language = language;
	}

	public String getLanguage() {
		return language;
	}

	public boolean equals(Object o) {
		if (o instanceof Language) {
			return getLanguage().equals(((Language) o).getLanguage());
		} else {
			return false;
		}
	}
	
	public int hashCode(){
		return getLanguage().hashCode();
	}
	
	public String toString() {
		return language;
	}

	/**
	 * Compare using the String
	 */
	@Override
	public int compareTo(Language o) {
		return getLanguage().compareTo(o.getLanguage());
	}
}
