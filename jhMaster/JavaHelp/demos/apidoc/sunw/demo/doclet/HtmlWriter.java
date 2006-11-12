/*
 * @(#)HtmlWriter.java	1.9 06/10/30
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
 * @(#) HtmlWriter.java 1.9 - last change made 10/30/06
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
import java.text.MessageFormat;

/**
 * This class controls the print of HTML documents.
 *
 * @author Roger D. Brinkley
 * @version	1.9	10/30/06
 */

public class HtmlWriter extends PrintWriter {


    public static Configuration configuration;

    private static ResourceBundle messageRB;

    /**
     * Constructor, initializes PrintWriter with the FileWriter.
     *
     * @param filename File Name to which the PrintWriter will do the Output.
     * @exception IOException Exception raised by the FileWriter is passed on
     * to next level.
     */
    public HtmlWriter(String filename) throws IOException, UnsupportedEncodingException {

	super(genWriter(configuration.destDirName + filename, configuration.docencoding));
       
    }

    static Writer genWriter(String filename, String docencoding) 
                       throws IOException, UnsupportedEncodingException {
        FileOutputStream fos = new FileOutputStream(filename);
        if (docencoding == null) {
            return new OutputStreamWriter(fos);
        } else {
            return new OutputStreamWriter(fos, docencoding);
        }
    }

    public void html() {
        println("<html>");
    }

    public void htmlEnd() {
        println("</html>");
    }

    public void body() {
        println("<body>");
    }
    
    public void body(String bgcolor) {
        println("<body bgcolor=\"" + bgcolor + "\">");
    }

    public void bodyEnd() {
        println("</body>");
    }

    public void title() {
        println("<title>");
    }
    
    public void titleEnd() {
        println("</title>");
    }

    public void ul() {
        println("<ul>");
    }

    public void ulEnd() {
        println("</ul>");
    }

    public void li() {
        print("<li>");
    }

    public void li(String type) {
        print("<li type=" + type + ">");
    }

    public void h1() {
        println("<h1>");
    }

    public void h1End() {
        println("</h1>");
    }

    public void h1(String text) {
        h1();
        println(text);
        h1End();
    }

    public void h2() {
        println("<h2>");
    }

    public void h2(String text) {
        h2();
        println(text);
        h2End();
    }

    public void h2End() {
        println("</h2>");
    }

    public void h3() {
        println("<h3>");
    }

    public void h3(String text) {
        h3();
        println(text);
        h3End();
    }

    public void h3End() {
        println("</h3>");
    }

    public void h4() {
        println("<h4>");
    }

    public void h4End() {
        println("</h4>");
    }

    public void h4(String text) {
        h4();
        println(text);
        h4End();
    }

    public void h5() {
        println("<h5>");
    }

    public void h5End() {
        println("</h5>");
    }

    public void img(String imggif, String imgname, int width, int height) {
        println("<img src=\"images/" + imggif + ".gif\"" 
              + " width=" + width + " height=" + height 
              + " alt=\"" + imgname + "\">");
    }

    public void menu() {
        println("<menu>");
    }

    public void menuEnd() {
        println("</menu>");
    }

    public void pre() {
        println("<pre>");
    }

    public void preEnd() {
        println("</pre>");
    }
    
    public void hr() {
        println("<hr>");
    }

    public void hr(int size, int widthPercent) {
        println("<hr size=" + size + " width=\"" + widthPercent + "%\">");
    }

    public void bold() {
        print("<b>");
    }

    public void boldEnd() {
        print("</b>");
    }

    public void bold(String text) {
        bold();
        print(text);
        boldEnd();
    }
    
    public void italics(String text) {
        print("<i>");
        print(text);
        println("</i>");
    }

    public void space() {
        print("&nbsp;");
    }
        
    public void dl() {
        println("<dl>");
    }

    public void dlEnd() {
        println("</dl>");
    }
    
    public void dt() {
        print("<dt>");
    }

    public void dd() {
        print("<dd>");
    }

    public void ddEnd() {
        println("</dd>");
    }

    public void sup() {
        println("<sup>");
    }

    public void supEnd() {
        println("</sup>");
    }
    
    public void font(String size) {
        println("<font size=\"" + size + "\">");
    }

    public void fontEnd() {
        println("</font>");
    }
   
    public void fontColor(String color) {
        println("<font color=\"" + color + "\">");
    }
 
    public void center() {
        println("<center>");
    }

    public void centerEnd() {
        println("</center>");
    }
    
    public void aName(String name) {
        print("<a name=\"" + name + "\">");
    }

    public void aEnd() {
        print("</a>");
    }

    public void anchor(String name, String content) {
        aName(name);
        print(content);
        aEnd();
    }

    public void anchor(String name) {
        aName(name);
        aEnd();
    }

    public void p() {
        println();
        println("<p>");
    }

