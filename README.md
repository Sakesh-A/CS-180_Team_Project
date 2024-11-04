# CS-180_Team_Project
1. d
2. "Student" Submitted Voareum Workspace
3. a. Classes
     - User:
       The User class models an individual user within the database, holding key information like username, password, and privacy status (isPublic). It maintains lists of friends, blocked users, and received messages, allowing each user to connect with others, manage interactions, and handle communication. Core methods in this class include adding/removing friends, blocking users, and sending or deleting messages, which enable interaction. The equals method ensures that each user is uniquely identified by their username. This class interacts with TextMessage for messaging and is managed collectively by UserDatabase, which organizes users.

     - UserDatabase:
       The UserDatabase class serves as a centralized manager for User instances, facilitating user registration, deletion, and message handling while ensuring thread safety through synchronization. It maintains lists of users, user-specific files, and associated writers to store user data persistently. Key methods include addUser to register new users, removeUser to delete existing users and manage their relationships, and everythingToFile to save the state of all users and their messages to files. The class is designed to prevent duplicate usernames and ensure proper cleanup of user data upon removal. It interacts closely with the User class for user management and utilizes TextMessage for message operations, ensuring a cohesive user experience within the system.
       
     - TextMessage:
       The TextMessage class represents a text message exchanged between users, encapsulating essential properties such as the sender's username, receiver's username, and the message content itself. It utilizes a string array to store these details, providing methods to retrieve each piece of information, including the sender and receiver usernames and the message content. The class overrides the equals method to ensure accurate comparison of TextMessage instances based on their sender, receiver, and message content, while the toString method formats the message for display. Additionally, it includes methods to access the original User objects for the sender and receiver, further facilitating interaction within the messaging system. This class is designed to work seamlessly with User and UserDatabase, enabling effective communication management within the application.

   b. Interfaces
     - UserInterface:
       
     - UserDatabasInterface:
   
     - MessageInterface:
       The MessageInterface defines a standardized contract for message objects within the application, specifying essential methods that any implementing class must provide. It includes methods to retrieve crucial message details, such as the sender's username, receiver's username, and the message content, ensuring consistent access across different message types. The interface also mandates the implementation of an equals method for comparing messages and a toString method for producing a formatted string representation of message details. By establishing these requirements, the interface facilitates polymorphism, allowing various message types to be handled uniformly within the messaging system. This design promotes code reusability and consistency, ensuring seamless interactions between message-related classes like TextMessage, User, and UserDatabase.



       
   c. Exceptions
     - BadException:
   
