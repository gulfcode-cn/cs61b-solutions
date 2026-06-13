package deque;

import org.junit.Test;

import java.util.Comparator;

import static org.junit.Assert.*;

public class MaxArrayDequeTest {
    @Test
    public void ReturnMAX() {
        MaxArrayDeque<String> test_MX01 = new MaxArrayDeque<String>();

        //insert simple three value in it
        test_MX01.addLast("apple");
        test_MX01.addLast("bear");
        test_MX01.addLast("dog");

        String max = test_MX01.Max();

        assertEquals("dog",max);
    }

    @Test
    public void CustomComparator(){

        //custom class
        class nigga {
            String m_name;
            int m_age;
            nigga(String name , int age) {
                m_age = age;
                m_name = name;
            }
            nigga() {
                m_name = "none";
                m_age = 114514;
            }
        }
        MaxArrayDeque<nigga> test_MX02 = new MaxArrayDeque<nigga>();
        //make a custom comparator
        Comparator<nigga> niggaComparator = new Comparator<nigga>() {
            @Override
            public int compare(nigga o1, nigga o2) {
                if (o1.m_age > o2.m_age){
                    return 1;
                }else if (o1.m_age < o2.m_age){
                    return -1;
                }else if (o1.m_name.compareTo(o2.m_name) > 0){
                    return 1;
                }else if (o1.m_name.compareTo(o2.m_name) < 0){
                    return -1;
                }else {
                    return 0;
                }
            }
        };


        nigga n1 = new nigga("Nm",15);
        nigga n2 = new nigga("lol",23);
        nigga n3 = new nigga("app",56);
        //insert simple three value in it
        test_MX02.addFirst(n1);
        test_MX02.addFirst(n2);
        test_MX02.addFirst(n3);

        nigga max_nigga = test_MX02.Max(niggaComparator);

        assertEquals(n3, max_nigga);
    }
}
