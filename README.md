# GoliathOUFX
Modern GUI Linux Nvidia GPU overclocking utility written in JavaFX

![](https://user-images.githubusercontent.com/10172435/48668265-da270700-eaae-11e8-9caf-b06eb6351946.png)
![](https://user-images.githubusercontent.com/10172435/48668266-da270700-eaae-11e8-95dc-a8df46b83d3e.png)
![](https://user-images.githubusercontent.com/10172435/48668267-da270700-eaae-11e8-9337-e657f019b9da.png)
![](https://user-images.githubusercontent.com/10172435/48668268-da270700-eaae-11e8-9990-10ff40add26e.png)
![](https://user-images.githubusercontent.com/10172435/48668269-dabf9d80-eaae-11e8-9f55-7e444671de8d.png)
![](https://user-images.githubusercontent.com/10172435/48668270-dabf9d80-eaae-11e8-9b23-3400ff7f6f3b.png)
![](https://user-images.githubusercontent.com/10172435/48668271-dabf9d80-eaae-11e8-86cf-f6f26b84d0aa.png)


## Download
You can download from the [releases](https://github.com/BlueGoliath/GoliathOUFX/releases) page.

## Features(currently)
* Easily get supported Nvidia Settings CLI Attribute readouts
* Change PowerMizer mode
* Apply over or under clocks to graphics core and memory transfer rate
* Increase voltage(not supported on Pascal or newer)
* Decrease or increase GPU power limit via Nvidia SMI
* Power draw readout from Nvidia SMI
* Performance Limit reason from Nvidia SMI
* On Screen Display for GPU info while in-game

## How it works
The Goliath Overclocking utility uses custom made Java Controller classes to push and pull Nvidia attribute data from the command line via the Goliath Terminal class. Graphics core, memory, voltage, and fan are done via nvidia-settings interface while Power limit is done via the nvidia-smi CLI interface. 
This application creates an app folder in your home folder in order to store app settings, GPU info, and fan profiles.
The classes can be found [here](https://github.com/BlueGoliath/GoliathENVIOUS) and the Terminal class can be found [here](https://github.com/BlueGoliath/GoliathTerminal)

## Requirements

* Nvidia binary GPU driver - legacy may work but IS NOT supported officially.
* Nvidia Settings CLI - may come with the Nvidia binary GPU driver depending on the distrobution you use
* X. Org config file with `cooltbits` set to `31` or some other value that allows Nvidia-Settings CLI access
* Java 9 or newer - project is always tested with the newest Java versions.

Native packaged versions do not need Java as a JRE is automatically included

## Known issues
* Attribute updating is CPU intensive(nothing can be done about it, sadly).
* Use of the OSD can and will cause performance issues with games.

## Building
Building is done via Ant and Netbeans 9. To build, place the contents of the downloaded ZIP files from the How It Works section into a project folder and open each folder. Add GoliathENVIOUS and GoliathTerminal as dependencies to the project.

Note: GoliathCSS is optional.

## Disclaimer

As per the license, I accept no responsiblity for any damages that happen by using this utility.
