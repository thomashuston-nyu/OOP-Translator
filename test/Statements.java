public class Statements {

  public static void main(String[] args) {
  
 
  testConditionalStatement();
  
  //testContinueStatement();
  
  testDoWhileStatement();
  
  //testExpressionStatement(); *****
  
  testForStatement();
  
  int i = testReturnStatement();
  System.out.println("x = " + i);
  
  testSwitchStatement();
    
  testTryCatchFinallyStatement();
  
  testWhileStatement();
    
  }
  
  
  public static void testConditionalStatement () {
  
  	System.out.println("Testing Conditional Statements :");
  	int x = 1;
  	int y = 2;
  	
  	System.out.println("x = 1 \ny = 2");
  	
  	if (x==y) {
  		System.out.println("x is equal to y");
  	}
  	else if (x>y) {
  		System.out.println("x is greater than y");
  	}
  	else {
  		System.out.println("x is less than y");
  	}
  	
  	if(x != 0) {
  		; // do nothing
  		System.out.println("Testing Empty Statement : nothing happens");
  	}

  }
  
  public static void testDoWhileStatement () {
  
  	System.out.println("Testing DoWhile Statements :");
  	
  	int x = 0;
  	
  	System.out.println("x = 0");
  	
  	do {
  		System.out.println("x is equal to zero");
  		x++; // exit out of loop
  	} while (x == 0);
  
  }
  
  
  public static void testExpressionStatement () {

  }
  
  public static void testForStatement () {
  
  	System.out.println("Testing For Statements : ");
  	System.out.println("i will count from 0 to 9");
  	
  	for(int i = 0; i <10; i++) {
  		System.out.println("i is now " + i);
  	}
  }
  
  public static int testReturnStatement () {
  
  	System.out.println("Testing Return Statement : ");
  
  	int x = 0;
  	System.out.println("x is equal to 0");
  	return x;

  }
  
  public static void testSwitchStatement () {
  
  	System.out.println("Testing Swicth, Break, and Default Statements : ");
  
  	int x = 0;
  	
  	switch(x) {
  		case 1: System.out.println("x is equal to 1"); break;
  		case 2: System.out.println("x is equal to 2"); break;
  		case 3: System.out.println("x is equal to 3"); break;
  		default: System.out.println("default case"); break;
  	}

  }
  
  
  public static void testTryCatchFinallyStatement () {
  
  	System.out.println("Testing Try Catch Finally and Throws Statements : ");  	
  	
  	int [] x = {0,1,2};
  	System.out.println("Array x is of length 3");
  	
  	try
  	{
  		System.out.println("Trying to access array x at index 3 which is out of bounds");
  		int y = x[3];
  		throw new ArrayIndexOutOfBoundsException();
  	}
  	catch (ArrayIndexOutOfBoundsException e)
  	{
  		System.out.println("Array Index Out of Bounds Exception is caught!");
  	}
  	finally
  	{
  		System.out.println("Executing finally block");
  	}  	

  }
  
  public static void testWhileStatement () {
  
  System.out.println("Testing While Statements :");
  	
  	int x = 0;
  	
  	System.out.println("x = 0");
  	
  	while (x == 0) {
  		System.out.println("x is equal to zero");
  		x++;// get out of loop
  	}

  }

}
