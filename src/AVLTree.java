
public class AVLTree {
	
	public Node root; // root node of tree
	public Integer val; // used to comparation for the balancing
	public boolean toBalance = false; // need balancing
	public Integer unbalanced; // value of unbalanced node	

	/**
	 * Add a new node to tree
	 * 
	 * @param value - node value
	 * @return value - value of no inserted
	 */
	public Integer addNode(Integer value) {
		Node aux = new Node(value, null, null, null);
		if (root == null) {
			root = aux;
		} else {
			val = value;
			add(root);
		}
		
		if(needBalance()){
			balance();
		}
		
		return value;
	}
	
	/**
	 * Make balancing of tree
	 */
	void balance(){
		Node aux = searchNode(unbalanced);
		int q = factor(aux);
		if(q > 1){
			if(factor(aux.right) < 0){
				doubleRotationLeft();
			}else{
				leftRotation();
			}
		}else{
			if(factor(aux.left) > 0){
				doubleRotationRight();
			}else{
				rightRotation();
			}
		}
		toBalance = false;
		unbalanced = null;
	}

	/**
	 * Remove a node from tree by node value
	 * 
	 * @param value
	 *            - Node value to remove
	 * @return delNode - Value of removed node
	 */
	public Node removeNode(Integer value) {
		Node delNode = searchNode(value);
		if (!remove(delNode)) {
			Node nextNode = getSuccessor(delNode);
			remove(nextNode);
			delNode.value = nextNode.value;
		}
		if(needBalance()){
			balance();
		}
		return delNode;
	}

	/**
	 * Find a node in tree by node value
	 * 
	 * @param value
	 *            - Node value 
	 * @return node - Found node
	 */
	public Node searchNode(Integer value) {
		Node current = root;
		while (current != null && current.value != value) {
			if (value < current.value) {
				current = current.left;
			} else {
				current = current.right;
			}
		}
		return current;
	}

	/**
	 * Method for support the remove node operation
	 * 
	 * @param node
	 *            - Base node for find the next
	 * @return node - Found node
	 */
	public Node getSuccessor(Node node) {
		Node temp = node.right;
		while (true) {
			if (temp.left == null) {
				break;
			}
			temp = temp.left;
		}
		return temp;
	}

	/**
	 * Method for support the insert node operation
	 * 
	 * @param node
	 *            - Node for check
	 */
	private void add(Node node) {
		if (val < node.value) {
			if (node.left != null) {
				add(node.left);
			} else {
				node.left = new Node(val, node);
			}
		} else {
			if (node.right != null) {
				add(node.right);
			} else {
				node.right = new Node(val, node);
			}
		}
	}

	/**
	 * Check if a node is external
	 * 
	 * @param node - Node for check
	 * @return boolean flag - true if don't have left and right children otherwise false
	 */
	public boolean isExternal(Node node) {
		return (node.right == null && node.left == null);
	}

	/**
	 * Check if a node is a left child
	 * @param node - Node for check
	 * @return boolean true if is left child otherwise false
	 */
	public boolean isLeftChild(Node node) {
		return (node.parent.left == node);
	}

	/**
	 * Remove a node from tree in three cases: 1- Without children, 2- With a child left ou right 
	 * 
	 * @param node - Node for remove
	 * @return boolean - true if was removed otherwise false				
	 */
	private boolean remove(Node node) {
		// Removal of a node without children
		if (isExternal(node)) {
			if (node == root) {
				root = null;
			} else if (isLeftChild(node)) {
				node.parent.left = null;
			} else {
				node.parent.right = null;
			}
		}
		// Removal of a node with a left child only
		else if (node.left != null && node.right == null) {
			if (node == root) {
				root = root.left;
			} else if (isLeftChild(node)) {
				node.parent.left = node.left;
			} else {
				node.parent.right = node.left;
			}
		}
		// Removal of a node with a right child only
		else if (node.left == null && node.right != null) {
			if (node == root) {
				root = root.right;
			} else if (isLeftChild(node)) {
				node.parent.left = node.right;
			} else {
				node.parent.right = node.right;
			}
		} else {
			return false;
		}

		return true;
	}
	
