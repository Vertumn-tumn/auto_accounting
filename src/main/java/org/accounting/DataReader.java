package org.accounting;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataReader {
    public Map<String, String> readMonthlyReports(String monthlyReportsPath) {
        Set<String> files = listFilesFromDirectory(monthlyReportsPath);
        if (files.isEmpty()) {
            System.out.println("Вы не положили отчёты в директорию для прочтения!");
            System.out.println("Положите их по адресу - C:/Users/admin/Documents/monthReport");
            return new HashMap<>();
        } else return getMonthlyMapWithFilesContent(files, monthlyReportsPath);
    }


    public Map<String, String> readYearlyReports(String yearlyReportsPath) {
        Set<String> files = listFilesFromDirectory(yearlyReportsPath);
        if (files.isEmpty()) {
            System.out.println("Вы не положили отчёты в директорию для прочтения!");
            System.out.println("Положите их по адресу - C:/Users/admin/Documents/yearReport");
            return new HashMap<>();
        }
        return getYearlyMapWithFilesContent(files, yearlyReportsPath);
    }

    Map<String, String> getYearlyMapWithFilesContent(Set<String> files, String yearlyReportsPath) {
        Map<String, String> content = new HashMap<>();
        String file = files.stream().findFirst().get();
        String fileContent = readFileContentOrNull(yearlyReportsPath + "/" + file);
        String fileName = file.substring(2, 6) + " год";
        content.put(fileName, fileContent);
        return content;
    }

    private Map<String, String> getMonthlyMapWithFilesContent(Set<String> files, String monthlyReportsPath) {
        Map<String, String> content = new HashMap<>();
        files.forEach(file -> {
            String fileContent = readFileContentOrNull(monthlyReportsPath + "/" + file);
            String fileNameCode = file.substring(6, 8);
            String fileName = DataUtil.months.get(fileNameCode);
            content.put(fileName, fileContent);
        });
        return content;
    }

    private String readFileContentOrNull(String path) {
        try {
            Charset windows1251 = Charset.forName("Windows-1251");
            return Files.readString(Path.of(path), windows1251);
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с ежемесячным отчётом.");
            return null;
        }
    }

    // Метод для получения списка файлов из директории
    public Set<String> listFilesFromDirectory(String path) {
        try (Stream<Path> stream = Files.list(Paths.get(path))) {
            return stream
                    .filter(file -> !Files.isDirectory(file)) // Исключаем директории
                    .map(Path::getFileName) // Получаем только имена файлов
                    .filter(this::isValidFile) // Применяем фильтрацию
                    .map(Path::toString) // Преобразуем в строку
                    .collect(Collectors.toSet()); // Собираем в Set
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Метод для фильтрации файлов
    private boolean isValidFile(Path path) {
        String fileName = path.toString();
        if (fileName.startsWith("m.20") && fileName.endsWith(".txt")) {
            return true;
        } else return fileName.startsWith("y.20") && fileName.endsWith(".txt");
    }
}
