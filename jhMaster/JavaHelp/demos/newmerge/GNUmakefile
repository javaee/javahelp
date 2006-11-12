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

# @(#)GNUmakefile 1.4 09/24/98
#
# GNUmakefile to build a simple demo of HelpSet Merging

.SUFFIXES: .java .class .properties

# JH jar
JH_JAR = ../../javahelp/lib/jh.jar

#  demo.jar is the JAR that defines the application
DEMO_JAR = newmerge.jar

CLASSPATH =$(JH_JAR):.
CLASSDIR=classes

DEMODIR=sunw/demo/newmerge

DEMO_CLASSES = \
	$(CLASSDIR)/$(DEMODIR)/Merge.class \
        $(CLASSDIR)/$(DEMODIR)/HSFilter.class \
        $(CLASSDIR)/$(DEMODIR)/JarAccessory.class 

SOURCES = \
	$(DEMO_CLASSES:$(CLASSDIR)/$(DEMODIR)/%.class=$(DEMODIR)/%.java)

CP=/bin/cp -f

all: $(CLASSDIR) sources $(DEMO_JAR)

sources: $(SOURCES)

quick: $(CLASSDIR) $(DEMO_CLASSES)

$(CLASSDIR):
	-mkdir -p $(CLASSDIR)

clean:
	rm -rf $(CLASSDIR) $(DEMO_JAR)

$(DEMO_JAR): $(DEMO_CLASSES) 
	cd $(CLASSDIR) ; jar cvfm ../$(DEMO_JAR) ../manifest *

$(CLASSDIR)/%.class: %.java
	export CLASSPATH; CLASSPATH=$(CLASSPATH) ; \
	javac -d $(CLASSDIR) $<