    public void br() {
        println();
        println("<br>");
    }

    public void address() {
        println("<address>");
    }

    public void addressEnd() {
        println("</address>");
    }
    
    public void head() {
        println("<head>");
    }

    public void headEnd() {
        println("</head>");
    }
    
    public void code() {
        println("<code>");
    }

    public void codeEnd() {
        println("</code>");
    }
    
    public void em() {
        println("<em>");
    }

    public void emEnd() {
        println("</em>");
    }
    
    public void tr() {
        println("<tr>");
    }

    public void trEnd() {
        println("</tr>");
    }
   
    public void td() {
        print("<td>");
    }

    public void tdEnd() {
        println("</td>");
    }
 
    public void trBgcolor(String color) {
        println("<tr BGCOLOR=\"" + color + "\">");
    }

    public void tdColspan(int i) {
        print("<td colspan=" + i + ">");
    }
        
    public void tdAlign(String str) {
        print("<td align=" + str + ">");
    }

    public void tdAlignRowspan(String str, int span) {
        print("<td align=" + str + " rowspan=" + span + ">");
    }

/*
 * The remaining  methods are from HtmlDocWriter
 */
    public void printHyperLink(String link, String tag) {
        printHyperLink(link, null, tag);
    }



    public void printHyperLink(String link, String where, String tag) {
        print("<a href=\"" + link);
        if (where != null) {
            print("." + where+".html");
        }
        print("\">" + tag + "</a>");
    }



    /**
     * Print Class Link with the generated file name with position.
     */
    public void printClassLink(ClassDoc cd, String where, String tag) {
        if (configuration.linkall || cd.isIncluded()) {
            printHyperLink(cd.qualifiedName() + ".html", where, tag);
        } else {
            print(tag);
        }
    }
    
    /**
     * Print Class Link with the generated file name without position.
     */
    public void printClassLink(ClassDoc cd, String tag) {
        printClassLink(cd, null, tag);
    }
   
    /**
     * Print Class link.
     */ 
    public void printClassLink(ClassDoc cd) {
        printClassLink(cd, cd.isIncluded()? cd.name(): cd.qualifiedName());
    }

    /**
     * Print Class link, with tag as qualified name.
     */
    public void printQualifiedClassLink(ClassDoc cd) {
        printClassLink(cd, cd.qualifiedName());
    }

    /**
     * Print Class link, with only class name as the link and prefixing
     * plain package name.
     */
    public void printPreQualifiedClassLink(ClassDoc cd) {
        String pkgName = cd.containingPackage().name();
	if (pkgName.length() > 0) {
            print(pkgName);
            print('.');
        }
        printClassLink(cd, cd.name());
    }

    public void printMemberHyperLink(String link, String tag) {
        printHyperLink(link, "<b>" + tag + "</b>");
    }



    public void printHeader(String title) {
        println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 3.2//EN\">");
        println("<!--NewPage-->");
        html();
        head();
        print("<!-- Generated by javadoc on ");
        println(today());
        println("-->");
        title();
        println(title);
        titleEnd();
        headEnd();
        body();       
        println();
    }


    public void printFooter() {
        p();
        bodyEnd();
        htmlEnd();
    }
    
    public void printImage(String imggif, String imgname, 
                             int width, int height) {
        img(imggif, imgname, width, height);
    }


    protected void printSeeTags(Doc doc) {
       SeeTag[] sees = doc.seeTags();
       if (sees.length > 0) {
           dt();
           bold(getText("doclet.See_Also"));
           dd();
           for (int i = 0; i < sees.length; ++i) {
               SeeTag see = sees[i];
               ClassDoc refClass = see.referencedClass();
               MemberDoc refMem = see.referencedMember();
               String refMemName = see.referencedMemberName();
               if (i > 0) {
                   print(", ");
               }
               if (refClass == null) {
                   // nothing to link to, just use text
                   print(see.text());
               } else if (refMemName == null) {
                   // class reference
                   printClassLink(refClass);
               } else if (refMem == null) {
                   // can't find the member reference
                   print(see.text());
	       } else if (refMem instanceof ExecutableMemberDoc) {
		   // executable member reference
		   printClassLink(refClass,
                                  refMem.name()+((ExecutableMemberDoc)refMem).signature(),
                                  refMemName);
               } else {
                   // member reference
                   printClassLink(refClass,
                                  refMemName, refMem.name());
               }
           }
       }
    }       


    public void printText(String key) {
        print(getText(key));
    }

    public void boldText(String key) {
        bold(getText(key));
    }

