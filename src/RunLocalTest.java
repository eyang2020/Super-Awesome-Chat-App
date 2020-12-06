//package src;

import org.junit.Test;
import org.junit.After;

import java.awt.*;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import org.junit.Assert;
import org.junit.Before;


import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.*;

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
        private final InputStream originalSystemin = System.in;
        private ByteArrayOutputStream testOut;

        @SuppressWarnings("FieldCanBeLocal")
        private ByteArrayInputStream testIn;


        @Before
        public void outputStart() {
            testOut = new ByteArrayOutputStream();
            System.setOut(new PrintStream(testOut));
        }

        @After
        public void restoreInputAndOutput() {
            System.setIn(originalSystemin);
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

        /**
         * Testing for user class
         */

        @Test(timeout = 1_000)
        public void userConstructorTest() {
            Class<?> userClass = User.class;
            String className = "User";

            Constructor<?> constructor;
            int modifiers;
            Class<?>[] exceptions;
            int expectedLength = 5;

            try {
                constructor = userClass.getDeclaredConstructor(String.class, String.class, String.class, long.class,
                        String.class);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `" + className + "` declares a constructor that is `public` and has five " +
                        "parameters with type String, String, String, long, and String!");
                return;
            } //end try catch

            modifiers = constructor.getModifiers();

            Assert.assertTrue("Ensure that `" + className + "`'s parameterized constructor is" +
                    " `public`!", Modifier.isPublic(modifiers));
        }

        @Test(timeout = 1_000)
        public void userClassTest() {
            // check if User class exists
            try {
                Class.forName("User");
            } catch (ClassNotFoundException e) {
                System.out.println("Ensure that `User` exists!");
                return;
            }
            Class<?> userObject = User.class;
            // check for correct superclass
            Class<?> superclass = userObject.getSuperclass();
            assertEquals("Ensure that your `User` class does NOT extend any other class!",
                    superclass, Object.class);

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
            } catch (NoSuchFieldException e) {
                System.out.println(e.toString());
                return;
            }
            // check fields of class for correct access modifier and data type
            modifiers = name.getModifiers();
            assertTrue("Ensure that `name` in `User` class is private!", Modifier.isPrivate(modifiers));
            assertTrue("Ensure that `name` in `User` class is of type String!",
                    String.class.isAssignableFrom(name.getType()));
            modifiers = username.getModifiers();
            assertTrue("Ensure that `username` in `User` class is private!", Modifier.isPrivate(modifiers));
            assertTrue("Ensure that `username` in `User` class is of type String!",
                    String.class.isAssignableFrom(username.getType()));
            modifiers = email.getModifiers();
            assertTrue("Ensure that `email` in `User` class is private!", Modifier.isPrivate(modifiers));
            assertTrue("Ensure that `email` in `User` class is of type String!",
                    String.class.isAssignableFrom(email.getType()));
            modifiers = phoneNumber.getModifiers();
            assertTrue("Ensure that `phoneNumber` in `User` class is private!", Modifier.isPrivate(modifiers));
            assertTrue("Ensure that `phoneNumber` in `User` class is of type String!",
                    Long.class.isAssignableFrom(phoneNumber.getType()));
            modifiers = password.getModifiers();
            assertTrue("Ensure that `password` in `User` class is private!",
                    Modifier.isPrivate(modifiers));
            assertTrue("Ensure that `password` in `User` class is of type String!",
                    String.class.isAssignableFrom(password.getType()));
            modifiers = groups.getModifiers();
            assertTrue("Ensure that `groups` in `User` class is private!",
                    Modifier.isPrivate(modifiers));
            assertTrue("Ensure that `groups` in `User` class is of type ArrayList!",
                    ArrayList.class.isAssignableFrom(groups.getType()));

            // check if methods are implemented correctly

            String testName = "Ryan";
            String testUsername = "Ryan123";
            String testEmail = "Ryan111@gmail.com";
            long testPhoneNumber = 1300443522L;
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

            Assert.assertTrue("Ensure that `User`'s `getUsername` method is `public`",
                    Modifier.isPublic(modifiers));
            Assert.assertEquals("Ensure that `User`'s `getUsername` method has the correct return type!",
                    expectedReturnType, returnType);

            // verifying getUsername

            assertEquals("The username and getting the username must match",
                    testUsername, tester.getUsername());

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

            Assert.assertTrue("Ensure that `User`'s `getPassword` method is `public`",
                    Modifier.isPublic(modifiers));
            Assert.assertEquals("Ensure that `User`'s `getPassword` method has the correct return type!",
                    expectedReturnType, returnType);

            // verifying getPassword

            assertEquals("The password and getting the password must match", testPassword,
                    tester.getPassword());

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

            Assert.assertTrue("Ensure that `User`'s `getName` method is `public`",
                    Modifier.isPublic(modifiers));
            Assert.assertEquals("Ensure that `User`'s `getName` method has the correct return type!",
                    expectedReturnType, returnType);

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

            Assert.assertTrue("Ensure that `User`'s `getPhoneNumber` method is `public`",
                    Modifier.isPublic(modifiers));
            Assert.assertEquals("Ensure that `User`'s `getPhoneNumber` method has the correct return type!",
                    expectedReturnType, returnType);

            // verifying getPhoneNumber

            assertEquals("The password and getting the password must match",
                    testPhoneNumber, tester.getPhoneNumber());

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

            Assert.assertTrue("Ensure that `User`'s `getEmail` method is `public`",
                    Modifier.isPublic(modifiers));
            Assert.assertEquals("Ensure that `User`'s `getEmail` method has the correct return type!",
                    expectedReturnType, returnType);

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

            Assert.assertTrue("Ensure that `User`'s `getUserID` method is `public`",
                    Modifier.isPublic(modifiers));
            Assert.assertEquals("Ensure that `User`'s `getUserID` method has the correct return type!",
                    expectedReturnType, returnType);

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

            Assert.assertTrue("Ensure that `User`'s `setUsername` method is `public`",
                    Modifier.isPublic(modifiers));
            assertNull("Ensure that `User`'s `setUsername` method has the correct return type!", returnType);

            // verifying setUsername

            tester.setUsername("I am changed");
            assertEquals("Text must match from being changed", "I am changed", tester.getUsername());

            // verifying failure setUsername

            tester.setUsername("Changing again");
            assertEquals("Text can not match since they are different values", "I am changed",
                    tester.getUsername());

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
            assertEquals("Text must match from being changed", "I am changed", tester.getName());

            // verifying failure setName

            tester.setName("Changing again");
            assertEquals("Text can not match since they are different values", "I am changed",
                    tester.getName());

            try {
                method = userObject.getDeclaredMethod("setEmail", String.class);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `User` declares a method named `setEmail` that has one parameter of type" +
                        "String!");
                return;
            }
            modifiers = method.getModifiers();
            returnType = method.getReturnType();

            Assert.assertTrue("Ensure that `User`'s `setEmail` method is `public`",
                    Modifier.isPublic(modifiers));
            assertNull("Ensure that `User`'s `setEmail` method has the correct return type!", returnType);

            // verifying setEmail

            tester.setEmail("I am changed");
            assertEquals("Text must match from being changed", "I am changed", tester.getEmail());

            // verifying failure setEmail

            tester.setEmail("Changing again");
            assertEquals("Text can not match since they are different values", "I am changed",
                    tester.getEmail());

            try {
                method = userObject.getDeclaredMethod("setPhoneNumber", Long.class);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `User` declares a method named `setPhoneNumber` that has one parameter of type"
                        + "Long!");
                return;
            }
            modifiers = method.getModifiers();
            returnType = method.getReturnType();

            Assert.assertTrue("Ensure that `User`'s `setPhoneNumber` method is `public`",
                    Modifier.isPublic(modifiers));
            assertNull("Ensure that `User`'s `setPhoneNumber` method has the correct return type!", returnType);

            // verifying setPhoneNumber

            tester.setPhoneNumber(4804289202L);
            assertEquals("Phone number must match from being changed", 4804925355L,
                    tester.getPhoneNumber());

            // verifying failure setPhoneNumber

            tester.setPhoneNumber(4804925355L);
            assertEquals("Phone number can not match since they are different values", 4804925355L,
                    tester.getPhoneNumber());

            try {
                method = userObject.getDeclaredMethod("setPassword", String.class);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `User` declares a method named `setPassword` that has one parameter of type" +
                        "String!");
                return;
            }
            modifiers = method.getModifiers();
            returnType = method.getReturnType();

            Assert.assertTrue("Ensure that `User`'s `setPassword` method is `public`",
                    Modifier.isPublic(modifiers));
            assertNull("Ensure that `User`'s `setPassword` method has the correct return type!", returnType);

            // verifying setPassword

            tester.setPassword("I am changed");
            assertEquals("Text must match from being changed", "I am changed", tester.getPassword());

            // verifying failure setPassword

            tester.setPassword("Changing again");
            assertEquals("Text can not match since they are different values", "I am changed",
                    tester.getPassword());

            try {
                method = userObject.getDeclaredMethod("setUsername", Integer.class);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `User` declares a method named `setUserID` that has one parameter of type" +
                        "int!");
                return;
            }
            modifiers = method.getModifiers();
            returnType = method.getReturnType();

            Assert.assertTrue("Ensure that `User`'s `setUserID` method is `public`",
                    Modifier.isPublic(modifiers));
            assertNull("Ensure that `User`'s `setUserID` method has the correct return type!", returnType);

            // verifying setUserID

            tester.setUserID(123456);
            assertEquals("ID number must match from being changed", 123456, tester.getUserID());

            // verifying failure setUserID

            tester.setUserID(654321);
            assertEquals("ID can not match since they are different values",
                    123456, tester.getUserID());

        }

        /**
         * Testing for message class
         */
        @Test(timeout = 1_000)
        public void messageConstructorTest() {
            Class<?> messageClass = Message.class;
            String className = "Message";

            Constructor<?> constructor;
            int modifiers;
            Class<?>[] exceptions;
            int expectedLength = 3;

            try {
                constructor = messageClass.getDeclaredConstructor(User.class, LocalDateTime.class,
                        String.class);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `" + className + "` declares a constructor that is `public` and has three" +
                        "parameters with type User, LocalDateTime, and String!");
                return;
            } //end try catch

            modifiers = constructor.getModifiers();

            Assert.assertTrue("Ensure that `" + className + "`'s parameterized constructor is" +
                    " `public`!", Modifier.isPublic(modifiers));
        }

        @Test(timeout = 1_000)
        public void messageTestClass() {
            // check if Message class exists
            try {
                Class.forName("Message");
            } catch (ClassNotFoundException e) {
                System.out.println("Ensure that `Message` exists!");
                return;
            }
            Class<?> messageObject = Message.class;
            // check for correct superclass
            Class<?> superclass = messageObject.getSuperclass();
            assertEquals("Ensure that your `Message` class does NOT extend any other class!",
                    superclass, Object.class);

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
            } catch (NoSuchFieldException e) {
                System.out.println(e.toString());
                return;
            }
            // check fields of class for correct access modifier and data type
            modifiers = author.getModifiers();
            assertTrue("Ensure that `author` in `Message` class is private!", Modifier.isPrivate(modifiers));
            assertTrue("Ensure that `author` in `Message` class is of type User!",
                    User.class.isAssignableFrom(author.getType()));
            modifiers = dateTime.getModifiers();
            assertTrue("Ensure that `dateTime` in `Message` class is private!",
                    Modifier.isPrivate(modifiers));
            assertTrue("Ensure that `dateTime` in `Message` class is of type LocalDateTime!",
                    LocalDateTime.class.isAssignableFrom(dateTime.getType()));
            modifiers = text.getModifiers();
            assertTrue("Ensure that `text` in `Message` class is private!",
                    Modifier.isPrivate(modifiers));
            assertTrue("Ensure that `text` in `Message` class is of type String!",
                    String.class.isAssignableFrom(text.getType()));

            // check if methods are implemented correctly
            User author1 = new User("Ryan", "Ryan123", "Ryan111@gmail.com",
                    1300443522, "NotRyan532");
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

            Assert.assertTrue("Ensure that `Message`'s `getAuthor` method is `public`",
                    Modifier.isPublic(modifiers));
            Assert.assertEquals("Ensure that `Message`'s `getAuthor` method has the correct return type!",
                    expectedReturnType, returnType);

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

            Assert.assertTrue("Ensure that `Message`'s `getAuthor` method is `public`",
                    Modifier.isPublic(modifiers));
            Assert.assertEquals("Ensure that `Message`'s `getAuthor` method has the correct return type!",
                    expectedReturnType, returnType);

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

            Assert.assertTrue("Ensure that `Message`'s `getAuthor` method is `public`",
                    Modifier.isPublic(modifiers));
            Assert.assertEquals("Ensure that `Message`'s `getAuthor` method has the correct return type!",
                    expectedReturnType, returnType);

            // verifying getText

            assertEquals("The text and getting the text must match", text1, tester.getText());

            // verifying failure getText

            assertEquals("The user and the text do not match", author1, tester.getText());

            try {
                method = messageObject.getDeclaredMethod("setMessage", LocalDateTime.class, String.class);
            } catch (NoSuchMethodException e) {
                Assert.fail(
                        "Ensure that `Message` declares a method named `setMessage` that has two parameters of types" +
                                "LocalDateTime and String!");
                return;
            }
            modifiers = method.getModifiers();
            returnType = method.getReturnType();

            Assert.assertTrue("Ensure that `Message`'s `setMessage` method is `public`",
                    Modifier.isPublic(modifiers));
            assertNull("Ensure that `Message`'s `setMessage` method has the correct return type!", returnType);

            // verifying setMessage

            tester.setMessage(LocalDateTime.now(), "I am changed");
            assertEquals("Text must match from being changed", "I am changed", tester.getText());

            // verifying failure setMessage

            tester.setMessage(LocalDateTime.now(), "Changing again");
            assertEquals("Text can not match since they are different values", "I am changed",
                    tester.getText());
        }


        /**
         * Group class testing
         */
        @Test(timeout = 1_000)
        public void groupConstructorTest() {
            Class<?> groupClass = Group.class;
            String className = "Group";

            Constructor<?> constructor;
            int modifiers;
            Class<?>[] exceptions;
            int expectedLength = 3;

            try {
                constructor = groupClass.getDeclaredConstructor(String.class, ArrayList.class);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `" + className + "` declares a constructor that is `public` and has two" +
                        "parameters with type String and ArrayList!");
                return;
            } //end try catch

            modifiers = constructor.getModifiers();

            Assert.assertTrue("Ensure that `" + className + "`'s parameterized constructor is" +
                    " `public`!", Modifier.isPublic(modifiers));
        }

        @Test(timeout = 1_000)
        public void groupTestClass() {
            // check if Group class exists
            User user = new User();
            try {
                Class.forName("Group");
            } catch (ClassNotFoundException e) {
                System.out.println("Ensure that `Group` exists!");
                return;
            }
            Class<?> groupObject = Group.class;
            // check for correct superclass
            Class<?> superclass = groupObject.getSuperclass();
            assertEquals("Ensure that your `Group` class does NOT extend any other class!",
                    superclass, Object.class);

            // defining fields and constructor
            Field groupName;
            Field users;
            Field messages;
            Method method;
            Class<?> returnType;
            Class<?> expectedReturnType;
            int modifiers;
            try {
                groupName = groupObject.getField("groupName");
                users = groupObject.getField("users");
                messages = groupObject.getField("messages");
            } catch (NoSuchFieldException e) {
                System.out.println(e.toString());
                return;
            }
            // check fields of class for correct access modifier and data type
            modifiers = groupName.getModifiers();
            assertTrue("Ensure that `groupName` in `Group` class is private!", Modifier.isPrivate(modifiers));
            assertTrue("Ensure that `groupName` in `Group` class is of type String!",
                    String.class.isAssignableFrom(groupName.getType()));
            modifiers = users.getModifiers();
            assertTrue("Ensure that `users` in `Group` class is private!", Modifier.isPrivate(modifiers));
            assertTrue("Ensure that `users` in `Group` class is of type ArrayList!",
                    ArrayList.class.isAssignableFrom(users.getType()));
            modifiers = messages.getModifiers();
            assertTrue("Ensure that `messages` in `Group` class is private!", Modifier.isPrivate(modifiers));
            assertTrue("Ensure that `messages` in `Group` class is of type ArrayList!",
                    ArrayList.class.isAssignableFrom(messages.getType()));

            // check if methods are implemented correctly
            User author1 = new User("Ryan", "Ryan123",
                    "Ryan111@gmail.com", 1300443522, "NotRyan532");
            ArrayList<User> users1 = new ArrayList<User>();
            users1.add(author1);
            String testName = "test group";
            Group testGroup = new Group(testName, users1);

            try {
                method = groupObject.getDeclaredMethod("getGroupName");
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `Group` declares a method named `getGroupName` that has no parameters!");
                return;
            }
            modifiers = method.getModifiers();
            returnType = method.getReturnType();
            expectedReturnType = String.class;

            Assert.assertTrue("Ensure that `Group`'s `getGroupName` method is `public`",
                    Modifier.isPublic(modifiers));
            Assert.assertEquals("Ensure that `Group`'s `getGroupName` method has the correct return type!",
                    expectedReturnType, returnType);

            // verifying getGroupName

            assertEquals("The group name and getting the group name must match", testName,
                    testGroup.getGroupName());

            // verifying failure getGroupName

            assertEquals("The test name and the retrieved group name do not match", "another name",
                    testGroup.getGroupName());

            try {
                method = groupObject.getDeclaredMethod("getUsers");
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `Group` declares a method named `getUsers` that has no parameters!");
                return;
            }
            modifiers = method.getModifiers();
            returnType = method.getReturnType();
            expectedReturnType = ArrayList.class;

            Assert.assertTrue("Ensure that `Group`'s `getUsers` method is `public`",
                    Modifier.isPublic(modifiers));
            Assert.assertEquals("Ensure that `Group`'s `getUsers` method has the correct return type!",
                    expectedReturnType, returnType);

            // verifying getUsers

            assertEquals("The returned users and getting the users must match", users1, testGroup.getUsers());

            // verifying failure getUsers

            assertEquals("The group name and the users do not match", testName, testGroup.getUsers());

            try {
                method = groupObject.getDeclaredMethod("getMessages");
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `Group` declares a method named `getMessages` that has no parameters!");
                return;
            }
            modifiers = method.getModifiers();
            returnType = method.getReturnType();
            expectedReturnType = ArrayList.class;

            Assert.assertTrue("Ensure that `Group`'s `getMessages` method is `public`",
                    Modifier.isPublic(modifiers));
            Assert.assertEquals("Ensure that `Group`'s `getMessages` method has the correct return type!",
                    expectedReturnType, returnType);

            // verifying getMessages

            assertEquals("The messages and getting the messages must match",
                    new ArrayList<Message>(), testGroup.getMessages());

            // verifying failure getMessages

            assertEquals("The messages and the messages do not match",
                    new ArrayList<Message>(), testGroup.getMessages());

            try {
                method = groupObject.getDeclaredMethod("setGroupName", String.class);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `Group` declares a method named `setGroupName` that has one parameter of type"
                        + " String!");
                return;
            }
            modifiers = method.getModifiers();
            returnType = method.getReturnType();

            Assert.assertTrue("Ensure that `Group`'s `setGroupName` method is `public`",
                    Modifier.isPublic(modifiers));
            assertNull("Ensure that `Group`'s `setGroupName` method has the correct return type!",
                    returnType);

            // verifying setGroupName

            testGroup.setGroupName("new name");
            assertEquals("Group name must match from being changed", "new name",
                    testGroup.getGroupName());

            // verifying failure setGroupName

            testGroup.setGroupName("another name");
            assertEquals("Group name can not match since they are different values", "another name",
                    testGroup.getGroupName());
            try {
                method = groupObject.getDeclaredMethod("addUser", User.class);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `Group` declares a method named `addUser` that has one parameter of type"
                        + " User!");
                return;
            }
            modifiers = method.getModifiers();
            returnType = method.getReturnType();

            Assert.assertTrue("Ensure that `Group`'s `addUser` method is `public`",
                    Modifier.isPublic(modifiers));
            assertNull("Ensure that `Group`'s `addUser` method has the correct return type!",
                    returnType);

            // verifying addUser

            User testUser = new User("Joy", "Joy123",
                    "Joy111@gmail.com", 4804925355L, "JoysUser");
            testGroup.addUser(testUser);
            assertEquals("Users must have new user", users1.size() == 2,
                    testGroup.getUsers().size() == 2);

            // verifying failure addUser
            User testUser1 = new User("Joy1", "Joy1231",
                    "Joy1111@gmail.com", 4804925255L, "JoysUser1");
            testGroup.addUser(testUser1);
            assertEquals("Users size does not match since one was added", users1.size() == 3,
                    testGroup.getUsers().size() == 3);

            try {
                method = groupObject.getDeclaredMethod("addMessage", Message.class);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `Group` declares a method named `addMessage` that has one parameter of type"
                        + " Message!");
                return;
            }
            modifiers = method.getModifiers();
            returnType = method.getReturnType();

            Assert.assertTrue("Ensure that `Group`'s `addMessage` method is `public`",
                    Modifier.isPublic(modifiers));
            assertNull("Ensure that `Group`'s `addMessage` method has the correct return type!",
                    returnType);

            // verifying addMessage

            LocalDateTime dateTime1 = LocalDateTime.now();
            String text1 = "Hello";
            Message tester = new Message(author1, dateTime1, text1);

            testGroup.addMessage(tester);
            assertEquals("Messages must have new messages", 1,
                    testGroup.getMessages().size());

            // verifying failure addUser
            String text2 = "Hi";
            Message tester1 = new Message(author1, dateTime1, text2);

            testGroup.addMessage(tester1);
            assertEquals("Messages size does not since one was added", 1,
                    testGroup.getMessages().size());

            try {
                method = groupObject.getDeclaredMethod("editMessage", int.class, Message.class);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `Group` declares a method named `editMessage` that has two parameters of types"
                        + " int and Message!");
                return;
            }
            modifiers = method.getModifiers();
            returnType = method.getReturnType();

            Assert.assertTrue("Ensure that `Group`'s `editMessage` method is `public`",
                    Modifier.isPublic(modifiers));
            assertNull("Ensure that `Group`'s `editMessage` method has the correct return type!",
                    returnType);

            // verifying editMessage
            String text3 = "Hello again";
            Message tester3 = new Message(author1, dateTime1, text3);

            testGroup.editMessage(0, tester3);
            assertEquals("Message must be changed", "Hello again",
                    testGroup.getMessages().get(0));

            // verifying failure editMessage
            String text4 = "Again";
            Message tester4 = new Message(author1, dateTime1, text4);

            testGroup.editMessage(0, tester4);
            assertEquals("Messages should not match since it was changed",
                    "Hello again",
                    testGroup.getMessages().get(0));

            try {
                method = groupObject.getDeclaredMethod("deleteMessage", int.class);
            } catch (NoSuchMethodException e) {
                Assert.fail(
                        "Ensure that `Group` declares a method named `deleteMessage` that has one parameters of type"
                                + " int");
                return;
            }
            modifiers = method.getModifiers();
            returnType = method.getReturnType();

            Assert.assertTrue("Ensure that `Group`'s `deleteMessage` method is `public`",
                    Modifier.isPublic(modifiers));
            assertNull("Ensure that `Group`'s `deleteMessage` method has the correct return type!",
                    returnType);

            // verifying deleteMessage
            int deleted = testGroup.getMessages().size() - 1;
            testGroup.deleteMessage(0);
            assertEquals("Message must be deleted", deleted, testGroup.getMessages().size());

            // verifying failure deleteMessage
            testGroup.deleteMessage(0);
            assertEquals("Messages size should not match since it was deleted", deleted,
                    testGroup.getMessages().size());

            try {
                method = groupObject.getDeclaredMethod("removeUser", User.class);
            } catch (NoSuchMethodException e) {
                Assert.fail(
                        "Ensure that `Group` declares a method named `removeUser` that has one parameters of type"
                                + " User");
                return;
            }
            modifiers = method.getModifiers();
            returnType = method.getReturnType();

            Assert.assertTrue("Ensure that `Group`'s `removeUser` method is `public`",
                    Modifier.isPublic(modifiers));
            assertNull("Ensure that `Group`'s `removeUser` method has the correct return type!",
                    returnType);

            // verifying removeUser
            deleted = users1.size() - 1;
            testGroup.removeUser(author1);
            assertEquals("User must be removed", deleted, testGroup.getUsers().size());

            // verifying failure deleteMessage
            testGroup.removeUser(testUser);
            assertEquals("User size should not match since it was deleted", deleted,
                    testGroup.getMessages().size());
        }

        /**
         * Testing for login class
         */
        @Test(timeout = 1_000)
        public void loginConstructorTest() {
            Class<?> loginClass = Login.class;
            String className = "Login";

            Constructor<?> constructor;
            int modifiers;
            Class<?>[] exceptions;
            int expectedLength = 1;

            try {
                constructor = loginClass.getDeclaredConstructor(Client.class);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `" + className + "` declares a constructor that is `public` and has " +
                        "one parameter type Client!");
                return;
            } //end try catch

            modifiers = constructor.getModifiers();

            Assert.assertTrue("Ensure that `" + className + "`'s parameterized constructor is" +
                    " `public`!", Modifier.isPublic(modifiers));
        }

        @Test(timeout = 1_000)
        public void loginTestClass() {
            // check if Login class exists
            try {
                Class.forName("Login");
            } catch (ClassNotFoundException e) {
                System.out.println("Ensure that `Login` exists!");
                return;
            }
            Class<?> loginObject = Login.class;
            // check for correct superclass
            Class<?> superclass = loginObject.getSuperclass();
            assertEquals("Ensure that your `Login` class does NOT extend any other class!",
                    superclass, Object.class);
            // check if fields exist
            Field login;
            Field createUser;
            Field login1;
            Field create;
            Field frame;
            Field panel;
            Field panel1;
            Field panel2;
            Field content;
            Field username;
            Field password;
            Field email;
            Field name;
            Field phoneNumber;
            Field client;
            Field user;
            Field actionListener;
            Method method;
            Class<?> returnType;
            Class<?> expectedReturnType;
            int modifiers;
            try {
                login = loginObject.getField("login");
                createUser = loginObject.getField("createUser");
                login1 = loginObject.getField("login1");
                create = loginObject.getField("create");
                frame = loginObject.getField("frame");
                panel = loginObject.getField("panel");
                panel1 = loginObject.getField("panel1");
                panel2 = loginObject.getField("panel2");
                content = loginObject.getField("content");
                name = loginObject.getField("name");
                username = loginObject.getField("username");
                email = loginObject.getField("email");
                phoneNumber = loginObject.getField("phoneNumber");
                password = loginObject.getField("password");
                client = loginObject.getField("client");
                user = loginObject.getField("user");
                actionListener = loginObject.getField("actionListener");
            } catch (NoSuchFieldException e) {
                System.out.println(e.toString());
                return;
            }
            // check fields of class for correct access modifier and data type
            modifiers = login.getModifiers();
            assertTrue("Ensure that `login` in `Login` class is private!", Modifier.isPrivate(modifiers));
            assertTrue("Ensure that `login` in `Login` class is of type JButton!",
                    JButton.class.isAssignableFrom(login.getType()));
            modifiers = createUser.getModifiers();
            assertTrue("Ensure that `createUser` in `Login` class is private!", Modifier.isPrivate(modifiers));
            assertTrue("Ensure that `createUSer` in `Login` class is of type JButton!",
                    JButton.class.isAssignableFrom(createUser.getType()));
            modifiers = login1.getModifiers();
            assertTrue("Ensure that `login1` in `Login` class is private!", Modifier.isPrivate(modifiers));
            assertTrue("Ensure that `login1` in `Login` class is of type JButton!",
                    JButton.class.isAssignableFrom(login1.getType()));
            modifiers = create.getModifiers();
            assertTrue("Ensure that `create` in `Login` class is private!", Modifier.isPrivate(modifiers));
            assertTrue("Ensure that `create` in `Login` class is of type JButton!",
                    JButton.class.isAssignableFrom(create.getType()));
            modifiers = frame.getModifiers();
            assertTrue("Ensure that `frame` in `Login` class is private!", Modifier.isPrivate(modifiers));
            assertTrue("Ensure that `frame` in `Login` class is of type JFrame!",
                    JFrame.class.isAssignableFrom(frame.getType()));
            modifiers = panel.getModifiers();
            assertTrue("Ensure that `panel` in `Login` class is private!", Modifier.isPrivate(modifiers));
            assertTrue("Ensure that `panel` in `Login` class is of type JPanel!",
                    JPanel.class.isAssignableFrom(panel.getType()));
            modifiers = panel1.getModifiers();
            assertTrue("Ensure that `panel1` in `Login` class is private!", Modifier.isPrivate(modifiers));
            assertTrue("Ensure that `panel1` in `Login` class is of type JPanel!",
                    JPanel.class.isAssignableFrom(panel1.getType()));
            modifiers = panel2.getModifiers();
            assertTrue("Ensure that `panel2` in `Login` class is private!", Modifier.isPrivate(modifiers));
            assertTrue("Ensure that `panel2` in `Login` class is of type JPanel!",
                    JPanel.class.isAssignableFrom(panel2.getType()));
            modifiers = content.getModifiers();
            assertTrue("Ensure that `content` in `Login` class is private!", Modifier.isPrivate(modifiers));
            assertTrue("Ensure that `content` in `Login` class is of type Container!",
                    Container.class.isAssignableFrom(content.getType()));
            modifiers = name.getModifiers();
            assertTrue("Ensure that `name` in `Login` class is private!", Modifier.isPrivate(modifiers));
            assertTrue("Ensure that `name` in `Login` class is of type JTextField!",
                    JTextField.class.isAssignableFrom(name.getType()));
            modifiers = username.getModifiers();
            assertTrue("Ensure that `username` in `Login` class is private!", Modifier.isPrivate(modifiers));
            assertTrue("Ensure that `username` in `Login` class is of type JTextField!",
                    JTextField.class.isAssignableFrom(username.getType()));
            modifiers = email.getModifiers();
            assertTrue("Ensure that `email` in `Login` class is private!", Modifier.isPrivate(modifiers));
            assertTrue("Ensure that `email` in `Login` class is of type JTextField!",
                    JTextField.class.isAssignableFrom(email.getType()));
            modifiers = phoneNumber.getModifiers();
            assertTrue("Ensure that `phoneNumber` in `Login` class is private!", Modifier.isPrivate(modifiers));
            assertTrue("Ensure that `phoneNumber` in `Login` class is of type JTextField!",
                    JTextField.class.isAssignableFrom(phoneNumber.getType()));
            modifiers = password.getModifiers();
            assertTrue("Ensure that `password` in `Login` class is private!", Modifier.isPrivate(modifiers));
            assertTrue("Ensure that `password` in `Login` class is of type JTextField!",
                    JTextField.class.isAssignableFrom(password.getType()));
            modifiers = client.getModifiers();
            assertTrue("Ensure that `client` in `Login` class is private!", Modifier.isPrivate(modifiers));
            assertTrue("Ensure that `client` in `Login` class is of type Client!",
                    Client.class.isAssignableFrom(client.getType()));
            modifiers = user.getModifiers();
            assertTrue("Ensure that `user` in `Login` class is private!", Modifier.isPrivate(modifiers));
            assertTrue("Ensure that `user` in `Login` class is of type User!",
                    User.class.isAssignableFrom(user.getType()));
            assertTrue("Ensure that `actionListener` in `Login` class is of type ActionLister!",
                    ActionListener.class.isAssignableFrom(actionListener.getType()));

            // check if methods are implemented correctly
            try {
                method = loginObject.getDeclaredMethod("run");
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `Login` declares a method named `run` that has no parameters!");
                return;
            }
            modifiers = method.getModifiers();
            returnType = method.getReturnType();
            expectedReturnType = void.class;

            Assert.assertTrue("Ensure that `Login`'s `run` method is `public`",
                    Modifier.isPublic(modifiers));
            Assert.assertEquals("Ensure that `Login`'s `run` method has the correct return type!",
                    expectedReturnType, returnType);

            try {
                method = loginObject.getDeclaredMethod("loginUser", String.class, String.class);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `Login` declares a method named `loginUser` that has two String parameters!");
                return;
            }
            modifiers = method.getModifiers();
            returnType = method.getReturnType();
            expectedReturnType = boolean.class;

            Assert.assertTrue("Ensure that `Login`'s `loginUser` method is `public`",
                    Modifier.isPublic(modifiers));
            Assert.assertEquals("Ensure that `Login`'s `loginUser` method has the correct return type!",
                    expectedReturnType, returnType);

            try {
                method = loginObject.getDeclaredMethod("userCreation", String.class, String.class,
                        String.class, long.class, String.class);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `Login` declares a method named `userCreation` that has five parameters with "
                        + "type String, String, String, long, and String!");
                return;
            }
            modifiers = method.getModifiers();
            returnType = method.getReturnType();
            expectedReturnType = boolean.class;

            Assert.assertTrue("Ensure that `Login`'s `userCreation` method is `public`",
                    Modifier.isPublic(modifiers));
            Assert.assertEquals("Ensure that `Login`'s `userCreation` method has the correct return type!",
                    expectedReturnType, returnType);

            try {
                method = loginObject.getDeclaredMethod("getUser");
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `Login` declares a method named ` that has no parameters");
                return;
            }
            modifiers = method.getModifiers();
            returnType = method.getReturnType();
            expectedReturnType = User.class;

            Assert.assertTrue("Ensure that `Login`'s `getUser` method is `public`",
                    Modifier.isPublic(modifiers));
            Assert.assertEquals("Ensure that `Login`'s `getUser` method has the correct return type!",
                    expectedReturnType, returnType);
        }

        @Test(timeout = 1_000)
        public void manageProfileConstructorTest() {
            Class<?> manageProfileClass = ManageProfile.class;
            String className = "Manage Profile";

            Constructor<?> constructor;
            int modifiers;
            Class<?>[] exceptions;
            int expectedLength = 1;

            try {
                constructor = manageProfileClass.getDeclaredConstructor(Client.class);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `" + className + "` declares a constructor that is `public` and has " +
                        "one parameter type Client!");
                return;
            } //end try catch

            modifiers = constructor.getModifiers();

            Assert.assertTrue("Ensure that `" + className + "`'s parameterized constructor is" +
                    " `public`!", Modifier.isPublic(modifiers));
        }

        @Test(timeout = 1_000)
        public void manageProfileTestClass() {
            // check if Manage Profile class exists
            try {
                Class.forName("ManageProfile");
            } catch (ClassNotFoundException e) {
                System.out.println("Ensure that `ManageProfile` exists!");
                return;
            }
            Class<?> manageProfileObject = ManageProfile.class;
            // check for correct superclass
            Class<?> superclass = manageProfileObject.getSuperclass();
            assertEquals("Ensure that your `ManageProfile` class does NOT extend any other class!",
                    superclass, Object.class);
            // check if fields exist
            Field editUsername;
            Field editPassword;
            Field editEmail;
            Field editName;
            Field editPhoneNumber;
            Field change;
            Field deleteAccount;
            Field newUsername;
            Field newPassword;
            Field newEmail;
            Field newName;
            Field newPhoneNumber;
            Field cUsername;
            Field cPassword;
            Field cEmail;
            Field cName;
            Field cPhoneNumber;
            Field frame;
            Field panel;
            Field content;
            Field selection;
            Field client;
            Field user;
            Field actionListener;
            Method method;
            Class<?> returnType;
            Class<?> expectedReturnType;
            int modifiers;
            try {
                editUsername = manageProfileObject.getField("editUsername");
                editName = manageProfileObject.getField("editName");
                editEmail = manageProfileObject.getField("editEmail");
                editPhoneNumber = manageProfileObject.getField("editPhoneNumber");
                editPassword = manageProfileObject.getField("editPassword");
                change = manageProfileObject.getField("change");
                deleteAccount = manageProfileObject.getField("deleteAccount");
                newName = manageProfileObject.getField("newName");
                newUsername = manageProfileObject.getField("newUsername");
                newEmail = manageProfileObject.getField("newEmail");
                newPhoneNumber = manageProfileObject.getField("newPhoneNumber");
                newPassword = manageProfileObject.getField("newPassword");
                cName = manageProfileObject.getField("cName");
                cUsername = manageProfileObject.getField("cUsername");
                cEmail = manageProfileObject.getField("cEmail");
                cPhoneNumber = manageProfileObject.getField("cPhoneNumber");
                cPassword = manageProfileObject.getField("cPassword");
                selection = manageProfileObject.getField("selection");
                frame = manageProfileObject.getField("frame");
                panel = manageProfileObject.getField("panel");
                content = manageProfileObject.getField("content");
                client = manageProfileObject.getField("client");
                user = manageProfileObject.getField("user");
                actionListener = manageProfileObject.getField("actionListener");
            } catch (NoSuchFieldException e) {
                System.out.println(e.toString());
                return;
            }
            // check fields of class for correct access modifier and data type
            modifiers = editName.getModifiers();
            assertTrue("Ensure that `editName` in `ManageProfile` class is private!",
                    Modifier.isPrivate(modifiers));
            assertTrue("Ensure that `editName` in `ManageProfile` class is of type JButton!",
                    JButton.class.isAssignableFrom(editName.getType()));
            modifiers = editUsername.getModifiers();
            assertTrue("Ensure that `editUsername` in `ManageProfile` class is private!",
                    Modifier.isPrivate(modifiers));
            assertTrue("Ensure that `editUsername` in `ManageProfile` class is of type JButton!",
                    JButton.class.isAssignableFrom(editUsername.getType()));
            modifiers = editEmail.getModifiers();
            assertTrue("Ensure that `editEmail` in `ManageProfile` class is private!",
                    Modifier.isPrivate(modifiers));
            assertTrue("Ensure that `editEmail` in `ManageProfile` class is of type JButton!",
                    JButton.class.isAssignableFrom(editEmail.getType()));
            modifiers = editPassword.getModifiers();
            assertTrue("Ensure that `editPassword` in `ManageProfile` class is private!",
                    Modifier.isPrivate(modifiers));
            assertTrue("Ensure that `editPassword` in `ManageProfile` class is of type JButton!",
                    JButton.class.isAssignableFrom(editPassword.getType()));
            modifiers = editPhoneNumber.getModifiers();
            assertTrue("Ensure that `editPhoneNumber` in `ManageProfile` class is private!",
                    Modifier.isPrivate(modifiers));
            assertTrue("Ensure that `editPhoneNumber` in `ManageProfile` class is of type JButton!",
                    JButton.class.isAssignableFrom(editPhoneNumber.getType()));
            modifiers = change.getModifiers();
            assertTrue("Ensure that `change` in `ManageProfile` class is private!",
                    Modifier.isPrivate(modifiers));
            assertTrue("Ensure that `change` in `ManageProfile` class is of type JButton!",
                    JButton.class.isAssignableFrom(change.getType()));
            modifiers = deleteAccount.getModifiers();
            assertTrue("Ensure that `deleteAccount` in `ManageProfile` class is private!",
                    Modifier.isPrivate(modifiers));
            assertTrue("Ensure that `deleteAccount` in `ManageProfile` class is of type JButton!",
                    JButton.class.isAssignableFrom(deleteAccount.getType()));

            modifiers = newName.getModifiers();
            assertTrue("Ensure that `newName` in `ManageProfile` class is private!",
                    Modifier.isPrivate(modifiers));
            assertTrue("Ensure that `newName` in `ManageProfile` class is of type JTextField!",
                    JTextField.class.isAssignableFrom(newName.getType()));
            modifiers = newUsername.getModifiers();
            assertTrue("Ensure that `newUsername` in `ManageProfile` class is private!",
                    Modifier.isPrivate(modifiers));
            assertTrue("Ensure that `newUsername` in `ManageProfile` class is of type JTextField!",
                    JTextField.class.isAssignableFrom(newUsername.getType()));
            modifiers = newEmail.getModifiers();
            assertTrue("Ensure that `newEmail` in `ManageProfile` class is private!",
                    Modifier.isPrivate(modifiers));
            assertTrue("Ensure that `newEmail` in `ManageProfile` class is of type JTextField!",
                    JTextField.class.isAssignableFrom(newEmail.getType()));
            modifiers = newPhoneNumber.getModifiers();
            assertTrue("Ensure that `newPhoneNumber` in `ManageProfile` class is private!",
                    Modifier.isPrivate(modifiers));
            assertTrue("Ensure that `newPhoneNumber` in `ManageProfile` class is of type JTextField!",
                    JTextField.class.isAssignableFrom(newPhoneNumber.getType()));
            modifiers = newPassword.getModifiers();
            assertTrue("Ensure that `newPassword` in `ManageProfile` class is private!",
                    Modifier.isPrivate(modifiers));
            assertTrue("Ensure that `newPassword` in `ManageProfile` class is of type JTextField!",
                    JTextField.class.isAssignableFrom(newPassword.getType()));

            modifiers = cName.getModifiers();
            assertTrue("Ensure that `cName` in `ManageProfile` class is private!",
                    Modifier.isPrivate(modifiers));
            assertTrue("Ensure that `cName` in `ManageProfile` class is of type JLabel!",
                    JLabel.class.isAssignableFrom(cName.getType()));
            modifiers = cUsername.getModifiers();
            assertTrue("Ensure that `cUsername` in `ManageProfile` class is private!",
                    Modifier.isPrivate(modifiers));
            assertTrue("Ensure that `cUsername` in `ManageProfile` class is of type JLabel!",
                    JLabel.class.isAssignableFrom(cUsername.getType()));
            modifiers = cEmail.getModifiers();
            assertTrue("Ensure that `cEmail` in `ManageProfile` class is private!",
                    Modifier.isPrivate(modifiers));
            assertTrue("Ensure that `cEmail` in `ManageProfile` class is of type JLabel!",
                    JLabel.class.isAssignableFrom(cEmail.getType()));
            modifiers = cPhoneNumber.getModifiers();
            assertTrue("Ensure that `cPhoneNumber` in `ManageProfile` class is private!",
                    Modifier.isPrivate(modifiers));
            assertTrue("Ensure that `cPhoneNumber` in `ManageProfile` class is of type JLabel!",
                    JLabel.class.isAssignableFrom(cPhoneNumber.getType()));
            modifiers = cPassword.getModifiers();
            assertTrue("Ensure that `cPassword` in `ManageProfile` class is private!",
                    Modifier.isPrivate(modifiers));
            assertTrue("Ensure that `cPassword` in `ManageProfile` class is of type JLabel!",
                    JLabel.class.isAssignableFrom(cPassword.getType()));

            modifiers = panel.getModifiers();
            assertTrue("Ensure that `panel` in `ManageProfile` class is private!",
                    Modifier.isPrivate(modifiers));
            assertTrue("Ensure that `panel` in `ManageProfile` class is of type JPanel!",
                    JPanel.class.isAssignableFrom(panel.getType()));
            modifiers = frame.getModifiers();
            assertTrue("Ensure that `frame` in `ManageProfile` class is private!",
                    Modifier.isPrivate(modifiers));
            assertTrue("Ensure that `frame` in `ManageProfile` class is of type JFrame!",
                    JFrame.class.isAssignableFrom(frame.getType()));
            modifiers = content.getModifiers();
            assertTrue("Ensure that `content` in `Login` class is private!", Modifier.isPrivate(modifiers));
            assertTrue("Ensure that `content` in `Login` class is of type Container!",
                    Container.class.isAssignableFrom(content.getType()));

            modifiers = client.getModifiers();
            assertTrue("Ensure that `client` in `ManageProfile` class is private!",
                    Modifier.isPrivate(modifiers));
            assertTrue("Ensure that `client` in `ManageProfile` class is of type Client!",
                    Client.class.isAssignableFrom(client.getType()));
            modifiers = user.getModifiers();
            assertTrue("Ensure that `user` in `ManageProfile` class is private!",
                    Modifier.isPrivate(modifiers));
            assertTrue("Ensure that `user` in `ManageProfile` class is of type User!",
                    User.class.isAssignableFrom(user.getType()));
            modifiers = selection.getModifiers();
            assertTrue("Ensure that `selection` in `ManageProfile` class is private!",
                    Modifier.isPrivate(modifiers));
            assertTrue("Ensure that `selection` in `ManageProfile` class is of type int!",
                    int.class.isAssignableFrom(selection.getType()));
            assertTrue("Ensure that `actionListener` in `ManageProfile` class is of type ActionLister!",
                    ActionListener.class.isAssignableFrom(actionListener.getType()));

            // check if methods are implemented correctly
            try {
                method = manageProfileObject.getDeclaredMethod("run");
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `ManageProfile` declares a method named `run` that has no parameters!");
                return;
            }
            modifiers = method.getModifiers();
            returnType = method.getReturnType();
            expectedReturnType = void.class;

            Assert.assertTrue("Ensure that `ManageProfile`'s `run` method is `public`",
                    Modifier.isPublic(modifiers));
            Assert.assertEquals("Ensure that `ManageProfile`'s `run` method has the correct return type!",
                    expectedReturnType, returnType);
        }

        /**
         * Testing for ServerThread class
         */
        @Test(timeout = 1_000)
        public void serverThreadConstructorTest() {
            Class<?> serverThreadClass = ServerThread.class;
            String className = "ServerThread";

            Constructor<?> constructor;
            int modifiers;
            Class<?>[] exceptions;
            int expectedLength = 1;

            try {
                constructor = serverThreadClass.getDeclaredConstructor(Socket.class);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `" + className + "` declares a constructor that is `public` and has " +
                        "one parameter type Socket!");
                return;
            } //end try catch

            modifiers = constructor.getModifiers();

            Assert.assertTrue("Ensure that `" + className + "`'s parameterized constructor is" +
                    " `public`!", Modifier.isPublic(modifiers));
        }

        @Test(timeout = 1_000)
        public void serverConstructorTest() {
            Class<?> serverClass = Server.class;
            Constructor<?> constructor;
            int modifiers;
            try {
                constructor = serverClass.getDeclaredConstructor();
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `Server` declares a constructor that is `public` and has no parameters");
                return;
            } //end try catch

            modifiers = constructor.getModifiers();

            Assert.assertTrue("Ensure that `Server`'s parameterized constructor is" +
                    " `public`!", Modifier.isPublic(modifiers));
        }

        @Test(timeout = 1_000)
        public void serverThreadTestClass() {
            // check if ServerThread class exists
            try {
                Class.forName("ServerThread");
            } catch (ClassNotFoundException e) {
                System.out.println("Ensure that `ServerThread` exists!");
                return;
            }
            Class<?> serverThreadObject = ServerThread.class;
            // check for correct superclass
            Class<?> superclass = serverThreadObject.getSuperclass();
            assertEquals("Ensure that your `ServerThread` class does NOT extend any other class!",
                    superclass, Object.class);
            // defining fields and constructor
            Field socket;
            Method method;
            Class<?> returnType;
            Class<?> expectedReturnType;
            int modifiers;
            try {
                socket = serverThreadObject.getField("socket");
            } catch (NoSuchFieldException e) {
                System.out.println(e.toString());
                return;
            }
            // check fields of class for correct access modifier and data type
            modifiers = socket.getModifiers();
            assertTrue("Ensure that `socket` in `ServerThread` class is private!",
                    Modifier.isPrivate(modifiers));
            assertTrue("Ensure that `socket` in `ServerThread` class is of type Socket!",
                    Socket.class.isAssignableFrom(socket.getType()));
            // verify methods of ServerThread class
            try {
                method = serverThreadObject.getDeclaredMethod("run");
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `ServerThread` declares a method " +
                        "named `run` that has no parameters!");
                return;
            }
            modifiers = method.getModifiers();
            returnType = method.getReturnType();

            Assert.assertTrue("Ensure that `ServerThread`'s `run` method is `public`",
                    Modifier.isPublic(modifiers));
            assertNull("Ensure that `ServerThread`'s `run` method has the correct return type!",
                    returnType);   
            /* run method was tested using manual testing when we tested networking and
            gui interactions. The ServerThread class in particular was tested by
            ensuring that each client was indeed dedicated a socket to operate on. */
        }

        @Test(timeout = 1_000)
        public void serverTestClass() {
            // check if Server class exists
            try {
                Class.forName("Server");
            } catch (ClassNotFoundException e) {
                System.out.println("Ensure that `Server` exists!");
                return;
            }
            Class<?> serverObject = Server.class;
            // check for correct superclass
            Class<?> superclass = serverObject.getSuperclass();
            assertEquals("Ensure that your `Server` class does NOT extend any other class!",
                    superclass, Object.class);
            // check if fields exist
            Field groups;
            Field users;
            Field port;
            Field serverSocket;
            Field userIDCounter;
            int modifiers;
            Method method;
            Class<?> returnType;
            Class<?> expectedReturnType;
            try {
                groups = serverObject.getField("groups");
                users = serverObject.getField("users");
                port = serverObject.getField("port");
                serverSocket = serverObject.getField("serverSocket");
                userIDCounter = serverObject.getField("userIDCounter");
            } catch (NoSuchFieldException e) {
                System.out.println(e.toString());
                return;
            }
            // check fields of class for correct access modifier and data type
            modifiers = groups.getModifiers();
            assertTrue("Ensure that `groups` in `Server` class is private!", Modifier.isPrivate(modifiers));
            assertTrue("Ensure that `groups` in `Server` class is of type ArrayList!",
                    ArrayList.class.isAssignableFrom(groups.getType()));
            modifiers = users.getModifiers();
            assertTrue("Ensure that `users` in `Server` class is private!", Modifier.isPrivate(modifiers));
            assertTrue("Ensure that `users` in `Server` class is of type ArrayList!",
                    ArrayList.class.isAssignableFrom(users.getType()));
            modifiers = port.getModifiers();
            assertTrue("Ensure that `port` in `Server` class is private!", Modifier.isPrivate(modifiers));
            assertTrue("Ensure that `port` in `Server` class is of type int!",
                    int.class.isAssignableFrom(port.getType()));
            modifiers = serverSocket.getModifiers();
            assertTrue("Ensure that `serverSocket` in `Server` class is protected!",
                    Modifier.isProtected(modifiers));
            assertTrue("Ensure that `serverSocket` in `Server` class is of type ServerSocket!",
                    ServerSocket.class.isAssignableFrom(serverSocket.getType()));
            modifiers = userIDCounter.getModifiers();
            assertTrue("Ensure that `userIDCounter` in `Server` class is private!",
                    Modifier.isPrivate(modifiers));
            assertTrue("Ensure that `userIDCounter` in `Server` class is of type int!",
                    int.class.isAssignableFrom(userIDCounter.getType()));
            // verify methods of Server class
            try {
                method = serverObject.getDeclaredMethod("acceptor");
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `Server` declares a method " +
                        "named `acceptor` that has no parameters!");
                return;
            }
            modifiers = method.getModifiers();
            returnType = method.getReturnType();
            Assert.assertTrue("Ensure that `Server`'s `acceptor` method is `public`",
                    Modifier.isPublic(modifiers));
            assertNull("Ensure that `Server`'s `acceptor` method has the correct return type!",
                    returnType);
            try {
                method = serverObject.getDeclaredMethod("readInUsersAndGroups", String.class, String.class);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `Server` declares a method " +
                        "named `readInUsersAndGroups` that takes two parameters of type String!");
                return;
            }
            modifiers = method.getModifiers();
            returnType = method.getReturnType();
            Assert.assertTrue("Ensure that `Server`'s `readInUsersAndGroups` method is `public`",
                    Modifier.isPublic(modifiers));
            assertNull("Ensure that `Server`'s `readInUsersAndGroups` method has the correct return type!",
                    returnType);
            try {
                method = serverObject.getDeclaredMethod("writeUsersAndGroups", String.class, String.class);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `Server` declares a method " +
                        "named `writeUsersAndGroups` that takes two parameters of type String!");
                return;
            }
            modifiers = method.getModifiers();
            returnType = method.getReturnType();
            Assert.assertTrue("Ensure that `Server`'s `writeUsersAndGroups` method is `public`",
                    Modifier.isPublic(modifiers));
            assertNull("Ensure that `Server`'s `writeUsersAndGroups` method has the correct return type!",
                    returnType);
            try {
                method = serverObject.getDeclaredMethod("getUsers");
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `Server` declares a method " +
                        "named `getUsers` that has no parameters!");
                return;
            }
            modifiers = method.getModifiers();
            returnType = method.getReturnType();
            expectedReturnType = ArrayList.class;
            Assert.assertTrue("Ensure that `Server`'s `getUsers` method is `public`",
                    Modifier.isPublic(modifiers));
            Assert.assertEquals("Ensure that `Server`'s `getUsers` method has the correct return type!",
                    expectedReturnType, returnType);
            try {
                method = serverObject.getDeclaredMethod("getGroups");
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `Server` declares a method " +
                        "named `getGroups` that has no parameters!");
                return;
            }
            modifiers = method.getModifiers();
            returnType = method.getReturnType();
            expectedReturnType = ArrayList.class;
            Assert.assertTrue("Ensure that `Server`'s `getUsers` method is `public`",
                    Modifier.isPublic(modifiers));
            Assert.assertEquals("Ensure that `Server`'s `getUsers` method has the correct return type!",
                    expectedReturnType, returnType);
            try {
                method = serverObject.getDeclaredMethod("getUserIDCounter");
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `Server` declares a method " +
                        "named `getGroups` that has no parameters!");
                return;
            }
            modifiers = method.getModifiers();
            returnType = method.getReturnType();
            expectedReturnType = int.class;
            Assert.assertTrue("Ensure that `Server`'s `getUserIDCounter` method is `public`",
                    Modifier.isPublic(modifiers));
            Assert.assertEquals("Ensure that `Server`'s `getUserIDCounter` method has the correct return type!",
                    expectedReturnType, returnType);
        }

        /**
         * Testing for ChatDriver class
         */
        @Test(timeout = 1_000)
        public void chatDriverConstructorTest() {
            Class<?> chatDriverClass = ChatDriver.class;
            String className = "ChatDriver";

            Constructor<?> constructor;
            int modifiers;
            Class<?>[] exceptions;
            int expectedLength = 1;

            try {
                constructor = chatDriverClass.getDeclaredConstructor(Client.class);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `" + className + "` declares a constructor that is `public` and has " +
                        "one parameter type Client!");
                return;
            } //end try catch

            modifiers = constructor.getModifiers();

            Assert.assertTrue("Ensure that `" + className + "`'s parameterized constructor is" +
                    " `public`!", Modifier.isPublic(modifiers));
        }

        @Test(timeout = 1_000)
        public void chatDriverTestClass() {
            // check if ChatDriver class exists
            try {
                Class.forName("ChatDriver");
            } catch (ClassNotFoundException e) {
                System.out.println("Ensure that `ChatDriver` exists!");
                return;
            }
            Class<?> chatDriverObject = ChatDriver.class;
            // check for correct superclass
            Class<?> superclass = chatDriverObject.getSuperclass();
            assertEquals("Ensure that your `ChatDriver` extends class JComponent!",
                    superclass, JComponent.class);
            // defining fields and constructor
            Field messageTextField;
            Field sendMessageButton;
            Field userSettingsButton;
            Field createGroupButton;
            Field editMessageButton;
            Field deleteMessageButton;
            Field editDeleteListener;
            Field clientUser;
            Field client;
            Field currentGroup;
            Method method;
            Class<?> returnType;
            Class<?> expectedReturnType;
            int modifiers;
            try {
                messageTextField = chatDriverObject.getField("messageTextField");
                sendMessageButton = chatDriverObject.getField("sendMessageButton");
                userSettingsButton = chatDriverObject.getField("userSettingsButton");
                createGroupButton = chatDriverObject.getField("createGroupButton");
                editMessageButton = chatDriverObject.getField("editMessageButton");
                deleteMessageButton = chatDriverObject.getField("deleteMessageButton");
                editDeleteListener = chatDriverObject.getField("editDeleteListener");
                clientUser = chatDriverObject.getField("clientUser");
                client = chatDriverObject.getField("client");
                currentGroup = chatDriverObject.getField("currentGroup");
            } catch (NoSuchFieldException e) {
                System.out.println(e.toString());
                return;
            }
            // check fields of class for correct access modifier and data type
            modifiers = messageTextField.getModifiers();
            assertTrue("Ensure that `messageTextField` in `ChatDriver` class is package-private!",
                    Modifier.isProtected(modifiers));
            assertTrue("Ensure that `messageTextField` in `ChatDriver` class is of type JTextField!",
                    JTextField.class.isAssignableFrom(messageTextField.getType()));
            modifiers = sendMessageButton.getModifiers();
            assertTrue("Ensure that `sendMessageButton` in `ChatDriver` class is package-private!",
                    Modifier.isProtected(modifiers));
            assertTrue("Ensure that `sendMessageButton` in `ChatDriver` class is of type JButton!",
                    JButton.class.isAssignableFrom(messageTextField.getType()));
            modifiers = userSettingsButton.getModifiers();
            assertTrue("Ensure that `userSettingsButton` in `ChatDriver` class is package-private!",
                    Modifier.isProtected(modifiers));
            assertTrue("Ensure that `userSettingsButton` in `ChatDriver` class is of type JButton!",
                    JButton.class.isAssignableFrom(userSettingsButton.getType()));
            modifiers = createGroupButton.getModifiers();
            assertTrue("Ensure that `createGroupButton` in `ChatDriver` class is package-private!",
                    Modifier.isProtected(modifiers));
            assertTrue("Ensure that `createGroupButton` in `ChatDriver` class is of type JButton!",
                    JButton.class.isAssignableFrom(createGroupButton.getType()));
            modifiers = editMessageButton.getModifiers();
            assertTrue("Ensure that `editMessageButton` in `ChatDriver` class is package-private!",
                    Modifier.isProtected(modifiers));
            assertTrue("Ensure that `editMessageButton` in `ChatDriver` class is of type JButton!",
                    JButton.class.isAssignableFrom(editMessageButton.getType()));
            modifiers = deleteMessageButton.getModifiers();
            assertTrue("Ensure that `deleteMessageButton` in `ChatDriver` class is package-private!",
                    Modifier.isProtected(modifiers));
            assertTrue("Ensure that `deleteMessageButton` in `ChatDriver` class is of type JButton!",
                    JButton.class.isAssignableFrom(deleteMessageButton.getType()));
            modifiers = editDeleteListener.getModifiers();
            assertTrue("Ensure that `editDeleteListener` in `ChatDriver` class is package-private!",
                    Modifier.isProtected(modifiers));
            assertTrue("Ensure that `editDeleteListener` in `ChatDriver` class is of type ActionListener!",
                    ActionListener.class.isAssignableFrom(editDeleteListener.getType()));
            modifiers = clientUser.getModifiers();
            assertTrue("Ensure that `clientUser` in `ChatDriver` class is private!",
                    Modifier.isPrivate(modifiers));
            assertTrue("Ensure that `clientUser` in `ChatDriver` class is of type User!",
                    User.class.isAssignableFrom(clientUser.getType()));
            modifiers = client.getModifiers();
            assertTrue("Ensure that `client` in `ChatDriver` class is private!",
                    Modifier.isPrivate(modifiers));
            assertTrue("Ensure that `client` in `ChatDriver` class is of type Client!",
                    Client.class.isAssignableFrom(client.getType()));
            modifiers = currentGroup.getModifiers();
            assertTrue("Ensure that `currentGroup` in `ChatDriver` class is private!",
                    Modifier.isPrivate(modifiers));
            assertTrue("Ensure that `currentGroup` in `ChatDriver` class is of type Group!",
                    Group.class.isAssignableFrom(currentGroup.getType()));
            // verify methods of ChatDriver class

            // run method
            try {
                method = chatDriverObject.getDeclaredMethod("run");
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `ChatDriver` declares a method " +
                        "named `run` that has no parameters!");
                return;
            }
            modifiers = method.getModifiers();
            returnType = method.getReturnType();

            Assert.assertTrue("Ensure that `ChatDriver`'s `run` method is `public`",
                    Modifier.isPublic(modifiers));
            assertNull("Ensure that `ChatDriver`'s `run` method has the correct return type!",
                    returnType);   
            /* run method was tested using manual testing when we tested networking and
            gui interactions. The ChatDriver class in particular was tested by
            ensuring that each client's chats were initiated and handled by ChatDriver. */

            // changeChatModel method
            try {
                method = chatDriverObject.getDeclaredMethod("changeChatModel", int.class);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `ChatDriver` declares a method " +
                        "named `changeChatModel` that has a parameter of type int!");
                return;
            }
            modifiers = method.getModifiers();
            returnType = method.getReturnType();

            Assert.assertTrue("Ensure that `ChatDriver`'s `changeChatModel` method is `public`",
                    Modifier.isPublic(modifiers));
            assertNull("Ensure that `ChatDriver`'s `changeChatModel` method has the correct return type!",
                    returnType);

            // changeUserModel method
            try {
                method = chatDriverObject.getDeclaredMethod("changeUserModel");
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `ChatDriver` declares a method " +
                        "named `changeUserModel` that nas no parameters!");
                return;
            }
            modifiers = method.getModifiers();
            returnType = method.getReturnType();

            Assert.assertTrue("Ensure that `ChatDriver`'s `changeUserModel` method is `public`",
                    Modifier.isPublic(modifiers));
            assertNull("Ensure that `ChatDriver`'s `changeUserModel` method has the correct return type!",
                    returnType);

            // changeGroupModel method
            try {
                method = chatDriverObject.getDeclaredMethod("changeGroupModel");
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `ChatDriver` declares a method " +
                        "named `changeGroupModel` that has no parameters!");
                return;
            }
            modifiers = method.getModifiers();
            returnType = method.getReturnType();

            Assert.assertTrue("Ensure that `ChatDriver`'s `changeGroupModel` method is `public`",
                    Modifier.isPublic(modifiers));
            assertNull("Ensure that `ChatDriver`'s `changeGroupModel` method has the correct return type!",
                    returnType);

            // sendMessageToServer method            
            try {
                method = chatDriverObject.getDeclaredMethod("sendMessageToServer", String.class);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `ChatDriver` declares a method " +
                        "named `sendMessageToServer` that has a parameter of type String!");
                return;
            }
            modifiers = method.getModifiers();
            returnType = method.getReturnType();

            Assert.assertTrue("Ensure that `ChatDriver`'s `sendMessageToServer` method is `public`",
                    Modifier.isPublic(modifiers));
            assertNull("Ensure that `ChatDriver`'s `sendMessageToServer` method has the correct return type!",
                    returnType);
        }

        /**
         * Testing for ClientDriver
         */
        @Test(timeout = 1_000)
        public void clientDriverTestClass() {
            // check if Client Driver class exists
            try {
                Class.forName("ClientDriver");
            } catch (ClassNotFoundException e) {
                System.out.println("Ensure that `ClientDriver` exists!");
                return;
            }
            Class<?> clientDriverObject = ManageProfile.class;
            // check for correct superclass
            Class<?> superclass = clientDriverObject.getSuperclass();
            assertEquals("Ensure that your `ClientDriver` class does NOT extend any other class!",
                    superclass, Object.class);
            //check for methods
            Method method;
            try {
                method = clientDriverObject.getDeclaredMethod("main", String[].class);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `ClientMethod` declares a method " +
                        "named `main` that has a parameter of type String[]!");
                return;
            }
            Class<?> returnType;
            Class<?> expectedReturnType;
            int modifiers;
            modifiers = method.getModifiers();
            returnType = method.getReturnType();

            Assert.assertTrue("Ensure that `ClientDriver`'s `main` method is `public`",
                    Modifier.isPublic(modifiers));
            Assert.assertEquals("Ensure that `ClientDriver`'s `main` method has the correct return type!",
                    void.class, returnType);
        }
        @Test(timeout = 1_000)
        public void clientConstructorTest() {
            Class<?> clientClass = Client.class;
            Constructor<?> constructor;
            int modifiers;
            try {
                constructor = clientClass.getDeclaredConstructor(String.class, int.class);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `Server` declares a constructor that is `public` takes two parameters of" +
                        "types String and int!");
                return;
            } //end try catch

            modifiers = constructor.getModifiers();

            Assert.assertTrue("Ensure that `Server`'s parameterized constructor is" +
                    " `public`!", Modifier.isPublic(modifiers));
        }
        @Test(timeout = 1_000)
        public void clientTest() {
            // check if Client class exists
            try {
                Class.forName("Client");
            } catch (ClassNotFoundException e) {
                System.out.println("Ensure that `Client` exists!");
                return;
            }
            Class<?> clientObject = Client.class;
            // check for correct superclass
            Class<?> superclass = clientObject.getSuperclass();
            assertEquals("Ensure that your `Client` class does NOT extend any other class!",
                    superclass, Object.class);
            // check if fields exist
            Field port;
            Field host;
            Field socket;
            Field out;
            Field in;
            Field currentUser;
            Field users;
            Field groups;
            Field chatDriver;
            int modifiers;
            Method method;
            Class<?> returnType;
            Class<?> expectedReturnType;
            try {
                port = clientObject.getField("port");
                host = clientObject.getField("host");
                socket = clientObject.getField("socket");
                out = clientObject.getField("out");
                in = clientObject.getField("in");
                currentUser = clientObject.getField("currentUser");
                users = clientObject.getField("users");
                groups = clientObject.getField("groups");
                chatDriver = clientObject.getField("chatDriver");
            } catch (NoSuchFieldException e) {
                System.out.println(e.toString());
                return;
            }
            // check fields of class for correct access modifier and data type
            modifiers = port.getModifiers();
            assertTrue("Ensure that `port` in `Client` class is private!", Modifier.isPrivate(modifiers));
            assertTrue("Ensure that `port` in `Client` class is of type int!",
                    int.class.isAssignableFrom(port.getType()));
            modifiers = host.getModifiers();
            assertTrue("Ensure that `host` in `Client` class is private!", Modifier.isPrivate(modifiers));
            assertTrue("Ensure that `host` in `Client` class is of type String!",
                    String.class.isAssignableFrom(host.getType()));
            modifiers = socket.getModifiers();
            assertTrue("Ensure that `socket` in `Client` class is private!", Modifier.isPrivate(modifiers));
            assertTrue("Ensure that `socket` in `Client` class is of type Socket!",
                    Socket.class.isAssignableFrom(socket.getType()));
            modifiers = out.getModifiers();
            assertTrue("Ensure that `out` in `Client` class is private!", Modifier.isPrivate(modifiers));
            assertTrue("Ensure that `out` in `Client` class is of type ObjectOutputStream!",
                    ObjectOutputStream.class.isAssignableFrom(out.getType()));
            modifiers = in.getModifiers();
            assertTrue("Ensure that `in` in `Client` class is private!", Modifier.isPrivate(modifiers));
            assertTrue("Ensure that `in` in `Client` class is of type ObjectInputStream!",
                    ObjectInputStream.class.isAssignableFrom(in.getType()));
            modifiers = currentUser.getModifiers();
            assertTrue("Ensure that `currentUser` in `Client` class is private!",
                    Modifier.isPrivate(modifiers));
            assertTrue("Ensure that `currentUser` in `Client` class is of type User!",
                    User.class.isAssignableFrom(currentUser.getType()));
            modifiers = users.getModifiers();
            assertTrue("Ensure that `users` in `Client` class is private!", Modifier.isPrivate(modifiers));
            assertTrue("Ensure that `users` in `Client` class is of type ArrayList!",
                    ArrayList.class.isAssignableFrom(users.getType()));
            modifiers = groups.getModifiers();
            assertTrue("Ensure that `groups` in `Client` class is private!", Modifier.isPrivate(modifiers));
            assertTrue("Ensure that `groups` in `Client` class is of type ArrayList!",
                    ArrayList.class.isAssignableFrom(groups.getType()));
            modifiers = chatDriver.getModifiers();
            assertTrue("Ensure that `chatDriver` in `Client` class is private!", Modifier.isPrivate(modifiers));
            assertTrue("Ensure that `chatDriver` in `Client` class is of type ChatDriver!",
                    ChatDriver.class.isAssignableFrom(chatDriver.getType()));
            // verify methods of Client class
            try {
                method = clientObject.getDeclaredMethod("connectToServer");
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `Client` declares a method " +
                        "named `connectToServer` that has no parameters!");
                return;
            }
            modifiers = method.getModifiers();
            returnType = method.getReturnType();
            Assert.assertTrue("Ensure that `Client`'s `connectToServer` method is `public`",
                    Modifier.isPublic(modifiers));
            assertNull("Ensure that `Client`'s `connectToServer` method has the correct return type!",
                    returnType);
            try {
                method = clientObject.getDeclaredMethod("createAccount", String.class, String.class,
                        String.class, String.class, long.class);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `Client` declares a method " +
                        "named `createAccount` that has 5 parameters of types String, String, String, String, Long!");
                return;
            }
            modifiers = method.getModifiers();
            returnType = method.getReturnType();
            expectedReturnType = User.class;
            Assert.assertTrue("Ensure that `Client`'s `createAccount` method is `public`",
                    Modifier.isPublic(modifiers));
            Assert.assertEquals("Ensure that `Client`'s `createAccount` method has the correct return type!",
                    expectedReturnType, returnType);
            try {
                method = clientObject.getDeclaredMethod("login", String.class, String.class);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `Client` declares a method " +
                        "named `login` that has 2 parameters of types String, String!");
                return;
            }
            modifiers = method.getModifiers();
            returnType = method.getReturnType();
            expectedReturnType = boolean.class;
            Assert.assertTrue("Ensure that `Client`'s `login` method is `public`",
                    Modifier.isPublic(modifiers));
            Assert.assertEquals("Ensure that `Client`'s `login` method has the correct return type!",
                    expectedReturnType, returnType);
            try {
                method = clientObject.getDeclaredMethod("createGroup", String.class, String[].class);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `Client` declares a method " +
                        "named `createGroup` that has 2 parameters of types String, String[]!");
                return;
            }
            modifiers = method.getModifiers();
            returnType = method.getReturnType();
            expectedReturnType = boolean.class;
            Assert.assertTrue("Ensure that `Client`'s `createGroup` method is `public`",
                    Modifier.isPublic(modifiers));
            Assert.assertEquals("Ensure that `Client`'s `createGroup` method has the correct return type!",
                    expectedReturnType, returnType);
            try {
                method = clientObject.getDeclaredMethod("addMessage", Message.class, Group.class);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `Client` declares a method " +
                        "named `addMessage` that has 2 parameters of types Message, Group!");
                return;
            }
            modifiers = method.getModifiers();
            returnType = method.getReturnType();
            expectedReturnType = boolean.class;
            Assert.assertTrue("Ensure that `Client`'s `addMessage` method is `public`",
                    Modifier.isPublic(modifiers));
            Assert.assertEquals("Ensure that `Client`'s `addMessage` method has the correct return type!",
                    expectedReturnType, returnType);
            try {
                method = clientObject.getDeclaredMethod("refreshUsersAndGroups");
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `Client` declares a method " +
                        "named `refreshUsersAndGroups` that has no parameters!");
                return;
            }
            modifiers = method.getModifiers();
            returnType = method.getReturnType();
            Assert.assertTrue("Ensure that `Client`'s `refreshUsersAndGroups` method is `public`",
                    Modifier.isPublic(modifiers));
            assertNull("Ensure that `Client`'s `refreshUsersAndGroups` method has the correct return type!",
                    returnType);
            try {
                method = clientObject.getDeclaredMethod("updateServerUser", User.class);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `Client` declares a method " +
                        "named `updateServerUser` that has 1 parameter of type User!");
                return;
            }
            modifiers = method.getModifiers();
            returnType = method.getReturnType();
            Assert.assertTrue("Ensure that `Client`'s `updateServerUser` method is `public`",
                    Modifier.isPublic(modifiers));
            assertNull("Ensure that `Client`'s `updateServerUser` method has the correct return type!",
                    returnType);
            try {
                method = clientObject.getDeclaredMethod("getCurrentUser");
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `Client` declares a method " +
                        "named `getCurrentUser` that has no parameters!");
                return;
            }
            modifiers = method.getModifiers();
            returnType = method.getReturnType();
            expectedReturnType = User.class;
            Assert.assertTrue("Ensure that `Client`'s `getCurrentUser` method is `public`",
                    Modifier.isPublic(modifiers));
            Assert.assertEquals("Ensure that `Client`'s `getCurrentUser` method has the correct return type!",
                    expectedReturnType, returnType);
            try {
                method = clientObject.getDeclaredMethod("getUsers");
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `Client` declares a method " +
                        "named `getUsers` that has no parameters!");
                return;
            }
            modifiers = method.getModifiers();
            returnType = method.getReturnType();
            expectedReturnType = ArrayList.class;
            Assert.assertTrue("Ensure that `Client`'s `getUsers` method is `public`",
                    Modifier.isPublic(modifiers));
            Assert.assertEquals("Ensure that `Client`'s `getUsers` method has the correct return type!",
                    expectedReturnType, returnType);
            try {
                method = clientObject.getDeclaredMethod("getGroups");
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `Client` declares a method " +
                        "named `getGroups` that has no parameters!");
                return;
            }
            modifiers = method.getModifiers();
            returnType = method.getReturnType();
            expectedReturnType = ArrayList.class;
            Assert.assertTrue("Ensure that `Client`'s `getGroups` method is `public`",
                    Modifier.isPublic(modifiers));
            Assert.assertEquals("Ensure that `Client`'s `getGroups` method has the correct return type!",
                    expectedReturnType, returnType);
            try {
                method = clientObject.getDeclaredMethod("updateClientUser");
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `Client` declares a method " +
                        "named `updateClientUser` that has no parameters!");
                return;
            }
            modifiers = method.getModifiers();
            returnType = method.getReturnType();
            Assert.assertTrue("Ensure that `Client`'s `updateClientUser` method is `public`",
                    Modifier.isPublic(modifiers));
            assertNull("Ensure that `Client`'s `updateClientUser` method has the correct return type!",
                    returnType);
            try {
                method = clientObject.getDeclaredMethod("editMessage", Message.class, String.class,
                        Group.class, boolean.class);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `Client` declares a method " +
                        "named `editMessage` that has 4 parameters of types Message, String, Group, boolean!");
                return;
            }
            modifiers = method.getModifiers();
            returnType = method.getReturnType();
            Assert.assertTrue("Ensure that `Client`'s `editMessage` method is `public`",
                    Modifier.isPublic(modifiers));
            assertNull("Ensure that `Client`'s `editMessage` method has the correct return type!",
                    returnType);
            try {
                method = clientObject.getDeclaredMethod("setChatDriver", ChatDriver.class);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `Client` declares a method " +
                        "named `setChatDriver` that has 1 parameter of type ChatDriver!");
                return;
            }
            modifiers = method.getModifiers();
            returnType = method.getReturnType();
            Assert.assertTrue("Ensure that `Client`'s `setChatDriver` method is `public`",
                    Modifier.isPublic(modifiers));
            assertNull("Ensure that `Client`'s `setChatDriver` method has the correct return type!",
                    returnType);
        }
    }
}
 
