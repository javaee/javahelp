/*
 * @(#)ExecutableMemberSubWriter.java	1.8 06/10/30
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
 * @(#) ExecutableMemberSubWriter.java 1.8 - last change made 10/30/06
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
 * This class prints method and constructor info.
 *
 * @author Roger Brinkley
 * @version	1.8	10/30/06
 */

public abstract class ExecutableMemberSubWriter extends AbstractSubWriter {

    ExecutableMemberSubWriter(HtmlWriter writer) {
        super(writer);
    }

    protected void printSignature(ExecutableMemberDoc member) {
        displayLength = 0;
	writer.pre();
	printModifiers(member);
	bold(member.name());
	printParameters(member);
	printExceptions(member);
	writer.preEnd();
    }
      
    protected void printDeprecatedLink(ProgramElementDoc member) {
        ExecutableMemberDoc emd = (ExecutableMemberDoc)member;
        writer.printClassLink(emd.containingClass(), 
                              emd.name() + emd.signature(), emd.qualifiedName());
    }
  
    protected void printSummaryLink(ClassDoc cd, ProgramElementDoc member) {
        ExecutableMemberDoc emd = (ExecutableMemberDoc)member;
        String name = emd.name();
	writer.bold();
	writer.printClassLink(cd, name + emd.signature(), name);
	writer.boldEnd();
        displayLength = name.length();
	printParameters(emd);
    }
 
    protected void printInheritedSummaryLink(ClassDoc cd, 
                                             ProgramElementDoc member) {
        ExecutableMemberDoc emd = (ExecutableMemberDoc)member;
        String name = emd.name();
	writer.printClassLink(cd, name + emd.signature(), name);
    }    

    protected void printParam(Parameter param) {
        printTypedName(param.type(), param.name());
    }

    protected void printParameters(ExecutableMemberDoc member) {
        String indent = "";
        print('(');
        Parameter[] params = member.parameters();
        if (params.length > 0) {
            indent = makeSpace(displayLength);
            printParam(params[0]);
        }

        for (int i = 1; i < params.length; i++) {
            writer.print(',');
            writer.print('\n');
            writer.print(indent);
            printParam(params[i]);
        }

        writer.print(')');
    }

    protected void printExceptions(ExecutableMemberDoc member) {
        ClassDoc[] except = member.thrownExceptions();
        if(except.length > 0) {
            writer.print(' ');
            writer.printText("doclet.throws");
            writer.print(' ');
            printClassLink(except[0]);

            for(int i = 1; i < except.length; i++) {
                writer.print(", ");
                printClassLink(except[i]);
            }
        }
    }

    protected void printImplementsInfo(MethodDoc method) {
        ClassDoc[] implIntfacs = method.containingClass().interfaces();
        if (implIntfacs.length > 0) {
            ClassDoc intfac = implementsMethodInIntfac(method, implIntfacs);
            if (intfac != null) {
                writer.dt();
                writer.boldText("doclet.Implements");
                writer.dd();
                writer.printHyperLink(intfac.qualifiedName() + ".html",
                               "#" + method.name(),
                               method.name());
                writer.print(' ');
                writer.printText("doclet.in_interface");
                writer.print(' ');
                printClassLink(intfac);
            }
        }
    }

    protected ClassDoc implementsMethodInIntfac(MethodDoc method,
                                                ClassDoc[] intfacs) {
        for (int i = 0; i < intfacs.length; i++) {
            MethodDoc[] methods = intfacs[i].methods();
            if (methods.length > 0) {
                for (int j = 0; j < methods.length; j++) {
                    if (methods[j].name().equals(method.name())) {
                        return intfacs[i];
                    }
                }
            }
        }
        return null;
    }

    protected void printParamTags(ParamTag[] params) {
        if (params.length > 0) {
            writer.dt();
            writer.boldText("doclet.Parameters");
            for (int i = 0; i < params.length; ++i) {
                ParamTag pt = params[i];
                writer.dd();
                writer.code();
                print(pt.parameterName());
                writer.codeEnd();
                print(" - ");
                writer.println(pt.parameterComment());
            }
        }
    }

    protected void printThrowsTags(ThrowsTag[] thrown) {
        if (thrown.length > 0) {
            writer.dt();
            writer.boldText("doclet.Throws");
            for (int i = 0; i < thrown.length; ++i) {
                ThrowsTag tt = thrown[i];
                writer.dd();
                ClassDoc cd = tt.exception();
                if (cd == null) {
                    writer.print(tt.exceptionName());
                } else {
                    printClassLink(cd);
                }
                print(" - ");
                print(tt.exceptionComment());
            }
        }
    }

    protected String name(ProgramElementDoc member) {
        return member.name() + "()";
    }

    protected void printFooter(ClassDoc cd) {
    }

    protected void printMember(ClassDoc cd, ProgramElementDoc member) {
        ExecutableMemberDoc emd = (ExecutableMemberDoc)member;
        String name = emd.name();
	String signature = emd.signature().replace(' ','_');

        writer.anchor(name);
        writer.anchor(name + signature);

        printHead(emd);
        printSignature(emd);
        printFullComment(emd);
    }

    /**
     * For printf debugging.
     */
    private static boolean debug = false;
    private static void debug(String str) {
        if (debug) {
            System.out.println("ExecutableMemberSubWriter: " + str);
        }
    }
}  
    
    
