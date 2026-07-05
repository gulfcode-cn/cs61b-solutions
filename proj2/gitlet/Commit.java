package gitlet;

// TODO: any imports you need here

import java.io.Serializable;
import java.time.*;

/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author gulfcode-cn
 */
public class Commit implements Serializable {
    /* *
       TODO: add instance variables here.
       String message
       String parentID
       String ID
       Instant commitTime

       List all instance variables of the Commit class here with a useful
       comment above them describing what that variable represents and how that
       variable is used. We've provided one example for `message`.
     */

    /** The message of this Commit. */
    private final String message;

    /* TODO: fill in the rest of this class. */

    /** save Hash ID of parent*/
    private final String parentID;

    /** the Hash ID of commit*/
    private final String ID;

    /** the time of commit */
    private final Instant commitTime;



    /** return sha-1 code of this commit*/
    public String getID() {
        return ID;
    }

    /* construct commit */
    Commit(String msg , Commit parent ) {
        message = msg;
        if (parent == null) {
            commitTime = Instant.EPOCH;
            parentID = null;
        } else {
            commitTime = Instant.now();
            parentID = parent.getID();
        }
        ID = Utils.sha1(Utils.serialize(this));
    }


}
