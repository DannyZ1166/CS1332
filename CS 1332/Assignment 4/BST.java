import org.w3c.dom.Node;

import java.util.*;

/**
 * Your implementation of a BST.
 *
 * @author Shuohang Zeng
 * @version 1.0
 * @userid szeng70
 * @GTID 903798569
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null || data.contains(null)) {
            throw new IllegalArgumentException("Data can't be null");
        }
        size = 0;
        for (T tmp : data) {
            add(tmp);
        }
    }

    /**
     * Adds the data to the tree.
     *
     * This must be done recursively.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data can't be null");
        }
        if (root == null) {
            root = new BSTNode<>(data);
            size++;
        }
        addHelper(data, root);
    }

    /**
     * Recursive helper that traverses the tree and adds the data where it belongs
     *
     * @param data,       the data to add to the tree
     * @param currentNode, the node that is the current root of the (sub)tree
     * @return true if it was successfully added, false otherwise
     */
    private boolean addHelper(T data, BSTNode<T> currentNode) {
        if ((data.compareTo(currentNode.getData()) == 0)) {
            return false;
        }
        if ((data.compareTo(currentNode.getData()) < 0)) {
            if (currentNode.getLeft() != null) {
                return addHelper(data, currentNode.getLeft());
            } else {
                currentNode.setLeft(new BSTNode(data));
                size++;
                return true;
            }
        } else if ((data.compareTo(currentNode.getData()) > 0)) {
            if (currentNode.getRight() != null) {
                return addHelper(data, currentNode.getRight());
            } else {
                currentNode.setRight(new BSTNode(data));
                size++;
                return true;
            }
        }
        return false;
    }
    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data cannot be null");
        }
        BSTNode<T> removedNode = new BSTNode<>(null);
        root = removeHelper(data, root, removedNode);
        return removedNode.getData();
    }

    /**
     * Recursive helper method to that removes the data from this BST.
     *
     * @param data, the data to be removed from the tree
     * @param currentNode, the root of the tree we are removing from
     * @param removed, the Node being removed
     * @return parent node of node that will get removed
     */
    private BSTNode<T> removeHelper(T data, BSTNode<T> currentNode, BSTNode<T> removed) {

        //BASE CASE: Data is not presented in the tree
        if (currentNode == null) {
            throw new java.util.NoSuchElementException("Data is not found");
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
                if (currentNode.getRight() == null && currentNode.getLeft() == null) {
                    currentNode = null;
                }
                //BASE CASE: currentNode has 2 children
                else if (currentNode.getRight() != null && currentNode.getLeft() != null) {
                    BSTNode<T> tmp = new BSTNode<T>(null);
                    currentNode.setRight(successorHelper(currentNode.getRight(), tmp));
                    currentNode.setData(tmp.getData());
                }
                //BASE CASE: current node has one child (one for the left and right respectively)
                else if (currentNode.getRight() != null) {
                    currentNode = currentNode.getRight();
                } else if (currentNode.getLeft() != null) {
                    currentNode = currentNode.getLeft();
                }
            }
        }
        return currentNode;
    }

    /**
    * Helper method for removeHelper()
    * Finds successor node
    *
    * @param currentNode the current node
    * @return successor node of an element that will be removed
    */
    private BSTNode<T> successorHelper(BSTNode<T> currentNode, BSTNode<T> temp) {
        if (currentNode.getLeft() == null) {
            temp.setData(currentNode.getData());
            return currentNode.getRight();
        } else {
            currentNode.setLeft(successorHelper(currentNode.getLeft(), temp));
            return currentNode;
        }
    }

    /**
     * Returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data cannot be null");
        }
        return getHelper(data, root).getData();
    }

    /**
     * Recursive helper method to that get the data from this BST.
     *
     * @param data, the data to be getting from the tree
     * @param currentNode, the root of the tree we are getting from
     * @return data of the node being requested
     */
    private BSTNode<T> getHelper(T data, BSTNode<T> currentNode) {
        if (currentNode == null) {
            throw new java.util.NoSuchElementException("Data is not found");
        } else {
            int compareValue = data.compareTo(currentNode.getData());
            if (compareValue > 0) {
                return getHelper(data, currentNode.getRight());
            } else if (compareValue < 0) {
                return getHelper(data, currentNode.getLeft());
            } else {
                return currentNode;
            }
        }
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * This must be done recursively.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data cannot be null");
        }
        return containsHelper(data, root);
    }

    /**
     * Recursive helper method to check if data is in tree.
     *
     * @param data, the data to be checked from the tree
     * @param currentNode, the root of the tree we are checking
     * @return whether data is in tree
     */
    private boolean containsHelper(T data, BSTNode<T> currentNode) {
        if (currentNode == null) {
            return false;
        } else {
            int compareValue = data.compareTo(currentNode.getData());
            if (compareValue > 0) {
                return containsHelper(data, currentNode.getRight());
            } else if (compareValue < 0) {
                return containsHelper(data, currentNode.getLeft());
            } else {
                return true;
            }
        }
    }

    /**
     * Generate a pre-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        List<T> list = new ArrayList<T>();
        preorderHelper(list, root);
        return list;
    }

    /**
     * pre-order Helper
     *
     * @param list, arraylist holding the preorder traversal of the tree
     * @param currentNode, the root of the tree we are checking
     */
    private void preorderHelper(List<T> list, BSTNode<T> currentNode) {
        if (currentNode == null) {
            return;
        } else {
            list.add(currentNode.getData());
            preorderHelper(list, currentNode.getLeft());
            preorderHelper(list, currentNode.getRight());
        }
    }

    /**
     * Generate an in-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        List<T> list = new ArrayList<T>();
        inorderHelper(list, root);
        return list;
    }

    /**
     * in-order Helper
     *
     * @param list, arraylist holding the preorder traversal of the tree
     * @param currentNode, the root of the tree we are checking
     */
    private void inorderHelper(List<T> list, BSTNode<T> currentNode) {
        if (currentNode == null) {
            return;
        } else {
            inorderHelper(list, currentNode.getLeft());
            list.add(currentNode.getData());
            inorderHelper(list, currentNode.getRight());
        }
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        List<T> list = new ArrayList<T>();
        postorderHelper(list, root);
        return list;
    }

    /**
     * post-order Helper
     *
     * @param list, arraylist holding the preorder traversal of the tree
     * @param currentNode, the root of the tree we are checking
     */
    private void postorderHelper(List<T> list, BSTNode<T> currentNode) {
        if (currentNode == null) {
            return;
        } else {
            postorderHelper(list, currentNode.getLeft());
            postorderHelper(list, currentNode.getRight());
            list.add(currentNode.getData());
        }
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {

        Queue<BSTNode<T>> queue = new LinkedList<>();
        List<T> list = new ArrayList<>();
        if (size == 0) {
            return list;
        }
        queue.add(root);
        do {
            BSTNode<T> temp = queue.poll();
            list.add(temp.getData());
            if (temp.getLeft() != null) {
                queue.add(temp.getLeft());
            }
            if (temp.getRight() != null) {
                queue.add(temp.getRight());
            }
        } while (!queue.isEmpty());
        return list;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * This must be done recursively.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return heightHelper(root);
    }

    private int heightHelper(BSTNode<T> currentNode) {
        if (currentNode == null) {
            return -1;
        } else {
            return Math.max(heightHelper(currentNode.getLeft()), heightHelper(currentNode.getRight())) + 1;
        }
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Finds the path between two elements in the tree, specifically the path
     * from data1 to data2, inclusive of both.
     *
     * This must be done recursively.
     * 
     * A good way to start is by finding the deepest common ancestor (DCA) of both data
     * and add it to the list. You will most likely have to split off and
     * traverse the tree for each piece of data adding to the list in such a
     * way that it will return the path in the correct order without requiring any
     * list manipulation later. One way to accomplish this (after adding the DCA
     * to the list) is to then traverse to data1 while adding its ancestors
     * to the front of the list. Finally, traverse to data2 while adding its
     * ancestors to the back of the list. 
     *
     * Please note that there is no relationship between the data parameters 
     * in that they may not belong to the same branch. 
     * 
     * You may only use 1 list instance to complete this method. Think about
     * what type of list to use considering the Big-O efficiency of the list
     * operations.
     *
     * This method only needs to traverse to the deepest common ancestor once.
     * From that node, go to each data in one traversal each. Failure to do
     * so will result in a penalty.
     * 
     * If both data1 and data2 are equal and in the tree, the list should be
     * of size 1 and contain the element from the tree equal to data1 and data2.
     *
     * Ex:
     * Given the following BST composed of Integers
     *              50
     *          /        \
     *        25         75
     *      /    \
     *     12    37
     *    /  \    \
     *   11   15   40
     *  /
     * 10
     * findPathBetween(10, 40) should return the list [10, 11, 12, 25, 37, 40]
     * findPathBetween(50, 37) should return the list [50, 25, 37]
     * findPathBetween(75, 75) should return the list [75]
     *
     * Must be O(log n) for a balanced tree and O(n) for worst case.
     *
     * @param data1 the data to start the path from
     * @param data2 the data to end the path on
     * @return the unique path between the two elements
     * @throws IllegalArgumentException if either data1 or data2 is
     *                                            null
     * @throws java.util.NoSuchElementException   if data1 or data2 is not in
     *                                            the tree
     */
    public List<T> findPathBetween(T data1, T data2) {
        if (data1 == null || data2 == null) {
            throw new IllegalArgumentException("data1 and data2 cannot be null");
        } else if (!contains(data1) || !contains(data2)) {
            throw new java.util.NoSuchElementException("data values aren't in the tree");
        }
        LinkedList<T> list = new LinkedList<>();
        BSTNode<T> dca = DCAFinder(data1, data2, root);
        findPathBetweenHelper1(data1, dca, list);
        list.removeLast();
        findPathBetweenHelper2(data2, dca, list);
        return list;
    }

    /**
     * finds the DCA between two data points in a tree
     *
     * @param data1 the data to start the path from
     * @param data2 the data to end the path on
     * @param currentNode, the root of the tree we are checking
     * @return DCA of the two data points.
     */
    private BSTNode<T> DCAFinder(T data1, T data2, BSTNode<T> currentNode) {
        int compareValue1 = data1.compareTo(currentNode.getData());
        int compareValue2 = data2.compareTo(currentNode.getData());
        if (compareValue1 > 0 && compareValue2 > 0) {
            return DCAFinder(data1, data2, currentNode.getRight());
        } else if (compareValue1 < 0 && compareValue2 < 0) {
            return DCAFinder(data1, data2, currentNode.getLeft());
        } else {
            return currentNode;
        }
    }

    /**
     * enters the first half of the path from data to DCA to list.
     *
     * @param data the data to start the path from
     * @param currentNode, the root of the tree we are checking
     * @param list, unique path between the two elements
     */
    private void findPathBetweenHelper1(T data, BSTNode<T> currentNode, LinkedList<T> list) {
        int compareValue = data.compareTo(currentNode.getData());
        if (compareValue == 0) {
            list.addFirst(currentNode.getData());
            return;
        } else {
            list.addFirst(currentNode.getData());
            if (compareValue < 0) {
                findPathBetweenHelper1(data, currentNode.getLeft(), list);
            } else if (compareValue > 0) {
                findPathBetweenHelper1(data, currentNode.getRight(), list);
            }
        }
    }

    /**
     * enters the second half of the path from DCA to Data to list.
     *
     * @param data the data to start the path from
     * @param currentNode, the root of the tree we are checking
     * @param list, unique path between the two elements
     */
    private void findPathBetweenHelper2(T data, BSTNode<T> currentNode, LinkedList<T> list) {
        int compareValue = data.compareTo(currentNode.getData());
        if (compareValue == 0) {
            list.addLast(currentNode.getData());
            return;
        } else {
            list.addLast(currentNode.getData());
            if (compareValue < 0) {
                findPathBetweenHelper2(data, currentNode.getLeft(), list);
            } else if (compareValue > 0) {
                findPathBetweenHelper2(data, currentNode.getRight(), list);
            }
        }
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
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
