#!/usr/bin/env bash

set -e

if [ "$BUILD_GROUP" == "instrument" ]; then

    mkdir -p ${ANDROID_HOME}licenses
    echo -e "\n8933bad161af4178b1185d1a37fbf41ea5269c55" > ${ANDROID_HOME}licenses/android-sdk-license

    ${ANDROID_HOME}tools/bin/sdkmanager --channel=0 "system-images;android-19;default;armeabi-v7a" "emulator"

    echo no | ${ANDROID_HOME}tools/bin/avdmanager create avd --force -n test --abi "armeabi-v7a" -k "system-images;android-19;default;armeabi-v7a" --device "3.2in QVGA (ADP2)"
    ${ANDROID_HOME}emulator/emulator -avd test -no-audio -netfast -no-window &

else
    echo "$BUILD_GROUP is unknown"
fi

exit 0