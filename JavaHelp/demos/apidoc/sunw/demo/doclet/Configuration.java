/*
 * @(#)Configuration.java	1.7 06/10/30
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
 * @(#) Configuration.java 1.7 - last change made 10/30/06
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

package sunw.demo.doclet;

import com.sun.javadoc.*;
import java.util.*;
import java.io.*;

/**
 * Configure the output based on the options.
 *
 * @author Robert Field
 * @author Roger Brinkley
 * @version	1.7	10/30/06
 */

public class Configuration {
    public String destDirName = "";
    public String docencoding = null;
    public String encoding = null;
    public String releaseName = "";
    public String javaPackagesHeader = "";
    public String otherPackagesHeader = "";
    public String footer = "";
    public String sourcePath = null;
    public boolean showAuthor = false;
    public boolean showVersion = false;
    public boolean linkall = false;
    public boolean breakIndex = false;
    public boolean createIndex = true;
    public boolean createTree = true;
    public boolean usersGuideLink = false; // User's guide link in package index
    public boolean oneOne = false;         // backwards compatible with JDK 1.1
    public boolean nodate = false;         // don't put dates in header
    public PackageDoc[] packages;
    public DocErrorReporter reporter = null;

    public Configuration() {
    }

    public void setOptions(RootDoc root) throws DocletAbortException {
        String[][] options = root.options();
        packages = root.specifiedPackages();
	reporter = root;	// epll
        for (int oi = 0; oi < options.length; ++oi) {
            String[] os = options[oi];
            String opt = os[0].toLowerCase();
            if (opt.equals("-d")) {
                String dd = os[1];
                File destDir = new File(dd);
                destDirName = addTrailingFileSep(dd);
                if (!destDir.exists()) {
		    if (!destDir.mkdirs()) {
			HtmlWriter.error("doclet.destination_directory_not_found_0", 
					 destDir.getPath());
			throw new DocletAbortException();
		    }
                }
            } else  if (opt.equals("-docencoding")) {
                docencoding = os[1];
            } else  if (opt.equals("-encoding")) {
                encoding = os[1];
            } else  if (opt.equals("-footer")) {
                footer =  os[1];
            } else  if (opt.equals("-author")) {
                showAuthor = true;
            } else  if (opt.equals("-version")) {
                showVersion = true;
            } else  if (opt.equals("-linkall")) {
                linkall = true;
            } else  if (opt.equals("-breakindex")) {
                breakIndex = true;
            } else  if (opt.equals("-noindex")) {
                createIndex = false;
            } else  if (opt.equals("-notree")) {
                createTree = false;
            } else  if (opt.equals("-xrelease")) {
                releaseName = os[1];
            } else  if (opt.equals("-xjavapackagesheader")) {
                javaPackagesHeader = os[1];
            } else  if (opt.equals("-xotherpackagesheader")) {
                otherPackagesHeader = os[1];
            } else  if (opt.equals("-xusersguidelink")) {
                usersGuideLink = true;
            } else  if (opt.equals("-x1.1")) {
                oneOne = true;
            } else  if (opt.equals("-xnodate")) {
                nodate = true;
            } else  if (opt.equals("-sourcepath") 
                        || (opt.equals("-classpath"))) {
                sourcePath = os[1];
            }
        }
        if (docencoding == null) {
            docencoding = encoding;
        }
            
        // hook configuration to writers
        ClassWriter.configuration = this;
	IndexWriter.configuration = this;
	TocWriter.configuration = this;
	ClassNavWriter.configuration = this;
	ProjectWriter.configuration = this;
    }

    String addTrailingFileSep(String path) {
	String fs = System.getProperty("file.separator");
	if (!path.endsWith(fs))
	    path += fs;
	
	return path;
    }

    /**
     * Check for doclet added options here. 
     *
     * @return number of arguments to option. Zero return means
     * option not known.  Negative value means error occurred.
     */
    public int optionLength(String option) {
        option = option.toLowerCase();
        if (option.equals("-version") ||
            option.equals("-author") ||
            option.equals("-nodeprecated") ||
            option.equals("-noindex") ||
            option.equals("-notree") ||
            option.equals("-linkall") ||
            option.equals("-breakindex") ||
            option.equals("-xusersguidelink") ||
            option.equals("-x1.1") ||
            option.equals("-xnodate") 
                                     ) {
            return 1;
        } else if (option.equals("-help") ) {
            HtmlWriter.notice("doclet.usage");
            return 1;
        } else if (option.equals("-x") ) {
            HtmlWriter.notice("doclet.xusage");
            return -1; // so run will end
        } else if (option.equals("-docencoding") ||
                   option.equals("-footer") ||
                   option.equals("-xrelease") ||
                   option.equals("-xjavapackagesheader") ||
                   option.equals("-xotherpackagesheader") ||
                   option.equals("-d") ) {
            return 2;
        } else {
            return 0;
        }
    }

}
        

