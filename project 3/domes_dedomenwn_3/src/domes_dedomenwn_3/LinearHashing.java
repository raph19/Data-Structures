package domes_dedomenwn_3;
import java.io.*;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;

import static java.lang.System.out;
import java.util.*;
import java.util.Scanner;
/************************************************************************************
 * This class provides hash maps that use the Linear Hashing algorithm.
 * A hash table is created that is an array of buckets.
 */
public class LinearHashing <V>
    
{
    /** The number of slots (for key-value pairs) per bucket.
     */
    private static final int SLOTS = 10;    


    /** The class for type V.
     */
    private final Class <V> classV;
	

    /********************************************************************************
     * This inner class defines buckets that are stored in the hash table.
     */
    private class Bucket
    {
        int    nKeys;
        V []   value;
        Bucket next;
        @SuppressWarnings("unchecked")
        Bucket (Bucket n)
        {
            nKeys = 0;
            value = (V []) Array.newInstance (classV, SLOTS);
            next  = n;
        } // constructor
    } // Bucket inner class

    /** The list of buckets making up the hash table.
     */
    private final List <Bucket> hTable;

    /** The modulus for low resolution hashing
     */
    private int mod1;

    /** The modulus for high resolution hashing
     */
    private int mod2;

    /** Counter for the number buckets accessed (for performance testing).
     */
    private int count = 100;
    static int nKeys=100;
    /** The index of the next bucket to split.
     */
    private int split = 0;

    private static int counter =0;
    
    final double DEFAULT_LOAD_FACTOR = 0.5;    
    final double DEFAULT_LOAD_FACTOR1 = 0.5; 
    
    /********************************************************************************
     * Construct a hash table that uses Linear Hashing.
     * @param classK    the class for keys (K)
     * @param classV    the class for keys (V)
     * @param initSize  the initial number of home buckets (a power of 2, e.g., 4)
     */
    public LinearHashing (Class <V> _classV, int initSize)
    {
    	initSize = 100;
        classV = _classV;
        hTable = new ArrayList <> ();
        mod1   = initSize;
        mod2   = 2 * mod1;
        
        //initialize the buckets in hTable to null buckets
        for(int t=0; t<100; t++)
            hTable.add(new Bucket(null));
    } // constructor

    private static boolean increaseCounter() {
    	
    	return increaseCounter(1);
    	
    }
 private static boolean  increaseCounter(int amount) {
    	
	 counter = counter +amount;
	 
    	return true;
    	
    }

    /********************************************************************************
     * Given the key, look up the value in the hash table.
     * @param key  the key used for look up
     * @return  the value associated with the key
     */
    public V get (Object value)
    {
        
        int i = h (value); 
        if(i<split)
            i=h2(value);
        Bucket tmp = hTable.get(i);
        //make sure the bucket isn't empty
        //because that would be a major waste of time
        if(tmp.nKeys == 0)
            return null;
        
        //go through my buckets in table entry i
        while(tmp != null){
            for(int m=0; m<tmp.nKeys; m++){
               if(value.equals(tmp.value[m]))
                    return tmp.value[m]; //found it
            }//for loop over values in bucket
            tmp = tmp.next; // key not in this bucket so try the next one
        }//while bucket != null

        return null;//if we make it this far, it ain't in the table so return null
    } // get

