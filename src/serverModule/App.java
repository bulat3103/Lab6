package serverModule;

import serverModule.commands.*;
import serverModule.utility.*;

import java.io.IOException;

public class App {
    public static final int PORT = 8090;

    public static void main(String[] args) {
        FileManager fileManager = new FileManager();
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
                new SaveCommand(collectionManager),
                new SumOfHealthCommand(collectionManager),
                new AverageOfHeartCountCommand(collectionManager),
                new LoadCollectionCommand(collectionManager));
        RequestManager requestManager = new RequestManager(commandManager);
        Server server = new Server(PORT, requestManager);
        server.run();
        collectionManager.saveCollection();
    }
}
