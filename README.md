# CS-180_Team_Project
1. Testing/Compiling
   
   a. Testing- We used JUNIT to write all of our test cases.
      - UserTest:
           - We test every method in the User class.
           - Run it on IntelliJ to see the observed result.
      - TextMessageTest:
           - We test every method in the TextMessage class.
           - Run it on IntelliJ to see the observed result.
      - UserDatabaseTest:
           - We test every method in the UserDatabase class.
           - Run it on IntelliJ to see the observed result.
      - ServerTest:
           - We test every method that doesn't utilize NetworkIO in the ServerTest class.
           - Run it on IntelliJ to see the observed result.
      - ClientTest:
           - We test every method that doesn't utilize NetworkIO in the ClientTest class.
           - Run it on IntelliJ to see the observed result.
      - ClientHandlerTest:
           - We test every method that doesn't utilize NetworkIO in the ClientTest class.
           - Run it on IntelliJ to see the observed result.
      - ClientGUITest
           - We test to make sure every user input works as expected in the ClientGUI class.
           - Run it on IntelliJ to see the observed result.
             
   b. Manual Testing/Compiling
      - Place all the required .java files (e.g., Server.java, Client.java, ClientHandler.java, User.java, UserDatabase.java, etc.) in the same directory.
      - Open a terminal or command prompt.
      - Navigate to the directory containing the .java files using the cd command.
      - Start the server in the terminal. The server will begin on the default port and wait for client connections.
      - Open a new terminal or command prompt.
      - Navigate to the same directory where the compiled classes are located.
      - Start the ClientGUI. The ClientGUI will attempt to connect to the server at localhost.
      - Then, the ClinetGUI will receive a popup on the screen, prompting them to either "Login" or "Create Account".
      - No matter what option the user selects, they will be prompted to enter a username and a password. If they select "Create Account," they will also be asked whether they want their account to be public or private.
      - The "Create Account" option will ensure that both the username and password are valid, and once they are, it will create an account using the username and password, and then prompt the user to log in.
      - After Logging in, the user will see a screen with the buttons "Add Friend," "Remove Friend," "Block User," "Send Message," "Delete Message," "Search User," "View User," "View Messages," and "Logout."
      - To perform any of these functions, the client has to click the button they want to perform. The user will then be prompted for more information.
      - "Add Friend" adds a friend checking to make sure the user exists, "Remove Friend" removes a friend checking to make sure the user is a friend, "Block User" blocks a user checking to make sure the user exists, "Send Message" sends a message to a specific user checking a variety of conditions, "Delete Message" deletes a message that was sent to a certain user, "Search User" checks to make sure that a user exists, "View Profile" shows the clients personal information, "View Messages" shows the whole conversation between you and another user, and "Logout" disconnects from the server and closes the GUI.
      - Every page/panel has a back button that you navigate to the page/panel you came from.
      - Notes: 1. Ensure the server is started before running the client. 2. Use multiple terminals or command prompt windows to simulate multiple clients connecting to the server. 3. All information related to a user (username, password, friends, blocked users) gets stored in UserList.txt. 4. Messages that are sent and received in a separate file for each user. The files are named "username".txt.     
