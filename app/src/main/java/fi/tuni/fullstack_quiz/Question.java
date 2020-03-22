package fi.tuni.fullstack_quiz;

/**
 * Holds the question, answers and an indicator to the correct answer.
 */
public class Question {
    private String question;
    private String[] answers;
    private int correctIndex;

    /**
     * Initiates the object with the given values.
     *
     * @param question Question to be asked in String form.
     * @param answers Four (4) answers in String form.
     * @param correctIndex Index to the position of the correct answer (0-3).
     */
    Question(String question, String[] answers, int correctIndex) {
        this.question = question;
        this.answers = answers;
        this.correctIndex = correctIndex;
    }

    /**
     * Returns the question.
     *
     * @return Question in String form.
     */
    String getQuestion() {
        return question;
    }

    /**
     * Returns the answers in an array.
     *
     * @return String array containing four (4) answers.
     */
    String[] getAnswers() {
        return answers;
    }

    /**
     * Checks if the given index is the same as the correct index.
     *
     * @param answerIndex Index number of the given answer.
     * @return true if the given index is the same as the correct index.
     */
    boolean isCorrect(int answerIndex) {
        return answerIndex == correctIndex;
    }
}
