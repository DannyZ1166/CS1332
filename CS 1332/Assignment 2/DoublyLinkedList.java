/**
 * Your implementation of a non-circular DoublyLinkedList with a tail pointer.
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
public class DoublyLinkedList<T> {

    // Do not add new instance variables or modify existing ones.
    private DoublyLinkedListNode<T> head;
    private DoublyLinkedListNode<T> tail;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the element to the specified index. Don't forget to consider whether
     * traversing the list from the head or tail is more efficient!
     *
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new element
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("index needs to be in the range between 0 and the size" +
                    "\n current size: " + index);
        } else if (data == null) {
            throw new IllegalArgumentException("data cannot be null");
        } else {
            if (index == 0) {
                addToFront(data);
            } else if (index == size) {
                addToBack(data);
            } else {
                if (index < size / 2) {
                    DoublyLinkedListNode<T> current = head;
                    for (int i = 0; i < index; i++) {
                        current = current.getNext();
                    }
                    DoublyLinkedListNode<T> temp = new DoublyLinkedListNode(data);
                    current.getPrevious().setNext(temp);
                    temp.setPrevious(current.getPrevious());
                    current.setPrevious(temp);
                    temp.setNext(current);
                    size++;
                } else {
                    DoublyLinkedListNode<T> current = tail;
                    for (int i = size - 1; i > index; i--) {
                        current = current.getPrevious();
                    }
                    DoublyLinkedListNode<T> temp = new DoublyLinkedListNode(data);
                    current.getPrevious().setNext(temp);
                    temp.setPrevious(current.getPrevious());
                    current.setPrevious(temp);
                    temp.setNext(current);
                    size++;
                }
            }
        }
    }

    /**
     * Adds the element to the front of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data cannot be null");
        } else {
            if (head == null) {
                head = new DoublyLinkedListNode(data);
                tail = head;
                size = 1;
            } else {
                head.setPrevious(new DoublyLinkedListNode(data, null, head));
                head = head.getPrevious();
                size++;
            }
        }
    }

    /**
     * Adds the element to the back of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data cannot be null");
        } else {
            if (head == null) {
                head = new DoublyLinkedListNode(data);
                tail = head;
                size = 1;
            } else {
                tail.setNext(new DoublyLinkedListNode(data, tail, null));
                tail = tail.getNext();
                size++;
            }
        }
    }

    /**
     * Removes and returns the element at the specified index. Don't forget to
     * consider whether traversing the list from the head or tail is more
     * efficient!
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index needs to be in the range between 0 and the size" +
                    "\n current size: " + index);
        } else {
            if (index == 0) {
                return removeFromFront();
            } else if (index == size - 1) {
                return removeFromBack();
            } else {
                if (index < size / 2) {
                    DoublyLinkedListNode<T> current = head;
                    for (int i = 0; i < index; i++) {
                        current = current.getNext();
                    }
                    DoublyLinkedListNode<T> temp = current;
                    current.getPrevious().setNext(current.getNext());
                    current.getNext().setPrevious(current.getPrevious());
                    size--;
                    return temp.getData();
                } else {
                    DoublyLinkedListNode<T> current = tail;
                    for (int i = size - 1; i > index; i--) {
                        current = current.getPrevious();
                    }
                    DoublyLinkedListNode<T> temp = current;
                    current.getPrevious().setNext(current.getNext());
                    current.getNext().setPrevious(current.getPrevious());
                    size--;
                    return temp.getData();
                }
            }
        }
    }

    /**
     * Removes and returns the first element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("the list is empty");
        } else {
            if (size == 1) {
                DoublyLinkedListNode<T> temp = head;
                head = null;
                tail = null;
                size = 0;
                return temp.getData();
            } else {
                DoublyLinkedListNode<T> temp = head;
                head = head.getNext();
                head.setPrevious(null);
                size--;
                return temp.getData();
            }
        }
    }

    /**
     * Removes and returns the last element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("the list is empty");
        } else {
            if (size == 1) {
                DoublyLinkedListNode<T> temp = head;
                head = null;
                tail = null;
                size = 0;
                return temp.getData();
            }
            DoublyLinkedListNode<T> temp = tail;
            tail = tail.getPrevious();
            tail.setNext(null);
            size--;
            return temp.getData();
        }
    }

    /**
     * Returns the element at the specified index. Don't forget to consider
     * whether traversing the list from the head or tail is more efficient!
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index needs to be in the range between 0 and the size" +
                    "\n current size: " + index);
        } else {
            if (index == 0) {
                return head.getData();
            } else if (index == size - 1) {
                return tail.getData();
            } else {
                if (index < size / 2) {
                    DoublyLinkedListNode<T> current = head;
                    for (int i = 0; i < index; i++) {
                        current = current.getNext();
                    }

                    return current.getData();
                } else {
                    DoublyLinkedListNode<T> current = tail;
                    for (int i = size - 1; i > index; i--) {
                        current = current.getPrevious();
                    }
                    return current.getData();
                }
            }
        }
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Clears the list.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     *
     * Must be O(1) if data is in the tail and O(n) for all other cases.
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data cannot be null");
        } else if (isEmpty()) {
            throw new java.util.NoSuchElementException("if the list is empty");
        } else if (size == 1) {
            if (head.getData().equals(data)) {
                T temp = head.getData();
                head = null;
                tail = null;
                size = 0;
                return temp;
            }
            throw new java.util.NoSuchElementException("data is not found \n data entered: " + data.toString());
        } else {
            {
                DoublyLinkedListNode<T> current = tail;
                T temp;
                for (int i = size - 1; i >= 0; i--) {
                    if (current.getData().equals(data)) {
                        if (i == size - 1) {
                            temp = removeFromBack();
                        } else if (i == 0) {
                            temp = removeFromFront();
                        } else {
                            temp = current.getData();
                            current.getPrevious().setNext(current.getNext());
                            current.getNext().setPrevious(current.getPrevious());
                            size--;
                        }
                        return temp;
                    }
                    current = current.getPrevious();
                }
                throw new java.util.NoSuchElementException("data is not found \n data entered: " + data.toString());
            }
        }
    }

    /**
     * Returns an array representation of the linked list. If the list is
     * size 0, return an empty array.
     *
     * Must be O(n) for all cases.
     *
     * @return an array of length size holding all of the objects in the
     * list in the same order
     */
    public Object[] toArray() {
        Object[] array = new Object[size];
        DoublyLinkedListNode<T> current = head;
        for (int i = 0; i < size; i++) {
            array[i] = current.getData();
            current = current.getNext();
        }
        return array;
    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public DoublyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the tail of the list
     */
    public DoublyLinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }

    /**
     * Returns the size of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }
}
