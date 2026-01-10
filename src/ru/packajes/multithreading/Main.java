package ru.packajes.multithreading;

import ru.packajes.FileManager;
import ru.packajes.InputManager;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.nio.file.Path;


public class Main {

    void main() {
        InputManager inputManager = new InputManager();
        OperationManager operationManager = new OperationManager(inputManager);
        FileManager fileManager = new FileManager();

        System.out.println("1. Потоки с массивом чисел");
        System.out.println("2. Потоки с файлами чисел");
        System.out.println("3. Копирование директории");
        System.out.println("4. Поиск и фильтрация файлов");
        System.out.print("Выберите задание (1-4): ");

        String choice = inputManager.getInput("");

        try {
            switch (choice) {
                case "1" -> task1(operationManager);
                case "2" -> task2(inputManager, operationManager, fileManager);
                case "3" -> task3(inputManager, fileManager);
                case "4" -> task4(inputManager, fileManager);
                default -> System.out.println("Неверный выбор!");
            }
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
        inputManager.close();
    }

    private static void task1(OperationManager operationManager) {
        int arraySize = 10;
        System.out.println("Генерируем массив из " + arraySize + " чисел");
        int[] numbers = operationManager.generateNumbers(arraySize);

        System.out.println("Массив создан: " + operationManager.arrayToString(numbers));

        Thread sumThread = new Thread(() -> {
                try {
                    Thread.sleep(100);
                    BigDecimal sum = operationManager.findSum(numbers);
                    System.out.println("Поток суммы: сумма = " + sum);
                } catch (Exception e) {
                    System.out.println("Ошибка в потоке суммы: " + e.getMessage());
                }
        });

        Thread averageThread = new Thread(() -> {
                try {
                    Thread.sleep(100);
                    BigDecimal average = operationManager.findAverage(numbers);
                    System.out.println("Поток среднего: среднее = " + average);
                } catch (Exception e) {
                    System.out.println("Ошибка в потоке среднего: " + e.getMessage());
                }
        });
        System.out.println("\nЗапускаем потоки");
        sumThread.start();
        averageThread.start();

        // ждем завершения потоков
        try {
            sumThread.join();
            averageThread.join();
        } catch (Exception e) {
            System.out.println("Ошибка при ожидании потоков: " + e.getMessage());
        }
        System.out.println("\nВсе потоки завершили работу!");
    }

    private static void task2(InputManager inputManager, OperationManager operationManager, FileManager fileManager) throws IOException, IOException {

        String filePath = inputManager.getInput("Введите путь к файлу: ");

        int arraySize = 20;
        System.out.println("Генерируем " + arraySize + " чисел");
        int[] numbers = operationManager.generateNumbers(arraySize);

        fileManager.writeNumbersToFile(filePath, numbers);
        System.out.println("Числа записаны в файл");

        // поток для простых чисел
        Thread primeThread = new Thread(() -> {
                try {
                    Thread.sleep(100); // ждем немного
                    int[] fileNumbers = fileManager.readNumbersFromFile(filePath);
                    List<Integer> primes = operationManager.findPrimes(fileNumbers);
                    String primeFile = "primes.txt";
                    List<String> lines = new ArrayList<>();
                    lines.add("Простые числа: " + primes.size());
                    for (int prime : primes) {
                        lines.add(String.valueOf(prime));
                    }
                    fileManager.writeAllLines(primeFile, lines);

                    System.out.println("Поток простых чисел: найдено " + primes.size() + " чисел");

                } catch (Exception e) {
                    System.out.println("Ошибка в потоке простых чисел: " + e.getMessage());
                }
        });

        // поток для факториалов
        Thread factorialThread = new Thread(() -> {
                try {
                    Thread.sleep(100);
                    int[] fileNumbers = fileManager.readNumbersFromFile(filePath);
                    int count = 0;
                    String factorialFile = "factorials.txt";
                    List<String> lines = new ArrayList<>();
                    lines.add("Факториалы чисел:");

                    for (int num : fileNumbers) {
                        if (num <= 10) {
                            java.math.BigInteger factorial = operationManager.factorial(num);
                            lines.add(num + "! = " + factorial);
                            count++;
                        }
                    }
                    fileManager.writeAllLines(factorialFile, lines);
                    System.out.println("Поток факториалов: посчитано " + count + " факториалов");
                } catch (Exception e) {
                    System.out.println("Ошибка в потоке факториалов: " + e.getMessage());
                }
        });

        System.out.println("\nЗапускаем потоки");
        primeThread.start();
        factorialThread.start();

        // ждем завершения
        try {
            primeThread.join();
            factorialThread.join();
        } catch (Exception e) {
            System.out.println("Ошибка при ожидании потоков: " + e.getMessage());
        }

        System.out.println("Всего чисел: " + arraySize);
        System.out.println("Простые числа сохранены в: primes.txt");
        System.out.println("Факториалы сохранены в: factorials.txt");
    }

    private static void task3(InputManager inputManager, FileManager fileManager) throws IOException {

        String sourceDir = inputManager.getInput("Введите исходную директорию: ");
        String targetDir = inputManager.getInput("Введите новую директорию: ");

        Thread copyThread = new Thread(() -> {
                try {
                    System.out.println("Начинаем копирование");
                    fileManager.copyDirectory(sourceDir, targetDir);
                    System.out.println("Копирование завершено!");
                } catch (Exception e) {
                    System.out.println("Ошибка при копировании: " + e.getMessage());
                }
        });
        System.out.println("\nЗапускаем поток копирования");
        copyThread.start();

        try {
            copyThread.join();
        } catch (Exception e) {
            System.out.println("Ошибка при ожидании потока: " + e.getMessage());
        }

        System.out.println("Скопировано из: " + sourceDir);
        System.out.println("Скопировано в: " + targetDir);
    }

    private static void task4(InputManager inputManager, FileManager fileManager) throws IOException {

        String directoryPath = inputManager.getInput("Введите директорию для поиска: ");
        String searchWord = inputManager.getInput("Введите слово для поиска: ");

        String forbiddenFile = "forbidden_words.txt";
        List<String> forbiddenWords = Arrays.asList("плохо", "ошибка", "проблема");
        fileManager.writeAllLines(forbiddenFile, forbiddenWords);
        System.out.println("Создан файл с запрещенными словами: " + forbiddenFile);

        // поток для поиска файлов
        Thread searchThread = new Thread(() -> {
            try {
                System.out.println("Поток поиска: ищем файлы");

                // получаем все файлы
                List<Path> allFiles = fileManager.getAllFilesInDirectory(directoryPath);
                System.out.println("Всего файлов в директории: " + allFiles.size());

                // ищем файлы с нужным словом
                List<Path> foundFiles = new ArrayList<>();
                for (Path file : allFiles) {
                    try {
                        String content = new String(Files.readAllBytes(file));
                        if (content.contains(searchWord)) {
                            foundFiles.add(file);
                        }
                    } catch (Exception e) {
                        // пропускаем файлы, которые не читаются
                    }
                }
                System.out.println("Найдено файлов с словом '" + searchWord + "': " + foundFiles.size());
                // объединяем содержимое
                if (!foundFiles.isEmpty()) {
                    String mergedFile = "merged.txt";
                    List<String> allLines = new ArrayList<>();

                    for (Path file : foundFiles) {
                        allLines.add("Файл: " + file.getFileName());
                        List<String> lines = Files.readAllLines(file);
                        allLines.addAll(lines);
                        allLines.add("");
                    }

                    Files.write(Paths.get(mergedFile), allLines);
                    System.out.println("Содержимое объединено в: " + mergedFile);
                }

            } catch (Exception e) {
                System.out.println("Ошибка в потоке поиска: " + e.getMessage());
            }
        });

        // поток для фильтрации
        Thread filterThread = new Thread(() -> {
                try {
                    System.out.println("Поток фильтрации: ждем завершения поиска");

                    // ждем завершения поиска
                    searchThread.join();

                    System.out.println("Поток фильтрации: начинаем фильтрацию");

                    // читаем запрещенные слова
                    List<String> forbidden = fileManager.readAllLines(forbiddenFile);

                    // читаем объединенный файл
                    String mergedFile = "merged.txt";
                    if (Files.exists(Paths.get(mergedFile))) {
                        List<String> lines = fileManager.readAllLines(mergedFile);

                        // удаляем запрещенные слова
                        List<String> filteredLines = new ArrayList<>();
                        for (String line : lines) {
                            String filteredLine = line;
                            for (String word : forbidden) {
                                filteredLine = filteredLine.replace(word, "***");
                            }
                            filteredLines.add(filteredLine);
                        }

                        // записываем результат
                        String resultFile = "filtered.txt";
                        fileManager.writeAllLines(resultFile, filteredLines);
                        System.out.println("Результат фильтрации сохранен в: " + resultFile);
                    } else {
                        System.out.println("Файл для фильтрации не найден");
                    }

                } catch (Exception e) {
                    System.out.println("Ошибка в потоке фильтрации: " + e.getMessage());
                }
        });

        System.out.println("\nЗапускаем потоки");
        searchThread.start();
        filterThread.start();

        // ждем завершения
        try {
            filterThread.join();
        } catch (Exception e) {
            System.out.println("Ошибка при ожидании потоков: " + e.getMessage());
        }

        System.out.println("Поиск слова: " + searchWord);
        System.out.println("Запрещенные слова: " + forbiddenFile);
        System.out.println("Объединенный файл: merged.txt");
        System.out.println("Отфильтрованный файл: filtered.txt");
    }
}
