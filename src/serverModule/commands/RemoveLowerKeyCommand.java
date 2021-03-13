package serverModule.commands;

import common.exceptions.EmptyCollectionException;
import common.exceptions.WrongAmountOfParametersException;
import serverModule.utility.CollectionManager;

import java.util.List;

/**
 * Command 'remove_lower_key'. Removes all items from the collection whose key is less than the specified one.
 */
public class RemoveLowerKeyCommand extends AbstractCommand{
    private CollectionManager collectionManager;

    public RemoveLowerKeyCommand(CollectionManager collectionManager) {
        super("remove_lower_key null", "удалить из коллекции все элементы, ключ которых меньше, чем заданный");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     * @return Command exit status.
     */
    @Override
    public boolean execute(String argument, Object objectArgument) {
        try {
            if (argument.isEmpty()) throw new WrongAmountOfParametersException();
            if (collectionManager.collectionSize() == 0) throw new EmptyCollectionException();
            int key = Integer.parseInt(argument);
            collectionManager.removeLowerKey(key);
            return true;
        } catch (WrongAmountOfParametersException exception) {
            System.out.println("Вместе с этой командой должен быть передан параметр! Наберит 'help' для справки");
        } catch (EmptyCollectionException exception) {
            System.out.println("Коллекция пуста!");
        }
        return false;
    }
}
