package lse;

import java.io.*;
import java.util.*;

/**
 * This class builds an index of keywords. Each keyword maps to a set of pages in
 * which it occurs, with frequency of occurrence in each page.
 *
 */
public class LittleSearchEngine {
	
	/**
	 * This is a hash table of all keywords. The key is the actual keyword, and the associated value is
	 * an array list of all occurrences of the keyword in documents. The array list is maintained in 
	 * DESCENDING order of frequencies.
	 */
	HashMap<String,ArrayList<Occurrence>> keywordsIndex;
	
	/**
	 * The hash set of all noise words.
	 */
	HashSet<String> noiseWords;
	
	/**
	 * Creates the keyWordsIndex and noiseWords hash tables.
	 */
	public LittleSearchEngine() {
		keywordsIndex = new HashMap<String,ArrayList<Occurrence>>(1000,2.0f);
		noiseWords = new HashSet<String>(100,2.0f);
	}
	
	/**
	 * Scans a document, and loads all keywords found into a hash table of keyword occurrences
	 * in the document. Uses the getKeyWord method to separate keywords from other words.
	 * 
	 * @param docFile Name of the document file to be scanned and loaded
	 * @return Hash table of keywords in the given document, each associated with an Occurrence object
	 * @throws FileNotFoundException If the document file is not found on disk
	 */
	public HashMap<String,Occurrence> loadKeywordsFromDocument(String docFile) 
	throws FileNotFoundException {
		/** COMPLETE THIS METHOD **/

		
		
		File docFile2 = new File(docFile);
		if (docFile2 == null)
			throw new FileNotFoundException();

		HashMap<String, Occurrence> keyWordMap = new HashMap<String, Occurrence>();


		Scanner readDoc = new Scanner(docFile2);

		while (readDoc.hasNext())
		{

			String key = getKeyword(readDoc.next());
			
			//check to see if getKeyword rejects the word
			if (key != null)
			{

				//add another instance of key to keyWordMap and increment the frequency
				if (keyWordMap.containsKey(key))
				{
					Occurrence keyWordoc = keyWordMap.get(key);
					keyWordoc.frequency++;
				}
				else
				//add the first occurrence of the keyword
				{
					Occurrence keyWordoc = new Occurrence(docFile, 1);
					keyWordMap.put(key, keyWordoc);
				}
			}
		}
		return keyWordMap;
		
		// following line is a placeholder to make the program compile
		// you should modify it as needed when you write your code
	//	return null;
	}
	
	/**
	 * Merges the keywords for a single document into the master keywordsIndex
	 * hash table. For each keyword, its Occurrence in the current document
	 * must be inserted in the correct place (according to descending order of
	 * frequency) in the same keyword's Occurrence list in the master hash table. 
	 * This is done by calling the insertLastOccurrence method.
	 * 
	 * @param kws Keywords hash table for a document
	 */
	public void mergeKeywords(HashMap<String,Occurrence> kws) {
		/** COMPLETE THIS METHOD **/

		
		final Set<String> keys =kws.keySet();
		

		
		
		//iterate through the keyset
		for (String key : keys)			
		{
			
			ArrayList<Occurrence> keyWordOccur = new ArrayList<Occurrence>();

			if (keywordsIndex.containsKey(key))
				keyWordOccur = keywordsIndex.get(key);			
			keyWordOccur.add(kws.get(key));
			insertLastOccurrence(keyWordOccur);
			keywordsIndex.put(key, keyWordOccur);
		}
		

	}
	
