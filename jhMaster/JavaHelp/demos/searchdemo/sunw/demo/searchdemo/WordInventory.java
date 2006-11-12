/*
 * @(#)WordInventory.java	1.6 06/10/30
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
 * @(#) WordInventory.java 1.6 - last change made 10/30/06
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
 * This class provides ...
 *
 *
 * @author	Roger D. Brinkley
 * @version	1.6	10/30/06
 *
 * @see Word
 */

import java.io.*;
import java.util.Vector;

public class WordInventory {
    
    /** The document id */
    protected short docId;

    /** The number of occurances of the word in the document */
    protected short count;

    /** The emphasis/weight of the occurances */
    protected short weight;

    /** An Vector of word positions in the document */
    protected Vector wordPos;

    /** The word I cam from */
    protected Word word;

    
    /**
     * Creates an initialised WordInventory.
     * @param doc	   the document index
     **/

    public WordInventory(short docId, short count, 
			 short weight, short pos, Word word) {
	this.docId = docId;
	this.count = count;
	this.weight = weight;
	wordPos = new Vector();
	wordPos.addElement (new Short(pos));
	this.word = word;
    }

    /**
     * Creates a WordInventory & copies info from a Word default inventory.
     * @param docId	   the document index
     * @param word	   the Word
     **/

    public WordInventory(short docId, Word word) {
	this.docId = docId;
	wordPos = new Vector();

	Vector wordInventory = word.getWordInventory();

	for (int i=0; i < wordInventory.size(); i++ ) {
	    WordInventory test = (WordInventory) wordInventory.elementAt(i);
	    if (test.getDocId() == -1) {
		this.count += test.count;
		this.weight += test.weight;
		Vector testWordPos = test.wordPos;
		for (int j=0; j < test.wordPos.size(); j++) {
		    wordPos.addElement (new Short(((Short)testWordPos.elementAt(j)).shortValue()));
		}
	    }
	}
    }

    /**
     * Creates an initialised WordInventory from a DataInputStream.
     * @param from 	     the data input stream to read
     * @exception IOException   if a stream read failure occurs
     **/

    public WordInventory(DataInputStream from, Word word) throws IOException {
	int j, size;

	this.docId = from.readShort();
	this.count = from.readShort();
	this.weight = from.readShort();
	size = (int) from.readShort();
	wordPos = new Vector(size);
	for (j = 0 ; j < size; j++) {
	    wordPos.addElement (new Short(from.readShort()));
	}
	this.word = word;
    }

    public void writeWordInventory (DataOutputStream to) throws IOException {
	int i, j;
	short size;

	to.writeShort(this.docId);
	to.writeShort(this.count);
	to.writeShort(this.weight);
	size = (short) wordPos.size();
	to.writeShort(size);
	for (i=0 ; i < size; i++) {
	    to.writeShort(((Short)wordPos.elementAt(i)).shortValue());
	}
    }

    /** Updates the inventory for a given word in this document */
    public void updateInventory (short count, short weight, short pos) {
	this.count += count;
	this.weight += weight;
	wordPos.addElement (new Short(pos));
    }

    /** Returns the document id */
    public short getDocId () { return docId; }

    /** Sets the document id */
    public void setDocId (short docId) { this.docId = docId; }
    
    /** Returns the weight */
    public short getWeight () { return weight; }

    /** Sets the document id */
    public String toString() {
	String out;
	out = new String ("  Inventory: docId=" + docId + " count=" + count + " weight=" + weight + " wordpos=");
	for (int i=0; i < wordPos.size(); i++) {
	    out = out.concat (((Short)wordPos.elementAt(i)).shortValue() + ",");
	}
	return out;
    }
	    
}
