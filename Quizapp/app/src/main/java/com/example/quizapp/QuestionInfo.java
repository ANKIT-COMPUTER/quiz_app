package com.example.quizapp;

public class QuestionInfo {
 public static
   String questionNo;
    String question;
    String option1;
    String option2;
    String option3;
    String option4;
    String answer;
    String attempted;
    String selectedOption;
 // default constructor
 public QuestionInfo() {}

 public String getQuestionNo() {
  return questionNo;
 }

 public String getQuestion() {
  return question;
 }

 public String getOption1() {
  return option1;
 }

 public String getOption2() {
  return option2;
 }

 public String getOption3() {
  return option3;
 }

 public String getOption4() {
  return option4;
 }

 public String getAnswer() {
  return answer;
 }

 public String getAttempted() {
  return attempted;
 }

 public String getSelectedOption() {
  return selectedOption;
 }

 public void setQuestionNo(String questionNo) {
  this.questionNo = questionNo;
 }

 public void setQuestion(String question) {
  this.question = question;
 }

 public void setOption1(String option1) {
  this.option1 = option1;
 }

 public void setOption2(String option2) {
  this.option2 = option2;
 }

 public void setOption3(String option3) {
  this.option3 = option3;
 }

 public void setOption4(String option4) {
  this.option4 = option4;
 }

 public void setAnswer(String answer) {
  this.answer = answer;
 }

 public void setAttempted(String attempted) {
  this.attempted = attempted;
 }

 public void setSelectedOption(String selectedOption) {
  this.selectedOption = selectedOption;
 }

 public QuestionInfo(String questionNo, String question, String option1, String option2, String option3, String option4, String answer, String attempted, String selectedOption)
 {
  this.questionNo = questionNo;
  this.question = question;
  this.option1=option1;
  this.option2=option2;
  this.option3=option3;
  this.option4=option4;
  this.answer=answer;
  this.attempted=attempted;
  this.selectedOption=selectedOption;
 }
}
