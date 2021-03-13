package serverModule.commands;

import common.data.SpaceMarine;
import common.exceptions.EmptyCollectionException;
import common.exceptions.NotFoundMarineException;
import common.exceptions.WrongAmountOfParametersException;
import serverModule.utility.CollectionManager;

/**
 * Command 'remove_key'. Removes an item from the collection by its key.
 */
public class RemoveKeyCommand extends AbstractCommand{
    private CollectionManager collectionManager;

    public RemoveKeyCommand(CollectionManager collectionManager) {
        super("remove_key", "удалить элемент из коллекции по его ключу");
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
            SpaceMarine o = collectionManager.getFromCollection(key);
            if (o == null) throw new NotFoundMarineException();
            collectionManager.removeFromCollection(key);
            return true;
        } catch (WrongAmountOfParametersException exception) {
            System.out.println("Вместе с этой командой должен быть передан параметр! Наберит 'help' для справки");
        } catch (EmptyCollectionException exception) {
            System.out.println("Коллекция пуста!");
        } catch (NotFoundMarineException exception) {
            System.out.println("Космический десант не найден!");
        }
        return false;
    }
}
