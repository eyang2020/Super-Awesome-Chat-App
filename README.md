# CS180 Project 5  

Code repository for Option 3 of Project 5.

## How to Run
Start Server.java and once it has initialized, to connect to the server start ClientDriver.java. If you want multiple people at once to connect to the Server, just run multiple instances of ClientDriver.java

## Team Members
* AJ
* Camber
* Evan
* Ian
* Ruth

## User Class

Class Description:

The User class creates a user object that has the users personal information as the parameters. Each user object has an ArrayList of chat groups they are apart of. This class also contains setters and getters for each parameter.

This class was used frequently in the operation of the client/server and ChatDriver so it was tested with an operating client/server to test the actual functionality. There was also a testing done to make sure the class exists, the constructor is correct, the fields exist, and the methods exist, set/get the correct values, and have correct return types.

Methods:

setName(String name): Takes one parameter of type string and changes the user's name to the given value

setEmail(String email): Takes one parameter of type string and changes the user's email to the given value

setUsername(String newUsername): Takes one parameter of type string and changes the user's username to the given value

setPassword(String newPassword): Takes one parameter of type string and changes the user's password to the given value

setPhoneNumber(long phoneNumber): Takes one parameter of type string and changes the user's phone number to the given value

getUsername: Takes no parameters and returns the username of the user

getPassword: Takes no parameters and returns the password of the user

addGroup(Group group): Takes one parameter of type group that adds that group to the groups arraylist

getGroups: Takes no parameters and returns the groups that the user is a part of

getName: Takes no parameters and returns the name of the user

getEmail: Takes no parameters and returns the email of the user

getPhoneNumber: Takes no parameters and returns the phone number of the user

getUserID: Takes no parameters and returns the ID of the user

setUserID(int userID): Takes one parameter of type int and changes the user's ID to the given value 

setGroups(ArrayList<Group> groups1): Takes one parameter of type ArrayList<Group> and changes the groups arraylist to the given value
  
toString: Returns a string that includes the user's name, username, email, phone number, password, ID, and groups that they are a part of

## Message Class

Class Description:

This class creates a message object that takes in specific information from that message as parameters. Each message object contains a user, the date and time that the message was sent, and the corresponding text that goes with the message. This class contains getters and setters for each parameter besides the user, and also allows users to change the corresponding message. 

This class was tested by checking every field and method and making sure that all of them exist, have correct accessors, correct type / return type, and successful and unsuccessful implementation.

Method Description:

getAuthor: Takes no parameters and returns the user that sends the corresponding message

getDateTime: Takes no parameters and returns the date that the corresponding message was sent at

getText: Takes no parameters and returns the corresponding message

setMessage(LocalDateTime dateTime, String text): Takes two parameters of types LocalDateTime and String that creates a new message with these values

equals(Object o): Takes a message object as a parameter and compares it to the current message object to check if they equal each other

## Group Class

Class Description:

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

Class Description:

This class servers as the representation of the client side of the messaging application. The client class is responsible for handling the initialization process when a user carries out various tasks such as signing up, loging in, creating a new group, or editing a message among other tasks.  

To test this class we used manual testing to test all aspects of user interaction with the program through the interface supported by the client. Among the tasks tested were creating a new group, sending a message into a group and ensuring all other users of that group receive it, and the account creation/validation process. 

Methods:

connectToServer: Takes no parameters and connects the client to a server given a specific port and host from the constructor

createAccount(String username, String password, String name, String email, long phoneNumber): Takes in 5 parameters of types string, string, string, string, long and sends this information to the server to create an account. Returns true if the user was created, and false if not

login(String username, String password): Takes in two parameters of type string, string and sends it to the server to try and log in. Returns true if the user was logged in, and false if not

createGroup(String groupName, String[] usernames): Takes in two parameters of type string, string[] and sends it to the server to create a new group. Returns true if the group was created, and false if not 

addMessage(Message message, Group group): Takes in two parameters of type message, group and sends it to the server to add the given message to the given group. Returns true if the message was created, and false if not

updateServerUser(User user): Takes in one parameter of type user and sends that user to the server to be updated

getCurrentUser: Takes in no parameters and returns the current user

getUsers: Takes in no parameters and returns the arraylist of users

getGroups: Takes in no parameters and returns the arraylist of groups

editMessage(Message message, String newMessage, Group group, boolean delete): Takes in 4 parameters of types message, string, group, boolean and either edits or deletes the given message in the given group based off of if the boolean delete is true or false

updateCurrentUser: Takes in no parameters and updates the groups arraylist for the client

deleteFromGroup(Group group): Takes in one parameter of type group and removes the current user from that group


