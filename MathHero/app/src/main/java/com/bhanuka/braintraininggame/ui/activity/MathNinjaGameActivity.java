package com.bhanuka.braintraininggame.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bhanuka.braintraininggame.ui.fragment.MainMenuFragment;
import com.bhanuka.braintraininggame.R;

import java.util.Random;

public class MathNinjaGameActivity extends AppCompatActivity {

    // <----- View related variables ----->
    // Toolbar related variables
    Button btnVolume, btnHint;

    // Game related variables
    TextView remainingTime, questionLbl, answerLbl, statusLbl, questionCountLbl, scoreLbl;
    Button key1, key2, key3, key4, key5, key6, key7, key8, key9, key0, keyDel, keyMinus, keyHash, tryAgainBtn;

    // Calculation variables
    int randomNumber, answerPart1, answerPart2, answerPart3, answerPart4, answerPart5, answerPart6, totalAnswer, score;
    char operationsRandom;
    Random random = new Random();
    char[] operatorArr = {'-', '+', '/', '*'};
    String questionPart1, questionPart2, questionPart3, questionPart4, questionPart5, questionPart6;
    int questionCounter = 0;
    int attemptCount = 1;
    int timeLeft;

    // Timer for the game
    CountDownTimer countDownTime;

    // boolean for button clicked
    boolean isSoundButtonClicked = false;
    boolean isHintButtonClicked = false;
    boolean isBackButtonPressed = false;

    // Animation layout
    LinearLayout scoreDisplayLayout, numberPadLayout;

    // Background music
    MediaPlayer mediaPlayer;

    // Time remaining for the next question
    int remainTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);
        countDownTime(); // start the countdown method when game started

        // Bind variables to the view
        btnVolume = findViewById(R.id.btnVolume);
        btnHint = findViewById(R.id.btnHint);
        questionLbl = findViewById(R.id.question_Lbl);
        answerLbl = findViewById(R.id.answer_Lbl);
        statusLbl = findViewById(R.id.game_Status_Lbl);
        questionCountLbl = findViewById(R.id.questionCountLbl);
        key1 = findViewById(R.id.key1);
        key2 = findViewById(R.id.key2);
        key3 = findViewById(R.id.key3);
        key4 = findViewById(R.id.key4);
        key5 = findViewById(R.id.key5);
        key6 = findViewById(R.id.key6);
        key7 = findViewById(R.id.key7);
        key8 = findViewById(R.id.key8);
        key9 = findViewById(R.id.key9);
        key0 = findViewById(R.id.key0);
        keyMinus = findViewById(R.id.key_minus);
        keyDel = findViewById(R.id.key_del);
        scoreDisplayLayout = findViewById(R.id.scoreDisplayLayout);
        numberPadLayout = findViewById(R.id.numberPadLayout);

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.background_music);

        mediaPlayer.start();

        /* Disabled all button until first time Hash button is pressed to play the game*/
        if (questionCounter == 0) {
            key0.setEnabled(false);
            key1.setEnabled(false);
            key2.setEnabled(false);
            key3.setEnabled(false);
            key4.setEnabled(false);
            key5.setEnabled(false);
            key6.setEnabled(false);
            key7.setEnabled(false);
            key8.setEnabled(false);
            key9.setEnabled(false);
            keyMinus.setEnabled(false);
            keyDel.setEnabled(false);
        }

        /* Play and mute background sounds for the game */
        btnVolume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isSoundButtonClicked) {
                    btnVolume.setBackgroundResource(R.drawable.icon_volume_off);
                    isSoundButtonClicked = true;
                    mediaPlayer.pause();
                } else if (isSoundButtonClicked) {
                    btnVolume.setBackgroundResource(R.drawable.icon_volume_up_icon);
                    isSoundButtonClicked = false;
                    mediaPlayer.start();
                }
            }
        });

        /* Active hint button and disable it for the game */
        btnHint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isHintButtonClicked) {
                    btnHint.setBackgroundResource(R.drawable.icon_hint_on);
                    isHintButtonClicked = true;
                } else if (isHintButtonClicked) {
                    btnHint.setBackgroundResource(R.drawable.icon_hint_off);
                    isHintButtonClicked = false;
                }

            }
        });

        /* When keyHash is pressed all button are enabled and check if the
        hint button is pressed or not then if not go for the attempt 1 method
        then displays the question using the generateRandomQuestionAndAnswer() method
        after the keyHash pressed it will check for the answer and displays the status of that answer
        with the correct answer and goes to the next question */
        keyHash = findViewById(R.id.key_hash);
        keyHash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int gameStartCounter = 1;

                if (questionCounter == 0) {
                    key0.setEnabled(true);
                    key1.setEnabled(true);
                    key2.setEnabled(true);
                    key3.setEnabled(true);
                    key4.setEnabled(true);
                    key5.setEnabled(true);
                    key6.setEnabled(true);
                    key7.setEnabled(true);
                    key8.setEnabled(true);
                    key9.setEnabled(true);
                    keyMinus.setEnabled(true);
                    keyDel.setEnabled(true);
                }

                countDownTime.cancel();
                countDownTime();

                if (isHintButtonClicked) {
                    attemptCount = 4;
                } else if (!isHintButtonClicked) {
                    attemptCount = 1;
                }

                if (attemptCount == 1 && gameStartCounter <= 1) {

                    String answerTyped = answerLbl.getText().toString();
                    String correctAnswer = Integer.toString(totalAnswer);

                    if (answerTyped.equals(correctAnswer) && questionCounter >= 1) {
                        String correct = "CORRECT!";
                        statusLbl.setText(correct);
                        statusLbl.setTextColor(Color.GREEN);
                        remainingTime = findViewById(R.id.timeRemaining);
                        remainTime = Integer.parseInt(remainingTime.getText().toString());
                        timeLeft = remainTime;
                        score = score + 100 / 10 - remainTime;
                        answerLbl.setText("?");
                    } else {
                        if (questionCounter >= 1) {
                            String wrong = "WRONG!";
                            remainingTime = findViewById(R.id.timeRemaining);
                            remainTime = Integer.parseInt(remainingTime.getText().toString());
                            timeLeft = remainTime;
                            statusLbl.setText(wrong);
                            statusLbl.setTextColor(Color.RED);
                            answerLbl.setText("?");
                        }
                    }
                    questionCounter++;
                    if (questionCounter >= 11) {
                        getScore(score);
                    }
                    questionCountLbl.setText(String.valueOf(questionCounter));
                    if (questionCounter == 1) {
                        answerLbl.setText("?");
                        statusLbl.setText("");
                    }
                    generateRandomQuestionAndAnswer();
                }

