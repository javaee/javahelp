#
# @(#)Makefile	1.29 06/10/30
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


.SUFFIXES:
.SUFFIXES: .java .class .properties $(SUFFIXES)

# JAVAC FLAGS


CLASSPATH= .;..\..\javahelp\lib\jhall.jar

# doclet classes
DCLASSDIR= dclasses
# the doclet package
DOCDIR= sunw\demo\doclet
DOCLET= sunw.demo.doclet.HelpDoclet
DOCLETPATH=doclet.jar:..\..\javahelp\lib\jhall.jar

# temporary directory for the creation of the API docs
APIDIR=apidoc
# Target package to get the API from
TARGETPKG= \
	javax.javahelp javax.javahelp.search javax.javahelp.event \
	javax.javahelp.plaf

JAVADOCFLAGS=-J-mx32m -J-ms32m

ALL_CLASSES= \
	$(DOCLET_CLASSES)

DOCLET_CLASSES= \
	$(DCLASSDIR)\$(DOCDIR)\HelpMemberPrototype.class \
	$(DCLASSDIR)\$(DOCDIR)\DocletAbortException.class \
	$(DCLASSDIR)\$(DOCDIR)\Configuration.class \
	$(DCLASSDIR)\$(DOCDIR)\ClassTree.class \
	$(DCLASSDIR)\$(DOCDIR)\ClassComparator.class \
	$(DCLASSDIR)\$(DOCDIR)\HtmlWriter.class \
	$(DCLASSDIR)\$(DOCDIR)\ClassWriter.class \
	$(DCLASSDIR)\$(DOCDIR)\AbstractSubWriter.class \
	$(DCLASSDIR)\$(DOCDIR)\ExecutableMemberSubWriter.class \
	$(DCLASSDIR)\$(DOCDIR)\ConstructorSubWriter.class \
	$(DCLASSDIR)\$(DOCDIR)\MethodSubWriter.class \
	$(DCLASSDIR)\$(DOCDIR)\FieldSubWriter.class \
	$(DCLASSDIR)\$(DOCDIR)\ClassNavWriter.class \
	$(DCLASSDIR)\$(DOCDIR)\TocWriter.class \
	$(DCLASSDIR)\$(DOCDIR)\IndexWriter.class \
	$(DCLASSDIR)\$(DOCDIR)\ProjectWriter.class \
	$(DCLASSDIR)\$(DOCDIR)\HelpDoclet.class

DOCLET_RESOURCES=\
	$(DCLASSDIR)\$(DOCDIR)\resources\doclet.properties

# navigator classes
NCLASSDIR= nclasses
# the navigator package
NAVDIR= sunw\demo\classviewer

NAVLET_CLASSES= \
	$(NCLASSDIR)\$(NAVDIR)\ClassViewerNavigator.class \
	$(NCLASSDIR)\$(NAVDIR)\plaf\basic\BasicClassViewerNavigatorUI.class \
	$(NCLASSDIR)\$(NAVDIR)\plaf\motif\MotifClassViewerNavigatorUI.class \
	$(NCLASSDIR)\$(NAVDIR)\plaf\metal\MetalClassViewerNavigatorUI.class \
	$(NCLASSDIR)\$(NAVDIR)\plaf\windows\WindowsClassViewerNavigatorUI.class


# All of them

all: doclet.jar classviewer.jar ..\hsjar\apidoc.jar

# The doclet

doclet.jar: $(DCLASSDIR) $(DOCLET_CLASSES) $(DCLASSDIR)\$(DOCDIR)\resources $(DOCLET_RESOURCES)
	cd $(DCLASSDIR)
	jar -cf ..\doclet.jar *
	cd ..

$(DCLASSDIR):
	-mkdir $(DCLASSDIR)

$(DCLASSDIR)\$(DOCDIR)\resources:
	-mkdir $(DCLASSDIR)\$(DOCDIR)\resources

# The Navigator, and its data

..\hsjar\apidoc.jar: $(APIDIR) apifiles
	cd $(APIDIR)
	jar xf ..\images.jar
	-deltree META-INF
	jar cf ..\..\hsjar\apidoc.jar *
	cd ..

apifiles: doclet.jar
	- javadoc $(JAVADOCFLAGS) -d $(APIDIR) -doclet $(DOCLET) -docletpath $(DOCLETPATH) -sourcepath ..\..\src\new:..\..\src\impl $(TARGETPKG)

classviewer.jar: $(NCLASSDIR) $(NAVLET_CLASSES)
	cd $(NCLASSDIR)
	jar cf ..\classviewer.jar *
	cd ..

$(APIDIR):
	mkdir $(APIDIR)

$(NCLASSDIR):
	mkdir $(NCLASSDIR)


# Clean them up

clean: 
	- deltree $(NCLASSDIR)
        - deltree $(DCLASSDIR)
	- deltree $(APIDIR)
	- del doclet.jar apidoc.jar classviewer.jar ..\..\hs\apidoc\apidoc.jar

# default rules

{$(DOCDIR)}.java{$(DCLASSDIR)\$(DOCDIR)}.class :
	set CLASSPATH=$(CLASSPATH)
	javac $(JCFLAGS) -d $(DCLASSDIR) $<

{$(DOCDIR)\resources}.properties{$(DCLASSDIR)\$(DOCDIR)\resources}.properties :
	copy $< $@

{$(NAVDIR)}.java{$(NCLASSDIR)\$(NAVDIR)}.class :
	set CLASSPATH=$(CLASSPATH)
	javac -d $(NCLASSDIR) $<

{$(NAVDIR)\plaf\basic}.java{$(NCLASSDIR)\$(NAVDIR)\plaf\basic}.class :
	set CLASSPATH=$(CLASSPATH)
	javac -d $(NCLASSDIR) $<

{$(NAVDIR)\plaf\motif}.java{$(NCLASSDIR)\$(NAVDIR)\plaf\motif}.class :
	set CLASSPATH=$(CLASSPATH)
	javac -d $(NCLASSDIR) $<

{$(NAVDIR)\plaf\windows}.java{$(NCLASSDIR)\$(NAVDIR)\plaf\windows}.class :
	set CLASSPATH=$(CLASSPATH)
	javac -d $(NCLASSDIR) $<

{$(NAVDIR)\plaf\metal}.java{$(NCLASSDIR)\$(NAVDIR)\plaf\metal}.class :
	set CLASSPATH=$(CLASSPATH)
	javac -d $(NCLASSDIR) $<

