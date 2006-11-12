/*
 * @(#)HelpDoclet.java	1.18 06/10/30
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
 * @(#) HelpDoclet.java 1.18 - last change made 10/30/06
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
import com.sun.java.help.search.Indexer;
import java.io.*;
import java.util.*;
import java.util.Arrays;

/**
 * This class generates JavaHelp files from javadoc.
 *
 * @author Roger D. Brinkley
 * @version	1.18	10/30/06
 */

public class HelpDoclet extends Doclet {
    
    public static Configuration configuration;

    /**
     * The "start" method as required by Javadoc.
     *
     * @param Root
     * @see sun.tools.javadoc.Root
     * @return boolean
     */
    public static boolean start(RootDoc root) {
        try { 
            configuration().setOptions(root);

            (new HelpDoclet()).startGeneration(root);
        } catch (DocletAbortException exc) {
            return false; // message has already been displayed
        }
        return true;
    }

    /**
     * Return the configuration instance. Create if it doesn't exist.
     * Override this method to use a different
     * configuration.
     */
    public static Configuration configuration() {
        if (configuration == null) {
            configuration = new Configuration();
        }
        return configuration;
    }

    /**
     * Start the generation of files. Call generate methods in the individual
     * writers, which will in turn genrate the documentation files. Call the
     * TreeWriter generation first to ensure the Class Hierarchy is built
     * first and then can be used in the later generation.
     *
     * For new format.
     *
     * @see sun.tools.javadoc.RootDoc
     */
    protected void startGeneration(RootDoc root) throws DocletAbortException {
        ClassTree classtree = new ClassTree(root);
	IndexWriter index= new IndexWriter(root.specifiedPackages());
	// change ProjectWriter to HelpSetWriter
	ProjectWriter proj= new ProjectWriter(root.specifiedPackages());
	TocWriter toc=new TocWriter(root.specifiedPackages());
	ClassNavWriter classtoc=new ClassNavWriter(root.specifiedPackages());
	
	debug ("generateClassFiles");
	generateClassFiles(root, classtree);

	// Get the list of files from the helpset
	String[] filelist=proj.getFilelist();

	// create an index for the files
	Indexer indexer= new Indexer();
	try {
	    indexer.compile(filelist);
	} catch (java.lang.Exception e) {
	    e.printStackTrace();
	}

    }

    protected void generateClassFiles(RootDoc root, ClassTree classtree) 
                                      throws DocletAbortException {
        ClassDoc[] classes = root.specifiedClasses();
        List incl = new ArrayList();
        for (int i = 0; i < classes.length; i++) {
            ClassDoc cd = classes[i];
            if (cd.isIncluded()) {
                incl.add(cd);
            }
        }
        ClassDoc[] inclClasses = new ClassDoc[incl.size()];
        for (int i = 0; i < inclClasses.length; i++) {
            inclClasses[i] = (ClassDoc)incl.get(i);
        }
        generateClassCycle(inclClasses, classtree);
        PackageDoc[] packages = root.specifiedPackages();
        for (int i = 0; i < packages.length; i++) {
            PackageDoc pkg = packages[i];
            generateClassCycle(pkg.interfaces(), classtree);
            generateClassCycle(pkg.ordinaryClasses(), classtree);
            generateClassCycle(pkg.exceptions(), classtree);
            generateClassCycle(pkg.errors(), classtree);
        }
    }

    protected String classFileName(ClassDoc cd) {
        return cd.qualifiedName() + ".html";
    }

    /**
     * Instantiate ClassWriter for each Class within the ClassDoc[]
     * passed to it and generate Documentation for that.
     */
    protected void generateClassCycle(ClassDoc[] arr, ClassTree classtree) throws DocletAbortException {
	debug("starting generateClassCyle");
	Arrays.sort(arr, new ClassComparator());
        for(int i = 0; i < arr.length; i++) {
            String prev = (i == 0)? 
                          null:
                          classFileName(arr[i-1]);
            ClassDoc curr = arr[i];
            String next = (i+1 == arr.length)? 
                          null:
                          classFileName(arr[i+1]);

	    debug ("generating Class info");
            ClassWriter.generate(curr, classtree);


	    /*
	    debug ("generating Method info");
	    ExecutableMemberDoc[] methods=curr.constructors();	  

	    for (int j=0;j<methods.length;j++) {
		MethodWriter.generate(arr[i].qualifiedName()+"."+methods[j].name()+methods[j].signature().replace(' ','_')+".html",
				      methods[j]);
	    }

	    methods=curr.methods();

	    for (int j=0;j<methods.length;j++) {
		MethodWriter.generate(arr[i].qualifiedName()+"."+methods[j].name()+methods[j].signature().replace(' ','_')+".html",
				      methods[j]);
	    }
	    */

        }
	debug("ending generateClassCyle");
    }

    /**
     * Check for doclet added options here. 
     *
     * @return number of arguments to option. Zero return means
     * option not known.  Negative value means error occurred.
     */
    public static int optionLength(String option) {
        return configuration().optionLength(option);
    }

    /**
     * For printf debugging.
     */
    private static boolean debug = false;
    private static void debug(String str) {
        if (debug) {
            System.out.println("HelpDoclet: " + str);
        }
    }
}
