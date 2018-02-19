import java.rmi.*;
import java.io.*;
import java.util.*;
import java.lang.*;

public class A3Client{
	static int id;
	public static void main(String args[]){
		System.setSecurityManager(new SecurityManager());

		try{
			//Binding client with the server
			String name = "//in-csci-rrpc02.cs.iupui.edu:8888/A3Server";
			//Change this server name if you are running server program in any machine other than in-csci-rrpc02.cs.iupui.edu
			A3 myA3 = (A3) Naming.lookup(name);
			id = myA3.get_id();
			while(true){
				System.out.println("\nConnection Number: " + id);	
				//Taking connection number from the server
				System.out.println("\nOptions are as below:");
				//Tasks user would like to do
				System.out.println("\nExit --> 0\nPath --> 1\nSort --> 2\nEcho --> 3\nCheck --> 4\nMultiplier --> 5\n");
				System.out.print("\nEnter your choice here: ");
				int ch = Integer.parseInt(System.console().readLine());
				long startTime = 0;
				long endTime = 0;
				switch(ch){ //Client program runs until the user decides to terminate
					case 0: //If user decides to terminate the program
						System.out.println("Exiting the program");
						System.exit(0);
						break;
					
					case 1: //Getting path of current directory from the server
						startTime = System.nanoTime();
						String path = myA3.get_path(); //Initializing the function in server and getting the output of that function copied in local variable
						endTime = System.nanoTime();
						System.out.println("\nPath sent from server is: " + path);
						System.out.println("Elapsed time: " + (endTime - startTime)/1000 + " micro seconds"); //printing elapsed time in micro seconds
						break;
					
					case 2: //Sending unsorted list to server and getting back the sorted list
						Scanner s = new Scanner(System.in);
						System.out.print("\nEnter the array size you want to sort: ");
						int ct = Integer.parseInt(System.console().readLine()); //taking array size from user
						int[] int_array = new int[ct];
						System.out.println("\nEnter "+ ct+" integers for the input array.");
						for (int i = 0; i < ct; i++){ //Taking input from user for list elements 
							System.out.print("Enter the element number " + (i+1) + ": ");
							int_array[i] = Integer.parseInt(System.console().readLine());
						}
						System.out.println("\n\nThe input array is: ");
						for (int i = 0; i < ct; i++) //taking input from user
							System.out.print(int_array[i] + "  ");
						startTime = System.nanoTime(); 
						int_array = myA3.sort_func(int_array, ct); //Sending the unsorted list to server and getting the sorted list from server
						endTime = System.nanoTime();
						System.out.println("\n\nThe sorted array received from server is: ");
						for (int i = 0; i < ct; i++) //printing the sorted array received from the server
							System.out.print(int_array[i] + "  ");
						System.out.println("\n\nElapsed time: " + (endTime - startTime)/1000 + " micro seconds"); //printing elapsed time in micro seconds
						break;
					
					case 3:
						System.out.print("\nEnter the information you want server to echo: ");
						String ip = System.console().readLine(); //taking input from user
						startTime = System.nanoTime();
						System.out.println("\nEcho from server is: " + myA3.get_echo(ip)); //Sending the user entered information to server and printing the reply from server
						endTime = System.nanoTime();
						System.out.println("Elapsed time: " + (endTime - startTime)/1000 + " micro seconds"); //printing elapsed time in micro seconds
						break;
						
					case 4:
						System.out.print("\nEnter the name of the file you want server to check: ");
						String file_name = System.console().readLine(); //taking filename as input from the user
						startTime = System.nanoTime();
						if(myA3.file_check(file_name) == true) //Sending the user entered filename informatiobn to the server and printing the reply from server
							System.out.println("\nFile found in the current directory");
						else
							System.out.println("\nFile not found in the current directory");
						endTime = System.nanoTime();
						System.out.println("\nElapsed time: " + (endTime - startTime)/1000 + " micro seconds"); //printing elapsed time in micro seconds
						break;
						
					case 5:
						System.out.print("\nEnter the dimension of the matrices.\nIf you want to have matrices of size 4 * 4, please enter 4 here: ");
						int temp = Integer.parseInt(System.console().readLine());//Since its the square matrix, I am taking only one input and squaring it later
						//Taking the input for matrix elements from the user and storing them in local integer array
						int[] mat1 = new int[temp*temp];
						int[] mat2 = new int[temp*temp];
						int[] mat3 = new int[temp*temp];
						System.out.println("\n\nEnter the elements of first matrix.\n");
						//Taking the input for matrix elements from the user and storing them in local integer arrays
						for(int i = 0; i < temp*temp; i++)
						{
							System.out.print("Enter the element number "+ (i+1) +" for Matrix 1- ");
							mat1[i] = Integer.parseInt(System.console().readLine());
						}
						System.out.println("\nEnter the elements of second matrix.\n");
						for(int i = 0; i < temp*temp; i++)
						{
							System.out.print("Enter the element number "+ (i+1) +" for Matrix 1- ");
							mat2[i] = Integer.parseInt(System.console().readLine());
						}
						
						System.out.println("\nThe entered matrices are as below.\n");
						System.out.println("\nMatrix-1:\n");
						for (int i = 0; i < temp*temp; i+=temp) { 
							for (int j = 0; j < temp; j++)
								System.out.print(mat1[i+j]+ "  ");
								System.out.println();
						}
						System.out.println("\nMatrix-2:\n");
						for (int i = 0; i < temp*temp; i+=temp) { 
							for (int j = 0; j < temp; j++)
								System.out.print(mat2[i+j] + "  ");
								System.out.println();
						}
						startTime = System.nanoTime();
						//Sending the input matrices as single int array and the dimensions to server
						//Getting product from the server and saving the same in local variable
						mat3 = myA3.mat_mul(mat1, mat2, temp);
						endTime = System.nanoTime();
						System.out.println("\nThe product of two input matrices given by server is:\n");
						//Printing the product sent by server
						for (int i = 0; i < temp*temp; i+=temp) { 
							for (int j = 0; j < temp; j++)
								System.out.print(mat3[i+j] + "  ");
								System.out.println();
						}
						System.out.println("\nElapsed time: " + (endTime - startTime)/1000 + " micro seconds"); //printing elapsed time in micro seconds
						break;
		
					default:
						System.out.println("Invalid input. Try again!");
						break;	
			}
		} }catch(Exception e){
			System.out.println("A3Client Exception: " +
			e.getMessage());
			e.printStackTrace();
		}
		System.exit(0);
	}
}