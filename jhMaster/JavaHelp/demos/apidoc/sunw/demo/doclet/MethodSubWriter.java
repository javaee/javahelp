/*
 * @(#)MethodSubWriter.java	1.6 06/10/30
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
 * @(#) MethodSubWriter.java 1.6 - last change made 10/30/06
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

/**
 * This class writes out java class methods.
 *
 * @author Roger D. Brinkley
 * @version	1.6	10/30/06
 */

public class MethodSubWriter extends ExecutableMemberSubWriter {

    MethodSubWriter(HtmlWriter writer) {
        super(writer);
    }

    public ProgramElementDoc[] members(ClassDoc cd) {
        return cd.methods();
    }

    public void printSummaryLabel(ClassDoc cd) {
        writer.boldText("doclet.Method_Summary"); 
    }

    public void printInheritedSummaryLabel(ClassDoc cd) {
        writer.bold();
        if (cd.isClass()) {
            writer.printText("doclet.Inherited_Methods_From_Class");
        } else {
            writer.printText("doclet.Inherited_Methods_From_Interface");
        } 
        writer.print(' ');
	writer.printPreQualifiedClassLink(cd);
        writer.boldEnd();
    }

    protected void printSummaryType(ProgramElementDoc member) {
        MethodDoc meth = (MethodDoc)member;
        printStaticAndType(meth.isStatic(), meth.returnType());
    }

    protected void printReturnTag(Tag[] returns) {
        if (returns.length > 0) {
            writer.dt();
            writer.boldText("doclet.Returns");
            writer.dd();
            writer.print(returns[0].text());
        }
    }

    protected void printOverriden(ClassDoc overriden, MethodDoc method) {
        if (overriden != null) {
            String name = method.name();
            writer.dt();
            writer.boldText("doclet.Overrides");
            writer.dd();
            if (overriden.isIncluded()) {
                writer.printHyperLink(overriden.qualifiedName() + ".html", 
                                      name + method.signature(), name);
            } else {
                // not in this run
                writer.print(name);
            }
            writer.print(' ');
            writer.printText("doclet.in_class");
            writer.print(' ');
            writer.printClassLink(overriden);
        }
    }

    protected void printTags(ProgramElementDoc member) {
        MethodDoc method = (MethodDoc)member;
        ParamTag[] params = method.paramTags();
        Tag[] returns = method.tags("return");
        ThrowsTag[] thrown = method.throwsTags();
        SeeTag[] sees = method.seeTags();
        ClassDoc[] intfacs = member.containingClass().interfaces();
        ClassDoc overriden = method.overriddenClass();
        if (params.length + returns.length + thrown.length 
            + intfacs.length + sees.length > 0 ||
            overriden != null) {
            writer.dd();
            writer.dl();
            printImplementsInfo(method);
            printParamTags(params);
            printReturnTag(returns);
            printThrowsTags(thrown);
            printOverriden(overriden, method);
            writer.printSeeTags(method);
            writer.dlEnd();
            writer.ddEnd();
        }
    }
                               
    protected void printSignature(ExecutableMemberDoc member) {
        displayLength = 0;
	writer.pre();
	printModifiers(member);
	printReturnType((MethodDoc)member);
	bold(member.name());
	printParameters(member);
	printExceptions(member);
	writer.preEnd();
    }
      
    protected void printReturnType(MethodDoc method) {
        Type type = method.returnType();
        if (type != null) {
            printTypeLink(type);
            print(' ');
        }
    }
    
    protected void printHeader(ClassDoc cd) {
        writer.anchor("methods");
        writer.printTableHeadingBackground(writer.getText("doclet.Methods"));
    }
}  
    
    
