#!/usr/bin/env bash

if [ "$BUILD_GROUP" == "unit" ]; then
    ./gradlew :app:assembleDebug :app:testDebug :app:lintDebug
elif [ "$BUILD_GROUP" == "instrument" ]; then

    export ADB_INSTALL_TIMEOUT=30

    ./gradlew :app:assembleDebug :app:assembleDebugAndroidTest

    which android-wait-for-emulator > /dev/null

    if [ "$?" -eq 0 ]; then
      echo "Waiting for emulator..."
      android-wait-for-emulator
    fi

    ./gradlew :app:connectedDebugAndroidTest
else
    echo "$BUILD_GROUP is an unknown group"
    exit 1
fi
