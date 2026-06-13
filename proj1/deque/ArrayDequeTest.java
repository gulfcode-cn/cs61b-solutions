package deque;

import org.junit.Test;
import static org.junit.Assert.*;
public class ArrayDequeTest {

    @Test
    public void addIsEmptySizeTest(){
        ArrayDeque<String> testAD_01 = new ArrayDeque<String>();

        assertTrue("A newly initialized Array Deque should be empty",testAD_01.isEmpty());
        testAD_01.addFirst("front");

        assertEquals(1,testAD_01.size());
        assertFalse("testAD_01 must now contain 1 item",testAD_01.isEmpty());

        testAD_01.addLast("middle");
        assertEquals(2,testAD_01.size());

        testAD_01.addLast("Last");
        assertEquals(3,testAD_01.size());

        System.out.println("print out deque");
        testAD_01.printDeque();
    }

    @Test
    public void addRemoveTest() {
        ArrayDeque<Integer> testAD_02 = new ArrayDeque<Integer>();
        assertTrue("testAD_02 must be empty upon initialization",testAD_02.isEmpty());

        testAD_02.addFirst(10);
        assertFalse("testAD_02 should contain 1 item",testAD_02.isEmpty());

        testAD_02.removeFirst();
        assertTrue("testAD_02 should be empty after removal",testAD_02.isEmpty());
    }

    @Test
    public void removeEmptyTest(){
        ArrayDeque<Integer> testAD_03 = new ArrayDeque<Integer>();
        testAD_03.addFirst(3);

        testAD_03.removeLast();
        testAD_03.removeFirst();
        testAD_03.removeLast();
        testAD_03.removeFirst();

        int size = testAD_03.size();
        String errorMsg = "  Bad size returned when removing from empty deque.\n";
        errorMsg +="  student size() return " + size + "\n";
        errorMsg += "  actual size() returned 0\n";

        assertEquals(errorMsg,0,size);
    }

    @Test
    public void emptyNullReturntest() {
        ArrayDeque<Integer> testAD_04 = new ArrayDeque<Integer>();

        boolean passed1 = false;
        boolean passed2 = false;
        assertEquals("Should return null when removeFirst is called on an empty Deque,",null,testAD_04.removeFirst());
        assertEquals("Should return null when removeLast is called on an empty Deque,",null,testAD_04.removeLast());
    }

    @Test
    public void removeThreeFirstTest(){
        ArrayDeque<Integer> testAD = new ArrayDeque<Integer>();

        for(int i = 0; i < 3; i++){
            testAD.addLast(i);
        }

        for(double i = 0; i < 3; i++){
            assertEquals(i, (double) testAD.removeFirst(),0.0);
        }
    }




    @Test
    public void bigArrayDequeTest(){
        ArrayDeque<Integer> testAD_05 = new ArrayDeque<Integer>();
        for(int i = 0; i < 1000000; i++){
            testAD_05.addLast(i);
        }

        for(double i = 0; i < 500000; i++){
            assertEquals("Should have the same value",i,(double) testAD_05.removeFirst(),0.0);
        }

        for(double i = 999999; i > 500000; i--){
            assertEquals("Should have the same value",i,(double) testAD_05.removeLast(),0.0);
        }
    }
}
