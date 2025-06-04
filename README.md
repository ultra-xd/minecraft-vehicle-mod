# Minecraft Vehicle Simulator Mod

**ICS 4U1 – Minecraft 1.21.5 Fabric Mod**

## 🚗 Overview

This mod brings a variety of fully functional, craftable vehicles into Minecraft, including cars, trucks, racecars, and more. It adds speed, fuel systems, trunk storage, and even collision explosions for a thrilling gameplay experience.

## 📦 Features

- 3D vehicle models with realistic physics
- Vehicles include stats: speed, braking, fuel capacity, explosion power, etc.
- Driveable, steerable, and fuelable with coal or redstone (for EVs)
- Craftable with in-game recipes
- Trunk storage, fuel tank GUI, and seating support

## 🛠 Installation

Before installing this mod:

1. Install **Minecraft 1.21.5**
2. Install **Java 21+**
3. Install **Fabric Loader** and **Fabric API**
4. Place the mod JAR into your `mods` folder.

## 🎮 Controls

| Action                 | Default Key |
|------------------------|-------------|
| Accelerate forward     | W           |
| Accelerate backward    | S           |
| Steer left             | A           |
| Steer right            | D           |
| Brake (fast stop)      | Space       |
| Sit in seat / Open trunk / Refuel | Right Click |
| Exit vehicle           | Shift       |

## ⛽ Fuel & Mechanics

- **Fuel Types**: Coal (default), Redstone (EVs), or both (Hybrids)
- One fuel unit is consumed every 5 seconds while driving
- Cars coast when not accelerating, decelerating naturally or with brakes
- Crashes at high speed can cause explosions based on speed and car type
- Water slows/brakes the vehicle; lava causes instant explosion

## 🚘 Vehicles

| Vehicle       | Max Speed | Explosion Power | Brake Power | Seats | Trunk | Fuel | Notes |
|---------------|-----------|-----------------|-------------|-------|--------|------|-------|
| **Kia Soul**   | 1 b/s     | 20              | -0.01       | 4     | 27 slots | 3  | Leaks fuel |
| **Tesla**      | 25 b/s    | 10              | -10         | 4     | 9 slots  | 3  | Uses redstone |
| **Honda Civic**| 30 b/s    | 30              | -10         | 4     | 9 slots  | 5  | Standard |
| **Rav 4**      | 27 b/s    | 50              | -7          | 6     | 27 slots | 7  | Hybrid (coal/redstone) |
| **Bugatti**    | 55 b/s    | 70              | -35         | 2     | 5 slots  | 18 | Fastest, 2s to max speed |
| **Garbage Truck**| 12 b/s | 20              | -3          | 2     | None     | 5  | Carries entities |

## 🧪 Crafting Recipes

Recipes follow a crafting table layout. All vehicles require a mix of common and rare materials like:
- Iron Ingots
- Droppers (motors)
- Glass Blocks
- Redstone Dust
- Diamonds
- Netherite Ingots
- Iron/Redstone Blocks

Each vehicle has a unique layout. See in-game recipe book or SRS documentation for exact patterns.

---

Feel free to contribute or suggest improvements!
