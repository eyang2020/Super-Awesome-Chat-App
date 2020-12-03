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
import java.util.ArrayList;
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
        @Test(timeout = 1000)
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
            assertTrue("Ensure that `groups` in `User` class is of type String!", Collection.class.isAssignableFrom(groups.getType()));
            // TODO: check if methods of User are implemented correctly with correct type and access modifier
            
            // TODO: two implementation test: 1. success with proper input 2. failure with improper input

        }
    }    
}
 
