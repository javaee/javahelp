#
# @(#)GNUmakefile	1.52 06/10/30
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

#
# @(#) GNUmakefile 1.11 - last change made 08/12/97
#
.SUFFIXES:
.SUFFIXES: .java .class $(SUFFIXES)

ifeq ($(JDKHOME),)
JDKHOME=/java/re/j2se/1.3.1/archive/fcs/solsparc
endif

CLASSPATH=.:../../javahelp/lib/jhall.jar:$(JDKHOME)/lib/tools.jar

# doclet classes
DCLASSDIR= dclasses
# the doclet package
DOCDIR=sunw/demo/doclet
DOCLET=sunw.demo.doclet.HelpDoclet
DOCLETPATH=doclet.jar:../../javahelp/lib/jhall.jar

# temporary directory for the creation of the API docs
APIDIR=apidoc

# intro file to the help
INTROFILE=intro.html

# Target package to get the API from
TARGETPKG= \
	javax.help javax.help.search javax.help.event \
	javax.help.plaf javax.help.plaf.basic \
	../../src/impl/com/sun/java/help/impl/ViewAwareComponent.java \
	../../src/impl/com/sun/java/help/impl/JHSecondaryViewer.java \
	../../src/impl/com/sun/java/help/impl/JHSecondaryViewerBeanInfo.java

JAVADOCFLAGS=-J-mx64m -J-ms64m

ALL_CLASSES=\
	$(DOCLET_CLASSES)

DOCLET_CLASSES=\
	$(DCLASSDIR)/$(DOCDIR)/HelpMemberPrototype.class \
	$(DCLASSDIR)/$(DOCDIR)/DocletAbortException.class \
	$(DCLASSDIR)/$(DOCDIR)/Configuration.class \
	$(DCLASSDIR)/$(DOCDIR)/ClassTree.class \
	$(DCLASSDIR)/$(DOCDIR)/ClassComparator.class \
	$(DCLASSDIR)/$(DOCDIR)/HtmlWriter.class \
	$(DCLASSDIR)/$(DOCDIR)/ClassWriter.class \
	$(DCLASSDIR)/$(DOCDIR)/AbstractSubWriter.class \
	$(DCLASSDIR)/$(DOCDIR)/ExecutableMemberSubWriter.class \
	$(DCLASSDIR)/$(DOCDIR)/ConstructorSubWriter.class \
	$(DCLASSDIR)/$(DOCDIR)/MethodSubWriter.class \
	$(DCLASSDIR)/$(DOCDIR)/FieldSubWriter.class \
	$(DCLASSDIR)/$(DOCDIR)/ClassNavWriter.class \
	$(DCLASSDIR)/$(DOCDIR)/TocWriter.class \
	$(DCLASSDIR)/$(DOCDIR)/IndexWriter.class \
	$(DCLASSDIR)/$(DOCDIR)/ProjectWriter.class \
	$(DCLASSDIR)/$(DOCDIR)/HelpDoclet.class

DOCLET_RESOURCES=\
	$(DCLASSDIR)/$(DOCDIR)/resources/doclet.properties

# navigator classes
NCLASSDIR= nclasses
# the navigator package
NAVDIR=sunw/demo/classviewer

NAVLET_CLASSES=\
	$(NCLASSDIR)/$(NAVDIR)/ClassViewerNavigator.class \
	$(NCLASSDIR)/$(NAVDIR)/ClassViewerView.class \
	$(NCLASSDIR)/$(NAVDIR)/plaf/basic/BasicClassViewerNavigatorUI.class \
	$(NCLASSDIR)/$(NAVDIR)/plaf/motif/MotifClassViewerNavigatorUI.class \
	$(NCLASSDIR)/$(NAVDIR)/plaf/metal/MetalClassViewerNavigatorUI.class \
	$(NCLASSDIR)/$(NAVDIR)/plaf/windows/WindowsClassViewerNavigatorUI.class


# All of them

all: doclet.jar classviewer.jar ../hsjar/apidoc.jar

# The doclet

doclet.jar: $(DCLASSDIR) $(DOCLET_CLASSES) $(DCLASSDIR)/$(DOCDIR)/resources $(DOCLET_RESOURCES)
	cd $(DCLASSDIR) ; jar -cvf ../doclet.jar *

$(DCLASSDIR):
	mkdir -p $(DCLASSDIR)

$(DCLASSDIR)/$(DOCDIR)/resources:
	mkdir -p $(DCLASSDIR)/$(DOCDIR)/resources

# The Navigator, and its data

../hsjar/apidoc.jar: $(APIDIR) apifiles $(APIDIR)/$(INTROFILE)
	cd $(APIDIR) ; jar xvf ../images.jar
	cd $(APIDIR) ; /bin/rm -rf META-INF ; jar cf ../../hsjar/apidoc.jar *

$(APIDIR)/$(INTROFILE): $(INTROFILE)
	cp -f $(INTROFILE) $(APIDIR)/$(INTROFILE)

#apifiles: doclet.jar src
apifiles: doclet.jar
	javadoc $(JAVADOCFLAGS) -d $(APIDIR) -doclet $(DOCLET) -docletpath $(DOCLETPATH) -sourcepath ../../src/new:../../src/impl $(TARGETPKG)
	touch apifiles

#src: $(SWINGHOME)/src.zip
#	mkdir -p src 
#	cd src ; jar -xf $(SWINGHOME)/src.zip

classviewer.jar:  $(NCLASSDIR) $(NAVLET_CLASSES)
	cd $(NCLASSDIR) ; jar cf ../classviewer.jar *

../hsjar:
	mkdir -p ../hsjar

$(APIDIR):
	mkdir -p $(APIDIR)

$(NCLASSDIR):
	mkdir -p $(NCLASSDIR)

# Clean them up

clean: 
	- /bin/rm -rf $(NCLASSDIR) $(DCLASSDIR) $(APIDIR) src
	- /bin/rm doclet.jar classviewer.jar apifiles

# Default rules

$(DCLASSDIR)/$(DOCDIR)/%.class: $(DOCDIR)/%.java
	export CLASSPATH; CLASSPATH=$(CLASSPATH) ; \
	javac -d $(DCLASSDIR) $<

$(DCLASSDIR)/$(DOCDIR)/resources/%.properties: $(DOCDIR)/resources/%.properties
	cp $< $@

$(NCLASSDIR)/$(NAVDIR)/%.class: $(NAVDIR)/%.java
	export CLASSPATH; CLASSPATH=$(CLASSPATH) ; \
	javac $(JAVACFLAGS) -d $(NCLASSDIR) $<

