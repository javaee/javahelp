#
# @(#)GNUmakefile	1.25 06/10/30
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

# Makefile for use on Solaris using gnumake.
#
# @(#)GNUmakefile 1.23 06/21/01

# Holydays.jar is the JAR that contains all the help information
HOLIDAY_JAR=../../hsjar/holidays.jar

JHHOME = ../../../javahelp
INDEXER=$(JHHOME)/bin/jhindexer
JAR=jar

HOLIDAY_META_FILES = \
	Map.jhm \
	HolidayTOC.xml \
	HolidayTOC2.xml \
	HolidayIndex.xml \
        HolidayGlossary.xml

HOLIDAY_CONTENT_FILES := $(shell find hol -name 'SCCS*' -prune -o -name '*.html' -print)

HOLIDAY_OTHER_FILES := $(shell find hol images -name 'SCCS*' -prune -o -name '*.gif' -print)

HOLIDAY_SEARCH_FILES = \
	JavaHelpSearch/DOCS \
	JavaHelpSearch/DOCS.TAB \
	JavaHelpSearch/OFFSETS \
	JavaHelpSearch/POSITIONS \
	JavaHelpSearch/SCHEMA \
	JavaHelpSearch/TMAP

HOLIDAY_SOURCE_FILES = \
	HolidayHistory.hs \
	jhexamples.css \
      $(HOLIDAY_META_FILES) \
	$(HOLIDAY_CONTENT_FILES) \
	$(HOLIDAY_OTHER_FILES)

all:  ../../hsjar $(HOLIDAY_JAR)

searchfiles: $(HOLIDAY_SEARCH_FILES)

clean:
	rm -rf JavaHelpSearch $(HOLIDAY_JAR)

$(HOLIDAY_JAR): $(HOLIDAY_SOURCE_FILES) $(HOLIDAY_SEARCH_FILES)
	@rm -f $(HOLIDAY_JAR)
	$(JAR) cf $@ $(HOLIDAY_SOURCE_FILES) $(HOLIDAY_SEARCH_FILES)

$(HOLIDAY_SEARCH_FILES): $(HOLIDAY_CONTENT_FILES)
	@rm -f $(HOLIDAY_SEARCH_FILES)
	@mkdir -p JavaHelpSearch
	$(INDEXER) -verbose $(HOLIDAY_CONTENT_FILES)

../../hsjar:
	mkdir -p ../../hsjar
