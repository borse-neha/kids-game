# Implementation Plan - Mandatory Intro Video Startup

Add a full-screen intro video that plays completely at every app launch before revealing the game hub.

## User Review Required

> [!IMPORTANT]
> **No Skip**: As requested, the skip functionality has been removed. The user must watch the full video before entering the game.

## Proposed Changes

### Layout Update

#### [MODIFY] [activity_main.xml](file:///C:/Users/Neha Borse/AndroidStudioProjects/nehakidsGame/app/src/main/res/layout/activity_main.xml)
- Overlay a `VideoView` on top of the `WebView` using a `FrameLayout`.
- The `VideoView` will start as visible, and the `WebView` will be hidden or under it.

### Activity Implementation

#### [MODIFY] [MainActivity.java](file:///C:/Users/Neha Borse/AndroidStudioProjects/nehakidsGame/app/src/main/java/com/example/neha_kidsgame/MainActivity.java)
- **Video Logic**:
    - Load `res/raw/game_intro.mp4`.
    - Use `setOnCompletionListener` to trigger the switch to the game hub.
    - Ensure the video is forced into full-screen and maintains its aspect ratio as best as possible.
- **Transition**:
    - Once the video ends, the `VideoView` will be removed/hidden with a fade animation.

## Verification Plan

### Automated Tests
- Run `./gradlew assembleDebug` to verify the build.

### Manual Verification
- **Startup**: Launch the app and verify the video plays.
- **No Skip**: Tap the screen during playback to ensure nothing happens (video keeps playing).
- **Auto-Switch**: Verify the game loads immediately and automatically once the video finishes.
