#Add to PATH variable to make sure that the correct version of Perl is
#used and that pbmplus is available for converting graphics

#PATH=/set/misc/local/bin:/set/misc/local/bin/pbmplus:$PATH

PATH=/usr/local/bin:/set/misc/local/bin/pbmplus:$PATH

export PATH

/home/larryh/bin/html2ps \
index.html \
Overview.html \
FileFormat.html \
I18N.html \
Customization.html \
Beans.html \
CSH.html \
Search.html \
Merge.html \
Changes.html \
Classes.html \
misc/Scenarios.html \
misc/RefImpl.html \
misc/JComponents.html \
misc/DefaultSearch.html \
misc/Jar.html \
Copyright.html > spec.ps
