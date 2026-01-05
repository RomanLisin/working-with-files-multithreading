package ru.packajes.multithreading;

import ru.packajes.FileManager;

import java.util.Random;

public class OperationManager {
    FileManager fileManager;

    public int[] generateNumbers(int size){
        Random random = new Random();
        int[] numbers = new int[size];

        for (int i =0; i<size;i++){
            numbers[i] = random.nextInt();
        }
        return numbers;
    }
    public String findMax(String... arrLines){
        arrLines.
        int max = 0;

    }
}
