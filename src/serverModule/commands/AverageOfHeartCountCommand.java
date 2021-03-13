package serverModule.commands;

import common.exceptions.EmptyCollectionException;
import common.exceptions.WrongAmountOfParametersException;
import serverModule.utility.CollectionManager;

/**
 * Command 'average_of_heart_count'. Outputs the average value of the heartCount field for all items in the collection.
 */
public class AverageOfHeartCountCommand extends AbstractCommand{
    private CollectionManager collectionManager;

    public AverageOfHeartCountCommand(CollectionManager collectionManager) {
        super("average_of_heart_count", "вывести среднее значение поля heartCount для всех элементов коллекции");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     * @return Command exit status.
     */
    @Override
    public boolean execute(String argument, Object objectArgument) {
        try {
            if (!argument.isEmpty()) throw new WrongAmountOfParametersException();
            double average_of_heart_count = collectionManager.averageOfHealthCount();
            if (average_of_heart_count == 0) throw new EmptyCollectionException();
            System.out.println("Среднее значение поля heartCount всех космических десантов: " + average_of_heart_count);
            return true;
        } catch (WrongAmountOfParametersException exception) {
            System.out.println("У этой команды нет параметров!");
        } catch (EmptyCollectionException exception) {
            System.out.println("Коллекция пуста!");
        }
        return false;
    }
}
