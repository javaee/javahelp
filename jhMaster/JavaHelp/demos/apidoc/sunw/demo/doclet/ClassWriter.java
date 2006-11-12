/*
 * @(#)ClassWriter.java	1.10 06/10/30
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
 * @(#) ClassWriter.java 1.10 - last change made 10/30/06
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
import java.io.*;
import java.lang.*;
import java.util.*;

/**
 * Generate the Class Information Page.
     * @see sun.tools.javadoc.ClassDoc 
     * @see java.util.Collections
     * @see java.util.List
     * @see java.util.ArrayList
     * @see java.util.HashMap
 *
 * @author Atul M Dambalkar
 * @author Robert Field
 * @author Roger D. Brinkley
 * @version	1.10	10/30/06
 */

public class ClassWriter extends HtmlWriter {
  
    private ClassDoc classdoc;

    protected ClassTree classtree;

    public ClassWriter(String filename,ClassDoc classd,
		       ClassTree classtree) throws IOException {
	super(filename);
        this.classdoc = classd;
	this.classtree = classtree;
    }

   
    /**
     * Generate a class page.
     *
     * @param classdoc the class to generate.
     * @param classtree the ClassTree
     */
    public static void generate(ClassDoc classdoc, ClassTree classtree) 
        throws DocletAbortException {
            ClassWriter clsgen;
            String filename = classdoc + ".html";
            try {
                clsgen = new ClassWriter(filename, classdoc, classtree);
                clsgen.generateClassFile();
                clsgen.close();
            } catch (IOException exc) {
                error("doclet.exception_encountered", exc.toString(), filename);
                throw new DocletAbortException();
            }
    }

    
    public void generateClassFile() {
        String label = getText(classdoc.isInterface()? 
                               "doclet.Interface" : 
                               "doclet.Class") + " " +
                               classdoc.qualifiedName();
        printHeader(label);
        hr();
        h1(label);

        // if this is a class (not an interface) then generate 
        // the super class tree.
        if (!classdoc.isInterface()) {
            pre();
            printTreeForClass(classdoc);
            preEnd();
        }

        printSubClassInterfaceInfo();
        
        if (classdoc.isInterface()) {
            printImplementingClasses();
        }

        hr();
        printClassDescription();
        printDeprecated();
        // generate documentation for the class.
        String comment = classdoc.commentText();
        if (comment.length() > 0) {
            print(comment);
            p();
        }
        // Print Information about all the tags here
        generateTagInfo(classdoc);
        hr();
        p();

	anchor("index");
	printAllMembers();
	hr();

	printFooter();
    }

    /*
     * This is derived from javadoc's ClassWriter.printAllMembers.
     * Striving for compatability so that when anchors are avialable
     * changing to a single file will be easier.
     *
     * No changes will be required here
     */
    protected void printAllMembers() {
	MethodSubWriter methW = new MethodSubWriter(this);
	ConstructorSubWriter consW = new ConstructorSubWriter(this);
        FieldSubWriter fieldW = new FieldSubWriter(this);

	fieldW.printMembers(classdoc);
	consW.printMembers(classdoc);
	methW.printMembers(classdoc);
    }


    /*** cl ***/
    protected void printClassDescription() {
        boolean isInterface = classdoc.isInterface();
        dl();
        dt();

        print(classdoc.modifiers() + " ");  

        if (!isInterface) {
            printText("doclet.class");
            print(' ');
        }
        bold(classdoc.name());

        if (!isInterface) {
            ClassDoc superclass = classdoc.superclass();
            if (superclass != null) {
                dt();
                printText("doclet.extends");
                print(' ');
                printClassLink(superclass);
            }
        }

        ClassDoc[] implIntfacs = classdoc.interfaces();
        if (implIntfacs != null && implIntfacs.length > 0) {
            dt();
            printText(isInterface? "doclet.extends" : "doclet.implements");
            print(' ');
            printClassLink(implIntfacs[0]);
            for (int i = 1; i < implIntfacs.length; i++) {
                print(", ");
                printClassLink(implIntfacs[i]);
            }
        }
        dlEnd();
    }
    
    protected void printDeprecated() {
        Tag[] deprs = classdoc.tags("deprecated");
        if (deprs.length > 0) {
            String text = deprs[0].text();
            bold(getText("doclet.Note_0_is_deprecated",  classdoc.name()));
            if (text.length() > 0) {
                italics(text);
            }
            p();
        }
    }

    protected void printStep(int indent) {
        String spc = spaces(8 * indent - 4);
        print(spc);
        println("|");
        print(spc);
        print("+----");
    }

    protected int printTreeForClass(ClassDoc type) {
        ClassDoc sup = type.superclass();
        int indent = 0;
        if (sup != null) {
            indent = printTreeForClass(sup);
            printStep(indent);
        }
        if (type.equals(classdoc)) {
            // don't want a link for us
            print(type.qualifiedName());
        } else {
            printQualifiedClassLink(type);
        }
        println();
        return indent + 1;
    }

    protected void printSubClassInterfaceInfo() {
        // Before using TreeBuilder.getSubClassList
        // make sure that tree.html is generated prior.
        List subclasses = classtree.subs(classdoc);
        if (subclasses.size() > 0) {
            printSubClassInfoHeader(subclasses);
            if (classdoc.isClass()) {
                bold(getText("doclet.Subclasses"));
            } else { // this is an interface
                bold(getText("doclet.Subinterfaces"));
            }
            printSubClassLinkInfo(subclasses);
        }
    }

    protected void printImplementingClasses() {
        List implcl = classtree.implementingclasses(classdoc);
        if (implcl.size() > 0) {
            printSubClassInfoHeader(implcl);
            bold(getText("doclet.Implementing_Classes"));
            printSubClassLinkInfo(implcl);
        }
    }


    protected void printSubClassInfoHeader(List list) {
        dl();
        dt();
    }

    protected void printSubClassLinkInfo(List list) {
        int i = 0;
        print(' ');
        dd();
        for (; i < list.size() - 1; i++) {
            printClassLink((ClassDoc)(list.get(i)));
            print(", ");
        }
        printClassLink((ClassDoc)(list.get(i)));
        ddEnd();
        dlEnd();
    }

    public String spaces(int len) {
        String space = "";

        for (int i = 0; i < len; i++) {
            space += " ";
        }
        return space;
    }
    

}