## ClientDriver Class

Class Description:

This class functions as a helper class for the Client class. It is used to run the client side of the chat application by initilizing a new client with a set address and port.

This class was used frequently in the operation of the client/server and ChatDriver so it was tested with an operating client/server to test the actual functionality. There was also a testing done to make sure the class exists and the methods exist with correct return types.

Methods:

main: creates a new instance of the Client class, passing it the address of the server and its respective port. Also invokes the EDT thread to prevent deadlock.

## Server Class

Class Description:

This class serves as the server-side representation of the project. As the main support for the backend, the Server class communicates with the Client class by receiving requests and sending updates to the Client upon completion of said requests. The server is responsible for reading in information about users and groups from our database to supply the client with a variety of data which can be used for account validation (for example, whether a username already exists in the database).

To test this class we used manual testing by using the client-side interface. We sent a multitude of requests to check if the server was both receiving them and correctly processing them. For example, we tested creating a new account for a user, and checked to see if the database was updated with this new information after the server processed a "new user" request. Moreover, we sent requests to the server to read the database regarding user info during login to ensure that the password used during login matched the one stored in the database during account creation.

Methods:

main: creates a new Server object and sets it to a deticated ServerSocket. While the server runs, it waits for clients to connect to be given a new thread.

acceptor: dedicates a thread for when a new client connects to the server

readInUsersAndGroups(String, String): given where the list of users and groups are stored, fill the current ArrayList of users and groups with the data from said file

writeUsersAndGroups(String, String): given where the list of users and groups are stored, use the data from the current ArrayList of users and groups and fill the files with current data about the users and groups respectively

getUsers: returns the current ArrayList of users

getGroups: returns the current ArrayList of groups

getUserIDCounter: increments the count of users and returns the new count

## ServerThread Class

Class Description:

This class acts as a helper class for the Server class in order to support multiple clients simultaneously. It dedicates a socket to each client that connects to the server and acts as the support for communication between clients and the server.

To test this class we ran the server and tested connecting multiple clients to the server at the same time. We also made sure to check if multithreading support was working correctly by sending multiple requests from two clients and ensuring that both requests were processed together, rather than one by one.

Methods:

run: this method is responsible for handling requests from the client to carry out various tasks. 

"createAccount" request: it will read in a username, password, name, email, user ID, and phone number. It then checks if the username is valid by checking if the server-side database already contains said username. If successful, the new user will be created, and its info will be written into the database. Otherwise, the user will not be able to create this account. 

"login" request: this method will check if the passed username and password are equal to those stored in the database according to the given username. 

"createGroup" request: the method will initialize a new Group object and add users to it according to the usernames passed by the client. Upon an "addMethod" request, the method searches all groups for the desired group and adds the message to the group's list of messages. 

"refresh" request: the method will write write all of the data contained within the server's current ArrayList of users and groups through its output stream. 

"updateServerUser" request: the method will read in the passed user object and update the user's contents on the server-side, including the name, email, password, phone number, and username.

"editMessage" request: the method will search for the correct group and find the respective message to be edited, changing its contents from there.

"updateCurrentUser" request: given a userID, the method will search for the respective user and write his or her data through the output stream.

"deleteFromGroup" request: the method will search for the correct group and find the respective user to be removed from the group. Note that this does not delete the group, it only removes the passed user.

## ChatDriver Class

Class Description:

This class servers as the chat panel which takes user input (such as a new message) and sends it to the server to be processed and delivered to other members of the respective group.

To test this class manual testing was used to ensure that when a user sent a message from the client-side, the server would receive said message and act from there. Furthermore, features such as scrolling and buttons were also tested by ensuring that their action listeners were applied correctly.  

Methods:

run: initializes the JFrame and container. Then, all the panels are are created using BorderLayout. The left panel is the list of group chats, the right panel is the list of current users in that group, the top panel is the nickname of the current group along with the settings button regarding the user's information, the bottom panel includes the textfield and three buttons to send, edit, and delete messages. Lastly, the central panel contains the message panel which displays all the messages of the current group. 

ActionListener: action listeners were created and added to buttons in order to add edit and delete functionality. Tied to the edit and delete button for messages, they allow the author of a particular message to edit a message or to delete it for the group as a whole, as long as they are the author. An action listener was also added to the setttings button which initiates a new instance of "ManageProfile", allowing the user to edit their information from there. Lastly, the send button was tied with an action listener which added support for sending a message to the server. From there, the message would be sent back to the group to each of its participants.

