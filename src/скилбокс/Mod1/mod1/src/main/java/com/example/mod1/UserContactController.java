package com.example.mod1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.util.Scanner;

@Controller
public class UserContactController implements CommandLineRunner {

    private final UserContactService userContactService;

    @Autowired
    public UserContactController(UserContactService userContactService) {
        this.userContactService = userContactService;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Добавить контакт");
            System.out.println("2. Удалить контакт по email");
            System.out.println("3. Показать все контакты");
            System.out.println("4. Выйти");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.println("Введите ФИО, номер телефона и email через ';'");
                    String input = scanner.nextLine();
                    String[] data = input.split(";");
                    if (data.length == 3) {
                        userContactService.addUserContact(new UserContact(data[0], data[1], data[2]));
                    } else {
                        System.out.println("Неправильный ввод.");
                    }
                    break;
                case "2":
                    System.out.println("Введите email контакта для удаления:");
                    String email = scanner.nextLine();
                    if (userContactService.removeContactByEmail(email)) {
                        System.out.println("Контакт удален.");
                    } else {
                        System.out.println("Контакт не найден.");
                    }
                    break;
                case "3":
                    System.out.println("Список контактов:");
                    userContactService.listAllContacts().forEach(System.out::println);
                    break;
                case "4":
                    System.out.println("Выход...");
                    return;
                default:
                    System.out.println("Неверный выбор.");
            }
        }
    }
}