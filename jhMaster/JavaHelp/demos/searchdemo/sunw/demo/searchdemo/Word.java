/*
 * @(#)Word.java	1.6 06/10/30
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
 * @(#) Word.java 1.6 - last change made 10/30/06
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
 * This class ...
 *
 * @author	Roger D. Brinkley
 * @version	1.6	10/30/06
 *
 * @see WordInventory
 * @see WordHashTable;
 */

import java.util.*;
import java.io.*;

public class Word {

    /** The word. */
    protected String word;

    /** The word Inventory, a Vector of WordInventory objects.
     * @see WordInventory 
     */
    protected Vector wordInventory;

    /**
     * Creates a Word
     * @param word  the word
     **/

    public Word(String word) {
	this.word = word;
	wordInventory = new Vector();
    }

    public Word(DataInputStream from) throws IOException {
	int size, j;
	StringBuffer buffer;
	char in;
	
	/* Get the word */
	buffer = new StringBuffer();
	while ((in=from.readChar()) != 0) {
	    buffer.append(in);
	}
	word = new String(buffer);

	/* Get the # of word inventories and the inventories */
	size = from.readShort();
	wordInventory = new Vector(size);
	for (j = 0 ; j < size ; j++) {
	    wordInventory.addElement (new WordInventory(from, this));
	}
    }

    public void writeWord (DataOutputStream to) throws IOException {
	int i, j;

	to.writeChars(word);
	to.writeChar(0);
	to.writeShort(wordInventory.size());
	for (i=0 ; i < wordInventory.size(); i++) {
	    ((WordInventory)wordInventory.elementAt(i)).writeWordInventory(to);
	}
    }

    /**
     * Augments an inventory reference.
     * @param docId	the document id
     * @param count     the word count
     * @param weight	the emphasis of the word
     * @param pos	the position of the word
     * @see WordInventory
     **/

    public void augmentWordInventory(short docId, short count,
				     short weight, short pos) {
	for (int i=0; i < wordInventory.size(); i++) {
	    WordInventory test = (WordInventory) wordInventory.elementAt(i);
	    if (test.getDocId() == docId) {
		test.updateInventory(count, weight, pos);
		return;
	    }
	}
	wordInventory.addElement(new WordInventory(docId, count, 
						   weight, pos, this));
    }

    /**
     * Copies an inventory reference from a Word.
     * @param docId	the document id
     * @param word	the Word to copy from
     * @see WordInventory
     **/

    public void addWordInventory(short docId, Word word) {
	wordInventory.addElement(new WordInventory (docId, word));
    }

    /**
     * Gets the word.
     **/

    public String getWord() { return word; }

    /**
     * Gets the word inventory.
     **/

    public Vector getWordInventory() { return wordInventory; }

  
    public Vector AndIntersection (Vector wordElemDocs) {
	Vector returnvec = new Vector();

	// Loop through the WordInventory 
	// compare the docId to each WordInventory in this class
	// If a match is found, add the tested WordInventory to the return
	// vector's list.
	for (int i=0; i < wordElemDocs.size() ; i++) {
	    WordInventory wordInv = (WordInventory) wordElemDocs.elementAt(i);
	    for (int j=0; j < wordInventory.size(); j++) {
		WordInventory test = (WordInventory) wordInventory.elementAt(j);
		if (test.docId == wordInv.docId) {
		    returnvec.addElement (test);
		    returnvec.addElement (wordInv);
		    break;
		}
	    }
	}
	return returnvec;
    }

    public Vector OrIntersection (Vector wordElemDocs) {
	Vector returnvec = new Vector();

	// Add all the element in wordElemDocs then add all the elements in 
	// in the WordInventory 
	for (int i=0; i < wordElemDocs.size() ; i++) {
	    WordInventory wordInv = (WordInventory) wordElemDocs.elementAt(i);
	    returnvec.addElement (wordInv);

	}
	for (int j=0; j < wordInventory.size(); j++) {
	    WordInventory test = (WordInventory) wordInventory.elementAt(j);
	    returnvec.addElement (test);
	}
	return returnvec;
    }

    public Vector NotIntersection (Vector wordElemDocs) {
        boolean addit;
  	Vector returnvec = new Vector();

	// Loop through the WordInventory 
	// compare the docId to each WordInventory in this class
	// If a match is found, add the tested WordInventory to the return
	// vector's list.
	for (int i=0; i < wordElemDocs.size() ; i++) {
	    WordInventory wordInv = (WordInventory) wordElemDocs.elementAt(i);
	    addit = true;
	    for (int j=0; j < wordInventory.size(); j++) {
		WordInventory test = (WordInventory) wordInventory.elementAt(j);
		if (test.docId == wordInv.docId) {
		    addit = false;
		    break;
		}
	    }
	    if (addit) {
		returnvec.addElement (wordInv);
	    }
	}
	return returnvec;
    }
  
    public Vector NearWord (Vector wordElemDocs, int proximity) {
	int k1, k2;
	Vector returnvec = new Vector();

	// loop through each of the wordElem's WordInventories
	for (int i=0; i < wordElemDocs.size() ; i++) {
	    WordInventory wordInv = (WordInventory) wordElemDocs.elementAt(i);
	    // loop through each of this words' wordInventories
	    for (int j=0; j < wordInventory.size(); j++) {
		WordInventory test = (WordInventory) wordInventory.elementAt(j);
		// if the docId are the same
		// loop through the various positions and see if the words are
		// in proximity to each other. Always take out the word at the
		// smaller position when your looping.
		//
		// Upon finding a match add the test WordInventory to the list
		// of documents where the words were in the given proximity
		if (test.docId == wordInv.docId) {
		    k1 = k2 = 0;
		    while (true) {
			int k1int = ((Short)test.wordPos.elementAt(k1)).intValue();
			int k2int = ((Short)wordInv.wordPos.elementAt(k2)).intValue();
			if (Math.abs(k1int - k2int) <= proximity) {
			    returnvec.addElement (test);
			    returnvec.addElement (wordInv);
			    break;
			}
			if (Math.min(k1int, k2int) == k1int) {
			    k1 ++;
			    if (test.wordPos.size() == k1) {
				break;
			    }
			} else {
			    k2 ++;
			    if (wordInv.wordPos.size() == k2) {
				break;
			    }
			}
		    }
		}
	    }
	}
	return returnvec;
    }

    public String toString () {
	String out;
	out = new String ("Word: " + word);
	for (int i=0; i < wordInventory.size(); i ++) {
	    out = out.concat (((WordInventory)wordInventory.elementAt(i)).toString());
	}
	return out;
    }
}
