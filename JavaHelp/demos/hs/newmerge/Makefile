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

# Makefile for use with Microsoft's nmake; it assumes your Java bin directory
# is in your path, and that a CLASSPATH environment variable is defined.
#
# @(#)Makefile 1.2 07/06/98

# newmerge.jar is the JAR that contains all the help information
HELP_JAR=..\..\hsjar\newmerge.jar

# Should be conditional...
JHHOME=..\..\..\javahelp

INDEXER=$(JHHOME)\bin\jhindexer
JAR=jar

HELP_SOURCE_FILES = \
	MergeHelp.hs \
	$(HELP_META_FILES) \
	$(HELP_CONTENT_FILES) \
	$(HELP_OTHER_FILES)

HELP_META_FILES = \
	Map.jhm \
	MergeTOC.xml \
	MergeIndex.xml

HELP_CONTENT_FILES = \
	Pages\Introduction.html \
        Pages\add.html \
        Pages\main.html \
        Pages\view.html

HELP_OTHER_FILES = \
        Pages\add.jpg \
        Pages\view.jpg \
        Pages\hg_proc.gif

HELP_SEARCH_FILES = \
	JavaHelpSearch\DOCS \
	JavaHelpSearch\DOCS.TAB \
	JavaHelpSearch\OFFSETS \
	JavaHelpSearch\POSITIONS \
	JavaHelpSearch\SCHEMA \
	JavaHelpSearch\TMAP

all: $(HELP_JAR)

searchfiles: $(HELP_SEARCH_FILES)

clean:
	del $(HELP_JAR)
	del $(HELP_SEARCH_FILES)

$(HELP_JAR): $(HELP_SOURCE_FILES) $(HELP_SEARCH_FILES)
	@del /f/q $(HELP_JAR)
	$(JAR) -cvf $@ $(HELP_SOURCE_FILES) $(HELP_SEARCH_FILES)

$(HELP_SEARCH_FILES): $(HELP_CONTENT_FILES)
	@del /f/q $(HELP_SEARCH_FILES)
	@mkdir JavaHelpSearch
	$(INDEXER) -db JavaHelpSearch -verbose $(HELP_CONTENT_FILES)

..\..\hsjar:
	mkdir ..\..\hsjar
