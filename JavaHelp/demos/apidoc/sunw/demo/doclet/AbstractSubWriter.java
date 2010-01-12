/*
 * @(#)AbstractSubWriter.java	1.6 06/10/30
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
 * @(#) AbstractSubWriter.java 1.6 - last change made 10/30/06
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
import java.lang.reflect.Modifier;

/**
 * This class is derived from javadoc AbstractSubWriter.
 * The intent is to make it as close to a subWriter as possible
 * so that when anchors are supported in the JFC viewer this
 * could be switched to a subwriter.
 *
 * @author Roger D. Brinkley
 * @version	1.6	10/30/06
 */

public abstract class AbstractSubWriter {

    protected HtmlWriter writer;

    /**
     * temp var.
     *
     * track how long the displayed (non-html) contents are.
     */
    protected int displayLength;

    AbstractSubWriter(HtmlWriter writer) {
	this.writer = writer;
    }

    /*** abstracts ***/

    public abstract ProgramElementDoc[] members(ClassDoc cd);

    protected abstract void printHeader(ClassDoc cd);

    protected abstract void printFooter(ClassDoc cd);

    protected abstract void printMember(ClassDoc cd, ProgramElementDoc elem);

    protected abstract void printDeprecatedLink(ProgramElementDoc member);

    /***  ***/

    protected void print(String str) {
        writer.print(str);
        displayLength += str.length();
    }

    protected void print(char ch) {
        writer.print(ch);
        displayLength++;
    }

    protected void bold(String str) {
        writer.bold(str);
        displayLength += str.length();
    }

    /**
     * Class link
     */
    protected void printClassLink(ClassDoc cd) {
        writer.printClassLink(cd);
        displayLength += cd.name().length();
    }

    /**
     * Type link no dimension
     */
    protected void printTypeLinkNoDimension(Type type) {
        ClassDoc cd = type.asClassDoc();
	if (cd == null) {
	    print(type.typeName());
	} else {
	    printClassLink(cd);
	}
    }

    /**
     * type information
     */
    protected void printTypeLink(Type type) {
        printTypeLinkNoDimension(type);
        print(type.dimension());
    }

    /**
     * Return a string describing the access modifier flags.
     * Don't include native or synchronized.
     *
     * The modifier names are returned in canonical order, as
     * specified by <em>The Java Language Specification</em>.
     */
    protected String modifierString(MemberDoc member) {
        int ms = member.modifierSpecifier();
        int no = Modifier.NATIVE | Modifier.SYNCHRONIZED;
	return Modifier.toString(ms & ~no);
    }

    /**
     * Modifiers
     */
    protected void printModifiers(MemberDoc member) {
        String mod;
	mod = modifierString(member);
        if(mod.length() > 0) {
            print(mod);
            print(' ');
        }
    }

    protected void printTypedName(Type type, String name) {
        if (type != null) {
	    printTypeLink(type);
        }
        if(name.length() > 0) {
            writer.print(' ');
            writer.print(name);
        }
    }

    protected String makeSpace(int len) {
        StringBuffer sb = new StringBuffer(len);
        for(int i = 0; i < len; i++) {
            sb.append(' ');
	}
        return sb.toString();
    }

    /**
     * Print 'static' if static and type link.
     */ 
    protected void printStaticAndType(boolean isStatic, Type type) {
        writer.printTypeSummaryHeader();
        if (isStatic) {
            print("static&nbsp;");
        }
        if (type != null) {
            printTypeLink(type); 
        }
        writer.printTypeSummaryFooter();
    }

    /**
     * comments
     */
    protected void printComment(ProgramElementDoc member) {
        String comment = member.commentText();
        if (comment.length() > 0) {
            writer.dd();
            print(comment);
            // writer.p();
        }
    }

    /**
     * Tags
     */
    protected void printTags(ProgramElementDoc member) {
        if (member.seeTags().length > 0) {
            writer.dd();
            writer.dl();
            writer.printSeeTags(member);
            writer.dlEnd();
            writer.ddEnd();
        }
    }

    protected String name(ProgramElementDoc member) {
        return member.name();
    }

    /**
     * depricated
     */
    protected void printDeprecated(ProgramElementDoc member) {
        Tag[] deprs = member.tags("deprecated");
        if (deprs.length > 0) {
            String text = deprs[0].text();
	    writer.dd();
            writer.bold(writer.getText("doclet.Note_0_is_deprecated",  name(member)));
            if (text.length() > 0) {
                writer.italics(text);
            }
            writer.p();
        }
    }

    /**
     * Header information
     */
    protected void printHead(MemberDoc member) {
        writer.h3();
        writer.print(member.name());
        writer.h3End();
    }

    /**
     * Full set of comments
     * includes depricated, comment and tags
     */
    protected void printFullComment(ProgramElementDoc member) {
        writer.dl();
        printDeprecated(member);
        printComment(member);
        printTags(member);
        writer.dlEnd();
    }

    public void printMembers(ClassDoc cd) {
        ProgramElementDoc[] members = members(cd);
        if (members.length > 0) {
            //printHeader(cd);
            for (int i = 0; i < members.length; ++i) {
                if (i > 0) {
                    //writer.hr();
                }
                printMember(cd, members[i]);
            }
            //printFooter(cd);
        }
    }

}  
    
    
