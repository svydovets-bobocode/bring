# Contributing to Bring project within Svydovets Team

## Important links
* [Google Drive Workspace](https://drive.google.com/drive/u/0/folders/1CUgg4TeZEbQSS-XSHp2LqBJRtXfZLnF_)
* [Trello board](https://trello.com/b/oj4H0D8Q/bring-project)
* [Figma workspace](https://www.figma.com/team_invite/redeem/2jzj6x6Zz6Q2V0OlcJctWm)
* [Project requirements](https://docs.google.com/document/d/1Zx83qIvoUanoSOg1wWvdPuKA_vQXthNWaRhqQwRPrpE/edit#heading=h.y4qlp0qqj7ui)

## Branching strategy

In this repository we maintain **one key branch** [main](https://github.com/rovein/bring-svydovets). 
In most cases it should be updated via the Pull Request.


## Creating a Pull Request


### Branching naming convention 
A branch name... 
* should start from the word "feature" or "bugfix" (according to the type of ticket you are working on)
* then following ticket name from Trello (e.g. "BR-34")
* optionally there may follow short description

Example

* ```feature/BR-5/aplicationContextBaseStructure```
* ```bugfix/BR-23```


### Commits
* In case you are working on the Trello ticket, please make sure that you always add its full number to every commit message
* Please provide a descriptive messages
* Use squash and rebase to avoid redundant commits


### Definition of done
You are ready to create a PR for your ticket if the following criteria are met:
* Local ```mvn verify``` is passing without the errors
* Ticket`s acceptance criteria are met
* Your code is covered by tests
* Your code is covered by JavaDocs on the main methods
