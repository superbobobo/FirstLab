package org.example;

import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class FileFilterUtilityTest {

    private FileFilterUtility fileFilterUtility;
    private static final String TEST_OUTPUT_PATH = "test_output";

    @BeforeEach
    void setUp() {
        fileFilterUtility = new FileFilterUtility();
    }

    @AfterEach
    void tearDown() throws IOException {
        // Удаляем временную папку после каждого теста
        Files.walk(Paths.get(TEST_OUTPUT_PATH))
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
    }

    @Test
    void testParseArguments() {
        String[] args = {"-o", "custom_output", "-p", "test_", "-a", "-f"};
        fileFilterUtility.parseArguments(args);

        assertEquals("custom_output", fileFilterUtility.getOutputPath());
        assertEquals("test_", fileFilterUtility.getPrefix());
        assertTrue(fileFilterUtility.isAppendMode());
        assertTrue(fileFilterUtility.isFullStatistics());
    }

    @Test
    void testGetInputFiles() {
        String[] args = {"file1.txt", "file2.txt", "-o", "output", "-p", "test_"};
        List<String> inputFiles = fileFilterUtility.getInputFiles(args);

        assertEquals(2, inputFiles.size());
        assertTrue(inputFiles.contains("file1.txt"));
        assertTrue(inputFiles.contains("file2.txt"));
    }

    @Test
    void testCreateOutputDirectory() throws IOException {
        fileFilterUtility.parseArguments(new String[]{"-o", TEST_OUTPUT_PATH});
        fileFilterUtility.createOutputDirectory();

        assertTrue(Files.exists(Paths.get(TEST_OUTPUT_PATH)));
    }

    @Test
    void testWriteToFile() throws IOException {
        fileFilterUtility.parseArguments(new String[]{"-o", TEST_OUTPUT_PATH});
        fileFilterUtility.createOutputDirectory();

        fileFilterUtility.writeToFile("test.txt", "Hello, World!");

        List<String> lines = Files.readAllLines(Paths.get(TEST_OUTPUT_PATH, "test.txt"));
        assertEquals(1, lines.size());
        assertEquals("Hello, World!", lines.get(0));
    }

    @Test
    void testProcessLine() throws IOException {
        fileFilterUtility.parseArguments(new String[]{"-o", TEST_OUTPUT_PATH});
        fileFilterUtility.createOutputDirectory();

        Statistics stats = new Statistics();
        fileFilterUtility.processLine("123", stats);
        fileFilterUtility.processLine("45.67", stats);
        fileFilterUtility.processLine("test string", stats);

        assertTrue(Files.exists(Paths.get(TEST_OUTPUT_PATH, "integers.txt")));
        assertTrue(Files.exists(Paths.get(TEST_OUTPUT_PATH, "floats.txt")));
        assertTrue(Files.exists(Paths.get(TEST_OUTPUT_PATH, "strings.txt")));

        List<String> integers = Files.readAllLines(Paths.get(TEST_OUTPUT_PATH, "integers.txt"));
        List<String> floats = Files.readAllLines(Paths.get(TEST_OUTPUT_PATH, "floats.txt"));
        List<String> strings = Files.readAllLines(Paths.get(TEST_OUTPUT_PATH, "strings.txt"));

        assertTrue(integers.contains("123"));
        assertTrue(floats.contains("45.67"));
        assertTrue(strings.contains("test string"));
    }

    @Test
    void testProcessFile() throws IOException {
        // Создаем временный файл
        Path inputFile = Files.createFile(Paths.get("input.txt"));
        Files.write(inputFile, List.of("123", "45.67", "test string"));

        fileFilterUtility.parseArguments(new String[]{"-o", TEST_OUTPUT_PATH});
        fileFilterUtility.createOutputDirectory();

        Statistics stats = new Statistics();
        fileFilterUtility.processFile(inputFile.toString(), stats);

        assertTrue(Files.exists(Paths.get(TEST_OUTPUT_PATH, "integers.txt")));
        assertTrue(Files.exists(Paths.get(TEST_OUTPUT_PATH, "floats.txt")));
        assertTrue(Files.exists(Paths.get(TEST_OUTPUT_PATH, "strings.txt")));

        // Удаляем временный файл
        Files.deleteIfExists(inputFile);
    }

    @Test
    void testProcessFiles() throws IOException {
        // Создаем временные файлы
        Path inputFile1 = Files.createFile(Paths.get("input1.txt"));
        Path inputFile2 = Files.createFile(Paths.get("input2.txt"));
        Files.write(inputFile1, List.of("123", "45.67"));
        Files.write(inputFile2, List.of("test string", "987"));

        fileFilterUtility.processFiles(new String[]{"-o", TEST_OUTPUT_PATH, inputFile1.toString(), inputFile2.toString()});

        assertTrue(Files.exists(Paths.get(TEST_OUTPUT_PATH, "integers.txt")));
        assertTrue(Files.exists(Paths.get(TEST_OUTPUT_PATH, "floats.txt")));
        assertTrue(Files.exists(Paths.get(TEST_OUTPUT_PATH, "strings.txt")));

        // Удаляем временные файлы
        Files.deleteIfExists(inputFile1);
        Files.deleteIfExists(inputFile2);
    }
}