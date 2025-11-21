package edu.pro;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

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
    }

    private static String loadAndCleanText(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)))
                .replaceAll("[^A-Za-z ]", " ")
                .toLowerCase(Locale.ROOT);
    }

    /**
     * Рахує частоти слів через Map
     * Не зберігаємо окремі масиви distincts[] і freq[]
     */
    private static Map<String, Integer> calculateFrequencies(String[] words) {
        Map<String, Integer> frequencyMap = new HashMap<>();

        for (String word : words) {
            if (!word.isEmpty()) {
                frequencyMap.merge(word, 1, Integer::sum);
            }
        }

        return frequencyMap;
    }

    /**
     * Використовує PriorityQueue для топ-N замість сортування всього масиву
     * Тримає тільки TOP_WORDS_COUNT елементів
     */
    private static List<WordFrequency> getTopWords(Map<String, Integer> frequencies, int topCount) {
        PriorityQueue<WordFrequency> minHeap = new PriorityQueue<>(
                topCount,
                Comparator.comparingInt(wf -> wf.frequency)
        );

        for (Map.Entry<String, Integer> entry : frequencies.entrySet()) {
            WordFrequency wf = new WordFrequency(entry.getKey(), entry.getValue());

            if (minHeap.size() < topCount) {
                minHeap.offer(wf);
            } else if (wf.frequency > minHeap.peek().frequency) {
                minHeap.poll();
                minHeap.offer(wf);
            }
        }

        List<WordFrequency> result = new ArrayList<>(minHeap);
        result.sort(Comparator.comparingInt((WordFrequency wf) -> wf.frequency).reversed());

        return result;
    }

    private static void printTopWords(List<WordFrequency> topWords) {
        System.out.println("Top " + topWords.size() + " most frequent words:");
        for (WordFrequency wf : topWords) {
            System.out.println(wf.word + " " + wf.frequency);
        }
    }

    public static void main(String[] args) throws IOException {
        LocalDateTime start = LocalDateTime.now();

        String cleanedText = loadAndCleanText(FILE_PATH);
        String[] words = cleanedText.split(" +");

        // HashMap замість двох масивів - економія пам'яті
        Map<String, Integer> frequencies = calculateFrequencies(words);

        // Очищаємо масив words
        words = null;
        System.gc();

        // PriorityQueue для топ-N замість сортування всього
        List<WordFrequency> topWords = getTopWords(frequencies, TOP_WORDS_COUNT);

        printTopWords(topWords);

        LocalDateTime finish = LocalDateTime.now();
        System.out.println("------");
        System.out.println("Execution time: " + ChronoUnit.MILLIS.between(start, finish) + " ms");
    }
}