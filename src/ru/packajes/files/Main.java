package ru.packajes.files;
import java.util.Scanner;

public class Main {

    void main() {
            Scanner input = new Scanner(System.in);

        System.out.println("Работа с файлами");
        System.out.println("1. Сравнение строк двух файлов");
        System.out.println("2. Поиск самой длинной строки в файле");
        System.out.println("3. Обработка массивов из файла");
        System.out.println("4. Сохранение массива в файл");
        System.out.println("5. Информационная система 'Корпорация'");
        System.out.println("Выберите задание (1-5): ");

        int choice = input.nextLine();

        switch (choice){
            case 1:
                task1();
                break;
            case 2:
                task2();
                break;
            case 3:
                task3();
                break;
            case 4:
                task4();
                break;
            case 5:
                task5();
                break;
            default:
                System.out.println("Неверный выбор!");

        }

        private static void task1(){
            System.out.println("Задание 1: Сравнение строк двух файлов");
            System.out.println("Введите путь к первому файлу: ");
            String filePath1 = input.nextLine();
            System.out.println("Введите путь ко второму файлу: ");
            String filePath2 = input.nextLine();
        }
        private static void task2(){
            System.out.println("Задание 2: Поиск самой длинной строки");
            System.out.println("Введите путь к файлу: ");
            String filePath = input.nextLine();
        }

        private static void task3(){
            System.out.println("Задание 3: Обработка массивов из файлов");
            System.out.println("Введите путь к файлу: ");
            String filePath = input.nextLine();
        }
        }

        private static void task4(){
            System.out.println("Задание 4: Сохранение массива в файл");
            System.out.println("Введите путь к файлу: ");
            String filePath1 = input.nextLine();
            System.out.println("Введите элементы массива через пробел");
            String inputNum = input.nextLine();
            String[] parts = inputNum.split(" ");
            int[] array = new int[parts.length];
            for(int i =0; i<parts.length; i++){
                array[i]= Integer.parseInt(parts[i]);
        }

        private static void task5(){

        }
    }
}
