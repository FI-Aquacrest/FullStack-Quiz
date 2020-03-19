package fi.tuni.fullstack_quiz;

public class Question {
    //private int id;
    private String question;
    private String[] answers;
    private int correctIndex;

    public Question(String question, String[] answers, int correctIndex) {
        this.question = question;
        this.answers = answers;
        this.correctIndex = correctIndex;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getAnswers() {
        return answers;
    }

    public boolean isCorrect(int answerIndex) {
        return answerIndex == correctIndex;
    }
}
