package ru.packajes.files;

import ru.packajes.FileManager;
import ru.packajes.InputManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CorporationSystem {
    private final List<Employee> employees;
    private final InputManager inputManager;
    private final FileManager fileManager;
    private int nextId;

    public CorporationSystem( InputManager inputManager, FileManager fileManager) {
        this.employees = new ArrayList<>();
        this.inputManager = inputManager;
        this.fileManager = fileManager;
        this.nextId = 1;
    }

    // основной метод запуска
    public void run() throws IOException {

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
        System.out.println("5.  Поиск сотрудника по фамилии");
        System.out.println("6.  Поиск сотрудников по указанным возрасту или первой букве фамилии");
        System.out.println("7.  Сохранить данные");
        System.out.println("8.  Экспорт результатов");
        System.out.println("0.  Выход");
    }

    private boolean processMenuChoice(String choice) throws IOException {
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
            System.out.printf("%-5s %-20s %-15s %-6s %-20s %-15s\n",
                    "ID", "Фамилия", "Имя", "Возвраст", "Должность", "Отдел");
            employees.forEach(emp -> {
                System.out.printf("%-5d %-20s %-15s %-6d %-20s %-15s\n",
                        emp.getId(),
                        emp.getLastName(),
                        emp.getFirstName(),
                        emp.getAge(),
                        emp.getPosition(),
                        emp.getDepartment());
            });
            System.out.println("Всего сотрудников: " + employees.size());
        }
    }

    private void addEmployee(){
        System.out.println("Добавление нового сотрудника");

        try {
            String lastName = inputManager.getInput("Фамилия: ");
            String firstName = inputManager.getInput("Имя: ");
            int age = inputManager.getInt("Возвраст: ");
            String position = inputManager.getInput("Должность: ");
            String department = inputManager.getInput("Отдел: ");
            // валидация данных
            if (lastName.isEmpty() || firstName.isEmpty()){
                System.out.println("Ошибка: Фамилия и имя обязательны!");
                return;
            }
            if (age < 18 || age > 90){
                System.out.println("Ошибка: Возраст должен быть в пределах от 18 до 90 лет!");
            }
            Employee newEmployee = new Employee(
                    nextId++,
                    lastName.trim(),
                    firstName.trim(),
                    age,
                    position.trim(),
                    department.trim()
            );
            employees.add(newEmployee);
            System.out.println("Сотрудник успешно добавлен!");
            System.out.println("ID нового сотрудника: " + newEmployee.getId());
        } catch (Exception e) {
            System.out.println("Ошибка при добавлении нового сотрудника: " + e.getMessage());
        }
    }

    private void editEmployee(){
        System.out.println("Редактирование сотрудника");
        displayAllEmployees();

        int id = inputManager.getInt("Введите ID сотрудника для редактирования: ");

        Employee emp = findEmployeeById(id);
        if (emp == null) {
            System.out.println("Сотрудник с ID " + id + " не найден.");
            return;
        }

        System.out.println("Текущие данные: " + emp);
        System.out.println("Введите новые данные (оставьте пустым, чтобы не менять):");

        String lastName = inputManager.getInput("Фамилия [" + emp.getLastName() + "]: ");
        if (!lastName.isEmpty()) emp.setLastName(lastName);

        String firstName = inputManager.getInput("Имя [" + emp.getFirstName() + "]: ");
        if (!firstName.isEmpty()) emp.setFirstName(firstName);

        String ageStr = inputManager.getInput("Возраст [" + emp.getAge() + "]: ");
        if (!ageStr.isEmpty()) emp.setAge(Integer.parseInt(ageStr));

        String position = inputManager.getInput("Должность [" + emp.getPosition() + "]: ");
        if (!position.isEmpty()) emp.setPosition(position);

        String department = inputManager.getInput("Отдел [" + emp.getDepartment() + "]: ");
        if (!department.isEmpty()) emp.setDepartment(department);

        System.out.println("Данные сотрудника обновлены!");
    }
    private void deleteEmployee(){
        System.out.println("Удаление сотрудника");
        displayAllEmployees();

        int id = inputManager.getInt("Введите ID сотрудника для удаления: ");

        Employee emp = findEmployeeById(id);
        if (emp == null) {
            System.out.println("Сотрудник с ID " + id + " не найден.");
            return;
        }

        employees.remove(emp);
        System.out.println("Сотрудник успешно удален!");
    }
    private void searchEmployee() throws IOException {
        // System.out.println("Поиск сотрудника по фамилии");
        String lastName = inputManager.getInput("Введите фамилию для поиска: ");

        List<Employee> results = new ArrayList<>();
        for (Employee emp : employees) {
            if (emp.getLastName().equalsIgnoreCase(lastName)) {
                results.add(emp);
            }
        }

        if (results.isEmpty()) {
            System.out.println("Сотрудники с фамилией '" + lastName + "' не найдены.");
        } else {
            System.out.println("Найдено " + results.size() + " сотрудников:");
            System.out.println();


            for (Employee emp : results) {
                System.out.println(emp);
            }
            String select = inputManager.getInput("Хотите сохранить найденную информацию в файл (Y/N)?: ");
            if(select.equalsIgnoreCase("Y")){
                String fileForSaveFindEmploys = inputManager.getInput("Куда сохранить найденных сотрудников (введите путь к файлу): ");
                fileManager.writeAllLinesByCleanOld(fileForSaveFindEmploys, String.valueOf(results));
            }
        }
    }

    private void advancedSearch() throws IOException {
        System.out.println("Расширенный поиск по возрасту или по первой букве фамилии");
        System.out.println("1. По возрасту");
        System.out.println("2. По первой букве фамилии");
        System.out.print("Выберите критерий: ");

        String choice = inputManager.getInput("");

        List<Employee> results = new ArrayList<>();

        if (choice.equals("1")) {
            int age = inputManager.getInt("Введите возраст: ");
            for (Employee emp : employees) {
                if (emp.getAge() == age) {
                    results.add(emp);
                }
            }
            System.out.println("Сотрудники в возрасте " + age + " лет:");
        } else if (choice.equals("2")) {
            String letter = inputManager.getInput("Введите первую букву фамилии: ");
            if (letter.length() > 0) {
                char firstChar = letter.toUpperCase().charAt(0);
                for (Employee emp : employees) {
                    if (emp.getLastName().toUpperCase().charAt(0) == firstChar) {
                        results.add(emp);
                    }
                }
                System.out.println("Сотрудники, чья фамилия начинается на '" + letter + "':");
            }
        }

        if (results.isEmpty()) {
            System.out.println("Сотрудники не найдены.");
        } else {
            for (Employee emp : results) {
                System.out.println(emp);
                String select = inputManager.getInput("Хотите сохранить найденную информацию в файл (Y/N)?: ");
                if(select.equalsIgnoreCase("Y")){
                    String fileForSaveFindEmploys = inputManager.getInput("Куда сохранить найденных сотрудников (введите путь к файлу): ");
                    fileManager.writeAllLinesByCleanOld(fileForSaveFindEmploys, String.valueOf(results));
                }
            }
        }
    }
    private void saveAllEmployees() throws IOException {
        System.out.println("Сохранение данных");
        String filePath = inputManager.getInput("Введите путь к файлу для сохранения: ");

        List<String> lines = new ArrayList<>();
        for (Employee emp : employees) {
            lines.add(emp.toFileString());
        }

        fileManager.writeAllLines(filePath, lines);
        System.out.println("Все сотрудники сохранены в файл.");
    }

    private void autoSaveOnExit() throws IOException {
        // System.out.println("Автосохранение при выходе");
        String filePath = inputManager.getInput("Введите путь к файлу для автоматического сохранения: ");

        List<String> lines = new ArrayList<>();
        for (Employee emp : employees) {
            lines.add(emp.toFileString());
        }

        fileManager.writeAllLines(filePath, lines);
        System.out.println("Данные автоматически сохранены при выходе.");
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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CorporationSystem that)) return false;
        return nextId == that.nextId && Objects.equals(employees, that.employees) && Objects.equals(inputManager, that.inputManager) && Objects.equals(fileManager, that.fileManager);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employees, inputManager, fileManager, nextId);
    }
}
