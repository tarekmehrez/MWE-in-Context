#####################
#Author: Tarek Mehrez
#Date: 19.03.15
#Desc: all commands needed to reproduce the mwe in context results described in the doc/ directory
#		(this tool is specific for the wiki50 corpus http://www.inf.u-szeged.hu/rgai/mwe)
####################

$JAVA='java -jar mwe.jar'

# 1- preprocessing steps for the corpus (all.txt is basically all text files in corpus/corpus/standalone/distilled/text/ in one)
$JAVA --reformat-text corpus/all.txt

# 2- compile the final mwe list from the iob file
$JAVA -jar mwe.jar --complie-iob corpus/IOB/wiki50_distilled.iob

# 3- create the final text file with mwes as phrases (_ separated) to be fed into word2vec and glove
$JAVA -jar mwe.jar --construct-phrases corpus/all.txt.out corpus/IOB/wiki50_distilled.iob.out



# produce vectors using word2vec (for skipgram and cbow models) https://code.google.com/p/word2vec/
$word2vec='tools/word2vec'
mkdir $word2vec/results

$word2vec/word2vec -train corpus/all.txt.out.phrased -output $word2vec/results/vectors.cbow.bin -cbow 1 -binary 1 -min-count 0
$word2vec/word2vec -train corpus/all.txt.out.phrased -output $word2vec/results/vectors.cbow.txt -cbow 1  -min-count 0
$word2vec/word2vec -train corpus/all.txt.out.phrased -output $word2vec/results/vectors.sg.bin  -binary 1 -min-count 0
$word2vec/word2vec -train corpus/all.txt.out.phrased -output $word2vec/results/vectors.sg.txt -min-count 0

# produce vectors using glove nlp.stanford.edu/projects/glove/
$glove='tools/glove'
mkdir $glove/results


$glove/vocab_count -verbose 2 -min-count 0 < corpus/all.txt.out.phrased > $glove/results/vocab.txt
$glove/cooccur -verbose 2 -window-size 5 -vocab-file vocab.txt -overflow-file tempoverflow < corpus/all.txt.out.phrased > $glove/results/cooccurrences.bin
$glove/shuffle -verbose 2 -memory 8.0 < cooccurrences.bin > cooccurrences.shuf.bin
$glove/glove -input-file $glove/results/cooccurrences.shuf.bin -vocab-file $glove/results/vocab.txt -save-file $glove/results/vectors -gradsq-file $glove/results/gradsq -verbose 2 -vector-size 100 -threads 16 -binary 2 -model 2
