#!/bin/bash
cd /Users/asadmansoor/IdeaProjects/Tracker
if /Users/asadmansoor/apache-maven-3.3.9/bin/mvn -Dtest=CheckStatus test | grep -q 'BUILD SUCCESS'; then
	echo "Book Available: 'Concurrency: state models & Java programs' at Thode Library. Pick it up now."
	echo "Press a button to continue."
	read -n1
fi