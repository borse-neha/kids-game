# Implementation Plan - UI Improvements & Content Expansion for FunLearning

This plan focuses on fixing the text clipping in the name boxes, expanding the game content with multiple levels for Animals, Colors, and Shapes, and ensuring a child-friendly, responsive design for 3-4 year olds.

## User Review Required

> [!IMPORTANT]
> I will be expanding the content to include all requested levels. The current "Difficulty" screen will be repurposed into a "Level Selector" screen for each category.

> [!WARNING]
> I will replace the SVG-based text rendering with standard HTML text to better handle long words like "DIAMOND" and "ELEPHANT" without clipping.

## Proposed Changes

### Game UI & Layout (HTML/CSS)

#### [MODIFY] [fun.html](file:///C:/Users/Neha Borse/AndroidStudioProjects/nehakidsGame/app/src/main/assets/fun.html)

**1. Fix Left-Side Name Boxes**
- Increase `.left-col` width to at least **40%** to prevent text clipping for long words.
- Replace SVG `<text>` with standard HTML `<span>` or `div` for the name display.
- Use `display: flex`, `justify-content: center`, and `align-items: center` to keep text perfectly centered.
- Add horizontal padding to the cards to ensure words like "DIAMOND" don't touch the edges.
- Implement responsive font scaling (e.g., using `clamp()` or CSS classes for word length) to ensure readability on small screens.

**2. Responsive Matching Area**
- Adjust the `.game-container` to use a flexible gap or percentage-based widths so both columns fit perfectly on all devices.
- Maintain the thick connection lines and large dots for easy interaction.

**3. Level Selection System**
- Update `screenDiff` to display a list of levels (⭐ Level 1, ⭐ Level 2, etc.) instead of generic "Easy/Medium/Hard".
- Each level will be mapped to a specific set of items and a pair count.

### Content & Logic (JavaScript)

#### [MODIFY] [fun.html](file:///C:/Users/Neha Borse/AndroidStudioProjects/nehakidsGame/app/src/main/assets/fun.html)

**1. Expanded Datasets**
- Complete the datasets for:
    - **Animals**: 4 levels with increasing difficulty and more animals (Tiger, Giraffe, Zebra, etc.).
    - **Colors**: 3 levels with bright, distinct colors.
    - **Shapes**: 3 levels with large, colorful shapes (Rectangle, Star, Hexagon, etc.).

**2. Level-Based Game Initialization**
- Update `renderGameRound()` to accept a specific `level` and `category`.
- Select the correct subset of data for the chosen level.
- Randomize both columns while maintaining the correct internal mapping.

**3. Feedback & Scoring**
- Maintain the existing score system.
- Ensure positive visual feedback (animations) remains active.

## Verification Plan

### Manual Verification
- **Text Visibility**: Check words like "DIAMOND", "TRIANGLE", and "ELEPHANT" on small and large screen emulators.
- **Level Check**: Play through one level of each category to ensure items are correct and progress works.
- **Matching Check**: Verify connection lines start and end at the correct dots.
- **Responsive Check**: Test the UI in both narrow and wide portrait modes.
