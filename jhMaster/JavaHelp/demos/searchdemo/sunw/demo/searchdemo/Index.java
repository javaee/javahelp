/*
 * @(#)Index.java	1.10 06/10/30
 * 
 * Copyright (c) 2006 Sun Microsystems, Inc.  All Rights Reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 
 * - Redistribution of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 * 
 * - Redistribution in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in
 *   the documentation and/or other materials provided with the
 *   distribution.
 * 
 * Neither the name of Sun Microsystems, Inc. or the names of
 * contributors may be used to endorse or promote products derived
 * from this software without specific prior written permission.
 * 
 * This software is provided "AS IS," without a warranty of any
 * kind. ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND
 * WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY
 * EXCLUDED. SUN MICROSYSTEMS, INC. ("SUN") AND ITS LICENSORS SHALL
 * NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF
 * USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE FOR
 * ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT, SPECIAL,
 * CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER CAUSED AND
 * REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF THE USE OF OR
 * INABILITY TO USE THIS SOFTWARE, EVEN IF SUN HAS BEEN ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGES.
 * 
 * You acknowledge that this software is not designed, licensed or
 * intended for use in the design, construction, operation or
 * maintenance of any nuclear facility. 
 */

/*
 * @(#) Index.java 1.10 - last change made 10/30/06
 *
 * (c) 1997-1998 Sun Microsystems, Inc.  All rights reserved.  Use is
 * subject to license terms. Sun, Sun Microsystems, the Sun Logo, Solaris,
 * Java, the Java Coffee Cup Logo, and JavaHelp are trademarks or registered
 * trademarks of Sun Microsystems, Inc. in  the U.S. and other countries.
 * 
 * This software is the confidential and proprietary information
 * of Sun Microsystems, Inc. ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with Sun.
 */

package sunw.demo.searchdemo;

import java.io.*;
import java.text.*;
import java.util.Vector;
import com.sun.java.help.impl.Parser;
import com.sun.java.help.impl.Tag;
import com.sun.java.help.impl.ParserListener;
import com.sun.java.help.impl.ParserEvent;

/**
 * This class provides ...
 *
 *
 * @version	1.10	10/30/06
 * @author	Roger D. Brinkley
 */

public class Index implements ParserListener{

    /** The database name. */
    String dbName;

    /** The stream for printing syntax errors */
    PrintStream verbose;

    /** The word table. */
    WordVector wordList;

    /** The documents. */
    DocVector docVec;

    /** The number of documents correctly parsed and compiled. */
    short docNumber;

    private ConfigFile config;
    private String title;
    private String header;
    private boolean firstheader=false;
    protected Vector tags;
    private WordHashtable table;
    private short wordCount;
    private Vector stopWords;


    /**
     * Starts the application.
     * @param args[]   the command line arguments
     **/

    public static void main(String args[]) {
	Index compiler = new Index();
	compiler.compile(args);
    }

    /**
     * Creates an initialised compiler.
     **/

    public Index() {
	dbName = "JavaHelpIndex";
	wordList = new WordVector(1000);
	docVec = new DocVector();
	docNumber = 0;
    }

    /**
     * Parses the <tt>.html</tt> files, and compiles the search database.
     * @param args[]   the command line arguments
     **/

