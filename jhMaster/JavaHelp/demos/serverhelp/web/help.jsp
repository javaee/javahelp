<%--
 @(#)help.jsp	1.5 06/10/30
 
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
<%@ taglib uri="/jhlib.tld" prefix="jh" %>
<html>
<head>
<%
// only an "id" should be set.
String id = request.getParameter("id");
if (id == null) {
    // nothing to do
    // in regular java code we would return.
} else {
    if (id != null) {
        // Yep, just update the helpBroker
	// The contentsframe has already been updated
	helpBroker.setCurrentID(id);
    }
}
%>
<title>JavaHelp Server Demo</title>
<SCRIPT LANGUAGE="JavaScript1.3" src="util.js">
</SCRIPT>
</head>
<FRAMESET ROWS="120,*" NAME="helptop" BORDER=0 FRAMESPACING=0>
    <FRAMESET COLS="*,0" NAME="upperframe" NORESIZE FRAMEBORDER=NO>
	<FRAME SRC="banner.html" NAME="bannerframe" SCROLLING="NO">
	<FRAME SRC="update.jsp" NAME="updateframe">
    </FRAMESET>
    <FRAMESET COLS="30%,70%" NAME="lowerhelp" BORDER=5 FRAMESPACING=5 FRAMEBORDER=YES>
	<FRAMESET ROWS="40,*" NAME="navigatortop" BORDER=0 FRAMESPACING=0>
	    <FRAME SRC="navigator.jsp" NAME="navigatorframe" SCROLLING="NO" FRAMEBORDER=NO>
	    <FRAME SRC="loading.html" NAME="treeframe" SCROLLING="AUTO" FRAMEBORDER=NO>
	</FRAMESET>
	<FRAMESET ROWS="40,*" NAME="rightpane">
	    <FRAME SRC="toolbar.html" NAME="toolbarframe" SCROLLING="NO" FRAMEBORDER=YES>
	    <FRAME SRC="<jsp:getProperty name="helpBroker" property="currentURL" />" NAME="contentsFrame" SCROLLING="AUTO" FRAMEBORDER=Yes>
	</FRAMESET>
    </FRAMESET>
</FRAMESET>
<NOFRAMES>
<BODY bgcolor=white>
Help requires frames to be displayed
</BODY>
</NOFRAMES> 
</HTML>
