/*
 * @(#)JHInvoker11.java	1.5 06/10/30
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

/**
 * @(#) JHInvoker11.java 1.5 - last change made 10/30/06
 *
 * (c) 1997-1998 Sun Microsystems, Inc.  All rights reserved.  Use is
 * subject to license terms. Sun, Sun Microsystems, the Sun Logo, Solaris,
 * Java, the Java Coffee Cup Logo, and JavaHelp are trademarks or registered
 * trademarks of Sun Microsystems, Inc. in  the U.S. and other countries.
 */

import javax.swing.*;
import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.util.Locale;
import java.io.IOException;
import com.sun.javax.javahelp.*;

/**
 * 1.1 version of this
 *
 * @author Roger D. Brinkley
 * @author Eduardo Pelegri-Llopart
 * @version	1.5	10/30/06
 */

public class JHInvoker11 extends JApplet implements MouseListener {
    private Image glyph = null;
    private String helpSetName = null;
    private String helpSetURL = null;
    private HelpSet hs;
    private HelpBroker hb;

    public void init() {
	System.err.println("init called");
	String glyphName = getParameter("GLYPH");
	System.err.println("glyphName: "+glyphName);
	helpSetName = getParameter("HELPSET");
	System.err.println("helpSetName: "+helpSetName);
	helpSetURL = getParameter("HELPSETURL");
	System.err.println("helpSetURL: "+helpSetURL);
	URL codebase = getCodeBase();
	System.err.println("codebase: "+codebase);
	try {
	    URL imageURL = new URL(codebase, glyphName);
	    System.err.println("imageURL: "+imageURL);
	    glyph = getImage(imageURL);
	    //	    glyph = getImage(codebase, glyphName);
	    if (glyph == null) {
		System.err.println("could not load image for "+glyphName);
	    }
	} catch (Exception ex) {
	    System.err.println("X!! exception while loading image"+ex);
	}
	addMouseListener(this);
    }


    private void createHelpSet(String name) {
	ClassLoader loader = this.getClass().getClassLoader();
	try {
	    // try - but it will fail in this case...
	    URL url = HelpSet.findHelpSet(loader, name, Locale.getDefault());
	    System.err.println("createHelpSet("+name+")");
	    System.err.println("  loader: "+loader);
	    System.err.println("  locale: "+Locale.getDefault());
	    System.err.println("  url: "+url);

	    if (helpSetURL != null && helpSetURL.length()!=0) {
		// we proceed to ignore it and instead use URLHELPSET
		url = new URL(helpSetURL);
	    }
	    System.err.println("Got a URL: "+url);
	    hs = new HelpSet(loader, url);
	    System.err.println("Loaded the HelpSet; hs: "+hs);
	} catch (Exception ee) {
	    System.out.println ("Trouble in createHelpSet;");
	    ee.printStackTrace();
	    return;
	}
    }

    public void mouseClicked(MouseEvent e) {
	if (hs == null) {
System.err.println("mouseclicked... creating HelpSet...");
	    createHelpSet(helpSetName);
	}
	if (hb == null) {
System.err.println("mouseclicked... creating JavaHelp...");
	    hb = hs.createHelpBroker();
	}
	hb.setVisible(true);

    }
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}


    public void paint(Graphics g) {
	if (glyph != null) {
	    g.drawImage(glyph, 0, 0, this);
	} else {
	    g.drawString("TROUBLE!!", 0, 0);
	}
    }
}
