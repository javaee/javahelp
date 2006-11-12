/*
 * @(#)Validator.java	1.3 06/10/30
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

import java.io.File;
import com.sun.xml.parser.Resolver;
import com.sun.xml.parser.ValidatingParser;
import com.sun.xml.parser.Parser;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.HandlerBase;

public class Validator
{
    private static String hsID = "-//Sun Microsystems Inc.//DTD JavaHelp HelpSet Version 1.0//EN";
    private static String hsDTD = "helpset_1_0.dtd";

    private static String tocID = "-//Sun Microsystems Inc.//DTD JavaHelp TOC Version 1.0//EN";
    private static String tocDTD = "toc_1_0.dtd";

    private static String indexID = "-//Sun Microsystems Inc.//DTD JavaHelp Index Version 1.0//EN";
    private static String indexDTD = "index_1_0.dtd";

    private static String mapID = "-//Sun Microsystems Inc.//DTD JavaHelp Map Version 1.0//EN";
    private static String mapDTD = "map_1_0.dtd";

    private static void updateResolver(Resolver r,
				       String pid,
				       String dtd) {
	r.registerCatalogEntry(pid, dtd, null);
	
	if (false)
	try {
	    java.net.URL u = ClassLoader.getSystemResource(dtd);
	    String sid = u.toExternalForm();
	    r.registerCatalogEntry(pid, sid);
	} catch (Exception e) {
	    System.err.println("caught "+e);
	}
    }

    //
    // Reading and validating an XML document stored in a file.
    // We add as "well known" PublicIDs those of HelpSet, TOC, View, and Map
    //
    public static void main (String argv [])
    {
	InputSource	input;
	Resolver	resolver;

	if (argv.length < 1) {
	    System.err.println ("Usage: cmd filename ...");
	    System.exit (1);
	}

	try {
	    // register some publicIDs
	    resolver = new Resolver();
	    
	    updateResolver(resolver, hsID, hsDTD);
	    updateResolver(resolver, tocID, tocDTD);
	    updateResolver(resolver, indexID, indexDTD);
	    updateResolver(resolver, mapID, mapDTD);
					  
	    // create a parser
	    Parser p = new ValidatingParser();
	    p.setEntityResolver(resolver);
	    p.setErrorHandler(new MyHandler());

	    for (int i=0; i<argv.length; i++) {
		// turn the filename into an input source and parse
		input = resolver.createInputSource(new File (argv [i]));
		try {
		    System.err.println("Parsing "+argv[i]+"...");
		    p.parse(input);
		} catch (SAXParseException err) {
		    System.out.println ("** Parsing error" 
					+ ", line " + err.getLineNumber ()
					+ ", uri " + err.getSystemId ());
		    System.out.println("   " + err.getMessage ());
		    // print stack trace as below
		}
	    }

	} catch (SAXException e) {
	    Exception	x = e.getException ();

	    ((x == null) ? e : x).printStackTrace ();

	} catch (Throwable t) {
	    t.printStackTrace ();
	}

	System.exit (0);
    }
}
    // An error handler

class MyHandler extends HandlerBase {
    public MyHandler() {
	super();
    }

    public void error(SAXParseException err) throws SAXException {
	System.out.println ("** Parsing error" 
			    + ", line " + err.getLineNumber ()
			    + ", uri " + err.getSystemId ());
	System.out.println("   " + err.getMessage ());
	super.error(err);
    }
    public void fatalError(SAXParseException err) throws SAXException {
	System.out.println ("** Parsing error" 
			    + ", line " + err.getLineNumber ()
			    + ", uri " + err.getSystemId ());
	System.out.println("   " + err.getMessage ());
	super.fatalError(err);
    }
    public void warning(SAXParseException err) throws SAXException {
	System.out.println ("** Parsing error" 
			    + ", line " + err.getLineNumber ()
			    + ", uri " + err.getSystemId ());
	System.out.println("   " + err.getMessage ());
	super.warning(err);
    }
}

