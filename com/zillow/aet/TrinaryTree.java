package com.zillow.aet;

import java.util.Random;

/**
 * @author Abraham Taylor
 * @version 10/19/2012
 * This class implements a Trinary Tree, each node with three children. For a given node with children, the left child is always 
 * less than the given node, the right child is always greater than the given node, and the middle child is always equal to the
 * given node.
 */
public class TrinaryTree {
	private Node root = null;
	
	/**
	 * inserts a number into the tree
	 * @param number - the value of the node to be inserted
	 */
	public void insert(int number) {
		if (root == null) {
			root = new Node(number, null, null, null);
		}
		else {		
			recursiveInsert(number, root);
		}
	}
	
	/**
	 * deletes a number from the tree. This function deletes the lowest node in the tree found. In more technical terms, the node
	 * deleted will not have any middle children.
	 * @param number - the value of the node to be deleted
	 * @return true if number is found in tree, false otherwise
	 */
	public boolean delete(int number) {
		if (root == null) {
			return false;
		}
		else {
			return recursiveDelete(number, root, null);
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		recursiveDfs(root, sb);
		return sb.toString();
	}
	
	/**
	 * This function recursively searches tree for the correct position, and then deletes the node depending on three possibilities :
	 * 1) no right child 2) no left child 3) there are both right and left children
	 * in case 3, the node is swapped with either the inorder predecessor (next smallest item) or the 
	 * inorder successor (next biggest item), chosen at random
	 * @param number - the value of the Node to be deleted
	 * @param position - the current position in the tree
	 * @param parent - the parent of the current position in the tree
	 * @return true if the value of the node exists in the tree, false otherwise
	 */
	private boolean recursiveDelete(int number, Node position, Node parent) {
		//check middle child, or at value
		if (number == position.getValue()) {
			if (position.getMiddleChild() != null) {
				return recursiveDelete(number, position.getMiddleChild(), position);
			}
			// we are at value, so delete -- note no middle children for current position at this point
			else {
				Node replacement = null;
				Random rnd = new Random();
				//this boolean variable determine whether we choose inorder predecessor or inorder successor
				boolean successor = rnd.nextBoolean();
				
				// if there in only a left node, delete current item, replace with left node
				if (position.getRightChild() == null) {
					replacement = position.getLeftChild();
				}
				//if there is only a right node, delete current item, replace with right node
				else if (position.getLeftChild() == null) {
					replacement = position.getRightChild();
				}
				//replace with inorder successor
				//if right child has no left child, delete current item, replace with right node, change left pointer
				else if (position.getRightChild().getLeftChild() == null && successor) {
					position.getRightChild().setLeftChild(position.getLeftChild());
					replacement = position.getRightChild();
				}
				//replace with inorder successor
				//right child has a left child, replace current item with inorder successor, delete inorder successor
				else if (position.getRightChild().getLeftChild() != null && successor) {
					Node successorParent = position;
					Node successorPosition = position.getRightChild();
					while (successorPosition.getLeftChild() != null) {
						successorParent = successorPosition;
						successorPosition = successorPosition.getLeftChild();
					}
					//replace current position with InorderSuccessor
					Node successorRightNode = successorPosition.getRightChild();
					replacement = successorPosition;
					replacement.setRightChild(position.getRightChild());
					replacement.setLeftChild(position.getLeftChild());
					//delete InorderSuccessor
					readjustTree(successorParent, successorPosition, successorRightNode);
				}
				//replace with inorder predecessor
				//if left child has no right child, delete current item, replace with left child, change right pointer
				else if (position.getLeftChild().getRightChild() == null && !successor) {
					position.getLeftChild().setRightChild(position.getRightChild());
					replacement = position.getLeftChild();
				}
				//replace with inorder predecessor
				//left child has a right child, replace current item with inorder predecessor, delete inorder predecessor
				else if (position.getLeftChild().getRightChild() != null && !successor) {
					Node predecessorParent = position;
					Node predecessorPosition = position.getLeftChild();
					while (predecessorPosition.getRightChild() != null) {
						predecessorParent = predecessorPosition;
						predecessorPosition = predecessorPosition.getRightChild();
					}
					//replace current position with InorderPredecessor
					Node predecessorLeftNode = predecessorPosition.getLeftChild();
					replacement = predecessorPosition;
					replacement.setRightChild(position.getRightChild());
					replacement.setLeftChild(position.getLeftChild());
					//delete InorderPredecessor
					readjustTree(predecessorParent, predecessorPosition, predecessorLeftNode);
				}
				else {
					System.err.println("ERROR - should never get here, right and left children nodes exhausted in delete");
					return false;
				}
				// replace current position with replacement node
				if (parent == null) {
					root = replacement;
					return true;
				}
				else {
					return readjustTree(parent, position, replacement);
				}
			}
		}
		//check left child
		else if (number < position.getValue()) {
			if (position.getLeftChild() == null) {
				return false;
			}
			else {
				return recursiveDelete(number, position.getLeftChild(), position);
			}
		}
		//check right child
		else if (number > position.getValue()) {
			if (position.getRightChild() == null) {
				return false;
			}
			else {
				return recursiveDelete(number, position.getRightChild(), position);
			}
		}
		else {
			System.err.println("Error: should never get here, number !comparable to position.getValue in delete");
			return false;
		} 
	}
	
	/**
	 * This function recursively searches the tree for the correct position and insert the node
	 * @param number - the value to be inserted at the node
	 * @param position - the current position in tree
	 */
	private void recursiveInsert(int number, Node position) {
		Node newNode = new Node(number, null, null, null);
		//check left child and insert if null, otherwise recurse
		if (number < position.getValue()) {
			if (position.getLeftChild() == null) {
				position.setLeftChild(newNode);
			}
			else {
				recursiveInsert(number, position.getLeftChild());
			}
		}
		// check middle child and insert if null, otherwise recurse
		else if (number == position.getValue()) {
			if (position.getMiddleChild() == null) {
				position.setMiddleChild(newNode);
			}
			else {
				recursiveInsert(number, position.getMiddleChild());
			}
		}
		//check right child and insert if null, otherwise recurse
		else if (number > position.getValue()) {
			if (position.getRightChild() == null) {
				position.setRightChild(newNode);
			}
			else {
				recursiveInsert(number, position.getRightChild());
			}
		}
		else {
			System.err.println("Error: should never get here, number !comparable to position.getValue in insert");
		}
	}
	
	/**
	 * This function finds the correct direction the parent goes to the position node, and replaces the position with changeIntoThis
	 * @param parent - the parent node
	 * @param position - the current position in tree
	 * @param changeIntoThis - the node to swap into current position
	 * @return should also be true
	 */
	private boolean readjustTree(Node parent, Node position, Node changeIntoThis) {
		if (parent.getLeftChild() == position) {
			parent.setLeftChild(changeIntoThis);
			return true;
		}
		else if (parent.getRightChild() == position) {
			parent.setRightChild(changeIntoThis);
			return true;
		}
		else if (parent.getMiddleChild() == position) {
			parent.setMiddleChild(changeIntoThis);
			return true;
		}
		else {
			System.err.println("Error - should never get here, parent does not match current position in delete");
			return false;
		}
	}
	
	/**
	 * This recursive depth first search is for printing out the tree for testing purpose
	 * @param position current position
	 * @param sb the string to build
	 */
	private void recursiveDfs(Node position, StringBuilder sb) {
		sb.append(position.getValue());
		if (position.getLeftChild() != null) {
			sb.append("-L-");
			recursiveDfs(position.getLeftChild(), sb);
		}
		if (position.getMiddleChild() != null) {
			sb.append("-M-");
			recursiveDfs(position.getMiddleChild(), sb);
		}
		if (position.getRightChild() != null) {
			sb.append("-R-");
			recursiveDfs(position.getRightChild(), sb);
		}
	}
}
