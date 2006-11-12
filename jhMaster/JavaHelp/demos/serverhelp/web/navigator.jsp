<%--
 @(#)navigator.jsp	1.4 06/10/30
 
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
<%@ page import="javax.help.NavigatorView" %>
<%@ taglib uri="/jhlib.tld" prefix="jh" %>
<HTML>
<STYLE type="text/css">
    .tabbedBodyStyle { margin-left:-2; margin-right:-2; margin-top:-2; margin-bottom:-2; background-color:white; }
    .tableStyle {border-width:10 10 0 10; border-color:#CCCCCC; border-style:solid; }
    .tabbedAnchorStyle { text-decoration:none; color:black;}
    .tableDefStyle {padding-top:5; padding-left:5; padding-right:5; padding-bottom:0;}
</STYLE>
<BODY CLASS="tabbedBodyStyle">
    <TABLE CLASS="tableStyle" WIDTH=100% BORDER=1 CELLSPACING=0 CELLPADDING=5>
      <TR BGCOLOR="#CCCCCC">

<jh:navigators helpBroker="<%= helpBroker %>" >
<td classtableDefStyle BGCOLOR="<%= isCurrentNav.booleanValue() ? "white" : "#E5E5E5" %>" ALIGN="center">
<A class=tabbedAnchorStyle HREF="navigator.jsp?nav=<%= name %>">
<IMG src="<%= iconURL!=""? iconURL : "images/" + className +".gif" %>" Alt="<%= tip %>" border=0>
</A>
</td>
</jh:navigators>
      </TR>
    </TABLE>
<%@ page import="javax.help.HelpSet,javax.help.NavigatorView" %>
<%
NavigatorView curNav = helpBroker.getCurrentNavigatorView();
if (curNav != null) {
%>
<SCRIPT>
     top.treeframe.location = "<%= curNav.getClass().getName() %>.jsp"
</SCRIPT
<%
} else {
%>
<SCRIPT> 
    top.treeframe.location = "nonavigator.jsp"
</SCRIPT>
<%
}
%>
</BODY>
</HTML>