changeChatModel(int): initializes the current message list model to a new default model. For the current user, this method will update the user and get the current group that the user is in. Then, this method updates the message list model by adding all of the current group's messages within it.

changeUserModel: for each group that the user using the client is part of, we add the model that supplies the list of the group's current users' usernames to the chat panel.

changeGroupModel: for each group that the user using the client is part of, we add the model that supplies the group nickname to each group panel.

sendMessageToServer(String): given a string servering as the new message to be sent to the server, this method creates a new message with the said string and passes it to the client, adding it to the current group.

main: creates a new instance of Client, passing it the respective address and port number. Invokes the EDT thread to prevent deadlock and creates a new instance of ChatDriver, passing the previously created client class within.

refreshMessages: invokes the methods of client in order to refresh the current users and groups list of the client. After this, the method changes the model of the chat according to the selected index of the groupJlist.

## Login Class

Class Description:
This class includes the GUI for the user to either login to their account or create an account. It takes the information it is given and sends it to the client to log the user in or create their account. If a user creates an account a new User object is created.

Constructor:
The constructor connects the GUI to the client so they can access necessary information as needed.

Action Listener:
The action listener senses when the login or create account is selected. When the user selects either option, it closes the welcome window and opens a new one corresponding to their selected option. The user will then be prompted with labels and text fields to enter their information. If login is selected the username and password prompts are displayed and if create account is displayed it prompts for the same information as well as their personal information. When the user confirms their information with a button labeled by whichever option they chose it is sensed by the listener and sends the information enetered to either loginUser or createUser. The window is then closed and the chat app opens.

loginUser:
This method accepts the information sent in with the confirmation of login and calls the clients method to login the user. If its successful the current user is set to whoever logged in and if there is an error with an incorrect username or password it provides an error message.

createUser:
This method accepts the information sent in with the confirmation of account creation and calls the clients method to create a user. The current user is then set to the user created and if there is an error with an existing username being entered it provides an error message.

To test this class a main method was run to ensure the formatting was done correctly and displayed the correct information/options and was tested with an operating client/server to test the actual functionality. There was also a testing done to make sure the class exists, the constructor is correct, the fields exist, and the methods exist and have correct return types.

## ManageProfile Class

Class Description:
This class includes the GUI for the user to view their current information and edit it if they so choose. The user can also delete their account. When the user selects an option for what information to edit, it opens a text box for them to input their new information and edits it where the current information is displayed. It also changes the information kept in all other classes where it is kept.

Constructor:
The constructor connects the GUI to the client so they can access necessary information as needed.

Action Listener:
There is an action listener for each edit information option that the user has. When the user selects the edit button they choose it opens a text box where they can enter their new information. They can confirm this change with a change button next to the text box that also has a listener. When selected, a switch statement in the listener for the change button gets the int value corresponding to their selection and edits the information that was changed across the classes that store it and updates the GUI with the new information.

Run:
The run method creates the frame that has the panel with the labels and buttons corresponding to the information they can edit. It also gives them the option to delete their account if they choose. This window is updated when the user selects one of the edit information buttons to include a text box where they put the new information this operation was described previously.

To test this class a main method was run to ensure the formatting was done correctly and displayed the correct information/options and was tested with an operating client/server to test the actual functionality. There was also a testing done to make sure the class exists, the constructor is correct, the fields exist, and the methods exist and have correct return types.

## Testing the GUI

To test the GUI you start by running Server.java and then running ClientDriver.java. The first check is making sure that the login screen pops up. Once the login screen pops up you click on create account and enter random test information, then press the create account button. You then make sure that that the chat screen pops up. Once the chat screen pops up enter a message into the message text box and click send, make sure that the message shows up in the chat window with your username above it. Next, to check the edit and delete functionality click on your message, it should turn blue and the message text box should fill with the message. Change the message in the message text box in some way and then press the edit button. Confirm that the edit shows on screen. Next, click on the message again and click the delete button, confirm that the message disapears. Enter a few more messages for a future test.

Now, click on the create group button in the bottom left. Enter a name for the group and then choose your name from the drop down list and press add. Once you have done this press the create group button. Confirm that the group has shown up in the groups list on the left. Click on the newly created group, confirm that your previous messages from the first group disapeared. Add a few messages to this new group, then, click the delete group button on the top of the window and confirm that the group disapears.

Next, click on the settings button and click change username. Change the username. Now close the window. Restart ClientDriver.java, click on login and confirm that you can login with the new username and your original password. Then, click the settings button, click delete account and close the window. Restart ClientDriver.java, click on login and confirm you can no longer login. That is all of the testing.

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License
[MIT](https://choosealicense.com/licenses/mit/)
