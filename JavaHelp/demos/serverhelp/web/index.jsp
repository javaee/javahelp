<%--
 @(#)index.jsp	1.4 06/10/30
 
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
<jh:validate helpBroker="<%= helpBroker %>" helpSetName="JavaHelpDemo/animals/Animals.hs"/>
<jh:validate merge="<%= true %>" helpSetName="JavaHelpDemo/invertebrates/Invertebrates.hs" helpBroker="<%= helpBroker %>" />
<jh:validate merge="<%= true %>" helpSetName="JavaHelpDemo/vertebrates/Vertebrates.hs" helpBroker="<%= helpBroker %>" />
<title>Sample JavaHelpServer Application</title>
<SCRIPT>
      function openNewWindow (URL, windowName, windowOptions)
      {
          var window = getWindow (URL, windowName, windowOptions);
      }

      function getWindow(URL, windowName, windowOptions)
      {
          var newWindow = open (URL, windowName, windowOptions)
          newWindow.focus();
          top.allOpenWindows[top.allOpenWindows.length] = newWindow;
          return window;
      }
</SCRIPT>
</head>
<body bgcolor=white>

<table border="0">
<tr>
<td>
<img src="images/tomcat.gif">
</td>
<td>
<h1>Sample JavaHelp JSP Application</h1>
<p>This is the home page for a sample JavaHelp JSP used to illustrate the
usage of JavaHelp in a web application.
</td>
</tr>
</table>

<p>To prove that they work, you can execute either of the following links:
<ul>
<li>To <a href="javascript: openNewWindow('help.jsp', 'helpWindow', 'WIDTH=700,HEIGHT=500,resizable=yes');">Animals - A merged Helpset</a>.

<li>To <a href="javascript: openNewWindow('help.jsp?id=blackbear.picture', 'helpWindow', 'WIDTH=700,HEIGHT=500,resizable=yes');">Animals - Black Bear</a>.

</ul>

<br>
<p>An alternative presentation is in the following links:

<ul>
<li>To <a href="javascript: openNewWindow('altpres/help.jsp', 'helpWindow', 'WIDTH=700,HEIGHT=500,resizable=yes');">Animals - A merged Helpset</a>.

<li>To <a href="javascript: openNewWindow('altpres/help.jsp?id=blackbear.picture', 'helpWindow', 'WIDTH=700,HEIGHT=500,resizable=yes');">Animals - Black Bear</a>.

</ul>


</body>
</html>
