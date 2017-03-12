#!/usr/bin/env bash

set -e

if [ "$BUILD_GROUP" == "instrument" ]; then
    android-update-sdk --components=sys-img-armeabi-v7a-android-19 --accept-licenses='android-sdk-license-[0-9a-f]{8}'
    echo no | android create avd --force -n test -t android-19 --abi armeabi-v7a --skin QVGA
    emulator -avd test -no-audio -no-skin -netfast -no-window &
else
    echo "$BUILD_GROUP is unknown"
fi

exit 0