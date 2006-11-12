/*
 * @(#)TocWriter.java	1.12 06/10/30
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
 * @(#) TocWriter.java 1.12 - last change made 10/30/06
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
//import java.util.Arrays;

/**
 * This class controls the printing of Table of Contents (TOC)
 *
 * @author Roger D. Brinkley
 * @version	1.12	10/30/06
 */

public class TocWriter {

  
    public static Configuration configuration;

    Hashtable table;
    PrintWriter out;

    public TocWriter(PackageDoc[] packages) {

	try {
	out=new PrintWriter(new FileOutputStream(configuration.destDirName+"apitoc.xml"));
	}
	catch (IOException e) {}
	
	out.println("<?xml version='1.0' encoding='ISO-8859-1' standalone='yes' ?>");
	out.println("<toc version=\"1.0\">");

	table=new Hashtable();

	for (int i = 0; i < packages.length; ++i) {
            doPackage(packages[i]);
        }

	out.println("</toc>");

	out.close();

	System.out.println("Done TOC");


    }
    

    void doPackage(PackageDoc pkg) {

	indent(4);
	out.println("<tocitem image=packageimg text=\""+pkg.name()+"\" >");

	doClasses(pkg.interfaces());
	doClasses(pkg.errors());
	doClasses(pkg.exceptions());
	doClasses(pkg.ordinaryClasses());

	indent(4);
	out.println("</tocitem>");

    }
  

    void doClasses(ClassDoc[] classes) {
	
	Arrays.sort(classes,new ClassComparator());

	for (int i = 0; i < classes.length; ++i) {
            ClassDoc cls = classes[i];	    
	    indent(8);
	    
	    String image;

	    if (cls.isError())
		image="error";
	    else if (cls.isException())
		image="exception";
	    else if (cls.isInterface())
		image="interface";
	    else image="class";
	    
	    out.println("<tocitem target=\"TOC" +cls.qualifiedName()+ "\" image=\"" +image+ "\" text=\"" +cls.name()+ "\" />");

	    //	    MethodDoc[] methods=cls.constructors();
	    //	    doFields(cls.fields(),cls.qualifiedName());
	    
	    //	    MethodDoc[] methods=cls.constructors();
	    //   doMethods(cls.constructors(),cls.qualifiedName());

	    //	    methods=cls.methods();
	    //	    doMethods(cls.methods(),cls.qualifiedName());
	  
	}

    }

    void doMethods(MethodDoc[] methods,String classname) {

	Arrays.sort(methods,new MethodComparator());
	
	String image;

	for (int i=0;i<methods.length;i++) {	   

	    if (methods[i].isConstructor()) 
		image="yellowball";
	    else if (methods[i].isStatic())
		image="greenball";
	    else image="redball";

	    indent(12);
	    out.println("<tocitem target=\"" +classname+ "." + methods[i].name() + methods[i].signature().replace(' ','_') + "\" image=\"" +image+ "\" text=\"" +methods[i].name() + methods[i].flatSignature() + "\" />");
	}
    }

    void doFields(FieldDoc[] fields,String classname) {

	Arrays.sort(fields,new MethodComparator());
	
	String image;

	for (int i=0;i<fields.length;i++) {	   

	    indent(12);
	    out.println("<tocitem target=\"" +classname+ "." +fields[i].name() + "\" image=\"blueball\" text=\"" + fields[i].name() + "\" />");
	}
    }

 
    void indent(int indent) {
	    out.print("                              ".substring(0, indent));
    }

}

class MethodComparator implements Comparator {

    public int compare(Object o1, Object o2) {
        return (((MemberDoc)o1).name())
            .compareTo(((MemberDoc)o2).name());
    }
}
