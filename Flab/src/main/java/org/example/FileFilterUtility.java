package org.example;

import java.io.*;
import java.util.*;

public class FileFilterUtility {
    private String outputPath = "output"; // По умолчанию файлы создаются в папке "output"
    private String prefix = "";
    private boolean appendMode = false; // По умолчанию файлы перезаписываются
    private boolean fullStatistics = false;

    public void processFiles(String[] args) throws IOException {
        parseArguments(args);
        List<String> inputFiles = getInputFiles(args);

        // Создаем папку output, если она не существует
        createOutputDirectory();

        Statistics stats = new Statistics();

        for (String file : inputFiles) {
            processFile(file, stats);
        }

        stats.printStatistics(fullStatistics);
    }

    public void parseArguments(String[] args) {
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-o":
                    outputPath = args[++i]; // Если указан путь через -o, используем его
                    break;
                case "-p":
                    prefix = args[++i];
                    break;
                case "-a":
                    appendMode = true; // Включаем режим добавления
                    break;
                case "-s":
                    fullStatistics = false;
                    break;
                case "-f":
                    fullStatistics = true;
                    break;
            }
        }
    }

    public List<String> getInputFiles(String[] args) {
        List<String> files = new ArrayList<>();
        for (String arg : args) {
            if (!arg.startsWith("-")) {
                files.add(arg);
            }
        }
        return files;
    }

    public void processFile(String filePath, Statistics stats) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                processLine(line, stats);
            }
        } catch (FileNotFoundException e) {
            throw new IOException("Файл не найден: " + filePath, e);
        } catch (IOException e) {
            throw new IOException("Ошибка при чтении файла: " + filePath, e);
        }
    }

    public void processLine(String line, Statistics stats) throws IOException {
        if (line.matches("-?\\d+")) {
            int number = Integer.parseInt(line);
            stats.addInteger(number);
            writeToFile(prefix + "integers.txt", line);
        } else if (line.matches("-?\\d+(\\.\\d+)?")) {
            double number = Double.parseDouble(line);
            stats.addFloat(number);
            writeToFile(prefix + "floats.txt", line);
        } else {
            stats.addString(line);
            writeToFile(prefix + "strings.txt", line);
        }
    }

    public void writeToFile(String fileName, String content) throws IOException {
        File file = new File(outputPath, fileName);

        // Если appendMode = false, файл будет перезаписан
        // Если appendMode = true, данные будут добавлены в конец файла
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, appendMode))) {
            writer.write(content);
            writer.newLine();
        } catch (IOException e) {
            throw new IOException("Ошибка при записи в файл: " + file.getAbsolutePath(), e);
        }
    }

    public void createOutputDirectory() throws IOException {
        File outputDir = new File(outputPath);
        if (!outputDir.exists()) {
            if (!outputDir.mkdirs()) {
                throw new IOException("Не удалось создать папку: " + outputDir.getAbsolutePath());
            }
        }
    }

    // Геттеры для тестирования
    public String getOutputPath() {
        return outputPath;
    }

    public String getPrefix() {
        return prefix;
    }

    public boolean isAppendMode() {
        return appendMode;
    }

    public boolean isFullStatistics() {
        return fullStatistics;
    }
}