2. Zachary O'Connell Submitted Vocareum Workspace
3. Classes/Interfaces/Exceptions
    a. Classes 
     - User:
       The User class models an individual user within the database, holding key information like username, password, and privacy status. It maintains lists of friends, blocked users, and received messages, allowing each user to connect with others, manage interactions, and handle communication. Core methods in this class include adding/removing friends, blocking users, and sending or deleting messages, which enable interaction. The equals method ensures that each user is uniquely identified by their username. This class interacts with TextMessage for messaging and is managed collectively by UserDatabase, which organizes users.

     - UserDatabase:
       The UserDatabase class serves as a centralized manager for User instances, facilitating user registration, deletion, and message handling while ensuring thread safety through synchronization. It maintains lists of users, user-specific files, and associated writers. Key methods include addUser to register new users, removeUser to delete existing users, and everythingToFile to save the state of all users and their messages to files. The class is designed to prevent duplicate usernames. It interacts closely with the User class for user management and utilizes TextMessage for message operations.
       
     - TextMessage:
       The TextMessage class represents a text message exchange between users, including the sender's username, receiver's username, and the message content itself. It utilizes a string array to store these details, providing methods to retrieve each piece of information, including the sender and receiver usernames and the message content. The class overrides the equals method to ensure accurate comparison of TextMessage instances based on their sender, receiver, and message content, while the toString method formats the message for display. This class is designed to work with User and UserDatabase.
       
       
     - Server:
       The Server class is responsible for facilitating client connections and user authentication. It maintains a thread-safe list of currently logged-in users and delegates client-specific operations to the ClientHandler class. The server continuously looks for incoming client connections on a specified port and starts a new thread to handle each client session.
       
     - ClientHandler:
       The ClientHandler class is a thread responsible for managing interactions with an individual client connected to the server. It handles tasks such as authenticating users, managing friend requests, blocking users, sending and deleting messages, and providing various account-related operations. Each client interaction is processed independently in a thread-safe manner, ensuring concurrent handling of multiple clients. The class maintains a connection with the client through input and output streams, which enables real-time communication and updates. Upon client logout or disconnection, the handler ensures proper cleanup by updating the server's logged-in users list and saving relevant data.
       
     - Client:
       The Client class establishes a connection to the server, enabling communication through object streams for sending and receiving messages. It manages user interactions through the console for now, allowing users to perform actions like login, account creation, and various commands sent to the server. The client handles the server by providing real-time feedback to users and ensuring graceful disconnection when the session ends.

     - ClientGUI:
         The ClientGUI class is a Java Swing-based interface that enables client-server communication for various user actions, such as blocking users, sending messages, deleting messages, searching for users, and viewing profile information. The interface is organized into multiple panels, each corresponding to a specific action, and uses CardLayout for easy navigation between them. Communication with the server is handled through socket connections and object streams to send requests and receive responses. Each action includes input validation, and the system provides feedback through dialog boxes to guide the user. Error handling is also integrated to ensure smooth operation and provide informative responses in case of issues.
         
   b. Interfaces
     - UserInterface:
       The UserInterface outlines the operations for user management, including methods for accessing and modifying user details, friendships, and messaging capabilities. It provides a clear contract for implementing user-related functionality, enabling flexibility and consistent interactions across different user implementations.
       
     - UserDatabaseInterface:
       The UserDatabaseInterface defines essential operations for managing a user database, including methods for adding, removing, and saving user data. It provides a structured way to interact with the user collection, ensuring that all database-related actions are handled consistently.
   
     - MessageInterface:
       The MessageInterface establishes the required methods for handling messages, including retrieving details like sender, receiver, and content. This interface ensures that any message implementation adheres to a standard format, facilitating smooth message operations within the application.

     - ServerInterface:
       The ServerInterface provides shared constants and static methods for managing server-side functionality, including a user database, a list of logged-in users, and synchronization for thread-safe operations. It offers methods to add or remove logged-in users, ensuring no duplicate logins and safe modifications to the shared user list.
       
     - ClientHandlerInterface:
       The ClientHandlerInterface defines methods for handling various client-related actions in a server, such as user authentication, account creation, and managing interactions like adding friends or sending messages. It ensures a structured approach to implementing client operations while handling potential exceptions during input/output and class deserialization.
       
     - ClientInterface:
       The ClientInterface defines a single method, handleCommunication, to manage interactions between the client and the server.

     - ClientGUIInterface
       The ClientGUIInterface defines methods for creating and managing various panels and components in a client application's graphical user interface. It ensures a modular design by standardizing functions for tasks like building account panels, main menus, and action placeholders. The interface also supports user interactions with features like adding or removing friends, blocking users, and managing messages. Additionally, it provides methods for searching and viewing user profiles, as well as creating dynamic buttons and placeholders for actions. This structure promotes consistency, reusability, and adaptability in GUI development.
       
   c. Exceptions
     - BadException:
       The BadException class is a custom exception that handles specific error conditions in the project by allowing developers to provide descriptive error messages. It extends the standard Exception class, enabling it to be thrown and caught like other exceptions while offering additional context about the error through a detailed message.
       







   
