public class AllGreensStats {

    public static void main(String[] args) {
        double[][] data = {
            {231, 156, 10, 519, 437, 487, 329, 195, 20, 68, 570, 428, 464, 15}, // x1
            {3, 2.2, 0.5, 5.5, 4.4, 4.9, 3.1, 2.5, 0.6, 1.2, 5.4, 4.2, 4.7, 0.6}, // x2
            {294, 232, 149, 560, 567, 581, 512, 347, 151, 102, 788, 577, 535, 163}, // x3
            {8.2, 6.9, 3.3, 10.6, 10.4, 11.8, 8.1, 7.7, 3.6, 4.9, 12.3, 10.5, 11.3, 2.5}, // x4
            {8.2, 4.1, 3.9, 16.1, 14.1, 12.7, 10.1, 8.2, 4.7, 4.9, 12.3, 14.0, 15.3, 2.5}, // x5
            {11, 12, 9, 16, 5, 4, 6, 12, 6, 8, 1, 7, 3, 14} // x6
        };

        String[] variableNames = {"x1", "x2", "x3", "x4", "x5", "x6"};
        int n = data.length;
        int numSamples = data[0].length;

        // Calculate means for each variable
        double[] means = new double[n];
        for (int i = 0; i < n; i++) {
            double sum = 0;
            for (double val : data[i]) {
                sum += val;
            }
            means[i] = sum / numSamples;
        }

        // Compute correlation coefficients
        double[][] r = new double[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                double sumXY = 0, sumX2 = 0, sumY2 = 0;
                for (int k = 0; k < numSamples; k++) {
                    double xi = data[i][k] - means[i];
                    double xj = data[j][k] - means[j];
                    sumXY += xi * xj;
                    sumX2 += xi * xi;
                    sumY2 += xj * xj;
                }
                r[i][j] = sumXY / Math.sqrt(sumX2 * sumY2);
                r[j][i] = r[i][j]; // symmetric
            }
        }

        // Display correlation matrix
        System.out.println("Correlation coefficients (r):");
        for (int i = 0; i < n; i++) {
            System.out.printf("%s: ", variableNames[i]);
            for (int j = 0; j < n; j++) {
                System.out.printf("%6.3f ", r[i][j]);
            }
            System.out.println();
        }

        // Compute r² for pairs involving x1 (index 0)
        System.out.println("\nCoefficient of determination (r²) with x1:");
        double maxR2 = -1;
        double minR2 = Double.MAX_VALUE;
        String mostInfluential = "";
        String leastInfluential = "";

        for (int j = 1; j < n; j++) {
            double r2 = r[0][j] * r[0][j];
            System.out.printf("x1 & %s: r² = %.4f%n", variableNames[j], r2);

            if (r2 > maxR2) {
                maxR2 = r2;
                mostInfluential = variableNames[j];
            }
            if (r2 < minR2) {
                minR2 = r2;
                leastInfluential = variableNames[j];
            }
        }

        System.out.printf("%nMost influential variable on x1 (highest r²): %s (r² = %.4f)%n", mostInfluential, maxR2);
        System.out.printf("Least influential variable on x1 (lowest r²): %s (r² = %.4f)%n", leastInfluential, minR2);
    }
}