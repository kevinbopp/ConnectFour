// Kevin Bopp - Assignment 5 - Updated BoardFragment.java.
package com.example.connectfour;

// Added Drawable, ContextCompat, and DrawableCompat imports.
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

public class BoardFragment extends Fragment
{
    private final String GAME_STATE = "gameState";
    private ConnectFourGame mGame;
    private GridLayout mGrid;

    // Updated method onCreateView for initializing a game.
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        View parentView = inflater.inflate(R.layout.fragment_board, container, false);

        // Add the same click handler to all grid buttons
        mGrid = parentView.findViewById(R.id.board_grid);

        for (int i = 0; i < mGrid.getChildCount(); i++)
        {
            Button gridButton = (Button) mGrid.getChildAt(i);
            gridButton.setOnClickListener(this::onButtonClick);
        }

        // First, instantiate the member variable mGame.
        mGame = new ConnectFourGame();

        // Check. If savedInstanceState is null...
        if (savedInstanceState == null)
        {
            // Then start a new game.
            startGame();
        }
        else
        {
            // Otherwise, we need to set the state and disc.

            // Declare a local String variable to store the game state.
            String gameState = savedInstanceState.getString(GAME_STATE);

            // Set the state in the game.
            mGame.setState(gameState);

            // Finally, set the disc.
            setDisc();
        }

        return parentView;
    }

    // Updated onButtonClick.
    private void onButtonClick(View view)
    {
        // First, find the button's row and col.
        // Declare a local integer variable equal to the mGrid child index.
        int buttonIndex = mGrid.indexOfChild(view);

        // Then, declare a local integer variable called row equal to buttonIndex / number of rows.
        int row = buttonIndex / ConnectFourGame.COL;

        // Lastly, declare another local integer variable and calculate columns using modulus.
        int col = buttonIndex % ConnectFourGame.COL;

        // Now that we have the row and col of the button, select the disc with them.
        mGame.selectDisc(row, col);

        // After selecting it, set the disc.
        setDisc();

        // Check: If the game is over, congratulate the player and make a new game.
        if (mGame.isGameOver())
        {
            // Instantiate a Toast to congratulate the player. Invert the last player
            // so we congratulate the correct color.
            if (mGame.player == mGame.RED)
            {
                Toast.makeText(getContext(), "Blue has won!", Toast.LENGTH_SHORT).show();
            }
            else if (mGame.player == mGame.FULL)
            {
                // Show a "no one won" Toast in case the board fills with no winners.
                Toast.makeText(getContext(), "No one won!", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getContext(), "Red has won!", Toast.LENGTH_SHORT).show();
            }

            // Make a new game and set the disc.
            mGame.newGame();
            setDisc();
        }
    }

    // Added method startGame to start a new game.
    public void startGame()
    {
        // Call the newGame method on the ConnectFourGame class.
        mGame.newGame();

        // Set the disc.
        setDisc();
    }

    // Added method setDisc to set the disc.
    public void setDisc()
    {
        // Iterate through the collection mGrid.getChildCount().
        for (int buttonIndex = 0; buttonIndex < mGrid.getChildCount(); buttonIndex++)
        {
            // Instantiate a Button class instance for the current gridButton.
            Button gridButton = (Button) mGrid.getChildAt(buttonIndex);

            // Find the button's row and column.
            // Do so by, like before, using division and modulus.
            int row = buttonIndex / ConnectFourGame.COL;
            int col = buttonIndex % ConnectFourGame.COL;

            // Instantiate a Drawable for each of the three drawable discs.
            Drawable drawWhite = ContextCompat.getDrawable(getActivity(), R.drawable.circle_white);
            Drawable drawBlue = ContextCompat.getDrawable(getActivity(), R.drawable.circle_blue);
            Drawable drawRed = ContextCompat.getDrawable(getActivity(), R.drawable.circle_red);

            // Set each Drawable object equal to DrawableCompat.wrap.
            drawWhite = DrawableCompat.wrap(drawWhite);
            drawBlue = DrawableCompat.wrap(drawBlue);
            drawRed = DrawableCompat.wrap(drawRed);

            // Get the value of the element stored in the current row and column.
            int discValue = mGame.getDisc(row, col);

            // Based on the value of the element, set the appropriate background.
            if (discValue == ConnectFourGame.BLUE)
            {
                gridButton.setBackground(drawBlue);
            }
            else if (discValue == ConnectFourGame.RED)
            {
                gridButton.setBackground(drawRed);
            }
            else if (discValue == ConnectFourGame.EMPTY)
            {
                gridButton.setBackground(drawWhite);
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putString(GAME_STATE, mGame.getState());
    }
}