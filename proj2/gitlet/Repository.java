package gitlet;

import java.io.File;
import java.util.Map;
import java.util.Set;

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
 *      * |      |---... (branch
 *      * |------HEAD
 *      * |------StagingArea
 *
 *  @author gulfcode-cn
 */
public class Repository {
    /* *
     * TODO: add instance variables here.
     * File CWD
     * File GITLET_DIR (dir
     * File objects (dir
     * File refs (dir
     * File heads (dir
     * File masterfile (file
     * File StagingArea (file
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */

    /* The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /* The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");
    /* the dir contains all date of blobs and commits*/
    public static final File objects = Utils.join(GITLET_DIR,"objects");
    /* the dir */
    public static final File refs = Utils.join(GITLET_DIR,"refs");
    /* the dir contains branch file */
    public static final File heads = Utils.join(refs,"heads");
    /* pointer to lastest commit*/
    public static final File masterfile = Utils.join(heads,"master");
    /* pointer to current branch */
    public static final File HEADfile = Utils.join(GITLET_DIR,"HEAD");
    /* file contains waiting to be added or removed files*/
    public static final File StagingArea = Utils.join(GITLET_DIR,"StagingArea");


    /* TODO: fill in the rest of this class. */

    /**
     * create .gitlet dir in CWD , and make this dir be a gitlet dir .
     * if it has haven .gitlet then return " A Gitlet version-control
     * system already exists in the current directory. "
     * */
    public static void init() {
        if (!GITLET_DIR.exists() && GITLET_DIR.mkdir()) {
            StagingArea stagingArea = new StagingArea();
            Commit commit = new Commit("initial commit",null);
            Utils.makeDir(refs);
            Utils.makeDir(heads);
            Utils.makeDir(objects);
            Utils.saveCommitTOobjectDir(commit);
            Utils.writeObject(StagingArea,stagingArea);
            Utils.writeObject(masterfile,commit.getID());
            Utils.writeObject(HEADfile, masterfile.getAbsolutePath());
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
     * renew stagingArea
     * */
    public static void add(String fileName) {
        File addedFile = Utils.join(new File(System.getProperty("user.dir")),fileName);
        if (addedFile.exists()) {
            Utils.saveFileTObjectDir(addedFile);
            StagingArea stagingArea = Utils.readObject(StagingArea,StagingArea.class);
            stagingArea.getAddition().put(fileName,Utils.sha1((Object) Utils.readContents(addedFile)));
            Utils.writeObject(StagingArea,stagingArea);
        } else {
            System.out.println("File does not exist.");
            System.exit(1);
        }
    }

    /**
     * commit the file from staging area to .gitlet dir .
     * then , make this version into a commit and commit to gitlet
     * the new commit include new time , new blobs_SHA_1 .
     * Move pointer to new commit . renew log and stagingArea file
     * */
    public static void commit(String msg) {
        StagingArea stagingArea = Utils.readObject(StagingArea,StagingArea.class);
        Map<String,String> addition = stagingArea.getAddition();
        Commit newCommit = new Commit(msg,Utils.readObject(new File(Utils.readContentsAsString(HEADfile)),Commit.class));
        newCommit.blobs.putAll(addition);
        addition.clear();
        Set<String> removalArea = stagingArea.getRemoval();
        for (String rmFileName : removalArea) {
            newCommit.blobs.remove(rmFileName);
        }
        removalArea.clear();
        Utils.writeObject(masterfile, newCommit.getID());
        Utils.saveCommitTOobjectDir(newCommit);
        Utils.writeObject(StagingArea,stagingArea);
    }

    /**
     * put the file into removal area of stagingArea ,
     * then delete the file from workspace .
     * if the file not exist or not be tracked by HEAD ,
     * then print "No reason to remove the file." and exit 1 .
     * renew StagingArea .
     * */
    public static void rm(String fileName) {
        String branchAddress = Utils.readContentsAsString(HEADfile);
        Commit branch = Utils.readObject(new File(branchAddress), Commit.class);
        StagingArea stagingArea = Utils.readObject(StagingArea, StagingArea.class);
        boolean fileIsTracked = branch.blobs.containsKey(fileName);
        if (fileIsTracked || stagingArea.getAddition().containsKey(fileName)) {
            if (fileIsTracked) {
                stagingArea.getRemoval().add(fileName);
            }
            stagingArea.getAddition().remove(fileName);
            Utils.restrictedDelete(fileName);
            Utils.writeObject(StagingArea,stagingArea);
        } else {
            System.out.println("No reason to remove the file.");
            System.exit(1);
        }
    }

    /* replace the file with file of commit that HEAD point to*/
    public static void checkout(String fileName) {
        File branch = new File(Utils.readContentsAsString(HEADfile));
        File file = Utils.join(CWD,fileName);
        Commit commit = Utils.readObject(Utils.getFile(Utils.readContentsAsString(branch)), Commit.class);
        if (commit.blobs.containsKey(fileName)) {
            File branchFile = Utils.getFile(commit.blobs.get(fileName));
            Utils.writeContents(file,branchFile);
        } else {
            System.out.println("file not exist");
            System.exit(1);
        }

    }
}
