/*
 * @(#)FieldSubWriter.java	1.7 06/10/30
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
 * @(#) FieldSubWriter.java 1.7 - last change made 10/30/06
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

/**
 * This class prints out Field documentation for a class
 *
 * @author Roger Brinkley
 * @version	1.7	10/30/06
 */

public class FieldSubWriter extends AbstractSubWriter {

    FieldSubWriter(HtmlWriter writer) {
        super(writer);
    }

    public ProgramElementDoc[] members(ClassDoc cd) {
        return cd.fields();
    }

    void printSignature(MemberDoc member) {
        FieldDoc field = (FieldDoc)member;
	writer.pre();
        printModifiers(field);
        printTypeLink(field.type());
        print(' ');
        bold(field.name());
	writer.preEnd();
    }

    protected void printHeader(ClassDoc cd) {
        writer.anchor("variables");
        writer.printTableHeadingBackground(writer.getText("doclet.Fields"));
    }

    // Not currently used. Will be used when anchors work
    protected void printFooter(ClassDoc cd) {
        // Nothing to do for now
    }

    protected void printMember(ClassDoc cd, ProgramElementDoc member) {
        FieldDoc field = (FieldDoc)member;
        writer.anchor(field.name());

        printHead(field);
        printSignature(field);
        printFullComment(field);
    }

    protected void printDeprecatedLink(ProgramElementDoc member) {
        writer.printClassLink(member.containingClass(), member.name(), 
                              ((FieldDoc)member).qualifiedName());
    }

    /**
     * For printf debugging.
     */
    private static boolean debug = false;
    private static void debug(String str) {
        if (debug) {
            System.out.println("FieldSubWriter: " + str);
        }
    }
}  
    
    
