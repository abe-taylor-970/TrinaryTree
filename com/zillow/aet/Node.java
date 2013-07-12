package com.zillow.aet;

/**
 * @author Abraham Taylor
 * @version 10/19/2012
 * This class implements a node for a Trinary Tree. There could be added features such as size of current subtree, the sum
 * of values in the subtree, or the max/min of the values in subtree. It could also be created with objects rather than integers,
 * and contain the sum of the has values of the subtree. 
 */
public class Node {
	private int value = 0;
	private Node leftChild = null;
	private Node rightChild = null;
	private Node middleChild = null;
	
	/**
	 * This constructor creates a new instance of Node
	 * @param value - the value to be put in the current node
	 * @param leftChild - the left child of the current node
	 * @param middleChild - the middle child of the current node
	 * @param rightChild - the right child of the current node
	 */
	public Node(int value, Node leftChild, Node middleChild, Node rightChild) {
		this.value = value;
		this.leftChild = leftChild;
		this.middleChild = middleChild;
		this.rightChild = rightChild;
	}

	//getters and setters
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public Node getLeftChild() {
		return leftChild;
	}
	public void setLeftChild(Node leftChild) {
		this.leftChild = leftChild;
	}
	public Node getRightChild() {
		return rightChild;
	}
	public void setRightChild(Node rightChild) {
		this.rightChild = rightChild;
	}
	public Node getMiddleChild() {
		return middleChild;
	}
	public void setMiddleChild(Node middleChild) {
		this.middleChild = middleChild;
	}
}
