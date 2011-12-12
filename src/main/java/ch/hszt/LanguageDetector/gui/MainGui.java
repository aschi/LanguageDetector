package ch.hszt.LanguageDetector.gui;

import java.awt.BorderLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import ch.hszt.LanguageDetector.backend.Language;
import ch.hszt.LanguageDetector.backend.LanguageDetector;
import ch.hszt.LanguageDetector.backend.Word;
import ch.hszt.LanguageDetector.input.TextFileParser;

public class MainGui {
	JFrame frame;

	WordOverview wo;
	LanguageOverview lo;

	LanguageDetector ld;
	Language currentLanguage;

	public MainGui() {
		ld = new LanguageDetector();
		
		createFrame();
		
		TextFileParser tfp = new TextFileParser();
		try {
			ld.learn(tfp.parseFile(new File(
					"learningMaterial/deutsch_learning.txt")), new Language(
					"Deutsch"));
			ld.learn(tfp.parseFile(new File(
					"learningMaterial/english_learning.txt")), new Language(
					"Englisch"));
					
			
			ld.detectLanguage(tfp.parseFile(new File(
					"learningMaterial/english_text.txt")));
			for(int n = 0;n < 10;n++){
				ld.detectLanguage(tfp.parseFile(new File(
						"learningMaterial/english_text.txt")));
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ld.getNeuronalNetwork().printNetwork();
		updateGui();
	}

	private void createFrame() {
		frame = new JFrame("Language Detector");

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Fullscreen

		// Set Navigation
		lo = new LanguageOverview(ld.getNeuronalNetwork(), this);
		frame.getContentPane().add(new JScrollPane(lo.getTree()),
				BorderLayout.WEST);

		// Set word overview
		wo = new WordOverview(ld.getNeuronalNetwork());
		frame.getContentPane().add(new JScrollPane(wo), BorderLayout.CENTER);
		
		//Set input form
		frame.getContentPane().add(new InputForm(ld, this), BorderLayout.SOUTH);
		
		frame.setVisible(true);
	}

	public void updateLanguageSelection(String lan) {
		Language l = new Language(lan);
		currentLanguage = l;
		updateGui();
	}

	public void updateGui() {
		lo.updateLanguageOverview();
		wo.updateWordOverview(currentLanguage);
	}
	
	public JFrame getFrame(){
		return frame;
	}
}
