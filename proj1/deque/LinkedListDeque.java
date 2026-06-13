package deque;

import java.util.Iterator;
import java.lang.Iterable;
public class LinkedListDeque<T> implements Deque<T>, Iterable<T>{
    private class Node<T>{
        Node(){
            m_val=null;
            Next=this;
            Last=this;
        }
        Node(T val){
            m_val=val;
            Next=this;
            Last=this;
        }
        Node(T val,Node<T> N,Node<T> L){
            m_val=val;
            Next=N;
            Last=L;
        }
        public T m_val;
        public Node<T> Next;
        public  Node<T> Last;
    }

    private final Node<T> Sentinel=new Node<>();
    private int m_size=0;

    @Override
    public void addFirst(T item){
        Node<T> AddItem=new Node<>(item,Sentinel.Next,Sentinel);
        Sentinel.Next.Last=AddItem;
        Sentinel.Next=AddItem;
        m_size++;
    }

    @Override
    public void addLast(T item){
        Node<T> AddItem=new Node<>(item,Sentinel,Sentinel.Last);
        Sentinel.Last.Next=AddItem;
        Sentinel.Last=AddItem;
        m_size++;
    }

    @Override
    public boolean isEmpty(){
        return m_size == 0;
    }

    @Override
    public int size(){
        return m_size;
    }

    @Override
    public void printDeque(){
        for(Iterator<T> it = iterator();it.hasNext();System.out.print(it.next()+" "));
    }

    @Override
    public T removeFirst(){
        if (size() == 0){
            return null;
        }else{
            T del = Sentinel.Next.m_val;
            Sentinel.Next.Next.Last=Sentinel;
            Sentinel.Next=Sentinel.Next.Next;
            m_size--;
            return del;
        }
    }

    @Override
    public T removeLast(){
        if (size() == 0){
            return null;
        }else{
            T del = Sentinel.Last.m_val;
            Sentinel.Last.Last.Next = Sentinel;
            Sentinel.Last = Sentinel.Last.Last;
            m_size--;
            return del;
        }
    }

    @Override
    public T get(int index){
        if (index < size()){
            Iterator<T> it =iterator();
            for(int i = 0;i < index;i++){
                it.next();
            }
            return it.next();
        }else{
            return null;
        }
    }

    //以下是迭代器类
    private class AllNodeIterator implements Iterator<T>{
        Node<T> cur_Iterator;

        AllNodeIterator(){
            cur_Iterator=Sentinel.Next;
        }

        public boolean hasNext(){
            return cur_Iterator != Sentinel;
        }

        public T next(){
            T cur = cur_Iterator.m_val;
            cur_Iterator = cur_Iterator.Next;
            return cur;
        }

    }

    @Override
    public Iterator<T> iterator(){
        return new AllNodeIterator();
    }

}
