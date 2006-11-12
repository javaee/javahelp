/*
 * @(#)util.js	1.5 06/10/30
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

   var staticCurrentURL = null;

   /*
    * check and see if any change in the content has occured
    * if it has fire an update with change
    */
    function checkContentsFrame() {
	var url;

	url = top.contentsFrame.document.URL;

	// if the staticCurrentURL hasn't been set then just set it
	if (staticCurrentURL == null) {
	    staticCurrentURL = url;
	} else {
	    // test if the staticCurrentURL equal the url
	    if (staticCurrentURL.indexOf(url) == -1) {
		// they are not equal
		// reload the updateFrame based on the url
		staticCurrentURL = url;
		top.updateframe.location = "update.jsp?url=" + url;
		return;
	    }
	}
	top.setTimeout("top.checkContentsFrame( );", 2000);
    }

function invoke(theURL, id) {
    top.contentsFrame.location = theURL;
    top.updateframe.location = "update.jsp?id=" + id;
}

var browser = new browserData();
function browserData()
{
    var useragnt = navigator.userAgent;
    this.canDoDOM = (document.getElementById) ? true : false;
    if ( useragnt.indexOf('Opera') >= 0) {
	this.name = 'Opera';
    } else if (  useragnt.indexOf('MSIE') >= 0 ) {
	this.name = 'InternetExplorer';
    } else {
	this.name = 'Another';
    }

    this.OS = ''
    var platform;
    if (typeof(window.navigator.platform) != 'undefined')
    {
	platform = window.navigator.platform.toLowerCase();
	if (platform.indexOf('win') != -1) {
	    this.OS = 'win';
	} else if (platform.indexOf('mac') != -1) {
	    this.OS = 'mac';
	} else if (platform.indexOf('unix') != -1 || platform.indexOf('linux') != -1 || platform.indexOf('sun') != -1) {
	    this.OS = 'nix';
	}
    }
}
