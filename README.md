# ShoddyStocks

This is an IRC bot that implements a variant of the game of [Nim][]. The
program was created by [Cathy Fitzpatrick][cathyjf] in July 2009. It is
licensed under the [GNU Affero General Public License][agpl3], version 3 or
later.

## Game description

This is a two player game. The players are presented with an array of sticks
arranged into rows and they must take turns removing sticks. Each turn, you
can remove any number of adjacent sticks from within a single row. The player
who removes the last stick loses.

Nim is not difficult to solve mathematically. However, it is a good way to
teach various game theory concepts, because the solution is not immediately
obvious, but is still easily within reach after some reflection.

## How to run

You must have a JDK installed. Inside the directory where you have cloned
the repository, try

```bash
# Compile the program.
javac -cp pircbot.jar ShoddySticks.java
# Run the bot.
java -cp pircbot.jar:. ShoddySticks irc.synirc.net "#example"
```

Note that it is necessary to quote the channel name in order to prevent the
hash symbol (#) from being interpreted as a shell comment character.

## Sample game

Here is a sample game using ShoddySticks:

```
[11:04:10] <Cathy> !sticks begin
[11:04:11] <ShoddySticks> Type !sticks join to join the game.
[11:04:20] <NimOpponent> !sticks join
[11:04:20] <ShoddySticks> NimOpponent has joined the game.
[11:04:21] <ShoddySticks> Another player is needed! Type !sticks join to join the game.
[11:04:27] <Cathy> !sticks join
[11:04:28] <ShoddySticks> Cathy has joined the game.
[11:04:29] <ShoddySticks> NimOpponent v. Cathy begins!
[11:04:30] <ShoddySticks> 1: | 
[11:04:31] <ShoddySticks> 2: | | 
[11:04:32] <ShoddySticks> 3: | | | 
[11:04:33] <ShoddySticks> 4: | | | | 
[11:04:34] <ShoddySticks> 5: | | | | | 
[11:04:35] <ShoddySticks> It's Cathy's turn!
[11:05:06] <Cathy> !sticks take 5:2-4
[11:05:07] <ShoddySticks> 1: | 
[11:05:08] <ShoddySticks> 2: | | 
[11:05:09] <ShoddySticks> 3: | | | 
[11:05:09] <ShoddySticks> 4: | | | | 
[11:05:11] <ShoddySticks> 5: | _ _ _ | 
[11:05:11] <ShoddySticks> It's NimOpponent's turn!
[11:05:31] <NimOpponent> !sticks take 4:1-4
[11:05:32] <ShoddySticks> 1: | 
[11:05:33] <ShoddySticks> 2: | | 
[11:05:34] <ShoddySticks> 3: | | | 
[11:05:35] <ShoddySticks> 5: | _ _ _ | 
[11:05:36] <ShoddySticks> It's Cathy's turn!
[11:05:44] <Cathy> !sticks take 3:1
[11:05:45] <ShoddySticks> 1: | 
[11:05:46] <ShoddySticks> 2: | | 
[11:05:47] <ShoddySticks> 3: _ | | 
[11:05:48] <ShoddySticks> 5: | _ _ _ | 
[11:05:49] <ShoddySticks> It's NimOpponent's turn!
[11:05:57] <NimOpponent> !sticks take 2:1-2
[11:05:57] <ShoddySticks> 1: | 
[11:05:58] <ShoddySticks> 3: _ | | 
[11:05:59] <ShoddySticks> 5: | _ _ _ | 
[11:06:00] <ShoddySticks> It's Cathy's turn!
[11:06:41] <Cathy> !sticks take 3:2
[11:06:41] <ShoddySticks> 1: | 
[11:06:43] <ShoddySticks> 3: _ _ | 
[11:06:43] <ShoddySticks> 5: | _ _ _ | 
[11:06:44] <ShoddySticks> It's NimOpponent's turn!
[11:06:56] <NimOpponent> !sticks take 1:1
[11:06:57] <ShoddySticks> 3: _ _ | 
[11:06:57] <ShoddySticks> 5: | _ _ _ | 
[11:06:58] <ShoddySticks> It's Cathy's turn!
[11:07:11] <Cathy> !sticks take 5:1
[11:07:11] <ShoddySticks> 3: _ _ | 
[11:07:12] <ShoddySticks> 5: _ _ _ _ | 
[11:07:13] <ShoddySticks> It's NimOpponent's turn!
[11:07:20] <NimOpponent> !sticks take 3:3
[11:07:20] <ShoddySticks> 5: _ _ _ _ | 
[11:07:21] <ShoddySticks> It's Cathy's turn!
[11:07:26] <Cathy> !sticks take 5:5
[11:07:26] <ShoddySticks> Game over! NimOpponent wins!
```

## Credits

+ [Cathy Fitzpatrick][cathyjf] created the program.
+ ShoddySticks uses [PircBot][], a copy of which is included in the repository.

[Nim]: https://en.wikipedia.org/wiki/Nim
[cathyjf]: https://cathyjf.com
[agpl3]: http://www.fsf.org/licensing/licenses/agpl-3.0.html
[PircBot]: http://www.jibble.org/pircbot.php
