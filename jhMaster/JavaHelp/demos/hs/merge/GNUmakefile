#
# @(#)GNUmakefile	1.6 06/10/30
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

# @(#)GNUmakefile 1.6 10/30/06
#
# Makefile for use on Solaris using gnumake.

# idedemo.jar is the JAR that contains all the help information
HELP_JAR=../../hsjar/master.jar

JHHOME = ../../../javahelp
INDEXER=$(JHHOME)/bin/jhindexer
JAR=jar

HELP_META_FILES = \
	Master.jhm \
	MasterTOC.xml \
	MasterIndex.xml

HELP_CONTENT_FILES := $(shell find master -name 'SCCS*' -prune -o -name '*.html' -print)

HELP_OTHER_FILES := $(shell find master -name 'SCCS*' -prune -o -name '*.gif' -print)

HELP_SEARCH_FILES = \
	MasterSearchIndex/DOCS \
	MasterSearchIndex/DOCS.TAB \
	MasterSearchIndex/OFFSETS \
	MasterSearchIndex/POSITIONS \
	MasterSearchIndex/SCHEMA \
	MasterSearchIndex/TMAP

HELP_SOURCE_FILES = \
	Master.hs \
	$(HELP_META_FILES) \
	$(HELP_CONTENT_FILES) \
	$(HELP_OTHER_FILES)

all: ../../hsjar $(HELP_JAR)

searchfiles: $(HELP_SEARCH_FILES)

clean:
	rm -rf MasterSearchIndex $(HELP_JAR)

$(HELP_JAR): $(HELP_SOURCE_FILES) $(HELP_SEARCH_FILES)
	@rm -f $(HELP_JAR)
	$(JAR) cf $@ $(HELP_SOURCE_FILES) $(HELP_SEARCH_FILES)

$(HELP_SEARCH_FILES): $(HELP_CONTENT_FILES)
	@rm -f $(HELP_SEARCH_FILES)
	@mkdir -p MasterSearchIndex
	$(INDEXER) -db MasterSearchIndex -verbose $(HELP_CONTENT_FILES)

../../hsjar:
	mkdir -p ../../hsjar
