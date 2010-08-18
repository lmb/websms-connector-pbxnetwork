#! /bin/sh

WGET=$(which wget)

if [ $? -eq 1 ]; then
	echo wget was not found on your system!
	exit 1
fi

echo Fetching ksoap2-android...
wget -P lib http://ksoap2-android.googlecode.com/files/ksoap2-android-assembly-2.4-jar-with-dependencies.jar