    public void compile(String args[]) {
	long parseTime, compileTime, startTime = System.currentTimeMillis();
	int words;
	String configFile = null;
	DataOutputStream dataOut;
	FileOutputStream file;
	Vector tmpfiles = new Vector();
	Vector files = new Vector();
	int size;
	String sourcepath="";
	
	for (int i=0; i < args.length ; i++) { 
	    if(args[i].equals("-db")) {
		if ((i + 1) < args.length) {
		    dbName = args[++i];
		} else {
		    System.out.println (args[i] + "-db requires argument");
		}
	    }
	    else if(args[i].equals("-sourcepath")) {
		if ((i + 1) < args.length) {
		    sourcepath = args[++i];
		} else {
		    System.out.println (args[i] + "-sourcepath requires argument");
		}
	    }	   
	    else if(args[i].equals("-verbose")) verbose = System.out;
	    else if(args[i].equals("-c")) {
		if ((i + 1) < args.length) {
		    configFile = args[++i];
		} else {
		    System.out.println (args[i] + "-c requires argument");
		}
	    }
	    else if(args[i].startsWith("-")) {
		System.out.println("Unknown argument '" + args[i] + "'");
		showUsage();
		return;
	    } else {
	        tmpfiles.addElement(args[i]);
	    }
	}

	// read the config file
	config = new ConfigFile (configFile, files);
	files = config.getFiles();

	// adjust the passed with config parameters
	size = tmpfiles.size();
	for (int i=0; i < size; i++) {
	    files = loadFiles ((String)tmpfiles.elementAt(i), files);
	}

	stopWords = config.getStopWords();

	size = files.size();
	for (int i=0; i < size; i++) {
	    DocFiles doc = (DocFiles) files.elementAt(i);
	    System.out.println("   File: '" + doc.getFile() + "'");
	    System.out.println("    URL: '" + doc.getURLString() + "'");

	    // preset document info
	    title = null;
	    header = null;
	    table = new WordHashtable();
	    firstheader=true;
	    wordCount = 0;
	    tags = new Vector();

	    try {
		BufferedInputStream dataIn = 
		  new BufferedInputStream (new FileInputStream(sourcepath+doc.getFile()));
		Parser p = new Parser(dataIn);
		p.addParserListener(this);
		p.parse();
		dataIn.close();
	    }
	    catch(IOException e) {
		System.out.println("I/O exception occurred in file '" + sourcepath+doc.getFile() + "'");
		continue;
	    }
	    if(title == null || title.length() < 1) {
		title = header;
		if (title == null || title.length() < 1) {
		    title = "No Title";
		    debug ("  Title: ** No title found **");
		}
	    }
	    debug("  Title: '" + title + "'");

	    if(table.size() > 0) {
		wordList.addWordHashtable(table, docNumber);
		docVec.addElement(new Doc (title, doc.getURLString()));
		words = table.total();
		++docNumber;
	    }
	}
	parseTime = System.currentTimeMillis() - startTime;

	// store the parsed results to persistent storage

	if(wordList.size() > 0 && docVec.size() > 0) {
	    try {
		System.out.println("Storing documents");
		file = new FileOutputStream(dbName + ".dat");
		dataOut = new DataOutputStream(new BufferedOutputStream(file));
		docVec.writeDocVector(dataOut);
		dataOut.flush();
		file.close();
		file = new FileOutputStream(dbName + ".inv");
		dataOut = new DataOutputStream(new BufferedOutputStream(file));

		wordList.writeWordVector(dataOut);
		dataOut.flush();
		file.close();
		}
	    catch(IOException e) {
	      System.out.println("I/O exception occurred writing file '" + dbName + ".(inv,doc)");
	    return;
	    }
	}

	// show some final statistics

	compileTime = System.currentTimeMillis() - startTime - parseTime;
	System.out.println("        Parse time: " + (float)parseTime / 1000.0 + " s");
	System.out.println("      Compile time: " + (float)compileTime / 1000.0 + " s");
	System.out.println("Documents compiled: " + docVec.size());
	System.out.println("   Word table size: " + wordList.size());
    }

  
    /**
     * A PI was found
     */
    public void piFound(ParserEvent e) {
	// ignore
    }

    /**
     * A DOCTYPE was parsed
     */
    public void doctypeFound(ParserEvent e) {
	// ignore
    }

    /**
     *  A Tag was parsed
     */
    public void tagFound(ParserEvent e) {
	Tag tag = e.getTag();
	if (tag.name.compareTo("H") == 0) {
	    if (tag.isEnd) {
		tags.removeElement(tags.lastElement());
		firstheader=false;
		return;
	    }
	    tags.addElement(tag);
	} else if (tag.name.compareTo("TITLE") == 0) {
	    if (tag.isEnd) {
		tags.removeElement(tags.lastElement());
		return;
	    }
	    tags.addElement(tag);
	}
    }

    /**
     * A continous block of text was parsed
     */
    public void textFound(ParserEvent e) {
	String text = e.getText();
	if (tags.size() > 0) {
	    Tag tag = (Tag) tags.lastElement();
	    if (tag.name.compareTo("TITLE") == 0) {
		if (title == null) {
		    title = text.trim();
		} else {
		    title.concat(text.trim());
		}
		parseIntoWords(text, (short) 10);
	    } else if (tag.name.compareTo("H") == 0) {
		if (firstheader) {
		    if (header == null) {
			header = text.trim();
		    } else {
			header.concat(text.trim());
		    }
		}
		parseIntoWords(text, (short) 5);
	    }  else {
		parseIntoWords(text, (short) 1);
	    }
	} else {
	    parseIntoWords(text, (short) 1);
	}
    }

