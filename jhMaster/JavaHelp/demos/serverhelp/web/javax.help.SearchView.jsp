<%--
 @(#)javax.help.SearchView.jsp	1.3 06/10/30
 
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
<%@ page import="javax.help.SearchView" %>
<%@ taglib uri="/jhlib.tld" prefix="jh" %>
<HTML>
<% String query = request.getParameter("searchQuery"); %>
<STYLE type="text/css">
    .anchorStyle { text-decoration:none; color:black; margin-left:8pt; }
    .anchorBoldStyle { text-decoration:none; color:black; font-weight: bold; margin-left:5pt;}
</STYLE>
<BODY BGCOLOR=white>
<SCRIPT LANGUAGE="JavaScript1.3" src="searchlist.js">
</SCRIPT>

<FORM METHOD="GET" NAME="search" ACTION="javax.help.SearchView.jsp">
<P>Find:
<INPUT TYPE="text" NAME="searchQuery" VALUE="<%= query!=null?query:"" %>" >
</FORM>

<%
if (query != null) {
    SearchView curNav = (SearchView)helpBroker.getCurrentNavigatorView(); 
%>
<SCRIPT>
searchList = new SearchList("searchList", 22, "ccccff");
<jh:searchTOCItem searchView="<%= curNav %>" helpBroker="<%= helpBroker %>" query="<%= query %>" >
searchList.addNode("<%= name %>","<%= confidence %>","<%= hits %>","<%= helpID %>","<%= contentURL %>" );
</jh:searchTOCItem>
searchList.drawList();
searchList.refreshList();
searchList.select(0);
</SCRIPT>
<%
}
%>
</BODY>
</HTML>
