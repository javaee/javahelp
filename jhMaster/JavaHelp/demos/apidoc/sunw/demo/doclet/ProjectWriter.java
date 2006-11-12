/*
 * @(#)ProjectWriter.java	1.25 06/10/30
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
 * @(#) ProjectWriter.java 1.25 - last change made 10/30/06
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
import java.util.Arrays;

/**
 * This class controls the printing of a project
 *
 * @author Roger D. Brinkley
 * @version	1.25	10/30/06
 */

public class ProjectWriter {

  
    public static Configuration configuration;

    Hashtable table;
    PrintWriter outHS;
    PrintWriter outMap;
    Vector filelist;

    public ProjectWriter(PackageDoc[] packages) {

	filelist=new Vector();

	filelist.addElement("-db");
	filelist.addElement(configuration.destDirName+"JavaHelpSearch");

	filelist.addElement("-sourcepath");
	filelist.addElement(configuration.destDirName);

	String dir = configuration.destDirName;

	try {
	    outHS = new PrintWriter(new FileOutputStream(dir+"api.hs"));
	    printHelpSet();
	    outHS.close();
	} catch (IOException e) {
	    System.err.println("Trouble creating HelpSet file");
	}


	try {
	    outMap = new PrintWriter(new FileOutputStream(dir+"Map.map"));

	    outMap.println("<?xml version='1.0' encoding='ISO-8859-1' standalone='yes' ?>");
	    outMap.println("<!DOCTYPE map");
	    outMap.println("  PUBLIC \"-//Sun Microsystems Inc.//DTD JavaHelp Map Version 1.0//EN\"");
	    outMap.println("         \"http://java.sun.com/products/javahelp/map_1_0.dtd\">");

	    outMap.println("<map version=\"1.0\">");

	    // Startwriting the Map file
	    outMap.println("\t<mapID target=\"intro\" url=\"intro.html\" />");

	    outMap.println("\t<mapID target=\"packageimg\" url=\"images/packageimg.gif\" />");
	    outMap.println("\t<mapID target=\"greenball\" url=\"images/greenball.gif\" />");
	    outMap.println("\t<mapID target=\"yellowball\" url=\"images/yellowball.gif\" />");
	    outMap.println("\t<mapID target=\"redball\" url=\"images/redball.gif\" />");
	    outMap.println("\t<mapID target=\"blueball\" url=\"images/blueball.gif\" />");
	    outMap.println("\t<mapID target=\"error\" url=\"images/error.gif\" />");
	    outMap.println("\t<mapID target=\"class\" url=\"images/class.gif\" />");
	    outMap.println("\t<mapID target=\"interface\" url=\"images/interface.gif\" />");
	    outMap.println("\t<mapID target=\"exception\" url=\"images/exception.gif\" />");

	    for (int i = 0; i < packages.length; ++i) {
		doPackage(packages[i]);
	    }
	    
	    outMap.println("</map>");
	    outMap.close();
	} catch (IOException e) {}

	System.out.println("Done Project");
    }

    // I think there is a method to create duplicates of a byte -- somewhere

    private final String indent="                  ";
    private void p(int l, String s) {
	outHS.println(indent.substring(0, l*2)+s);
    }

    private void printHelpSet() {
	p(0, "<?xml version='1.0' encoding='ISO-8859-1' ?>");
	p(0, "<!DOCTYPE helpset");
	p(0, "  PUBLIC \"-//Sun Microsystems Inc.//DTD JavaHelp HelpSet Version 1.0//EN\"");
	p(0, "         \"http://java.sun.com/products/javahelp/helpset_1_0.dtd\">");

	p(0, "<helpset version=\"1.0\">");

	p(1, "<title>Java API Reference</title>");

	p(1, "<maps>");
	p(2, "<homeID>intro</homeID>");
	p(2, "<mapref location=\"Map.map\"/>");
	p(1, "</maps>");

	p(1, "<view>");
	p(2, "<name>TOC</name>");
	p(2, "<label>Classes</label>");
	p(2, "<type>sunw.demo.classviewer.ClassViewerView</type>");
	p(2, "<data>apitoc.xml</data>");
	p(1, "</view>");

	p(1, "<view>");
	p(2, "<name>Index</name>");
	p(2, "<label>Index on Classes</label>");
	p(2, "<type>javax.help.IndexView</type>");
	p(2, "<data>apiindex.xml</data>");
	p(1, "</view>");

	p(1, "<view>");
	p(2, "<name>Search</name>");
	p(2, "<label>Search on Classes</label>");
	p(2, "<type>javax.help.SearchView</type>");
	p(2, "<data>JavaHelpSearch</data>");
	p(1, "</view>");
	    
	p(0, "</helpset>");
    }

    public String[] getFilelist() {
	String[] list=new String[filelist.size()];
	for (int i=0;i<filelist.size();i++) {
	    list[i]=(String)filelist.elementAt(i);
	}
	return list;
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
	filelist.addElement(cls.qualifiedName()+".html");
	outMap.println("\t<mapID target=\""+cls.qualifiedName()+"\" url=\""+
		    cls.qualifiedName()+".html\" />");	
	outMap.println("\t<mapID target=\"TOC"+cls.qualifiedName()+"\" url=\""+
		    cls.qualifiedName()+".xml\" />");	

	doFields(cls.qualifiedName(),cls.fields());
	doMethods(cls.qualifiedName(),cls.constructors());
	doMethods(cls.qualifiedName(),cls.methods());

    }


    void doFields(String classname, FieldDoc[] fields) {
	for (int i=0;i<fields.length;i++) {
	    outMap.println("\t<mapID target=\""+classname+"."+fields[i].name()+"\" url=\""+classname+".html#"+fields[i].name()+"\" />");
	}
    }

    void doMethods(String classname, ExecutableMemberDoc[] methods) {

	for (int i=0;i<methods.length;i++) {
	    outMap.println("\t<mapID target=\""+classname+"."+methods[i].name()+methods[i].signature().replace(' ','_')+"\" url=\""+classname+".html#"+methods[i].name()+methods[i].signature().replace(' ','_')+"\" />");

	}

    }


  
}

