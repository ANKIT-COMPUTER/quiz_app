package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class QaActivity extends AppCompatActivity {
    String category,difficulty,numberQ;
    TextView timertv,questiontv,option1tv,option2tv,option3tv,option4tv,timeer,questionCounter;
    Button next,back;


    ProgressBar progressBar;
    ArrayList<QuestionInfo> list;

    CountDownTimer timer;
    int num =0;
    int index = 0;
    int correctAnswers=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qa);

        list= new ArrayList<QuestionInfo>();
        back = findViewById(R.id.quizBtn);
        next =findViewById(R.id.nextBtn);



        progressBar=findViewById(R.id.progressBar);
        progressBar.setProgressTintList(ColorStateList.valueOf(Color.BLUE));


        option1tv = findViewById(R.id.option_1);
        option2tv = findViewById(R.id.option_2);
        option3tv = findViewById(R.id.option_3);
        option4tv = findViewById(R.id.option_4);
        questiontv = findViewById(R.id.question);
        questionCounter= findViewById(R.id.questionCounter);
        questionCounter = findViewById(R.id.questionCounter);

        category=getIntent().getStringExtra("object_category");
        difficulty=getIntent().getStringExtra("object_Difficulty");
        numberQ=getIntent().getStringExtra("object_nuberOfQuestion");







        String url =GenrateUrl(category,difficulty,numberQ);
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.GET,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    Log.e("err", response);
                    JSONObject mainObj = new JSONObject(response);

                        JSONArray result = mainObj.getJSONArray("results");
                        String[] answerList = new String[]{"","","",""};
                        for (int i = 0; i <  Integer.valueOf(numberQ); i++) {

                            Integer[] random = new Integer[]{1, 2, 3, 0};
                            List<Integer> l = Arrays.asList(random);
                            Collections.shuffle(l);

                            JSONObject object = result.getJSONObject(i);
                            String question = result.getJSONObject(i).getString("question").toString();
                            String option1 = result.getJSONObject(i).getJSONArray("incorrect_answers").getString(0).toString();
                            String option2 = result.getJSONObject(i).getJSONArray("incorrect_answers").getString(1).toString();
                            String option3 = result.getJSONObject(i).getJSONArray("incorrect_answers").getString(2).toString();
                            String correctAnswer = result.getJSONObject(i).getString("correct_answer").toString();


                            answerList[0] = option1;
                            answerList[1] = option2;
                            answerList[2] = option3;
                            answerList[3] = correctAnswer;
                            QuestionInfo questionInfo = new QuestionInfo("",question,answerList[random[0]],
                                    answerList[random[1]],
                                    answerList[random[2]],
                                    answerList[random[3]],
                                    correctAnswer,
                                    "",
                                    "");

                            list.add(questionInfo);


                        }

                        progressBar.setVisibility(View.GONE);
                         setNextQuestion();

                } catch (JSONException e) {
                    e.printStackTrace();


                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                toastMessage("error resposnse");
                questiontv.setText("No resposnse");

            }
        });

        queue.add(request);

     back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {


                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);



            }

        });



    }
    void reset() {
        option1tv.setBackground(getResources().getDrawable(R.drawable.option_unselected));
        option2tv.setBackground(getResources().getDrawable(R.drawable.option_unselected));
        option3tv.setBackground(getResources().getDrawable(R.drawable.option_unselected));
        option4tv.setBackground(getResources().getDrawable(R.drawable.option_unselected));
    }

    void showAnswer() {
        if(list.get(num).answer.equals(option1tv.getText().toString()))
            option1tv.setBackground(getResources().getDrawable(R.drawable.option_right));
        else if(list.get(num).answer.equals(option2tv.getText().toString()))
            option2tv.setBackground(getResources().getDrawable(R.drawable.option_right));
        else if(list.get(num).answer.equals(option3tv.getText().toString()))
            option3tv.setBackground(getResources().getDrawable(R.drawable.option_right));
        else if(list.get(num).answer.equals(option4tv.getText().toString()))
            option4tv.setBackground(getResources().getDrawable(R.drawable.option_right));
    }

    void checkAnswer(TextView textView) {
        String selectedAnswer = textView.getText().toString();
        if(selectedAnswer.equals(list.get(num).answer)) {
            correctAnswers++;
            textView.setBackground(getResources().getDrawable(R.drawable.option_right));
        } else {
            showAnswer();
            textView.setBackground(getResources().getDrawable(R.drawable.option_wrong));
        }
    }

    public void onClick(View view) {
        if (option1tv.equals(view) || option2tv.equals(view) || option3tv.equals(view) || option4tv.equals(view)) {

            TextView selected = (TextView) view;
            checkAnswer(selected);
        } else if (next.equals(view)) {

            reset();
            if(num < Integer.valueOf(numberQ)) {
                num++;
                setNextQuestion();
            } else {
                Intent intent = new Intent(QaActivity.this, ResultActivity.class);
                intent.putExtra("correct", correctAnswers);
                intent.putExtra("total",Integer.valueOf(numberQ) );
                startActivity(intent);
            }
        }
    }

    void setNextQuestion()  {



        if(num < Integer.valueOf(numberQ)) {

            questionCounter.setText(String.format("%d/%d", (num+1), Integer.valueOf(numberQ)));
            questiontv.setText(list.get(num).question);
            option1tv.setText(list.get(num).option1);
            option2tv.setText(list.get(num).option2);
            option3tv.setText(list.get(num).option3);
            option4tv.setText(list.get(num).option4);
        }
        else {
            Intent intent = new Intent(QaActivity.this, ResultActivity.class);
            intent.putExtra("correct", correctAnswers);
            intent.putExtra("total",Integer.valueOf(numberQ) );
            startActivity(intent);
        }

    }















    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    String GenrateUrl(String category,String difficulty,String numberQ){
        if (getCategoryValue(category)=="" && getDifficultyValue(difficulty)=="") {
            return "https://opentdb.com/api.php?amount="+numberQ+"&type=multiple";
        }else if(getCategoryValue(category)==""){
            return "https://opentdb.com/api.php?amount="+numberQ+"&difficulty="+getDifficultyValue(difficulty)+"&type=multiple";
        } else if (getDifficultyValue(difficulty)=="") {
            return "https://opentdb.com/api.php?amount="+numberQ+"&category="+getCategoryValue(category)+"&type=multiple";
        }  else {
            return "https://opentdb.com/api.php?amount=" + numberQ + "&category=" + getCategoryValue(category) + "&difficulty=" + getDifficultyValue(difficulty) + "&type=multiple";
        }

    }
    String getDifficultyValue(String difficulty){
        if(difficulty == "Easy"){
            return "easy";
        } else if (difficulty == "Medium") {
            return "medium";

        } else if (difficulty == "Hard") {
            return "hard";
        }
        else {
            return "";
        }


    }
    String getCategoryValue(String category){
   if(category == "Any Category" ) {
       return "";
   } else if (category == "General Knowledge" ) {
       return "9";
   }else if (category == "Entertainment: Books" ) {
       return "10";
   }else if (category == "Entertainment: Film" ) {
       return "11";
   }else if (category == "Entertainment: Music" ) {
       return "12";
   }else if (category == "Entertainment: Musicals &amp; Theatres" ) {
       return "13";
   }else if (category == "Entertainment: Television" ) {
       return "14";
   } else if (category == "Entertainment: Video Games" ) {
       return "15";
   }  else if (category == "Entertainment: Board Games" ) {
       return "16";
   }  else if (category == "Science &amp; Nature" ) {
       return "17";
   } else if (category == "Science: Computers" ) {
       return "18";
   } else if (category == "Science: Mathematics" ) {
       return "19";
   }else if (category == "Mythology" ) {
       return "20";
   }else if (category == "Sports" ) {
       return "21";
   }else if (category == "Geography" ) {
       return "22";
   }else if (category == "History" ) {
       return "23";
   }else if (category == "Politics" ) {
       return "24";
   }else if (category == "Art" ) {
       return "25";
   }else if (category == "Celebrities" ) {
       return "26";
   }else if (category == "Animals" ) {
       return "27";
   }else if (category == "Vehicles" ) {
       return "28";
   } else if (category == "Entertainment: Comics" ) {
      return "29";
   }else if (category == "Science: Gadgets" ) {
       return "30";
   }else if (category == "Entertainment: Japanese Anime &amp; Manga" ) {
       return "31";
   }else if (category == "Entertainment: Cartoon &amp; Animations" ) {
       return "32";
   }else {

       return "";
   }

    }


}