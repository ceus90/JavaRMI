import java.rmi.*;
import java.io.*;
import java.rmi.server.*;

public class A3Server extends UnicastRemoteObject implements A3{
	private float value = 10000;
	private int id = 0;
	private String name;
	private String path;

	public A3Server(String s) throws RemoteException{
		super(); name = s; //For binding
	}
	
	public synchronized int get_id() throws RemoteException{
		id++; // To get the number of clients connected to the server
		System.out.println("\nConnection number " +id+" established.");
		return id;
	}
	
	public synchronized String get_path() throws RemoteException{
		path = System.getProperty("user.dir"); //Function to get path of the current directory
		System.out.println("\n\nClient request received to get the current directory path.\nPath sent to client is: " + path);
		return path; //Sending the path to client
	}
	
	public synchronized int[] sort_func(int int_arr[], int ct) throws
		RemoteException{
			System.out.println("\n\nThe input array received from client is: ");
			for (int i = 0; i < ct; i++) //Taking user input integer array from the client program
							System.out.print(int_arr[i] + "  ");
			for (int i = 0; i < ct-1; i++)
				for (int j = 0; j < ct-i-1; j++)
                if (int_arr[j] > int_arr[j+1])
                { //Sorting the list using bubblesort
                    int temp = int_arr[j];
                    int_arr[j] = int_arr[j+1];
                    int_arr[j+1] = temp;
                }
			System.out.println("\n\nThe sorted array sent to client is: ");
			for (int i = 0; i < ct; i++) //Sending the sorted list back to the client
				System.out.print(int_arr[i] + "  ");
			return int_arr;
	}
	
	public synchronized String get_echo(String s) throws RemoteException{
		System.out.println("\n\nInput received from client is \""+ s + "\", and the same is echoed back.");
		return s; //Taking user input information from the client and returning the same
	}
	
	public synchronized boolean  file_check(String file_name) throws RemoteException{
		
		path = System.getProperty("user.dir");
		String temp = path+"/"+file_name; //Receive filename from the client
		boolean check = new File(temp).exists(); //Check if that file exists in the current directory or not
		System.out.println("\nRequest received from client to check for file \"" + file_name + "\" in the directory- " + path);
		System.out.println("Result sent back to the client: " + check); //Return the boolean value to the client
		return check;
	}
	
	public synchronized int[] mat_mul(int int_arr1[], int int_arr2[], int sz) throws
		RemoteException{
			
			int[] prod = new int[sz*sz];
			int d = 0; //Receiving two matrices from the client and their dimension
			System.out.println("\n\nThe input matrices received from the client are as below.");
			System.out.println("\nMatrix-1:\n");
				for (int i = 0; i < sz*sz; i+=sz) { 
					for (int j = 0; j < sz; j++)
						System.out.print(int_arr1[i+j]+ "  ");
						System.out.println();
				}
			System.out.println("\nMatrix-2:\n");
				for (int i = 0; i < sz*sz; i+=sz) { 
					for (int j = 0; j < sz; j++)
						System.out.print(int_arr2[i+j] + "  ");
						System.out.println();
				}
			for (int k = 0; k < sz; ++k)
				for (int i = 0; i < sz; ++i)
            		for(int j = 0; j < sz; ++j)
            		{ //Calculating the product of two matrices received
                		d = prod[i*sz+j];
                		d += int_arr1[i*sz+k] * int_arr2[k*sz+j];
                		prod[i*sz+j] = d;
            		}
					
			System.out.println("\nThe below product is sent to the client:\n");
				for (int i = 0; i < sz*sz; i+=sz) { 
					for (int j = 0; j < sz; j++)
						System.out.print(prod[i+j] + "  ");
						System.out.println();
				} //Sending the product back to the client
			return prod;
		}

	public static void main(String args[]){
		System.setSecurityManager(new SecurityManager());
		try{
			System.out.println("Creating a A3 Server!");
			String name = "//in-csci-rrpc02.cs.iupui.edu:8888/A3Server";
			A3Server A3 = new A3Server(name);
			System.out.println("A3Server: binding it to name: " +
			name);
			Naming.rebind(name, A3);
			System.out.println("A3 Server Ready!");
		} catch (Exception e){
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
