/*
 * @(#)IndexWriter.java	1.11 06/10/30
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
 * @(#) IndexWriter.java 1.11 - last change made 10/30/06
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

/**
 * This class controls the printing of an Index
 *
 * @author Roger D. Brinkley
 * @author Eduardo Pelegri-Llopart
 * @version	1.11	10/30/06
 */

import com.sun.javadoc.*;
import java.io.*;
import java.util.*;
import java.util.Arrays;

public class IndexWriter {

  
    public static Configuration configuration;

    Hashtable table;

    public IndexWriter(PackageDoc[] packages) {

	

	table=new Hashtable();

	for (int i = 0; i < packages.length; ++i) {
            doPackage(packages[i]);
        }


	makeIndex();

	System.out.println("Done Index");


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

	    doClass(cls);
	}

    }

    
    void doClass(ClassDoc cls) {

	doFields(cls.qualifiedName(), cls.fields());
	doMethods(cls.qualifiedName(), cls.constructors());
	doMethods(cls.qualifiedName(), cls.methods());
    }


    void doFields(String classname,FieldDoc[] fields) {

	for (int i=0;i<fields.length;i++) {
	    
	    String name=fields[i].name();


	    if (table.containsKey(name)) {
		Vector vect=(Vector)table.get(name);
		if (!vect.contains(classname+"."+fields[i].name())) {
		    vect.addElement((classname+"."+fields[i].name()));
		    table.put(name,vect);
		}
	    }
	    else {
		Vector vect=new Vector();
		vect.addElement((fields[i].qualifiedName()));
		table.put(name,vect);
	    }

	}


    }

    void doMethods(String classname, ExecutableMemberDoc[] methods) {


	for (int i=0;i<methods.length;i++) {
	    
	    String name=methods[i].name();


	    if (table.containsKey(name)) {
		Vector vect=(Vector)table.get(name);
		if (!vect.contains(classname+"."+methods[i].name()+methods[i].signature())) {
		    vect.addElement((classname+"."+methods[i].name()+methods[i].signature()));
		    table.put(name,vect);
		}
	    }
	    else {
		Vector vect=new Vector();
		vect.addElement((classname+"."+methods[i].name()+methods[i].signature()));
		table.put(name,vect);
	    }

	}

    }


    void makeIndex() {

	try {

	    PrintWriter out=new PrintWriter(new FileOutputStream(configuration.destDirName+"apiindex.xml"));

	    out.println("<?xml version='1.0' encoding='ISO-8859-1' standalone='yes' ?>");
	    out.println("<index version=\"1.0\">");

	    Enumeration enum1=table.keys();

	    String[] keys=new String[table.size()];

	    int c=0;

	    while (enum1.hasMoreElements()) {
		keys[c++]=(String)enum1.nextElement();
	    }
	    
	   
	    

	    Arrays.sort(keys);
	    
	    for (int i=0;i<keys.length;i++) {
		
		Vector vect=(Vector)table.get((String)keys[i]);
		Object[] values=vect.toArray();

		//	Arrays.sort(values);
		indent(4,out);
		out.println("<indexitem image=packageimg.gif>"+(String)keys[i]);
		
		for (int j=0;j<values.length;j++) {
		    indent(8,out);
		    out.println("<indexitem image=greenball target="+((String)values[j]).replace(' ','_')+">"+((String)values[j]).replace(' ','_')+"</indexitem>");
		}
		
		indent(4,out);
		out.println("</indexitem>");
		
		
	    }
	    
	    

	    out.println("</index>");

	    out.close();

	}
	catch (IOException e) {
	    System.out.println(e);
	}
	    
	

    }


	void indent(int indent, PrintWriter out1) {
	    out1.print("                              ".substring(0, indent));
    }

}

