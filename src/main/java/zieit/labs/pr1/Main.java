package zieit.labs.pr1;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        // выводим приглашение на ввод своего имени
        System.out.println("What is your name?");
        // создаем массив байт длинной в 50 элементов
        byte[] b = new byte[50];
        // считываем имя ввеленное пользователем в созданый массив
        // запоминам в переменную длинну имени
        int kolSymbol = System.in.read(b);
        // содаем объект Строка и передаем в нее массив
        String s = new String(b);
        // выводим подстроку с именем начиная с 1го символа до количества символов
        System.out.println("hello " + s.substring(0, kolSymbol));
    }

}
