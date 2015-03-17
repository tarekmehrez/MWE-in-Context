package de.unistuttgart.ims.mwe.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author tarekmehrez
 *
 */
public class IOBFile {

	ArrayList<String> excludedTypes;

	// final String[] excludedTypes = new String[] { "MWE_VPC" };

	String filePath;
	String output;

	HashMap<Integer, Integer> counts;

	public IOBFile(String file) {

		this.excludedTypes = new ArrayList<String>();
		addExcludedTypes();

		this.counts = new HashMap<Integer, Integer>(10);

		this.filePath = file;
		this.output = file + ".out";
		createFile();

	}

	private void addExcludedTypes() {
		this.excludedTypes.add("MWE_VPC");
//		this.excludedTypes.add("MWE_LVC");
	}

	private void createFile() {
		System.out.println("Writig filtered IOB file...");
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(
					this.filePath).getAbsolutePath()));
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(
					this.output).getAbsolutePath()));

			String line;
			String mweType = "";
			String mwe = "";
			boolean isMWE = false;
			while ((line = reader.readLine()) != null) {

				if (line.startsWith("-DOCSTART-") || line == "") {
					continue;
				} else if (line.contains("MWE")) {
					if (!isMWE) {
						isMWE = true;
						mweType = line.split("\\s")[1].split("-")[1];

						// in case we are not interested in this type of MWE
						if (excludedTypes.contains(mweType)) {
							isMWE = false;
							continue;
						}
					}
					mwe += line.split("\\s")[0] + " ";
					continue;
				} else {
					if (isMWE) {
						isMWE = false;
						writer.write(mwe.trim().replaceAll("[^a-zA-Z ]", "").toLowerCase() + "," + mweType
								+ "\n");
						int curr = mwe.trim().split(" ").length;
						if (counts.get(curr) == null)
							counts.put(curr, 1);
						else
							counts.put(curr, counts.get(curr) + 1);

						mwe = "";
						mweType = "";
					}
				}

			}
			reader.close();
			writer.close();
			System.out.println("Done writing IOB file");
			System.out.println("Statistics:\nSize\tFrequency");
			for (int i : counts.keySet()) {
				System.out.println(i + "\t" + counts.get(i));
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
