package com.jawue;

import com.jawue.shared.Board;
import com.jawue.shared.PlayerMove;
import com.jawue.shared.message.GameSymbol;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }

    public void testPlayerMoveConstructor() {
        PlayerMove playerMove = new PlayerMove(2, 1);
        assertEquals(playerMove.getRow(), (Character) '2' );
        assertEquals(playerMove.getColumn(), (Character) 'B');
    }

    public void testIsFieldOccupied() {
        Board board = new Board();
        PlayerMove playerMove = new PlayerMove(0,1);
        PlayerMove playerMoveOccupied = new PlayerMove(1, 0);
        PlayerMove playerMove2 = new PlayerMove('0', 'A');
        board.fill(playerMoveOccupied, GameSymbol.X);

        assertFalse( board.isFieldOccupied(playerMove));
        assertTrue(board.isFieldOccupied(playerMoveOccupied));
        assertEquals(false, board.isFieldOccupied(playerMove2));

    }



}
