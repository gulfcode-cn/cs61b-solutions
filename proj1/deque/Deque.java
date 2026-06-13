package deque;

public interface Deque<T> {
    void addFirst(T item); //头插法
    void addLast(T item); //尾插法
    boolean isEmpty(); //判空
    int size(); //返回元素数量
    void printDeque(); //从前到后打印
    T removeFirst(); //删除并返回头元素
    T removeLast(); //删除并返回尾元素
    T get(int index); //获取索引元素,不存在则返回null
}