//                if (attemptCount == 4 && gameStartCounter <= 1) {
//
//                    String answerTyped = answerLbl.getText().toString();
//                    String correctAnswer = Integer.toString(totalAnswer);
//
//                    int attempt = 1;
//
//                        for (int i = 5; i > attempt; attempt++) {
//
//                            if (answerTyped.equals(correctAnswer) && questionCounter >= 1) {
//                                String correct = "CORRECT!";
//                                statusLbl.setText(correct);
//                                statusLbl.setTextColor(Color.GREEN);
//                                remainingTime = findViewById(R.id.timeRemaining);
//                                remainTime = Integer.parseInt(remainingTime.getText().toString());
//                                timeLeft = remainTime;
//                                score = score + 100 / 10 - remainTime;
//                                answerLbl.setText("?");
//                                generateRandomQuestionAndAnswer();
//                                questionCounter++;
//
//                            } else if (!answerTyped.equals(correctAnswer) && questionCounter >= 1) {
//                                String wrong = "WRONG!";
//                                remainingTime = findViewById(R.id.timeRemaining);
//                                remainTime = Integer.parseInt(remainingTime.getText().toString());
//                                timeLeft = remainTime;
//                                statusLbl.setText(wrong);
//                                statusLbl.setTextColor(Color.RED);
//                                answerLbl.setText("?");
//                                if (!answerTyped.equals("")) {
//                                    int result = answerTyped.compareTo(correctAnswer);
//                                    if (result == 0) {
//                                        Toast.makeText(MathNinjaGameActivity.this, "EQUAL", Toast.LENGTH_SHORT).show();
//                                    } else if (result < 0) {
//                                        Toast.makeText(MathNinjaGameActivity.this, "GREATER", Toast.LENGTH_SHORT).show();
//                                    } else {
//                                        Toast.makeText(MathNinjaGameActivity.this, "LESS", Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            }
//                        }
//
//                    if (questionCounter == 1) {
//                        answerLbl.setText("?");
//
//                    }
//
//                    if (questionCounter >= 11) {
//                        getScore(score);
//                    }
//                    questionCountLbl.setText(String.valueOf(questionCounter));
//                    if (questionCounter == 1 || remainTime == 0){
//                        generateRandomQuestionAndAnswer();
//                    }
//
//                    questionCounter++;
//                }

            }
        });

        // Number Pad Related OnClicks
        key1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView answerLbl = findViewById(R.id.answer_Lbl);
                String answerTyped = (String) answerLbl.getText();
                if (answerTyped.contains("?"))
                    answerTyped = answerTyped.replace("?", ""); // value is replaced for the answerLbl when onClick performed
                answerLbl.setText(answerTyped + "1");


            }
        });


        key2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView answerLbl = findViewById(R.id.answer_Lbl);
                String answerTyped = (String) answerLbl.getText();
                if (answerTyped.contains("?"))
                    answerTyped = answerTyped.replace("?", "");
                answerLbl.setText(answerTyped + "2");
            }
        });


        key3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView answerLbl = findViewById(R.id.answer_Lbl);
                String answerTyped = (String) answerLbl.getText();
                if (answerTyped.contains("?"))
                    answerTyped = answerTyped.replace("?", "");
                answerLbl.setText(answerTyped + "3");
            }
        });


        key4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView answerLbl = findViewById(R.id.answer_Lbl);
                String answerTyped = (String) answerLbl.getText();
                if (answerTyped.contains("?"))
                    answerTyped = answerTyped.replace("?", "");
                answerLbl.setText(answerTyped + "4");
            }
        });


        key5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView answerLbl = findViewById(R.id.answer_Lbl);
                String answerTyped = (String) answerLbl.getText();
                if (answerTyped.contains("?"))
                    answerTyped = answerTyped.replace("?", "");
                answerLbl.setText(answerTyped + "5");
            }
        });


        key6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView answerLbl = findViewById(R.id.answer_Lbl);
                String answerTyped = (String) answerLbl.getText();
                if (answerTyped.contains("?"))
                    answerTyped = answerTyped.replace("?", "");
                answerLbl.setText(answerTyped + "6");
            }
        });


        key7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView answerLbl = findViewById(R.id.answer_Lbl);
                String answerTyped = (String) answerLbl.getText();
                if (answerTyped.contains("?"))
                    answerTyped = answerTyped.replace("?", "");
                answerLbl.setText(answerTyped + "7");
            }
        });


        key8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView answerLbl = findViewById(R.id.answer_Lbl);
                String answerTyped = (String) answerLbl.getText();
                if (answerTyped.contains("?"))
                    answerTyped = answerTyped.replace("?", "");
                answerLbl.setText(answerTyped + "8");
            }
        });


        key9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView answerLbl = findViewById(R.id.answer_Lbl);
                String answerTyped = (String) answerLbl.getText();
                if (answerTyped.contains("?"))
                    answerTyped = answerTyped.replace("?", "");
                answerLbl.setText(answerTyped + "9");
            }
        });


        key0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView answerLbl = findViewById(R.id.answer_Lbl);
                String answerTyped = (String) answerLbl.getText();
                if (answerTyped.contains("?"))
                    answerTyped = answerTyped.replace("?", "");
                answerLbl.setText(answerTyped + "0");
            }
        });


        keyMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView answerLbl = findViewById(R.id.answer_Lbl);
                String answerTyped = (String) answerLbl.getText();
                if (answerTyped.contains("?"))
                    answerTyped = answerTyped.replace("?", "");
                answerLbl.setText(answerTyped + "-");
            }
        });


        keyDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView answerLbl = findViewById(R.id.answer_Lbl);
                String keyPadDelete = (String) answerLbl.getText();
                if (keyPadDelete.length() > 0) {
                    keyPadDelete = keyPadDelete.substring(0, keyPadDelete.length() - 1); // substring and delete the last string value
                    answerLbl.setText(keyPadDelete);
                }
            }
        });
    }

    /* This method will execute when the question count is reached to 10 and
    calculate the score and displays the summary of the game*/
    private void getScore(int score) {
        numberPadLayout.setVisibility(View.GONE);
        scoreDisplayLayout.setVisibility(View.VISIBLE);
        if (score > 10) {
            scoreDisplayLayout.setBackgroundColor(Color.parseColor("#2db300"));
            Animation slideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up_animation);
            scoreDisplayLayout.setAnimation(slideUp);
            countDownTime.cancel();
            tryAgainBtn = findViewById(R.id.tryAgainBtn);
            scoreLbl = findViewById(R.id.scoreLbl);
            scoreLbl.setText(String.valueOf(score));
        } else if (score > 15) {
            scoreDisplayLayout.setBackgroundColor(Color.RED);
            Animation slideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up_animation);
            scoreDisplayLayout.setAnimation(slideUp);
            countDownTime.cancel();
            tryAgainBtn = findViewById(R.id.tryAgainBtn);
            scoreLbl = findViewById(R.id.scoreLbl);
            scoreLbl.setText(String.valueOf(score));
        } else {
            Animation slideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up_animation);
            scoreDisplayLayout.setAnimation(slideUp);
            countDownTime.cancel();
            tryAgainBtn = findViewById(R.id.tryAgainBtn);
            scoreLbl = findViewById(R.id.scoreLbl);
            scoreLbl.setText(String.valueOf(score));
        }
        tryAgainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MathNinjaGameActivity.this, MainMenuActivity.class);
                startActivity(intent);
            }
        });

    }


    public void countDownTime() {
        countDownTime = new CountDownTimer(10000, 1000) {
            Long millisUntilFinished;

            public void onTick(long millisUntilFinished) {
                TextView remainingTime = findViewById(R.id.timeRemaining);
                remainingTime.setText(String.valueOf(millisUntilFinished / 1000));
                this.millisUntilFinished = millisUntilFinished / 1000;
            }

            public void onFinish() {
                TextView remainingTime = findViewById(R.id.timeRemaining);
                remainingTime.setText("10");
                keyHash.performClick();
            }
        }.start();
    }

    /* Pause the background music tried to send the game back to main menu to save the game status*/
    @Override
    public void onBackPressed() {
//        String answerEntered = answerLbl.getText().toString();
//        String questionDisplayed = questionLbl.getText().toString();
//        String answerForTheQuestion = String.valueOf(totalAnswer);
//        int questionCount = Integer.parseInt(questionCountLbl.getText().toString());
//        isBackButtonPressed = true;
//        Intent intent = new Intent();
//        intent.putExtra("answerEntered", answerEntered);
//        intent.putExtra("questionDisplayed", questionDisplayed);
//        intent.putExtra("questionCount", questionCount);
//        intent.putExtra("backButtonPressed", isBackButtonPressed);
//        intent.putExtra("remainingTime", timeLeft);
//        intent.putExtra("answerForTheQuestion", answerForTheQuestion);
//        setResult(RESULT_OK, intent);
//        finish();
        mediaPlayer.pause();
        super.onBackPressed();
    }

    /* This method will select the difficulty you selected
    and generate a random question and a answer displays it on the UI*/
    public void generateRandomQuestionAndAnswer() {
        int count = 0;
        if (count >= 0) {

            // Create a random number and add that to the questionLbl
            if (MainMenuFragment.difficulty == 0) {
                int numberCount = 2;
                int j = 0;
                if (numberCount == 2) {
                    for (int i = 0; i < numberCount; i++) {
                        int num1 = random.nextInt(9 - 1 + 1) + 1;
                        randomNumber = random.nextInt(9 - 1 + 1) + 1;
                        if (j < numberCount - 1) {
                            operationsRandom = operatorArr[new Random().nextInt(4)];
                            switch (operationsRandom) {
                                case '+':
                                    if (j == 0) {
                                        answerPart1 = num1 + randomNumber;
                                        answerPart2 = answerPart1;
                                        questionPart1 = num1 + " " + "+" + " " + randomNumber + " " + " = ";
                                        questionLbl.setText(questionPart1);
                                        totalAnswer = Math.round(answerPart2);
                                        j++;
                                    }
                                    break;

                                case '-':
                                    if (j == 0) {
                                        answerPart1 = num1 - randomNumber;
                                        answerPart2 = answerPart1;
                                        questionPart1 = num1 + " " + "-" + " " + randomNumber + " " + " = ";
                                        questionLbl.setText(questionPart1);
                                        totalAnswer = Math.round(answerPart2);
                                        j++;
                                    }
                                    break;
                                case '/':
                                    if (j == 0) {
                                        answerPart1 = num1 / randomNumber;
                                        answerPart2 = answerPart1;
                                        questionPart1 = num1 + " " + "/" + " " + randomNumber + " " + " = ";
                                        questionLbl.setText(questionPart1);
                                        totalAnswer = Math.round(answerPart2);
                                        j++;

                                    }
                                    break;
                                case '*':
                                    if (j == 0) {
                                        answerPart1 = num1 * randomNumber;
                                        answerPart2 = answerPart1;
                                        questionPart1 = num1 + " " + "*" + " " + randomNumber + " " + " = ";
                                        questionLbl.setText(questionPart1);
                                        totalAnswer = Math.round(answerPart2);
                                        j++;
                                    }
                                    break;
                            }
                        }
                    }
                }
            } else if (MainMenuFragment.difficulty == 1) {
                int numberCount = random.nextInt(3 - 2 + 1) + 2;
                int j = 0;
                if (numberCount == 2) {
                    for (int i = 0; i < numberCount; i++) {
                        int num1 = random.nextInt(9 - 1 + 1) + 1;
                        randomNumber = random.nextInt(9 - 1 + 1) + 1;
                        if (j < numberCount - 1) {
                            operationsRandom = operatorArr[new Random().nextInt(4)];
                            switch (operationsRandom) {
                                case '+':
                                    if (j == 0) {
                                        answerPart1 = num1 + randomNumber;
                                        answerPart2 = answerPart1;
                                        questionPart1 = num1 + " " + "+" + " " + randomNumber + " " + " = ";
                                        questionLbl.setText(questionPart1);
                                        totalAnswer = Math.round(answerPart2);
                                        j++;
                                    }
                                    break;

                                case '-':
                                    if (j == 0) {
                                        answerPart1 = num1 - randomNumber;
                                        answerPart2 = answerPart1;
                                        questionPart1 = num1 + " " + "-" + " " + randomNumber + " " + " = ";
                                        questionLbl.setText(questionPart1);
                                        totalAnswer = Math.round(answerPart2);
                                        j++;
                                    }
                                    break;
                                case '/':
                                    if (j == 0) {
                                        answerPart1 = num1 / randomNumber;
                                        answerPart2 = answerPart1;
                                        questionPart1 = num1 + " " + "/" + " " + randomNumber + " " + " = ";
                                        questionLbl.setText(questionPart1);
                                        totalAnswer = Math.round(answerPart2);
                                        j++;
                                    }
                                    break;
                                case '*':
                                    if (j == 0) {
                                        answerPart1 = num1 * randomNumber;
                                        answerPart2 = answerPart1;
                                        questionPart1 = num1 + " " + "*" + " " + randomNumber + " " + " = ";
                                        questionLbl.setText(questionPart1);
                                        totalAnswer = Math.round(answerPart2);
                                        j++;
                                    }
                                    break;
                            }
                        }
                    }
                } else if (numberCount == 3) {
                    for (int i = 0; i < numberCount; i++) {
                        int num1 = random.nextInt(9 - 1 + 1) + 1;
                        randomNumber = random.nextInt(9 - 1 + 1) + 1;
                        if (j < numberCount - 1) {
                            operationsRandom = operatorArr[new Random().nextInt(4)];
                            switch (operationsRandom) {
                                case '+':
                                    if (j == 0) {
                                        answerPart1 = num1 + randomNumber;
                                        answerPart2 = answerPart1;
                                        questionPart1 = num1 + " " + "+" + " " + randomNumber;
                                        j++;
                                    } else if (j == 1) {
                                        answerPart2 += randomNumber;
                                        answerPart3 = answerPart2;
                                        questionPart2 = questionPart1 + " " + "+" + " " + randomNumber + " " + " = ";
                                        questionLbl.setText(questionPart2);
                                        totalAnswer = Math.round(answerPart3);
                                        j++;

                                    }
                                    break;

                                case '-':
                                    if (j == 0) {
                                        answerPart1 = num1 - randomNumber;
                                        answerPart2 = answerPart1;
                                        questionPart1 = num1 + " " + "-" + " " + randomNumber;
                                        j++;
                                    } else if (j == 1) {
                                        answerPart2 += randomNumber;
                                        answerPart3 = answerPart2;
                                        questionPart2 = questionPart1 + " " + "-" + " " + randomNumber + " " + " = ";
                                        questionLbl.setText(questionPart2);
                                        totalAnswer = Math.round(answerPart3);
                                        j++;
                                    }
                                    break;
                                case '/':
                                    if (j == 0) {
                                        randomNumber++;
                                        answerPart1 = num1 / randomNumber;
                                        answerPart2 = answerPart1;
                                        questionPart1 = num1 + " " + "/" + " " + randomNumber;
                                        j++;

                                    } else if (j == 1) {
                                        answerPart2 += randomNumber;
                                        answerPart3 = answerPart2;
                                        questionPart2 = questionPart1 + " " + "/" + " " + randomNumber + " " + " = ";
                                        questionLbl.setText(questionPart2);
                                        totalAnswer = Math.round(answerPart3);
                                        j++;

                                    }
                                    break;
                                case '*':
                                    if (j == 0) {
                                        answerPart1 = num1 * randomNumber;
                                        answerPart2 = answerPart1;
                                        questionPart1 = num1 + " " + "*" + " " + randomNumber;
                                        j++;
                                    } else if (j == 1) {
                                        answerPart2 += randomNumber;
                                        answerPart3 = answerPart2;
                                        questionPart2 = questionPart1 + " " + "*" + " " + randomNumber + " " + " = ";
                                        questionLbl.setText(questionPart2);
                                        totalAnswer = Math.round(answerPart3);
                                        j++;
                                    }
                                    break;
                            }
                        }
                    }
                }
            } else if (MainMenuFragment.difficulty == 2) {
                int numberCount = random.nextInt(4 - 2 + 1) + 2;
                int j = 0;
                if (numberCount == 2) {
                    for (int i = 0; i < numberCount; i++) {
                        int num1 = random.nextInt(9 - 1 + 1) + 1;
                        randomNumber = random.nextInt(9 - 1 + 1) + 1;
                        if (j < numberCount - 1) {
                            operationsRandom = operatorArr[new Random().nextInt(4)];
                            switch (operationsRandom) {
                                case '+':
                                    if (j == 0) {
                                        answerPart1 = num1 + randomNumber;
                                        answerPart2 = answerPart1;
                                        questionPart1 = num1 + " " + "+" + " " + randomNumber + " " + " = ";
                                        questionLbl.setText(questionPart1);
                                        totalAnswer = Math.round(answerPart2);
                                        j++;
                                    }
                                    break;

                                case '-':
                                    if (j == 0) {
                                        answerPart1 = num1 - randomNumber;
                                        answerPart2 = answerPart1;
                                        questionPart1 = num1 + " " + "-" + " " + randomNumber + " " + " = ";
                                        questionLbl.setText(questionPart1);
                                        totalAnswer = Math.round(answerPart2);
                                        j++;
                                    }
                                    break;
                                case '/':
                                    if (j == 0) {
                                        answerPart1 = num1 / randomNumber;
                                        answerPart2 = answerPart1;
                                        questionPart1 = num1 + " " + "/" + " " + randomNumber + " " + " = ";
                                        questionLbl.setText(questionPart1);
                                        totalAnswer = Math.round(answerPart2);
                                        j++;
                                    }
                                    break;
                                case '*':
                                    if (j == 0) {
                                        answerPart1 = num1 * randomNumber;
                                        answerPart2 = answerPart1;
                                        questionPart1 = num1 + " " + "*" + " " + randomNumber + " " + " = ";
                                        questionLbl.setText(questionPart1);
                                        totalAnswer = Math.round(answerPart2);
                                        j++;
                                    }
                                    break;
                            }
                        }
                    }
                } else if (numberCount == 3) {
                    for (int i = 0; i < numberCount; i++) {
                        int num1 = random.nextInt(9 - 1 + 1) + 1;
                        randomNumber = random.nextInt(9 - 1 + 1) + 1;
                        if (j < numberCount - 1) {
                            operationsRandom = operatorArr[new Random().nextInt(4)];
                            switch (operationsRandom) {
                                case '+':
                                    if (j == 0) {
                                        answerPart1 = num1 + randomNumber;
                                        answerPart2 = answerPart1;
                                        questionPart1 = num1 + " " + "+" + " " + randomNumber;
                                        j++;
                                    } else if (j == 1) {
                                        answerPart2 += randomNumber;
                                        answerPart3 = answerPart2;
                                        questionPart2 = questionPart1 + " " + "+" + " " + randomNumber + " " + " = ";
                                        questionLbl.setText(questionPart2);
                                        totalAnswer = Math.round(answerPart3);
                                        j++;

                                    }
                                    break;

                                case '-':
                                    if (j == 0) {
                                        answerPart1 = num1 - randomNumber;
                                        answerPart2 = answerPart1;
                                        questionPart1 = num1 + " " + "-" + " " + randomNumber;
                                        j++;
                                    } else if (j == 1) {
                                        answerPart2 += randomNumber;
                                        answerPart3 = answerPart2;
                                        questionPart2 = questionPart1 + " " + "-" + " " + randomNumber + " " + " = ";
                                        questionLbl.setText(questionPart2);
                                        totalAnswer = Math.round(answerPart3);
                                        j++;
                                    }
                                    break;
                                case '/':
                                    if (j == 0) {
                                        answerPart1 = num1 / randomNumber;
                                        answerPart2 = answerPart1;
                                        questionPart1 = num1 + " " + "/" + " " + randomNumber;
                                        j++;

                                    } else if (j == 1) {
                                        answerPart2 += randomNumber;
                                        answerPart3 = answerPart2;
                                        questionPart2 = questionPart1 + " " + "/" + " " + randomNumber + " " + " = ";
                                        questionLbl.setText(questionPart3);
                                        totalAnswer = Math.round(answerPart3);
                                        j++;

                                    }
                                    break;
                                case '*':
                                    if (j == 0) {
                                        answerPart1 = num1 * randomNumber;
                                        answerPart2 = answerPart1;
                                        questionPart1 = num1 + " " + "*" + " " + randomNumber;
                                        j++;
                                    } else if (j == 1) {
                                        answerPart2 += randomNumber;
                                        answerPart3 = answerPart2;
                                        questionPart2 = questionPart1 + " " + "*" + " " + randomNumber + " " + " = ";
                                        questionLbl.setText(questionPart3);
                                        totalAnswer = Math.round(answerPart3);
                                        j++;
                                    }
                                    break;
                            }
                        }
                    }
                } else if (numberCount == 4) {
                    for (int i = 0; i < numberCount; i++) {
                        int num1 = random.nextInt(9 - 1 + 1) + 1;
                        randomNumber = random.nextInt(9 - 1 + 1) + 1;
                        if (j < numberCount - 1) {
                            operationsRandom = operatorArr[new Random().nextInt(4)];
                            switch (operationsRandom) {
                                case '+':
                                    if (j == 0) {
                                        answerPart1 = num1 + randomNumber;
                                        answerPart2 = answerPart1;
                                        questionPart1 = num1 + " " + "+" + " " + randomNumber;
                                        j++;
                                    } else if (j == 1) {
                                        answerPart2 += randomNumber;
                                        answerPart3 = answerPart2;
                                        questionPart2 = questionPart1 + " " + "+" + " " + randomNumber;
                                        questionPart3 = questionPart2;
                                        j++;

                                    } else if (j == 2) {
                                        answerPart3 += randomNumber;
                                        answerPart4 = answerPart3;
                                        questionPart3 = questionPart2 + " " + "+" + " " + randomNumber + " " + " = ";
                                        questionLbl.setText(questionPart3);
                                        totalAnswer = Math.round(answerPart4);
                                        j++;
                                    }
                                    break;

                                case '-':
                                    if (j == 0) {
                                        answerPart1 = num1 - randomNumber;
                                        answerPart2 = answerPart1;
                                        questionPart1 = num1 + " " + "-" + " " + randomNumber;
                                        j++;
                                    } else if (j == 1) {
                                        answerPart2 += randomNumber;
                                        answerPart3 = answerPart2;
                                        questionPart2 = questionPart1 + " " + "-" + " " + randomNumber;
                                        questionPart3 = questionPart2;
                                        j++;

                                    } else if (j == 2) {
                                        answerPart3 += randomNumber;
                                        answerPart4 = answerPart3;
                                        questionPart3 = questionPart2 + " " + "-" + " " + randomNumber + " " + " = ";
                                        questionLbl.setText(questionPart3);
                                        totalAnswer = Math.round(answerPart4);
                                        j++;
                                    }
                                    break;
                                case '/':
                                    if (j == 0) {
                                        answerPart1 = num1 / randomNumber;
                                        answerPart2 = answerPart1;
                                        questionPart1 = num1 + " " + "/" + " " + randomNumber;
                                        j++;

                                    } else if (j == 1) {
                                        answerPart2 += randomNumber;
                                        answerPart3 = answerPart2;
                                        questionPart2 = questionPart1 + " " + "/" + " " + randomNumber;
                                        questionPart3 = questionPart2;
                                        j++;

                                    } else if (j == 2) {
                                        answerPart3 += randomNumber;
                                        answerPart4 = answerPart3;
                                        questionPart3 = questionPart2 + " " + "/" + " " + randomNumber + " " + " = ";
                                        questionLbl.setText(questionPart3);
                                        totalAnswer = Math.round(answerPart4);
                                        j++;
                                    }
                                    break;
                                case '*':
                                    if (j == 0) {
                                        answerPart1 = num1 * randomNumber;
                                        answerPart2 = answerPart1;
                                        questionPart1 = num1 + " " + "*" + " " + randomNumber;
                                        j++;
                                    } else if (j == 1) {
                                        answerPart2 += randomNumber;
                                        answerPart3 = answerPart2;
                                        questionPart2 = questionPart1 + " " + "*" + " " + randomNumber;
                                        questionPart3 = questionPart2;
                                        j++;

                                    } else if (j == 2) {
                                        answerPart3 += randomNumber;
                                        answerPart4 = answerPart3;
                                        questionPart3 = questionPart2 + " " + "*" + " " + randomNumber + " " + " = ";
                                        questionLbl.setText(questionPart3);
                                        totalAnswer = Math.round(answerPart4);
                                        j++;
                                    }
                                    break;
                            }
                        }
                    }
                }
            } else if (MainMenuFragment.difficulty == 3) {
                int numberCount = random.nextInt(6 - 4 + 1) + 4;
                int j = 0;
                if (numberCount == 4) {
                    for (int i = 0; i < numberCount; i++) {
                        int num1 = random.nextInt(9 - 1 + 1) + 1;
                        randomNumber = random.nextInt(9 - 1 + 1) + 1;
                        if (j < numberCount - 1) {
                            operationsRandom = operatorArr[new Random().nextInt(4)];
                            switch (operationsRandom) {
                                case '+':
                                    if (j == 0) {
                                        answerPart1 = num1 + randomNumber;
                                        answerPart2 = answerPart1;
                                        questionPart1 = num1 + " " + "+" + " " + randomNumber;
                                        j++;
                                    } else if (j == 1) {
                                        answerPart2 += randomNumber;
                                        answerPart3 = answerPart2;
                                        questionPart2 = questionPart1 + " " + "+" + " " + randomNumber;
                                        questionPart3 = questionPart2;
                                        j++;

                                    } else if (j == 2) {
                                        answerPart3 += randomNumber;
                                        answerPart4 = answerPart3;
                                        questionPart3 = questionPart2 + " " + "+" + " " + randomNumber + " " + " = ";
                                        questionLbl.setText(questionPart3);
                                        totalAnswer = Math.round(answerPart4);
                                        j++;
                                    }
                                    break;

                                case '-':
                                    if (j == 0) {
                                        answerPart1 = num1 - randomNumber;
                                        answerPart2 = answerPart1;
                                        questionPart1 = num1 + " " + "-" + " " + randomNumber;
                                        j++;
                                    } else if (j == 1) {
                                        answerPart2 += randomNumber;
                                        answerPart3 = answerPart2;
                                        questionPart2 = questionPart1 + " " + "-" + " " + randomNumber;
                                        questionPart3 = questionPart2;
                                        j++;

                                    } else if (j == 2) {
                                        answerPart3 += randomNumber;
                                        answerPart4 = answerPart3;
                                        questionPart3 = questionPart2 + " " + "-" + " " + randomNumber + " " + " = ";
                                        questionLbl.setText(questionPart3);
                                        totalAnswer = Math.round(answerPart4);
                                        j++;
                                    }
                                    break;
                                case '/':
                                    if (j == 0) {
                                        answerPart1 = num1 / randomNumber;
                                        answerPart2 = answerPart1;
                                        questionPart1 = num1 + " " + "/" + " " + randomNumber;
                                        j++;
                                    } else if (j == 1) {
                                        answerPart2 += randomNumber;
                                        answerPart3 = answerPart2;
                                        questionPart2 = questionPart1 + " " + "/" + " " + randomNumber;
                                        questionPart3 = questionPart2;
                                        j++;

                                    } else if (j == 2) {
                                        answerPart3 += randomNumber;
                                        answerPart4 = answerPart3;
                                        questionPart3 = questionPart2 + " " + "/" + " " + randomNumber + " " + " = ";
                                        questionLbl.setText(questionPart3);
                                        totalAnswer = Math.round(answerPart4);
                                        j++;
                                    }
                                    break;
                                case '*':
                                    if (j == 0) {
                                        answerPart1 = num1 * randomNumber;
                                        answerPart2 = answerPart1;
                                        questionPart1 = num1 + " " + "*" + " " + randomNumber;
                                        j++;
                                    } else if (j == 1) {
                                        answerPart2 += randomNumber;
                                        answerPart3 = answerPart2;
                                        questionPart2 = questionPart1 + " " + "*" + " " + randomNumber;
                                        questionPart3 = questionPart2;
                                        j++;

                                    } else if (j == 2) {
                                        answerPart3 += randomNumber;
                                        answerPart4 = answerPart3;
                                        questionPart3 = questionPart2 + " " + "*" + " " + randomNumber + " " + " = ";
                                        questionLbl.setText(questionPart3);
                                        totalAnswer = Math.round(answerPart4);
                                        j++;
                                    }
                                    break;
                            }
                        }
                    }
                }
                if (numberCount == 5) {
                    for (int i = 0; i < numberCount; i++) {
                        int num1 = random.nextInt(9 - 1 + 1) + 1;
                        randomNumber = random.nextInt(9 - 1 + 1) + 1;
                        if (j < numberCount - 1) {
                            operationsRandom = operatorArr[new Random().nextInt(4)];
                            switch (operationsRandom) {
                                case '+':
                                    if (j == 0) {
                                        answerPart1 = num1 + randomNumber;
                                        answerPart2 = answerPart1;
                                        questionPart1 = num1 + " " + "+" + " " + randomNumber;
                                        j++;
                                    } else if (j == 1) {
                                        answerPart2 += randomNumber;
                                        answerPart3 = answerPart2;
                                        questionPart2 = questionPart1 + " " + "+" + " " + randomNumber;
                                        questionPart3 = questionPart2;
                                        j++;

                                    } else if (j == 2) {
                                        answerPart3 += randomNumber;
                                        answerPart4 = answerPart3;
                                        questionPart3 = questionPart2 + " " + "+" + " " + randomNumber;
                                        questionPart4 = questionPart3;
                                        j++;
                                    } else if (j == 3) {
                                        answerPart4 += randomNumber;
                                        answerPart5 = answerPart4;
                                        questionPart4 = questionPart3 + " " + "+" + " " + randomNumber + " " + " = ";
                                        questionLbl.setText(questionPart4);
                                        totalAnswer = Math.round(answerPart5);
                                        j++;
                                    }
                                    break;

                                case '-':
                                    if (j == 0) {
                                        answerPart1 = num1 - randomNumber;
                                        answerPart2 = answerPart1;
                                        questionPart1 = num1 + " " + "-" + " " + randomNumber;
                                        j++;
                                    } else if (j == 1) {
                                        answerPart2 += randomNumber;
                                        answerPart3 = answerPart2;
                                        questionPart2 = questionPart1 + " " + "-" + " " + randomNumber;
                                        questionPart3 = questionPart2;
                                        j++;

                                    } else if (j == 2) {
                                        answerPart3 += randomNumber;
                                        answerPart4 = answerPart3;
                                        questionPart3 = questionPart2 + " " + "-" + " " + randomNumber;
                                        questionPart4 = questionPart3;
                                        j++;
                                    } else if (j == 3) {
                                        answerPart4 += randomNumber;
                                        answerPart5 = answerPart4;
                                        questionPart4 = questionPart3 + " " + "-" + " " + randomNumber + " " + " = ";
                                        questionLbl.setText(questionPart4);
                                        totalAnswer = Math.round(answerPart5);
                                        j++;
                                    }
                                    break;
                                case '/':
                                    if (j == 0) {
                                        answerPart1 = num1 / randomNumber;
                                        answerPart2 = answerPart1;
                                        questionPart1 = num1 + " " + "/" + " " + randomNumber;
                                        j++;

                                    } else if (j == 1) {
                                        answerPart2 += randomNumber;
                                        answerPart3 = answerPart2;
                                        questionPart2 = questionPart1 + " " + "/" + " " + randomNumber;
                                        questionPart3 = questionPart2;
                                        j++;

                                    } else if (j == 2) {
                                        answerPart3 += randomNumber;
                                        answerPart4 = answerPart3;
                                        questionPart3 = questionPart2 + " " + "/" + " " + randomNumber;
                                        questionPart4 = questionPart3;
                                        j++;
                                    } else if (j == 3) {
                                        answerPart4 += randomNumber;
                                        answerPart5 = answerPart4;
                                        questionPart4 = questionPart3 + " " + "/" + " " + randomNumber + " " + " = ";
                                        questionLbl.setText(questionPart4);
                                        totalAnswer = Math.round(answerPart5);
                                        j++;
                                    }
                                    break;
                                case '*':
                                    if (j == 0) {
                                        answerPart1 = num1 * randomNumber;
                                        answerPart2 = answerPart1;
                                        questionPart1 = num1 + " " + "*" + " " + randomNumber;
                                        j++;
                                    } else if (j == 1) {
                                        answerPart2 += randomNumber;
                                        answerPart3 = answerPart2;
                                        questionPart2 = questionPart1 + " " + "*" + " " + randomNumber;
                                        questionPart3 = questionPart2;
                                        j++;

                                    } else if (j == 2) {
                                        answerPart3 += randomNumber;
                                        answerPart4 = answerPart3;
                                        questionPart3 = questionPart2 + " " + "*" + " " + randomNumber;
                                        questionPart4 = questionPart3;
                                        j++;
                                    } else if (j == 3) {
                                        answerPart4 += randomNumber;
                                        answerPart5 = answerPart4;
                                        questionPart4 = questionPart3 + " " + "*" + " " + randomNumber + " " + " = ";
                                        questionLbl.setText(questionPart4);
                                        totalAnswer = Math.round(answerPart5);
                                        j++;
                                    }
                                    break;
                            }
                        }
                    }
                }
                if (numberCount == 6) {
                    for (int i = 0; i < numberCount; i++) {
                        int num1 = random.nextInt(9 - 0 + 1) + 0;
                        randomNumber = random.nextInt(9 - 0 + 1) + 0;
                        if (j < numberCount - 1) {
                            operationsRandom = operatorArr[new Random().nextInt(4)];
                            switch (operationsRandom) {
                                case '+':
                                    if (j == 0) {
                                        answerPart1 = num1 + randomNumber;
                                        answerPart2 = answerPart1;
                                        questionPart1 = num1 + " " + "+" + " " + randomNumber;
                                        j++;
                                    } else if (j == 1) {
                                        answerPart2 += randomNumber;
                                        answerPart3 = answerPart2;
                                        questionPart2 = questionPart1 + " " + "+" + " " + randomNumber;
                                        questionPart3 = questionPart2;
                                        j++;

                                    } else if (j == 2) {
                                        answerPart3 += randomNumber;
                                        answerPart4 = answerPart3;
                                        questionPart3 = questionPart2 + " " + "+" + " " + randomNumber;
                                        questionPart4 = questionPart3;
                                        j++;
                                    } else if (j == 3) {
                                        answerPart4 += randomNumber;
                                        answerPart5 = answerPart4;
                                        questionPart4 = questionPart3 + " " + "+" + " " + randomNumber;
                                        questionPart5 = questionPart4;
                                        j++;
                                    } else if (j == 4) {
                                        answerPart5 += randomNumber;
                                        answerPart6 = answerPart5;
                                        questionPart6 = questionPart5 + " " + "+" + " " + randomNumber + " " + " = ";
                                        questionLbl.setText(questionPart6);
                                        totalAnswer = Math.round(answerPart6);
                                        j++;
                                    }
                                    break;

                                case '-':
                                    if (j == 0) {
                                        answerPart1 = num1 - randomNumber;
                                        answerPart2 = answerPart1;
                                        questionPart1 = num1 + " " + "-" + " " + randomNumber;
                                        j++;
                                    } else if (j == 1) {
                                        answerPart2 += randomNumber;
                                        answerPart3 = answerPart2;
                                        questionPart2 = questionPart1 + " " + "-" + " " + randomNumber;
                                        questionPart3 = questionPart2;
                                        j++;

                                    } else if (j == 2) {
                                        answerPart3 += randomNumber;
                                        answerPart4 = answerPart3;
                                        questionPart3 = questionPart2 + " " + "-" + " " + randomNumber;
                                        questionPart4 = questionPart3;
                                        j++;
                                    } else if (j == 3) {
                                        answerPart4 += randomNumber;
                                        answerPart5 = answerPart4;
                                        questionPart4 = questionPart3 + " " + "-" + " " + randomNumber;
                                        questionPart5 = questionPart4;
                                        j++;
                                    } else if (j == 4) {
                                        answerPart5 += randomNumber;
                                        answerPart6 = answerPart5;
                                        questionPart6 = questionPart5 + " " + "-" + " " + randomNumber + " " + " = ";
                                        questionLbl.setText(questionPart6);
                                        totalAnswer = Math.round(answerPart6);
                                        j++;
                                    }
                                    break;
                                case '/':
                                    if (j == 0) {
                                        answerPart1 = num1 / randomNumber;
                                        answerPart2 = answerPart1;
                                        questionPart1 = num1 + " " + "/" + " " + randomNumber;
                                        j++;

                                    } else if (j == 1) {
                                        answerPart2 += randomNumber;
                                        answerPart3 = answerPart2;
                                        questionPart2 = questionPart1 + " " + "/" + " " + randomNumber;
                                        questionPart3 = questionPart2;
                                        j++;

                                    } else if (j == 2) {
                                        answerPart3 += randomNumber;
                                        answerPart4 = answerPart3;
                                        questionPart3 = questionPart2 + " " + "/" + " " + randomNumber;
                                        questionPart4 = questionPart3;
                                        j++;
                                    } else if (j == 3) {
                                        answerPart4 += randomNumber;
                                        answerPart5 = answerPart4;
                                        questionPart4 = questionPart3 + " " + "/" + " " + randomNumber;
                                        questionPart5 = questionPart4;
                                        j++;
                                    } else if (j == 4) {
                                        answerPart5 += randomNumber;
                                        answerPart6 = answerPart5;
                                        questionPart6 = questionPart5 + " " + "/" + " " + randomNumber + " " + " = ";
                                        questionLbl.setText(questionPart6);
                                        totalAnswer = Math.round(answerPart6);
                                        j++;
                                    }
                                    break;
                                case '*':
                                    if (j == 0) {
                                        answerPart1 = num1 * randomNumber;
                                        answerPart2 = answerPart1;
                                        questionPart1 = num1 + " " + "*" + " " + randomNumber;
                                        j++;
                                    } else if (j == 1) {
                                        answerPart2 += randomNumber;
                                        answerPart3 = answerPart2;
                                        questionPart2 = questionPart1 + " " + "*" + " " + randomNumber;
                                        questionPart3 = questionPart2;
                                        j++;

                                    } else if (j == 2) {
                                        answerPart3 += randomNumber;
                                        answerPart4 = answerPart3;
                                        questionPart3 = questionPart2 + " " + "*" + " " + randomNumber;
                                        questionPart4 = questionPart3;
                                        j++;
                                    } else if (j == 3) {
                                        answerPart4 += randomNumber;
                                        answerPart5 = answerPart4;
                                        questionPart4 = questionPart3 + " " + "*" + " " + randomNumber;
                                        questionPart5 = questionPart4;
                                        j++;
                                    } else if (j == 4) {
                                        answerPart5 += randomNumber;
                                        answerPart6 = answerPart5;
                                        questionPart6 = questionPart5 + " " + "*" + " " + randomNumber + " " + " = ";
                                        questionLbl.setText(questionPart6);
                                        totalAnswer = Math.round(answerPart6);
                                        j++;
                                    }
                                    break;
                            }
                        }
                    }
                }
            }

        }
        count++;
    }
}




