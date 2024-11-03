import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

public class RunLocalTest {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TestCase.class);
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
            assertTrue("User class should be public", java.lang.reflect.Modifier.isPublic(modifiers));

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
        }

        @Test(timeout = 1000)
        public void UserInterfaceTest() {
            Class<?> clazz;
            int modifiers;
            Class<?> superclass;
            Class<?>[] superinterfaces;

            clazz = UserInterface.class;

            modifiers = clazz.getModifiers();

            superclass = clazz.getSuperclass();

            superinterfaces = clazz.getInterfaces();

            //todo
        }

        @Test(timeout = 1000)
        public void MessageTest() {
            Class<?> clazz;
            int modifiers;
            Class<?> superclass;
            Class<?>[] superinterfaces;

            clazz = User.class;

            modifiers = clazz.getModifiers();

            superclass = clazz.getSuperclass();

            superinterfaces = clazz.getInterfaces();

            //todo

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
            Class<?> clazz;
            int modifiers;
            Class<?> superclass;
            Class<?>[] superinterfaces;

            clazz = PhotoMessage.class;

            modifiers = clazz.getModifiers();

            superclass = clazz.getSuperclass();

            superinterfaces = clazz.getInterfaces();

            //todo
        }




    }
}