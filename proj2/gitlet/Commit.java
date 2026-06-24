package gitlet;

// TODO: any imports you need here

import java.util.Date; // TODO: You'll likely use this in this class

/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  Commit must be Immutable
 *  does at a high level.
 *
 *  @author gulfcode-cn
 */

public class Commit {
    /**
     * TODO: add instance variables here.
     * ** String message
     * ** String author
     * ** String timestamp
     * ** Commit parent
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */

    /* The author of this Commit. */
    private  String author;

    /** The message of this Commit. */
    private final String message;

    /* The time of this Commit. */
    private final Long timestamp;

    /* The parent of this Commit. */
    private final Commit parent;

    /* TODO: fill in the rest of this class. */

    /* return the message of this Commit */
    public String getMessage() {
        return message;
    }

    /* return the time of this Commit when it was created */
    public Long getTimestamp() {
        return timestamp;
    }

    /* return the parent of this Commit  */
    public Commit getParent() {
        return parent;
    }

    /* init Commit Object */
    public Commit(String message, Commit parent) {
        Date now = new Date();
        this.message = message;
        this.parent = parent;
        this.timestamp = now.getTime();
    }

    public Commit(String message, Long timestamp) {
        this.message = message;
        this.parent = null;
        this.timestamp = timestamp;
    }

}
