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
            case "rm":
                // TODO: handle the 'rm [file name]'
                Repository.rm(args[1]);
            case "":
                System.out.println("Please enter a command.");
                System.exit(1);
            default:
                System.out.println("No command with that name exists.");
            // TODO: FILL THE REST IN
        }
    }
}
