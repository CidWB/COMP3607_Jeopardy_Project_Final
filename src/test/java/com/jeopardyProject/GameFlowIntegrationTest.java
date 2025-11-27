package com.jeopardyProject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import com.jeopardyProject.Game.GameController;
import com.jeopardyProject.Game.Question;
import com.jeopardyProject.Game.QuestionList;
import com.jeopardyProject.Game.PlayerList;
import com.jeopardyProject.Game.Player;
import com.jeopardyProject.Game.Command.*;
import com.jeopardyProject.Game.State.*;

public class GameFlowIntegrationTest {
    private GameController controller;
    private PlayerList players;
    private QuestionList questions;

    @BeforeEach
    void setUp(){
        GameController.reset();
        controller = GameController.getInstance();
        controller.startGame();

        questions = new QuestionList();
        HashMap<String,String> options = new HashMap<>();
        options.put("A","ans1");
        options.put("B","ans2");
        options.put("C","ans3");
        options.put("D","ans4");

        questions.addQuestion(new Question("Variables", 100, "Q1", options, "A"));
        questions.addQuestion(new Question("Variables", 200, "Q2", options, "B"));
        questions.addQuestion(new Question("Functions", 300, "Q3", options, "C"));
        questions.addQuestion(new Question("Arrays", 400, "Q4", options, "D"));

        controller.setQuestions(questions);

        players = new PlayerList(3);
        players.addPlayer("Alice");
        players.addPlayer("Bob");
        players.addPlayer("Carol");
    }

    @Test
    void testCompleteTurnSequenceCorrectAnswer(){
        Player currentPlayer = players.getCurrentPlayer();
        assertEquals("Alice", currentPlayer.getPlayerId());
        assertEquals(0, currentPlayer.getScore());

        currentPlayer.setState(new SelectCategoryState());
        SelectCategoryAction selectCat = new SelectCategoryAction(controller);
        assertTrue(currentPlayer.getState().isAllowed(selectCat));

        controller.setCategory("Variables");
        currentPlayer.setState(new SelectQuestionState());

        SelectQuestionAction selectQ = new SelectQuestionAction(controller);
        assertTrue(currentPlayer.getState().isAllowed(selectQ));

        Question q = questions.getQuestionsByCategory("Variables").get(0);
        controller.setQuestion(q);
        currentPlayer.setState(new AnswerQuestionState());

        AnswerQuestionAction answer = new AnswerQuestionAction(controller);
        assertTrue(currentPlayer.getState().isAllowed(answer));

        answer.execute("A");
        assertTrue(q.getIsAnswered());

        players.endTurn(100, true);
        assertEquals(100, currentPlayer.getScore());

        Player nextPlayer = players.getCurrentPlayer();
        assertEquals("Bob", nextPlayer.getPlayerId());
    }

    @Test
    void testCompleteTurnSequenceIncorrectAnswer(){
        Player currentPlayer = players.getCurrentPlayer();
        currentPlayer.setState(new AnswerQuestionState());

        Question q = questions.getQuestionArray().get(0);
        controller.setQuestion(q);

        AnswerQuestionAction answer = new AnswerQuestionAction(controller);
        answer.execute("B");

        assertFalse(q.getIsAnswered());
        players.endTurn(100, false);
        assertEquals(-100, currentPlayer.getScore());
    }

    @Test
    void testPlayerRotationThroughMultipleTurns(){
        assertEquals("Alice", players.getCurrentPlayer().getPlayerId());
        players.endTurn(100, true);

        assertEquals("Bob", players.getCurrentPlayer().getPlayerId());
        players.endTurn(200, true);

        assertEquals("Carol", players.getCurrentPlayer().getPlayerId());
        players.endTurn(300, true);

        assertEquals("Alice", players.getCurrentPlayer().getPlayerId());
    }

    @Test
    void testStateTransitionsThroughCompleteTurn(){
        Player player = players.getCurrentPlayer();

        player.setState(new WaitingState());
        assertFalse(player.getState().isAllowed(new SelectCategoryAction(controller)));

        player.setState(new SelectCategoryState());
        assertTrue(player.getState().isAllowed(new SelectCategoryAction(controller)));
        assertFalse(player.getState().isAllowed(new SelectQuestionAction(controller)));

        player.setState(new SelectQuestionState());
        assertTrue(player.getState().isAllowed(new SelectQuestionAction(controller)));
        assertFalse(player.getState().isAllowed(new AnswerQuestionAction(controller)));

        player.setState(new AnswerQuestionState());
        assertTrue(player.getState().isAllowed(new AnswerQuestionAction(controller)));
        assertFalse(player.getState().isAllowed(new SelectCategoryAction(controller)));

        player.setState(new WaitingState());
        assertFalse(player.getState().isAllowed(new AnswerQuestionAction(controller)));
    }