	/**
	 * Height of a node
	 * 
	 * @param node - Node for calculate height
	 * @return height - Node height
	 */
	public Integer height(Node node) {
		if (node == null) {
			return 0;
		}
		int hl = height(node.left), hr = height(node.right);
		return (hl > hr) ? 1 + hl : 1 + hr;
	}

	// Support method for print nodes
	public void print() {
		print(root);
	}
	
	/**
	 * Print nodes recursively
	 * 
	 * @param node - Node base for print. (For print all nodes, the root node should be passed in param)
	 */
	private void print(Node node) {
		if (node != null) {
			System.out.print(node.value + " --> ");
			print(node.left);
			print(node.right);
		}
	}
	
	public void printFactor(){
		printFactor(root);
		System.out.println("");
	}
	
	private void printFactor(Node node){
		if(node != null){
			System.out.println("Nó "+node.value+", FATOR : "+factor(node));
			printFactor(node.left);
			printFactor(node.right);
		}
		
	}
	
	/**
	 * Calculate the factor of a node
	 * 
	 * @param node - Node for calculate factor
	 * @return fator Calculated factor 
	 */
	private int factor(Node node){
		int he = height(node.left)+1, hd = height(node.right)+1;
		return hd-he;
	}
	
	/**
	 * Chekc if tree need balancing
	 * 
	 * @return boolean if nedd balancing 
	 */
	public boolean needBalance(){
		checkBalance(root);
		return toBalance;
	}
	
	/**
	 * Check balancing of tree 
	 * 
	 * @param node Base node
	 */
	public void checkBalance(Node node){
		if(node == null || toBalance){
			return;
		}
		
		int factor = factor(node);
		if(factor < -1 || factor > 1){
			toBalance = true;
			unbalanced = node.value;
			return;
		}
		checkBalance(node.left);
		checkBalance(node.right);
	}
	
	// Support method to double-rotation
	public void leftRotation(){
		Node aux = root; // old root
		root =root.right; // left child will be the new root
		aux.right = root.left; // Making the right subtree of old root to be left subtree of new root 
		aux.parent = root; // the parent of old root will be the new root
		root.left = aux; // the old root will be left child of new root
		root.parent= null; // root don't have parent
	}
	
	/**
	 * Make left rotation operation in tree
	 * 
	 * @param value Node value 
	 */
	public void leftRotation(Integer value){
		leftRotation(searchNode(value));
	}
	
	/**
	 * Support method for left-rotation operation
	 * 
	 * @param n Node base for rotation
	 */
	private void leftRotation(Node n){
		n.parent.left = n.right;
		n.right.left =n;
		n.right.left.right=null;
	}
	
	// Make a double-rotation-left operation
	public void doubleRotationLeft(){
		rightRotation(root.right);
		leftRotation();
	}
	
	// Make a double-rotation-right operation
	public void doubleRotationRight(){
		leftRotation(root.left);
		rightRotation();
	}
	
	/**
	 * Make a right rotation operation
	 * @param root value of node (use root for all nodes of tree)
	 */
	public void rightRotation(Integer root){
		Node n = searchNode(root);
		rightRotation(n);
	}
	
	/**
	 * Method support for right-rotation operation
	 * 
	 * @param n - Node base for operation
	 */
	private void rightRotation(Node n){
		n.parent.right = n.left;
		n.left.right = n;
		n.left.right.left=null;
	}
	
	// Method support for double-rotation operation
	public void rightRotation(){	
		Node aux = root; // raiz antiga
		root = root.left; // tornando o filho da esquerda a nova raiz
		aux.left = root.right; 
		aux.parent = root;
		root.right = aux;
		root.parent = null;
	}
	
	// Get tree root
	public Node getRoot(){
		return root;
	}

	
}
