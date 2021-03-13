package clientModule.utility;

import common.data.Chapter;
import common.data.Coordinates;
import common.data.Weapon;
import common.exceptions.ScriptRecursionException;
import common.exceptions.WrongAmountOfParametersException;
import common.utility.Request;
import common.utility.SpaceMarineLite;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Operates command input.
 */
public class Console {
    private Scanner scanner;
    private Stack<String> scriptFileNames = new Stack<>();

    public Console(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Mode for catching commands from user input.
     */
    public Request interactiveMode() {
        String[] userCommand = {"", ""};
        ProcessCode processCode = null;
        try {
            do {
                System.out.print("$ ");
                userCommand = (scanner.nextLine().trim() + " ").split(" ", 2);
                userCommand[1] = userCommand[1].trim();
                processCode = checkCommand(userCommand[0], userCommand[1]);
            } while (processCode == ProcessCode.ERROR || userCommand[0].isEmpty());
        } catch (Exception e) {

        }
        try {
            switch (processCode) {
                case OBJECT:
                    SpaceMarineLite marineToInsert = generateMarineToInsert();
                    return new Request(userCommand[0], userCommand[1], marineToInsert);
                case UPDATE_OBJECT:
                    SpaceMarineLite marineToUpdate = generateMarineToUpdate();
                    return new Request(userCommand[1], userCommand[1], marineToUpdate);
                case SCRIPT:
                    break;
            }
        } catch (Exception e) {

        }
        return new Request(userCommand[0], userCommand[1]);
    }

    /**
     * Launches the command.
     * @param command Command to launch.
     * @return Exit code.
     */
    private ProcessCode checkCommand(String command, String argument) {
        try {
            switch (command) {
                case "":
                    return ProcessCode.ERROR;
                case "help":
                case "show":
                case "info":
                case "clear":
                case "save":
                case "history":
                case "sum_of_health":
                case "average_of_heart_count":
                case "exit":
                    if (!argument.isEmpty()) throw new WrongAmountOfParametersException();
                    return ProcessCode.OK;
                case "insert":
                    if (argument.isEmpty()) throw new WrongAmountOfParametersException();
                    return ProcessCode.OBJECT;
                case "update":
                    if (argument.isEmpty()) throw new WrongAmountOfParametersException();
                    return ProcessCode.UPDATE_OBJECT;
                case "remove_key":
                case "remove_lower_key":
                case "remove_all_by_weapon_type":
                    if (argument.isEmpty()) throw new WrongAmountOfParametersException();
                    return ProcessCode.OK;
                case "execute_script":
                    if (argument.isEmpty()) throw new WrongAmountOfParametersException();
                    return ProcessCode.SCRIPT;
                case "remove_greater":
                    if (!argument.isEmpty()) throw new WrongAmountOfParametersException();
                    return ProcessCode.OBJECT;
                default:
                    System.out.println("Команда '" + command + "' не найдена. Наберите 'help' для справки.");
                    return ProcessCode.ERROR;
            }
        } catch (WrongAmountOfParametersException e) {
            System.out.println("Проверьте правильность ввода аргументов!");
        }
        return ProcessCode.OK;
    }

    private SpaceMarineLite generateMarineToInsert() {
        SpaceMarineBuilder builder = new SpaceMarineBuilder(scanner);
        return new SpaceMarineLite(
                builder.askName(),
                builder.askCoordinates(),
                builder.askHealth(),
                builder.askHeartCount(),
                builder.askAchievements(),
                builder.askWeapon(),
                builder.askChapter()
        );
    }

    private SpaceMarineLite generateMarineToUpdate() {
        SpaceMarineBuilder builder = new SpaceMarineBuilder(scanner);
        String name = builder.askAboutChangingField("Хотите изменить имя космического солдата?") ?
                builder.askName() : null;
        Coordinates coordinates = builder.askAboutChangingField("Хотите изменить координаты космического солдата?") ?
                builder.askCoordinates() : null;
        int health = builder.askAboutChangingField("Хотите изменить здоровье космического солдата?") ?
                builder.askHealth() : -1;
        Integer heartCount = builder.askAboutChangingField("Хотите изменить количество сердец космического солдата?") ?
                builder.askHeartCount() : -1;
        String achievements = builder.askAboutChangingField("Хотите изменить достижения космического солдата?") ?
                builder.askAchievements() : null;
        Weapon weapon = builder.askAboutChangingField("Хотите изменить оружие космического солдата?") ?
                builder.askWeapon() : null;
        Chapter chapter = builder.askAboutChangingField("Хотите изменить часть, к которой принадлежит космический солдат?") ?
                builder.askChapter() : null;
        return new SpaceMarineLite(
                name,
                coordinates,
                health,
                heartCount,
                achievements,
                weapon,
                chapter
        );
    }
}
