
import java.util.*;

// A Map ADT structure using a red-black tree, where keys must implement
// Comparable.
// The key type of a map must be Comparable. Values can be any type.
public class RedBlackTreeMap<TKey extends Comparable<TKey>, TValue> {
	// A Node class.
	private class Node {
		private TKey mKey;
		private TValue mValue;
		private Node mParent;
		private Node mLeft;
		private Node mRight;
		private boolean mIsRed;

		// Constructs a new node with NIL children.
		public Node(TKey key, TValue data, boolean isRed) {
			mKey = key;
			mValue = data;
			mIsRed = isRed;

			mLeft = NIL_NODE;
			mRight = NIL_NODE;
		}

		@Override
		public String toString() {
			return mKey + " : " + mValue + " (" + (mIsRed ? "red)" : "black)");
		}
	}

	private Node mRoot;
	private int mCount;

	// Rather than create a "blank" black Node for each NIL, we use one shared
	// node for all NIL leaves.
	private final Node NIL_NODE = new Node(null, null, false);

	//////////////////// I give you these utility functions for free.

	// Get the # of keys in the tree.
	public int getCount() {
		return mCount;
	}

	// Finds the value associated with the given key.
	public TValue find(TKey key) {
		Node n = bstFind(key, mRoot); // find the Node containing the key if any
		if (n == null || n == NIL_NODE)
			throw new RuntimeException("Key not found");
		return n.mValue;
	}

	/////////////////// You must finish the rest of these methods.

	// Inserts a key/value pair into the tree, updating the red/black balance
	// of nodes as necessary. Starts with a normal BST insert, then adjusts.
	public void add(TKey key, TValue data) {
		Node n = new Node(key, data, true); // nodes start red

		// normal BST insert; n will be placed into its initial position.
		// returns false if an existing node was updated (no rebalancing needed)
		boolean insertedNew = bstInsert(n, mRoot);
		if (!insertedNew)
			return;

		// check cases 1-5 for balance violations.
		checkBalance(n);
	}

	// Applies rules 1-5 to check the balance of a tree with newly inserted
	// node n.
	private void checkBalance(Node n) {
		Node uncle=getUncle(n);
		Node grandparent = getGrandparent(n);
		if (n == mRoot) {
			// case 1: new node is root.
			n.mIsRed = false;
			return;
		} else {
			if (n.mParent.mIsRed==false) {
				//case 2: mParent is black
				return;
			} else {
				if (n.mParent.mIsRed==true && uncle.mIsRed == true) {
					// case 3: Parent and uncle are red
					//uncle & parent painted black; grandparent painted red
	                uncle.mIsRed = false;
	                n.mParent.mIsRed = false;
	                grandparent.mIsRed = true;

	                //recursively fix these colors
	                checkBalance(uncle.mParent);
				} else {
					if (n.mParent == grandparent.mLeft && n == n.mParent.mRight) {
						// case 4: n is lr or rl grandchild of G
						singleRotateLeft(n.mParent);
						n = grandparent.mLeft.mLeft;
						
						//case 5:
						singleRotateRight(grandparent);
						n.mParent.mIsRed = false;
						grandparent.mIsRed = true;

					} else {
						//n is ll grandchild of G
							if (n.mParent == getGrandparent(n).mLeft && n == n.mParent.mLeft) {
								
								 singleRotateRight(n.mParent);
			                        n = grandparent.mRight.mRight;
			                        
			                        singleRotateLeft(grandparent);
			                        n.mParent.mIsRed = false;
			                        grandparent.mIsRed = true;


							} 
					}
				}
			}
		}
		

		// handle additional insert cases here.

	}

	// Returns true if the given key is in the tree.
	public boolean containsKey(TKey key) {
		// TODO: using at most three lines of code, finish this method.
		// HINT: write the bstFind method first.
		Node a=bstFind(key, mRoot);
		if (a.mKey==key) {
			return true;
		} else {
			return false;
		}
	}

