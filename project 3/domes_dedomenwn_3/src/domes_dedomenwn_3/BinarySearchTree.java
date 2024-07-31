package domes_dedomenwn_3;

public class BinarySearchTree { 
	  
    /* Class containing left and right child of current node and key value*/
    class Node { 
        int key; 
        Node left, right; 
  
        public Node(int item) { 
            key = item; 
            left = right = null; 
        } 
    } 
  
    // Root of BST 
    Node root; 
  
    // Constructor 
    BinarySearchTree() {  
        root = null;  
    } 
  
    // This method mainly calls insertRec() 
    void insert(int key) { 
       root = insertRec(root, key); 
    } 
      
    /* A recursive function to insert a new key in BST */
    Node insertRec(Node root, int key) { 
  
        /* If the tree is empty, return a new node */
        if (root == null) { 
            root = new Node(key); 
            return root; 
        } 
  
        /* Otherwise, recur down the tree */
        if (key < root.key) 
            root.left = insertRec(root.left, key); 
        else if (key > root.key) 
            root.right = insertRec(root.right, key); 
  
        /* return the (unchanged) node pointer */
        return root; 
    } 
    public Node search(Node root, int key) 
    { 
        // Base Cases: root is null or key is present at root 
        if ((increaseCounter() && root==null) ||( increaseCounter() && root.key==key)) 
            return root; 
      
        // val is greater than root's key 
        if (increaseCounter() && root.key > key) 
            return search(root.left, key); 
      
        // val is less than root's key 
        return search(root.right, key); 
    }
    
    static int counter =0;
    private static boolean increaseCounter() {
    	
    	return increaseCounter(1);
    	
    }
    private static boolean  increaseCounter(int amount) {
    	
	 counter = counter +amount;
	 
    	return true;
    	
    }

} 
