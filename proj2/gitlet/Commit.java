package gitlet;

// TODO: any imports you need here

import java.io.Serializable;
import java.time.*;
import java.util.HashMap;
import java.util.Map;

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
       Deque<String> blobsId
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

    /** contain all SHA-1 hash code of blobs with Deque*/
    public Map<String , String> blobsId;

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
            blobsId = new HashMap<>();
        } else {
            commitTime = Instant.now();
            parentID = parent.ID;
            blobsId = new HashMap<>(parent.blobsId);
        }
        ID = Utils.sha1((Object) Utils.serialize(this));
    }


}
