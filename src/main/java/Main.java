import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    static AtomicInteger wordsLengthThree = new AtomicInteger(0);
    static AtomicInteger wordsLengthFour = new AtomicInteger(0);
    static AtomicInteger wordsLengthFive = new AtomicInteger(0);

    final static int wordCount = 100_000;
    final static String originalString = "abc";
    final static int minWordLength = 3;
    final static int coefficientMaxWordLength = 3;

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[wordCount];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText(originalString, minWordLength + random.nextInt(coefficientMaxWordLength));
        }

        Thread polyndromeThread = new Thread(() -> {
            for (String text : texts) {
                if (isPolyndrome(text)) counterChanger(text);
            }
        });
        polyndromeThread.start();
        polyndromeThread.join();

        Thread oneLetterWordThread = new Thread(() -> {
            for (String text : texts) {
                if (isOneLetterWord(text)) counterChanger(text);
            }
        });
        oneLetterWordThread.start();
        oneLetterWordThread.join();

        Thread ascendingLettersThread = new Thread(() -> {
            for (String text : texts) {
                if (isAscendingLetters(text)) counterChanger(text);
            }
        });
        ascendingLettersThread.start();
        ascendingLettersThread.join();

        System.out.printf("""
                Красивых слов с длиной 3: %s шт
                Красивых слов с длиной 4: %s шт
                Красивых слов с длиной 5: %s шт
                """, wordsLengthThree, wordsLengthFour, wordsLengthFive);
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static boolean isPolyndrome(String str) {
        if (str == null || str.isEmpty()) return false;
        String reverse = new StringBuilder(str).reverse().toString();
        return str.equals(reverse);
    }

    public static boolean isOneLetterWord(String str) {
        if (str == null || str.isEmpty()) return false;
        char ch = str.charAt(0);
        for (int i = 1; i < str.length(); i++) {
            if (str.charAt(i) != ch) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAscendingLetters(String str) {
        if (str == null || str.isEmpty()) return false;
        char ch = str.charAt(0);
        for (int i = 1; i < str.length(); i++) {
            if (ch > str.charAt(i)) {
                return false;
            }
            ch = str.charAt(i);
        }
        return true;
    }

    private static void counterChanger(String str) {
        if (str.length() == 3) {
            wordsLengthThree.incrementAndGet();
        } else if (str.length() == 4) {
            wordsLengthFour.incrementAndGet();
        } else {
            wordsLengthFive.incrementAndGet();
        }
    }
}
