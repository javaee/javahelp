#
# @(#)GNUmakefile	1.9 06/10/30
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

.SUFFIXES: .java .class .properties

# JH jar
JH_JAR = ../../javahelp/lib/jh.jar

# idehelp.jar is the JAR that defines the application help
IDEHELP_JAR = ../hsjar/idehelp.jar

# images.jar contains the images for ApiDemo
IDEIMAGES_JAR = ../idedemo/images.jar

CLASSPATH =classes:../../javahelp/lib/jhall.jar
CLASSDIR=classes

BROWSERDIR=sunw/demo/browser

BROWSER_CLASSES = \
	$(CLASSDIR)/$(BROWSERDIR)/BrowserApiDemo.class

SOURCES = \
	$(BROWSER_CLASSES:$(CLASSDIR)/$(IDEDIR)/%.class=$(IDEDIR)/%.java)

APIDEMO_PROPFILE = \
	$(CLASSDIR)/$(BROWSERDIR)/ApiDemo.properties

CP=/bin/cp -f

all: local

# Made local be the default.  Follows the new directions in README
fat: $(CLASSDIR) local fat_classes

local: $(CLASSDIR) $(BROWSER_CLASSES) $(APIDEMO_PROPFILE) $(IDEIMAGES_JAR)
	-mkdir -p $(CLASSDIR)/$(BROWSERDIR)
	cd $(CLASSDIR)/$(BROWSERDIR); jar -xvf ../../../../$(IDEIMAGES_JAR)
	rm -rf $(CLASSDIR)/META-INF

quick: $(CLASSDIR) $(BROWSER_CLASSES)

$(CLASSDIR):
	-mkdir -p $(CLASSDIR)

fat_classes: $(IDEHELP_JAR) $(JH_JAR)
	-mkdir -p $(CLASSDIR)
	cd $(CLASSDIR); jar -xvf ../$(IDEHELP_JAR)
	cd $(CLASSDIR); jar -xvf ../$(JH_JAR)
	rm -rf $(CLASSDIR)/META-INF

clean:
	rm -rf $(CLASSDIR)

$(CLASSDIR)/%.class: %.java
	export CLASSPATH; CLASSPATH=$(CLASSPATH) ; \
	javac -d $(CLASSDIR) $<

$(CLASSDIR)/%.properties: %.properties
	cp $< $@
