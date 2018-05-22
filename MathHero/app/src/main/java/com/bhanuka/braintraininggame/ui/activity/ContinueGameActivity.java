package com.bhanuka.braintraininggame.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bhanuka.braintraininggame.ui.fragment.MainMenuFragment;
import com.bhanuka.braintraininggame.R;

import java.util.Random;

public class ContinueGameActivity extends AppCompatActivity {

    // <----- View related variables ----->
    // Toolbar related variables
    Button btnVolume, btnHint;

    // Game related variables
    TextView remainingTime, questionLbl, answerLbl, statusLbl, questionCount, scoreLbl;
    Button key1, key2, key3, key4, key5, key6, key7, key8, key9, key0, keyDel, keyMinus, keyHash, tryAgainBtn;

    // Calculation variables
    int randomNumber, answerPart1, answerPart2, answerPart3, answerPart4, answerPart5, answerPart6, totalAnswer, score;
    char operationsRandom;
    Random random = new Random();
    char[] operatorArr = {'-', '+', '/', '*'};
    String questionPart1, questionPart2, questionPart3, questionPart4, questionPart5, questionPart6, question, answer;
    int questionCounter = 0;
    int attemptCount;

    // Timer for the game
    CountDownTimer countDownTime;

    // boolean for button clicked
    boolean isSoundButtonClicked = false;
    boolean isHintButtonClicked = false;

    // animation layout
    LinearLayout scoreDisplayLayout, numberPadLayout;

    String answerEntered, questionDisplayed;
    int questionCounterPrevious;

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
        questionCount = findViewById(R.id.questionCountLbl);
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

//        questionDisplayed = getIntent().getStringExtra("questionDisplayed");
//        answerEntered = getIntent().getStringExtra("answerEntered");
//        questionCounterPrevious = getIntent().getIntExtra("questionCount",0);

        questionLbl.setText(questionDisplayed);
        answerLbl.setText(answerEntered);
        questionCount.setText(String.valueOf(questionCounterPrevious));

        btnVolume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isSoundButtonClicked) {
                    btnVolume.setBackgroundResource(R.drawable.icon_volume_off);
                    isSoundButtonClicked = true;
                } else if (isSoundButtonClicked) {
                    btnVolume.setBackgroundResource(R.drawable.icon_volume_up_icon);
                    isSoundButtonClicked = false;
                }
            }
        });

        btnHint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isHintButtonClicked) {
                    btnHint.setBackgroundResource(R.drawable.icon_hint_off);
                    isHintButtonClicked = true;
                    attemptCount = 4;
                } else if (isHintButtonClicked) {
                    btnHint.setBackgroundResource(R.drawable.icon_hint_on);
                    attemptCount = 1;
                    isHintButtonClicked = false;
                }

            }
        });

        keyHash = findViewById(R.id.key_hash);
        keyHash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTime.cancel();
                countDownTime();

                String answerTyped = answerLbl.getText().toString();
                String correctAnswer = Integer.toString(totalAnswer);

                if (answerTyped.equals(correctAnswer)) {
                    String correct = "CORRECT!";
                    statusLbl.setText(correct);
                    statusLbl.setTextColor(Color.GREEN);
                    remainingTime = findViewById(R.id.timeRemaining);
                    String time = remainingTime.getText().toString();
                    int remainTime = Integer.parseInt(time);
                    score = score + 100 / 10 - remainTime;
                    answerLbl.setText("?");
                } else {
                    String wrong = "WRONG!";
                    statusLbl.setText(wrong);
                    statusLbl.setTextColor(Color.RED);
                    answerLbl.setText("?");
                }
                questionCounter++;
                if (questionCounter == 11) {
                    getScore(score);
                }
                questionCount.setText(String.valueOf(questionCounter));

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

    /* Displays the summary of the corresponding
    to the score which the user got*/
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
                Intent intent = new Intent(ContinueGameActivity.this, MainMenuActivity.class);
                startActivity(intent);
            }
        });

    }

    /* Creating a countDownTimer object and initialize it with
    10 seconds and show the time in the time remaining text view*/
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
