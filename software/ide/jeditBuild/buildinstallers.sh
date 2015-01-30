#!/bin/sh
#run on Mac or Linux

#checkout v.5.1.1 of jEdit
svn co -r 23300 https://jedit.svn.sourceforge.net/svnroot/jedit/jEdit/branches/5.1.x jedit_temp


#download launch4j
if [ ! -e launch4j-3.6-macosx-x86-10.8.tar ]
   then
      curl http://iweb.dl.sourceforge.net/project/launch4j/launch4j-3/3.6/launch4j-3.6-macosx-x86-10.8.tgz > launch4j-3.6-macosx-x86-10.8.tgz
      gunzip launch4j-3.6-macosx-x86-10.8.tgz
      tar -xf launch4j-3.6-macosx-x86-10.8.tar
fi

#build launch4j
cd launch4j
ant
cd ..

mkdir -p ./jedit_temp/build/launch4j
cp -r launch4j ./jedit_temp/build
cd ./jedit_temp/build/launch4j
ant
cd -
cd jedit_temp

#copy over the most recent versions of 'ld' and 'windres' (needed for Mac installer)
mkdir -p ./build/launch4j/bin
cp ../ld-new ./build/launch4j/bin/ld
cp ../windres ./build/launch4j/bin/windres

ant dist

mkdir -p ./lib/default-plugins
cp ../../plugin/jars/Aithon.jar ./lib/default-plugins
#set -e

#copy the modified build.xml
cp ../build.xml.modified_5.1.1 ./build.xml

#copy the splash image
mkdir -p ./org/gjt/sp/jedit/icons
cp ../splash_aithon.png ./org/gjt/sp/jedit/icons/splash.png

#need for InnoSetup (needed for Windows installer)
cp ../build.properties ./build.properties

mkdir -p ./package-files/windows
cp ../win32installer.iss ./package-files/windows/win32installer.iss

#cp Aithon plugin

ant dist

