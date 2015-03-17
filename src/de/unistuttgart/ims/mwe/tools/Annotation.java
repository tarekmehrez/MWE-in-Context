package de.unistuttgart.ims.mwe.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;

import de.unistuttgart.ims.mwe.io.Corpus;

/**
 * @author tarekmehrez
 *
 */
public class Annotation {

	Corpus corpus;

	String resultPath;
	public Annotation(Corpus corpus, String resultPath) {
		this.resultPath = resultPath;
		this.corpus = corpus;

		if (createAnnotations())
			System.out.println("annotations extracted successfully");
	}

	private boolean createAnnotations() {

		ArrayList<File> textfiles = corpus.getTextfiles();
		ArrayList<File> annfiles = corpus.getAnnfiles();
		
		FileReader in=null;
		for (int i = 0; i < textfiles.size(); i++) {
			System.out.println("Processing file: " + textfiles.get(i).getName());
			try {
				in = new FileReader(textfiles.get(i));				
				getAnnotations(annfiles.get(i),IOUtils.toString(in));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return true;

	}

	private void getAnnotations(File file,String text) {

		try {
			BufferedReader reader = new BufferedReader (new FileReader(file));		
			String line;
			String result="";
			int position=0;
			while ((line = reader.readLine()) != null) {
				if (line.startsWith("position")){
					continue;
				}
					
				if (Character.isDigit(line.charAt(0)))
					continue;

				String[] annotations = line.split("\t");

				result += annotations[0] + ",";
				result += text.substring(Integer.parseInt(annotations[1])-position,Integer.parseInt(annotations[2])-position)+"\n";
				write(this.resultPath+"/"+file.getName()+".result",result);

			}
			reader.close();
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void write(String name, String result) {
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(name), "utf-8"));
			
		    writer.write(result);
		    writer.close();
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
