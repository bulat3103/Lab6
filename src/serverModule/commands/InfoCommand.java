package serverModule.commands;

import common.exceptions.WrongAmountOfParametersException;
import serverModule.utility.CollectionManager;

import java.time.LocalDateTime;

public class InfoCommand extends AbstractCommand{
    private CollectionManager collectionManager;

    public InfoCommand(CollectionManager collectionManager) {
        super("info", "вывести информацию о коллекции");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     * @return Command exit status.
     */
    @Override
    public boolean execute(String argument, Object objectArgument) {
        try{
            if (!argument.isEmpty()) throw new WrongAmountOfParametersException();
            LocalDateTime lastInitTime = collectionManager.getLastInitTime();
            String lastInitTimeString = (lastInitTime == null) ? "в данной сессии инициализации еще не происходило" :
                                        lastInitTime.toLocalDate().toString() + " " + lastInitTime.toLocalTime().toString();
            System.out.println("Информация о коллекции:");
            System.out.println(" Тип: " + collectionManager.collectionType());
            System.out.println(" Количество элементов: " + collectionManager.collectionSize());
            System.out.println(" Дата последней инициализации: " + lastInitTimeString);
        } catch (WrongAmountOfParametersException exception) {
            System.out.println("У этой команды нет параметров!");
        }
        return false;
    }
}
