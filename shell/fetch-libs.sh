#! /bin/sh

WGET=$(which wget)
PDIR="$(dirname $0)/.."

if [ $? -eq 1 ]; then
	echo wget was not found on your system!
	exit 1
fi

echo Fetching ksoap2-android...
wget -P ${PDIR}/libs http://ksoap2-android.googlecode.com/files/ksoap2-android-assembly-2.4-jar-with-dependencies.jar

