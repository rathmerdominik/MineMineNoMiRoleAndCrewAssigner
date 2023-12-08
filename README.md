
<h1 align="center">Role and Crew Assigner</h1>

<p align="center">Role and Crew Assigner is a Minecraft Addon for the popular <a href="https://www.curseforge.com/minecraft/mc-mods/mine-mine-no-mi"> Mine Mine no Mi Mod</a> mod.</p>

<p align="center">It also is an Integration for the <a href="https://modrinth.com/plugin/dcintegration">Discord Integration Mod</a> to streamline the process of adding this Addon in your server environment!</p>

# Description

<h3 align="center">Role Assigner</h1>
<p align="center"> <img src="roles.png"> </p>
<p align="center">
This addon automatically assigns Roles based on Faction, Race and Fighting Styles chosen in-game.  
</p>

<p align="center">
Those automatically update as well, when a Character Creation Book has been used or any of those Stats have been changed in any other way.
</p>

<h3 align="center">Crew Assigner</h3>
<p align="center"> <img src="crews.png"> </p>

<p align="center">
The second part of the mod consists of synchronizing crews of the Mine Mine no Mi Mod into a Discord Forum Channel.
</p>

<p align="center">
This also synchronizes crew kicking, leaving, captains and captain change, members and creation date.
</p>

# Important

In order for the addon to work several changes to the main Mine Mine no Mi mod had to be made.  
Those changes have already been committed by me to the main mod and will be available with the next Update.  
Specifics can be found [here](https://github.com/rathmerdominik/MineMineNoMiModded).  

It is imperative that for now this custom version is used. Changes in there are purely server sided. So only you need to install this version on your server.  
Players can still use the normal Mine Mine no Mi Version.

I HAVE EXPLICIT PERMISSION BY THE CREATOR OF THE MINE MINE NO MI MOD TO REDISTRIBUTE THIS.  
YOU DO NOT! THEREFORE EVERY FURTHER REDISTRIBUTION HAS TO HAPPEN BY REFERENCING TO THIS PAGE!

# Installation

```
git clone https://github.com/rathmerdominik/MineMineNoMiRoleAndCrewAssigner.git
cd MineMineNoMiRoleAndCrewAssigner
cd libs
curl -O -L https://github.com/rathmerdominik/MineMineNoMiModded/raw/main/release/mine-mine-no-mi-0.9.5-HAMMER.jar
cd ..
./gradlew build
cd build/libs
```

You can now just take the jar file out of this directory and place it under the `mods/` folder on your server.

This mod requires Discord Integration 3.0.3 to be installed and setup on your server!

# Config Options

```toml
#To disable a specific Role from syncing just do not change the ID on the config option
[Races]
    #Range: 0 ~ 9223372036854775807
    "Human Role Id" = 0
    #Range: 0 ~ 9223372036854775807
    "Mink Role Id" = 0
    #Range: 0 ~ 9223372036854775807
    "Cyborg Role Id" = 0
    #Range: 0 ~ 9223372036854775807
    "Fishman Role Id" = 0

[Factions]
    #Range: 0 ~ 9223372036854775807
    "Bounty Hunter Role Id" = 0
    #Range: 0 ~ 9223372036854775807
    "Revolution Army Role Id" = 0
    #Range: 0 ~ 9223372036854775807
    "Marine Role Id" = 0
    #Range: 0 ~ 9223372036854775807
    "Pirate Role Id" = 0

["Marine Ranks"]
    #Range: 0 ~ 9223372036854775807
    "Marine Lieutenant Role Id" = 0
    #Range: 0 ~ 9223372036854775807
    "Marine Commander Role Id" = 0
    #Range: 0 ~ 9223372036854775807
    "Marine Sea Man Role Id" = 0
    #Range: 0 ~ 9223372036854775807
    "Marine Commodore Role Id" = 0
    #Range: 0 ~ 9223372036854775807
    "Marine Chore Boy Role Id" = 0
    #Range: 0 ~ 9223372036854775807
    "Marine Petty Officer Role Id" = 0
    #Range: 0 ~ 9223372036854775807
    "Marine Vice Admiral Man Role Id" = 0
    #Range: 0 ~ 9223372036854775807
    "Marine Admiral Role Id" = 0
    #Range: 0 ~ 9223372036854775807
    "Marine Captain Role Id" = 0
    #Range: 0 ~ 9223372036854775807
    "Marine Fleet Admiral Role Id" = 0

["Revolutionary Ranks"]
    #Range: 0 ~ 9223372036854775807
    "Revolutionary Army Officer Role Id" = 0
    #Range: 0 ~ 9223372036854775807
    "Revolutionary Army Supreme Commander Role Id" = 0
    #Range: 0 ~ 9223372036854775807
    "Revolutionary Army Member Role Id" = 0
    #Range: 0 ~ 9223372036854775807
    "Revolutionary Army Commander Role Id" = 0
    #Range: 0 ~ 9223372036854775807
    "Revolutionary Army Chief of Staff Role Id" = 0

["Pirate Crews"]
    "Sync Crew Members" = true
    "Show Crew Creation Date" = true
    #Has to be a Forum channel! Therefore your server MUST be a community server if you want to use this feature!
    #Range: 0 ~ 9223372036854775807
    "Crew Forum Channel Id" = 0
    "Show Captain of the crew" = true
    "Sync Crew Banner" = true

["Fighting Styles"]
    #Range: 0 ~ 9223372036854775807
    "Art of Weather Role Id" = 0
    #Range: 0 ~ 9223372036854775807
    "Brawler Role Id" = 0
    #Range: 0 ~ 9223372036854775807
    "Sniper Role Id" = 0
    #Range: 0 ~ 9223372036854775807
    "Black Leg Role Id" = 0
    #Range: 0 ~ 9223372036854775807
    "Doctor Role Id" = 0
    #Range: 0 ~ 9223372036854775807
    "Swords Man Role Id" = 0
```
