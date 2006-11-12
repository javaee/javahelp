/*
 * @(#)Doc.java	1.6 06/10/30
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
 * @(#) Doc.java 1.6 - last change made 10/30/06
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
 * @author	Roger D. Brinkley
 * @version	1.6	10/30/06
 */

import java.util.*;
import java.io.*;

public class Doc {

    /** The document title */
    protected String title;

    /** The document URL in string form */
    protected String url;

    /** The search weight of the document */
    protected int weight;

    /** The docID number */
    protected int docId;

    /**
     * Creates a Doc
     * @param title	The title of the document
     * @param url	The URL of the document is String format
     **/

    public Doc(String title, String url) {
	this.title = title;
	this.url = url;
    }

    public Doc(DataInputStream from) throws IOException {
	short i, j;
	StringBuffer buffer;
	char in;
	
	/* Get the title */
	buffer = new StringBuffer();
	while ((in=from.readChar()) != '\n') {
	    buffer.append(in);
	}
	title = new String(buffer);

	/* Get the url*/
	buffer = new StringBuffer();
	while ((in=from.readChar()) != '\n') {
	    buffer.append(in);
	}
	url = new String(buffer);

    }

    public void writeDoc (DataOutputStream to) throws IOException {
	int i, j;

	to.writeChars(title + '\n');
	// Make sure that all the slashes are Unix file separators not MSDOS
	url = url.replace('\\','/');
	to.writeChars(url + '\n');
    }

    /**
     * Gets the title.
     **/

    public String getTitle() { return title; }

    /**
     * Gets the url.
     **/

    public String getURLString() { return url; }

    /**
     * Gets the weight.
     **/

    public int getWeight() { return weight; }

    /**
     * Gets the docId.
     **/

    public int getDocId() { return docId; }

    /**
     * Sets the title.
     **/

    public void setTitle(String title) { this.title = title; }

    /**
     * Sets the url.
     **/

    public void setURLString(String url) { this.url = url; }
 
    /**
     * Sets the weight.
     **/

    public void setWeight(int weight) { this.weight = weight; }

    /**
     * Sets the docID.
     **/

    public void setDocId(int docId) { this.docId = docId; }

    /**
     * Add to the weight.
     **/

    public void addToWeight(int weight) { this.weight += weight; }

    /**
     * Resets the weight to 0.
     **/

    public void resetWeight() { this.weight = 0; }

    public String toString () {
	return "Doc: title=" + title + " url=" + url + " weight=" + weight;
    }
}
