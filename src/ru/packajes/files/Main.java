package ru.packajes.files;
import java.io.IOException;
import java.util.List;

import ru.packajes.FileManager;
import ru.packajes.InputManager;

public class Main {

    public static void main(String[] args){
        //Scanner input = new Scanner(System.in);
        InputManager inputManager = new InputManager();
        FileManager fileManager = new FileManager();

        System.out.println("Работа с файлами");
        System.out.println("1. Сравнение строк двух файлов");
        System.out.println("2. Поиск самой длинной строки в файле");
        System.out.println("3. Обработка массивов из файла");
        System.out.println("4. Сохранение массива в файл");
        System.out.println("5. Информационная система 'Корпорация'");
        System.out.print("Выберите задание (1-5): ");

        //int choice = input.nextLine();
        String choice = inputManager.getInput("");

        try {
            switch (choice) {
                case "1":
                    task1(inputManager, fileManager);
                    break;
                case "2":
                    task2(inputManager, fileManager);
                    break;
                case "3":
                    task3(inputManager, fileManager);
                    break;
                case "4":
                    task4(inputManager, fileManager);
                    break;
                case "5":
                    task5(inputManager, fileManager);
                    break;
                default:
                    System.out.println("Неверный выбор!");

            }
        } catch (IOException e) {
            System.out.println("Ошибка работы с файлом: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Ошибка формата числа: " + e.getMessage());
        }
        inputManager.close();
    }

        private static void task1(InputManager input, FileManager fileManager) throws IOException {
            System.out.println("Задание 1: Сравнение строк двух файлов");
            String filePath1 = input.getInput("Введите путь к первому файлу: ");
            String filePath2 =input.getInput("Введите путь ко второму файлу: ");
            List<String> lines1 = fileManager.readAllLines(filePath1);
            List<String> lines2 = fileManager.readAllLines(filePath2);
            int minLen = Math.min(lines1.size(), lines2.size());

            boolean allMatch = true;
            for (int i=0; i<minLen ;i++) {
                if (!lines2.get(i).equals(lines1.get(i))) {
                    System.out.println("\nСтроки не совпадают: ");
                    System.out.println("Файл 1, строка " + (i+1) + ": " + lines1.get(i));
                    System.out.println("Файл 2, строка " + (i+2) + ": " + lines2.get(i));
                    allMatch = false;
                }
            }
            if (allMatch && lines1.size() == lines2.size()) {
                System.out.println("Все строки совпадают.");
            } else if (lines1.size() != lines2.size()) {
                System.out.println("Файлы имеют разное количество строк.");

            }
          }

        private static void task2(InputManager input, FileManager fileManager) throws IOException {
            System.out.println("Задание 2: Поиск самой длинной строки");
            String filePath = input.getInput("Введите путь к файлу: ");

            String[] result = fileManager.findLongestLine(filePath); //  первый - длина, второй -  сама строка

            System.out.println("Длина самой длинной строки: " + result[0]);
            System.out.println("Самая длинная строка:\n" + result[1]);

        }


        private static void task3(InputManager input, FileManager fileManager) throws IOException {
            System.out.println("Задание 3: Обработка массивов из файлов");
            String filePath = input.getInput("Введите путь к файлу: ");

            List<int[]> arrays = fileManager.readArraysFromFile(filePath);

            int totalSum = 0;
            int globalMax = Integer.MIN_VALUE;
            int globalMin = Integer.MAX_VALUE;

            for (int i = 0; i < arrays.size(); i++) {
                int[] array = arrays.get(i);
                int arraySum = 0;
                int arrayMax = array.length > 0 ? array[0] : 0;
                int arrayMin = array.length > 0 ? array[0] : 0;

                System.out.println("\nМассив " + (i + 1) + ": ");
                for (int j = 0; j < array.length; j++) {
                    if (array[j] > arrayMax) {
                        arrayMax = array[j];
                    }
                    if (array[j] < arrayMin) {
                        arrayMin = array[j];
                    }

                    if (array[j] > globalMax) {
                        globalMax = array[j];
                    }
                    if (array[j] < globalMin) {
                        globalMin = array[j];
                    }

                    arraySum += array[j];
                }
                totalSum += arraySum;

                System.out.println("\n Сумма: " + arraySum);
                System.out.println(" Максимум: " + arrayMax);
                System.out.println(" Минимум: " + arrayMin);
            }
            System.out.println("\nОбщая сумма всех массивов: " + totalSum);
            System.out.println("Глобальный максимум: " + globalMax);
            System.out.println("Глобальный минимум: " + globalMin);

        }


        private static void task4(InputManager input, FileManager fileManager) throws IOException {
            System.out.println("Задание 4: Сохранение массива в файл");
            String filePath = input.getInput("Введите путь к файлу: ");
            int[] inputNum = input.getIntArray("Введите элементы массива через пробел: ");

            String originalArray = arrayToString(inputNum);
            String evenNumbers = getEvenNumbers(inputNum);
            String oddNumbers = getOddNumbers(inputNum);
            String reversedArray = getReversedArray(inputNum);

            fileManager.writeAllLinesByCleanOld(filePath,
                    originalArray,
                    evenNumbers,
                    oddNumbers,
                    reversedArray
            );
            System.out.println("Массив успешно сохранен в файл.");

        }

        private static void task5(InputManager input, FileManager fileManager) throws  IOException{
            System.out.println("\nИнформационная система \"Корпорация\"");
            CorporationSystem corporationSystem = new CorporationSystem(input, fileManager);
            corporationSystem.run();
        }

        private static String arrayToString(int[] array) {
            if (array.length ==0) return "";
            StringBuilder sb = new StringBuilder(); // быстрее чем ""
            for (int i = 0; i< array.length; i++) {
                sb.append(array[i]);
                if (i < array.length - 1) {
                    sb.append(" ");
                }
            }
            return sb.toString();
        }

        private static  String getEvenNumbers(int[] array) {
            if (array == null || array.length == 0) {
                return "Нет четных чисел";
            }
            StringBuilder sb = new StringBuilder();

            for (int value : array) {
                if (value % 2 == 0) {
                    if (sb.length() > 0) {
                        sb.append(" ");
                    }
                    sb.append(value);
                }
            }
                return sb.length() > 0 ? sb.toString():"Нет четных чисел.";
        }

        private static String getOddNumbers(int[] array) {
            if (array == null || array.length == 0) {
                return "Нет нечетных чисел";
            }
            StringBuilder sb = new StringBuilder();

            for (int value : array) {
                if (value % 2 != 0) {
                    if (sb.length() > 0) {
                        sb.append(" ");
                    }
                    sb.append(value);
                }
            }
            return sb.length() > 0 ? sb.toString():"Нет нечетных чисел.";
        }
        private static String getReversedArray(int[] array) {
                if (array.length == 0) return "";
                StringBuilder sb = new StringBuilder();
                for(int i = array.length - 1; i >=0; i--) {
                    sb.append(array[i]);
                        if( i>0 ) {
                            sb.append(" ");
                        }
                }
                return sb.toString();
        }

}
