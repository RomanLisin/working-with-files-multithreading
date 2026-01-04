package ru.packajes.files;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.io.IOException;
import java.util.concurrent.Phaser;

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
}
