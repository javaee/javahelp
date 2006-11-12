#
# @(#)GNUmakefile	1.22 06/10/30
# 
# Copyright (c) 2006 Sun Microsystems, Inc.  All Rights Reserved.
# DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
# 
# This code is free software; you can redistribute it and/or modify it
# under the terms of the GNU General Public License version 2 only, as
# published by the Free Software Foundation.  Sun designates this
# particular file as subject to the "Classpath" exception as provided
# by Sun in the LICENSE file that accompanied this code.
# 
# This code is distributed in the hope that it will be useful, but WITHOUT
# ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
# FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
# version 2 for more details (a copy is included in the LICENSE file that
# accompanied this code).
# 
# You should have received a copy of the GNU General Public License version
# 2 along with this work; if not, write to the Free Software Foundation,
# Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
# 
# Please contact Sun Microsystems, Inc., 4150 Network Circle, Santa Clara,
# CA 95054 USA or visit www.sun.com if you need additional information or
# have any questions.
#

# @(#)GNUmakefile	1.10 97/06/05
#
# Makefile for use on Solaris using gnumake.
# It assumes JavaHelp has been built previously
# Just builds the search index for the user's guide
# Then build the jhug.pdf file. 
# Note: You must set your path correctly for html2ps, perl, and distill
#       For building at JavaSoft this means making sure path includes
#       /net/suntools.sfbay/export/tools/sparc/bin 

JHHOME = ../../javahelp/
INDEXER=$(JHHOME)/bin/jhindexer

JHUG_CONTENT_FILES = jhug.html \
	release/release.html \
	release/contents.html \
	release/requirements.html \
	release/changes.html \
	release/demos.html \
	release/helpsets.html \
	release/libraries.html \
	release/limitations.html \
	release/file_list.html \
	overview/overview.html \
	overview/intro.html \
	overview/features.html \
	overview/scenarios.html \
	overview/invocation.html \
	overview/deploy.html \
	overview/search.html \
	overview/jcomp.html \
	author/author.html \
	author/viewing.html \
	author/accessibility.html \
	author/setup.html \
	author/helpset.html \
	author/map.html \
	author/jar.html \
	author/toc.html \
	author/index.html \
	author/csh.html \
	author/search.html \
	author/searchdb.html \
	author/jhindexer.html \
	author/jhsearch.html \
	author/popup.html \
	author/merge.html \
	dev/dev.html \
	dev/basics.html \
	dev/csh.html \
      dev/dynamicmerge.html \
	dev/embed.html \
	dev/lwcomp.html \
	dev/serverhlp.html \
	locale/loc.html \
	locale/loc_pres.html \
	locale/loc_helpset.html \
	locale/loc_xml.html \
	locale/loc_html.html \
	locale/loc_font.html \
	locale/loc_search.html\
      jhugIndex.html 

JHUG_SEARCH_FILES = \
	JavaHelpSearch/DOCS \
	JavaHelpSearch/DOCS.TAB \
	JavaHelpSearch/OFFSETS \
	JavaHelpSearch/POSITIONS \
	JavaHelpSearch/SCHEMA \
	JavaHelpSearch/TMAP

JHUG_SOURCE_FILES = \
	$(JHUG_CONTENT_FILES) \ 

all: $(JHUG_SEARCH_FILES)

clean:
	rm -f $(JHUG_SEARCH_FILES)

$(JHUG_SEARCH_FILES): $(JHUG_CONTENT_FILES)
	@rm -f $(JHUG_SEARCH_FILES)
	@mkdir -p JavaHelpSearch
	ksh $(INDEXER) -verbose $(JHUG_CONTENT_FILES)

# jhug.pdf: $(JHUG_CONTENT_FILES)
#	@rm -f jhug.pdf
#	perl5 ../../build/tools/html2ps -u -t -C hb $(JHUG_CONTENT_FILES) > jhug.ps
#	distill jhug.ps
#	$(RM) jhug.ps
