package gitlet;

import java.io.File;

import static gitlet.Repository.init;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author gulfcode-cn
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ...
     *  <Such as : java gitlet.Main init
     *  <Such as : java gitlet.Main add Hello.txt
     */
    public static void main(String[] args) {
        // TODO: what if args is empty?
        String firstArg = args[0];
        switch(firstArg) {
            case "init":
                // TODO: handle the `init` command
                init();
                break;
            case "add":
                // TODO: handle the `add [filename]` command

                break;

            // TODO: FILL THE REST IN
        }
    }
}
