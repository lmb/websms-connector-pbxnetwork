#! /bin/sh

ODIR="$PWD"
SDIR="$(dirname $0)"
cd "$SDIR/.."

VER=$(grep -E 'android:versionName=".*?"' AndroidManifest.xml | grep -oP '\d+\.\d+')

"$SDIR/preDeploy.sh"
ant release < ../release.ks.pw
"$SDIR/postDeploy.sh"

cp -f ./bin/*-release.apk ./bin/websms-connector-pbxnetwork-$VER.apk

cd $ODIR
