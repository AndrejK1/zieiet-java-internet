package zieit.labs.pr4;

import java.util.*;

public class Pr4 {
    private static final List<Integer> PRESET = List.of(23, 234, 45, 27, 2, 89, 34, 978, 5, -23);
    private static final List<String> WORDS = List.of("Cat", "Dog", "Chicken", "Tiger", "Horse");

    private static final String WIKI_TEXT = "Java (вимовляється Джава) — об'єктно-орієнтована мова програмування, " +
            "випущена 1995 року компанією «Sun Microsystems» як основний компонент платформи Java. " +
            "З 2009 року мовою займається компанія «Oracle», яка того року придбала «Sun Microsystems». " +
            "В офіційній реалізації Java-програми компілюються у байт-код, який при виконанні інтерпретується " +
            "віртуальною машиною для конкретної платформи. «Oracle» надає компілятор Java та віртуальну машину Java, " +
            "які задовольняють специфікації Java Community Process, під ліцензією GNU General Public License." +
            "Мова значно запозичила синтаксис із C і C++. Зокрема, взято за основу об'єктну модель С++, " +
            "проте її модифіковано. Усунуто можливість появи деяких конфліктних ситуацій, що могли виникнути " +
            "через помилки програміста та полегшено сам процес розроблення об'єктно-орієнтованих програм. " +
            "Ряд дій, які в С/C++ повинні здійснювати програмісти, доручено віртуальній машині. " +
            "Передусім Java розроблялась як платформо-незалежна мова, тому вона має менше низькорівневих можливостей " +
            "для роботи з апаратним забезпеченням, що в порівнянні, наприклад, з C++ зменшує швидкість роботи програм. " +
            "За необхідності таких дій Java дозволяє викликати підпрограми, написані іншими мовами програмування." +
            "Java вплинула на розвиток J++, що розроблялась компанією «Microsoft». " +
            "Роботу над J++ було зупинено через судовий позов «Sun Microsystems», " +
            "оскільки ця мова програмування була модифікацією Java. " +
            "Пізніше в новій платформі «Microsoft» .NET випустили J#, щоб полегшити міграцію програмістів J++ " +
            "або Java на нову платформу. З часом нова мова програмування С# стала основною мовою платформи, " +
            "перейнявши багато чого з Java. J# востаннє включався в версію Microsoft Visual Studio 2005. " +
            "Мова сценаріїв JavaScript має схожу із Java назву і синтаксис, але не пов'язана із Java.";

    public static void main(String[] args) {
        // bubble sort test
        List<Integer> integers = new ArrayList<>(PRESET);
        bubbleSort(integers);
        integers.forEach(i -> System.out.print(i + " "));
        System.out.println();

        // linked list iteration
        LinkedList<String> animals = new LinkedList<>(WORDS);

        // iteration using for each
        animals.forEach(animal -> System.out.print(animal + " "));
        System.out.println();
        // iteration in loop
        for (String animal : animals) {
            System.out.print(animal + " ");
        }
        System.out.println();
        // iteration using iterator
        animals.iterator()
                .forEachRemaining(animal -> System.out.print(animal + " "));
        System.out.println();

        // HashMap
        HashMap<String, String> peoples = new HashMap<>();
        peoples.put("Smith", "Oliver");
        peoples.put("Jones", "Jack");
        peoples.put("Davies", "Olivia");
        peoples.put("Wilson", "Mary");
        peoples.put("Evans", "Michael");
        peoples.forEach((name, surname) -> System.out.println(name + " " + surname));

        // HashSet
        String sanitizedText = WIKI_TEXT.replaceAll("[.,«»—()]", "");
        List<String> words = Arrays.asList(sanitizedText.split(" "));
        Set<String> uniqueWords = new HashSet<>(words);

        System.out.println("Слів у статті - " + words.size() + ", унікальних - " + uniqueWords.size());
    }

    public static <T extends Comparable<T>> void bubbleSort(List<T> list) {
        int size = list.size();

        for (int i = 0; i < size - 1; ++i) {
            for (int j = 0; j < size - i - 1; ++j) {
                if (list.get(j + 1).compareTo(list.get(j)) < 0) {
                    T swap = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, swap);
                }
            }
        }
    }

}
