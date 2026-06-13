package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private Comparator<T> defaultCmp;

    public MaxArrayDeque () {
        defaultCmp = (Comparator<T>) Comparator.naturalOrder();
    }

    public MaxArrayDeque (Comparator<T> c) {
        super();
        defaultCmp = c;
    }

    public T Max() {
        if (size() == 0){
            return null;
        }
        T max = null;
        for (T it : this){
            if (max == null){
                max = it;
            }else {
                if (defaultCmp.compare(it, max) > 0){
                    max = it;
                }
            }
        }
        return max;
    }

    public T Max(Comparator<T> c) {
        if (size() == 0){
            return null;
        }
        T max =null;
        for (T it : this){
            if (max == null){
                max = it;
            }else {
                if (c.compare(it, max) > 0){
                    max = it;
                }
            }
        }
        return max;
    }
}
