/*
 * @(#)GetLocalizedResource.java	1.3 06/10/30
 * 
 * Copyright (c) 2006 Sun Microsystems, Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * 
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Sun designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Sun in the LICENSE file that accompanied this code.
 * 
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 * 
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 * 
 * Please contact Sun Microsystems, Inc., 4150 Network Circle, Santa Clara,
 * CA 95054 USA or visit www.sun.com if you need additional information or
 * have any questions.
 */

import javax.javahelp.*;
import java.net.URL;
import java.util.Locale;

public class GetLocalizedResource {
    private static ClassLoader loader = GetLocalizedResource.class.getClassLoader();

    private static int count = 0;

    private static void error(String msg) {
	count += 1;
	System.err.println(msg);
    }

    private static void exists(String front, String back, Locale locale) {
	URL url;
	url = HelpUtilities.getLocalizedResource(loader,
						 front, back,
						 locale);
	if (url == null) {
	    error("Could not find resource.\n"+
		  "  front: "+front+"\n"+
		  "  back: "+back+"\n"+
		  "  locale: "+locale);
	} else {
	    System.err.println("Found "+url+" for\n"+
			       "  front: "+front+"\n"+
			       "  back: "+back+"\n"+
			       "  locale: "+locale);
	}
    }

    public static void main(String args[]) {
	exists("foo", ".hs", Locale.getDefault());
	exists("foo", ".hs", Locale.getDefault());
	exists("foo", ".hs", Locale.JAPANESE);
	exists("foo", ".hs", Locale.CANADA_FRENCH);
	exists("foo", ".hs", Locale.TAIWAN);
	exists("foo", ".hs", Locale.JAPANESE);
	if (count != 0) {
	    throw new Error("Errors found");
	}
    }
}
