package hellofx;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Leaderboard {

    // Use a safe writable location for the score file
    public static final String SCORE_FILE =
            System.getProperty("user.home") + "/snake_scores.txt";

    /**
     * Load the top 10 scores from the file
     */
    public static List<Integer> loadTop() {
    List<Integer> list = new ArrayList<>();
    File f = new File(SCORE_FILE);

    if (!f.exists()) return list;

    try (BufferedReader br = new BufferedReader(new FileReader(f))) {
        String line;
        while ((line = br.readLine()) != null) {
            try {
                int score = Integer.parseInt(line.trim());

                // Ignore zero / negative
                if (score <= 0) continue;

                // Ignore duplicates
                if (!list.contains(score)) {
                    list.add(score);
                }

            } catch (NumberFormatException ignored) {}
        }
    } catch (IOException e) {
        e.printStackTrace();
    }

    Collections.sort(list, Collections.reverseOrder());

    if (list.size() > 10) {
        list = new ArrayList<>(list.subList(0, 10));
    }

    return list;
}


    /**
     * Add a new score and save to file
     */
    public static void addScore(int score) {
    if (score <= 0) return;

    List<Integer> list = loadTop();

    if (list.contains(score)) return;

    list.add(score);
    Collections.sort(list, Collections.reverseOrder());

    if (list.size() > 10) {
        list = new ArrayList<>(list.subList(0, 10));
    }

    try (PrintWriter pw = new PrintWriter(new FileWriter(SCORE_FILE))) {
        for (int s : list) {
            pw.println(s);
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}
}
