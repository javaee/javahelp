/*
 * @(#)WordVector.java	1.6 06/10/30
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
 * @(#) WordVector.java 1.6 - last change made 10/30/06
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

/**
 * A Vector containing Words in alphabetical order.
 *
 * @author Roger D. Brinkley
 * @version	1.6	10/30/06
 *
 * @see Word
 * @see WordHashtable
 */


import java.util.Enumeration;
import java.util.Vector;
import java.io.*;

public class WordVector extends Vector {


    /**
     * Creates an empty WordVector.
     **/

    public WordVector() { }

    /**
     * Creates an empty WordVector with initial size.
     * @param size     the number of elements initially in the vector
     **/

    public WordVector(int size) {
	super(size);
    }


    public WordVector (DataInputStream from) throws IOException {
	try {
	    short cookie = from.readShort();
	    if (cookie != COOKIE.INVENTORY) {
		from.close();
		return;
	    }
	    short version = from.readShort();
	    int vecsize = from.readInt();
	    ensureCapacity (vecsize);

	    for (int i=0; i < vecsize; i++) {
		/*
		 * Note: A simple AddElement will still give an alphabetical list
		 * because the list has already been alphabetized before being
		 * written out
		 */
		Word word = new Word(from);
		addElement (word);
	    }
	} catch(IOException e) {}
    }

    public void writeWordVector (DataOutputStream to) throws IOException {
	try {
	    to.writeShort(COOKIE.INVENTORY);
	    to.writeShort(COOKIE.VERSION);
	    to.writeInt (size());
      
	    for(int i = 0; i < size(); i++) {
		Word word = (Word)elementAt(i);
		word.writeWord(to);
	    }
	} catch(IOException e) {}
    }

    /**
     * Adds all the elements of a WordHashtable and a document index number.
     * @param hash   the WordHashtable to add
     * @param doc	  the document index
     * @see WordHashtable
     **/

    public final void addWordHashtable(WordHashtable hash, short docId) {
	int total = hash.total();
	Enumeration enum = hash.elements();
	while(enum.hasMoreElements()) {
	    Word node = (Word)enum.nextElement();
	    addWord(node, docId);
	}
    }

    /**
     * Adds a Word and a document index number to the vector.
     * @param wordElem	the Words element to add
     * @param docId	the document index
     * @see Word
     * @see WordInventory
     **/

    protected void addWord(Word wordElem, short docId) {
	int upper, lower, key, comparison;
	String word = wordElem.getWord();
	Word elem;

	key = comparison = 0;
	if((upper = size()) > 0) {
	    lower = -1;
	    while(true) {
		key = (upper + lower) >> 1;
		elem = (Word)elementAt(key);
		if((comparison = word.compareTo(elem.getWord())) == 0) {
		    if(docId >= 0) elem.addWordInventory(docId, wordElem);
		    return;
		}
		if(comparison < 0) upper = key;
		else lower = key;
		if(lower >= (upper - 1)) break;
	    }
	}
	if(comparison > 0) ++key;
	elem = new Word(word);
	insertElementAt(elem, key);
	if(docId >= 0) elem.addWordInventory(docId, wordElem);
    }

    /**
     * Find a Word in the vector.
     * @param word      the word to search for
     * @return Word	the matching Word
     * @see Word
     * @see WordInventory
     **/

    protected Word findWord(String word) {
	int upper, lower, key, comparison;
	Word elem;

	if (word.endsWith("*")) {
	    return findWords(word);
	}
	key = comparison = 0;
	if((upper = size()) > 0) {
	    lower = -1;
	    while(true) {
		key = (upper + lower) >> 1;
		elem = (Word)elementAt(key);
		if((comparison = word.compareTo(elem.getWord())) == 0) {
		    return elem;
		}
		if(comparison < 0) upper = key;
		else lower = key;
		if(lower >= (upper - 1)) break;
	    }
	}
	return null;
    }

    /**
     * Find all the Words in the vector beginning with word
     * @param word      the word to search for
     * @return Vector	A Vector of matching Words
     * @see Word
     * @see WordInventory
     **/

    protected Word findWords(String wordin) {
	int upper, lower, key, comparison, i, size, j;
	Word elem;
	boolean found=false;
	String word = new String (wordin.substring(0,wordin.lastIndexOf("*")));

	key = comparison = 0;
	if((upper = size()) > 0) {
	    lower = -1;
	    while(true) {
	        key = (upper + lower) >> 1;
		elem = (Word)elementAt(key);
		if(elem.getWord().startsWith(word) == true) {
		    found=true;
		    break;
		}
		comparison = word.compareTo(elem.getWord());
		if(comparison < 0) upper = key;
		else lower = key;
		if(lower >= (upper - 1)) break;
	    }
	}

	/*
	 * Didn't find a match so bug out of here
	 */
	if (!found) {
	    return null;
	}

	Word returnWord = new Word(word);
	Vector returnInvVec = returnWord.getWordInventory();

	/*
	 * Go back to first entry that matches
	 */
	for (i=key-1; i > 0 ; i--) {
	    elem = (Word)elementAt(i);
	    if(elem.getWord().startsWith(word) != true) {
		break;
	    }
	}
	
	/*
	 * Ok found the match now build the psuedo word
	 */
	for (i++; i < size() ; i++) {
	    elem = (Word)elementAt(i);
	    if(elem.getWord().startsWith(word) == true) {
		Vector elemInvVec = elem.getWordInventory();
		size = elemInvVec.size();
		for (j=0; j < size; j++) {
		    WordInventory elemInv = 
			(WordInventory) elemInvVec.elementAt(j);
		    returnInvVec.addElement (elemInv);
		}
	    } else {
		break;
	    }
	}
	
	return returnWord;
    }
}
