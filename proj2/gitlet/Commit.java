package gitlet;

// TODO: any imports you need here

import java.io.Serializable;
import java.time.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author gulfcode-cn
 */
public class Commit implements Serializable , Dumpable{
    /* *
       TODO: add instance variables here.
       String message
       String parentID
       String ID
       Instant commitTime
       Deque<String> blobs
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
    private final Date commitTime;

    /** key is file name , value is SHA-1 code of file*/
    public Map<String , String> blobs;

    /** return sha-1 code of this commit*/
    public String getID() {
        return ID;
    }

    /* return parent id*/
    public String getParentID() {
        return parentID;
    }

    /* return commit time*/
    public Date getCommitTime() {
        return commitTime;
    }

    /* return commit message*/
    public String getMessage() {
        return message;
    }


    /* construct commit */
    Commit(String msg , Commit parent ) {
        message = msg;
        if (parent == null) {
            commitTime = new Date(0);
            parentID = null;
            blobs = new HashMap<>();
        } else {
            commitTime = new Date();
            parentID = parent.ID;
            blobs = new HashMap<>(parent.blobs);
        }
        ID = Utils.sha1((Object) Utils.serialize(this));
    }

    @Override
    public void dump() {
        System.out.printf("this commit id is : %s \n",ID);
        System.out.println("message is : "+message);
        System.out.println("tracked file : ");
        for (Map.Entry<String,String> entry : blobs.entrySet()) {
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
            System.out.println("===============");
        }
    }
}
