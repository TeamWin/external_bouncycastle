#!/bin/bash

if [ -z "$ANDROID_BUILD_TOP" ]; then
    echo "Missing environment variables. Did you run build/envsetup.sh and lunch?" 1>&2
    exit 1
fi

CLASSPATH=${ANDROID_HOST_OUT}/framework/currysrc.jar:${ANDROID_HOST_OUT}/framework/android_bouncycastle_srcgen.jar
BOUNCY_CASTLE_DIR=${ANDROID_BUILD_TOP}/external/bouncycastle

cd ${ANDROID_BUILD_TOP}
make -j15 currysrc android_bouncycastle_srcgen

CORE_PLATFORM_API_FILE=${BOUNCY_CASTLE_DIR}/srcgen/core-platform-api.txt

function do_transform() {
  local SRC_IN_DIR=$1
  local SRC_OUT_DIR=$2

  if [ ! -d $SRC_OUT_DIR ]; then
    echo ${SRC_OUT_DIR} does not exist >&2
    exit 1
  fi
  rm -rf ${SRC_OUT_DIR}
  mkdir -p ${SRC_OUT_DIR}

  java -cp ${CLASSPATH} com.android.bouncycastle.srcgen.BouncyCastleTransform ${SRC_IN_DIR} ${SRC_OUT_DIR} ${CORE_PLATFORM_API_FILE}
}

BCPROV_SRC_IN_DIR=${BOUNCY_CASTLE_DIR}/bcprov/src/main/java
BCPROV_SRC_OUT_DIR=${BOUNCY_CASTLE_DIR}/android_bcprov/src/main/java
do_transform ${BCPROV_SRC_IN_DIR} ${BCPROV_SRC_OUT_DIR}
