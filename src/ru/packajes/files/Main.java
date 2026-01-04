package ru.packajes.files;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import ru.packajes.files.FileManager;
import ru.packajes.files.InputManager;

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
        System.out.println("Выберите задание (1-5): ");

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
                    task5();
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

          }

        private static void task2(InputManager input, FileManager fileManager){
            System.out.println("Задание 2: Поиск самой длинной строки");
            String filePath = input.getInput("Введите путь к файлу: ");
        }

        private static void task3(InputManager input, FileManager fileManager){
            System.out.println("Задание 3: Обработка массивов из файлов");
            String filePath = input.getInput("Введите путь к файлу: ");

        }
        }

        private static void task4(InputManager input, FileManager fileManager){
            System.out.println("Задание 4: Сохранение массива в файл");
            String filePath = input.getInput("Введите путь к файлу: ");
            int[] inputNum = input.getIntArray("Введите элементы массива через пробел: ");


        }

        private static void task5(){

        }
}

