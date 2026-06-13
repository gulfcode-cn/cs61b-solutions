package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T>,Iterable<T> {
    private int m_size;
    private int m_capacity;
    private int m_front;
    private int m_back;
    private T[] Array;
    ArrayDeque(){
        m_capacity = 8;
        m_size = 0;
        m_back = 0;
        m_front = 0;
        Array = (T[]) new Object[m_capacity];
    }
    private void resize(int new_capacity){
        T[] new_arr = (T[]) new Object[new_capacity];
        for (int i = 0; i < m_size; i++) {
            new_arr[i] = Array[(m_front + i) % m_capacity];
        }
        m_front = 0;
        m_back = m_size;
        m_capacity = new_capacity;
        Array = new_arr;
    }

    @Override
    public void addFirst(T item){
        if (m_size == m_capacity){
            resize(m_capacity * 2);
        }
        m_front = (m_front - 1 + m_capacity) % m_capacity;
        Array[m_front] = item;
        m_size++;
    }

    @Override
    public void addLast(T item){
        if (m_size == m_capacity){
            resize(m_capacity * 2);
        }
        Array[m_back] = item;
        m_back = (m_back + 1) % m_capacity;
        m_size++;
    }

    @Override
    public boolean isEmpty(){
        return size() == 0;
    }

    @Override
    public int size(){
        return m_size;
    }

    @Override
    public void printDeque(){
        for(int i = 0; i < m_size; i++){
            System.out.print(Array[(m_front + i) % m_capacity] + " ");
        }
        System.out.println();
    }

    @Override
    public T removeFirst(){
        if (m_size == 0){
            return null;
        }
        T removed = Array[m_front];
        Array[m_front] = null;
        m_front = (m_front + 1) % m_capacity;
        m_size--;
        if (m_capacity > 8 && m_size <= m_capacity / 4){
            resize(m_capacity / 2);
        }
        return removed;
    }

    @Override
    public T removeLast(){
        if (m_size == 0){
            return null;
        }
        m_back = (m_back - 1 + m_capacity) % m_capacity;
        T removed = Array[m_back];
        Array[m_back] = null;
        m_size--;
        if (m_capacity > 8 && m_size <= m_capacity / 4){
            resize(m_capacity / 2);
        }
        return removed;
    }

    @Override
    public T get(int index){
        if (index < 0 || index >= m_size){
            return null;
        }
        return Array[(m_front + index) % m_capacity];
    }

    //以下是迭代器类
    private class AllArrIterator implements Iterator<T>{
        int index;

        AllArrIterator(){
            index = 0;
        }

        public boolean hasNext() {
            return index < m_size;
        }

        public T next(){
            T item = get(index);
            index++;
            return item;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new AllArrIterator();
    }
}


