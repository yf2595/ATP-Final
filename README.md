# ATP-Final
Pokemon Maze game - a client & server game which allows you to move your chosen pokemon a cross randomized maze created by your choosing. 
This project is a part of advanced topics in programming class which covers all the topics that learned in the class - design patterns, multi-threading, SOLID, Search algorithms - BFS, DFS, UCS , log4J ,TCP ,MVVM and JUnit. 
The user chooses the maze settings and the server will create the maze and send it by tcp connection to the client side based on MVVM architecture which will display the maze to the user.
If the user wish to see the maze solution - the server will solve it using user chosen search algorithm - BFS, DFS or UCS.
During the game there are multiple sounds effects and images related to pokemon theme.
Every key press, mouse movement and client- server requests are recorded by log4J.
There is only 1 server working with multi-threading allowing more the 1 player to play.
all the classes are tested with Junit.
