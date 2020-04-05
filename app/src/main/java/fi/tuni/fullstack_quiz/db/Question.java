package fi.tuni.fullstack_quiz.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Holds the question, answers and an indicator to the correct answer.
 */
@Entity(tableName = "questions")
public class Question {

    @PrimaryKey(autoGenerate = true)
    private int questionID;

    private String question;
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;
    private int correctIndex;

    /**
     * Initiates the object with the given values.
     *
     * @param question Question to be asked in String form.
     * TBA Four (4) answers in String form.
     * @param correctIndex Index to the position of the correct answer (0-3).
     */
    Question(String question, String answer1, String answer2,
             String answer3, String answer4, int correctIndex) {
        this.question = question;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
        this.correctIndex = correctIndex;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public int getQuestionID() {
        return questionID;
    }

    /**
     * Returns the question.
     *
     * @return Question in String form.
     */
    public String getQuestion() {
        return question;
    }

    public String getAnswer1() {
        return answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public String getAnswer4() {
        return answer4;
    }

    public int getCorrectIndex() {
        return this.correctIndex;
    }
}
