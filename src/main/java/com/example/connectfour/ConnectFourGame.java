// Kevin Bopp - Assignment 5 - Updated ConnectFourGame.java.
package com.example.connectfour;

import java.util.Random;

public class ConnectFourGame
{
    // Added missing constants needed for implementation.
    public static final int ROW = 7;
    public static final int COL = 6;
    public static final int EMPTY = 0;
    public static final int YELLOW = 1;
    public static final int BLUE = 1;
    public static final int RED = 2;
    public static final int DISCS = 42;
    // Added an extra constant in case the board is full so we know to say it's a tie.
    public static final int FULL = 50;
    private int[][] boardGrid;

    // Added an integer player member variable, initialized to the first player.
    public int player = BLUE;

    public ConnectFourGame()
    {
        boardGrid = new int[ROW][COL];
    }

    // Updated method newGame to set the first player to go.
    public void newGame()
    {
        for (int row = 0; row < ROW; row++)
        {
            for (int col = 0; col < COL; col++)
            {
                boardGrid[row][col] = EMPTY;
            }
        }

        // After setting up the grid, set "player" equal to the first player.
        player = BLUE;
    }

    // Added a new method called getDisc, accepting parameters for a row and column.
    public int getDisc(int row, int col)
    {
        // Returns the element stored at the specified row and col of boardGrid.
        return boardGrid[row][col];
    }

    // Added a new method called isGameOver to check if the game is over.
    public boolean isGameOver()
    {
        // There are two game over conditions: if all the discs are
        // full, or if isWin ever returns true.

        // First, check if we encountered a win condition.
        if (isWin())
        {
            return true;
        }

        // If we didn't, we then need to check if every disc is full.
        for (int row = 0; row < ROW; row++)
        {
            for (int col = 0; col < COL; col++)
            {
                // Check every disc. If we encounter a single empty disc,
                // then the board is not full yet so the game is not over.
                if (boardGrid[row][col] == EMPTY)
                {
                    return false;
                }
            }
        }

        // If we make it to this point, the board is completely full.
        // So, the game is over.
        player = FULL;
        return true;
    }

    // Added a new method called isWin to check for win scenarios.
    public boolean isWin()
    {
        // If at least one of the three direction checks return true,
        // we can return true as the game has encountered a win condition.
        return (checkHorizontal() || checkVertical() || checkDiagonal());
    }

    // Added a new method called checkHorizontal() to check for horizontal wins.
    public boolean checkHorizontal()
    {
        // Leaving appropriate space, check discs for a horizontal match.
        for (int row = 0; row < ROW; row++)
        {
            for (int col = 0; col <= COL - 4; col++)
            {
                // Obtain the value at the current disc.
                int discValue = boardGrid[row][col];

                // Check every consecutive disc from left to right.
                // If the next consecutive disc's value matches the
                // one to its left, we can keep checking, or otherwise
                // the statement fails and we continue to the next
                // disc to check again.
                if (discValue != EMPTY &&
                        discValue == boardGrid[row][col + 1] &&
                        discValue == boardGrid[row][col + 2] &&
                        discValue == boardGrid[row][col + 3])
                {
                    // If we enter the condition, we found four consecutive
                    // discs in a horizontal order that all share the same
                    // discValue; that's a win.
                    return true;
                }
            }
        }

        // If we made it to this point, we did not meet a horizontal win condition.
        return false;
    }

    // Added a new method called checkVertical() to check for horizontal wins.
    public boolean checkVertical()
    {
        // Leaving appropriate space, check discs for a vertical match.
        for (int row = 0; row <= ROW - 4; row++)
        {
            for (int col = 0; col < COL; col++)
            {
                // Obtain the value at the current disc.
                int discValue = boardGrid[row][col];

                // Just like before, repeat the process from horizontal
                // checking--only this time, check across consecutive
                // row spaces rather than column spaces for vertical
                // checks.
                if (discValue != EMPTY &&
                        discValue == boardGrid[row + 1][col] &&
                        discValue == boardGrid[row + 2][col] &&
                        discValue == boardGrid[row + 3][col])
                {
                    // If we enter the condition, we found four consecutive
                    // discs in a vertical order that all share the same
                    // discValue; that's a win.
                    return true;
                }
            }
        }

        // If we made it to this point, we did not meet a vertical win condition.
        return false;
    }

    // Added a new method called checkDiagonal() to check for horizontal wins.
    public boolean checkDiagonal()
    {
        // We'll check two separate times for this method.
        // Start with checking each space from the top-left to the bottom-right.
        // Again, be sure to leave adequate space and check for a diagonal match.
        for (int row = 0; row <= ROW - 4; row++)
        {
            for (int col = 0; col <= COL - 4; col++)
            {
                // Obtain the value at the current disc.
                int discValue = boardGrid[row][col];

                // Check every consecutive disc 1 row down and 1 column
                // down, moving towards the bottom-right as we check.
                if (discValue != EMPTY &&
                        discValue == boardGrid[row + 1][col + 1] &&
                        discValue == boardGrid[row + 2][col + 2] &&
                        discValue == boardGrid[row + 3][col + 3])
                {
                    // If we enter the condition, we found four consecutive
                    // discs in a diagonal order that all share the same
                    // discValue from top-left to bottom-right; that's a win.
                    return true;
                }
            }
        }

        // Next, check again via the same process, this time from
        // the top-right to the bottom-left.
        for (int row = 0; row <= ROW - 4; row++)
        {
            for (int col = COL - 1; col >= 3; col--)
            {
                // Obtain the value at the current disc.
                int discValue = boardGrid[row][col];

                // This time, we check while still moving down
                // across rows, but move backwards in columns to
                // check the opposite direction.
                if (discValue != EMPTY &&
                        discValue == boardGrid[row + 1][col - 1] &&
                        discValue == boardGrid[row + 2][col - 2] &&
                        discValue == boardGrid[row + 3][col - 3])
                {
                    // If we enter the condition, we found four consecutive
                    // discs in a diagonal order that all share the same
                    // discValue from top-right to bottom-left; that's a win.
                    return true;
                }
            }
        }

        // If we made it to this point, we did not meet a diagonal
        // win condition in either direction.
        return false;
    }

    // Added a new method called setState().
    public void setState(String gameState)
    {
        // Keep track of an index while reading the gameState.
        int gameStateIndex = 0;

        // Populate every disc in the board with the gameState data.
        for (int row = 0; row < ROW; row++)
        {
            for (int col = 0; col < COL; col++)
            {
                // Fill in the value from the gameState data.
                boardGrid[row][col] = Character.getNumericValue(gameState.charAt(gameStateIndex));

                // Increment the gameStateIndex as we traverse each disc.
                gameStateIndex++;
            }
        }
    }

    // Added a new method called selectDisc(), used when setting a disc on the board.
    public void selectDisc(int row, int col)
    {
        // Iterate through the boardGrid rows to find the first empty row/col.
        for (int i = ROW - 1; i >= 0; i--)
        {
            if (boardGrid[i][col] == EMPTY)
            {
                boardGrid[i][col] = player;

                // ii. Switch players by updating member variable player to the opponent (i.e., RED or BLUE).
                if (player == BLUE)
                {
                    player = RED;
                }
                else
                {
                    player = BLUE;
                }

                // iii. Break out of the loop after a disc has been placed on the board.
                   break;
            }
        }
    }

    public String getState()
    {
        StringBuilder boardString = new StringBuilder();
        for (int row = 0; row < ROW; row++)
        {
            for (int col = 0; col < COL; col++)
            {
                boardString.append(boardGrid[row][col]);
            }
        }

        return boardString.toString();
    }
}