    /********************************************************************************
     * Put the key-value pair in the hash table.
     * @param key    the key to insert
     * @param value  the value to insert
     * @return  null (not the previous value)
     */
        
    
    public V put (V value)
    {
    	
    	
        int i = h (value); increaseCounter();
        if(increaseCounter() && i<split) {
            i=h2(value); increaseCounter();}
        Bucket temp = hTable.get(i); increaseCounter();
        
        if(increaseCounter() && temp.nKeys < SLOTS ){

            //simple insert; no split needed

            temp.value[temp.nKeys] = value;
            
            temp.nKeys++;		increaseCounter(2);
            
        }else{
        	
            while(increaseCounter() && temp.next != null){
                temp = temp.next; 	increaseCounter();
            }
            //check in the last bucket of the chain
            if(increaseCounter() && temp.nKeys < SLOTS){
            	
                temp.value[temp.nKeys] = value;
                temp.nKeys++;		increaseCounter(2);
                				
            }else{
            	
                temp.next = new Bucket(null);
                temp = temp.next;
                temp.value[temp.nKeys]=value;
                temp.nKeys++;
                
                increaseCounter(4);
                
            }
         
            
        }
   
            
            double alpha = ((double)nKeys)/(SLOTS * count);		increaseCounter();
            if(  increaseCounter() && alpha > DEFAULT_LOAD_FACTOR ){
            	
                count++;
                Bucket temp2 = new Bucket(null);  //replace the split
                Bucket temp3 = new Bucket(null);  //the new bucket
                temp = hTable.get(split);         //the bucket to split
                int p1 = 0;
                int p2 = 0;			increaseCounter(6);
                for(int p=0; increaseCounter() && p<temp.nKeys; p++){
                    int z = h2(temp.value[p]); increaseCounter();
                    if(  increaseCounter() && z == split){
                      
                        temp2.value[p1] = temp.value[p]; 
                        temp2.nKeys = p1+1;		 
                        p1++;					increaseCounter(3);
                    }else{
                       
                        temp3.value[p2] = temp.value[p]; 
                        temp3.nKeys = p2+1;
                        p2++;					increaseCounter(3);
                        
                    }
                }
                
                
                hTable.remove(split);
                hTable.add(split, temp2);
                hTable.add(temp3);
                
                if(increaseCounter() && split==mod1-1) {
                    mod1= mod1*2;
                    mod2= mod1*2;
                    split=0;
                    
                    increaseCounter(3);
                  
                }else{
                    split++;		increaseCounter();
                   
                }

        } return null;       
  } 

    /********************************************************************************
     * Return the size (SLOTS * number of home buckets) of the hash table. 
     * @return  the size of the hash table
     */
    public int size ()
    {
        return SLOTS * (mod1 + split);
    } 

    /********************************************************************************
     * Print the hash table.
     */
    private void print ()
    {
        out.println ("Hash Table (Linear Hashing)");
        out.println ("-------------------------------------------");

        for(int x=0; x<hTable.size();x++){
            out.print(x + ":");
            Bucket tmp = hTable.get(x);
            boolean chain = false; //assume no chain
            if(tmp.next!=null)
                chain=true;//if next exist, there is a chain
            if(chain){
                out.print("[ ");
                for(int z=0; z<SLOTS;z++){
                    out.print(tmp.value[z]);
                    if(SLOTS!=z+1)
                        out.print(", ");//there is another item
                    
                    else
                        out.print(" ] --> ");//end of bucket, but another one is coming
                }//for
                out.print("[ ");
                for(int z=0;z<SLOTS;z++){
                    out.print(tmp.next.value[z]);
                    if(SLOTS!=z+1) {
                        out.print(" ,");
                    }

                }
                out.print(" ] ");
            }else{
                //only one bucket
                out.print("[ ");
                for(int z=0; z<SLOTS; z++){
                    out.print(tmp.value[z]);
                    if(SLOTS != z+1)
                        out.print(", ");
                }//for
                out.print(" ]");
            }
            out.println();
        }//for over hTable
        out.println ("-------------------------------------------");
    } // print

    /********************************************************************************
     * Hash the key using the low resolution hash function.
     * @param key  the key to hash
     * @return  the location of the bucket chain containing the key-value pair
     */
    private int h (Object key)
    {
    	if (key.hashCode()<0) {
    		
    		
    		
            return (key.hashCode ()*(-1)) % mod1;

    		
    	}else
        return key.hashCode () % mod1;
    } // h
    
    /********************************************************************************
     * Hash the key using the high resolution hash function.
     * @param key  the key to hash
     * @return  the location of the bucket chain containing the key-value pair
     */
    private int h2 (Object key)
    {
    		if (key.hashCode()<0) {

            return (key.hashCode ()*(-1)) % mod2;
    		}else
        return key.hashCode () % mod2;
    } // h2
        
    
    public int[] addNum(int nums) throws IOException {

    	int arr [] = new int [10000];
    	
		FileInputStream fis = new FileInputStream("testnumbers_10000_BE.bin");
		byte[] array = new byte[100*nums*4];
		fis.read(array);
		ByteBuffer bb = ByteBuffer.wrap(array);
		for(int i=0;i<100*nums;i++) {
			arr[i] = bb.getInt(i);
	    }
		
		fis.close();
		return arr;
    }
    
