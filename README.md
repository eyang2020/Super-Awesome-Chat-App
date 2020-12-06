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

This class creates a message object that takes in specific information from that message as parameters. Each message object contains a user, the date and time that the message was sent, and the corresponding text that goes with the message. This class contains getters and setters for each parameter besides the user, and also allows users to change the corresponding message. 

This class was tested by checking every field and method and making sure that all of them exist, have correct accessors, correct type / return type, and successful and unsuccessful implementation.

## Group Class

This class is used to represent a group conversation including two or more users. Each instance of the group class keeps track of the users that make up the group and the messages that have been sent within the group. It also maintains a title which acts as the nickname of the conversation for all the users in that group.

This class was used frequently in the operation of the client/server and ChatDriver so it was tested with an operating client/server to test the actual functionality. There was also a testing done to make sure the class exists, the constructor is correct, the fields exist, and the methods exist, set/get the correct values, and have correct return types.

## Client Class

TODO: add description about the class.

TODO: add description about testing done on class.

## ClientDriver Class

TODO: add description about the class.

TODO: add description about testing done on class.

## Server Class

TODO: add description about the class.

TODO: add description about testing done on class.

## ServerThread Class

This class acts as a helper class for the Server class in order to support multiple clients simultaneously. It dedicates a socket to each client that connects to the server and acts as the support for communication between clients and the server.

To test this class we ran the server and tested connecting multiple clients to the server at the same time. We also made sure to check if multithreading support was working correctly by sending multiple requests from two clients and ensuring that both requests were processed together, rather than one by one.

## ChatDriver Class

TODO: add description about the class.

TODO: add description about testing done on class.

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
