package de.unistuttgart.ims.mwe.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import de.unistuttgart.ims.mwe.essentials.Phrase;

/**
 * @author tarekmehrez
 *
 */
public class Distance {

	String mwePath;
	String vecPath;
	String clusterPath;

	HashMap<String, Phrase> phrases; // phrase content -> phrase reference
	HashMap<Integer, ArrayList<Phrase>> classes; // cluster -> [tokens]

	double accuracy;
	int mweNum;

	public Distance(String mwe, String vec, String clusters) {

		this.mwePath = mwe;
		this.vecPath = vec;
		this.clusterPath = clusters;
		this.mweNum = 0;
		
		phrases = new HashMap<String, Phrase>();
		classes = new HashMap<Integer, ArrayList<Phrase>>();

		initialize();
		initializeBaselines();

		calculateDistances();

	}

	private void calculateDistances() {
		System.out
				.println("Calculating final distances between mwes and tokens...");

		for (Phrase phrase : phrases.values()) {

			if (!phrase.isMWE())
				continue;
			
			String[] tokens = phrase.getContent().split("_");
			if (isDifferentContext(phrase, tokens))
				accuracy++;


		}

		System.out.println("Accuracy is: " + accuracy/mweNum * 100 + "%");
	}

	private boolean isDifferentContext(Phrase phrase, String[] tokens) {

		double[] mweVector = phrase.getVector();
		double[] averageVector = getAverageVector(tokens);
		if (distance(mweVector, averageVector) != phrase.getBaseline()) {

			for (String token : tokens) {
				if (phrases.get(token) != null){
					
					if((phrase.getClassNumber() == phrases.get(token).getClassNumber()))
						return false;

					if (equalVectors(phrase.getVector(), phrases.get(token)
							.getVector()))
						return false;
				}

			}
			return true;

		}
		return false;

	}

	private boolean equalVectors(double[] v1, double[] v2) {
		
		if (v1.length != v2.length) {
			System.out.println("Vector sizes are not equal");
			System.exit(0);
		}
		
		for (int i = 0; i < v1.length; i++) {

			if (v1[i] != v2 [i])
				return false;

		}
		return true;
	}

	private double[] getAverageVector(String[] tokens) {

		double[] result = new double[100];

		for (int i = 0; i < result.length; i++) {

			for (String token : tokens) {
				if(phrases.get(token) != null)
					result[i] += phrases.get(token).getVector()[i];
			}

			result[i] /= tokens.length;
		}
		return result;

	}

	private void initialize() {

		try {
			System.out.println("Initializing Classes...");

			BufferedReader clusterReader = new BufferedReader(new FileReader(
					new File(this.clusterPath).getAbsolutePath()));

			String line;
			while ((line = clusterReader.readLine()) != null) {
				String token = line.split("\\s")[0];
				int cluster = Integer.parseInt(line.split("\\s")[1]);

				// initialize phrase
				boolean isMWE = token.contains("_");
				Phrase newPhrase = new Phrase(token, cluster,
						isMWE);
				
				if(isMWE)
					mweNum++;

				// add in mwes hashmap
				phrases.put(newPhrase.getContent(), newPhrase);

				// add in classes hash map
				if (classes.containsKey(cluster)) {
					classes.get(cluster).add(newPhrase);
				} else {
					ArrayList<Phrase> arr = new ArrayList<Phrase>();
					arr.add(newPhrase);
					classes.put(cluster, arr);
				}
			}
			line = "";
			clusterReader.close();
			System.out.println("Initializing Vectors...");

			BufferedReader vectorReader = new BufferedReader(new FileReader(
					new File(this.vecPath).getAbsolutePath()));
			vectorReader.readLine();
			while ((line = vectorReader.readLine()) != null) {

				String token = line.split("\\s")[0].trim();

				double[] vector = stringToDouble(line.split("\\s"));

				Phrase currPhrase = phrases.get(token);
				currPhrase.setVector(vector);
			}
			line = "";
			vectorReader.close();
			System.out.println("Initializing MWE Types...");

			BufferedReader typeReader = new BufferedReader(new FileReader(
					new File(this.mwePath).getAbsolutePath()));

			while ((line = typeReader.readLine()) != null) {

				String token = line.split(",")[0];
				String type = line.split(",")[1];
				if (phrases.get(token.replaceAll("\\s", "_")) != null)
					phrases.get(token.replaceAll("\\s", "_")).setType(type);

			}
			typeReader.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void initializeBaselines() {
		System.out.println("Calculating baselines for each mwe...");
		for (Phrase phrase : phrases.values()) {

			if (!phrase.isMWE())
				continue;

			for (ArrayList<Phrase> arr : classes.values()) {

				if (arr.contains(phrase))
					phrase.setBaseline(calculateBaseLine(arr, phrase));

			}

		}

	}

	private double calculateBaseLine(ArrayList<Phrase> phrases, Phrase mwe) {

		double result = 0.0;

		for (Phrase phrase : phrases) {

			if (phrase.equals(mwe))
				continue;

			result += distance(phrase.getVector(), mwe.getVector());

		}
		return result / phrases.size() - 1;

	}

	private double distance(double[] v1, double[] v2) {

		if (v1.length != v2.length) {
			System.out.println("Vector sizes are not equal");
			System.exit(0);
		}

		double result = 0;

		for (int i = 0; i < v1.length; i++)
			result += Math.pow(v1[i] - v2[i], 2);

		return Math.sqrt(result);

	}

	private double[] stringToDouble(String[] str) {
		double[] result = new double[str.length - 1];

		// i = 1 to skip the first element, which is the token
		for (int i = 0; i < result.length; i++)
			result[i] = Double.parseDouble(str[i + 1]);

		return result;

	}

}
