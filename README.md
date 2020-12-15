# Game of Three
Simple game of three implementation using DDD and Websocket over Spring Boot

The project is divided into 4 maven modules:

* application: contains the main method that initializes the spring boot application
* domain: contains the game rules and logic implemented
* infrastructure: contains the websocket config and the endpoints used by the client to access the game
* presentation: contain the html client for the game (accessible under http://localhost:8080)


To build the docker container, just run from main project folder
```
docker build -t gameofthree .
```

To run the docker container, just run from main project folder
```
docker run -p 8080:8080 gameofthree
```

To play the game, access http://localhost:8080 and the interface will be displayed
