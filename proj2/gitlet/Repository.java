package gitlet;

import java.io.File;
import java.util.*;

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
            Utils.writeContents(masterfile,commit.getID());
            Utils.writeContents(HEADfile, masterfile.getPath());
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
     * Move HEAD pointer to new commit . renew log and stagingArea file
     * */
    public static void commit(String msg) {
        StagingArea stagingArea = Utils.readObject(StagingArea,StagingArea.class);
        Map<String,String> addition = stagingArea.getAddition();
        Commit newCommit = new Commit(msg,Utils.getHEADofCommit());
        newCommit.blobs.putAll(addition);
        addition.clear();
        Set<String> removalArea = stagingArea.getRemoval();
        for (String rmFileName : removalArea) {
            newCommit.blobs.remove(rmFileName);
        }
        removalArea.clear();
        File HEADtoBranchFile = new File(Utils.readContentsAsString(HEADfile));
        Utils.writeContents(HEADtoBranchFile, newCommit.getID());
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
        Commit HEADcommit = Utils.getHEADofCommit();
        StagingArea stagingArea = Utils.readObject(StagingArea, StagingArea.class);
        boolean fileIsTracked = HEADcommit.blobs.containsKey(fileName);
        if (fileIsTracked || stagingArea.getAddition().containsKey(fileName)) {
            if (fileIsTracked) {
                stagingArea.getRemoval().add(fileName);
            }
            stagingArea.getAddition().remove(fileName);
            File file = new File(fileName);
            if (file.exists()) {
                Utils.restrictedDelete(fileName);
            }
            Utils.writeObject(StagingArea,stagingArea);
        } else {
            System.out.println("No reason to remove the file.");
            System.exit(1);
        }
    }

    /**
     * show all current branches and mark HEAD branch with '*' ,
     *  show staged added file names and removed file names .
     * */
    public static void status() {
       StagingArea stagingArea = readObject(StagingArea, StagingArea.class);
       Map<String , String> addition = stagingArea.getAddition();
       Set<String> removalNames = stagingArea.getRemoval();
       List<String> currentWorkFiles = plainFilenamesIn(CWD);
       Commit HEADcommit = getHEADofCommit();
       Set<String> allFileNames = new HashSet<>();
       Set<String> HEADblobsNames = HEADcommit.blobs.keySet();
       Set<String> untrackedFileNames = new HashSet<>();
       if (currentWorkFiles != null) {
           allFileNames.addAll(currentWorkFiles);
       }
       allFileNames.addAll(HEADblobsNames);
       String HEADname = getHEADname();
       List<String> branchNames = plainFilenamesIn(heads);
       if (branchNames != null) {
           System.out.println("=== Branches ===");
           for (String branchName : branchNames) {
               if (Objects.equals(branchName, HEADname)) {
                   System.out.print("*");
               }
               System.out.println(branchName);
           }
       }
       System.out.println("\n=== Staged Files ===");
       for (Map.Entry<String,String> entry : addition.entrySet()) {
           System.out.println(entry.getKey());
       }
       System.out.println("\n=== Removal Files ===");
       for (String removalName : removalNames) {
           System.out.println(removalName);
       }
       System.out.println("\n=== Modifications Not Staged For Commit ===");
       for (String fileName : allFileNames) {
           if (addition.containsKey(fileName)
                   || currentWorkFiles != null && HEADblobsNames.contains(fileName) && currentWorkFiles.contains(fileName)) {
                File currentFile = new File(fileName);
                boolean whetherSame = addition.containsKey(fileName) ?
                        Objects.equals(addition.get(fileName),sha1((Object) readContents(currentFile))) :
                        Objects.equals(HEADcommit.blobs.get(fileName),sha1((Object) readContents(currentFile)));
                if (!whetherSame) {
                     System.out.println(fileName + " (modified)");
                }
           } else if ( HEADblobsNames.contains(fileName) &&
                   (currentWorkFiles == null || !currentWorkFiles.contains(fileName)) ) {
                System.out.println(fileName + " (deleted)");
           } else {
               untrackedFileNames.add(fileName);
           }
       }
       System.out.println("\n=== Untracked Files ===");
       for (String fileName : untrackedFileNames) {
           System.out.println(fileName);
       }
    }



    /* replace the file with file of commit that HEAD point to*/
    public static void checkout(String fileName) {
        File file = Utils.join(CWD,fileName);
        Commit HEADcommit = Utils.getHEADofCommit();
        if (HEADcommit.blobs.containsKey(fileName)) {
            String file_Id = HEADcommit.blobs.get(fileName);
            File blobFile = Utils.getFile(file_Id);
            Utils.writeContents(file,Utils.readContentsAsString(blobFile));
        } else {
            System.out.println("file not exist in HEAD .");
            System.exit(1);
        }
    }

    /* check out HEAD pointer to the branch , and renew work space*/
    public static void checkoutBranch(String branchName) {
        if (Utils.checkClean()) {
            File branchFile = Utils.join(heads, branchName);
            if (branchFile.exists()) {
                Set<String> currentFileNames = getHEADofCommit().blobs.keySet();
                String CommitId = readContentsAsString(branchFile);
                Commit branchCommit = readObject(getFile(CommitId), Commit.class);
                Set<String> allFileNames = new HashSet<>(currentFileNames);
                allFileNames.addAll(branchCommit.blobs.keySet());
                for (String fileName : allFileNames) {
                    String blobId = branchCommit.blobs.get(fileName);
                    File blobInBranch = getFile(blobId);
                    File fileInCurrent = join(CWD,fileName);
                    if (blobInBranch != null) {
                        writeContents(fileInCurrent,readContentsAsString(blobInBranch));
                    } else {
                        restrictedDelete(fileInCurrent);
                    }
                }
                writeContents(HEADfile,branchFile.getPath());
            } else {
                System.out.println("the branch is not exist");
                System.exit(1);
            }
        } else {
            System.out.println("work directory is not clean");
            System.exit(1);
        }
    }

    /* replace the file with targeted version*/
    public static void checkout(String commitID,String fileName) {
        File fileInWorkSpace = Utils.join(CWD, fileName);
        File commitFile = Utils.getFile(commitID);
        if (commitFile != null) {
            Commit commit = Utils.readObject(commitFile, Commit.class);
            String fileId = commit.blobs.get(fileName);
            if (fileId != null) {
                File file = Utils.getFile(fileId);
                String fileContent = Utils.readContentsAsString(file);
                Utils.writeContents(fileInWorkSpace, fileContent);
            } else {
                System.out.println("the file is not exist in that version");
                System.exit(1);
            }
        } else {
            System.out.println("commit id is not exist");
            System.exit(1);
        }
    }

    /** Start from the current HEAD commit,
     * traverse back along the first parent chain
     * and print to the initial commit (include time , id , message)
     * */
    public static void log() {
        Commit ptr = Utils.getHEADofCommit();
        while(ptr != null) {
            System.out.println("===");
            System.out.println("commit " + ptr.getID());
            Utils.printCommitTime(ptr);
            System.out.println(ptr.getMessage());
            System.out.println();
            if (ptr.getParentID() != null) {
                ptr = Utils.readObject(Utils.getFile(ptr.getParentID()), Commit.class);
            } else {
                break;
            }
        }
    }

    /** Make a new branch ,
     *  the new branch points to HEAD commit when it was created.
     *  if the branch which has the same name has existed ,
     *  then failed and printf .
     * */
    public static void branch(String branchName) {
        File branchFile = Utils.join(heads,branchName);
        if (branchFile.exists()) {
            System.out.println("A branch with that name already exists.");
            System.exit(1);
        } else {
            Commit HEADCommit = Utils.getHEADofCommit();
            Utils.writeContents(branchFile,HEADCommit.getID());
        }
    }

    /** merge branch into master
     *  if the file only exists in HEAD , then keep it;
     *  if the file only exists in branch , then place it into addition of Staging area;
     *  if the file exists in spilt point , it wasn't modified in master and removed in branch , then rm it;
     *  if the file was modified in both branch differently , then encounter a merge conflict .
     * */
    public static void merge(String branchName) {
        File branchFile = Utils.join(heads,branchName);
        if (!branchFile.exists()) {
            System.out.println("branch not exist");
            System.exit(1);
        } else {
            Commit branchCommit = Utils.readObject(branchFile, Commit.class);
            Commit HEADcommit = Utils.getHEADofCommit();
            Commit spiltPoint = seekSplitPoint(Utils.readObject(branchFile, Commit.class));
            String currentBranchName = getHEADname();
            Commit mergeCommit = new Commit("Merged " + branchName + " into " + currentBranchName,
                    HEADcommit,branchCommit);
            Set<String> allName = new HashSet<>(HEADcommit.blobs.keySet());
            allName.addAll(branchCommit.blobs.keySet());
            allName.addAll(spiltPoint.blobs.keySet());
            if (checkClean()) {
                for (String fileName : allName) {
                    String caseType = fileType(fileName, HEADcommit.blobs, branchCommit.blobs, spiltPoint.blobs);
                    File file = join(CWD, fileName);
                    String blobInHEADid = HEADcommit.blobs.get(fileName);
                    switch (caseType) {
                        case "only be modified or removed or added in HEAD commit":
                            if (file.exists()) {
                                mergeCommit.blobs.put(fileName, blobInHEADid);
                                writeContents(file, readContentsAsString(getFile(blobInHEADid)));
                            }
                            break;
                        case "file only modified , removed or added in branch":
                            String fileInBranchId = branchCommit.blobs.get(fileName);
                            if (fileInBranchId != null) {  //file is added or modified in branch
                                writeContents(file, readContentsAsString(getFile(fileInBranchId)));
                                add(fileName);
                            } else {  //else is file removed in branch
                                restrictedDelete(file);
                            }
                            break;
                        case "there are same modification in same file",
                             "file is same in all commit":
                            mergeCommit.blobs.put(fileName, HEADcommit.getID());
                            break;
                        case "HEAD and branch has different modification in same file":
                            File conflictFileInBranch = Utils.getFile(branchCommit.blobs.get(fileName));
                            String contentOfBranchFile = conflictFileInBranch != null ?
                                    readContentsAsString(conflictFileInBranch) :
                                    "(file was removed in )" + branchName;
                            String contentOfHEADfile = file.exists() ?
                                    readContentsAsString(file) :
                                    "(file was deleted in HEAD)";
                            writeContents(file,
                                    "<<<<<<< HEAD\n",
                                    contentOfHEADfile,
                                    "\n=======\n",
                                    contentOfBranchFile,
                                    "\n<<<<<<<" + branchName);
                            String fileId = sha1((Object) readContents(file));
                            mergeCommit.blobs.put(fileName,fileId);
                            saveFileTObjectDir(file);
                            System.out.println("Encountered a merge conflict In :" + fileName);
                            break;
                        default:
                            System.out.println("encounter a error , an unexpected case.");
                            System.exit(1);
                    }
                }
                saveCommitTOobjectDir(mergeCommit);
            } else {
                System.out.println("work place is not clean");
                System.exit(1);
            }
        }
    }

    /* just for finding spilt point*/
    private static Commit seekSplitPoint(Commit branch) {
        Commit HEAD = Utils.getHEADofCommit();
        Commit H_ptr = HEAD;
        Commit B_ptr = branch;
        while (H_ptr != B_ptr) {
            H_ptr = H_ptr.getParentID() != null ?
                    readObject(getFile(H_ptr.getParentID()), Commit.class) : HEAD;
            B_ptr = B_ptr.getParentID() != null ?
                    readObject(getFile(B_ptr.getParentID()), Commit.class) : branch;
        }
        return H_ptr;
    }


    /* return status of file from HEAD , branch and baseCommit*/
    private static String fileType(String fileName ,
                                   Map<String,String> HEADblobs ,
                                   Map<String,String> branchBlobs ,
                                   Map<String,String> baseBlobs) {
        boolean fileModifiedInHEAD = !Objects.equals(baseBlobs.get(fileName),HEADblobs.get(fileName));
        boolean fileModifiedInBranch = !Objects.equals(baseBlobs.get(fileName),branchBlobs.get(fileName));
        boolean fileHasDifferentContentBetweenHEADandBranch = !Objects.equals(HEADblobs.get(fileName),branchBlobs.get(fileName));
        if (fileModifiedInHEAD && !fileModifiedInBranch) {
            return "only be modified or removed or added in HEAD commit";
        } else if (fileModifiedInHEAD) {
            if (fileHasDifferentContentBetweenHEADandBranch) {
                return "HEAD and branch has different modification in same file";
            } else {
                return "there are same modification in same file";
            }
        } else if (fileModifiedInBranch) {
            return "file only modified , removed or added in branch";
        } else {
            return "file is same in all commit";
        }
    }
}
























