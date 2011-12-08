package ch.hszt.LanguageDetector;

public class Language implements Comparable<Language>{
	private int id;
	private String language;

	public Language(String language) {
		super();
		this.language = language;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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