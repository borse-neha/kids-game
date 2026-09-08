# Implementation Plan - Fix Animal Audio Mapping & Prevent Overwriting with sound_cat

This plan updates `MainActivity.java` to map each animal to its dedicated sound resource (`sound_dog`, `sound_cat`, `sound_cow`, `sound_lion`, `sound_hen`, `sound_sheep`, `sound_horse`, `sound_elephant`, `sound_monkey`, `sound_bear`, `sound_tiger`, `sound_giraffe`, `sound_zebra`, `sound_rabbit`), ensures all corresponding raw audio files exist in `res/raw/`, and sets non-animal categories or unmapped items to `0` (playing no sound instead of defaulting to cat).

## Proposed Changes

### 1. Populate Missing Animal Audio Files in `res/raw/`
- Copy existing valid sound files (`sound_dog.mp3` / `sound_cat.wav`) to create placeholder raw resources for remaining animals so Gradle compiles successfully:
  - `sound_elephant.mp3`
  - `sound_tiger.mp3`
  - `sound_giraffe.mp3`
  - `sound_zebra.mp3`
  - `sound_rabbit.mp3`

### 2. Update Native Audio Bridge (`MainActivity.java`)
- Replace the catch-all `sound_cat` default in `playAnimalSound(String animalName)` with a comprehensive switch-case statement:
  - `DOG` $\rightarrow$ `R.raw.sound_dog`
  - `CAT` $\rightarrow$ `R.raw.sound_cat`
  - `COW` $\rightarrow$ `R.raw.sound_cow`
  - `LION` $\rightarrow$ `R.raw.sound_lion`
  - `HEN` $\rightarrow$ `R.raw.sound_hen`
  - `SHEEP` $\rightarrow$ `R.raw.sound_sheep`
  - `HORSE` $\rightarrow$ `R.raw.sound_horse`
  - `ELEPHANT` $\rightarrow$ `R.raw.sound_elephant`
  - `MONKEY` $\rightarrow$ `R.raw.sound_monkey`
  - `BEAR` $\rightarrow$ `R.raw.sound_bear`
  - `TIGER` $\rightarrow$ `R.raw.sound_tiger`
  - `GIRAFFE` $\rightarrow$ `R.raw.sound_giraffe`
  - `ZEBRA` $\rightarrow$ `R.raw.sound_zebra`
  - `RABBIT` $\rightarrow$ `R.raw.sound_rabbit`
  - Default $\rightarrow$ `0` (no sound for non-animal categories or unmapped items).

## Verification Plan

### Automated Verification
- Run `./gradlew assembleDebug` to confirm all `R.raw.sound_*` references compile successfully.

### Manual Verification
- Match DOG $\rightarrow$ hears `sound_dog.mp3` (bark).
- Match CAT $\rightarrow$ hears `sound_cat.wav` (meow).
- Match COW $\rightarrow$ hears `sound_cow.wav` (moo).
- Match LION $\rightarrow$ hears `sound_lion.wav`.
- Play non-animal categories (Colors, Shapes, Alphabets) $\rightarrow$ verify no unwanted cat sound plays.
