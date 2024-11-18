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
      - PhotoMessageTest:
           - We test every method in the PhotoMessage class.
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
             
   b. Manual Testing/Compiling
      - We used JUNIT to write all of our test cases.+ Manual Testing/Compiling Instructions: Place all the required .java files (e.g., Server.java, Client.java, ClientHandler.java, User.java, UserDatabase.java, and any other dependencies like TextMessage or PhotoMessage) in the same directory.
      - Open a terminal or command prompt.
      - Navigate to the directory containing the .java files using the cd command.
      - Start the server in the terminal. The server will begin on the default port and wait for client connections.
      - Open a new terminal or command prompt.
      - Navigate to the same directory where the compiled classes are located.
      - Start the client. The client will attempt to connect to the server at localhost.
      - Then, the client will receive options to either "CREATE_ACCOUNT" or "LOGIN". The client has to type the chosen option next to "Your input:"
      - The related message will be printed on the terminal if there are any errors with creating the account or logging in. Then you will be given the opportunity to act again by typing "CREATE_ACCOUNT" or "LOGIN".
      - If the client choose to create an account, you have to perform the "LOGIN" before you get access to the rest of the actions available.
      - After Logging in, all actions will be printed to the terminal one time: "ADD_FRIEND" "REMOVE_FRIEND" "BLOCK_USER" "SEND_MESSAGE" "DELETE_MESSAGE"  "SEARCH_USER"  "VIEW_USER"  "LOGOUT".
      - To perform any of these functions, the client has to type them next to "Your input:", and the user will be prompted for more information.
      - "ADD_FRIEND" adds a friend, "REMOVE_FRIEND" removes a friend, "BLOCK_USER" blocks a user, "SEND_MESSAGE" sends a message to a specific user, "DELETE_MESSAGE" deletes a message that was sent to a certain user, "SEARCH_USER" prints out the username if the username the client enters exists, "VIEW_USER" shows the clients personal information, and "LOGOUT" disconnects from the server.
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
       
     - PhotoMessage:
        It is very similar to TextMessage, but it is still in progress.
       
     - Server:
       The Server class is responsible for facilitating client connections and user authentication. It maintains a thread-safe list of currently logged-in users and delegates client-specific operations to the ClientHandler class. The server continuously looks for incoming client connections on a specified port and starts a new thread to handle each client session.
       
     - ClientHandler:
       The ClientHandler class is a thread responsible for managing interactions with an individual client connected to the server. It handles tasks such as authenticating users, managing friend requests, blocking users, sending and deleting messages, and providing various account-related operations. Each client interaction is processed independently in a thread-safe manner, ensuring concurrent handling of multiple clients. The class maintains a connection with the client through input and output streams, which enables real-time communication and updates. Upon client logout or disconnection, the handler ensures proper cleanup by updating the server's logged-in users list and saving relevant data.
       
     - Client:
       The Client class establishes a connection to the server, enabling communication through object streams for sending and receiving messages. It manages user interactions through the console for now, allowing users to perform actions like login, account creation, and various commands sent to the server. The client handles the server by providing real-time feedback to users and ensuring graceful disconnection when the session ends.

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
       
   c. Exceptions
     - BadException:
       The BadException class is a custom exception that handles specific error conditions in the project by allowing developers to provide descriptive error messages. It extends the standard Exception class, enabling it to be thrown and caught like other exceptions while offering additional context about the error through a detailed message.
       







   
