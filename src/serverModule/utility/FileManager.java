package serverModule.utility;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import common.data.SpaceMarine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * Operates the file for saving/loading collection.
 */
public class FileManager {
    private Gson gson = new Gson();
    private String fileName;

    public FileManager(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Writes collection to a file.
     * @param collection Collection to write.
     */
    public void writeCollection(TreeMap<Integer, SpaceMarine> collection) {
        File file = new File(this.fileName);
        if (!file.canRead()) {
            ResponseOutputer.append("Ошибка записи. Проверьте права на данный файл!\n");
            return;
        }
        try (PrintWriter pw = new PrintWriter(new File(this.fileName))){
            pw.write(gson.toJson(collection));
            ResponseOutputer.append("Коллекция успешна сохранена в файл!\n");
        } catch (IllegalStateException exception) {
            ResponseOutputer.append("Ошибка считывания. Проверьте права на данный файл!\n");
        } catch (FileNotFoundException e) {
            ResponseOutputer.append("Загрузочный файл не найден!\n");
        }
    }

    /**
     * Reads collection from a file.
     * @return Readed collection.
     */
    public TreeMap<Integer, SpaceMarine> readCollection() {
        File file = new File(this.fileName);
        if (!file.canRead()) {
            ResponseOutputer.append("Ошибка считывания. Проверьте права на данный файл!\n");
            return new TreeMap<>();
        }
        try (Scanner fileScanner = new Scanner(new File(this.fileName))){
            TreeMap<Integer, SpaceMarine> collection;
            Type collectionType = new TypeToken<TreeMap<Integer, SpaceMarine>>() {}.getType();
            collection = gson.fromJson(fileScanner.nextLine().trim(), collectionType);
            ResponseOutputer.append("Коллекция успешна загружена!\n");
            return collection;
        } catch (FileNotFoundException e) {
            ResponseOutputer.append("Загрузочный файл не найден!\n");
        } catch (NoSuchElementException e) {
            ResponseOutputer.append("Загрузочный файл пуст!\n");
        } catch (JsonParseException | NullPointerException exception) {
            ResponseOutputer.append("В загрузочном файле не обнаружена необходимая коллекция!\n");
        }
        return new TreeMap<>();
    }
}
