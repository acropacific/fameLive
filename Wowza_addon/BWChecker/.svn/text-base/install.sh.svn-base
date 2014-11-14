#!/bin/sh
echo "Installing BWChecker..."
if [ -d /Library/WowzaMediaServer ]
then
	cd /Library/WowzaMediaServer/examples/BWChecker
else
	cd /usr/local/WowzaMediaServer/examples/BWChecker
fi

cp lib/* ../../lib/

if [ ! -d ../../applications/bwcheck ]; then
	cp -R conf/* ../../conf/
	mkdir ../../applications/bwcheck
fi
