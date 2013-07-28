package com.sirra.appcore.util;


import java.io.*;

import javax.servlet.*;

import java.rmi.RemoteException;

/**
 * This class has the functions to convert Exception StackTrace to a string.
 */
public class ExceptionUtil
{
	public static void print(Throwable t)
	{
		System.out.println(getStackTrace(t));
	}
	/**
	 *	This method takes a exception as an input argument and returns the stacktrace as a string.
	 */    
	public static String getStackTrace(Throwable exception)
    {
		if(exception == null) return "";
		
    	StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        
        pw.println("");
        pw.println("");
        pw.println("---------------- BEGINNING OF EXCEPTION TRACE --------------------");
        
        recursivelyPrintStackTrace(exception, pw);
        
        return sw.toString();
    }
	
    private static void recursivelyPrintStackTrace(Throwable exception, PrintWriter pw)
    {           
        // Get the message
        pw.println("Exception of type \"" + exception.getClass().getName() + "\" has occured.");
        pw.print("Message: ");
        pw.println(exception.toString());
        
        pw.println("Stack trace:");
        printStack(exception, pw);
        Throwable nestedException = getNestedException(exception);
        if(nestedException != null) {
        	pw.println("");
        	pw.println("This exception has a nested exception, shown below.");
        	recursivelyPrintStackTrace(nestedException, pw);
        }
        else {
        	pw.println("---------------- END OF EXCEPTION TRACE --------------------");
        }
    }
    
    public static Throwable findNestedException(Throwable exception, Class throwableClass)
    {
    	Throwable currentException = exception;
    	for(int i=0; i<1000; i++) {
			if(currentException == null) { return null; }
			if(throwableClass.isInstance(currentException)) { return currentException; }
			currentException = getNestedException(currentException);
    	}
    	return null;
    }
    
    /**
     * Search for a nested exception matching the packagePrefix, but not one of the exclusionPackagePrefixes.
     * 
     */
    public static Throwable findNestedException(Throwable exception, String packagePrefix, String... exclusionPackagePrefixes)
    {
    	Throwable currentException = exception;
    	for(int i=0; i<1000; i++) {
			if(currentException == null) { return null; }
			String currentExceptionFullyQualifiedClassName = currentException.getClass().getCanonicalName();
			if(currentExceptionFullyQualifiedClassName.indexOf(packagePrefix) == 0) {
				boolean isExcluded = false;
				for(int j=0; j<exclusionPackagePrefixes.length; j++) {
					if(currentExceptionFullyQualifiedClassName.indexOf(exclusionPackagePrefixes[j]) == 0) {
						isExcluded = true;
						break;
					}
				}
				if(!isExcluded) return currentException;
			}
			currentException = getNestedException(currentException);
    	}
    	return null;
    }
    
    public static Throwable getNestedException(Throwable exception) 
    {
    	Throwable nestedException;
    	
    	// java.lang.Throwable
    	try {
    		nestedException = exception.getCause();
    		return nestedException;
    	}
    	catch(Exception e) {
    	}
    	
    	return null;
    }
    private static void printStack(Throwable exception, PrintWriter pw) 
    {	
    	StackTraceElement stack[] = exception.getStackTrace();
    	// stack[0] contains the method that created the exception.
        // stack[stack.length-1] contains the oldest method call.
        // Enumerate each stack element.
        for (int i=0; i<stack.length; i++) {
            String filename = stack[i].getFileName();
            int line = stack[i].getLineNumber();
            String fileStr;
            if (filename == null) {
                // The source filename is not available
                fileStr = "";
            }
            else
            {
            	fileStr = " -- (" + filename + ":" + line + ")";
        	}
            String className = stack[i].getClassName();
            String methodName = stack[i].getMethodName();
            String tab;
            
            // $ is for tapestry built classes.
            if(className.indexOf("com.sirra") == 0 && 
            		className.indexOf("$") < 0 && 
            		methodName.indexOf("$") < 0 &&
            		methodName.indexOf("dispatchComponentEvent") < 0)
            {
            	tab = "    ";
            	pw.println(tab + "at " + className +  "." + methodName + "()" + fileStr);
            }
        }
    }
}