	/**
	 * Given a word, returns it as a keyword if it passes the keyword test,
	 * otherwise returns null. A keyword is any word that, after being stripped of any
	 * trailing punctuation, consists only of alphabetic letters, and is not
	 * a noise word. All words are treated in a case-INsensitive manner.
	 * 
	 * Punctuation characters are the following: '.', ',', '?', ':', ';' and '!'
	 * 
	 * @param word Candidate word
	 * @return Keyword (word without trailing punctuation, LOWER CASE)
	 */
	public String getKeyword(String word) {
		/** COMPLETE THIS METHOD **/

		if ((word == null) || (word.equals(null)))
			return null;
		word = word.toUpperCase();
		
		//check to see if word is a number or contains numbers
		if (word.contains("0") || word.contains("1") || word.contains("2") || word.contains("3") || word.contains("4") ||
				word.contains("5") || word.contains("6") || word.contains("7") || word.contains("8") || word.contains("9") ) {
			

			return null;
		}
		
		//check to see if word contains non letter characters in middle or beginning
		int lengthOfWord =0;
		
		if(!(Character.isLetter(word.charAt(lengthOfWord)))) {
			return null;
		}
		
		boolean containsNonLetter = false;
		while(lengthOfWord < word.length()) {
			if(!(Character.isLetter(word.charAt(lengthOfWord)))) {
				containsNonLetter = true;
			}
			if(Character.isLetter(word.charAt(lengthOfWord)) && containsNonLetter) {
				return null;
			}
			lengthOfWord ++;
		}
			
		//remove trailing punctuation
		lengthOfWord = 0;
		while (lengthOfWord < word.length()) {
			if(!(Character.isLetter(word.charAt(lengthOfWord)))) {
				break;
			}
			lengthOfWord ++;
		}	
		word = word.substring(0, lengthOfWord);
		// following line is a placeholder to make the program compile
		// you should modify it as needed when you write your code
		return word;
	}
	
	/**
	 * Inserts the last occurrence in the parameter list in the correct position in the
	 * list, based on ordering occurrences on descending frequencies. The elements
	 * 0..n-2 in the list are already in the correct order. Insertion is done by
	 * first finding the correct spot using binary search, then inserting at that spot.
	 * 
	 * @param occs List of Occurrences
	 * @return Sequence of mid point indexes in the input list checked by the binary search process,
	 *         null if the size of the input list is 1. This returned array list is only used to test
	 *         your code - it is not used elsewhere in the program.
	 */
	public ArrayList<Integer> insertLastOccurrence(ArrayList<Occurrence> occs) {
		/** COMPLETE THIS METHOD **/
		

		//check to see if occurrence arraylist has more than 1 element
		if (occs.size() < 2)
			return null;

		//use binary search to find 
		
		ArrayList<Integer> midpoints = new ArrayList<Integer>();
		
		int left = 0;
		int right = occs.size()-2;
		int endOfOccList;
		int insertOccList;
		
		int entry = occs.get(occs.size()-1).frequency;
		int mid = 0;


		while (right >= left)
		{
		 mid = ((left + right) / 2);
		 int occFreq = occs.get(mid).frequency;
		midpoints.add(mid);

	
		
		if (occFreq == entry)
			break;


		
		else if (occFreq < entry)
			{
				right = mid - 1;
			}

		  else if (occFreq > entry)
			{
				left = mid + 1;
				if (right <= mid)
					mid = mid + 1;
			}
		}
		
		midpoints.add(mid);
		
		//remove last Occurrence from end of ArrayList
		endOfOccList = occs.size()-1;
		Occurrence temp = occs.remove(endOfOccList);
		
		//insert last Occurrence into correct position in ArrayList
		insertOccList = midpoints.get(midpoints.size()-1);
		occs.add(insertOccList, temp);
 

		
		return midpoints;
		
		// following line is a placeholder to make the program compile
		// you should modify it as needed when you write your code
	//	return null;
	}
	
