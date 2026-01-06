package ru.packajes;

import java.util.Scanner;

public class InputManager {
    private final Scanner scanner;


    public InputManager() {
        this.scanner = new Scanner(System.in);

    }

    public String getInput(String text){
        if(!text.isEmpty()){
            System.out.print(text);
        }
        return scanner.nextLine();
    }
    public int getInt(String text){
        System.out.println(text);
        return Integer.parseInt(scanner.nextLine());
    }

    public int[] getIntArray(String text){
        System.out.println(text);
        String input = scanner.nextLine();
        String[] parts = input.trim().split("\\s+");
        int[] array = new int[parts.length];

        for (int i=0;i<parts.length;i++){
            array[i] = Integer.parseInt(parts[i]);
        }
        return array;
    }

    public void close(){
        scanner.close();
    }
}
