---
title: Build Instructions and technical requirement.
description: User and build instructions, technical requirements and dependencies.
menu: Instructions
order: 60
---

## Basic User Instructions
- Sign-in using your Google Account

### Home Screen Navigation 

#### 1- Mood-Check-in button  
  - click on the icon labeled Mood check in on the top right, to register a mood.
  - The seekbar slides starting from 1 to 10, choose a number according to your state of mind.
  - After registering your mood, you will be taken to your advice screen, where an advice and an image will be generated.
  - You will then get the option of clicking on advice to generate a second one, or to be taken to your diary to write an entry.
  - Use the left arrow at the top right anytime you want to get back to the previous screen.

#### 2- Diary button
  - Next to the Mood check in button, on the left, click on the Diary button, to navigate directly to your diary and write an entry.
  - Use the left arrow at the top right anytime you want to get back to the previous screen. 

#### 3- Advice button
  - Below the Diary button, click on the Advice button to navigate to a history of all the advices that have been displayed to the current user.
  - Use the left arrow at the top right want to get back to the previous screen.

### Bottom Navigation 
  - Home screen button takes you back to the home screen.
  - Moods button navigates to a descending history of your mood check in but also gives you the opportunity to once again register a mood and to take you to the advice screen.
  - Diary button displays the previous entries made by the user to the diary.
  - Favorite displays all the advice favorited and gives you the option to be taken directly to their instances,
  - Use the left arrow at the top right want to get back to the previous screen when the arrow is displaying, otherwise navigate with the bottom nav menu.

## Build Instructions
- On GitHub.com, navigate to [El8](https://github.com/ddc-java-15/el-8)
- Click on Code.
- Click on SSH link and copy the link.
- Open IntelliJ.
- Click on get from VCS and clone the repository.
- Paste the SSH link.
- Change the current working directory to the desired location.
- Execute the build.

## Technical Requirements and Dependencies

#### This app requires:

- Minimum SDK 26
- Room
- ReactiveX
- Stetho
- Google material design 
- Has been tested on Emulator API 28

        
    -