package gitlet;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static gitlet.Utils.*;

// TODO: any imports you need here

/** Represents a gitlet repository.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 * * .gitlet
 *      * |
 *      * |------objects/
 *      * |   |-- 00/
 *      * |   |-- 01/
 *      * |   |...
 *      * |------refs/
 *      * |   |--heads/
 *      * |      |---master
 *      * |------HEAD
 *      * |------StagingArea
 *
 *  @author gulfcode-cn
 */
public class Repository {
    /* *
     * TODO: add instance variables here.
     * File CWD
     * File GITLET_DIR
     *
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");
    /** the dir contains all date of blobs and commits*/
    public static final File objects = Utils.join(GITLET_DIR,"objects");

    /** HEAD is the pointer that place checkout commit */
    public static Commit HEAD;
    /** master point to lastest commit*/
    public static Commit master;

    /* TODO: fill in the rest of this class. */

    /** create .gitlet dir in CWD , and make this dir be a gitlet dir .
     * if it has haven .gitlet then return " A Gitlet version-control
     * system already exists in the current directory. "
     * */
    public static void init() {
        if (!GITLET_DIR.exists() && GITLET_DIR.mkdir()) {
            File HEAD = Utils.join(GITLET_DIR,"HEAD");
            File refs = Utils.join(GITLET_DIR,"refs");
            File heads = Utils.join(refs,"heads");
            File master = Utils.join(heads,"master");
            File StagingArea = Utils.join(GITLET_DIR,"StagingArea");
            StagingArea stagingArea = new StagingArea();
            Commit commit = new Commit("initial commit",null);
            String commitSHA_1 = commit.getID();
            if (!refs.mkdir()) {
                System.out.println("refs dir created failed");
                System.exit(1);
            }
            if (!heads.mkdir()) {
                System.out.println("heads dir created failed");
                System.exit(1);
            }
            if (!objects.mkdir()) {
                System.out.println("objects dir created failed");
                System.exit(1);
            }
            File save_dir = Utils.join(objects,commitSHA_1.substring(0,2));
            if (!save_dir.mkdir()) {
                System.out.println("save_dir created failed");
                System.exit(1);
            }
            File save_file = Utils.join(save_dir,commitSHA_1.substring(2));
            Utils.writeObject(StagingArea,stagingArea);
            Utils.writeObject(save_file,commit);
            Utils.writeObject(master,commitSHA_1);
        } else {
            System.out.println("A Gitlet version-control system already exists in the current directory.");
            System.exit(1);
        }
    }

    /**
     * add file to staging area , whether this file was added before ,
     * it would be cover old one in staging area or just add in it .
     * if added file was the same as the version in lastest commit ,
     * then remove it from staging area .
     * */
    public static void add(String fileName) {
        File addedFile = Utils.join(new File(System.getProperty("user.dir")),fileName);
        if (addedFile.exists()) {
            String addedFileSHA_1 = Utils.sha1(Utils.readContents(addedFile));
            File preDir = Utils.join(objects,addedFileSHA_1.substring(0,2));
            if (Utils.join(preDir,addedFileSHA_1.substring(2)).exists()) {
                return;
            }
            File stageFile = Utils.join(GITLET_DIR,"StagingArea");
            StagingArea stagingArea = Utils.readObject(stageFile,StagingArea.class);
            stagingArea.getAddtion().put(fileName,addedFileSHA_1);
        } else {
            System.out.println("File does not exist.");
            System.exit(1);
        }
    }
}
