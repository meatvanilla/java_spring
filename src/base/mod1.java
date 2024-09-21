package base;

public class mod1 {
    //1) Напишите программу на Java, которая будет подсчитывать общую сумму чисел от 0 до 1000.
    // Суммировать необходимо лишь те числа, которые кратны 3 или 5.
    public static void zadanie1() {
        int number = 0;
        int sum = 0;
        while (number < 1001) {
            if ((number % 3 == 0) || (number % 5 == 0)) {
                sum = sum + number;
            }
            number++;
        }
        System.out.println("Сумма чисел, кратных 3 или 5: " + sum);
    }

    //2) Создайте двумерный массив, в котором найдите минимальный элемент среди всех элементов массива.
// Важно учесть, что нахождение минимального массива должен производиться при помощи циклов.
//Массив:
//int[][] x = { {20, 34, 2}, {9, 12, 18}, {3, 4, 5} };

    public static void zadanie2() {
        int[][] x = {
                {20, 34, 2},
                {9, 12, 18},
                {3, 4, 5}
        };
        int min = Integer.MAX_VALUE;  // Используем максимальное значение для корректного сравнения
        for (int i = 0; i < x.length; i++) {
            for (int j = 0; j < x[i].length; j++) {
                min = Math.min(x[i][j], min);
            }
        }
        System.out.println("Минимальный элемент массива: " + min);
    }

    public static void main(String[] args) {
        // Вызов первого задания
        zadanie1();

        // Вызов второго задания
        zadanie2();
    }
}
