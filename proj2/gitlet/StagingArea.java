package gitlet;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class StagingArea implements Serializable {
    private Map<String,String> addtion;
    private Set<String> removal;

    /*return addtion area*/
    public Map<String, String> getAddtion() {
        return addtion;
    }

    /*return removal area*/
    public Set<String> getRemoval() {
        return removal;
    }

    StagingArea() {
        addtion = new HashMap<>();
        removal = new HashSet<>();
    }
}
