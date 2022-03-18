# Multimedia Technology - NTUA (2021-2022)

<p align="left">
	<img alt="Byte Code Size" src="https://img.shields.io/github/languages/code-size/d-dimos/multimedia_ntua?color=red" />
	<img alt="# Lines of Code" src="https://img.shields.io/tokei/lines/github/d-dimos/multimedia_ntua?color=blue" />
	<img alt="Top language" src="https://img.shields.io/github/languages/top/d-dimos/multimedia_ntua?color=green" />
</p>

This repository hosts the **semester project** of the Multimedia Technology course held by ECE NTUA during the Winter 2021-2022.

The project's objective was to implement the Hangman Game in Java with some extra features. In brief, these features are:

- Creating dictionaries of words that we collect and process from [OPEN LIBRARY](https://openlibrary.org). The hidden word is randomly chosen from this collection.
- Extracting certain statistics regarding the characteristics of the words in a chosen dictionary.
- Collecting and presenting statistics regarding the games played.
- Calculating and presenting suggested letters for each position of the hidden word to the player. These letters are presented in ascending order of probability. The probability of a letter X at a position Y is considered to be the times that this letter is present in position Y in all the words of the chosen dictionary to the number of the words.
- The player's score is a function of the probability of the letter he/she chooses.

<p align="center">
	<img src="https://github.com/d-dimos/multimedia_ntua/blob/main/ui.png" width="500" height="450">
</p>
