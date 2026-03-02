# Player Animator Library Implementation Guide

## Current Status
The Player Animator Library has been configured in your project but needs testing.

## What Has Been Done

### 1. Build Configuration (`build.gradle`)
- Added Kosmx's maven repository (https://maven.kosmx.dev/)
- Added Player Animator Library dependency:
  ```groovy
  modImplementation "dev.kosmx.playerAnim:player-animation-lib-fabric:${project.pal_version}"
  include "dev.kosmx.playerAnim:player-animation-lib-fabric:${project.pal_version}"
  ```

### 2. Gradle Properties (`gradle.properties`)
- Set PAL version to `1.1.3`

### 3. Fabric Mod Configuration (`fabric.mod.json`)
- Added `playeranimator` as a required dependency

### 4. Animation Controller (`AnimationController.java`)
- Implemented full Player Animator Library integration
- Added methods:
  - `AnimationControllerRegister()` - Registers animation layer for all players
  - `playSlideAnimation(player)` - Plays the slide animation
  - `stopSlideAnimation(player)` - Stops the slide animation

## How to Use

### Step 1: Create Animation File
You need to create a slide animation file at:
```
src/main/resources/assets/movementum/animations/slide.json
```

### Step 2: Register the Animation
The animation is automatically registered with ID: `movementum:slide`

### Step 3: Integrate with Slide Mechanic
In your `Slide.java`, call the animation methods:
```java
// When starting slide
AnimationController.playSlideAnimation((AbstractClientPlayerEntity) client.player);

// When stopping slide
AnimationController.stopSlideAnimation((AbstractClientPlayerEntity) client.player);
```

## Animation File Format
Create a file at `src/main/resources/assets/movementum/animations/slide.json`:
```json
{
  "version": 1,
  "poses": [
    {
      "timestamp": 0,
      "head": {"x": 0, "y": 0, "z": 0, "pitch": 0, "yaw": 0, "roll": 0},
      "body": {"x": 0, "y": 0, "z": 0, "pitch": 45, "yaw": 0, "roll": 0},
      "leftArm": {"x": 0, "y": 0, "z": 0, "pitch": -45, "yaw": 0, "roll": 0},
      "rightArm": {"x": 0, "y": 0, "z": 0, "pitch": -45, "yaw": 0, "roll": 0},
      "leftLeg": {"x": 0, "y": 0, "z": 0, "pitch": 90, "yaw": 0, "roll": 0},
      "rightLeg": {"x": 0, "y": 0, "z": 0, "pitch": 90, "yaw": 0, "roll": 0}
    }
  ],
  "isLooped": false,
  "returnTick": 0
}
```

## Next Steps
1. Test the build: `gradlew build`
2. Create the animation JSON file
3. Integrate animation calls into Slide.java
4. Test in-game

## Troubleshooting
If the build fails:
- Try different PAL versions (1.0.2, 1.1.0, 1.1.3)
- Check if the repository is accessible
- Verify Minecraft version compatibility

