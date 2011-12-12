package ch.hszt.LanguageDetector.gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTable;

import ch.hszt.LanguageDetector.backend.Language;
import ch.hszt.LanguageDetector.backend.Neuron;
import ch.hszt.LanguageDetector.backend.NeuronalNetwork;
import ch.hszt.LanguageDetector.backend.Word;

public class WordOverview extends JPanel{
	ReadOnlyTableModel model;
	NeuronalNetwork<Word, Language> neuronalNetwork;
	
	public WordOverview(NeuronalNetwork<Word, Language> neuronalNetwork){
		this.neuronalNetwork = neuronalNetwork;
		
		String columnTitles[] = {"#", "word", "emphasis"};
		model = new ReadOnlyTableModel(columnTitles, 0);
		
		
		//Add table to component
		JTable table = new JTable(model);
		
		table.getColumnModel().getColumn(0).setPreferredWidth(10);
		table.getColumnModel().getColumn(1).setPreferredWidth(100);
		table.getColumnModel().getColumn(2).setPreferredWidth(30);
		
		this.setLayout(new BorderLayout());
		this.add(table, BorderLayout.CENTER);
	}
	
	/**
	 * updateWordOverview
	 * @param l
	 */
	public void updateWordOverview(Language l){
		while(model.getRowCount() > 0){
			model.removeRow(0);
		}
		
		int i = 0;
		
		String[] row = new String[3];
		for(Neuron<Word, Language> n : neuronalNetwork.getNeuronsFromTarget(l)){
			i++;
			row[0] = Integer.toString(i);
			row[1] = n.getSource().getText();
			row[2] = Double.toString(n.getEmphasis());
			
			model.addRow(row);
		}
	}
	
	
}
