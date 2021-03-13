package serverModule.commands;

import common.exceptions.WrongAmountOfParametersException;

/**
 * Command 'history'. Checks for wrong arguments then do nothing.
 */
public class HistoryCommand extends AbstractCommand{

    public HistoryCommand() {
        super("history", "вывести историю использованных команд");
    }

    /**
     * Executes the command.
     * @return Command exit status.
     */
    @Override
    public boolean execute(String argument, Object objectArgument) {
        try {
            if (!argument.isEmpty()) throw new WrongAmountOfParametersException();
            return true;
        } catch (WrongAmountOfParametersException exception) {
            System.out.println("У этой команды нет параметров!");
        }
        return false;
    }
}
