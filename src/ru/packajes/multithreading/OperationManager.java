package ru.packajes.multithreading;

import ru.packajes.FileManager;
import ru.packajes.InputManager;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OperationManager {
    private InputManager inputManager;


    public OperationManager(InputManager inputManager) {
        this.inputManager = inputManager;
    }

    public int[] generateNumbers(int size){
        int[] numbers = new int[size];
        Random random = new Random();
        for (int i =0; i<size;i++){
            numbers[i] = random.nextInt(100);
        }
        return numbers;
    }
    public String arrayToString(int[] array){
        StringBuilder str = new StringBuilder();
        for(int num : array) {
           // str = num + " ";
            str.append(num).append(" ");
        }
        return str.toString().trim();
    }

    public BigDecimal findSum(int[] array){

       BigDecimal sum = BigDecimal.ZERO;
       for(int num : array){
           sum = sum.add(BigDecimal.valueOf(num));
       }
       return  sum;
    }
    public BigDecimal findAverage(int[] array){
        //  BigDecimal array = BigDecimal.valueOf(inputManager.getIntArray(array).length);
        //return findSum(array).divide(array.toString(), 2, RoundingMode.HALF_UP);
        if(array.length == 0) return  BigDecimal.ZERO;
        return findSum(array).divide(BigDecimal.valueOf(array.length),
                2, // точность после запятой
                RoundingMode.HALF_UP // режим округления
                );
    }

    public boolean isPrime(int number){
        if (number <= 1) return false;
        if (number <= 3) return true;
        if (number%2 == 0 || number%3 ==0) return false;
        for(int i=5; i*i<= number; i+=6){
            if(number % i == 0 || number % (i+2) == 0) return false;
        }
        return true;
    }

    public BigInteger factorial(int n){
        if (n < 0) return BigInteger.ZERO;
        if (n==0 || n==1) return BigInteger.ONE;

        BigInteger result = BigInteger.ONE;
        for (int i=2; i<=n; i++){
            result = result.multiply(BigInteger.valueOf(i));
        }
        return result;
    }
    // получение списка простых чисел
    public List<Integer> findPrimes(int[] numbers){
        List<Integer> primes = new ArrayList<>();
        for (int num : numbers) {
            if (isPrime(num)) {
                primes.add(num);
            }
        }
        return primes;
    }

    public List<String> calculateFactorials(int[] numbers) {
        List<String> factorials = new ArrayList<>();
        for (int num : numbers){
            factorials.add(num + "! = " + factorial(num));
        }
        return factorials;
    }
    public List<String> findFilesByExtension(List<String> fileList, String extension) {
        List<String> result  = new ArrayList<>();
        for (String file : fileList) {
            if (file.toLowerCase().endsWith(extension.toLowerCase())) {
                result.add(file);
            }

        }
        return result;
    }
}