    private void parseIntoWords (String source, short weight) {
	BreakIterator boundary;
	int start;
	String word;
	int stopSize = stopWords.size();

	boundary = BreakIterator.getWordInstance();
	boundary.setText(source);
	start = boundary.first();
	for (int end = boundary.next();
	     end != BreakIterator.DONE;
	     start = end, end = boundary.next()) {
	    word = new String(source.substring(start,end));
	    word = word.toLowerCase();
	    word = word.trim();
	    // Check and see if this is a stop word
	    for (int i=0; i < stopSize; i++) {
		if (word.compareTo((String)stopWords.elementAt(i)) == 0) {
		    continue;
		}
	    }
	    if (word.length() > 1) {
		wordCount += 1;
		table.addWord(word, weight, wordCount);
	    } else if (word.length() == 1) {
		int charType = Character.getType(word.charAt(0));
		if ((charType == Character.DECIMAL_DIGIT_NUMBER) || 
		    (charType == Character.LETTER_NUMBER) || 
		    (charType == Character.LOWERCASE_LETTER) || 
		    (charType == Character.OTHER_LETTER) || 
		    (charType == Character.OTHER_NUMBER) || 
		    (charType == Character.TITLECASE_LETTER) || 
		    (charType == Character.UNASSIGNED) || 
		    (charType == Character.UPPERCASE_LETTER)) {
		    wordCount += 1;
		    table.addWord(word, weight, wordCount);
		}
	    }
	}
    }

    // The remaing events from Parser are ignored
    public void commentFound(ParserEvent e) {}

    public void errorFound(ParserEvent e){
	System.out.println (e.getText());
    }

  public Vector loadFiles (String file, Vector files) {
      File tstfile = new File (file);
      if (tstfile.isDirectory()) {
	  String list[] = tstfile.list();
	  for (int i=0; i < list.length; i++) {
	      files = loadFiles (tstfile.getPath() + 
				 File.separator +
				 list[i], files);
	  }
      } else {
	  if (file.endsWith(".html") || file.endsWith(".htm")) {
	      DocFiles doc = new DocFiles(file, config.getURLString(file));
	      files.addElement(doc);
	  }
      }
      return files;
  }

    /**
     * Shows the usage message.
     **/

    public void showUsage() {
	System.out.println(" Usage:   java JavaHelp.Index options file ...");
	System.out.println(" Options: -c file   config file");
	System.out.println("          -db file  generated database file name");
	System.out.println("          -verbose  verbose documentation");
	System.out.println("Note: config file composition:");
	System.out.println("          IndexRemove /public_html/JavaHelp/demo");
	System.out.println("          IndexPrepend ..");
	System.out.println("          StopWords word1 ... wordN");
	System.out.println("          File /public_html/JavaHelp/demo/first.html");
	System.out.println("          File=/public_html/JavaHelp/demo/second.html");
	System.out.println("          ...");
    }

    class DocFiles {
	/*
	 * internal storage of doc files in config file
	 */
	private String file;
	private String url;

	public DocFiles (String file, String url) {
	    this.file = file;
	    this.url = url;
	}

	public String getFile() { return file; }
	public String getURLString() { return url; }
    }

    class ConfigFile {

	private String remove;

	private String prepend;

	private Vector stopWords;

