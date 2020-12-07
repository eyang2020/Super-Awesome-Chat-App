# CS180 Project 5  

Code repository for Option 3 of Project 5.

## Team Members
* AJ
* Camber
* Evan
* Ian
* Ruth

## User Class

The User class creates a user object that has the users personal information as the parameters. Each user object has an ArrayList of chat groups they are apart of. This class also contains setters and getters for each parameter.

This class was used frequently in the operation of the client/server and ChatDriver so it was tested with an operating client/server to test the actual functionality. There was also a testing done to make sure the class exists, the constructor is correct, the fields exist, and the methods exist, set/get the correct values, and have correct return types.

## Message Class

Class Description:

This class creates a message object that takes in specific information from that message as parameters. Each message object contains a user, the date and time that the message was sent, and the corresponding text that goes with the message. This class contains getters and setters for each parameter besides the user, and also allows users to change the corresponding message. 

This class was tested by checking every field and method and making sure that all of them exist, have correct accessors, correct type / return type, and successful and unsuccessful implementation.

Method Description:

getAuthor: Takes no parameters and returns the user that sends the corresponding message

getDateTime: Takes no parameters and returns the date that the corresponding message was sent at

getText: Takes no parameters and returns the corresponding message

setMessage: Takes two parameters of types LocalDateTime and String that creates a new message with these values

equals: Takes a message object as a parameter and compares it to the current message object to check if they equal each other

## Group Class

This class is used to represent a group conversation including two or more users. Each instance of the group class keeps track of the users that make up the group and the messages that have been sent within the group. It also maintains a title which acts as the nickname of the conversation for all the users in that group.

This class was used frequently in the operation of the client/server and ChatDriver so it was tested with an operating client/server to test the actual functionality. There was also a testing done to make sure the class exists, the constructor is correct, the fields exist, and the methods exist, set/get the correct values, and have correct return types.

Methods:
getGroupName: returns the name of the current group
getUsers: returns the ArrayList of users in the current group
getMessages: returns the ArrayList of messages in the current group
setGroupName(String): sets the current name of the group to given string
addUser(User): appends a new given user to the ArrayList of users
addMessage(Message): appends a new given message to the ArrayList of messages
editMessage(int, Message): edits the message to the passed message at the given index
deleteMessage(int): deletes the message at given index of ArrayList of messages
removeUser(User): removes the user passed in from ArrayList of users
equals(Object): checks if two groups are equal using the group name, its users, and its messages

## Client Class

This class servers as the representation of the client side of the messaging application. The client class is responsible for handling the initialization process when a user carries out various tasks such as signing up, loging in, creating a new group, or editing a message among other tasks.  

To test this class we used manual testing to test all aspects of user interaction with the program through the interface supported by the client. Among the tasks tested were creating a new group, sending a message into a group and ensuring all other users of that group receive it, and the account creation/validation process. 

## ClientDriver Class

This class functions as a helper class for the Client class. It is used to run the client side of the chat application by initilizing a new client with a set address and port.

This class was used frequently in the operation of the client/server and ChatDriver so it was tested with an operating client/server to test the actual functionality. There was also a testing done to make sure the class exists and the methods exist with correct return types.

## Server Class

This class serves as the server-side representation of the project. As the main support for the backend, the Server class communicates with the Client class by receiving requests and sending updates to the Client upon completion of said requests. The server is responsible for reading in information about users and groups from our database to supply the client with a variety of data which can be used for account validation (for example, whether a username already exists in the database).

To test this class we used manual testing by using the client-side interface. We sent a multitude of requests to check if the server was both receiving them and correctly processing them. For example, we tested creating a new account for a user, and checked to see if the database was updated with this new information after the server processed a "new user" request. Moreover, we sent requests to the server to read the database regarding user info during login to ensure that the password used during login matched the one stored in the database during account creation.

## ServerThread Class

This class acts as a helper class for the Server class in order to support multiple clients simultaneously. It dedicates a socket to each client that connects to the server and acts as the support for communication between clients and the server.

To test this class we ran the server and tested connecting multiple clients to the server at the same time. We also made sure to check if multithreading support was working correctly by sending multiple requests from two clients and ensuring that both requests were processed together, rather than one by one.

## ChatDriver Class

This class servers as the chat panel which takes user input (such as a new message) and sends it to the server to be processed and delivered to other members of the respective group.

To test this class manual testing was used to ensure that when a user sent a message from the client-side, the server would receive said message and act from there. Furthermore, features such as scrolling and buttons were also tested by ensuring that their action listeners were applied correctly.  

## Login Class

This class includes the GUI for the user to either login to their account or create an account. It takes the information it is given and sends it to the client to log the user in or create their account. If a user creates an account a new User object is created.

To test this class a main method was run to ensure the formatting was done correctly and displayed the correct information/options and was tested with an operating client/server to test the actual functionality. There was also a testing done to make sure the class exists, the constructor is correct, the fields exist, and the methods exist and have correct return types.

## ManageProfile Class

This class includes the GUI for the user to view their current information and edit it if they so choose. The user can also delete their account. When the user selects an option for what information to edit, it opens a text box for them to input their new information and edits it where the current information is displayed. It also changes the information kept in all other classes where it is kept.

To test this class a main method was run to ensure the formatting was done correctly and displayed the correct information/options and was tested with an operating client/server to test the actual functionality. There was also a testing done to make sure the class exists, the constructor is correct, the fields exist, and the methods exist and have correct return types.

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License
[MIT](https://choosealicense.com/licenses/mit/)
