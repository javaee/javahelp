#
# @(#)GNUmakefile	1.18 06/10/30
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
# @(#)GNUmakefile 1.3 09/30/98
#
.SUFFIXES:
.SUFFIXES: .java .class $(SUFFIXES)
.PHONY: validate

SUBDIRS = hs apidoc object jhdemo idedemo searchdemo merge newmerge browser newbrowser lib bin
JAVACFLAGS=-target 1.1

DTDPATH = ../doc/public-spec/dtd
ifeq ($(XMLHOME),)
XMLHOME = /java/re/jaxp/1.0.1_001/archive/fcs/binaries
endif

HS_FILES := $(shell find hs object merge newmerge searchdemo ../doc -name 'SCCS*' -prune -o -name '*.hs' -print)
JHM_FILES := $(shell find hs object merge newmerge searchdemo ../doc -name 'SCCS*' -prune -o -name '*.jhm' -print)
XML_FILES := $(shell find hs object merge newmerge searchdemo ../doc -name 'SCCS*' -prune -o -name '*.xml' -print)
JAXP_FILES := $(shell ls $(XMLHOME)/*.jar)

all: validate
	@for dir in $(SUBDIRS) ; do \
	   echo "" ; \
	   cd $$dir ; \
	   $(MAKE) "JAVACFLAGS=$(JAVACFLAGS)" $@ ; \
	   cd .. ; \
	done

clean:
	@for dir in $(SUBDIRS) ; do \
	   echo "" ; \
	   cd $$dir ; \
	   $(MAKE) "JAVACFLAGS=$(JAVACFLAGS)" $@ ; \
	   cd .. ; \
	done

# Validate

validate: jaxp_jars Validator.class $(XML_FILES) $(HS_FILES) $(JHM_FILES)
	export CLASSPATH; CLASSPATH=$(CLASSPATH):parser.jar:$(DTDPATH):. ; \
	java Validator $(XML_FILES) $(HS_FILES) $(JHM_FILES)

%.class: %.java
	export CLASSPATH; CLASSPATH=$(CLASSPATH):parser.jar ; \
	javac $(JAVACFLAGS) $<

jaxp_jars: $(JAXP_FILES)
	cp -f $(JAXP_FILES) ./
