public class Nussinov {

    private static int dp[][];
    private static String sequence;

    private Nussinov(String inputSequence) {

        sequence = inputSequence;
        int N = sequence.toCharArray().length;
        dp = new int[N][N];

        // Initialize DP matrix
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                dp[i][j] = -1;
    }

    private static int nussinovAlgo(int i, int j) {

        if (dp[i][j] != -1)
            return dp[i][j];

        if (i >= j) {
            dp[i][j] = 0;
            return 0;
        }

        int max = 0;

        max = Math.max(max, Math.max(nussinovAlgo(i + 1, j), nussinovAlgo(i, j - 1)));

        if (isPair(sequence.charAt(i), sequence.charAt(j)))
            max = Math.max(max, nussinovAlgo(i + 1, j - 1) + 1);

        for (int k = i + 1; k < j; k++)
            max = Math.max(max, nussinovAlgo(i, k) + nussinovAlgo(k + 1, j));

        dp[i][j] = max;
        return max;
    }

    private static String backtrack(int i, int j) {

        if (i == j)
            return Character.toString(sequence.charAt(i));

        if (i > j)
            return "";

        if (nussinovAlgo(i, j) == nussinovAlgo(i + 1, j))
            return sequence.charAt(i) + backtrack(i + 1, j);

        if (nussinovAlgo(i, j) == nussinovAlgo(i, j - 1))
            return backtrack(i, j - 1) + sequence.charAt(j);

        if (isPair(sequence.charAt(i), sequence.charAt(j)) && nussinovAlgo(i, j) == nussinovAlgo(i + 1, j - 1) + 1)
            return "(" + sequence.charAt(i) + backtrack(i + 1, j - 1) + sequence.charAt(j) + ")";

        for (int k = i + 1; k < j; k++) {
            if (nussinovAlgo(i, j) == nussinovAlgo(i, k) + nussinovAlgo(k + 1, j))
                return backtrack(i, k) + backtrack(k + 1, j);
        }

        return "Backtracking failed";
    }

    private static boolean isPair(char x, char y) {
        return ((x == 'A' && y == 'U') || (x == 'U' && y == 'A') || (x == 'C' && y == 'G') || (x == 'G' && y == 'C'));
    }

    private static boolean checkIfInvalidChars(String input) {

        String allowedChars = "ACGU";

        // Check if the characters in input is valid or not
        for (char c : input.toUpperCase().toCharArray()) {

            switch (c) {
                case 'A':
                case 'C':
                case 'G':
                case 'U':
                    break;

                default:
                    return true;
            }
        }

        return false;
    }

    public static void main(String args[]) {


        if (args[0].toCharArray().length == 0 || checkIfInvalidChars(args[0]))
            System.out.println("Enter valid RNA sequence");

        else {
            Nussinov nussinov = new Nussinov(args[0]);
            System.out.println("Matched pairs: " + nussinovAlgo(0, args[0].toCharArray().length - 1));
            System.out.println("Secondary Structure after folding: " +  backtrack(0, args[0].toCharArray().length - 1));

            // Example input:
            // GGGAAAUCC Ans = 3
            // AAAUCCCAGGA Ans = 3
            // AAAUCCCAGGAU Ans = 4
        }
    }
}
