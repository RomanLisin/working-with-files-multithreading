package ru.packajes.files;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class CorporationSystem {
    private List<Employee> employees;
    private InputManager inputManager;
    private FileManager fileManager;
    private int nextId;

    public CorporationSystem( InputManager inputManager, FileManager fileManager) {
        this.employees = new ArrayList<>();
        this.inputManager = inputManager;
        this.fileManager = fileManager;
        this.nextId = 1;
    }

    // основной метод запуска
    public void run() {

        loadEmployeesOnStart();

        boolean running = true;
        while (running) {
            displayMainMenu();
            String choice = inputManager.getInput("Выберите действие: ");
            running = processMenuChoice(choice);
        }

        autoSaveOnExit();
    }

    private void displayMainMenu(){
        System.out.println("Информационная система <<Корпорация>>");
        System.out.println("1.  Просмотр всех сотрудников");
        System.out.println("2.  Добавить сотрудника");
        System.out.println("3.  Редактировать сотрудника");
        System.out.println("4.  Удалить сотрудника");
        System.out.println("5.  Поиск сотрудника");
        System.out.println("6.  Расширенный поиск");
        System.out.println("7.  Сохранить данные");
        System.out.println("8.  Экспорт результатов");
        System.out.println("0.  Выход");
    }

    private boolean processMenuChoice(String choice){
        switch (choice){
            case "1":
                displayAllEmployees();
                break;
            case "2":
                addEmployee();
                break;
            case "3":
                editEmployee();
                break;
            case "4":
                deleteEmployee();
                break;
            case "5":
                searchEmployee();
                break;
            case "6":
                advancedSearch();
                break;
            case "7":
                saveAllEmployees();
                break;
            case "8":
                exportSearchResults();
                break;
            case "0":
                return false;
            default:
                System.out.println("Неправильно выбранное действие.");
        }
        return true;
    }

    private void loadEmployeesOnStart(){
        String filePath = inputManager.getInput("Введите путь к файлу для загрузки: ");

        try {
            List<String> lines = fileManager.readAllLines(filePath);
            for (String line : lines){
                Employee emp = Employee.fromFileString(line);
                employees.add(emp);
                if(emp.getAge() >= nextId){
                    nextId = emp.getId() + 1;
                }
            }
            System.out.println("Загружено " + employees.size() + " сотрудников. ");
        } catch (IOException e) {
            System.out.println("Файл не найден. Добавим новый файл.");
            System.out.println("Создайте новый файл или введите данные: ");
        }
    }

    private void displayAllEmployees(){
        System.out.println("Список всех сотрудников: ");
        if(employees.isEmpty()){
            System.out.println("Список сотрудиков пуст.");
        } else {
            employees.forEach(System.out::println);
        }
    }

    private void addEmployee(){
        System.out.println("Добавление нового сотрудника");
    }

    private void editEmployee(){
        System.out.println("Редактирование сотрудника");
    }
    private void deleteEmployee(){
        System.out.println("Удаление сотрудника");
    }
    private void searchEmployee(){
        System.out.println("Поиск сотрудника");
    }

    private void advancedSearch(){
        System.out.println("Расширенный поиск");
    }
    private void saveAllEmployees(){
        System.out.println("Сохранение данных");
    }
    private void autoSaveOnExit(){
        System.out.println("Автосохранение при выходе");
    }

    private void exportSearchResults(){
        System.out.println("Экспорт результата");
    }
    private Employee findEmployeeById(int id) {
//        for (int i = 0; i < employees.size(); i++) {
//            Employee employee = employees.get(i);
//            if (employee.getId() == id) {
//                return employee;
//            }
//        }
//        return null;
        return employees.stream()
                .filter(e -> e.getId() == id)
                .findFirst()// берем первого попавшегося если есь
                .orElse(null);
    }

}
