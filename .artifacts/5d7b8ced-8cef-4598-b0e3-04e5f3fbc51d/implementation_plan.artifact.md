# Implementation Plan - Stability & Performance Fix

This plan addresses the "frozen UI" report by hardening the JavaScript logic, improving audio initialization, and ensuring all navigation buttons work reliably.

## Proposed Changes

### Game Logic Hardening (HTML/JS)

#### [MODIFY] [index.html](file:///C:/Users/Neha Borse/AndroidStudioProjects/nehakidsGame/app/src/main/assets/index.html)

**1. Reliable Audio Initialization**
- Move `AudioContext` creation inside a "Start" interaction. Most mobile browsers block audio until an explicit user gesture.
- Add `audioCtx.resume()` to the first click to prevent the audio engine from blocking other scripts.

**2. Improved Modal & Navigation Logic**
- Update the `showScreen` function to be more defensive.
- Ensure all modals (Settings, Scoreboard, Win) are explicitly hidden when switching between main screens.
- Fix the logic for the "Main Menu" button to ensure it always resets the game state.

**3. Robust Dataset Handling**
- Add fallback values for `datasets` and `selectedCategory` to prevent "undefined" errors that can freeze the script.
- Restore the full list of game items for Alphabets, Animals, Shapes, and Colors.

**4. Performance Polishing**
- Optimize the `resizeCanvas` and `drawLines` functions to be more efficient.
- Ensure `setTimeout` calls are cleaned up or handled correctly to avoid "ghost" updates.

## Verification Plan

### Automated Tests
- Run `./gradlew assembleDebug` to verify the build.

### Manual Verification
- **Startup**: Open the app and verify the "Kids Game Hub" loads immediately.
- **Interactivity**: Tap every button on the home screen to ensure none are frozen.
- **Audio Toggle**: Mute and Unmute both Music and SFX multiple times to ensure they toggle correctly.
- **Full Loop**: Play a round of "Animals" (small dataset) and "Alphabets" (large dataset) to verify the "Awesome" popup and "Main Menu" button work in all cases.
