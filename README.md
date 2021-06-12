# Don't Stutter

## Overview
Don't Stutter is a mobile game for Android. 
It was done as a project for my native mobile app development studies.

## Features
### Game
One Minute Mode gives player one minute (as the name suggests) to guess as many words as they possibly can. The game displays random letters and player has to insert a word that contains those letters. If the word is accepted as a common word, player receives one point. If player guesses wrong or skips, one heart is lost. If three hearts are lost or the timer hits zero, the game ends. The app counts correctly guessed words and hearts left and combines them for a final score.

### Highscore
After the game has ended the final score is compared to current high scores and is saved if it reaches top five. Highscore list displays the score, the date when the score was achieved and the player's profile name and photo.

### Profiles
Maximum of five profiles can be created. Name is required for a profile but photo is optional. The camera feature supports back camera / landscape only. After adding a profile it is automatically set as active profile. Active profile can be switched by tapping profiles. If no profiles have been created, the app uses default profile "anonymous".

## API
Datamuse API

## Known bugs
- There is a rare bug where the timer keeps going even though the game has been stopped. This makes the Game Over -screen open when the time is up.
- Datamuse API can sometimes respond very slowly and therefore new letters don’t appear. This doesn't stop timer (if it has been started).
- Some words in Datamuse API contain numbers and special characters and those should not be used in Don't Stutter. Filtering words based on frequency should get rid of those rare occurrences.
- Sometimes the same letters appear multiple times in one round and the player can input the same word again and get points from it.

## Screencast
Will be added later.
