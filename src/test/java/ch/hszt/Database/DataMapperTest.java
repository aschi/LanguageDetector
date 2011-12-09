package ch.hszt.Database;

import java.sql.SQLException;
import java.util.Set;
import java.util.TreeSet;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import Database.DataMapper;
import ch.hszt.LanguageDetector.Language;
import ch.hszt.LanguageDetector.Word;

public class DataMapperTest {

	DataMapper dm;
	Word d1;
	Word d2;
	Word d3;
	Word e1;
	Word e2;
	Word e3;
	Word ed;
	Language de;
	Language en;

	int wordid;
	int languageid;
	
	Set<Word> words;
	Set<Language> languages;

	@Before
	public void init() {
		de = new Language("Deutsch");
		d1 = new Word("Hallo");
		d1.addLanguage(de);
		d2 = new Word("Wie");
		d2.addLanguage(de);
		d3 = new Word("geht");
		d3.addLanguage(de);

		en = new Language("Englisch");
		e1 = new Word("Hello");
		e1.addLanguage(en);
		e2 = new Word("How");
		e2.addLanguage(en);
		e3 = new Word("are");
		e3.addLanguage(en);

		ed = new Word("Kindergarten");
		ed.addLanguage(en);
		ed.addLanguage(de);

		words = new TreeSet<Word>();
		words.add(d1);
		words.add(d2);
		words.add(d3);
		words.add(e1);
		words.add(e2);
		words.add(e3);
		words.add(ed);
		
		languages = new TreeSet<Language>();
		languages.add(de);
		languages.add(en);

		wordid = -1;
		languageid = -1;

		try {
			dm = new DataMapper();
		} catch (ClassNotFoundException e) {
			System.out.println("Class Not Found Exception");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("SQL Exception");
			e.printStackTrace();
		}

	}

	@Test
	public void insertWordTest() {

		SQLException sql = null;

		try {
			wordid = dm.insertWord(d1);
		} catch (SQLException e) {
			sql = e;
			e.printStackTrace();
		}

		TestCase.assertTrue(wordid >= 0);

	}

	@Test
	public void insertLanguageTest() {

		try {
			languageid = dm.insertLanguage(de.getLanguage());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		TestCase.assertTrue(languageid >= 0);

	}

	@Test
	public void insertWordLanguageTest() {

		String message = "";

		// Da aufgrund der Tests InsertWord und InsertLanguage
		// bereits je eine Sprache in der Datenbank haben sollte, werden diese
		// nun
		// ermittelt. Falls dies ein Fehler gibt, wird dies in der
		// TestcaseAssert ausgegeben
		if (languageid < 0 || wordid < 0) {
			message = "Insert Word oder Insert Language waren fehlerhaft";
		} else {

			try {
				dm.insertWordLanguage(wordid, languageid);
			} catch (SQLException e) {
				message = e.getMessage();
			}
		}

		TestCase.assertTrue(message, message == "");

	}
	
	@Test
	public void deleteTest() {
		SQLException sql = null;
		
		try {
			dm.delete();
		} catch (SQLException e) {
			sql = e;
			e.printStackTrace();
		}
		
		TestCase.assertNull(sql);
		
	}
	
	@Test
	public void TestSaveAllWords() {
		boolean testOK = true;
		try {
			dm.saveAllWords(words, languages);
			
			Set<Word> dbwords = dm.getWordSet();
			for (Word w: dbwords) {
				System.out.println("Wort: " +  w.getText());
				System.out.println("Sprachen:");
				for (Language l: w.getLanguages()) {
					System.out.println("- " + l.getLanguage());
				}
				System.out.println("Hitcount: " + w.getHitCount());
			}
			
		} catch (SQLException e) {
			testOK = false;
			e.printStackTrace();
		}
		
		TestCase.assertTrue(testOK);
	}

}
