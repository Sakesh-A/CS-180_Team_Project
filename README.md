# CS-180_Team_Project
1. d
2. "Student" Submitted Voareum Workspace
3. a. Classes
     - User:
       The User class models an individual user within the database, holding key information like username, password, and privacy status (isPublic). It maintains lists of friends, blocked users, and received messages, allowing each user to connect with others, manage interactions, and handle communication. Core methods in this class include adding/removing friends, blocking users, and sending or deleting messages, which enable interaction. The equals method ensures that each user is uniquely identified by their username. This class interacts with TextMessage for messaging and is managed collectively by UserDatabase, which organizes users.

     - UserDatabase:
       The UserDatabase class serves as a centralized manager for User instances, facilitating user registration, deletion, and message handling while ensuring thread safety through synchronization. It maintains lists of users, user-specific files, and associated writers to store user data persistently. Key methods include addUser to register new users, removeUser to delete existing users and manage their relationships, and everythingToFile to save the state of all users and their messages to files. The class is designed to prevent duplicate usernames and ensure proper cleanup of user data upon removal. It interacts closely with the User class for user management and utilizes TextMessage for message operations, ensuring a cohesive user experience within the system.
       
     - TextMessage:

   b. Interfaces
     - UserInterface:
       
     - UserDatabasInterface:
   
     - MessageInterface:
       
   c. Exceptions
     - BadException:
   