    /**
     * Print tag information
     */
    protected void generateTagInfo(Doc doc) {
        Tag[] sinces = doc.tags("since");
        Tag[] sees = doc.seeTags();
        Tag[] authors;
        Tag[] versions;
        if (configuration.showAuthor) {
            authors = doc.tags("author");
        } else {
            authors = new Tag[0];
        }            
        if (configuration.showVersion) {
            versions = doc.tags("version");
        } else {
            versions = new Tag[0];
        }
        if (sinces.length > 0 
            || sees.length > 0
            || authors.length > 0
            || versions.length > 0 ) {
            dl();
            if (sinces.length > 0) {
                // There is going to be only one Since tag.
                dt();
                boldText("doclet.Since");
                print(' ');
                dd();
                println(sinces[0].text());
                ddEnd();
            }
            if (versions.length > 0) {
                // There is going to be only one Version tag.
                dt();
                boldText("doclet.Version");
                print(' ');
                dd();
                println(versions[0].text());
                ddEnd();
            }
            for (int i = 0; i < authors.length; ++i) {
                dt();
                boldText("doclet.Author");
                print(' ');
                dd();
                println(authors[i].text());
                ddEnd();
            }
            printSeeTags(doc);
            dlEnd();
        }
    }

    public String today() {
        if (configuration.nodate) {
            return "TODAY";
        }
        Calendar calendar = new GregorianCalendar(TimeZone.getDefault());
        return calendar.getTime().toString();
    }

   /**
     * Initialize ResourceBundle
     */
    static void initResource() {
	try {
	    messageRB =
		ResourceBundle.getBundle("sunw.demo.doclet.resources.doclet");
	} catch (MissingResourceException e) {
	    throw new Error("Fatal: Resource for javadoc doclets is missing");
	}
    }
    

    /**
     * get and format message string from resource
     *
     * @param key selects message from resource
     */
    public static String getText(String key) {
        return getText(key, (String)null);
    }

    /**
     * get and format message string from resource
     *
     * @param key selects message from resource
     * @param a1 first argument
     */
    public static String getText(String key, String a1) {
        return getText(key, a1, null);
    }

    /**
     * get and format message string from resource
     *
     * @param key selects message from resource
     * @param a1 first argument
     * @param a2 second argument
     */
    public static String getText(String key, String a1, String a2) {
        return getText(key, a1, a2, null);
    }

    /**
     * get and format message string from resource
     *
     * @param key selects message from resource
     * @param a1 first argument
     * @param a2 second argument
     * @param a3 third argument
     */
    public static String getText(String key, String a1, String a2, String a3) {
	if (messageRB == null) {
	    initResource();
	}
	try {
	    String message = messageRB.getString(key);
	    String[] args = new String[3];
	    args[0] = a1;
	    args[1] = a2;
	    args[2] = a3;
	    return MessageFormat.format(message, (java.lang.Object[]) args);
	} catch (MissingResourceException e) {
	    throw new Error("Fatal: Resource for javadoc is broken. There is no " + key + " key in resource.");
	}
    }

    /**
     * Print error message, increment error count.
     *
     * @param key selects message from resource
     * @param a1 first argument
     */
    public static void error(String key, String a1) { 
	HelpDoclet.configuration().reporter.printError(getText(key, a1)); 
    }

    /**
     * Print error message, increment error count.
     *
     * @param key selects message from resource
     * @param a1 first argument
     * @param a2 second argument
     */
    public static void error(String key, String a1, String a2) { 
        HelpDoclet.configuration().reporter.printError(getText(key, a1, a2)); 
    }

    /**
     * Print a message.
     *
     * @param key selects message from resource
     */
    public static void notice(String key) { 
	HelpDoclet.configuration().reporter.printNotice(getText(key)); 
    }

     /** 
     * Just sufficient to take care of the 1.1 output format
     * Can be modified further
     */ 
    public void table() {
        println("<table border=\"0\" width=\"100%\">");
    } 
    
    public void tableEnd() {
        println("</table>");
    }


    public void tableIndexDetail() {
        println("<table border=\"1\" cellpadding=\"3\" " + 
                "cellspacing=\"0\" width=100%>");
    }

    public void tdIndex() {
        print("<td align=right valign=top width=1%>");
    }

    public void tableHeaderStart(String color, int span) {
        trBgcolor(color);
        tdColspan(span);
        font("+2");
    }

    public void tableHeaderEnd() {
        fontEnd();
        tdEnd();
        trEnd();
    }


/*
 * Originally in SubWriterHolderWriter
 */

    public void printTypeSummaryHeader() {
        tdIndex();
        font("-1");
    }

    public void printTypeSummaryFooter() {
        fontEnd();
        tdEnd();
    }

    public void printTableHeadingBackground(String str) {
        tableIndexDetail();
        tableHeaderStart("#CCFFCC", 1);
        bold(str);
        tableHeaderEnd();
        tableEnd();
    }
 
    /**
     * For printf debugging.
     */
    private static boolean debug = false;
    private static void debug(String str) {
        if (debug) {
            System.out.println("HelpWriter: " + str);
        }
    }
}
