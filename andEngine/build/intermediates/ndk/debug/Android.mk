LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE := andengine_shared
LOCAL_LDFLAGS := -Wl,--build-id
LOCAL_SRC_FILES := \
	/home/pranav/Desktop/Work/M-Haxball/DiskSoccerMultiplayer/andEngine/src/main/jni/src/GLES20Fix.c \
	/home/pranav/Desktop/Work/M-Haxball/DiskSoccerMultiplayer/andEngine/src/main/jni/src/BufferUtils.cpp \
	/home/pranav/Desktop/Work/M-Haxball/DiskSoccerMultiplayer/andEngine/src/main/jni/Android.mk \
	/home/pranav/Desktop/Work/M-Haxball/DiskSoccerMultiplayer/andEngine/src/main/jni/build.sh \
	/home/pranav/Desktop/Work/M-Haxball/DiskSoccerMultiplayer/andEngine/src/main/jni/Application.mk \

LOCAL_C_INCLUDES += /home/pranav/Desktop/Work/M-Haxball/DiskSoccerMultiplayer/andEngine/src/main/jni
LOCAL_C_INCLUDES += /home/pranav/Desktop/Work/M-Haxball/DiskSoccerMultiplayer/andEngine/src/debug/jni

include $(BUILD_SHARED_LIBRARY)
