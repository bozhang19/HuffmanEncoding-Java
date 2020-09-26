import java.util.Arrays;

/**
 * This class represents a four way heap.
 *
 * @author Bo Zhang
 * @version 1.0
 */
public class FourWayHeap<T extends Comparable<T>> {
    private T[] heap;
    private int capacity = 10;
    private int size = 0; // number of elements in the array

    /**
     * Creates a four way heap with the default capacity.
     */
    @SuppressWarnings("unchecked")
    public FourWayHeap() {
        heap = (T[]) new Comparable[capacity];
    }

    /**
     * Creates a four way heap with the given array.
     *
     * @param elements  the given array
     */
    @SuppressWarnings("unchecked")
    public FourWayHeap(T[] elements) {
        heap = (T[]) new Comparable[elements.length];

        // build-heap routine
        for (T element : elements) {
            insert(element);
        }
    }

    /**
     * Adds the input element to the heap while
     * maintaining the heap ordering property.
     *
     * @param element  the new element to add
     */
    public void insert(T element) {
        // increase the capacity of the heap if the it is full
        if (size == heap.length) {
            increaseCapacity();
        }

        // put the new element at the last index of the heap array
        heap[size] = element;

        // update size
        size++;

        swim();
    }

    /**
     * Increases the capacity of the heap by two times.
     */
    @SuppressWarnings("unchecked")
    private void increaseCapacity() {
        T[] oldHeap = heap;
        capacity *= 2;
        heap = (T[]) new Comparable[capacity];

        System.arraycopy(oldHeap, 0, heap, 0, oldHeap.length);
    }

    /**
     *  Moves the element up the heap structure until it
     *  reaches to the right spot.
     */
    private void swim() {
        int index = size - 1;
        while (hasParent(index) && heap[index].compareTo(heap[getParentIndex(index)]) < 0) {
            swap(index, getParentIndex(index));
            index = getParentIndex(index); // update the index
        }
    }

    /**
     * Swaps the two elements in the heap.
     *
     * @param first  the first element's index
     * @param second  the second element's index
     */
    private void swap(int first, int second) {
        T temp = heap[first];
        heap[first] = heap[second];
        heap[second] = temp;
    }

    /**
     * Returns the minimum element in the heap.
     *
     * @return the minimum element or null
     */
    public T peek() {
        if (size == 0) {
            return null;
        }

        return heap[0];
    }

    /**
     * Removes and returns the minimum element in the heap.
     *
     * @return the minimum element or null
     */
    public T deleteMin() {
        if (isEmpty()) {
            return null;
        }

        T min = heap[0];
        swap(0, size - 1);
        heap[size - 1] = null;
        size--;

        // perform the sink operation
        sink();

        return min;
    }

    /**
     *  Moves down the element in the heap to the right spot.
     */
    private void sink() {
        int index = 0;
        int smallestChildIndex;
        while (hasLeftFirstChild(index) && heap[index].compareTo(heap[getLeftFirstChildIndex(index)]) > 0) {
            smallestChildIndex = getLeftFirstChildIndex(index);
            if (hasLeftSecondChild(index) && heap[smallestChildIndex].compareTo(heap[getLeftSecondChildIndex(index)]) > 0) {
                smallestChildIndex = getLeftSecondChildIndex(index);
            }

            if (hasRightFirstChild(index) && heap[smallestChildIndex].compareTo(heap[getRightFirstChildIndex(index)]) > 0) {
                smallestChildIndex = getRightFirstChildIndex(index);
            }

            if (hasRightSecondChild(index) && heap[smallestChildIndex].compareTo(heap[getRightSecondChildIndex(index)]) > 0) {
                smallestChildIndex = getRightSecondChildIndex(index);
            }

            swap(index, smallestChildIndex);
            index = smallestChildIndex;
        }
    }

    /**
     * Returns the index of the parent.
     *
     * @param index  the child's index
     * @return int  the parent's index
     */
    private int getParentIndex(int index) {
        return (index - 1) / 4;
    }

    /**
     * Checks if the child has a parent.
     *
     * @param index  the child's index
     * @return boolean   whether the index is greater than
     *                   or equals to zero, if it is, that means the child
     *                   does have a parent, otherwise it does not
     */
    private boolean hasParent(int index) {
        return getParentIndex(index) >= 0;
    }

    /**
     * Returns the index of the left first child.
     *
     * @param index  the parent's index
     * @return int  the left first child's index
     */
    private int getLeftFirstChildIndex(int index) {
        return (index * 4) + 1;
    }

    /**
     * Checks if the parent has a left first child.
     *
     * @param index  the parent's index
     * @return boolean  true if the left first child exists, false otherwise
     */
    private boolean hasLeftFirstChild(int index) {
        return getLeftFirstChildIndex(index) < size;
    }

    /**
     * Returns the index of the left second child.
     *
     * @param index  the parent's index
     * @return int  the left second child's index
     */
    private int getLeftSecondChildIndex(int index) {
        return (index * 4) + 2;
    }

    /**
     * Checks if the parent has a left second child.
     *
     * @param index  the parent's index
     * @return boolean  true if the left second child exists, false otherwise
     */
    private boolean hasLeftSecondChild(int index) {
        return getLeftSecondChildIndex(index) < size;
    }

    /**
     * Returns the index of the right first child.
     *
     * @param index  the parent's index
     * @return int  the right first child's index
     */
    private int getRightFirstChildIndex(int index) {
        return (index * 4) + 3;
    }

    /**
     * Checks if the parent has a right first child.
     *
     * @param index  the parent's index
     * @return boolean  true if the right first child exists, false otherwise
     */
    private boolean hasRightFirstChild(int index) {
        return getRightFirstChildIndex(index) < size;
    }

    /**
     * Returns the index of the right second child.
     *
     * @param index  the parent's index
     * @return int  the right second child's index
     */
    private int getRightSecondChildIndex(int index) {
        return (index * 4) + 4;
    }

    /**
     * Checks if the right second child exists.
     *
     * @param index  the parent's index
     * @return boolean  true if right second child exists, false otherwise
     */
    private boolean hasRightSecondChild(int index) {
        return getRightSecondChildIndex(index) < size;
    }


    /**
     * Returns the size of the heap.
     *
     * @return the size
     */
    public int size() {
        return size;
    }

    /**
     * Checks if the heap is empty.
     *
     * @return true if the heap is empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Removes all elements from the heap.
     */
    @SuppressWarnings("unchecked")
    public void clear() {
        heap = (T[]) new Comparable[capacity];
        size = 0;
    }

    /**
     * Checks if the search element is in the heap.
     *
     * @param element  the element to search
     * @return true if the element is in the heap, false otherwise
     */
    public boolean contains(T element) {
        for (T heapElement : heap) {
            if (heapElement != null && heapElement == element) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a string representation of the heap.
     *
     * @return a string
     */
    @Override
    public String toString() {
        return "FourWayHeap{" +
                "heap=" + Arrays.toString(heap) +
                ", capacity=" + capacity +
                ", size=" + size +
                '}';
    }
}
