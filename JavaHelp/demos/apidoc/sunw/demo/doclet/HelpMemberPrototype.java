/*
 * @(#)HelpMemberPrototype.java	1.9 06/10/30
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
 * @(#) HelpMemberPrototype.java 1.9 - last change made 10/30/06
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
 * This class print the Member documentation for a class
 *
 * @author Roger D. Brinkley
 * @author Eduardo Pelegri-Llopart
 * @version	1.9	10/30/06
 */

public class HelpMemberPrototype {

    MemberDoc member;
  
    StringBuffer sb = new StringBuffer();

    HelpMemberPrototype(MemberDoc member) {
        this.member = member;
    }

    /**
     * track how long the displayed (non-html) contents
     * of 'sb' are.
     */
    int displayLength = 0;

    static String generate(ExecutableMemberDoc method) {
        HelpMemberPrototype mp = new HelpMemberPrototype(method);
        return mp.methodPrototype(method);
    }

    static String generate(FieldDoc field) {
        HelpMemberPrototype mp = new HelpMemberPrototype(field);
        return mp.fieldPrototype(field);
    }

    void append(String str) {
        sb.append(str);
        displayLength += str.length();
    }

    void append(char ch) {
        sb.append(ch);
        displayLength++;
    }

    void makeLink(Type type) {
	ClassDoc cls = null;
	if (type == null) {
	    return;
	}
	try {
	    cls = type.asClassDoc();
	} catch (NullPointerException e) {
	    cls = null;
	}
        if (cls == null) {
	    try {
		append(type.typeName());
	    } catch (NullPointerException e2) {
	    }
        } else {
            // use sb.append() to by-pass length count
            sb.append("<a href=\"");    
	    //            if (cls != member.containingClass()) {
                sb.append(cls.qualifiedTypeName());
		//            }
            sb.append(".html\">");
	    append(cls.name());
            sb.append("</a>");
        }
    }

    void appendModifiers() {
        String mod = member.modifiers();  // ??? wrong!!!
        if(mod.length() > 0) {
            append(mod);
            append(' ');
        }
    }

    void appendParam(Parameter param) {
	Type type = param.type();
        makeLink(type);

        // the name of the arg is not always available
        String name = param.name();
        if(name.length() > 0) {
            sb.append(' ');
            sb.append(name);
        }
	if (type != null) {
	    sb.append(type.dimension());
	}
    }

  
    String makeSpace(int len) {
        StringBuffer sb = new StringBuffer(len);
        for(int i = 0; i < len; i++)
            sb.append(' ');
    
        return sb.toString();
    }

    String fieldPrototype(FieldDoc field) {
        appendModifiers();

        Type type = field.type();
        if (type != null) {
            makeLink(type);

            append(' ');
        }

        append(member.name());

        // for a field the [] goes with the name, not the type 
        // ??? change after compare!!!
        append(type.dimension());

        return sb.toString();
    }

    String methodPrototype(ExecutableMemberDoc method) {
        appendModifiers();

	if (method instanceof MethodDoc) {
	    MethodDoc method2 = (MethodDoc) method;
	    Type type = method2.returnType();
	    if (type != null) {
		makeLink(type);

		// for a field the [] goes with the name, not the type 
		// ??? remove comment after this is fixed after compare!!!
		append(type.dimension());
		
		append(' ');
	    }
	}

        append(method.name());
        append('(');

        String space = "";

        Parameter[] params = method.parameters();
        if (params.length > 0) {
            space = makeSpace(displayLength);
            appendParam(params[0]);
        }

        for (int i = 1; i < params.length; i++) {
            sb.append(',');
            sb.append('\n');
            sb.append(space);
            appendParam(params[i]);
        }

        sb.append(')');

        Type[] except = method.thrownExceptions();
        if(except.length > 0) {
            sb.append(" throws ");
            makeLink(except[0]);

            for(int i = 1; i < except.length; i++) {
                sb.append(", ");
                makeLink(except[i]);
            }
        }
        return sb.toString();
    }

    /**
     * For printf debugging.
     */
    private static boolean debug = false;
    private static void debug(String str) {
        if (debug) {
            System.out.println("HelpMemberPrototype: " + str);
        }
    }
}  
