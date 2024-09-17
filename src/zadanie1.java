//1) Напишите программу на Java, которая будет подсчитывать общую сумму чисел от 0 до 1000.
// Суммировать необходимо лишь те числа, которые кратны 3 или 5.
public class zadanie1 {
    public static void main(String[] args) {
        int number = 0;
        int sum = 0;
        while (number < 1001) {
            if ((number % 3 == 0)||(number % 5 == 0)){
                sum = sum + number;
            }
            number++;
        }
        System.out.println(sum);
    }
}
