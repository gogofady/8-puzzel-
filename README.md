# 8-puzzel-


This code implements the 8-puzzle search problem using three search algorithms: Breadth-First Search (BFS), Depth-First Search (DFS), and A*.

## Problem statement

The 8-puzzle is a sliding puzzle that consists of a 3x3 grid with 8 numbered tiles and one empty space. The objective is to move the tiles around the grid to achieve a particular configuration, usually to arrange them in ascending order from left to right, top to bottom. Each move consists of sliding a tile into the empty space, and the problem is considered solved when the tiles are in the desired configuration.

## Usage

To use this code, simply run the `main` method in the `EightPuzzle` class. This will initialize an instance of the `EightPuzzleProblem` class, which represents the 8-puzzle problem, and solve it using the three search algorithms.

The `EightPuzzleProblem` class takes a string argument representing the initial state of the puzzle, where each number is separated by a space, and the empty space is represented by a zero. For example, the following string represents the initial state of the puzzle where the tiles are arranged in descending order:
8 7 6 5 4 3 2 1 0
## Performance
The Solution object contains the time taken to find the solution in milliseconds, which can be used to compare the performance of the different search algorithms.

