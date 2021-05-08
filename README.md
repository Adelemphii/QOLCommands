# QOLCommands
A simple grouping of "Quality of Life" commands in one repo. Feel free to add on to this.

## Commands
- /walk - Slows players to a 'walking' speed set in the config
- /walk (number) - Allows players to choose a speed between 0 and .2
- /sit - Lets players sit where they stand
- /roll <number> - Lets players roll a number locally
- /broadcastroll <number> - Lets players roll a number which is broadcast to the whole server
- /carry <player> and /carry confirm <player> - Lets players carry another player

## Permissions
- qolcommands.walk | This permission allows players to use the /walk command
- qolcommands.sit | This permission allows players to use the /sit command
- qolcommands.carry | This permission allows players to use the /carry command and its subcommands
- qolcommands.roll | This permission allows players to use the /roll command
- qolcommands.roll.broadcast | This permission allows users to use the /broadcastroll command
- qolcommands.admin | This permission allows users to use the /qolreload command

## Config
- player-sleep-divide-amount: | This is determines the amount of players needed to sleep, the math is: "online-players / player-sleep-divide-amount". So a 2 would be half the online players!
- better-sleep: | This enables the ability to let the night be skipped if a certain amount of online players are sleeping!
- local-roll-range: | This is the distance in blocks for how far players can see the output from the /roll command 
- simple-farming: | This enables left click (with a hoe) and right click (barehanded and with items) farming of crops
- disable-crop-trample: | With this enabled, you can prevent players from jumping on farmland and crushing it
- walk-speed: | Between 0 and .2, this value is for the default /walk speed (If you put something outside that range, it defaults to .1 and gives an error in the console!)

[Plugin Showcase](https://www.youtube.com/watch?v=yAISOvR_uc0)
