package ru.packajes;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import java.io.IOException;
import java.util.regex.Pattern;

public class FileManager {

    public List<String> readAllLines(String filePath) throws IOException {
        Path path = Path.of(filePath);
        return Files.readAllLines(path);
    }

    public void writeAllLines(String filePath, List<String> lines) throws IOException {
        Path path = Path.of(filePath);
        Files.write(path, lines);
    }

    public void writeAllLinesByCleanOld(String filePath, String... lines) throws IOException{
        Path path = Path.of(filePath);
        Files.write(path, List.of(lines), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    public void appendLines(String filePath, List<String> lines) throws  IOException {
        Path path = Path.of(filePath);
        Files.write(path, lines, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }

    public List<int[]> readArraysFromFile(String filePath) throws IOException {
       // Path path = Path.of(filePath);
        List<String> lines = this.readAllLines(filePath);
        List<int[]> arrays = new ArrayList<>();

        for(String line : lines){
            if (line.trim().isEmpty()) continue;

            String[] parts = line.trim().split("\\s+");
            int[] array = new int[parts.length];

            for (int i=0 ; i<parts.length; i++){
                array[i] = Integer.parseInt(parts[i]);
            }
            arrays.add(array);
        }
        return arrays;
    }

    public String[] findLongestLine(String filePath) throws IOException{
        List<String> lines = this.readAllLines(filePath);
        String longestLine = "";
        int maxLen = 0;
        
        for(String line : lines){
            //if (line.trim().isEmpty()) continue;
            if (line.length() > maxLen){
                maxLen = line.length();
                longestLine = line;
            }
        }

        return new String[]{String.valueOf(maxLen), longestLine};
    }
    public void writeNumbersToFile(String filePath, int[] numbers) throws IOException {
        List<String> lines = new ArrayList<>();
        StringBuilder line = new StringBuilder();
        for (int i = 0 ; i < numbers.length; i++) {
            line.append(numbers[i]).append(" ");
            if ((i  + 1) % 10 == 0 || i == numbers.length - 1) {
                lines.add(line.toString().trim());
                line = new StringBuilder();
            }
        }

        writeAllLines(filePath, lines);
    }

    public int[] readNumbersFromFile(String filePath) throws IOException {
        List<String> lines = readAllLines(filePath);
        List<Integer> numbersList = new ArrayList<>();

        for (String line : lines) {
            if (line.trim().isEmpty()) continue;
            String[] parts = line.trim().split("\\s+");
            for (String part : parts) {
                try {
                    numbersList.add(Integer.parseInt(part));
                } catch (NumberFormatException e) {
                    // пропускаем некорректные числа
                }
            }
        }
        int[] numbers = new int[numbersList.size()];
        for (int i = 0; i < numbersList.size(); i++) {
            numbers[i] = numbersList.get(i);
        }
        return numbers;
    }
    public void copyDirectory(String sourceDir, String targetDir) throws IOException {
        Path sourcePath = Paths.get(sourceDir);
        Path targetPath = Paths.get(targetDir);
        Files.walk(sourcePath)
                .forEach(source -> {
                    try {
                        Path target = targetPath.resolve(sourcePath.relativize(source));
                        if (Files.isDirectory(source)) {
                            if (!Files.exists(target)) {
                                Files.createDirectories(target);
                            }
                        } else {
                            Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
                        }
                    } catch (IOException e) {
                        System.out.println("Ошибка: " + e.getMessage());
                    }
                });
    }

    // получение списка всех файлов в директориях и поддиректорияъ
    public List<Path> getAllFilesInDirectory(String directoryPath) throws IOException {
        List<Path> files = new ArrayList<>();
        Path startPath = Paths.get(directoryPath);

        Files.walk(startPath)
                .filter(Files::isRegularFile)
                .forEach(files::add);

        return files;
    }

    public List<Path> findFilesContainingWord(String directoryPath, String searchWord) throws IOException {
        List<Path> result = new ArrayList<>();
        List<Path> allFiles = getAllFilesInDirectory(directoryPath);
        for (Path file : allFiles) {
            try {
                String content = new String(Files.readAllBytes(file));
                if (content.contains(searchWord)) {
                    result.add(file);
                }
            } catch (IOException e) {
                //  пропускаем файлы, которые не удалось прочитать
            }
        }
        return result;
    }
    // объединение фалйов в один
    public void mergeFiles(List<Path> files, String outputFile) throws IOException {
        List<String> allLines = new ArrayList<>();

        for (Path file : files) {
            List<String> lines = Files.readAllLines(file);
            allLines.add("Файл: " + file.getFileName());
            allLines.addAll(lines);
            allLines.add(""); // пустая строка между файлами
        }
        Files.write(Paths.get(outputFile), allLines);
    }
    // удаление запрещенных слов из файлы
    public void removeForbiddenWords(String inputFile, String forbiddenWordsFile, String outputFile) throws IOException {
        List<String> forbiddenWords = readAllLines(forbiddenWordsFile);
        List<String> inputLines = readAllLines(inputFile);
        List<String> outputLines = new ArrayList<>();

        for (String line : inputLines) {
            String modifiedLine = line;
            for (String word : forbiddenWords) {
                if (!word.trim().isEmpty()) {
                    modifiedLine = modifiedLine.replaceAll("(?i)\\b" + Pattern.quote(word.trim()) + "\\b", "Удалено");
                }
            }
            outputLines.add(modifiedLine);
        }
        writeAllLines(outputFile, outputLines);
    }

    public DirectoryStats getDirectoryStats(String directoryPath) throws IOException {
        DirectoryStats stats = new DirectoryStats();
        Path startPath = Paths.get(directoryPath);

        Files.walkFileTree(startPath, new SimpleFileVisitor<Path>() { // анонимный класс
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException { // BasicFileAttributes для того, чтобы файл не открывать несколько раз
                stats.fileCount++;
                stats.totalSize += attrs.size();
                String fileName = file.getFileName().toString();
                int dotIndex = fileName.lastIndexOf('.');
                if (dotIndex > 0) {
                    String extension = fileName.substring(dotIndex + 1).toLowerCase();
                    stats.fileTypes.add(extension);
                }

                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                if (!dir.equals(startPath)) {
                    stats.directoryCount++;
                }
                return FileVisitResult.CONTINUE;
            }
        });
        return stats;
    }
}
