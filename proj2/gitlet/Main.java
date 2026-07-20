package gitlet;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author gulfcode-cn
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */
    public static void main(String[] args) {
        // TODO: what if args is empty?
        String firstArg = args[0];
        switch(firstArg) {
            case "init":
                // TODO: handle the `init` command
                Repository.init();
                break;
            case "add":
                // TODO: handle the `add [filename]` command
                Repository.add(args[1]);
                break;
            case "commit":
                // TODO: handle the 'commit [message]' command
                if (args[1].isEmpty()) {
                    System.out.println("Please enter a commit message.");
                    System.exit(1);
                } else {
                    Repository.commit (args[1]);
                }
                break;
            case "rm":
                // TODO: handle the 'rm [file name]'
                Repository.rm(args[1]);
                break;
            case "status":
                // TODO:
                Repository.status();
                break;
            case "checkout":
                // TODO: handle the 'checkout [commit id] -- [file name]'
                //                  'checkout -- [file name]'
                //                  'checkout [branch name]'  command
                int argNum = args.length;
                switch (argNum) {
                    case 1:
                        System.out.println("plz inout more args");
                        System.exit(1);
                        break;
                    case 2:
                        Repository.checkoutBranch(args[1]);
                        break;
                    case 3:
                        Repository.checkout(args[2]);
                        break;
                    case 4:
                        Repository.checkout(args[1],args[3]);
                        break;
                    default:
                        System.out.println("The number of args is over 4");
                        System.exit(1);
                        break;
                }
                break;
            case "log":
                Repository.log();
                break;
            case "branch":
                //TODO: handle the 'branch [branch name]' command
                Repository.branch(args[1]);
                break;
            case "rm-branch":
                //TODO: handle the 'rm-branch [branch name]' command
                Repository.rm_branch(args[1]);
                break;
            case "find":
                //TODO: handle the 'find [commit message]' command
                Repository.find(args[1]);
                break;
            case "merge":
                //TODO: handle the 'merge [branch name]' command
                Repository.merge(args[1]);
                break;
            case "reset":
                //TODO: handle the 'reset [commit id]' command
                Repository.reset(args[1]);
                break;
            case "":
                System.out.println("Please enter a command.");
                System.exit(1);
                break;
            default:
                System.out.println("No command with that name exists.");
                break;
            // TODO: FILL THE REST IN
        }
    }
}
