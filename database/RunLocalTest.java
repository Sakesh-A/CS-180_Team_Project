import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.*;

import java.io.*;
import java.util.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

/**
 * Team Project -- RunLocalTest
 *
 * Test Cases for all classes and methods.
 *
 *
 * @author Mahith Narreddy, Daniel Zhang, Sakesh Andhavarapu, Zachary O'Connell, Seth Jeevanandham
 *
 * @version Nov 3, 2024
 *
 */

public class RunLocalTest {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TestCase.class); //stores the result of the JUnit test
        if (result.wasSuccessful()) {
            System.out.println("Excellent - Test ran successfully");
        } else {
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }
        }
    }

    public static class TestCase {
        public static final String loginFile = "UsersList.txt";
        
        @Test(timeout = 1000)
        public void BadDataExceptionDeclarationTest() {
                Class<?> clazz;
                int modifiers;
                Class<?> superclass;
                Class<?>[] superinterfaces;

                clazz = BadException.class;

                modifiers = clazz.getModifiers();

                superclass = clazz.getSuperclass();

                superinterfaces = clazz.getInterfaces();

                Assert.assertTrue("Ensure that `BadDataException` is `public`!",
                        Modifier.isPublic(modifiers));
                Assert.assertFalse("Ensure that `BadDataException` is NOT `abstract`!",
                        Modifier.isAbstract(modifiers));
                Assert.assertEquals("Ensure that `BadDataException` extends `Exception`!",
                        Exception.class, superclass);
                Assert.assertEquals("Ensure that `BadDataException` implements no interfaces!",
                        0, superinterfaces.length);
        }

        @Test(timeout = 1000)
        public void UserTest() {
                Class<?> clazz;
                int modifiers;
                Class<?> superclass;
                Class<?>[] superinterfaces;

                clazz = User.class;

                modifiers = clazz.getModifiers();
                assertTrue("User class should be public",
                        java.lang.reflect.Modifier.isPublic(modifiers));

                superclass = clazz.getSuperclass();
                assertEquals("User class should extend Object", Object.class, superclass);

                superinterfaces = clazz.getInterfaces();
                boolean implementsUserInterface = false;

                for (Class<?> iface : superinterfaces) {
                        if (iface.equals(UserInterface.class)) {
                                implementsUserInterface = true;
                                break;
                        }
                }

                assertTrue("User class should implement UserInterface", implementsUserInterface);

                // Constructor and fields check
                String username = "TestUser";
                String password = "Password1!";
                boolean isPublic = true;

                User user = new User(username, password, isPublic);

                // Username, password, and isPublic fields check
                assertEquals("Username should be set correctly", username, user.getUsername());
                assertEquals("Password should be set correctly", password, user.getPassword());
                assertEquals("isPublic should be set correctly", isPublic, user.isPublic());

                // Friends, blockedUsers, messages, and photos lists initialized as empty check
                assertNotNull("Friends list should be initialized", user.getFriends());
                assertTrue("Friends list should initially be empty", user.getFriends().isEmpty());

                assertNotNull("Blocked users list should be initialized", user.getBlockedUsers());
                assertTrue("Blocked users list should initially be empty", user.getBlockedUsers().isEmpty());

                assertNotNull("Messages list should be initialized", user.getMessages());
                assertTrue("Messages list should initially be empty", user.getMessages().isEmpty());

                // addFriend method check
                User friend = new User("FriendUser", "FriendPassword1!",
                        true);
                assertTrue("User should be able to add a friend", user.addFriend(friend));
                assertEquals("Friends list should contain the added friend", 1,
                        user.getFriends().size());

                // removeFriend method check
                assertTrue("User should be able to remove a friend", user.removeFriend(friend));
                assertTrue("Friends list should be empty after removing the friend",
                        user.getFriends().isEmpty());

                // blockUser method check
                User blockedUser = new User("BlockedUser", "BlockedPassword1!",
                        true);
                assertTrue("User should be able to block another user", user.blockUser(blockedUser));
                assertEquals("Blocked users list should contain the blocked user", 1,
                        user.getBlockedUsers().size());

                // sendMessage method check
                User receiver = new User("ReceiverUser", "ReceiverPassword1!",
                        true);
                String message = "Hello, this is a test message!";

                assertTrue("User should be able to send a message to a receiver",
                        user.sendMessage(receiver, message));
                assertEquals("Sender's messages list should contain the sent message", 1,
                        user.getMessages().size());
                assertEquals("Receiver's messages list should contain the received message", 1,
                        receiver.getMessages().size());

                // deleteMessage method check
                user.deleteMessage(receiver, message);
                assertTrue("Sender's messages list should be empty after deleting the message",
                        user.getMessages().isEmpty());
                assertTrue("Receiver's messages list should be empty after deleting the message",
                        receiver.getMessages().isEmpty());

                // hasBlocked method check
                assertTrue("User should recognize the blocked user", user.hasBlocked(blockedUser));

                // hasFriended method check
                user.addFriend(friend);
                assertTrue("User should recognize a friend in their friends list",
                        user.hasFriended(friend));

                // toString method check
                String expectedToString = "TestUser,Password1!," + isPublic + "End of Friends";
                assertTrue("toString method should return the correct string representation",
                        user.toString().contains(expectedToString));

                // setter and getter methods for username, password, and isPublic checks
                String newUsername = "NewTestUser";
                String newPassword = "NewPassword1@";
                boolean newIsPublic = false;

                user.setUsername(newUsername);
                assertEquals("Username should be updated correctly", newUsername,
                        user.getUsername());

                user.setPassword(newPassword);
                assertEquals("Password should be updated correctly", newPassword,
                        user.getPassword());

                user.setPublic(newIsPublic);
                assertEquals("isPublic should be updated correctly", newIsPublic,
                        user.isPublic());

                // setter and getter methods for friends and blocked users lists check
                ArrayList<User> newFriends = new ArrayList<>();
                newFriends.add(friend);
                user.setFriends(newFriends);
                assertEquals("Friends list should be updated correctly", newFriends,
                        user.getFriends());

                ArrayList<User> newBlockedUsers = new ArrayList<>();
                newBlockedUsers.add(blockedUser);
                user.setBlockedUsers(newBlockedUsers);
                assertEquals("Blocked users list should be updated correctly", newBlockedUsers,
                        user.getBlockedUsers());
                }

                @Test(timeout = 1000)
                public void UserInterfaceTest() {
                Class<?> clazz;
                int modifiers;
                Class<?> superclass;
                Class<?>[] superinterfaces;

                clazz = UserInterface.class;

                modifiers = clazz.getModifiers();
                assertTrue("UserInterface should be an interface",
                        Modifier.isInterface(modifiers));

                superclass = clazz.getSuperclass();
                assertNull("UserInterface should not extend any superclass", superclass);

                superinterfaces = clazz.getInterfaces();
                assertEquals("UserInterface should not implement any other interfaces", 0,
                        superinterfaces.length);

                // All methods check
                Method[] methods = clazz.getDeclaredMethods();

                // Expected methods and signatures check
                boolean hasGetUsername = false;
                boolean hasSetUsername = false;
                boolean hasSetPassword = false;
                boolean hasGetPassword = false;
                boolean hasIsPublic = false;
                boolean hasSetPublic = false;
                boolean hasGetFriends = false;
                boolean hasSetFriends = false;
                boolean hasGetBlockedUsers = false;
                boolean hasSetBlockedUsers = false;
                boolean hasAddFriend = false;
                boolean hasRemoveFriend = false;
                boolean hasBlockUser = false;
                boolean hasSendMessage = false;
                boolean hasDeleteMessage = false;
                boolean hasEquals = false;

                for (Method method : methods) {
                        switch (method.getName()) {
                                case "getUsername":
                                        hasGetUsername = true;
                                        assertEquals("getUsername should return String", String.class,
                                        method.getReturnType());
                                assertEquals("getUsername should have no parameters", 0,
                                        method.getParameterCount());
                                break;

                        case "setUsername":
                                hasSetUsername = true;
                                assertEquals("setUsername should return void", void.class,
                                        method.getReturnType());
                                assertEquals("setUsername should have one parameter", 1,
                                        method.getParameterCount());
                                assertEquals("setUsername parameter should be of type String",
                                        String.class, method.getParameterTypes()[0]);
                                break;

                        case "setPassword":
                                hasSetPassword = true;
                                assertEquals("setPassword should return void", void.class,
                                        method.getReturnType());
                                assertEquals("setPassword should have one parameter", 1,
                                        method.getParameterCount());
                                assertEquals("setPassword parameter should be of type String",
                                        String.class, method.getParameterTypes()[0]);
                                break;

                        case "getPassword":
                                hasGetPassword = true;
                                assertEquals("getPassword should return String",
                                        String.class, method.getReturnType());
                                assertEquals("getPassword should have no parameters", 0,
                                        method.getParameterCount());
                                break;

                        case "isPublic":
                                hasIsPublic = true;
                                assertEquals("isPublic should return boolean", boolean.class,
                                        method.getReturnType());
                                assertEquals("isPublic should have no parameters", 0,
                                        method.getParameterCount());
                                break;

                        case "setPublic":
                                hasSetPublic = true;
                                assertEquals("setPublic should return void", void.class,
                                        method.getReturnType());
                                assertEquals("setPublic should have one parameter", 1,
                                        method.getParameterCount());
                                assertEquals("setPublic parameter should be of type boolean",
                                        boolean.class, method.getParameterTypes()[0]);
                                break;

                        case "getFriends":
                                hasGetFriends = true;
                                assertEquals("getFriends should return ArrayList<User>",
                                        ArrayList.class, method.getReturnType());
                                assertEquals("getFriends should have no parameters", 0,
                                        method.getParameterCount());
                                break;

                        case "setFriends":
                                hasSetFriends = true;
                                assertEquals("setFriends should return void", void.class,
                                        method.getReturnType());
                                assertEquals("setFriends should have one parameter", 1,
                                        method.getParameterCount());
                                assertEquals("setFriends parameter should be of type ArrayList<User>",
                                        ArrayList.class, method.getParameterTypes()[0]);
                                break;

                        case "getBlockedUsers":
                                hasGetBlockedUsers = true;
                                assertEquals("getBlockedUsers should return ArrayList<User>",
                                        ArrayList.class, method.getReturnType());
                                assertEquals("getBlockedUsers should have no parameters", 0,
                                        method.getParameterCount());
                                break;

                        case "setBlockedUsers":
                                hasSetBlockedUsers = true;
                                assertEquals("setBlockedUsers should return void", void.class,
                                        method.getReturnType());
                                assertEquals("setBlockedUsers should have one parameter", 1,
                                        method.getParameterCount());
                                assertEquals("setBlockedUsers parameter should be of type " +
                                        "ArrayList<User>", ArrayList.class,
                                        method.getParameterTypes()[0]);
                                break;

                        case "addFriend":
                                hasAddFriend = true;
                                assertEquals("addFriend should return boolean", boolean.class,
                                        method.getReturnType());
                                assertEquals("addFriend should have one parameter", 1,
                                        method.getParameterCount());
                                assertEquals("addFriend parameter should be of type User",
                                        User.class, method.getParameterTypes()[0]);
                                break;

                        case "removeFriend":
                                hasRemoveFriend = true;
                                assertEquals("removeFriend should return boolean",
                                        boolean.class, method.getReturnType());
                                assertEquals("removeFriend should have one parameter", 1,
                                        method.getParameterCount());
                                assertEquals("removeFriend parameter should be of type User",
                                        User.class, method.getParameterTypes()[0]);
                                break;

                        case "blockUser":
                                hasBlockUser = true;
                                assertEquals("blockUser should return boolean", boolean.class,
                                        method.getReturnType());
                                assertEquals("blockUser should have one parameter", 1,
                                        method.getParameterCount());
                                assertEquals("blockUser parameter should be of type User",
                                        User.class, method.getParameterTypes()[0]);
                                break;

                        case "sendMessage":
                                hasSendMessage = true;
                                assertEquals("sendMessage should return boolean", boolean.class,
                                        method.getReturnType());
                                assertEquals("sendMessage should have two parameters", 2,
                                       method.getParameterCount());
                                assertEquals("sendMessage first parameter should be of type User",
                                        User.class, method.getParameterTypes()[0]);
                                assertEquals("sendMessage second parameter should be of type String",
                                        String.class, method.getParameterTypes()[1]);
                                break;

                        case "deleteMessage":
                                hasDeleteMessage = true;
                                assertEquals("deleteMessage should return void", void.class,
                                        method.getReturnType());
                                assertEquals("deleteMessage should have two parameters", 2,
                                        method.getParameterCount());
                                assertEquals("deleteMessage first parameter should be of type User",
                                        User.class, method.getParameterTypes()[0]);
                                assertEquals("deleteMessage second parameter should be of type String",
                                        String.class, method.getParameterTypes()[1]);
                                break;

                        case "equals":
                                hasEquals = true;
                                assertEquals("equals should return boolean", boolean.class,
                                        method.getReturnType());
                                assertEquals("equals should have one parameter", 1,
                                        method.getParameterCount());
                                assertEquals("equals parameter should be of type Object",
                                        Object.class, method.getParameterTypes()[0]);
                                break;
                        }       
                }

                // All expected methods found check
                assertTrue("UserInterface should have getUsername method", hasGetUsername);
                assertTrue("UserInterface should have setUsername method", hasSetUsername);
                assertTrue("UserInterface should have setPassword method", hasSetPassword);
                assertTrue("UserInterface should have getPassword method", hasGetPassword);
                assertTrue("UserInterface should have isPublic method", hasIsPublic);
                assertTrue("UserInterface should have setPublic method", hasSetPublic);
                assertTrue("UserInterface should have getFriends method", hasGetFriends);
                assertTrue("UserInterface should have setFriends method", hasSetFriends);
                assertTrue("UserInterface should have getBlockedUsers method", hasGetBlockedUsers);
                assertTrue("UserInterface should have setBlockedUsers method", hasSetBlockedUsers);
                assertTrue("UserInterface should have addFriend method", hasAddFriend);
                assertTrue("UserInterface should have removeFriend method", hasRemoveFriend);
                assertTrue("UserInterface should have blockUser method", hasBlockUser);
                assertTrue("UserInterface should have sendMessage method", hasSendMessage);
                assertTrue("UserInterface should have deleteMessage method", hasDeleteMessage);
                assertTrue("UserInterface should have equals method", hasEquals);
        }

        @Test(timeout = 1000)
        public void MessageInterfaceTest() {
                Class<?> clazz = MessageInterface.class;

                // Check if the interface is public
                int modifiers = clazz.getModifiers();
                assertTrue("MessageInterface should be public", java.lang.reflect.Modifier.isPublic(modifiers));

                // Check that it is an interface
                assertTrue("MessageInterface should be an interface", clazz.isInterface());

                // Check superclass (should be Object)
                Class<?> superclass = clazz.getSuperclass();
                assertEquals("MessageInterface should extend Object", Object.class, superclass);

                // Check methods
                String[] expectedMethodNames = {
                        "getMessageArray",
                        "equals",
                        "toString",
                        "getSenderUsername",
                        "getReceiverUsername",
                        "getMessageContent"
                };

                for (String methodName : expectedMethodNames) {
                        try {
                            // Check the method exists with correct signature
                            if (methodName.equals("equals")) {
                                clazz.getMethod(methodName, Object.class);
                            } else if (methodName.equals("toString")) {
                                clazz.getMethod(methodName);
                            } else {
                                clazz.getMethod(methodName);
                            }
                        } catch (NoSuchMethodException e) {
                            fail("Method " + methodName + " should exist in MessageInterface");
                        }
                }

                // You may also want to ensure the interface cannot be instantiated
                try {
                        MessageInterface msgInterfaceInstance = (MessageInterface) clazz.getDeclaredConstructor().newInstance();
                        fail("MessageInterface should not be instantiable");
                } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                // Expected behavior, continue
                }
        }

        @Test(timeout = 1000)
        public void TextMessageTest() {
                Class<?> clazz = TextMessage.class;

                // Check if the class is public
                int modifiers = clazz.getModifiers();
                assertTrue("TextMessage should be public", java.lang.reflect.Modifier.isPublic(modifiers));

                // Check superclass
                Class<?> superclass = clazz.getSuperclass();
                assertEquals("TextMessage should extend Object", Object.class, superclass);

                // Check interfaces
                Class<?>[] superinterfaces = clazz.getInterfaces();
                assertEquals("TextMessage should implement MessageInterface", 1, superinterfaces.length);
                assertEquals("TextMessage should implement MessageInterface", MessageInterface.class, superinterfaces[0]);

                // Create a User object for testing
                User sender = new User("Alice");
                User receiver = new User("Bob");

                // Create a TextMessage instance
                String messageContent = "Hello, Bob!";
                TextMessage textMessage = new TextMessage(messageContent, sender, receiver);

                // Test getters
                assertEquals("Sender username should match", "Alice", textMessage.getSenderUsername());
                assertEquals("Receiver username should match", "Bob", textMessage.getReceiverUsername());
                assertEquals("Message content should match", messageContent, textMessage.getMessageContent());

                // Test the equality method
                TextMessage sameMessage = new TextMessage(messageContent, sender, receiver);
                assertTrue("TextMessages with the same content should be equal", textMessage.equals(sameMessage));

                TextMessage differentMessage = new TextMessage("Different Message", sender, receiver);
                assertFalse("TextMessages with different content should not be equal", textMessage.equals(differentMessage));

                // Test toString
                String expectedString = "Alice,Bob,Hello, Bob!;";
                assertEquals("toString should return the correct format", expectedString, textMessage.toString());
        }

        @Test(timeout = 1000)
        public void PhotoMessageTest() {
                Class<?> clazz = PhotoMessage.class;

                // Check if the class is public
                int modifiers = clazz.getModifiers();
                assertTrue("PhotoMessage should be public", java.lang.reflect.Modifier.isPublic(modifiers));

                // Check superclass
                Class<?> superclass = clazz.getSuperclass();
                assertEquals("PhotoMessage should extend TextMessage", TextMessage.class, superclass);

                // Check interfaces
                Class<?>[] superinterfaces = clazz.getInterfaces();
                assertEquals("PhotoMessage should implement MessageInterface", 1, superinterfaces.length);
                assertEquals("PhotoMessage should implement MessageInterface", MessageInterface.class, superinterfaces[0]);

                // Create User objects for testing
                User sender = new User("Alice");
                User receiver = new User("Bob");

                // Create a PhotoMessage instance
                String messageContent = "Check out this photo!";
                String photoUrl = "http://example.com/photo.jpg";
                PhotoMessage photoMessage = new PhotoMessage(messageContent, sender, receiver, photoUrl);

                // Test getters
                assertEquals("Sender username should match", "Alice", photoMessage.getSenderUsername());
                assertEquals("Receiver username should match", "Bob", photoMessage.getReceiverUsername());
                assertEquals("Message content should match", messageContent, photoMessage.getMessageContent());
                assertEquals("Photo URL should match", photoUrl, photoMessage.getPhoto());

                // Test setPhoto
                String newPhotoUrl = "http://example.com/new_photo.jpg";
                photoMessage.setPhoto(newPhotoUrl);
                assertEquals("Photo URL should be updated", newPhotoUrl, photoMessage.getPhoto());

                // Test the equality method
                PhotoMessage samePhotoMessage = new PhotoMessage(messageContent, sender, receiver, photoUrl);
                assertTrue("PhotoMessages with the same content and photo should be equal", photoMessage.equals(samePhotoMessage));

                PhotoMessage differentPhotoMessage = new PhotoMessage("Different Message", sender, receiver, newPhotoUrl);
                assertFalse("PhotoMessages with different content should not be equal", photoMessage.equals(differentPhotoMessage));

                // Test toString inherited from TextMessage
                String expectedString = "Alice,Bob,Check out this photo!;";
                assertEquals("toString should return the correct format from TextMessage", expectedString, photoMessage.toString());
                }
        }
}
