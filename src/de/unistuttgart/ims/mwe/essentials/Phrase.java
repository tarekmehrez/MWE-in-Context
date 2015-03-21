package de.unistuttgart.ims.mwe.essentials;

/**
 * @author tarekmehrez
 *
 */
public class Phrase {

	String content;
	String type;
	
	int classNumber;
	
	double[] vector;
	double baseline;
	
	boolean isMWE;

	public Phrase(String c, int classNumber, boolean isMWE) {

		this.content = c;
		this.classNumber = classNumber;
		this.isMWE = isMWE;
		type = "";
		
		
		vector = new double[100]; // default size, could be changed in case vector
								// size was changed
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getClassNumber() {
		return classNumber;
	}

	public void setClassNumber(int classNumber) {
		this.classNumber = classNumber;
	}

	public double[] getVector() {
		return vector;
	}

	public void setVector(double[] vector) {
		this.vector = vector;
	}

	public double getBaseline() {
		return baseline;
	}

	public void setBaseline(double baseline) {
		this.baseline = baseline;
	}

	public boolean isMWE() {
		return isMWE;
	}

	public void setMWE(boolean isMWE) {
		this.isMWE = isMWE;
	}

}
