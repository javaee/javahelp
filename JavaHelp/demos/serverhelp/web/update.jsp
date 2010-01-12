<%--
 @(#)update.jsp	1.4 06/10/30
 
 Copyright (c) 2006 Sun Microsystems, Inc.  All Rights Reserved.
 
 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions
 are met:
 
 - Redistribution of source code must retain the above copyright
   notice, this list of conditions and the following disclaimer.
 
 - Redistribution in binary form must reproduce the above copyright
   notice, this list of conditions and the following disclaimer in
   the documentation and/or other materials provided with the
   distribution.
 
 Neither the name of Sun Microsystems, Inc. or the names of
 contributors may be used to endorse or promote products derived
 from this software without specific prior written permission.
 
 This software is provided "AS IS," without a warranty of any
 kind. ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND
 WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY
 EXCLUDED. SUN MICROSYSTEMS, INC. ("SUN") AND ITS LICENSORS SHALL
 NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF
 USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE FOR
 ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT, SPECIAL,
 CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER CAUSED AND
 REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF THE USE OF OR
 INABILITY TO USE THIS SOFTWARE, EVEN IF SUN HAS BEEN ADVISED OF THE
 POSSIBILITY OF SUCH DAMAGES.
 
 You acknowledge that this software is not designed, licensed or
 intended for use in the design, construction, operation or
 maintenance of any nuclear facility. 
--%>

<jsp:useBean id="helpBroker" class="javax.help.ServletHelpBroker" scope="session" />
<%@ page import="java.net.URL, javax.help.HelpSet, javax.help.Map.ID" %>
<HTML>
<BODY STYLE="margin-left:-2;margin-right:-2;margin-top:-2;margin-bottom:-2" BGCOLOR=white>
<SCRIPT>
<%
// only a "url" or an "id" should be set.
// If for some reason both are set the url overrides the id 
String url = request.getParameter("url");
String id = request.getParameter("id");
if (url == null && id == null) {
    // nothing to do
    // in regular java code we would return.
    // we'll just else here
} else {
    // Try the URL first.
    // If a parameter has been past then there has been
    // a change in the contentframe that needs to be reflected in the
    // helpBroker and the navigator
    if (url != null) {
	URL curURL = helpBroker.getCurrentURL();
	URL testURL = new URL(url);
	if (!testURL.sameFile(curURL)) {
	    ID currentid = helpBroker.getCurrentID();
	    helpBroker.setCurrentURL(testURL);
	    ID mapid = helpBroker.getCurrentID();
	    // if the changed url translates into an id'
	    // update the navigatorframe 
	    // otherwise make sure that nothing is selected
	    // in the navigator frame
	    if (mapid != null && mapid != currentid) {
		%>
                top.findHelpID("<%= mapid.id %>");
		<%
	    } else {
		if (currentid != null) {
		    %>
 		    top.setSelected("<%= currentid.id %>", false);
		    <%
		}
	    }
	}
    } else {
	// no URL was specified how about an id?
	if (id != null) {
	    // Yep, just update the helpBroker
	    // The contentsframe has already been updated
	    helpBroker.setCurrentID(id);
	}
    }
}
%>
    top.setTimeout("top.checkContentsFrame( );", 2000);
</SCRIPT>
</BODY>
</HTML>
