package de.unistuttgart.ims.mwe.essentials;

import java.io.File;
import java.util.ArrayList;

/**
 * @author tarekmehrez
 *
 */
public class Corpus {

	private ArrayList<File> textfiles;
	private ArrayList<File> annfiles;

	private final File textroot;
	private final File annroot;

	public Corpus(String root) {
		this.textroot = new File(root + "/text");
		this.annroot = new File(root + "/annotation");

		textfiles = new ArrayList<File>();
		annfiles = new ArrayList<File>();

		listFilesForFolder();
		if (!validateFolders()) {
			System.err.println("folders not validated");
			System.exit(0);
		}
	}

	private boolean validateFolders() {

		if (textfiles.size() != annfiles.size())
			return false;

		for (int i = 0; i < textfiles.size(); i++) {

			if (!textfiles.get(i).getName().split("\\.")[0].equals(annfiles
					.get(i).getName().split("\\.")[0]))
				return false;
		}
		return true;

	}

	private void listFilesForFolder() {
		for (final File fileEntry : this.textroot.listFiles())
			textfiles.add(fileEntry);

		for (final File fileEntry : this.annroot.listFiles())
			annfiles.add(fileEntry);
	}

	public ArrayList<File> getTextfiles() {
		return textfiles;
	}

	public void setTextfiles(ArrayList<File> textfiles) {
		this.textfiles = textfiles;
	}

	public ArrayList<File> getAnnfiles() {
		return annfiles;
	}

	public void setAnnfiles(ArrayList<File> annfiles) {
		this.annfiles = annfiles;
	}

	public File getTextroot() {
		return textroot;
	}

	public File getAnnroot() {
		return annroot;
	}

}
