#!/bin/bash
echo " "
echo "====================================="
echo "Flare App - Automated Status Checker"
echo "====================================="
echo " "
cd Tracker
if mvn -Dtest=CheckStatus test | grep -q 'BUILD SUCCESS'; then
	echo "====================================="
	echo " "
	echo "Book Available: 'Concurrency: state models & Java programs' at Thode Library. Pick it up now."
	echo " "
	echo "Press a button to continue."
	read -n1
fi