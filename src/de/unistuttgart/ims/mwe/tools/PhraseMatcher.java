package de.unistuttgart.ims.mwe.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author tarekmehrez
 *
 */
public class PhraseMatcher {

	String textPath;
	String annPath;
	String out;

	public PhraseMatcher(String text, String ann) {
		this.textPath = text;
		this.annPath = ann;
		this.out = text + ".phrased";
		matchPhrases();

	}

	private void matchPhrases() {
		System.out.println("Started writing ...");
		try {
			BufferedReader textReader = new BufferedReader(new FileReader(
					new File(this.textPath).getAbsolutePath()));
			BufferedReader annReader = new BufferedReader(new FileReader(
					new File(this.annPath).getAbsolutePath()));
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(
					out).getAbsolutePath()));

			String textLine;
			String text = "";
			while ((textLine = textReader.readLine()) != null) {
				text += textLine + "\n";
			}

			ArrayList<String> mwes = new ArrayList<String>();
			String mweLine;
			while ((mweLine = annReader.readLine()) != null) {
				mwes.add(mweLine.split(",")[0].toLowerCase());
			}

			for (String s : mwes) {
				text = text.replaceAll(s, s.replaceAll("\\s+", "_"));
			}

			writer.write(text);

			textReader.close();
			annReader.close();
			writer.close();
			System.out
					.println("Successfully done, output is written in " + out);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
