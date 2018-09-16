/**
 * 
 */
package textgen;

import static org.junit.Assert.*;
import java.util.LinkedList;
import org.junit.Before;
import org.junit.Test;

/**
 * @author UC San Diego MOOC team
 *
 */
public class MyLinkedListTester {

	private static final int LONG_LIST_LENGTH =10; 

	MyLinkedList<String> shortList;
	MyLinkedList<Integer> emptyList;
	MyLinkedList<Integer> longerList;
	MyLinkedList<Integer> list1;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		// Feel free to use these lists, or add your own
	    shortList = new MyLinkedList<String>();
		shortList.add("A");
		shortList.add("B");
		emptyList = new MyLinkedList<Integer>();
		longerList = new MyLinkedList<Integer>();
		for (int i = 0; i < LONG_LIST_LENGTH; i++)
		{
			longerList.add(i);
		}
		list1 = new MyLinkedList<Integer>();
		list1.add(65);
		list1.add(21);
		list1.add(42);
		
	}

	
	/** Test if the get method is working correctly.
	 */
	/*You should not need to add much to this method.
	 * We provide it as an example of a thorough test. */
	@Test
	public void testGet()
	{
		//test empty list, get should throw an exception
		try {
			emptyList.get(0);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
			
		}
		
		// test short list, first contents, then out of bounds
		assertEquals("Check first", "A", shortList.get(0));
		assertEquals("Check second", "B", shortList.get(1));
		
		try {
			shortList.get(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		try {
			shortList.get(2);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		// test longer list contents
		for(int i = 0; i<LONG_LIST_LENGTH; i++ ) {
			assertEquals("Check "+i+ " element", (Integer)i, longerList.get(i));
		}
		
		// test off the end of the longer array
		try {
			longerList.get(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		try {
			longerList.get(LONG_LIST_LENGTH);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		}
		
	}
	
	
	/** Test removing an element from the list.
	 * We've included the example from the concept challenge.
	 * You will want to add more tests.  */
	@Test
	public void testRemove()
	{
		int a = list1.remove(0);
		assertEquals("Remove: check a is correct ", 65, a);
		assertEquals("Remove: check element 0 is correct ", (Integer)21, list1.get(0));
		assertEquals("Remove: check size is correct ", 2, list1.size());
		//assertEquals("Remove: check linkage is correct", list1.getNode(0).prev, list1.head);
		
		// TODO: Add more tests here
		try {
			emptyList.remove(0);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
			
		}
		try {
			shortList.remove(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		try {
			shortList.remove(2);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		
		// test off the end of the longer array
		try {
			longerList.remove(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		try {
			longerList.remove(LONG_LIST_LENGTH);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		}
		
		a = longerList.remove(9);
		assertEquals("Remove: check a is correct ", 9, a);
		assertEquals("Remove: check element 8 is correct ", (Integer) 8, longerList.get(8));
		assertEquals("Remove: check size is correct ", 9, longerList.size());
		//assertEquals("Remove: check linkage is correct", longerList.tail.prev, longerList.getNode(8));

		a = longerList.remove(5);
		assertEquals("Remove: check a is correct ", 5, a);
		assertEquals("Remove: check element 5 is correct ", (Integer) 6, longerList.get(5));
		assertEquals("Remove: check size is correct ", 8, longerList.size());
		//assertEquals("Remove: check linkage is correct", longerList.getNode(5).prev, longerList.getNode(4));
	}
		

	
	/** Test adding an element into the end of the list, specifically
	 *  public boolean add(E element)
	 * */
	@Test
	public void testAddEnd()
	{
        // TODO: implement this test
			boolean a = emptyList.add(100);
			assertEquals("AddEnd: check return is correct", true, a);
			assertEquals("AddEnd: check size is correct", 1, emptyList.size());
			assertEquals("AddEnd: check element 0 is correct", (Integer) 100, emptyList.get(0));
			assertEquals("AddEnd: check linkage to head is correct", emptyList.head, emptyList.head.next.prev);
			a = shortList.add("abc");
			assertEquals("AddEnd: check return is correct", true, a);
			assertEquals("AddEnd: check size is correct", 3, shortList.size());
			assertEquals("AddEnd: check element 0 is correct", "abc", shortList.get(2));
			//assertEquals("AddEnd: check linkage to prev is correct", shortList.tail.prev.prev, shortList.getNode(1));
			a = longerList.add(100);
			assertEquals("AddEnd: check return is correct", true, a);
			assertEquals("AddEnd: check size is correct", 11, longerList.size());
			assertEquals("AddEnd: check element 0 is correct", (Integer) 100, longerList.get(10));
			//assertEquals("AddEnd: check linkage to prev is correct", longerList.getNode(9), longerList.tail.prev.prev);		
	}

	
	/** Test the size of the list */
	@Test
	public void testSize()
	{
		// TODO: implement this test
		assertEquals("check size is correct", emptyList.size(), 0);
		assertEquals("check size is correct", 10, longerList.size());
		assertEquals("check size is correct", 2, shortList.size());	
	}

	
	
	/** Test adding an element into the list at a specified index,
	 * specifically:
	 * public void add(int index, E element)
	 * */
	@Test
	public void testAddAtIndex()
	{
        // TODO: implement this test
		try {
			emptyList.add(-1, 100);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		try {
			emptyList.add(1, 100);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
			
		}
		try {
			shortList.add(-1, "abc");
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		try {
			shortList.add(2, "abc");
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		try {
			longerList.add(-1, 100);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		try {
			longerList.add(LONG_LIST_LENGTH, 100);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
			
		}
		shortList.add(0, "abc");
		assertEquals("AddAtIndex: check size is correct", 3, shortList.size());
		assertEquals("AddEnd: check element 0 is correct", "abc", shortList.get(0));
		//assertEquals("AddEnd: check linkage is correct", shortList.head.next.next.prev, shortList.getNode(0));
		//assertEquals("AddEnd: check linkage is correct", emptyList.tail.prev.prev, "B");
		shortList.add(2, "abc");
		assertEquals("AddAtIndex: check size is correct", 4, shortList.size());
		assertEquals("AddEnd: check element 2 is correct", "abc", shortList.get(2));
		//assertEquals("AddEnd: check linkage is correct", shortList.head.next.next, "A");
		longerList.add(5, 100);
		assertEquals("AddAtIndex: check size is correct", 11, longerList.size());
		assertEquals("AddEnd: check element 5 is correct", (Integer)100, longerList.get(5));
		//assertEquals("AddEnd: check linkage is correct", longerList.head.next.next, 0);
	}
	
	/** Test setting an element in the list */
	@Test
	public void testSet()
	{
	    // TODO: implement this test
		try {
			emptyList.set(0, 100);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
			
		}
		try {
			shortList.set(-1, "abc");
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		try {
			shortList.set(2, "abc");
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		
		// test off the end of the longer array
		try {
			longerList.set(-1, 100);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		try {
			longerList.set(LONG_LIST_LENGTH, 100);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		}
		assertEquals("Set first", "A", shortList.set(0, "abc"));
		assertEquals("Set first", "abc", shortList.get(0));
		assertEquals("Set second", "B", shortList.set(1, "def"));
		assertEquals("Set second", "def", shortList.get(1));
		for(int i = 0; i<LONG_LIST_LENGTH; i++ ) {
			assertEquals("Set "+i+ " element", (Integer) i, longerList.set(i, (Integer) i*3));
			Integer int1 = i*3;
			assertEquals("Set "+i+ " element", longerList.get(i), int1);
		}
	}
	
	
	// TODO: Optionally add more test methods.
	
}
