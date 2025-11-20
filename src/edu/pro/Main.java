package edu.pro;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;

public class Main {

    private static final int TOP_WORDS_COUNT = 30;
    private static final String FILE_PATH = "src/edu/pro/txt/harry.txt";

    static class WordFrequency {
        String word;
        int frequency;

        WordFrequency(String word, int frequency) {
            this.word = word;
            this.frequency = frequency;
        }

        @Override
        public String toString() {
            return word + " " + frequency;
        }
    }

    /**
     * Читає та очищає текст з файлу
     */
    private static String loadAndCleanText(String filePath) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(filePath)));
        return content.replaceAll("[^A-Za-z ]", " ").toLowerCase(Locale.ROOT);
    }

    /**
     * Розбиває текст на слова
     */
    private static String[] tokenize(String text) {
        return text.split(" +");
    }

    /**
     * Витягує унікальні слова з відсортованого масиву
     */
    private static String[] extractDistinctWords(String[] sortedWords) {
        String distinctString = " ";
        for (String word : sortedWords) {
            if (!distinctString.contains(word)) {
                distinctString += word + " ";
            }
        }
        return distinctString.split(" ");
    }

    /**
     * Рахує частоту кожного унікального слова
     */
    private static int[] calculateFrequencies(String[] distinctWords, String[] allWords) {
        int[] frequencies = new int[distinctWords.length];

        for (int i = 0; i < distinctWords.length; i++) {
            int count = 0;
            for (String word : allWords) {
                if (distinctWords[i].equals(word)) {
                    count++;
                }
            }
            frequencies[i] = count;
        }

        return frequencies;
    }

    /**
     * Комбінує слова з їх частотами
     */
    private static WordFrequency[] combineWordsWithFrequencies(String[] words, int[] frequencies) {
        WordFrequency[] result = new WordFrequency[words.length];

        for (int i = 0; i < words.length; i++) {
            result[i] = new WordFrequency(words[i], frequencies[i]);
        }

        return result;
    }

    /**
     * Виводить топ N слів за частотою
     */
    private static void printTopWords(WordFrequency[] wordFrequencies, int topCount) {
        Arrays.sort(wordFrequencies, Comparator.comparingInt(wf -> wf.frequency));

        System.out.println("Top " + topCount + " most frequent words:");
        for (int i = 0; i < topCount && i < wordFrequencies.length; i++) {
            System.out.println(wordFrequencies[wordFrequencies.length - 1 - i]);
        }
    }

    public static void main(String[] args) throws IOException {
        LocalDateTime start = LocalDateTime.now();

        // 1. Завантажити та очистити текст
        String cleanedText = loadAndCleanText(FILE_PATH);

        // 2. Токенізувати
        String[] words = tokenize(cleanedText);

        // 3. Відсортувати для групування
        Arrays.sort(words);

        // 4. Знайти унікальні слова
        String[] distinctWords = extractDistinctWords(words);

        // 5. Порахувати частоти
        int[] frequencies = calculateFrequencies(distinctWords, words);

        // 6. Створити об'єкти WordFrequency
        WordFrequency[] wordFrequencies = combineWordsWithFrequencies(distinctWords, frequencies);

        // 7. Вивести топ слів
        printTopWords(wordFrequencies, TOP_WORDS_COUNT);

        LocalDateTime finish = LocalDateTime.now();
        System.out.println("------");
        System.out.println("Execution time: " + ChronoUnit.MILLIS.between(start, finish) + " ms");
    }
}
