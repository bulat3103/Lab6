package serverModule.commands;

import common.exceptions.WrongAmountOfParametersException;

/**
 * Command 'exit'. Checks for wrong arguments then do nothing.
 */
public class ExitCommand extends AbstractCommand {

    public ExitCommand() {
        super("exit", "завершить программу (без сохранения в файл)");
    }

    /**
     * Executes the command.
     * @return Command exit status.
     */
    @Override
    public boolean execute(String argument, Object objectArgument) {
        try {
            if (!argument.isEmpty() || objectArgument != null) throw new WrongAmountOfParametersException();
            return true;
        } catch (WrongAmountOfParametersException exception) {
            System.out.println("У этой команды нет параметров!");
        }
        return false;
    }
}
