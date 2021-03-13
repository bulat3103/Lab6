package serverModule.commands;

import common.exceptions.WrongAmountOfParametersException;

/**
 * Command 'execute_script'. Executes scripts from a file. Actually only checks argument and prints messages.
 */
public class ExecuteScriptCommand extends AbstractCommand{

    public ExecuteScriptCommand() {
        super("execute_script <file_name>", "исполнить скрипт из указанного файла");
    }

    /**
     * Executes the command.
     * @return Command exit status.
     */
    @Override
    public boolean execute(String argument, Object objectArgument) {
        try {
            if (argument.isEmpty()) throw new WrongAmountOfParametersException();
            System.out.println("Скрипт выполняется!");
            return true;
        } catch (WrongAmountOfParametersException exception) {
            System.out.println("Вместе с этой командой должен быть передан параметр! Наберит 'help' для справки");
        }
        return false;
    }
}
