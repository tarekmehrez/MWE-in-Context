package de.unistuttgart.ims.mwe.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

public class Distance {

	HashMap<String, String> mwes;
	HashMap<String, double[]> vectors;

	String vecPath;
	String mwePath;
	String out;
	String clustersPath;

	public Distance(String vec, String mwe, String clusters) {
		this.vecPath = vec;
		this.mwePath = mwe;
		this.clustersPath = clusters;
		this.out = vec + ".distances.txt";

		this.mwes = new HashMap<String, String>();
		this.vectors = new HashMap<String, double[]>();
		initialize();

		computeDistances();
	}

	// fill in vectors hashmap <token, vector> and mwes hashmap <mwe, mwe type>

	private void computeBaseLine() {
		// TODO Auto-generated method stub

	}

	private void initialize() {
		System.out.println("Reading vectors and MWEs...");
		try {
			BufferedReader vecReader = new BufferedReader(new FileReader(
					new File(this.vecPath).getAbsolutePath()));

			String vector;

			while ((vector = vecReader.readLine()) != null) {
				String[] curr = vector.split("\\s+");
				String key = curr[0];
				double[] currInt = strToDouble(curr);

				vectors.put(key, currInt);
			}

			vecReader.close();

			BufferedReader mweReader = new BufferedReader(new FileReader(
					new File(this.mwePath).getAbsolutePath()));
			String mwe;
			while ((mwe = mweReader.readLine()) != null) {

				mwes.put(mwe.split(",")[0], mwe.split(",")[1]);

			}

			mweReader.close();
			System.out.println("Done reading vectors and MWEs...");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private double[] strToDouble(String[] arr) {
		double[] result = new double[arr.length - 1];
		for (int i = 1; i < arr.length; i++) {
			result[i] = Integer.parseInt(arr[i]);
		}
		return result;
	}

	private void computeDistances() {

		System.out.println("Computing distances...");

		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(
					this.out).getAbsolutePath()));
			writer.write("mwe,type,distance-to-avg-tokens");

			for (Entry<String, String> entry : mwes.entrySet()) {

				double d1 = mweTokensDistance(entry.getKey());

				writer.write(entry.getKey() + "," + entry.getValue() + "," + d1);

			}
			writer.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private double mweTokensDistance(String key) {

		String[] words = key.split("\\s+");
		double[] average = new double[100];

		for (int i = 0; i < average.length; i++) {

			double sum = 0.0;

			for (int j = 0; j < words.length; j++) {
				sum += vectors.get(words[j])[i];
			}
			average[i] = sum / words.length;
		}

		return distance(average, vectors.get(key));

	}

	private double distance(double[] a, double[] b) {
		double sum = 0;
		for (int i = 0; i < a.length; i++) {

			sum += Math.pow(a[i] - b[i], 2);
		}
		return Math.sqrt(sum);
	}
}
