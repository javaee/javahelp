/*
 * @(#)ClassViewerView.java	1.7 06/10/30
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

package sunw.demo.classviewer;

import java.awt.Component;
import java.util.Hashtable;
import java.util.Locale;
import javax.help.HelpSet;
import javax.help.HelpModel;
import javax.help.NavigatorView;

/**
 * View information for a ClassViewer Navigator
 *
 * @author Eduardo Pelegri-Llopart
 * @version	1.7	10/30/06
 */

public class ClassViewerView extends NavigatorView {
    /**
     * Construct a ClassViewer VIew with some given data.  Locale defaults
     * to that of the HelpSet
     *
     * @param hs The HelpSet that provides context information
     * @param name The name of the View
     * @param label The label (to show the user) of the View
    * @param params A Hashtable providing different key/values for this type
    */
    public ClassViewerView(HelpSet hs,
			   String name,
			   String label,
			   Hashtable params) {
	super(hs, name, label, hs.getLocale(), params);
    }

    /**
     * Construct a ClassViewer VIew with some given data.
     *
     * @param hs The HelpSet that provides context information
     * @param name The name of the View
     * @param label The label (to show the user) of the View
     * @param locale The default locale to interpret data in this View
    * @param params A Hashtable providing different key/values for this type
    */
    public ClassViewerView(HelpSet hs,
			   String name,
			   String label,
			   Locale locale,
			   Hashtable params) {
	super(hs, name, label, locale, params);
    }

    /**
     * create a navigator for a given model
     */
    public Component createNavigator(HelpModel model) {
	return new ClassViewerNavigator(this, model);
    }
}
