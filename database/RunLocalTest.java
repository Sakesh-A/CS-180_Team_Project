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
            Class<?> clazz;
            int modifiers;
            Class<?> superclass;
            Class<?>[] superinterfaces;

            clazz = TextMessage.class;

            modifiers = clazz.getModifiers();

            superclass = clazz.getSuperclass();

            superinterfaces = clazz.getInterfaces();

            //todo
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