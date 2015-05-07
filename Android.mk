LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := customLogger
LOCAL_SRC_FILES := $(call all-java-files-under,library/src/main/java)

include $(BUILD_STATIC_JAVA_LIBRARY)
