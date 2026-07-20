package gitlet;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class StagingArea implements Serializable ,Dumpable{
    /*key is FileName , value is SHA-1 code of addition File*/
    private final Map<String,String> addition;
    /*contain removal fileName*/
    private final Set<String> removal;

    /*return addition area*/
    public Map<String, String> getAddition() {
        return addition;
    }

    /*return removal area*/
    public Set<String> getRemoval() {
        return removal;
    }

    /* clear staging area*/
    public void clear() {
        addition.clear();
        removal.clear();
    }

    StagingArea() {
        addition = new HashMap<>();
        removal = new HashSet<>();
    }

    @Override
    public void dump() {
        System.out.println("addition file :");
        for (Map.Entry<String,String> entry : addition.entrySet()) {
            System.out.println(entry.getKey());
        }
        System.out.println("==========");
        System.out.println("removal file :");
        for (String fileName : removal) {
            System.out.println(fileName);
        }
    }
}
