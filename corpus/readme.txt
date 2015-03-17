-----------------------------------------------------------------------------------------------
-----------------------------------------------------------------------------------------------
Corpus Documentation for article 'Wikipedia Corpus of Multiword Expressions and Named Entities'
-----------------------------------------------------------------------------------------------
-----------------------------------------------------------------------------------------------

----------------------
Introduction
----------------------

This corpus consists of fifty Wikipedia articles in which several types of multiword expressions, Named Entities and sentence boundaries are manually annotated. The articles contain only running text (i.e. no tables, lists etc.) and at least 1000 tokens occur in each. Topics of the articles vary.

----------------------
The corpus
----------------------

In the corpus, (1) sentence boundaries; (2) multiword expressions and (3) Named Entities were marked. The following categorization and labeling of items were applied when annotating:

(1)	Sentence boundaries
The last word (token) of each sentence was marked with the following labels:

SENT_BOUND: sentence boundary
SENT_BOUND_ABBREV: this tag was applied if the last token of the sentence was an abbreviation ending in a punctuation mark ("Jr.", "etc."). The punctuation mark bears the label. In this way, we were able to indicate that the punctuation mark signals the end of the sentence and belongs to the abbreviation at the same time.

(2)	Multiword expressions
The following tags were used for identifying different types of multiword expressions:

MWE_COMPOUND_NOUN: nominal compounds
MWE_COMPOUND_ADJ: adjectival compounds
MWE_IDIOM: idioms and proverbs
MWE_LVC: light verb constructions
MWE_VPC: verb-particle constructions
MWE_OTHER: other types of multiword expressions that do not belong to the above categories (e.g. foreign phrases ("status quo"))

In the case of light verb constructions and verb-particle constructions, the two parts might not be adjacent ("a decision has just been taken" or "put it off") hence we chose to mark the two parts of both constructions separately. In this way, split multiword expressions can be identified in our corpus as well. The annotation was carried out hierarchically: first, it was marked that the text span is an instance of e.g. a light verb construction then its two parts were also labeled. The following tags were employed at the second level of annotation:

MWE_LVC -> Noun: nominal component of a light verb construction
MWE_LVC -> Verb: verbal component of a light verb construction
MWE_VPC -> Verb: verbal part of a verb-particle construction
MWE_VPC -> Particle: particle in a verb-particle construction

Another layer of hierarchical annotation should be mentioned: since each type of multiword expressions can occur at the very end of the sentence, we had to apply double categories as well. First, we marked the multiword expressions and their subcomponents (if any) and the last token in the sentence was marked with an MWE_TYPE_SB tag. In this way, the information that the last token is a part of a multiword expression is also preserved.

(3)	Named Entities
The following Named Entity categories were applied when annotating (in accordance with the CoNLL-2003 Shared Task annotation principles):

NE_PER: people and people-like entities (e.g. gods)
NE_LOC: locations
NE_ORG: organizations
NE_MISC: other types of Named Entities

Named Entities also might occur at the end of the sentence, thus, hierarchical layers of annotation were also introduced here: NE_TYPE_SB refers to a Named Entity (or to the last token of a multiword Named Entity) that occurs at a sentence boundary. On the other hand, NEs often contain abbreviations (especially organization names), which made it necessary to include another second level annotation tag: NE_TYPE_SB_ABBREV. This tag was used whenever the last token of the multiword NE was an abbreviation ("Inc.", "Ltd.") and occurred at the end of the sentence.

Some annotation principles:

-	only MWEs mentioned above were annotated (for instance, no quotes or determinerless PPs were marked)
-	elliptical coordinations were not marked: thus, in "music and sound effects", only "sound effects" is marked as MWE, "music" is not
-	however, in "Julia and Peter Smith", both "Julia" and "Peter Smith" are annotated (since they form a NE on their own)
-	for NEs, tag-for-meaning annotation was carried out

----------------------
Corpus format
----------------------

The corpus exists in two versions: in the distilled version, segmentation errors (e.g. missing spaces) were corrected manually, and irrelevant parts of the documents (e.g. references or footnotes) were filtered. In the noisy version no such modifications were carried out, thus, it truly represents texts collected from the web.

Both versions of the corpus are available in two formats:

(1)	Each article in one file (ARTICLE.txt) + annotation in another file (ARTICLE.txt.annotation).
(2)	In IOB format (wiki50.{distilled,noisy}.iob).

----------------------
Corpus statistics
----------------------

The corpus contains 4350 sentences (114,570 tokens).

Type	Occurrence	Unique phrases
Noun compound	2929	2405
Adjectival compound	78	60
VPC	446	342
LVC	368	338
Idiom	19	18
Other MWE	21	17
MWEs total	3861	3180
PER	4093	1533
ORG	1498	893
LOC	1558	705
MISC	1827	952
NEs total	8976	4083


----------------------
Contact
----------------------

For further information please contact Veronika Vincze (vinczev AT inf DOT u-szeged DOT hu).

