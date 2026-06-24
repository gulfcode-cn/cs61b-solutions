package gitlet;

import java.io.File;
import static gitlet.Utils.*;

// TODO: any imports you need here

/** Represents a gitlet repository.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
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

    /* TODO: fill in the rest of this class. */
    public static void init() {
        /* * 在当前目录创建一套全新的 Gitlet 版本控制系统。
         * 该系统初始化时会自动生成一条提交记录：这条Commit不包含任何文件，
         * 提交信息固定为 initial commit（原样书写，不能加任何标点符号）。
         * 仓库仅有一条分支 master，初始化时 master 分支会指向这条初始Commit，
         * 且 master 会被设为当前分支。
         *
         * 这条初始Commit的时间戳固定为 UTC 1970年1月1日星期四 00:00:00，
         * 无论你程序内部选用哪种日期展示格式
         * （这个时间点被称作「Unix 纪元起点」，在程序底层用数字 0 来表示）。
         * 因为所有由 Gitlet 创建的仓库，它们的初始提交内容完全一致，
         * 所以所有仓库会共用这一条提交（它们的唯一哈希标识 UID 完全相同），
         * 仓库中后续所有提交最终都能追溯到这条初始提交。
         */
        Commit initial = new Commit("initial commit", 0L);
        //Here we need to initialize a master branch and have it point to initial Commit
    }
}
