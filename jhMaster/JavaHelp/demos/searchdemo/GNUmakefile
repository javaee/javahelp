#
# @(#)GNUmakefile	1.11 06/10/30
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

# Jhdemo.jar is the JAR that defines the application
JHSEARCH_JAR = searchdemo.jar

# Search
SEARCHDIR=sunw/demo/searchdemo

CLASSPATH =.:$(JH_JAR)
CLASSPATH2 =.:../$(JH_JAR)
CLASSDIR=classes
JAVACFLAGS=-target 1.1
JAVAC = javac $(JAVACFLAGS)
JAVA = java

JHSEARCH_CLASSES = \
	$(CLASSDIR)/$(SEARCHDIR)/COOKIE.class \
	$(CLASSDIR)/$(SEARCHDIR)/ClientSearchEngine.class \
	$(CLASSDIR)/$(SEARCHDIR)/ClientSearchQuery.class \
	$(CLASSDIR)/$(SEARCHDIR)/Doc.class \
	$(CLASSDIR)/$(SEARCHDIR)/DocVector.class \
	$(CLASSDIR)/$(SEARCHDIR)/Index.class \
	$(CLASSDIR)/$(SEARCHDIR)/Word.class \
	$(CLASSDIR)/$(SEARCHDIR)/WordHashtable.class \
	$(CLASSDIR)/$(SEARCHDIR)/WordInventory.class \
	$(CLASSDIR)/$(SEARCHDIR)/WordVector.class

all: $(JHSEARCH_JAR)

clean:
	rm -f -r classes $(JHSEARCH_JAR)

$(JHSEARCH_JAR): $(JHSEARCH_CLASSES) $(HOLIDAY_JAR) HolidayHistory.hs
	@rm -f $(JHSEARCH_JAR)
	cd $(CLASSDIR) ; \
	jar xf ../$(HOLIDAY_JAR) ; /bin/rm -rf META-INF JavaHelpSearch ;\
	cp ../HolidayHistory.hs . ; \
	export CLASSPATH; CLASSPATH=$(CLASSPATH2); \
	java sunw.demo.searchdemo.Index -verbose hol ; \
	jar cf ../$(JHSEARCH_JAR) *

$(CLASSDIR)/$(SEARCHDIR)/%.class: $(SEARCHDIR)/%.java
	mkdir -p $(CLASSDIR)
	export CLASSPATH; CLASSPATH=$(CLASSPATH); \
	$(JAVAC) -d $(CLASSDIR) $?


