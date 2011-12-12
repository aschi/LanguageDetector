package ch.hszt.LanguageDetector.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import ch.hszt.LanguageDetector.backend.Language;
import ch.hszt.LanguageDetector.backend.LanguageDetector;
import ch.hszt.LanguageDetector.backend.Word;

public class InputForm extends JPanel{
	JTextArea area;
	LanguageDetector ld;
	MainGui gui;
	
	public InputForm(LanguageDetector ld, MainGui gui){
		this.ld = ld;
		this.gui = gui;
		createForm();
	}
	
	private void createForm(){
		area = new JTextArea("");
		//area.setSize(500, 50);
		area.setPreferredSize(new Dimension(800, 200));
		this.add(new JScrollPane(area));
		
		JButton parseButton = new JButton("Parse");
		this.add(parseButton);	
		parseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Map<Language, Double> result = ld.detectLanguage(Word.generateWordListFromArray(area.getText().toLowerCase().replaceAll("\\p{Punct}", " ").replaceAll("  ", " ").split(" ")));
				
				StringBuffer sb = new StringBuffer();
				double biggest = 0;
				Language significantLanguage = new Language("");
				
				sb.append("Language analysis of your given text:\n");
				for(Language l : result.keySet()){
					sb.append(l);
					sb.append(": ");
					sb.append(result.get(l));
					sb.append("\n");
					
					if(result.get(l).doubleValue() > biggest){
						biggest = result.get(l).doubleValue();
						significantLanguage = l;
					}
				}
				sb.append("This text is probably in: ");
				sb.append(significantLanguage.toString());
				
				
				JOptionPane.showMessageDialog(gui.getFrame(), sb.toString());
				
				
				gui.updateGui();
			}
		});
	}
	
}