	// Prints a pre-order traversal of the tree's nodes, printing the key, value,
	// and color of each node.
	public void printStructure() {
		// TODO: a pre-order traversal. Will need recursion.
		printStructure(mRoot);
	}
    
	private void printStructure(Node temp) {
		if (temp==null || temp.equals(NIL_NODE)) {
			System.out.println();
		} else {
			System.out.println(temp);
		}
		printStructure(temp.mLeft);
		printStructure(temp.mRight);
	}
	
	// Retuns the Node containing the given key. Recursive.
	private Node bstFind(TKey key, Node currentNode) {
		// TODO: write this method. Given a key to find and a node to start at,
		// proceed left/right from the current node until finding a node whose
		// key is equal to the given key.
		if (mRoot!=null) {
			if (currentNode.mKey.equals(key)) {
				return currentNode;
			} else {
				if (currentNode.mLeft != null) {
					return bstFind(key, currentNode.mLeft);
				} else {
					if (currentNode.mRight != null) {
						return bstFind(key, currentNode.mRight);
					}
				}
			}
		}
		return null;
	}



	//////////////// These functions are needed for insertion cases.

	// Gets the grandparent of n.
	private Node getGrandparent(Node n) {
		// TODO: return the grandparent of n
		if (n==mRoot) {
			return null;
		} else {
			return n.mParent.mParent;
		}
		
	}

	// Gets the uncle (parent's sibling) of n.
	private Node getUncle(Node n) {
		// TODO: return the uncle of n
		//return null;
		Node temp= getGrandparent(n);
		if (temp==null) {
			return null;
		} else {
			if (n.mParent==temp.mRight) {
				return temp.mLeft;
			} else {
				return temp.mRight;
			}
		}
	}

	// Rotate the tree right at the given node.
	private void singleRotateRight(Node n) {
		Node l = n.mLeft, lr = l.mRight, p = n.mParent;
		n.mLeft = lr;
		lr.mParent = n;
		l.mRight = n;
		n.mParent = l;

		if (p == null) { // n is root
			mRoot = l;
		}
		else if (p.mLeft == n) {
			p.mLeft = l;
		} 
		else {
			p.mRight = l;
		}
		
		l.mParent = p;
	}

	// Rotate the tree left at the given node.
	private void singleRotateLeft(Node n) {
		// TODO: do a single left rotation (AVL tree calls this a "rr" rotation)
		// at n.
		Node r = n.mRight, rl = r.mLeft, p = n.mParent;
		n.mRight = rl;
		rl.mParent = n;
		r.mLeft = n;
		n.mParent = r;

		if (p == null) { // n is root
			mRoot = r;
		}
		else if (p.mLeft == n) {
			p.mLeft = r;
		} 
		else {
			p.mRight = r;
		}
		
		r.mParent = p;

	}


	// This method is used by insert. It is complete.
	// Inserts the key/value into the BST, and returns true if the key wasn't
	// previously in the tree.
	private boolean bstInsert(Node newNode, Node currentNode) {
		if (mRoot == null) {
			// case 1
			mRoot = newNode;
			return true;
		} 
		else {
			int compare = currentNode.mKey.compareTo(newNode.mKey);
			if (compare < 0) {
				// newNode is larger; go right.
				if (currentNode.mRight != NIL_NODE) {
					return bstInsert(newNode, currentNode.mRight);
				}
				else {
					currentNode.mRight = newNode;
					newNode.mParent = currentNode;
					mCount++;
					return true;
				}
			} 
			else if (compare > 0) {
				if (currentNode.mLeft != NIL_NODE) {
					return bstInsert(newNode, currentNode.mLeft);
				}
				else {
					currentNode.mLeft = newNode;
					newNode.mParent = currentNode;
					mCount++;
					return true;
				}
			} 
			else {
				// found a node with the given key; update value.
				currentNode.mValue = newNode.mValue;
				return false; // did NOT insert a new node.
			}
		}
	}
}