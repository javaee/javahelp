#
# @(#)Makefile	1.3 06/10/30
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
# @(#)Makefile 1.14 07/07/98

# idehelp.jar is the JAR that contains all the help information
IDEHELP_JAR=..\..\..\hsjar\idehelp_fr.jar

# Should be conditional...
JHHOME=..\..\..\..\javahelp

INDEXER=$(JHHOME)\bin\jhindexer
JAR=jar

IDEHELP_SOURCE_FILES = \
	IdeHelp_fr.hs \
	$(IDEHELP_META_FILES) \
	$(IDEHELP_CONTENT_FILES) \
	$(IDEHELP_OTHER_FILES)

IDEHELP_META_FILES = \
	Map.jhm \
	IdeHelpTOC.xml \

IDEHELP_CONTENT_FILES = \
	idehelp\jde.html \
	idehelp\start\breakpoint.html \
	idehelp\start\browse.html \
	idehelp\start\build.html \
	idehelp\start\copyproject.html \
	idehelp\start\create.html \
	idehelp\start\customize.html \
	idehelp\start\debug.html \
	idehelp\start\edit.html \
	idehelp\start\editbasics.html \
	idehelp\start\editbuild.html \
	idehelp\start\editcode.html \
	idehelp\start\editlesson.html \
	idehelp\start\enddebug.html \
	idehelp\start\errors.html \
	idehelp\start\evaluate.html \
	idehelp\start\fixbuild.html \
	idehelp\start\flow.html \
	idehelp\start\html.html \
	idehelp\start\import.html \
	idehelp\start\hierarchy.html \
	idehelp\start\method.html \
	idehelp\start\overview.html \
	idehelp\start\portfolio.html \
	idehelp\start\profile.html \
	idehelp\start\project.html \
	idehelp\start\quickstart.html \
	idehelp\start\runpage.html \
	idehelp\start\run.html \
	idehelp\start\simpledebug.html \
	idehelp\start\startdebug.html \
	idehelp\start\starttoc.html \
	idehelp\start\step.html \
	idehelp\start\strings.html \
	idehelp\start\terminology.html \
	idehelp\start\threads.html \
	idehelp\start\tools.html \
	idehelp\menus\browse.html \
	idehelp\menus\build.html \
	idehelp\menus\debug.html \
	idehelp\menus\edit.html \
	idehelp\menus\file.html \
	idehelp\menus\help.html \
	idehelp\menus\preferences.html \
	idehelp\menus\gui.html \
	idehelp\menus\project.html \
	idehelp\menus\windows.html

IDEHELP_OTHER_FILES = \
	images\spacer2_t.gif \
	images\hg_see.gif \
	images\stages.gif \
	images\tProfile.gif \
	idehelp\images\db-eval_u.gif \
	idehelp\images\db-restart_u.gif \
	idehelp\images\db-down-frame_u.gif \
	idehelp\images\db-resume_u.gif \
	idehelp\images\db-stop-at_u.gif \
	idehelp\images\db-up-frame_u.gif \
	idehelp\images\db-run-to-here_u.gif \
	idehelp\images\db-step-into_u.gif \
	idehelp\images\db-step-over_u.gif \
	idehelp\images\file-open_u.gif \
	idehelp\images\file-save_u.gif

IDEHELP_SEARCH_FILES = \
	JavaHelpIndex\DOCS \
	JavaHelpIndex\DOCS.TAB \
	JavaHelpIndex\OFFSETS \
	JavaHelpIndex\POSITIONS \
	JavaHelpIndex\SCHEMA \
	JavaHelpIndex\TMAP

all: $(IDEHELP_JAR)

searchfiles: $(IDEHELP_SEARCH_FILES)

clean:
	del $(IDEHELP_JAR)
	del $(IDEHELP_SEARCH_FILES)

$(IDEHELP_JAR): $(IDEHELP_SOURCE_FILES) $(IDEHELP_SEARCH_FILES) ..\..\hsjar
	@del /f/q $(IDEHELP_JAR)
	$(JAR) -cvf $@ $(IDEHELP_SOURCE_FILES) $(IDEHELP_SEARCH_FILES)

$(IDEHELP_SEARCH_FILES): $(IDEHELP_CONTENT_FILES)
	@del /f/q $(IDEHELP_SEARCH_FILES)
	@mkdir JavaHelpIndex
	$(INDEXER) -locale fr -verbose $(IDEHELP_CONTENT_FILES)

..\..\..\hsjar:
	mkdir ..\..\..\hsjar
