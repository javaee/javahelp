#
# @(#)Makefile	1.2 06/10/30
# 
# Copyright (c) 2006 Sun Microsystems, Inc.  All Rights Reserved.
# 
# Redistribution and use in source and binary forms, with or without
# modification, are permitted provided that the following conditions
# are met:
# 
# - Redistribution of source code must retain the above copyright
#   notice, this list of conditions and the following disclaimer.
# 
# - Redistribution in binary form must reproduce the above copyright
#   notice, this list of conditions and the following disclaimer in
#   the documentation and/or other materials provided with the
#   distribution.
# 
# Neither the name of Sun Microsystems, Inc. or the names of
# contributors may be used to endorse or promote products derived
# from this software without specific prior written permission.
# 
# This software is provided "AS IS," without a warranty of any
# kind. ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND
# WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY,
# FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY
# EXCLUDED. SUN MICROSYSTEMS, INC. ("SUN") AND ITS LICENSORS SHALL
# NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF
# USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
# DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE FOR
# ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT, SPECIAL,
# CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER CAUSED AND
# REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF THE USE OF OR
# INABILITY TO USE THIS SOFTWARE, EVEN IF SUN HAS BEEN ADVISED OF THE
# POSSIBILITY OF SUCH DAMAGES.
# 
# You acknowledge that this software is not designed, licensed or
# intended for use in the design, construction, operation or
# maintenance of any nuclear facility. 
#

# Makefile
#

.SUFFIXES: .java .class .properties

# JH jar
JH_JAR = lib\jh.jar

# idehelp.jar is the JAR that defines the application help
IDEDEMO_JAR = ../hs/idehelp/idehelp.jar

# images.jar contains the images for ApiDemo
IDEIMAGES_JAR = ../idedemo/images.jar

# classviewer.jar provides the ClassViewer navigator
# htmldemo.jar provides the specialized HTML viewer

CLASSPATH =classes
CLASSDIR=classes

BROWSERDIR=sunw\demo\browser

BROWSER_CLASSES = \
	$(CLASSDIR)\$(BROWSERDIR)\BrowserApiDemo.class 

SOURCES = \
	$(BROWSER_CLASSES:$(CLASSDIR)\$(IDEDIR)\%.class=$(IDEDIR)\%.java)

APIDEMO_PROPFILE = \
	$(CLASSDIR)\$(BROWSERDIR)\ApiDemo.properties

all: $(CLASSDIR) sources $(APIDEMO_PROPFILE)

sources: $(SOURCES)

$(CLASSDIR): $(IDEHELP_JAR) $(JH_JAR) $(IDEIMAGES_JAR)
	-mkdir $(CLASSDIR)
	-mkdir $(CLASSDIR)\$(BROWSERDIR)
	cd $(CLASSDIR)
	jar -xvf ..\$(IDEHELP_JAR)
	jar -xvf ..\$(JH_JAR)
	cd $(BROWSERDIR)
	jar -xvf ..\..\..\..\$(IDEIMAGES_JAR)
	cd ..\..
	-del $(CLASSDIR)\META-INF

clean:
	-del/S/Q $(CLASSDIR)

{$(BROWSERDIR)}.java{$(CLASSDIR)\$(BROWSERDIR)}.class :
	set CLASSPATH=.;$(CLASSPATH)
	javac -d $(CLASSDIR) $<

{$(BROWSERDIR)}.properties{$(CLASSDIR)\$(BROWSERDIR)}.properties :
	copy $< $(CLASSDIR)\$(BROWSERDIR)

