package de.unistuttgart.ims.mwe.main;

import de.unistuttgart.ims.mwe.io.Corpus;
import de.unistuttgart.ims.mwe.tools.Annotation;
import de.unistuttgart.ims.mwe.tools.Formatter;
import de.unistuttgart.ims.mwe.tools.IOBFile;
import de.unistuttgart.ims.mwe.tools.PhraseMatcher;

/**
 * @author tarekmehrez
 * 
 */
public class MWE {

	public static void main(String[] args) {

		// no args

		if (args.length == 0)
			printHelp(args.length, 1);

		// annotating corpus

		else if (args[0].equals("--annotate-corpus")) {
			printHelp(args.length, 3);

			Corpus corpus = new Corpus(args[1]);
			new Annotation(corpus, args[2]);
		}

		// filtering iob file to list of mwes

		else if (args[0].equals("--compile-iob")) {

			printHelp(args.length, 2);

			if (!args[1].endsWith(".iob")) {
				System.out.println("File extension should be .iob");
				System.exit(0);
			}
			new IOBFile(args[1]);
		}

		// reformatting text files to be ready to extract word vectors

		else if (args[0].equals("--reformat-text")) {

			printHelp(args.length, 2);

			new Formatter(args[1]);

		}

		else if (args[0].equals("--construct-phrases")) {

			printHelp(args.length, 3);

			new PhraseMatcher(args[1], args[2]);

		}

		else if (args[0].equals("--compute-distances")) {

			printHelp(args.length, 3);

		}

		else {
			System.out.println("Undefined Option");
			printHelp(args.length, 5);
		}
	}

	// help statement

	public static void printHelp(int argsLength, int min) {

		if (argsLength < min) {

			System.out
					.println("MWE Extraction from wiki50 corpus\n"
							+ "Options:\n"
							+ "Create annotations from text: --annotate-corpus path/to/annoations/and/text /path/to/output\n"
							+ "Filter iob file to only mwes: --compile-iob path/to/iob/file\n"
							+ "Reformat text file to 1 single sentence & remove punctuation: --reformat-text /path/to/file\n"
							+ "Construct file with annotated mwes as phrases, to be fed into word2vec/glove: --construct-phrases /path/to/text/file /path/to/iob/file\n"
							+ "Compute distance of of MWEs: --compute-distances /path/to/vectors/file /path/to/iob/file");

			System.exit(0);
		}
	}

}