	/**
	 * This method indexes all keywords found in all the input documents. When this
	 * method is done, the keywordsIndex hash table will be filled with all keywords,
	 * each of which is associated with an array list of Occurrence objects, arranged
	 * in decreasing frequencies of occurrence.
	 * 
	 * @param docsFile Name of file that has a list of all the document file names, one name per line
	 * @param noiseWordsFile Name of file that has a list of noise words, one noise word per line
	 * @throws FileNotFoundException If there is a problem locating any of the input files on disk
	 */
	public void makeIndex(String docsFile, String noiseWordsFile) 
	throws FileNotFoundException {
		// load noise words to hash table
		Scanner sc = new Scanner(new File(noiseWordsFile));
		while (sc.hasNext()) {
			String word = sc.next();
			noiseWords.add(word);
		}
		
		// index all keywords
		sc = new Scanner(new File(docsFile));
		while (sc.hasNext()) {
			String docFile = sc.next();
			HashMap<String,Occurrence> kws = loadKeywordsFromDocument(docFile);
			mergeKeywords(kws);
		}
		sc.close();
	}
	
	/**
	 * Search result for "kw1 or kw2". A document is in the result set if kw1 or kw2 occurs in that
	 * document. Result set is arranged in descending order of document frequencies. (Note that a
	 * matching document will only appear once in the result.) Ties in frequency values are broken
	 * in favor of the first keyword. (That is, if kw1 is in doc1 with frequency f1, and kw2 is in doc2
	 * also with the same frequency f1, then doc1 will take precedence over doc2 in the result. 
	 * The result set is limited to 5 entries. If there are no matches at all, result is null.
	 * 
	 * @param kw1 First keyword
	 * @param kw1 Second keyword
	 * @return List of documents in which either kw1 or kw2 occurs, arranged in descending order of
	 *         frequencies. The result size is limited to 5 documents. If there are no matches, returns null.
	 */
	public ArrayList<String> top5search(String kw1, String kw2) {
		/** COMPLETE THIS METHOD **/

		
		ArrayList<String> top5 = new ArrayList<String>();  //ArrayList to hold documents with top5 occurrences
		
		ArrayList<Occurrence> occKw1 = new ArrayList<Occurrence>();
		ArrayList<Occurrence> occKw2 = new ArrayList<Occurrence>();
		
		ArrayList<Occurrence> occKw1orKw2 = new ArrayList<Occurrence>();//ArrayList to hold all occurrences for both keywords
		
		//add kw1 arraylist to combined arraylist
		if (keywordsIndex.containsKey(kw1))
			occKw1 = keywordsIndex.get(kw1);
		
		if (keywordsIndex.containsKey(kw2))
			occKw2 = keywordsIndex.get(kw2);
		
		//add kw2 arraylist to combined arraylist
		for (int j = 0; j < occKw1.size(); j++) {
			occKw1orKw2.add(occKw1.get(j));
		}
		
		for (int j = 0; j < occKw2.size(); j++) {
			occKw1orKw2.add(occKw2.get(j));
		}
		

		
		if (!(occKw1.isEmpty()) && !(occKw2.isEmpty()))
		{
			              // Sort in favor of occKw1
			for (int i = 0; i < occKw1orKw2.size()-1; i++)
			{
			 for (int j = 1; j < occKw1orKw2.size()-i; j++)
			  {
				if (occKw1orKw2.get(j-1).frequency < occKw1orKw2.get(j).frequency)
				 {
					Occurrence temp = occKw1orKw2.get(j-1);
					occKw1orKw2.set(j-1, occKw1orKw2.get(j));
					occKw1orKw2.set(j,  temp);
				 }
			  }
			}

			            // Remove any repeats
			for (int i = 0; i < occKw1orKw2.size()-1; i++)
			{
			 for (int j = i + 1; j < occKw1orKw2.size(); j++)
			  {
				if (occKw1orKw2.get(i).document == occKw1orKw2.get(j).document)
				 occKw1orKw2.remove(j);
			  }
			}
		}


		              //return the top 5 document occurrences
		for (int j = 0; j < 5; j++) {
			top5.add(occKw1.get(j).document);
		}


		return top5;
		
		// following line is a placeholder to make the program compile
		// you should modify it as needed when you write your code
//		return null;
	
	}
}
