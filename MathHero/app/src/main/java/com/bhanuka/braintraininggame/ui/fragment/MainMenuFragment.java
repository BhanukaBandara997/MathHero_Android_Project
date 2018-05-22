package com.bhanuka.braintraininggame.ui.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bhanuka.braintraininggame.R;
import com.bhanuka.braintraininggame.ui.activity.ContinueGameActivity;
import com.bhanuka.braintraininggame.ui.activity.MainMenuActivity;
import com.bhanuka.braintraininggame.ui.activity.MathNinjaGameActivity;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class MainMenuFragment extends Fragment {

    // Fragment related variables
    AlertDialog aboutDialog, exitDialog;
    View rootView;
    View continueButton, newGameButton, aboutButton, exitButton;
    public static int difficulty;
    String questionDisplayed, answerEntered, remainingTime, answerForTheQuestion;
    int questionCount;
    boolean isBackButtonPressed;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {

        // Binding fragment variables into the view
        rootView = inflater.inflate(R.layout.fragment_main, viewGroup, false);
        continueButton = rootView.findViewById(R.id.continueButton);
        newGameButton = rootView.findViewById(R.id.newGameButton);
        aboutButton = rootView.findViewById(R.id.aboutButton);
        exitButton = rootView.findViewById(R.id.exitButton);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ContinueGameActivity.class);
//                intent.putExtra("answerEntered", answerEntered);
//                intent.putExtra("questionDisplayed", questionDisplayed);
//                intent.putExtra("questionCount", questionCount);
//                intent.putExtra("backButtonPressed",isBackButtonPressed);
//                intent.putExtra("remainingTime",remainingTime);
//                intent.putExtra("answerForTheQuestion",answerForTheQuestion);
                startActivity(intent);

            }
        });

        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewGameDialog();
            }
        });


        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.aboutTitle);
                builder.setMessage(R.string.aboutTxt);
                builder.setCancelable(false);
                builder.setPositiveButton(R.string.okLbl, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                    }
                });
                exitDialog = builder.show();
            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.confirmExitTitle);
                builder.setMessage(R.string.confirmExitTxt);
                builder.setCancelable(false);
                builder.setPositiveButton(R.string.saveAndExitBtn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                        getActivity().finish();
                    }
                });
                builder.setNegativeButton(R.string.cancelBtn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.cancel();
                    }
                });
                exitDialog = builder.show();
            }
        });

        return rootView;
    }


    @Override
    public void onPause() {
        super.onPause();

        if (aboutDialog != null) {
            aboutDialog.dismiss();
        }
    }

    // creating a alert builder dialog and onclick for each item for that
    private void openNewGameDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.new_game_title);
        builder.setItems(R.array.difficulty,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                        startGame(i);
                    }
                })
        ;
        builder.show();
    }

    // starting activity for the corresponding level
    private void startGame(int i) {

        Log.e("Math Ninja", "clicked on " + i);

        switch (i) {
            case 0:
                startActivityForResult(new Intent(getActivity(), MathNinjaGameActivity.class), 1);
                difficulty = 0;
                break;

            case 1:
                startActivityForResult(new Intent(getActivity(), MathNinjaGameActivity.class), 1);
                difficulty = 1;
                break;

            case 2:
                startActivityForResult(new Intent(getActivity(), MathNinjaGameActivity.class), 1);
                difficulty = 2;
                break;

            case 3:
                startActivityForResult(new Intent(getActivity(), MathNinjaGameActivity.class), 1);
                difficulty = 3;
                break;
        }
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == 1) {
//            if (resultCode == RESULT_OK) {
//
//                questionDisplayed = data.getStringExtra("questionDisplayed");
//                answerEntered = data.getStringExtra("answerEntered");
//                questionCount = data.getIntExtra("questionCount", 0);
//                isBackButtonPressed = data.getBooleanExtra("backButtonPressed", false);
//                remainingTime = data.getStringExtra("remainingTime");
//                answerForTheQuestion = data.getStringExtra("answerForTheQuestion");
//
//            }
//            if (resultCode == RESULT_CANCELED) {
//
//                Toast.makeText(getActivity(), "Nothing To Display", Toast.LENGTH_LONG).show();
//            }
//        }
//
//    }

}
