public interface A3 extends java.rmi.Remote{
	int get_id() throws java.rmi.RemoteException;
	String get_path() throws java.rmi.RemoteException;
	int[] sort_func(int int_arr[], int ct) throws java.rmi.RemoteException;
	String get_echo(String s) throws java.rmi.RemoteException;
	boolean  file_check(String file_name) throws java.rmi.RemoteException;
	int[] mat_mul(int int_arr1[], int int_arr2[], int sz) throws java.rmi.RemoteException;
}
