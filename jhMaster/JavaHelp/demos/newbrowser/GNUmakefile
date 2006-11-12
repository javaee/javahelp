#
# @(#)GNUmakefile	1.4 06/10/30
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

# @(#)GNUmakefile	1.10 97/06/05
#
# Makefile for use on Solaris using gnumake.

JH_JAR=../../javahelp/lib/jh.jar

HOLIDAY_JAR=../hsjar/holidays.jar

# browserdemo.jar is the JAR that defines the application
JHBROWSER_JAR = browserdemo.jar

# Search
BROWSERDIR=sunw/demo/browser

CLASSPATH =.:$(JH_JAR)
CLASSPATH2 =.:../$(JH_JAR)
CLASSDIR=classes
JAVACFLAGS=-target 1.1
JAVAC = javac $(JAVACFLAGS)
JAVA = java

JHBROWSER_CLASSES = \
	$(CLASSDIR)/$(BROWSERDIR)/HelpButton.class

all: $(JHBROWSER_JAR)

clean:
	rm -f -r classes $(JHBROWSER_JAR) jh.jar
	rm -rf Holiday*.* Map.jhm hol images

$(JHBROWSER_JAR): $(JHBROWSER_CLASSES) $(HOLIDAY_JAR)
	@rm -f $(JHSEARCH_JAR)
	jar xf $(HOLIDAY_JAR) ; /bin/rm -rf META-INF JavaHelpSearch ;
	cd $(CLASSDIR) ; \
	jar cf ../$(JHBROWSER_JAR) *
	cp $(JH_JAR) .

$(CLASSDIR)/$(BROWSERDIR)/%.class: $(BROWSERDIR)/%.java
	mkdir -p $(CLASSDIR)
	export CLASSPATH; CLASSPATH=$(CLASSPATH); \
	$(JAVAC) -d $(CLASSDIR) $?


