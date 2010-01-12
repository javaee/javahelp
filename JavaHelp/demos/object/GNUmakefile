#
# @(#)GNUmakefile	1.14 06/10/30
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

# all.jar contains demo.jar and hs.jar
OBJECT_JAR = object.jar

HELP_FILES = \
	Object.hs \
	Map.jhm \
	ObjectTOC.xml \
	demo.html \
	popup_contents.html \
	popup_contents2.html \
	popup_demo.html \
	popup_gloss.html \
	secondary_contents.html \
	secondary_demo.html \
	secondary_gloss.html \
	images/hg_exam.gif \
	images/hg_note.gif \
	images/hg_proc.gif \
	images/hg_see.gif \
	images/hg_tip.gif \
	images/popup_icon.gif \
	images/rel_topic_button.gif \
	images/tb-build_u.gif \
	images/tb-project-manager_u.gif

CP=/bin/cp -f

all: $(OBJECT_JAR) ../hsjar/object.jar

../hsjar/object.jar: $(OBJECT_JAR)
	cp $(OBJECT_JAR) ../hsjar/object.jar

clean:
	rm -rf $(OBJECT_JAR)

$(OBJECT_JAR): 
	@rm -f $(OBJECT_JAR)
	cd hs ; jar cvf ../$(OBJECT_JAR) $(HELP_FILES)


