package capers;

import java.io.File;
import java.io.IOException;

/** A repository for Capers 
 * @author gulfcode-cn
 * The structure of a Capers Repository is as follows:
 * *
 * .capers/ -- top level folder for all persistent data in your lab12 folder
 *    - dogs/ -- folder containing all of the persistent data for dogs
 *    - story -- file containing the current story
 *
 * TODO: change the above structure if you do something different.
 */
public class CapersRepository {
    /** Current Working Directory. */
    static final File CWD = new File(System.getProperty("user.dir"));

    /** Main metadata folder. */
    static final File CAPERS_FOLDER = Utils.join(CWD,".capers"); // TODO Hint: look at the `join`
                                                                             //function in Utils

    /**
     * Does required filesystem operations to allow for persistence.
     * (creates any necessary folders or files)
     * Remember: recommended structure (you do not have to follow):
     * *
     * .capers/ -- top level folder for all persistent data in your lab12 folder
     *    - dogs/ -- folder containing all of the persistent data for dogs
     *    - story -- file containing the current story
     */
    public static void setupPersistence() {
        // TODO
        if (CWD.getName().equals("lab6")) {
            if (!CAPERS_FOLDER.mkdir()) {
                 Utils.exitWithError(".capers dir creation failed");
            }
            File dogs = Utils.join(CAPERS_FOLDER,"dogs");
            if (!dogs.mkdir()) {
                Utils.exitWithError("dogs dir creation failed");
            }
            File story = Utils.join(CAPERS_FOLDER,"story");
            try {
                if(!story.createNewFile()) {
                    Utils.exitWithError("story file creation failed");
                }
            } catch (IOException excp) {
                throw new IllegalArgumentException(excp.getMessage());
            }
        } else {
            Utils.exitWithError("You must be in lab6 to call this command !");
        }
    }

    /**
     * Appends the first non-command argument in args
     * to a file called `story` in the .capers directory.
     * @param text String of the text to be appended to the story
     */
    public static void writeStory(String text) {
        // TODO
        File story = Utils.join(CAPERS_FOLDER,"story");
        if (story.exists()) {
            Utils.writeContents(story,
                    Utils.readContentsAsString(story),
                    text);
        } else {
            Utils.exitWithError("story was not existed");
        }
    }

    /**
     * Creates and persistently saves a dog using the first
     * three non-command arguments of args (name, breed, age).
     * Also prints out the dog's information using toString().
     */
    public static void makeDog(String name, String breed, int age) {
        // TODO
        if (Dog.DOG_FOLDER.exists()) {
            Dog newDog = new Dog(name, breed, age);
            newDog.saveDog();
        } else {
            Utils.exitWithError("dogs dir was not found .");
        }
    }

    /**
     * Advances a dog's age persistently and prints out a celebratory message.
     * Also prints out the dog's information using toString().
     * Chooses dog to advance based on the first non-command argument of args.
     * @param name String name of the Dog whose birthday we're celebrating.
     */
    public static void celebrateBirthday(String name) {
        // TODO
        Dog birthdayDog = Dog.fromFile(name);
        birthdayDog.haveBirthday();
        birthdayDog.saveDog();
    }
}
