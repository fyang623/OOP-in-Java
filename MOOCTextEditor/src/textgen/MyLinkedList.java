package textgen;

import java.util.AbstractList;


/** A class that implements a doubly linked list
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;

	/** Create a new empty LinkedList */
	public MyLinkedList() {
		// TODO: Implement this method
		head = new LLNode<E>(null);
		tail = new LLNode<E>(null);
		head.next = tail;
		head.prev = null;
		tail.prev = head;
		tail.next = null;
		size = 0;
	}

	/**
	 * Appends an element to the end of the list
	 * @param element The element to add
	 */
	public boolean add(E element) 
	{
		// TODO: Implement this method
		if (element == null) throw new NullPointerException("NullPointer!");
		LLNode<E> nodeToAdd = new LLNode<E>(element);
		nodeToAdd.prev = tail.prev;
		nodeToAdd.next = tail;
		nodeToAdd.next.prev = nodeToAdd;
		nodeToAdd.prev.next = nodeToAdd;
		size++;	
		return true;
	}

	/** Get the element at position index 
	 * @throws IndexOutOfBoundsException if the index is out of bounds. */
	public E get(int index) 
	{
		// TODO: Implement this method.
		if (index <0 || index > size-1) {
			throw new IndexOutOfBoundsException("IndexOutOfBounds!");
		}
		LLNode<E> current = head;
		for (int i=0; i< index; i++)
				current = current.next;
		return current.next.data;
	}
	
	public LLNode<E> getNode(int index) {
		if (index <0 || index > size-1) {
			throw new IndexOutOfBoundsException("IndexOutOfBounds!");
		}
		LLNode<E> current = head;
		for (int i=0; i< index; i++)
				current = current.next;
		return current.next;
	}
	
	/**
	 * Add an element to the list at the specified index
	 * @param The index where the element should be added
	 * @param element The element to add
	 */
	public void add(int index, E element ) 
	{
		// TODO: Implement this method
		if (element == null) throw new NullPointerException("NullPointer!");

		if ((index <0 || index > size-1) && size != 0) 
			throw new IndexOutOfBoundsException("IndexOutOfBounds!");
		if (size == 0 && index == 0) {
			add(element);
			return;
		}
		LLNode<E> nodeToAdd = new LLNode<E>(element);
		LLNode<E> front = getNode(index).prev;
		LLNode<E> behind = getNode(index);
		nodeToAdd.prev = front;
		nodeToAdd.next = behind;
		nodeToAdd.next.prev = nodeToAdd;
		nodeToAdd.prev.next = nodeToAdd;
		size++;
	}


	/** Return the size of the list */
	public int size() 
	{
		// TODO: Implement this method
		return size;
	}

	/** Remove a node at the specified index and return its data element.
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 * 
	 */
	public E remove(int index) 
	{
		// TODO: Implement this method
		if (index <0 || index > size-1) 
			throw new IndexOutOfBoundsException("IndexOutOfBounds!");
		LLNode<E> node = getNode(index);
		node.next.prev = node.prev;
		node.prev.next = node.next;
		node.prev = null;
		node.next = null;
		size--;
		return node.data;
	}

	/**
	 * Set an index position in the list to a new element
	 * @param index The index of the element to change
	 * @param element The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	public E set(int index, E element) 
	{
		// TODO: Implement this method
		if (element == null) throw new NullPointerException("NullPointer!");
		if (index <0 || index > size-1) 
			throw new IndexOutOfBoundsException("IndexOutOfBounds!");
		LLNode<E> node = getNode(index);
		E removed = node.data;
		node.data = element;
		return removed;
	}   
}

class LLNode<E> 
{
	LLNode<E> prev;
	LLNode<E> next;
	E data;

	// TODO: Add any other methods you think are useful here
	// E.g. you might want to add another constructor

	public LLNode(E e) 
	{
		this.data = e;
		this.prev = null;
		this.next = null;
	}

}
