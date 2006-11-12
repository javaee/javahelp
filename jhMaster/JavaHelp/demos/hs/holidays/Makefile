#
# @(#)Makefile	1.21 06/10/30
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
# @(#)Makefile 1.21 10/30/06

# Holidays.jar is the JAR that contains all the help information
HOLIDAY_JAR=..\..\hsjar\holidays.jar

# Should be conditional...
JHHOME=..\..\..\javahelp

INDEXER=$(JHHOME)\bin\jhindexer
JAR=jar

HOLIDAY_SOURCE_FILES = \
	HolidayHistory.hs \
	$(HOLIDAY_META_FILES) \
	$(HOLIDAY_CONTENT_FILES) \
	$(HOLIDAY_OTHER_FILES)

HOLIDAY_META_FILES = \
	Map.jhm \
	HolidayTOC.xml \
	HolidayTOC2.xml \
	HolidayIndex.xml \
        HolidayGlossary.xml

HOLIDAY_CONTENT_FILES = \
	hol\4th.html \
	hol\5may.html \
	hol\allthanks.html \
	hol\bach.html \
	hol\beatles.html \
	hol\dday.html \
	hol\fathers.html \
	hol\fool.html \
	hol\guy.html \
	hol\hall.html \
	hol\hol.html \
	hol\ides.html \
	hol\inforefo.html \
	hol\jacko.html \
	hol\jerusalem1.html \
	hol\jerusalem2.html \
	hol\jerusalem3.html \
	hol\love.html \
	hol\luther.html \
	hol\memorial.html \
	hol\passover.html \
	hol\pat.html \
	hol\rosh.html \
	hol\startrek.html \
	hol\thanks.html \
	hol\thanks2.html \
	hol\thanks3.html \
	hol\thanks4.html \
	hol\val.html \
	hol\ve.html \
	hol\war.html \
	hol\yom.html \
        hol\gloss_ash.html \
        hol\gloss_cinco.html \
        hol\gloss_easter.html \
        hol\gloss_eucharist.html \
        hol\gloss_fawkes.html \
        hol\gloss_friday.html \
        hol\gloss_halloween.html \
        hol\gloss_herod.html \
        hol\gloss_saturday.html \
	hol\easter\ash.html \
	hol\easter\easter.html \
	hol\easter\friday.html \
	hol\easter\herod.html \
	hol\easter\hist.html \
	hol\easter\now.html \
	hol\easter\palm.html \
	hol\easter\pilate.html \
	hol\easter\sanhed.html \
	hol\easter\trial.html \
	hol\xmas\augustus.html \
	hol\xmas\augustus2.html \
	hol\xmas\carols.html \
	hol\xmas\chan12.html \
	hol\xmas\creche.html \
	hol\xmas\date.html \
	hol\xmas\handel.html \
	hol\xmas\herod12.html \
	hol\xmas\lucia.html \
	hol\xmas\magi.html \
	hol\xmas\music.html \
	hol\xmas\poinsett.html \
	hol\xmas\santa2.html \
	hol\xmas\season.html \
	hol\xmas\star12.html \
	hol\xmas\trad.html \
	hol\xmas\tree.html \
	hol\xmas\visit.html \
	hol\xmas\year.html

HOLIDAY_OTHER_FILES = \
	hol\images\calendar.gif \
	hol\images\rule.gif \
	images\toplevel.gif

HOLIDAY_SEARCH_FILES = \
	JavaHelpIndex\DOCS \
	JavaHelpIndex\DOCS.TAB \
	JavaHelpIndex\OFFSETS \
	JavaHelpIndex\POSITIONS \
	JavaHelpIndex\SCHEMA \
	JavaHelpIndex\TMAP

all: $(HOLIDAY_JAR)

searchfiles: $(HOLIDAY_SEARCH_FILES)

clean:
	del $(HOLIDAY_JAR)
	del $(HOLIDAY_SEARCH_FILES)

$(HOLIDAY_JAR): $(HOLIDAY_SOURCE_FILES) $(HOLIDAY_SEARCH_FILES) ..\..\hsjar
	@del /f/q $(HOLIDAY_JAR)
	$(JAR) -cvf $@ $(HOLIDAY_SOURCE_FILES) $(HOLIDAY_SEARCH_FILES)

$(HOLIDAY_SEARCH_FILES): $(HOLIDAY_CONTENT_FILES)
	@del /f/q $(HOLIDAY_SEARCH_FILES)
	@mkdir JavaHelpIndex
	$(INDEXER) $(HOLIDAY_CONTENT_FILES)

..\..\hsjar:
	mkdir ..\..\hsjar


