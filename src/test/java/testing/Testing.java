package testing;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pl.sydygaliev.java_journey.model.InitialHandlerAndStorer;
import pl.sydygaliev.java_journey.model.MessageModel;
import pl.sydygaliev.java_journey.model.MessageOperator;
import pl.sydygaliev.java_journey.model.ModeEnum;
import pl.sydygaliev.java_journey.model.exception.NonAlphaNumericException;
import pl.sydygaliev.java_journey.model.exception.UnexpectedModeException;
import pl.sydygaliev.java_journey.model.exception.WrongFormatException;

/**
 * Testing public methods of models Keywords and messages are handled(tuned)
 * earlier before encryption and decryption so there are no really border-like
 * scenarios in public methods. 
 * Decryption heavily depends on initial given input(to restore symbols,
 * capital letters, numbers, etc.
 *
 * @author Ulan Sydygaliev
 * @version f2
 */
public class Testing {


    /**
     * Testing encryption, default situation 1. There are no border situations
     * because strings were handled beforehand
     */
    @Test
    void properEncryptionStandardScenario() {
        MessageOperator messageOperator = new MessageOperator("example", "keyword");
        assertEquals("fygmkyhobxmfkkkimd", messageOperator.encryptMessage("helpmeobiwankenobi"));
        assertNotEquals("helpmeobiwankenobi", messageOperator.encryptMessage("helpmeobiwankenobi"));
    }

    /**
     * Testing encryption, default situation 2
     */
    @Test
    void properEncryptionStandardScenario2() {
        MessageOperator messageOperator = new MessageOperator("vinivi", "divici");
        assertEquals("aoocvlaovcyk", messageOperator.encryptMessage("attackatdawn"));

    }

    /**
     * Testing default decryption situation 1
     *
     */
    @Test
    void properDecryptionStandardScenario() {
        MessageOperator messageOperator = new MessageOperator("vinivi", "divici");
        assertEquals("attackatdawn", messageOperator.decryptMessage("aoocvlaovcyk"));
        assertNotEquals("aoocvlaovcyk", messageOperator.encryptMessage("aoocvlaovcyk"));
    }

    /**
     * Testing default decryption situation 2
     *
     */
    @Test
    void properDecryptionStandardScenario2() {
        MessageOperator messageOperator = new MessageOperator("example", "keyword");
        assertEquals("helpmeobiwankenobi", messageOperator.decryptMessage("fygmkyhobxmfkkkimd"));
    }
    
    /**
     * Testing how the program handles
     * keywords and messages
     * @throws WrongFormatException when format is illegal just in case
     * @throws NonAlphaNumericException when data is not alpha numberic
     * just in case
     */
    @Test
    void keywordAndMessageHandlerShouldThrowException() throws WrongFormatException, NonAlphaNumericException{
        InitialHandlerAndStorer ihas = new InitialHandlerAndStorer();
        assertThrows(WrongFormatException.class, () -> {
           ihas.handleMessage(""); 
        });
        
        
        assertThrows(NonAlphaNumericException.class, () ->
        {
            ihas.handleMessage("!?%はりかHE LP qQMEqQ 生放送ObiWAN KeNqObi");
        });
        
        assertThrows(WrongFormatException.class, () -> {
            ihas.handleKeyword("{");
                });
        assertThrows(WrongFormatException.class, () -> {
            ihas.handleKeyword("\t");
                });
        
        assertEquals("help me obiwan", ihas.handleKeyword("help me obiwan"));
        
        assertEquals("help me obiwan", ihas.handleMessage("help me obiwan"));
    }

    /**
     * Make sure that mode works fine and
     * doesn't accept wrong values
     * @param mode given to handle it properly
     */
    @ParameterizedTest
    @ValueSource(strings = {"hello", "world", "ohayo", "java is the best",
        "I like pancakes", "2", "-1"})
    void argsHandlerTestOfIllegals(String mode){
        InitialHandlerAndStorer ihas = new InitialHandlerAndStorer();
        assertThrows(UnexpectedModeException.class, ()->ihas.handleMode(mode));
    }
    
    /**
     * Assert that all generated keywords
     * have length of 10
     * @param length expected length of keyword
     */
    @ParameterizedTest
    @ValueSource(ints = {10, 10, 10, 10, 10})
    void rightGenerationOfKeywordTest(int length){
        InitialHandlerAndStorer ihas = new InitialHandlerAndStorer();
        String keyword = ihas.automaticKeywordSetter();
        assertEquals(keyword.length(), length);
    }
    
    /**
     * Testing for args handler.
     * Make sure it doesn't accept unacceptable
     * keywords as well
     */
    @Test
    void argsHandlerTest(){
        InitialHandlerAndStorer ihas = new InitialHandlerAndStorer();
        ModeEnum argsDecider = ModeEnum.NOARGS_ATALL;
        assertEquals(argsDecider, ihas.argsHandler());
        argsDecider = ModeEnum.NONARGS;
        assertEquals(argsDecider, ihas.argsHandler("Hello", "keyword"));
        assertEquals(argsDecider, ihas.argsHandler("Davininci", "This is Pedro",
                "keyword1", "keyword2"));
        assertEquals(argsDecider, ihas.argsHandler("attack at dawn", "はりか", "栗栖淡"));
        assertEquals(argsDecider, ihas.argsHandler("attack at dawn", "dzie̝kuje", "Zgubiłem"));
        argsDecider = ModeEnum.ARGS;
        assertEquals(argsDecider, ihas.argsHandler("Hello world!"));
        assertEquals(argsDecider, ihas.argsHandler("Hello world!", "example", "keyword"));
    }
}
