package serverModule;

import serverModule.commands.*;
import serverModule.utility.CollectionManager;
import serverModule.utility.CommandManager;
import serverModule.utility.FileManager;
import serverModule.utility.RequestManager;

import java.io.IOException;

public class App {
    public static final int PORT = 1821;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        FileManager fileManager = new FileManager("data.json");
        CollectionManager collectionManager = new CollectionManager(fileManager);
        CommandManager commandManager = new CommandManager(new HelpCommand(),
                new InfoCommand(collectionManager),
                new ShowCommand(collectionManager),
                new InsertCommand(collectionManager),
                new UpdateCommand(collectionManager),
                new RemoveKeyCommand(collectionManager),
                new ClearCommand(collectionManager),
                new ExecuteScriptCommand(),
                new ExitCommand(),
                new RemoveGreaterCommand(collectionManager),
                new HistoryCommand(),
                new RemoveLowerKeyCommand(collectionManager),
                new RemoveAllByWeaponTypeCommand(collectionManager),
                new SumOfHealthCommand(collectionManager),
                new AverageOfHeartCountCommand(collectionManager));
        RequestManager requestManager = new RequestManager(commandManager);
        Server server = new Server(8090, requestManager);
        server.run();
        collectionManager.saveCollection();
    }
}
