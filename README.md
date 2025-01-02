# Mario Game in Java

## Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [System Requirements](#system-requirements)
- [Installation](#installation)
- [Usage](#usage)
- [Controls](#controls)


---

## Overview
This project is a 2D Mario-style platformer game built using Java. It features interactive gameplay, physics-based movement, and a graphical user interface. The project demonstrates the integration of multiple libraries to handle graphics rendering, physics simulation, UI design, and JSON data management.

---

## Features
- **2D Platforming Gameplay**: Jump, run, and interact with objects in a side-scrolling environment.
- **Physics Engine**: Powered by Box2D for realistic collision detection and physics-based movement.
- **Modern Graphics Rendering**: Utilizes OpenGL for smooth rendering and graphics processing.
- **Customizable UI**: Built with ImGui for dynamic and flexible UI design.
- **Data Handling**: JSON data management enabled by GSON for saving/loading game states.

---

## Technologies Used
- **LWJGL (Lightweight Java Game Library)** - For OpenGL rendering and window management.
- **ImGui** - To create an intuitive graphical user interface.
- **Box2D** - Provides physics simulations for in-game objects.
- **GSON** - Used for JSON parsing and serialization.
- **OpenGL** - For advanced graphics rendering.

---

## System Requirements
- **Java Version**: Java 1.8 or later.
- **Build Tool**: Gradle 6.3 or later.
- **Operating System**: Windows, macOS, or Linux.

### Dependencies
Ensure that the following dependencies are included in your `build.gradle` file:
```gradle
dependencies {
    implementation 'org.lwjgl:lwjgl:3.2.3'
    implementation 'com.google.code.gson:gson:2.8.6'
    implementation 'org.immutables:value:2.8.2'
    implementation 'io.imgui:imgui:1.82'
    implementation 'org.jbox2d:jbox2d-library:2.2.1.1'
}
```

---

## Installation

1. **Clone the Repository**:
   ```bash
   git clone <repository-url>
   cd mario-game-java
   ```
2. **Install Dependencies**:
   Ensure that Gradle and Java are properly installed and added to your environment variables.

   - Verify Java version:
     ```bash
     java -version
     ```
   - Verify Gradle version:
     ```bash
     gradle -v
     ```
3. **Build the Project**:
   ```bash
   gradle build
   ```
4. **Run the Game**:
   ```bash
   gradle run
   ```

---

## Usage
Once the game starts, players can control the Mario character using the specified controls (see below) and interact with the environment. Collect coins, avoid obstacles.

---

## Controls
- **Move Left**: `Left Arow key`
- **Move Right**: `Right Arow key`
- **Jump**: `Space`
- **Fireball**: `E`


---

## Future Improvement
- **Pause/Resume Button** - Implement functionality to pause and resume the game.
- **Crouch Button** - Add crouching ability for enhanced gameplay mechanics.
- **Levels** - Introduce multiple levels to increase complexity and engagement.
- **Bug Fixes**- Debug and optimize performance issues.


---

## Acknowledgments
- Inspired by the classic Mario games.
- Thanks to the developers and contributors of LWJGL, ImGui, Box2D, GSON, and OpenGL.

