import java.util.Collection;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Your implementation of an AVL.
 *
 * @author Shuohang Zeng
 * @version 1.0
 * @userid szeng70
 * @GTID 903798569
 * <p>
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 * <p>
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class AVL<T extends Comparable<? super T>> {

    // Do not add new instance variables or modify existing ones.
    private AVLNode<T> root;
    private int size;

    /**
     * Constructs a new AVL.
     * <p>
     * This constructor should initialize an empty AVL.
     * <p>
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new AVL.
     * <p>
     * This constructor should initialize the AVL with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data can't be null");
        }
        size = 0;
        data.forEach(this::add);
    }

    /**
     * Adds the element to the tree.
     * <p>
     * Start by adding it as a leaf like in a regular BST and then rotate the
     * tree as necessary.
     * <p>
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     * <p>
     * Remember to recalculate heights and balance factors while going back
     * up the tree after adding the element, making sure to re-balance if
     * necessary.
     * <p>
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data can't be null");
        }
        root = addHelper(data, root);
    }

    /**
     * find height and balance factor
     *
     * @param node node that will get calculated
     */
    private void heightAndBFFinder(AVLNode<T> node) {
        int leftHeight = -1;
        int rightHeight = -1;
        if (node.getLeft() != null) {
            leftHeight = node.getLeft().getHeight();
        }
        if (node.getRight() != null) {
            rightHeight = node.getRight().getHeight();
        }
        node.setHeight(Math.max(leftHeight, rightHeight) + 1);
        node.setBalanceFactor(leftHeight - rightHeight);
    }

    /**
     * Helper method for add
     *
     * @param data data to be added
     * @param node root of a tree
     * @return node that is balanced
     */
    private AVLNode<T> addHelper(T data, AVLNode<T> node) {
        if (node == null) {
            size++;
            return new AVLNode<>(data);
        }
        int compareData = data.compareTo(node.getData());
        if (compareData < 0) {
            node.setLeft(addHelper(data, node.getLeft()));
        } else if (compareData > 0) {
            node.setRight(addHelper(data, node.getRight()));
        } else {
            return node;
        }
        heightAndBFFinder(node);
        return balance(node);
    }

    /**
     * Balances the node by rotation
     *
     * @param node the node of a tree that will get balanced
     * @return node that is balanced
     */
    private AVLNode<T> balance(AVLNode<T> node) {
        if (node.getBalanceFactor() < -1) {
            if (node.getRight().getBalanceFactor() > 0) {
                node.setRight(rotate(node.getRight(), "right"));
            }
            node = rotate(node, "left");
        } else if (node.getBalanceFactor() > 1) {
            if (node.getLeft().getBalanceFactor() < 0) {
                node.setLeft(rotate(node.getLeft(), "left"));
            }
            node = rotate(node, "right");
        }
        return node;
    }

    /**
     * Rotates a part of a tree
     *
     * @param node the node of a tree that will get rotated
     * @param orientation the orientation to rotate
     * @return node that is rotated
     */
    private AVLNode<T> rotate(AVLNode<T> node, String orientation) {
        AVLNode<T> tmp;
        if (orientation.equals("left")) {
            tmp = node.getRight();
            node.setRight(tmp.getLeft());
            tmp.setLeft(node);
        } else {
            tmp = node.getLeft();
            node.setLeft(tmp.getRight());
            tmp.setRight(node);
        }
        heightAndBFFinder(node);
        heightAndBFFinder(tmp);
        return tmp;
    }


    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     * <p>
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the predecessor to
     * replace the data, NOT successor. As a reminder, rotations can occur
     * after removing the predecessor node.
     * <p>
     * Remember to recalculate heights and balance factors while going back
     * up the tree after removing the element, making sure to re-balance if
     * necessary.
     * <p>
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     * <p>
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not found
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data can't be null");
        }
        AVLNode<T> removed = new AVLNode<>(null);
        root = removeHelper(data, root, removed);
        return removed.getData();
    }

    /**
     * Helper method for remove(T data)
     *
     * @param data        data to search for
     * @param currentNode root node to inspect
     * @param removed     node that will store removed data
     * @return parent node of node that will get removed
     */
    private AVLNode<T> removeHelper(T data, AVLNode<T> currentNode, AVLNode<T> removed) {
        //BASE CASE: Data is not presented in the tree
        if (currentNode == null) {
            throw new NoSuchElementException("Data is not found");
        } else {
            int compareValue = data.compareTo(currentNode.getData());
            if (compareValue > 0) {
                currentNode.setRight(removeHelper(data, currentNode.getRight(), removed));
            } else if (compareValue < 0) {
                currentNode.setLeft(removeHelper(data, currentNode.getLeft(), removed));
            } else {
                //BASE CASE: current node has no children
                removed.setData(currentNode.getData());
                size--;
                if (currentNode.getLeft() == null) {
                    return currentNode.getRight();
                } else if (currentNode.getRight() == null) {
                    return currentNode.getLeft();
                } else if (currentNode.getRight() != null && currentNode.getLeft() != null) {
                    AVLNode<T> child = new AVLNode<>(null);
                    currentNode.setLeft(predecessorHelper(currentNode.getLeft(), child));
                    currentNode.setData(child.getData());
                }
            }
        }
        heightAndBFFinder(currentNode);
        return balance(currentNode);
    }

    /**
     * Finds predecessor of node
     *
     * @param currentNode the node to inspect
     * @param temp        the temporary node to later replace current node
     * @return predecessor node of an element that will be removed
     */
    private AVLNode<T> predecessorHelper(AVLNode<T> currentNode, AVLNode<T> temp) {
        if (currentNode.getRight() == null) {
            temp.setData(currentNode.getData());
            return currentNode.getLeft();
        }
        currentNode.setRight(predecessorHelper(currentNode.getRight(), temp));
        heightAndBFFinder(currentNode);
        return balance(currentNode);
    }

    /**
     * Returns the element from the tree matching the given parameter.
     * <p>
     * Hint: Should you use value equality or reference equality?
     * <p>
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * @param data the data to search for in the tree
     * @return the data in the tree equal to the parameter
     * @throws IllegalArgumentException if data is null
     * @throws NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data can't be null");
        }
        return getHelper(data, root);
    }

    /**
     * Helper method for get(T data)
     *
     * @param data the data to search for
     * @param currentNode the root node to inspect
     * @return data of a node that matches passed in parameter
     */
    private T getHelper(T data, AVLNode<T> currentNode) {
        if (currentNode == null) {
            throw new NoSuchElementException("Data is not found");
        }
        int compareData = data.compareTo(currentNode.getData());
        if (compareData > 0) {
            return getHelper(data, currentNode.getRight());
        } else if (compareData < 0) {
            return getHelper(data, currentNode.getLeft());
        } else {
            return currentNode.getData();
        }
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     * <p>
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to search for in the tree.
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data can't be null");
        }
        return containHelper(data, root);
    }
    /**
     * Helper method for contains(T data)
     *
     * @param data the data to search for
     * @param currentNode the node to inspect
     * @return data of a node that matches passed in parameter
     */
    private boolean containHelper(T data, AVLNode<T> currentNode) {
        if (currentNode == null) {
            return false;
        }
        int compareData = data.compareTo(currentNode.getData());
        if (compareData > 0) {
            return containHelper(data, currentNode.getRight());
        } else if (compareData < 0) {
            return containHelper(data, currentNode.getLeft());
        } else {
            return true;
        }
    }

    /**
     * Returns the height of the root of the tree.
     * <p>
     * Should be O(1).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (root == null) {
            return -1;
        }
        return root.getHeight();
    }

    /**
     * Clears the tree.
     * <p>
     * Clears all data and resets the size.
     */
    public void clear() {
        size = 0;
        root = null;
    }

    /**
     * The predecessor is the largest node that is smaller than the current data.
     * <p>
     * Should be recursive.
     * <p>
     * This method should retrieve (but not remove) the predecessor of the data
     * passed in. There are 2 cases to consider:
     * 1: The left subtree is non-empty. In this case, the predecessor is the
     * rightmost node of the left subtree.
     * 2: The left subtree is empty. In this case, the predecessor is the lowest
     * ancestor of the node containing data whose right child is also
     * an ancestor of data.
     * <p>
     * This should NOT be used in the remove method.
     * <p>
     * Ex:
     * Given the following AVL composed of Integers
     * 76
     * /    \
     * 34      90
     * \    /
     * 40  81
     * predecessor(76) should return 40
     * predecessor(81) should return 76
     *
     * @param data the data to find the predecessor of
     * @return the predecessor of data. If there is no smaller data than the
     * one given, return null.
     * @throws IllegalArgumentException if the data is null
     * @throws NoSuchElementException   if the data is not in the tree
     */
    public T predecessor(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data can't be null");
        }
        ArrayList<String> route = new ArrayList<>();
        return predecessorHelper(data, root, route);
    }

    /**
     * Helper method for predecessor(T data)
     *
     * @param data  the data to search for
     * @param currentNode the node to inspect
     * @param route the route it takes to find the data
     * @return data of a node that matches passed in parameter
     */
    private T predecessorHelper(T data, AVLNode<T> currentNode, ArrayList<String> route) {
        if (currentNode == null) {
            throw new NoSuchElementException("Data is not found");
        }
        int compareData = data.compareTo(currentNode.getData());
        if (compareData > 0) {
            route.add("right");
            return predecessorHelper(data, currentNode.getRight(), route);
        } else if (compareData < 0) {
            route.add("left");
            return predecessorHelper(data, currentNode.getLeft(), route);
        } else {
            if (currentNode.getLeft() != null) {
                currentNode = currentNode.getLeft();
                while (currentNode.getRight() != null) {
                    currentNode = currentNode.getRight();
                }
                return currentNode.getData();
            } else if (route.size() == 0) {
                return null;
            } else if (route.get(route.size() - 1).equals("right")) {
                AVLNode<T> tempNode = root;
                for (int i = 0; i < route.size() - 1; i++) {
                    if (route.get(i).equals("left")) {
                        tempNode = tempNode.getLeft();
                    } else {
                        tempNode = tempNode.getRight();
                    }
                }
                return tempNode.getData();
            } else if ((route.get(route.size() - 2).equals("right")) && route.get(route.size() - 1).equals("left")) {
                AVLNode<T> tempNode = root;
                for (int i = 0; i < route.size() - 2; i++) {
                    if (route.get(i).equals("left")) {
                        tempNode = tempNode.getLeft();
                    } else {
                        tempNode = tempNode.getRight();
                    }
                }
                return tempNode.getData();
            }
        }
        return null;
    }

    /**
     * Returns the data in the deepest node. If there is more than one node
     * with the same deepest depth, return the rightmost (i.e. largest) node with
     * the deepest depth.
     * <p>
     * Should be recursive.
     * <p>
     * Must run in O(log n) for all cases.
     * <p>
     * Example
     * Tree:
     * 2
     * /    \
     * 0      3
     * \
     * 1
     * Max Deepest Node:
     * 1 because it is the deepest node
     * <p>
     * Example
     * Tree:
     * 2
     * /    \
     * 0      4
     * \    /
     * 1  3
     * Max Deepest Node:
     * 3 because it is the maximum deepest node (1 has the same depth but 3 > 1)
     *
     * @return the data in the maximum deepest node or null if the tree is empty
     */
    public T maxDeepestNode() {
        if (root == null) {
            return null;
        }
        return maxDeepestNodeHelper(root);
    }

    /**
     * Helper method for predecessor(T data)
     *
     * @param currentNode the node to inspect
     * @return the data of the node that matches passed in parameter
     */
    private T maxDeepestNodeHelper(AVLNode<T> currentNode) {
        if (currentNode.getRight() == null && currentNode.getLeft() == null) {
            return currentNode.getData();
        } else if (currentNode.getBalanceFactor() > 0) {
            return maxDeepestNodeHelper(currentNode.getLeft());
        } else if (currentNode.getBalanceFactor() < 0) {
            return maxDeepestNodeHelper(currentNode.getRight());
        } else {
            return maxDeepestNodeHelper(currentNode.getRight());
        }
    }

    /**
     * Returns the root of the tree.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
