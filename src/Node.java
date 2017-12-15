
public class Node {
	
	// parent node, left and right node
	public Node parent, left, right;
	
	// valeu of node, balancing of node
	public Integer value, balancing;
	
	public Node(Integer value, Node parent, Node left, Node right){
		this.value = value;
		this.parent = parent;
		this.left = left;
		this.right = right;
	}
	
	public Node(Integer value, Node parent){
		this.value = value;
		this.parent = parent;
	}
	
	public String toString(){
		return "Node value : "+this.value;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public Node getLeft() {
		return left;
	}

	public void setLeft(Node left) {
		this.left = left;
	}

	public Node getRight() {
		return right;
	}

	public void setRight(Node right) {
		this.right = right;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public Integer getBalancing() {
		return balancing;
	}

	public void setBalancing(Integer balancing) {
		this.balancing = balancing;
	}
	
}
