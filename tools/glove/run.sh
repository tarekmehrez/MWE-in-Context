# to be run separately

./vocab_count -verbose 2 -max-vocab 100000 -min-count 1 < ../../corpus/all.txt > vocab.txt

./cooccur -window-size 5 -vocab-file vocab.txt  -memory 8.0 -overflow-file tempoverflow < ../../corpus/all.txt > cooccurrences.bin

./glove -input-file cooccurrences.bin -vocab-file vocab.txt  -save-file vectors -gradsq-file gradsq -verbose 2 -vector-size 100 -binary 2 -model 2
