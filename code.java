import java.util.*;

public class SudokuSolver {
    private final int[][] board = new int[9][9];
    private final int[] rowMask = new int[9];   // bit i set means digit i is present in that row
    private final int[] colMask = new int[9];
    private final int[] boxMask = new int[9];
    private final List<int[]> empties = new ArrayList<>();

    public static void main(String[] args) {
        SudokuSolver solver = new SudokuSolver();
        if (!solver.readInput()) {
            System.out.println("Invalid input. Provide 9 lines with 9 characters each (1-9 or . 0 _).");
            return;
        }
        if (solver.solve(0)) solver.printBoard();
        else System.out.println("No solution exists");
    }

    private boolean readInput() {
        Scanner sc = new Scanner(System.in);
        for (int r = 0; r < 9; r++) {
            if (!sc.hasNextLine()) return false;
            String line = sc.nextLine().trim();
            // Accept several formats: "530070000", "5 3 . 0 7 . . . .", or "5 3 . . ."
            line = line.replaceAll("\\s+", "");
            if (line.length() < 9) return false;
            for (int c = 0; c < 9; c++) {
                char ch = line.charAt(c);
                int val = 0;
                if (ch >= '1' && ch <= '9') val = ch - '0';
                else if (ch == '.' || ch == '0' || ch == '_') val = 0;
                else return false;
                board[r][c] = val;
                if (val != 0) {
                    int bit = 1 << val;
                    int bIdx = boxIndex(r, c);
                    if ((rowMask[r] & bit) != 0 || (colMask[c] & bit) != 0 || (boxMask[bIdx] & bit) != 0) {
                        return false; // invalid puzzle (duplicate)
                    }
                    rowMask[r] |= bit;
                    colMask[c] |= bit;
                    boxMask[bIdx] |= bit;
                } else {
                    // store empties after initialization
                }
            }
        }
        // collect empties
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (board[r][c] == 0) empties.add(new int[]{r, c});
            }
        }
        return true;
    }

    private static int boxIndex(int r, int c) {
        return (r / 3) * 3 + (c / 3);
    }

    private boolean solve(int idx) {
        if (idx == empties.size()) return true;
        // Simple heuristic: choose the empty cell with fewest candidates.
        int sel = -1;
        int minChoices = 10;
        for (int i = idx; i < empties.size(); i++) {
            int r = empties.get(i)[0];
            int c = empties.get(i)[1];
            int used = rowMask[r] | colMask[c] | boxMask[boxIndex(r, c)];
            int candidates = (~used) & 0x3FE; // bits 1..9
            int count = Integer.bitCount(candidates);
            if (count < minChoices) {
                minChoices = count;
                sel = i;
                if (count == 1) break;
            }
        }
        if (sel == -1) return false;

        // swap selected empty to current index
        Collections.swap(empties, idx, sel);
        int r = empties.get(idx)[0];
        int c = empties.get(idx)[1];
        int b = boxIndex(r, c);
        int used = rowMask[r] | colMask[c] | boxMask[b];
        int candidates = (~used) & 0x3FE; // bits 1..9

        while (candidates != 0) {
            int lowbit = candidates & -candidates;
            int d = Integer.numberOfTrailingZeros(lowbit); // digit d
            candidates -= lowbit;

            placeDigit(r, c, b, d);
            if (solve(idx + 1)) return true;
            removeDigit(r, c, b, d);
        }

        // backtrack: swap back
        Collections.swap(empties, idx, sel);
        return false;
    }

    private void placeDigit(int r, int c, int b, int d) {
        board[r][c] = d;
        int bit = 1 << d;
        rowMask[r] |= bit;
        colMask[c] |= bit;
        boxMask[b] |= bit;
    }

    private void removeDigit(int r, int c, int b, int d) {
        board[r][c] = 0;
        int bit = ~(1 << d);
        rowMask[r] &= bit;
        colMask[c] &= bit;
        boxMask[b] &= bit;
    }

    private void printBoard() {
        for (int r = 0; r < 9; r++) {
            StringBuilder sb = new StringBuilder();
            for (int c = 0; c < 9; c++) {
                sb.append(board[r][c]);
                if (c < 8) sb.append(' ');
            }
            System.out.println(sb.toString());
        }
    }
}
