/*
 * @(#)ClassNavWriter.java	1.12 06/10/30
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
 * @(#) ClassNavWriter.java 1.12 - last change made 10/30/06
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
import java.util.*;

/**
 * This class writes out all the documentation for a class
 *
 * @author Roger D. Brinkley
 * @version	1.12	10/30/06
 */

public class ClassNavWriter {

  
    public static Configuration configuration;

    Hashtable table;
    PrintWriter out;

    public ClassNavWriter(PackageDoc[] packages) {
	for (int i = 0; i < packages.length; ++i) {
            doPackage(packages[i]);
        }
    }
    

    void doPackage(PackageDoc pkg) {
	doClasses(pkg.interfaces());
	doClasses(pkg.errors());
	doClasses(pkg.exceptions());
	doClasses(pkg.ordinaryClasses());
    }
  

    void doClasses(ClassDoc[] classes) {
	for (int i = 0; i < classes.length; ++i) {
            ClassDoc cls = classes[i];	    

	    try {
		out=new PrintWriter(new FileOutputStream(configuration.destDirName+cls.qualifiedName()+".xml"));
	    }
	    catch (IOException e) {}

	    out.println("<?xml version='1.0' encoding='ISO-8859-1' standalone='yes' ?>");
	    out.println("<class version=\"1.0\" text=\"" + cls.qualifiedName() + "\" >");

	    indent(1);
	    out.println("<description target=\"" + cls.qualifiedName() + "\" />");
	    doFields(cls.fields(),cls.qualifiedName());
	    doConstructors(cls.constructors(),cls.qualifiedName());
	    doMethods(cls.methods(),cls.qualifiedName());
	    out.println("</class>");

	    out.close();
	}
    }

    void doConstructors(ExecutableMemberDoc[] methods,String classname) {
	if (methods.length <= 0) {
	    return;
	}

	indent(1);
	out.println("<constructors>");

	for (int i=0;i<methods.length;i++) { 
	    indent(2);
	    out.println("<constructor target=\"" + classname+"."+methods[i].name()+methods[i].signature().replace(' ','_') + "\" text=\"" + methods[i].name()+methods[i].flatSignature() + "\" />");
	}

	indent(1);
	out.println("</constructors>");
    }  

    void doMethods(ExecutableMemberDoc[] methods,String classname) {
	if (methods.length <= 0) {
	    return;
	}

	indent(1);
	out.println("<methods>");


	for (int i=0;i<methods.length;i++) { 
	    indent(2);
	    out.println("<method target=\"" + classname+"."+methods[i].name()+methods[i].signature().replace(' ','_') + "\" text=\"" + methods[i].name()+methods[i].flatSignature() +"\" />");
	}

	indent(1);
	out.println("</methods>");
    }  

    void doFields(FieldDoc[] methods,String classname) {
	if (methods.length <= 0) {
	    return;
	}

	indent(1);
	out.println("<fields>");

	for (int i=0;i<methods.length;i++) {
	    indent(2);
	    out.println("<field target=\""+ classname+"."+methods[i].name() +"\" text=\""+ methods[i].name() + "\" />");
	}

	indent(1);
	out.println("</fields>");
    }  
 
    void indent(int indent) {
	int max = indent/2;
	for (int i=0; i<max; i++) {
	    out.print("\t");
	}
	if (indent != max*2) {
	    out.print("    ");
	}
    }

}