    @Test
    void testMultiplePlayersAnsweringIncorrectly(){
        Question q = questions.getQuestionArray().get(0);
        controller.setQuestion(q);

        Player alice = players.getCurrentPlayer();
        alice.setState(new AnswerQuestionState());
        AnswerQuestionAction answer = new AnswerQuestionAction(controller);
        answer.execute("B");
        players.endTurn(100, false);
        assertEquals(-100, alice.getScore());

        Player bob = players.getCurrentPlayer();
        bob.setState(new AnswerQuestionState());
        answer.execute("C");
        players.endTurn(100, false);
        assertEquals(-100, bob.getScore());

        Player carol = players.getCurrentPlayer();
        carol.setState(new AnswerQuestionState());
        answer.execute("A");
        players.endTurn(100, true);
        assertEquals(100, carol.getScore());

        assertTrue(q.getIsAnswered());
    }

    @Test
    void testQuestionsBecomeAnsweredAndUnavailable(){
        Question q1 = questions.getQuestionArray().get(0);
        Question q2 = questions.getQuestionArray().get(1);

        assertFalse(q1.getIsAnswered());
        assertFalse(q2.getIsAnswered());

        controller.setQuestion(q1);
        AnswerQuestionAction answer = new AnswerQuestionAction(controller);
        answer.execute(q1.getRightAnswer());

        assertTrue(q1.getIsAnswered());
        assertFalse(q2.getIsAnswered());
    }

    @Test
    void testAllQuestionsAnsweredEndGame(){
        AnswerQuestionAction answer = new AnswerQuestionAction(controller);

        for(Question q : questions.getQuestionArray()){
            controller.setQuestion(q);
            answer.execute(q.getRightAnswer());
            assertTrue(q.getIsAnswered());
        }

        assertFalse(questions.hasQuestions());
    }

    @Test
    void testScoreAccumulationOverMultipleTurns(){
        Player alice = players.getCurrentPlayer();
        AnswerQuestionAction answer = new AnswerQuestionAction(controller);

        controller.setQuestion(questions.getQuestionArray().get(0));
        answer.execute("A");
        players.endTurn(100, true);
        assertEquals(100, alice.getScore());

        players.endTurn(200, true);
        players.endTurn(300, true);

        assertEquals("Alice", players.getCurrentPlayer().getPlayerId());
        controller.setQuestion(questions.getQuestionArray().get(1));
        answer.execute("B");
        players.endTurn(200, true);
        assertEquals(300, alice.getScore());
    }

    @Test
    void testNegativeScorePossible(){
        Player player = players.getCurrentPlayer();
        AnswerQuestionAction answer = new AnswerQuestionAction(controller);

        for(int i = 0; i < 3; i++){
            controller.setQuestion(questions.getQuestionArray().get(i));
            answer.execute("Z");
            players.endTurn(100, false);
            players.endTurn(100, true);
            players.endTurn(100, true);
        }

        assertEquals(-300, player.getScore());
    }

    @Test
    void testCategoryFilteringWorksInGameplay(){
        controller.setCategory("Variables");
        SelectQuestionAction selectQ = new SelectQuestionAction(controller);
        selectQ.execute("100");

        Question selected = controller.getQuestion();
        assertNotNull(selected);
        assertEquals("Variables", selected.getCategory());
        assertEquals(100, selected.getValue());
    }

    @Test
    void testGameControllerIntegrationWithPlayerList(){
        assertNotNull(controller.getCaseId());
        controller.setQuestions(questions);

        assertEquals(4, controller.getQuestions().getQuestionArray().size());
        assertTrue(controller.getQuestions().hasQuestions());
    }

    @Test
    void testPlayerStateIndependence(){
        Player alice = players.getCurrentPlayer();
        players.endTurn(100, true);
        Player bob = players.getCurrentPlayer();
        players.endTurn(100, true);
        Player carol = players.getCurrentPlayer();

        alice.setState(new SelectCategoryState());
        bob.setState(new WaitingState());
        carol.setState(new AnswerQuestionState());

        assertTrue(alice.getState().isAllowed(new SelectCategoryAction(controller)));
        assertFalse(bob.getState().isAllowed(new SelectCategoryAction(controller)));
        assertFalse(carol.getState().isAllowed(new SelectCategoryAction(controller)));
    }

    @Test
    void testCompleteGameSimulation(){
        int questionsAnswered = 0;

        for(Question q : questions.getQuestionArray()){
            Player currentPlayer = players.getCurrentPlayer();
            currentPlayer.setState(new AnswerQuestionState());

            controller.setQuestion(q);
            AnswerQuestionAction answer = new AnswerQuestionAction(controller);
            answer.execute(q.getRightAnswer());

            players.endTurn(q.getValue(), true);
            questionsAnswered++;

            currentPlayer.setState(new WaitingState());
        }

        assertEquals(4, questionsAnswered);
        assertFalse(questions.hasQuestions());
    }
}
