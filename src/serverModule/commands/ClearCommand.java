package serverModule.commands;

import common.exceptions.WrongAmountOfParametersException;
import serverModule.utility.CollectionManager;

/**
 * Command 'clear'. Clears the collection.
 */
public class ClearCommand extends AbstractCommand{
    private CollectionManager collectionManager;

    public ClearCommand(CollectionManager collectionManager) {
        super("clear", "очистить коллекцию");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     * @return Command exit status.
     */
    @Override
    public boolean execute(String argument, Object objectArgument) {
        try {
            if(!argument.isEmpty()) throw new WrongAmountOfParametersException();
            collectionManager.clearCollection();
            return true;
        } catch (WrongAmountOfParametersException exception) {
            System.out.println("У этой команды нет параметров!");
        }
        return false;
    }
}
