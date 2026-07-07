package gitlet;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class StagingArea implements Serializable {
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

    StagingArea() {
        addition = new HashMap<>();
        removal = new HashSet<>();
    }
}