        private String defStopWords[] = {
	  "a", "about", "above", "according", "across", "actually", "adj", "after", 
	  "afterwards", "again", "against", "all", "almost", "alone", "along", 
	  "already", "also", "although", "always", "among", "amongst", "an", "and", 
	  "another", "any", "anyhow", "anyone", "anything", "anywhere", "are", "aren", 
	  "aren't", "around", "as", "at", "be", "became", "because", "become", "becomes", 
	  "becoming", "been", "before", "beforehand", "begin", "beginning", "behind", 
	  "being", "below", "beside", "besides", "between", "beyond", "billion", "both", 
	  "but", "by", "can", "can't", "cannot", "caption", "co", "could", "couldn",
	  "couldn't", "did", "didn", "didn't", "do", "does", "doesn", "doesn't", "don",
	  "don't", "down", "during", "each", "eg", "eight", "eighty", "either", "else",
	  "elsewhere", "end", "ending", "enough", "etc", "even", "ever", "every",
	  "everyone", "everything", "everywhere", "except", "few", "fifty", "first",
	  "five", "for", "former", "formerly", "forty", "found", "four", "from",
	  "further", "had", "has", "hasn", "hasn't", "have", "haven", "haven't",
	  "he", "hence", "her", "here", "hereafter", "hereby", "herein", "hereupon", 
	  "hers", "herself", "him", "himself", "his", "how", "however", "hundred", 
	  "ie", "i.e.", "if", "in", "inc", "inc.", "indeed", "instead", "into", "is",
	  "isn", "isn't", "it", "its", "itself", "last", "later", "latter", "latterly",
	  "least", "less", "let", "like", "likely", "ll", "ltd", "made", "make",
	  "makes", "many", "maybe", "me", "meantime", "meanwhile", "might", "million",
	  "miss", "more", "moreover", "most", "mostly", "mr", "mrs", "much", "must",
	  "my", "myself", "namely", "neither", "never", "nevertheless", "next", "nine",
	  "ninety", "no", "nobody", "none", "nonetheless", "noone", "nor", "not",
	  "nothing", "now", "nowhere", "of", "off", "often", "on", "once", "one",
	  "only", "onto", "or", "other", "others", "otherwise", "our", "ours",
	  "ourselves", "out", "over", "overall", "own", "per", "perhaps", "rather",
	  "re", "recent", "recently", "same", "seem", "seemed", "seeming", "seems",
	  "seven", "seventy", "several", "she", "should", "shouldn", "shouldn't",
	  "since", "six", "sixty", "so", "some", "somehow", "someone", "something",
	  "sometime", "sometimes", "somewhere", "still", "stop", "such", "taking",
	  "ten", "than", "that", "the", "their", "them", "themselves", "then",
	  "thence", "there", "thereafter", "thereby", "therefore", "therein",
	  "thereupon", "these", "they", "thirty", "this", "those", "though",
	  "thousand", "three", "through", "throughout", "thru", "thus", "to",
	  "together", "too", "toward", "towards", "trillion", "twenty", "two", "under",
	  "unless", "unlike", "unlikely", "until", "up", "upon", "us", "used", "using",
	  "ve", "very", "via", "was", "wasn", "we", "we", "well", "were", "weren",
	  "weren't", "what", "whatever", "when", "whence", "whenever", "where", 
	  "whereafter", "whereas", "whereby", "wherein", "whereupon", "wherever", 
	  "whether", "which", "while", "whither", "who", "whoever", "whole", "whom", 
	  "whomever", "whose", "why", "will", "with", "within", "without", "won", 
	  "would", "wouldn", "wouldn't", "yes", "yet", "you", "your", "yours",
	  "yourself", "yourselves"};

	private Vector files;

	public ConfigFile (String configFile, Vector files) {
	    this.files = files;
	    LineNumberReader in;
	    String line;
	    String removeText = new String ("IndexRemove ");
	    String prependText = new String ("IndexPrepend ");
	    String fileText = new String ("File ");
	    String stopWordsText = new String ("StopWords ");
	    BreakIterator boundary;
	    int start;
	    String url;
	    String word;

	    stopWords = new Vector();

	    if (configFile == null) {
	      return;
	    }
	    try {
		in = new LineNumberReader(new BufferedReader
					  (new FileReader(configFile)));
		while ((line = in.readLine()) != null) {
		    if (line.startsWith(removeText)) {
			remove = line.substring (removeText.length(),
						 line.length());
		    } else if (line.startsWith(prependText)) {
			prepend = line.substring (prependText.length(),
						  line.length());
		    } else if (line.startsWith(fileText)) {
			String file = line.substring (fileText.length(),
						      line.length());
			url = getURLString(file);
			DocFiles doc = new DocFiles (file, url);
			files.addElement (doc);
		    } else if (line.startsWith(stopWordsText)) {
			String words = line.substring (stopWordsText.length(),
						       line.length());
			boundary = BreakIterator.getWordInstance();
			boundary.setText(words);
			start = boundary.first();
			for (int end = boundary.next();
			     end != BreakIterator.DONE;
			     start = end, end = boundary.next()) {
			    word = new String(words.substring(start,end));
			    word = word.toLowerCase();
			    word = word.trim();
			    stopWords.addElement (word);
			}
		    } else {
			System.out.println ("Unknown Config Keyword at line " +
					    in.getLineNumber());
		    }
		}
	    } catch (IOException e) {}
	}

        public String getURLString (String file) {
	    String url;

	    if (remove != null && (file.startsWith(remove))) {
	        url = file.substring(remove.length(), file.length());
	    } else {
	        url = file;
	    }
	    if (prepend != null) {
	      url = prepend + url;
	    }
	    return url;
	}

	public Vector getStopWords() { 
	  if (stopWords.size() == 0) {
	    for (int i = 0; i < defStopWords.length; i++) {
	      stopWords.addElement(defStopWords[i]);
	    }
	  }
	  return stopWords;
	}

	public Vector getFiles () { return files; }
    }

    /**
     * For printf debugging.
     */
    private static boolean debugFlag = true;
    private static void debug(String str) {
        if( debugFlag ) {
            System.out.println("Index: " + str);
        }
    }
}

