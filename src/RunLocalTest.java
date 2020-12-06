import org.junit.Test;
import org.junit.After;
import java.lang.reflect.Field;
import org.junit.Assert;
import org.junit.Before;
import org.junit.rules.Timeout;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * A framework to run public test cases.
 *
 * @author AJ, Camber, Evan, Ian, Ruth
 * @version December 2, 2020
 */
public class RunLocalTest {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TestCase.class);
        if (result.wasSuccessful()) {
            System.out.println("Excellent - Tests ran successfully");
        } else {
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }
        }
    }

    /**
     * A framework to run public test cases.
     *
     * @author AJ, Camber, Evan, Ian, Ruth
     * @version December 2, 2020
     */
    public static class TestCase {
        private final PrintStream originalOutput = System.out;
        private final InputStream originalSysin = System.in;

        @SuppressWarnings("FieldCanBeLocal")
        private ByteArrayInputStream testIn;

        @SuppressWarnings("FieldCanBeLocal")
        private ByteArrayOutputStream testOut;

        @Before
        public void outputStart() {
            testOut = new ByteArrayOutputStream();
            System.setOut(new PrintStream(testOut));
        }

        @After
        public void restoreInputAndOutput() {
            System.setIn(originalSysin);
            System.setOut(originalOutput);
        }

        private String getOutput() {
            return testOut.toString();
        }

        @SuppressWarnings("SameParameterValue")
        private void receiveInput(String str) {
            testIn = new ByteArrayInputStream(str.getBytes());
            System.setIn(testIn);
        }

        // testing for User class
        @Test(timeout = 1_000)
        public void userClassTest() {
            // check if User class exists
            try {
                Class.forName("User");
            }
            catch(ClassNotFoundException e) {
                System.out.println("Ensure that `User` exists!");
                return;
            }
            Class<?> userObject = User.class;
            // check for correct superclass
            Class<?> superclass = userObject.getSuperclass();
            assertEquals("Ensure that your `User` class does NOT extend any other class!", superclass, Object.class);
            // check if fields exist
            Field name;
            Field username;
            Field email;
            Field phoneNumber;
            Field password;
            Field groups;
            Method method;
            Class<?> returnType;
            Class<?> expectedReturnType;
            int modifiers;
            try {
                name = userObject.getField("name");
                username = userObject.getField("username");
                email = userObject.getField("email");
                phoneNumber = userObject.getField("phoneNumber");
                password = userObject.getField("password");
                groups = userObject.getField("groups");
            }
            catch(NoSuchFieldException e) {
                System.out.println(e.toString());
                return;
            }
            // check fields of class for correct access modifier and data type
            modifiers = name.getModifiers();
            assertTrue("Ensure that `name` in `User` class is private!", Modifier.isPrivate(modifiers));
            assertTrue("Ensure that `name` in `User` class is of type String!", String.class.isAssignableFrom(name.getType()));
            modifiers = username.getModifiers();
            assertTrue("Ensure that `username` in `User` class is private!", Modifier.isPrivate(modifiers));
            assertTrue("Ensure that `username` in `User` class is of type String!", String.class.isAssignableFrom(username.getType()));
            modifiers = email.getModifiers();
            assertTrue("Ensure that `email` in `User` class is private!", Modifier.isPrivate(modifiers));
            assertTrue("Ensure that `email` in `User` class is of type String!", String.class.isAssignableFrom(email.getType()));
            modifiers = phoneNumber.getModifiers();
            assertTrue("Ensure that `phoneNumber` in `User` class is private!", Modifier.isPrivate(modifiers));
            assertTrue("Ensure that `phoneNumber` in `User` class is of type String!", Long.class.isAssignableFrom(phoneNumber.getType()));
            modifiers = password.getModifiers();
            assertTrue("Ensure that `password` in `User` class is private!", Modifier.isPrivate(modifiers));
            assertTrue("Ensure that `password` in `User` class is of type String!", String.class.isAssignableFrom(password.getType()));
            modifiers = groups.getModifiers();
            assertTrue("Ensure that `groups` in `User` class is private!", Modifier.isPrivate(modifiers));
            assertTrue("Ensure that `groups` in `User` class is of type String!", ArrayList.class.isAssignableFrom(groups.getType()));
            
            // check if methods are implemented correctly

            String testName = "Ryan";
            String testUsername = "Ryan123";
            String testEmail = "Ryan111@gmail.com";
            long testPhoneNumber = 1300443522;
            String testPassword = "NotRyan532";
            int testUserID = 23;
            User tester = new User(testName, testUsername, testEmail, testPhoneNumber, testPassword);
            try {
                method = userObject.getDeclaredMethod("getUsername");
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `User` declares a method named `getUsername` that has no parameters!");
                return;
            }
            modifiers = method.getModifiers();
            returnType = method.getReturnType();
            expectedReturnType = User.class;

            Assert.assertTrue("Ensure that `User`'s `getUsername` method is `public`", Modifier.isPublic(modifiers));
            Assert.assertEquals("Ensure that `User`'s `getUsername` method has the correct return type!", expectedReturnType, returnType);

            // verifying getUsername

            assertEquals("The username and getting the username must match", testUserntester.getUsername());

            // verifying failure getUsername

            assertEquals("The email and the username do not match", testEmail, tester.getUsername());

            try {
                method = userObject.getDeclaredMethod("getPassword");
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `User` declares a method named `getPassword` that has no parameters!");
                return;
            }
            modifiers = method.getModifiers();
            returnType = method.getReturnType();
            expectedReturnType = User.class;

            Assert.assertTrue("Ensure that `User`'s `getPassword` method is `public`", Modifier.isPublic(modifiers));
            Assert.assertEquals("Ensure that `User`'s `getPassword` method has the correct return type!", expectedReturnType, returnType);

            // verifying getPassword

            assertEquals("The password and getting the password must match", testPassword, tester.getPassword());

            // verifying failure getPassword

            assertEquals("The email and the password do not match", testEmail, tester.getPassword());

            try {
                method = userObject.getDeclaredMethod("getName");
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `User` declares a method named `getName` that has no parameters!");
                return;
            }
            modifiers = method.getModifiers();
            returnType = method.getReturnType();
            expectedReturnType = User.class;

            Assert.assertTrue("Ensure that `User`'s `getName` method is `public`", Modifier.isPublic(modifiers));
            Assert.assertEquals("Ensure that `User`'s `getName` method has the correct return type!", expectedReturnType, returnType);

            // verifying getName

            assertEquals("The name and getting the name must match", testName, tester.getName());

            // verifying failure getName

            assertEquals("The email and the name do not match", testEmail, tester.getName());

            try {
                method = userObject.getDeclaredMethod("getPhoneNumber");
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `User` declares a method named `getName` that has no parameters!");
                return;
            }
            modifiers = method.getModifiers();
            returnType = method.getReturnType();
            expectedReturnType = User.class;

            Assert.assertTrue("Ensure that `User`'s `getPhoneNumber` method is `public`", Modifier.isPublic(modifiers));
            Assert.assertEquals("Ensure that `User`'s `getPhoneNumber` method has the correct return type!", expectedReturnType, returnType);

            // verifying getPhoneNumber

            assertEquals("The password and getting the password must match", testPhone, tester.getPhoneNumber());

            // verifying failure getPhoneNumber

            assertEquals("The email and the password do not match", testEmail, tester.getPhoneNumber());

            try {
                method = userObject.getDeclaredMethod("getEmail");
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `User` declares a method named `getEmail` that has no parameters!");
                return;
            }
            modifiers = method.getModifiers();
            returnType = method.getReturnType();
            expectedReturnType = User.class;

            Assert.assertTrue("Ensure that `User`'s `getEmail` method is `public`", Modifier.isPublic(modifiers));
            Assert.assertEquals("Ensure that `User`'s `getEmail` method has the correct return type!", expectedReturnType, returnType);

            // verifying getEmail

            assertEquals("The email and getting the email must match", testEmail, tester.getEmail());

            // verifying failure getEmail

            assertEquals("The email and the password do not match", testPassword, tester.getEmail());

            try {
                method = userObject.getDeclaredMethod("getUserID");
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `User` declares a method named `getUserID` that has no parameters!");
                return;
            }
            modifiers = method.getModifiers();
            returnType = method.getReturnType();
            expectedReturnType = User.class;

            Assert.assertTrue("Ensure that `User`'s `getUserID` method is `public`", Modifier.isPublic(modifiers));
            Assert.assertEquals("Ensure that `User`'s `getUserID` method has the correct return type!", expectedReturnType, returnType);

            // verifying getUserID

            assertEquals("The userID and getting the userID must match", testUserID, tester.getEmail());

            // verifying failure getUserID

            assertEquals("The userID and the email do not match", testUserID, tester.getEmail());

            try {
                method = userObject.getDeclaredMethod("setUsername", String.class);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `User` declares a method named `setUsername` that has one parameter of type" +
                        "String!");
                return;
            }
            modifiers = method.getModifiers();
            returnType = method.getReturnType();

            Assert.assertTrue("Ensure that `User`'s `setUsername` method is `public`", Modifier.isPublic(modifiers));
            assertNull("Ensure that `User`'s `setUsername` method has the correct return type!", returnType);

            // verifying setUsername

            tester.setUsername("I am changed");
            assertEquals("Text must match from being changed","I am changed", tester.getUsername());

            // verifying failure setUsername

            tester.setUsername("Changing again");
            assertEquals("Text can not match since they are different values", "I am changed", tester.getUsername());

            try {
                method = userObject.getDeclaredMethod("setName", String.class);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `User` declares a method named `setName` that has one parameter of type" +
                        "String!");
                return;
            }
            modifiers = method.getModifiers();
            returnType = method.getReturnType();

            Assert.assertTrue("Ensure that `User`'s `setName` method is `public`", Modifier.isPublic(modifiers));
            assertNull("Ensure that `User`'s `setName` method has the correct return type!", returnType);

            // verifying setName

            tester.setName("I am changed");
            assertEquals("Text must match from being changed","I am changed", tester.getName());

            // verifying failure setName

            tester.setName("Changing again");
            assertEquals("Text can not match since they are different values", "I am changed", tester.getName());

            try {
                method = userObject.getDeclaredMethod("setEmail", String.class);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `User` declares a method named `setEmail` that has one parameter of type" +
                        "String!");
                return;
            }
            modifiers = method.getModifiers();
            returnType = method.getReturnType();

            Assert.assertTrue("Ensure that `User`'s `setEmail` method is `public`", Modifier.isPublic(modifiers));
            assertNull("Ensure that `User`'s `setEmail` method has the correct return type!", returnType);

            // verifying setEmail

            tester.setEmail("I am changed");
            assertEquals("Text must match from being changed","I am changed", tester.getEmail());

            // verifying failure setEmail

            tester.setEmail("Changing again");
            assertEquals("Text can not match since they are different values", "I am changed", tester.getEmail());

            try {
                method = userObject.getDeclaredMethod("setPhoneNumber", Long.class);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `User` declares a method named `setPhoneNumber` that has one parameter of type" +
                        "Long!");
                return;
            }
            modifiers = method.getModifiers();
            returnType = method.getReturnType();

            Assert.assertTrue("Ensure that `User`'s `setPhoneNumber` method is `public`", Modifier.isPublic(modifiers));
            assertNull("Ensure that `User`'s `setPhoneNumber` method has the correct return type!", returnType);

            // verifying setPhoneNumber

            tester.setPhoneNumber("I am changed");
            assertEquals("Text must match from being changed","I am changed", tester.getPhoneNumber());

            // verifying failure setPhoneNumber

            tester.setPhoneNumber("Changing again");
            assertEquals("Text can not match since they are different values", "I am changed", tester.getPhoneNumber());

            try {
                method = userObject.getDeclaredMethod("setPassword", String.class);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `User` declares a method named `setPassword` that has one parameter of type" +
                        "String!");
                return;
            }
            modifiers = method.getModifiers();
            returnType = method.getReturnType();

            Assert.assertTrue("Ensure that `User`'s `setPassword` method is `public`", Modifier.isPublic(modifiers));
            assertNull("Ensure that `User`'s `setPassword` method has the correct return type!", returnType);

            // verifying setPassword

            tester.setPassword("I am changed");
            assertEquals("Text must match from being changed","I am changed", tester.getPassword());

            // verifying failure setPassword

            tester.setPassword("Changing again");
            assertEquals("Text can not match since they are different values", "I am changed", tester.getPassword());

            try {
                method = userObject.getDeclaredMethod("setUsername", Integer.class);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `User` declares a method named `setUserID` that has one parameter of type" +
                        "int!");
                return;
            }
            modifiers = method.getModifiers();
            returnType = method.getReturnType();

            Assert.assertTrue("Ensure that `User`'s `setUserID` method is `public`", Modifier.isPublic(modifiers));
            assertNull("Ensure that `User`'s `setUserID` method has the correct return type!", returnType);

            // verifying setUserID

            tester.setUserID("I am changed");
            assertEquals("Text must match from being changed","I am changed", tester.getUserID());

            // verifying failure setUserID

            tester.setUserID("Changing again");
            assertEquals("Text can not match since they are different values", "I am changed", tester.getUserID());

        }
        // testing for Message class
        @Test(timeout = 1_000)
        public void messageTestClass() {
            // check if Message class exists
            try {
                Class.forName("Message");
            }
            catch(ClassNotFoundException e) {
                System.out.println("Ensure that `Message` exists!");
                return;
            }
            Class<?> messageObject = Message.class;
            // check for correct superclass
            Class<?> superclass = messageObject.getSuperclass();
            assertEquals("Ensure that your `Message` class does NOT extend any other class!", superclass, Object.class);

            // defining fields and constructor
            Field author;
            Field dateTime;
            Field text;
            Method method;
            Class<?> returnType;
            Class<?> expectedReturnType;
            int modifiers;
            try {
                author = messageObject.getField("author");
                dateTime = messageObject.getField("dateTime");
                text = messageObject.getField("text");
            }
            catch(NoSuchFieldException e) {
                System.out.println(e.toString());
                return;
            }
            // check fields of class for correct access modifier and data type
            modifiers = author.getModifiers();
            assertTrue("Ensure that `author` in `Message` class is private!", Modifier.isPrivate(modifiers));
            assertTrue("Ensure that `author` in `Message` class is of type User!", User.class.isAssignableFrom(author.getType()));
            modifiers = dateTime.getModifiers();
            assertTrue("Ensure that `dateTime` in `Message` class is private!", Modifier.isPrivate(modifiers));
            assertTrue("Ensure that `dateTime` in `Message` class is of type LocalDateTime!", LocalDateTime.class.isAssignableFrom(dateTime.getType()));
            modifiers = text.getModifiers();
            assertTrue("Ensure that `text` in `Message` class is private!", Modifier.isPrivate(modifiers));
            assertTrue("Ensure that `text` in `Message` class is of type String!", String.class.isAssignableFrom(text.getType()));

            // check if methods are implemented correctly
            User author1 = new User("Ryan", "Ryan123", "Ryan111@gmail.com", 1300443522, "NotRyan532");
            LocalDateTime dateTime1 = LocalDateTime.now();
            String text1 = "Hello";
            Message tester = new Message(author1, dateTime1, text1);

            try {
                method = messageObject.getDeclaredMethod("getAuthor");
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `Message` declares a method named `getAuthor` that has no parameters!");
                return;
            }
            modifiers = method.getModifiers();
            returnType = method.getReturnType();
            expectedReturnType = User.class;

            Assert.assertTrue("Ensure that `Message`'s `getAuthor` method is `public`", Modifier.isPublic(modifiers));
            Assert.assertEquals("Ensure that `Message`'s `getAuthor` method has the correct return type!", expectedReturnType, returnType);

            // verifying getAuthor

            assertEquals("The user and getting the user must match", author1, tester.getAuthor());

            // verifying failure getAuthor

            assertEquals("The date and the user do not match", dateTime1, tester.getAuthor());

            try {
                method = messageObject.getDeclaredMethod("getDateTime");
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `Message` declares a method named `getDateTime` that has no parameters!");
                return;
            }
            modifiers = method.getModifiers();
            returnType = method.getReturnType();
            expectedReturnType = LocalDateTime.class;

            Assert.assertTrue("Ensure that `Message`'s `getAuthor` method is `public`", Modifier.isPublic(modifiers));
            Assert.assertEquals("Ensure that `Message`'s `getAuthor` method has the correct return type!", expectedReturnType, returnType);

            // verifying getDateTime

            assertEquals("The DateTime and getting the DateTime must match", dateTime1, tester.getDateTime());

            // verifying failure getDateTime

            assertEquals("The user and the date do not match", author1, tester.getDateTime());

            try {
                method = messageObject.getDeclaredMethod("getText");
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `Message` declares a method named `getText` that has no parameters!");
                return;
            }
            modifiers = method.getModifiers();
            returnType = method.getReturnType();
            expectedReturnType = String.class;

            Assert.assertTrue("Ensure that `Message`'s `getAuthor` method is `public`", Modifier.isPublic(modifiers));
            Assert.assertEquals("Ensure that `Message`'s `getAuthor` method has the correct return type!", expectedReturnType, returnType);

            // verifying getText

            assertEquals("The text and getting the text must match", text1, tester.getText());

            // verifying failure getText

            assertEquals("The user and the text do not match", author1, tester.getText());

            try {
                method = messageObject.getDeclaredMethod("setMessage", LocalDateTime.class, String.class);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `Message` declares a method named `setMessage` that has two parameters of types" +
                        "LocalDateTime and String!");
                return;
            }
            modifiers = method.getModifiers();
            returnType = method.getReturnType();

            Assert.assertTrue("Ensure that `Message`'s `setMessage` method is `public`", Modifier.isPublic(modifiers));
            assertNull("Ensure that `Message`'s `setMessage` method has the correct return type!", returnType);

            // verifying setMessage

            tester.setMessage(LocalDateTime.now(), "I am changed");
            assertEquals("Text must match from being changed","I am changed", tester.getText());

            // verifying failure setMessage

            tester.setMessage(LocalDateTime.now(), "Changing again");
            assertEquals("Text can not match since they are different values", "I am changed", tester.getText());
        }
    }
}
 