    public V delete (Object value) {
    	
    	 int i = h (value); increaseCounter();
         if(increaseCounter() && i<split) {
            i=h2(value); 	increaseCounter();}
         Bucket tmp = hTable.get(i); increaseCounter();
         
         boolean del=true;  increaseCounter();
         while((increaseCounter() && tmp != null) && increaseCounter() && del){
        	 int e=0; increaseCounter();
        	 while (increaseCounter() && del && increaseCounter() && e<tmp.nKeys)
	         {
	        	  if(increaseCounter() && value.equals(tmp.value[e])) {
	        		  
	        		  del=false; increaseCounter();
	        		  System.out.println("Element deleted from bucket : "+ i);
	        		  nKeys--; 		increaseCounter();
	        		  //in case of deletion, we transfer the following keys one position left
	        		 
	        		  for (int r=e;increaseCounter()&& r<tmp.nKeys-1 ; r++) {
	        			  tmp.value[r]=tmp.value[r+1]; increaseCounter();
	        		  }
	        		  tmp.value[tmp.nKeys-1]=null; 
	        		  tmp.nKeys--;increaseCounter(2);
	        		  
	        		 
	        			
	        		  //in case there is overflow bucket
	        		 if (increaseCounter() && (tmp.next != null)) {
	        			  tmp.value[9]=tmp.next.value[0]; 
	        			  tmp.nKeys++; increaseCounter(2);
	        			  for (int m=0;increaseCounter()&& m<tmp.next.nKeys-1 ; m++) {
		        			  tmp.next.value[m]=tmp.next.value[m+1]; increaseCounter();
		        		  }
		        		  tmp.next.value[tmp.nKeys-1]=null; 
		        		  tmp.next.nKeys--; increaseCounter(2);
		        		  if (increaseCounter() && tmp.next.nKeys==0) {
		        		   tmp.next=null; increaseCounter();
		        		  } 
	        		  }
	        		  
	        		  
	        		  
	        		  
	        		  
	        	  }
	        	  e++;	increaseCounter();
	         } 
        	 tmp=tmp.next; increaseCounter();
        	 
         }
         if (increaseCounter() && del) {
	         System.out.println("That element does not exist in HashTable.");
	         return null;
	         }
         else {
        	 
        	 //merge
             double beta = ((double)nKeys)/(SLOTS * count); increaseCounter();
             if(increaseCounter() && beta < DEFAULT_LOAD_FACTOR1 && count!=100 ){

                 int q = h (count-1); 
                 
                 
            	 Bucket temp = hTable.get(count-1);	//last bucket
            	 Bucket temp2 = hTable.get(q);//last splitted 
            	 increaseCounter(3);
            	 ///put
            	 for (int r=0 ;increaseCounter() && r<temp.nKeys ; r++ ) {
	                 
	                 if(increaseCounter() && temp2.nKeys < SLOTS ){
	
	                     //simple insert
	                     temp2.value[temp2.nKeys] = temp.value[r];
	                     temp2.nKeys++; increaseCounter(2);
	                     
	                 }else{
	                     
	                     while(increaseCounter() && temp2.next != null){
	                         temp2 = temp2.next; 	 increaseCounter();

	                     }
	                     //check in the last bucket of the chain
	                     if(increaseCounter() && temp2.nKeys < SLOTS){
	                         temp2.value[temp2.nKeys] = temp.value[r];
	                         temp2.nKeys++; increaseCounter(2);
	                     }else{
	                         temp2.next = new Bucket(null);
	                         temp2 = temp2.next;
	                         temp2.value[temp2.nKeys]=temp.value[r];
	                         temp2.nKeys++;
	                         increaseCounter(4);
	                     }
	                 }
            	 }
	            	 
	            	 
	             hTable.remove(count-1);
	             count--;		increaseCounter();
	             if ( increaseCounter() && split==0) {
	            	 split=((mod1/2)-1);
	        		 increaseCounter();
	             }
	             else {
	            	 split--; increaseCounter();}
	        	 if (increaseCounter() && split==((mod1/2)-1)) {
	        		 mod1=mod1/2;
	        		 mod2=mod1*2;
	        		 increaseCounter(2);
	        	 }
            }
        	
            
        	return null; 
         }
	       
    }
         
    
	public V search (Object value)
     {
        
        int i = h (value); increaseCounter();
        if(increaseCounter() && i<split) {
           i=h2(value); increaseCounter();}
        Bucket tmp = hTable.get(i); increaseCounter();
        
        while(increaseCounter() && tmp != null){
        	
	        
	        for ( int e=0;increaseCounter() && e<tmp.nKeys ; e++)
	         {
	        	  if(increaseCounter() && value.equals(tmp.value[e])) {
	        		  System.out.println("That  element exists in HashTable, in bucket "+ i);
		          return null;
	           }	  
	         }
	        tmp=tmp.next; increaseCounter();
        }
        System.out.println("That element does not exist in HashTable.");
        return null;
               
            
     }
     


    
    /********************************************************************************
     * The main method used for testing.
     * @param  the command-line arguments (args [0] gives number of keys to insert)
     * @throws IOException 
     */
    private static Scanner scanner = new Scanner(System.in);
	private static boolean isNotX = true;
	static String newLine = System.getProperty("line.separator");
    
