import java.util.ArrayList;
import java.util.List;

public class App1 {
    public static void main(String[] args) {
        App1 lab1 = new App1();
        //lab1.compulsory();
        //lab1.homework(args);
        lab1.bonus(args);
    }

    void compulsory() {
        System.out.println("Hello, World!");
        String languages[] = { "C", "C++", "C#", "Python", "Go", "Rust", "JavaScript", "PHP", "Swift", "Java" };
        int n = (int) (Math.random() * 1_000_000);
        n *= 3;
        n = n + 0b10101 + 0xFF;
        n *= 6;
        int digitOfNumber;
        if (n > 9) {
            digitOfNumber = getDigit(n);
        } else
            digitOfNumber = n;
        // System.out.println(digitOfNumber);
        System.out.println("Willy-nilly, this semester I will learn " + languages[digitOfNumber]);
    }

    void homework(String[] args) {

        long t1 = System.currentTimeMillis();
        if (args.length != 3) {
            System.err.println("Too much or insuficient arguments!");
            System.exit(-1);
        }

        StringBuilder sb = new StringBuilder("");

        int a = Integer.parseInt(args[0]);
        int b = Integer.parseInt(args[1]);
        int k = Integer.parseInt(args[2]);

        for (int i = a; i <= b; i++) {
            if (k == getKReduct(i)) {
                sb.append(" ").append(Integer.toString(i));
            }
        }
        System.out.println(sb);
        // la rularea "java App1 100 200 1", in string am val: 100 103 109 129 130 133
        // 139 167 176 188 190 192 193
        long t2 = System.currentTimeMillis();
        System.out.println("Running time in milliseconds is : " + (t2 - t1));
    }

    void bonus(String[] args) {

        int numVertices = Integer.parseInt(args[0]);
        if (numVertices <= 3) {
            System.err.println("Insuficient number of vertices!");
            System.exit(-1);
        }
        int[][] adjacencyMatrix = new int[numVertices][numVertices];

        for (int i = 0; i < numVertices; i++) {
            if (i == 0) {
                for (int j = 1; j < numVertices; j++) {
                    adjacencyMatrix[i][j] = 1;
                    adjacencyMatrix[j][i] = 1;
                }
            } else if (i == numVertices - 1) {
                adjacencyMatrix[i][1] = 1;
                adjacencyMatrix[1][i] = 1;
            } else {
                adjacencyMatrix[i][i + 1] = 1;
                adjacencyMatrix[i + 1][i] = 1;
            }
        }
        System.out.printf("Adjacency matrix for %s vertices is : \n", numVertices);
        print2D(adjacencyMatrix);

        if (numOfCycles(adjacencyMatrix) == ((numVertices * numVertices) - (3 * numVertices) + 3)) {
            System.out.println("The algorithm is ok.");
            System.out.printf("Number of cycles found in the wheel graph: " + numOfCycles(adjacencyMatrix));
        } else{
            System.out.println("The algorithm failed.");
            System.out.printf("Number of cycles found in the wheel graph: " + numOfCycles(adjacencyMatrix));
        }     
    }

    public static int numOfCycles(int[][] mat) {
        int n = mat.length;
        boolean[] visited = new boolean[n];
        int[] cyclesCount = {0};

        for (int i = 0; i < n; i++) {
            List<Integer> path = new ArrayList<>();
            path.add(i);
            dfs(mat, visited, path, i, i, cyclesCount);
            visited[i] = true;
        }

        return cyclesCount[0] / 2;
    }

    public static void dfs(int[][] mat, boolean[] visited, List<Integer> path, int start, int current, int[] cyclesCount) {
        int n = mat.length;
        visited[current] = true;

        for (int next = 0; next < n; next++) {
            if (mat[current][next] == 1) {
                if (next == start && path.size() > 2) {
                    cyclesCount[0]++;
                } else if (!visited[next] && !path.contains(next)) {
                    List<Integer> newPath = new ArrayList<>(path);
                    newPath.add(next);
                    dfs(mat, visited, newPath, start, next, cyclesCount);
                }
            }
        }

        visited[current] = false;
    }

    public static void print2D(int mat[][]) {
        for (int[] row : mat) {
            for (int x : row)
                System.out.print(x + " ");
            System.out.print("\n");
        }
    }

    public static int getDigit(int n) {
        while (n > 9) {
            int cpy = n;
            n = 0;
            while (cpy > 0) {
                n += cpy % 10;
                cpy /= 10;
            }
        }
        return n;
    }

    public static int getKReduct(int n) {
        if (n > 9) {
            while (n > 9) {
                int cpy = n;
                n = 0;
                while (cpy > 0) {
                    n += (cpy % 10) * (cpy % 10);
                    cpy /= 10;
                }
            }
        } else
            do {
                int cpy = n;
                n = 0;
                while (cpy > 0) {
                    n += (cpy % 10) * (cpy % 10);
                    cpy /= 10;
                }
            } while (n > 9);
        return n;
    }
}
