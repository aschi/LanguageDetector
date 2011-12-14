package ch.hszt.LanguageDetector.gui;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import ch.hszt.LanguageDetector.backend.Language;
import ch.hszt.LanguageDetector.backend.NeuronalNetwork;
import ch.hszt.LanguageDetector.backend.Word;

public class LanguageOverview extends JPanel{
	private JTree tree;
	private MainGui gui;
	private DefaultMutableTreeNode top;
	private NeuronalNetwork<Word, Language> neuronalNetwork;
	
	
	public LanguageOverview(NeuronalNetwork<Word, Language> neuronalNetwork, MainGui gui) {
		this.neuronalNetwork = neuronalNetwork;
		this.gui = gui;
		
		top = new DefaultMutableTreeNode("Languages");
		tree = new JTree(top);
		
		//add to panel
		add(tree);
		setPreferredSize(new Dimension(130, 300));
		
		updateLanguageOverview();
		
		tree.scrollPathToVisible(new TreePath(top.getLastLeaf().getPath()));
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
}
