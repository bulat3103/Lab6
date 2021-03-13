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
            System.out.println("Ошибка записи. Проверьте права на данный файл!");
            return;
        }
        try (PrintWriter pw = new PrintWriter(this.fileName)){
            pw.write(gson.toJson(collection));
            System.out.println("Коллекция успешна сохранена в файл!");
        } catch (IllegalStateException exception) {
            System.out.println("Ошибка считывания. Проверьте права на данный файл!");
        } catch (FileNotFoundException e) {
            System.out.println("Загрузочный файл не найден!");
        }
    }

    /**
     * Reads collection from a file.
     * @return Readed collection.
     */
    public TreeMap<Integer, SpaceMarine> readCollection() {
        File file = new File(this.fileName);
        if (!file.canRead()) {
            System.out.println("Ошибка считывания. Проверьте права на данный файл!");
            return new TreeMap<>();
        }
        try (Scanner fileScanner = new Scanner(new File(this.fileName))){
            TreeMap<Integer, SpaceMarine> collection;
            Type collectionType = new TypeToken<TreeMap<Integer, SpaceMarine>>() {}.getType();
            collection = gson.fromJson(fileScanner.nextLine().trim(), collectionType);
            System.out.println("Коллекция успешна загружена!");
            return collection;
        } catch (FileNotFoundException e) {
            System.out.println("Загрузочный файл не найден!");
        } catch (NoSuchElementException e) {
            System.out.println("Загрузочный файл пуст!");
        } catch (JsonParseException | NullPointerException exception) {
            System.out.println("В загрузочном файле не обнаружена необходимая коллекция!");
        }
        return new TreeMap<>();
    }
}
