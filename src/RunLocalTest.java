package src;

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
            }
            catch(ClassNotFoundException e) {
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
            }
            catch(NoSuchFieldException e) {
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
            assertEquals("Text must match from being changed","I am changed", tester.getUsername());

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
            assertEquals("Text must match from being changed","I am changed", tester.getName());

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
            assertEquals("Text must match from being changed","I am changed", tester.getEmail());

            // verifying failure setEmail

            tester.setEmail("Changing again");
            assertEquals("Text can not match since they are different values", "I am changed",
                    tester.getEmail());

            try {
                method = userObject.getDeclaredMethod("setPhoneNumber", Long.class);
            } catch (NoSuchMethodException e) {
                Assert.fail("Ensure that `User` declares a method named `setPhoneNumber` that has one parameter of type" +
                        "Long!");
                return;
            }
            modifiers = method.getModifiers();
            returnType = method.getReturnType();

            Assert.assertTrue("Ensure that `User`'s `setPhoneNumber` method is `public`",
                    Modifier.isPublic(modifiers));
            assertNull("Ensure that `User`'s `setPhoneNumber` method has the correct return type!", returnType);

            // verifying setPhoneNumber

            tester.setPhoneNumber(4804289202L);
            assertEquals("Phone number must match from being changed",4804925355L,
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
            assertEquals("Text must match from being changed","I am changed", tester.getPassword());

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
            assertEquals("ID number must match from being changed",123456, tester.getUserID());

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
            }
            catch(ClassNotFoundException e) {
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
            }
            catch(NoSuchFieldException e) {
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
            assertEquals("Text must match from being changed","I am changed", tester.getText());

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
            }
            catch(ClassNotFoundException e) {
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
            }
            catch(NoSuchFieldException e) {
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
            assertEquals("Group name must match from being changed","new name",
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
            }
            catch(ClassNotFoundException e) {
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
            }
            catch(NoSuchFieldException e) {
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
            modifiers = actionListener.getModifiers();
            assertTrue("Ensure that `actionListener` in `Login` class is private!", Modifier.isPrivate(modifiers));
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
        public void serverThreadTestClass() {
            // check if ServerThread class exists
            try {
                Class.forName("ServerThread");
            }
            catch(ClassNotFoundException e) {
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
            }
            catch(NoSuchFieldException e) {
                System.out.println(e.toString());
                return;
            }
            // check fields of class for correct access modifier and data type
            modifiers = socket.getModifiers();
            assertTrue("Ensure that `socket` in `ServerThread` class is private!", 
                    Modifier.isPrivate(modifiers));
            assertTrue("Ensure that `socket` in `ServerThread` class is of type Socket!",
                    String.class.isAssignableFrom(socket.getType()));
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
    }
}
 
