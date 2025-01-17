package com.android.camera.videoplayer;

public enum PlayerMessageState {
    SETTING_NEW_PLAYER,
    IDLE,
    INITIALIZED,
    PREPARING,
    PREPARED,
    STARTING,
    STARTED,
    PAUSING,
    PAUSED,
    STOPPING,
    STOPPED,
    RELEASING,
    RELEASED,
    RESETTING,
    RESET,
    CLEARING_PLAYER_INSTANCE,
    PLAYER_INSTANCE_CLEARED,
    CREATING_PLAYER_INSTANCE,
    PLAYER_INSTANCE_CREATED,
    SETTING_DATA_SOURCE,
    DATA_SOURCE_SET,
    PLAYBACK_COMPLETED,
    END,
    ERROR
}
