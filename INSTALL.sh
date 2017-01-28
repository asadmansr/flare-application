#!/bin/bash
sudo cp /Applications/FlareApp/com.flareapp.startjob.plist /Library/LaunchDaemons/com.flareapp.startjob.plist
launchctl load -w /Library/LaunchDaemons/com.flareapp.startjob.plist