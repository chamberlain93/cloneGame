package fileIO;

import java.io.File;

import javax.swing.JFileChooser;

public class XMLFileChooser extends JFileChooser {

	/**
	 * Creates a XMLFileChooser instance.
	 */
	public XMLFileChooser() {
		File defaultLoc = new File("xmls");
		this.setCurrentDirectory(defaultLoc);
		this.setFileFilter(new XMLFileFilter());
		this.setFileSelectionMode(JFileChooser.FILES_ONLY);
		this.addChoosableFileFilter(new XMLFileFilter());
		this.setAcceptAllFileFilterUsed(false);
	}
}
