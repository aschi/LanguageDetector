package ch.hszt.LanguageDetector.gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import ch.hszt.LanguageDetector.backend.Language;
import ch.hszt.LanguageDetector.backend.NeuronalNetwork;
import ch.hszt.LanguageDetector.backend.Word;

public class LanguageOverview {
	private JTree tree;
	private MainGui gui;
	private DefaultMutableTreeNode top;
	private NeuronalNetwork<Word, Language> neuronalNetwork;
	
	
	public LanguageOverview(NeuronalNetwork<Word, Language> neuronalNetwork, MainGui gui) {
		this.neuronalNetwork = neuronalNetwork;
		this.gui = gui;
		
		top = new DefaultMutableTreeNode("Languages");
		tree = new JTree(top);
		
		updateLanguageOverview();
	}
	
	public void updateLanguageOverview(){
		//remove languages
		while(top.getChildCount() > 0){
			top.remove(0);
		}
		
		// add languages
		for (Language l : neuronalNetwork.getTargetList()) {
			DefaultMutableTreeNode ln = new DefaultMutableTreeNode(l.getLanguage());
			top.add(ln);
		}
		
		addListener();
	}
	
	private void addListener() {
		tree.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					// Get location
					TreePath p = tree.getPathForLocation(e.getX(), e.getY());
					TreeNode node = (TreeNode) p.getLastPathComponent();

					if(!node.toString().equals("Languages")){
						gui.updateLanguageSelection(node.toString());
					}
				}
			}
		});
	}
	
	
	/**
	 * Returns the generated JTree
	 * 
	 * @return the generated JTree
	 */
	public JTree getTree() {
		return tree;
	}
}
