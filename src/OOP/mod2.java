package OOP;

import java.util.Random;

enum VARIANTS {
    ROCK, //КАМЕНЬ
    PAPER, //БУМАГА
    SCISSORS; //НОЖНИЦЫ
}

class Player {
    private VARIANTS choice; //Выбор игрока (камень, ножницы, бумага)
    private String name; //Имя игрока

    public Player() {
        this.choice = getRandomVariant(); //Случайный выбор для бота
        this.name = "Bot"; //Имя по умолчанию
    }

    //Конструктор для создания игрока с заданным вариантом и именем
    public Player(VARIANTS choice, String name) {
        this.choice = choice;
        this.name = name;
    }

    //Метод для получения случайного значения из перечисления VARIANTS
    private VARIANTS getRandomVariant() {
        VARIANTS[] values = VARIANTS.values();
        int randomIndex = new Random().nextInt(values.length);
        return values[randomIndex];
    }

    //Метод для определения победителя
    public String whoWins(Player p1, Player p2) {
        if (p1.choice == p2.choice) {
            return "Ничья";
        }

        //Определение победителя по правилам игры
        if ((p1.choice == VARIANTS.ROCK && p2.choice == VARIANTS.SCISSORS) ||
                (p1.choice == VARIANTS.SCISSORS && p2.choice == VARIANTS.PAPER) ||
                (p1.choice == VARIANTS.PAPER && p2.choice == VARIANTS.ROCK)) {
            return p1.name + " побеждает!";
        } else {
            return p2.name + " побеждает!";
        }
    }
}


public class mod2 {
    public static void main(String[] args) {

        Player bot = new Player(); //Бот со случайным выбором
        Player alex = new Player(VARIANTS.SCISSORS, "Alex"); //Alex с выбором ножниц

        //Результат
        System.out.println(bot.whoWins(bot, alex));
    }
}