    public static void main (String [] args) throws IOException
    {
        LinearHashing <Integer> ht = new LinearHashing <> (Integer.class, 1);
        //insert first 100 keys in hashtable
        int count1 =1;
        int []	 myArray = ht.addNum(1);
        if (args.length == 1) nKeys = Integer.valueOf (args [0]);
        for (int i = 0; i < nKeys; i ++) ht.put (myArray[i]);
        ht.print ();
        
        //insert first 100 keys in bst
        BinarySearchTree tree = new BinarySearchTree(); 
        for (int i = 0; i < nKeys; i ++) tree.insert(myArray[i]);
        
        
        System.out.println((double)counter/100);
        out.println ("-------------------------------------------");
       
        
        while (isNotX) {
        
        	System.out.println(
        			
        			"Insert a key : @ "   + newLine +
					"Search a Key : s "   + newLine +
					"Delete a key : d "   + newLine +
					"Make a choice :  "   +newLine);
        	
        	char input = scanner.next().charAt(0);
        	
        	switch(input) {
        	
        	case '@' : //insert a key 
        		
	        	counter=0;
	            count1++;
	            	
		        if (count1<101)	{
		            myArray = ht.addNum(count1*1);        
		            nKeys+=100;
		            for (int i =nKeys-100; i < nKeys; i ++) ht.put (myArray[i]);
		           
		            ht.print ();
		                 
		            System.out.println((double)(counter/(100)));
		            
		            
		           //insert next 100 keys in bst
		            for (int i =nKeys-100; i < nKeys; i ++) tree.insert(myArray[i]); }
		        else {
		        	System.out.println("There are no more keys!!");}
              
                 
            break;
          
        	case 's' : //search a key 
		
        		counter = 0;
        		BinarySearchTree.counter = 0;
        		myArray = ht.addNum(100);

        		for (int i =0; i < 50; i ++){   
        			
	        		Random random = new Random();
	       			int randomInteger = random.nextInt(9999);
       			
	       			System.out.println("Search key: " + myArray[randomInteger]);   
	       			
	       			ht.search (myArray[randomInteger]);
	       			// bst searching
	       			tree.search(tree.root, myArray[randomInteger]);}
       		
                System.out.println("hashtable : " + (double)(counter/50));
                //bst counter
                System.out.println("binary search tree : " + (double)(BinarySearchTree.counter/50));
                break;
       			
        	case 'd' : //delete a key 
        		
        		counter = 0;

        		myArray = ht.addNum(100);
        		
        		for (int i =0; i < 50; i ++){   
        			
	        		Random random1 = new Random();
	       			int randomInteger1 = random1.nextInt(9999);
       			
	       			System.out.println("Key to delete : " + myArray[randomInteger1]);   
	       			
	       			ht.delete (myArray[randomInteger1]);}
        		
       			
      			ht.print();

                System.out.println((double)(counter/50));
                
                break;

        }
     // main

	
    }
    }

}// LinHashMap class
