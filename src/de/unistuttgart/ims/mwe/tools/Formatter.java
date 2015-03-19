package de.unistuttgart.ims.mwe.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


/**
 * @author tarekmehrez
 * 
 */

public class Formatter {

	String path;
	String out;

	public Formatter(String path) {
		this.path = path;
		this.out = path + ".out";
		reformat();

	}

	private void reformat() {
		try {
			System.out.println("Started reformatting...");
			BufferedReader reader = new BufferedReader(new FileReader(
					new File(path).getAbsolutePath()));
			BufferedWriter writer = new BufferedWriter(new FileWriter(
					new File(out).getAbsolutePath()));
			
			String line;
			while ((line = reader.readLine()) != null) {
				if(line == "")
					continue;
				
				writer.write(line.replaceAll("\\-", " ").replaceAll("[^a-zA-Z ]", "")
						.replaceAll("[ ]+", " ").toLowerCase() +"\n");
				
			}
			reader.close();
			writer.close();
			System.out.println("Successfully reformatted, output is written in " + out);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
