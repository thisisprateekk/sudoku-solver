# Sudoku-solver
This project is a Java-based Sudoku Solver that uses backtracking and bitmask optimization to solve any valid 9×9 Sudoku puzzle. The goal of this project is to demonstrate clear problem-solving ability, algorithmic thinking, and efficient use of Java features.

Overview

The solver reads a standard 9×9 Sudoku grid as input. Filled cells contain digits from 1 to 9, while empty cells can be represented using 0, dot (.), or underscore (_). The program processes the input, validates it, and then attempts to find a valid solution.

How the Solver Works

Input Handling
The puzzle is read as nine lines of input. Each line is cleaned and stored in a 9×9 integer grid. Invalid inputs or duplicate values in rows, columns, or sub-boxes are detected before solving.

Bitmask-Based Constraints
To check if a digit can be placed in a cell, the solver maintains three arrays: one for rows, one for columns, and one for each 3×3 box.
Each array uses bitmasks to track which digits are already used.
This allows constant-time validation and significantly improves performance.

Backtracking Algorithm
All empty cells are collected first.
The solver selects the next cell to fill using a simple heuristic: it chooses the empty cell that has the fewest available candidate digits.
This reduces the number of steps and speeds up the solving process.

Recursive Search
For each empty cell, the solver tries all possible digits that do not violate row, column, or box constraints.
If a digit leads to a dead end later, the solver backtracks and tries another digit.
This continues until either the puzzle is fully solved or no solution exists.

Output
When a valid solution is found, the program prints the completed 9×9 Sudoku grid in a readable format.
If the puzzle is unsolvable, it prints a clear message indicating that no solution exists.

Features

Works for any valid 9×9 Sudoku puzzle

Fast validation using bitmasking

Efficient backtracking with a heuristic to reduce search time

Fully console-based and requires only standard Java

Clean and readable implementation suitable for learning and demonstration

Why This Project Is Useful

This project clearly showcases skills in Java programming, recursion, constraint-solving, optimization techniques, and clean software design. It is a strong example of applying data structures and algorithms to a real-world logic problem.
