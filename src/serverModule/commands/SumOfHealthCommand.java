package serverModule.commands;

import common.exceptions.EmptyCollectionException;
import common.exceptions.WrongAmountOfParametersException;
import serverModule.utility.CollectionManager;

/**
 * Command 'sum_of_health'. Prints the sum of health of all marines.
 */
public class SumOfHealthCommand extends AbstractCommand{
    private CollectionManager collectionManager;

    public SumOfHealthCommand(CollectionManager collectionManager) {
        super("sum_of_health", "вывести сумму значений поля health для всех элементов коллекции");
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
            double sum_of_health = collectionManager.getSumOfHealth();
            if (sum_of_health == 0) throw new EmptyCollectionException();
            System.out.println("Сумма здоровья всех космических десантов: " + sum_of_health);
            return true;
        } catch (WrongAmountOfParametersException exception) {
            System.out.println("У этой команды нет параметров!");
        } catch (EmptyCollectionException exception) {
            System.out.println("Коллекция пуста!");
        }
        return false;
    }
}
