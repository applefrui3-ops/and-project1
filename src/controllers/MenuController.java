package controllers;

public class MenuController {

    public void showMainMenu(){
        System.out.println("\nSelect a menu item: (enter the number of the selected menu item)\n");
        System.out.println("Main menu:");
        System.out.println("    1. apartments");
        System.out.println("    2. clients");
        System.out.println("    0. close application");
        System.out.print("\n->");
    }

    public void showApartmentMenu(){
        System.out.println("\nSelect a menu item: (enter the number of the selected menu item)\n");
        System.out.println("Apartments:");
        System.out.println("    1. show list");
        System.out.println("    2. edit");
        System.out.println("    0. get back");
        System.out.print("\n->");
    }

    public void showClientMenu(){
        System.out.println("\nSelect a menu item: (enter the number of the selected menu item)\n");
        System.out.println("Clients:");
        System.out.println("    1. show list");
        System.out.println("    0. get back");
        System.out.print("\n->");
    }

}
