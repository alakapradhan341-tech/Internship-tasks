import java.io.*;     // For file handling (BufferedReader, FileReader)
import java.util.*;   // For data structures like Map, HashMap

public class RecommendationEngine {

    // Stores user -> (item -> rating)
    static Map<Integer, Map<Integer, Double>> data = new HashMap<>();

    // Method to load data from CSV file
    public static void loadData(String fileName) throws Exception {

        // Open file for reading
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line;

        // Read file line by line
        while ((line = br.readLine()) != null) {

            // Split each line using comma
            String[] parts = line.split(",");

            // Extract user ID, item ID, and rating
            int user = Integer.parseInt(parts[0]);
            int item = Integer.parseInt(parts[1]);
            double rating = Double.parseDouble(parts[2]);

            // If user not present, create new map for that user
            data.putIfAbsent(user, new HashMap<>());

            // Store item and rating for that user
            data.get(user).put(item, rating);
        }

        // Close file after reading
        br.close();
    }

    // Method to calculate similarity between two users (Cosine Similarity)
    public static double similarity(int user1, int user2) {

        // Get ratings of both users
        Map<Integer, Double> r1 = data.get(user1);
        Map<Integer, Double> r2 = data.get(user2);

        double dot = 0, norm1 = 0, norm2 = 0;

        // Calculate dot product and norm of user1
        for (int item : r1.keySet()) {

            // If both users rated same item
            if (r2.containsKey(item)) {
                dot += r1.get(item) * r2.get(item); // Multiply ratings
            }

            // Sum of squares of user1 ratings
            norm1 += Math.pow(r1.get(item), 2);
        }

        // Calculate norm of user2
        for (double val : r2.values()) {
            norm2 += Math.pow(val, 2);
        }

        // Avoid division by zero
        if (norm1 == 0 || norm2 == 0) return 0;

        // Return cosine similarity formula
        return dot / (Math.sqrt(norm1) * Math.sqrt(norm2));
    }

    // Method to recommend items for a given user
    public static void recommend(int user) {

        // Stores weighted scores of items
        Map<Integer, Double> score = new HashMap<>();

        // Stores sum of similarities
        Map<Integer, Double> simSum = new HashMap<>();

        // Loop through all other users
        for (int other : data.keySet()) {

            // Skip same user
            if (other == user) continue;

            // Calculate similarity between users
            double sim = similarity(user, other);

            // Loop through items rated by other user
            for (int item : data.get(other).keySet()) {

                // Recommend only items NOT rated by current user
                if (!data.get(user).containsKey(item)) {

                    // Add weighted score (similarity * rating)
                    score.put(item,
                        score.getOrDefault(item, 0.0) +
                        sim * data.get(other).get(item));

                    // Add similarity sum
                    simSum.put(item,
                        simSum.getOrDefault(item, 0.0) + sim);
                }
            }
        }

        // Print recommended items
        System.out.println("Recommended items for User " + user + ":");

        // Calculate final score for each item
        for (int item : score.keySet()) {

            // Normalize score
            double finalScore = score.get(item) / simSum.get(item);

            // Print result
            System.out.println("Item " + item + " -> Score: " + finalScore);
        }
    }

    // Main method (program starts here)
    public static void main(String[] args) throws Exception {

        // Load dataset from CSV file
        loadData("data.csv");

        // Get recommendations for user 1
        recommend(1);
    }
}