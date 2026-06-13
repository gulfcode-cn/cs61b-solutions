package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */

// YOUR TESTS HERE
public class TestBuggyAList {
    @Test
    public void testThreeAddThreeRemove() {
        BuggyAList<Integer> Buggytest = new BuggyAList<>();
        AListNoResizing<Integer> true0fOne=new AListNoResizing<>();
        for(int i=0;i<5;i++){
            Buggytest.addLast(i);
            true0fOne.addLast(i);
        }
        for(int j=0;j<3;j++){
            assertEquals(true0fOne.removeLast(),Buggytest.removeLast());
        }
    }
    @Test
    public void RandomTestDemo(){
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> test = new BuggyAList<>();
        int N = 50000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                test.addLast(randVal);
                assertEquals(L.get(L.size() - 1),test.get(test.size() - 1));
            } else if (operationNumber == 1) {
                // size
                int size = L.size();
                int test_size = test.size();
                assertEquals(size,test_size);
            } else if (L.size() > 0 && operationNumber == 2) {
                //getLast
                int LastVal = L.getLast();
                int Test_LastVal = test.getLast();
                assertEquals(LastVal,Test_LastVal);
            } else if (L.size() > 0 && operationNumber == 3){
                //remove
                int MovedItem = L.removeLast();
                int Test_MovedItem = test.removeLast();
                assertEquals(MovedItem,Test_MovedItem);
            }
        }
    }
}
