LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE := andenginephysicsbox2dextension
LOCAL_LDFLAGS := -Wl,--build-id
LOCAL_SRC_FILES := \
	/home/pranav/Desktop/Work/M-Haxball/DiskSoccerMultiplayer/andEnginePhysicsBox2DExtension/src/main/jni/build.bat \
	/home/pranav/Desktop/Work/M-Haxball/DiskSoccerMultiplayer/andEnginePhysicsBox2DExtension/src/main/jni/Android.mk \
	/home/pranav/Desktop/Work/M-Haxball/DiskSoccerMultiplayer/andEnginePhysicsBox2DExtension/src/main/jni/build.sh \
	/home/pranav/Desktop/Work/M-Haxball/DiskSoccerMultiplayer/andEnginePhysicsBox2DExtension/src/main/jni/Box2D/GearJoint.cpp \
	/home/pranav/Desktop/Work/M-Haxball/DiskSoccerMultiplayer/andEnginePhysicsBox2DExtension/src/main/jni/Box2D/PrismaticJoint.cpp \
	/home/pranav/Desktop/Work/M-Haxball/DiskSoccerMultiplayer/andEnginePhysicsBox2DExtension/src/main/jni/Box2D/Manifold.cpp \
	/home/pranav/Desktop/Work/M-Haxball/DiskSoccerMultiplayer/andEnginePhysicsBox2DExtension/src/main/jni/Box2D/MouseJoint.cpp \
	/home/pranav/Desktop/Work/M-Haxball/DiskSoccerMultiplayer/andEnginePhysicsBox2DExtension/src/main/jni/Box2D/Dynamics/b2World.cpp \
	/home/pranav/Desktop/Work/M-Haxball/DiskSoccerMultiplayer/andEnginePhysicsBox2DExtension/src/main/jni/Box2D/Dynamics/b2Island.cpp \
	/home/pranav/Desktop/Work/M-Haxball/DiskSoccerMultiplayer/andEnginePhysicsBox2DExtension/src/main/jni/Box2D/Dynamics/b2Body.cpp \
	/home/pranav/Desktop/Work/M-Haxball/DiskSoccerMultiplayer/andEnginePhysicsBox2DExtension/src/main/jni/Box2D/Dynamics/b2Fixture.cpp \
	/home/pranav/Desktop/Work/M-Haxball/DiskSoccerMultiplayer/andEnginePhysicsBox2DExtension/src/main/jni/Box2D/Dynamics/Contacts/b2PolygonContact.cpp \
	/home/pranav/Desktop/Work/M-Haxball/DiskSoccerMultiplayer/andEnginePhysicsBox2DExtension/src/main/jni/Box2D/Dynamics/Contacts/b2CircleContact.cpp \
	/home/pranav/Desktop/Work/M-Haxball/DiskSoccerMultiplayer/andEnginePhysicsBox2DExtension/src/main/jni/Box2D/Dynamics/Contacts/b2Contact.cpp \
	/home/pranav/Desktop/Work/M-Haxball/DiskSoccerMultiplayer/andEnginePhysicsBox2DExtension/src/main/jni/Box2D/Dynamics/Contacts/b2ContactSolver.cpp \
	/home/pranav/Desktop/Work/M-Haxball/DiskSoccerMultiplayer/andEnginePhysicsBox2DExtension/src/main/jni/Box2D/Dynamics/Contacts/b2PolygonAndCircleContact.cpp \
	/home/pranav/Desktop/Work/M-Haxball/DiskSoccerMultiplayer/andEnginePhysicsBox2DExtension/src/main/jni/Box2D/Dynamics/Contacts/b2TOISolver.cpp \
	/home/pranav/Desktop/Work/M-Haxball/DiskSoccerMultiplayer/andEnginePhysicsBox2DExtension/src/main/jni/Box2D/Dynamics/b2WorldCallbacks.cpp \
	/home/pranav/Desktop/Work/M-Haxball/DiskSoccerMultiplayer/andEnginePhysicsBox2DExtension/src/main/jni/Box2D/Dynamics/b2ContactManager.cpp \
	/home/pranav/Desktop/Work/M-Haxball/DiskSoccerMultiplayer/andEnginePhysicsBox2DExtension/src/main/jni/Box2D/Dynamics/Joints/b2DistanceJoint.cpp \
	/home/pranav/Desktop/Work/M-Haxball/DiskSoccerMultiplayer/andEnginePhysicsBox2DExtension/src/main/jni/Box2D/Dynamics/Joints/b2RevoluteJoint.cpp \
	/home/pranav/Desktop/Work/M-Haxball/DiskSoccerMultiplayer/andEnginePhysicsBox2DExtension/src/main/jni/Box2D/Dynamics/Joints/b2MouseJoint.cpp \
	/home/pranav/Desktop/Work/M-Haxball/DiskSoccerMultiplayer/andEnginePhysicsBox2DExtension/src/main/jni/Box2D/Dynamics/Joints/b2GearJoint.cpp \
	/home/pranav/Desktop/Work/M-Haxball/DiskSoccerMultiplayer/andEnginePhysicsBox2DExtension/src/main/jni/Box2D/Dynamics/Joints/b2WeldJoint.cpp \
	/home/pranav/Desktop/Work/M-Haxball/DiskSoccerMultiplayer/andEnginePhysicsBox2DExtension/src/main/jni/Box2D/Dynamics/Joints/b2LineJoint.cpp \
	/home/pranav/Desktop/Work/M-Haxball/DiskSoccerMultiplayer/andEnginePhysicsBox2DExtension/src/main/jni/Box2D/Dynamics/Joints/b2FrictionJoint.cpp \
	/home/pranav/Desktop/Work/M-Haxball/DiskSoccerMultiplayer/andEnginePhysicsBox2DExtension/src/main/jni/Box2D/Dynamics/Joints/b2PulleyJoint.cpp \
	/home/pranav/Desktop/Work/M-Haxball/DiskSoccerMultiplayer/andEnginePhysicsBox2DExtension/src/main/jni/Box2D/Dynamics/Joints/b2Joint.cpp \
	/home/pranav/Desktop/Work/M-Haxball/DiskSoccerMultiplayer/andEnginePhysicsBox2DExtension/src/main/jni/Box2D/Dynamics/Joints/b2PrismaticJoint.cpp \
	/home/pranav/Desktop/Work/M-Haxball/DiskSoccerMultiplayer/andEnginePhysicsBox2DExtension/src/main/jni/Box2D/FrictionJoint.cpp \
	/home/pranav/Desktop/Work/M-Haxball/DiskSoccerMultiplayer/andEnginePhysicsBox2DExtension/src/main/jni/Box2D/Android.mk \
	/home/pranav/Desktop/Work/M-Haxball/DiskSoccerMultiplayer/andEnginePhysicsBox2DExtension/src/main/jni/Box2D/RevoluteJoint.cpp \
	/home/pranav/Desktop/Work/M-Haxball/DiskSoccerMultiplayer/andEnginePhysicsBox2DExtension/src/main/jni/Box2D/Contact.cpp \
	/home/pranav/Desktop/Work/M-Haxball/DiskSoccerMultiplayer/andEnginePhysicsBox2DExtension/src/main/jni/Box2D/Shape.cpp \
	/home/pranav/Desktop/Work/M-Haxball/DiskSoccerMultiplayer/andEnginePhysicsBox2DExtension/src/main/jni/Box2D/World.cpp \
	/home/pranav/Desktop/Work/M-Haxball/DiskSoccerMultiplayer/andEnginePhysicsBox2DExtension/src/main/jni/Box2D/LineJoint.cpp \
	/home/pranav/Desktop/Work/M-Haxball/DiskSoccerMultiplayer/andEnginePhysicsBox2DExtension/src/main/jni/Box2D/CircleShape.cpp \
	/home/pranav/Desktop/Work/M-Haxball/DiskSoccerMultiplayer/andEnginePhysicsBox2DExtension/src/main/jni/Box2D/Common/b2Settings.cpp \
	/home/pranav/Desktop/Work/M-Haxball/DiskSoccerMultiplayer/andEnginePhysicsBox2DExtension/src/main/jni/Box2D/Common/b2Math.cpp \
	/home/pranav/Desktop/Work/M-Haxball/DiskSoccerMultiplayer/andEnginePhysicsBox2DExtension/src/main/jni/Box2D/Common/b2BlockAllocator.cpp \
	/home/pranav/Desktop/Work/M-Haxball/DiskSoccerMultiplayer/andEnginePhysicsBox2DExtension/src/main/jni/Box2D/Common/b2StackAllocator.cpp \
	/home/pranav/Desktop/Work/M-Haxball/DiskSoccerMultiplayer/andEnginePhysicsBox2DExtension/src/main/jni/Box2D/Body.cpp \
	/home/pranav/Desktop/Work/M-Haxball/DiskSoccerMultiplayer/andEnginePhysicsBox2DExtension/src/main/jni/Box2D/DistanceJoint.cpp \
	/home/pranav/Desktop/Work/M-Haxball/DiskSoccerMultiplayer/andEnginePhysicsBox2DExtension/src/main/jni/Box2D/PulleyJoint.cpp \
	/home/pranav/Desktop/Work/M-Haxball/DiskSoccerMultiplayer/andEnginePhysicsBox2DExtension/src/main/jni/Box2D/Joint.cpp \
	/home/pranav/Desktop/Work/M-Haxball/DiskSoccerMultiplayer/andEnginePhysicsBox2DExtension/src/main/jni/Box2D/Collision/b2TimeOfImpact.cpp \
	/home/pranav/Desktop/Work/M-Haxball/DiskSoccerMultiplayer/andEnginePhysicsBox2DExtension/src/main/jni/Box2D/Collision/b2Distance.cpp \
	/home/pranav/Desktop/Work/M-Haxball/DiskSoccerMultiplayer/andEnginePhysicsBox2DExtension/src/main/jni/Box2D/Collision/b2DynamicTree.cpp \
	/home/pranav/Desktop/Work/M-Haxball/DiskSoccerMultiplayer/andEnginePhysicsBox2DExtension/src/main/jni/Box2D/Collision/b2CollideCircle.cpp \
	/home/pranav/Desktop/Work/M-Haxball/DiskSoccerMultiplayer/andEnginePhysicsBox2DExtension/src/main/jni/Box2D/Collision/Shapes/b2CircleShape.cpp \
	/home/pranav/Desktop/Work/M-Haxball/DiskSoccerMultiplayer/andEnginePhysicsBox2DExtension/src/main/jni/Box2D/Collision/Shapes/b2PolygonShape.cpp \
	/home/pranav/Desktop/Work/M-Haxball/DiskSoccerMultiplayer/andEnginePhysicsBox2DExtension/src/main/jni/Box2D/Collision/b2Collision.cpp \
	/home/pranav/Desktop/Work/M-Haxball/DiskSoccerMultiplayer/andEnginePhysicsBox2DExtension/src/main/jni/Box2D/Collision/b2BroadPhase.cpp \
	/home/pranav/Desktop/Work/M-Haxball/DiskSoccerMultiplayer/andEnginePhysicsBox2DExtension/src/main/jni/Box2D/Collision/b2CollidePolygon.cpp \
	/home/pranav/Desktop/Work/M-Haxball/DiskSoccerMultiplayer/andEnginePhysicsBox2DExtension/src/main/jni/Box2D/PolygonShape.cpp \
	/home/pranav/Desktop/Work/M-Haxball/DiskSoccerMultiplayer/andEnginePhysicsBox2DExtension/src/main/jni/Box2D/Fixture.cpp \
	/home/pranav/Desktop/Work/M-Haxball/DiskSoccerMultiplayer/andEnginePhysicsBox2DExtension/src/main/jni/Box2D/ContactImpulse.cpp \
	/home/pranav/Desktop/Work/M-Haxball/DiskSoccerMultiplayer/andEnginePhysicsBox2DExtension/src/main/jni/Application.mk \

LOCAL_C_INCLUDES += /home/pranav/Desktop/Work/M-Haxball/DiskSoccerMultiplayer/andEnginePhysicsBox2DExtension/src/main/jni
LOCAL_C_INCLUDES += /home/pranav/Desktop/Work/M-Haxball/DiskSoccerMultiplayer/andEnginePhysicsBox2DExtension/src/release/jni

include $(BUILD_SHARED_LIBRARY)
