/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.sydygaliev.java_journey.model;

import java.util.Arrays;
import pl.sydygaliev.java_journey.model.exception.NonAlphaNumericException;
import pl.sydygaliev.java_journey.view.View;

/**
 *
 * @author Ulan Sydygaliev
 */
public class MessageOperator {

    private final char[][] SQUARE_FOR_ENCRYPTION = {
        {
            'a', 'b', 'c', 'd', 'e'
        }, {
            'f', 'g', 'h', 'i', 'j'
        }, {
            'k', 'l', 'm', 'n', 'o'
        }, {
            'p', 'r', 's', 't', 'u'
        }, {
            'v', 'w', 'x', 'y', 'z'
        }
    };

    private char[][] squareFromKeyword1;
    private char[][] squareFromKeyword2;
    private char[][] fourSquareMatrix;

    public MessageOperator(String keyword1, String keyword2) {
        //initialization of main 10x10 matrix
        fourSquareMatrix = new char[10][10];

        //preparing keywords for squares
        char[] tunedKeyword1 = keywordPolish(keyword1);
        char[] tunedKeyword2 = keywordPolish(keyword2);

        //setup for testing
        for (int i = 0; i < fourSquareMatrix.length; i++) {
            for (int j = 0; j < fourSquareMatrix[0].length; j++) {
                fourSquareMatrix[i][j] = 'a';
            }
        }

        //set up for two generic squares
        for (int i = 0; i < SQUARE_FOR_ENCRYPTION.length; i++) {
            for (int j = 0; j < SQUARE_FOR_ENCRYPTION[0].length; j++) {
                fourSquareMatrix[i][j] = SQUARE_FOR_ENCRYPTION[i][j];
            }
        }

        for (int i = 5; i < SQUARE_FOR_ENCRYPTION.length + 5; i++) {
            for (int j = 5; j < SQUARE_FOR_ENCRYPTION[0].length + 5; j++) {
                fourSquareMatrix[i][j] = SQUARE_FOR_ENCRYPTION[i - 5][j - 5];
            }
        }

        squareFromKeyword1 = generateMatrixOnTunedKeyword(tunedKeyword1);
        squareFromKeyword2 = generateMatrixOnTunedKeyword(tunedKeyword2);

        for (int i = 0; i < 5; i++) {
            for (int j = 5; j < 10; j++) {
                fourSquareMatrix[i][j] = squareFromKeyword1[i][j - 5];
            }
        }

        for (int i = 5; i < 10; i++) {
            for (int j = 0; j < 5; j++) {
                fourSquareMatrix[i][j] = squareFromKeyword2[i - 5][j];
            }
        }

    }

    private char[] keywordPolish(String keyword) {
        keyword = keyword.toLowerCase();
        String tunedKeyword = "";

        char[] keywordArray = keyword.toCharArray();
        for (char i : keywordArray) {
            if (i != 'q' && (i >= 97 && i <= 122)) {
                tunedKeyword += i;
            }
        }

        String finalTunedKeyword = "";
        // loop to ensure that every letter is unique
        for (char keywordLetter : tunedKeyword.toCharArray()) {
            int contains = 0;
            for (int i = 0; i < finalTunedKeyword.length(); i++) {
                if (keywordLetter == finalTunedKeyword.charAt(i)) {
                    contains++;
                }
            }
            if (contains > 0) {
                continue;
            } else {
                finalTunedKeyword += keywordLetter;
            }
        }

        return finalTunedKeyword.toCharArray();
    }

    private boolean isCharacterPresentAndCurrent(char character, char[] characters) {
        char[] newArray = characters.clone();
        Arrays.sort(newArray);
        for (char i : newArray) {
            if (character == i) {
                return true;
            }
        }
        return false;

    }

    private char[][] generateMatrixOnTunedKeyword(char[] tunedKeyword) {
        char[][] matrixOnTunedKeyword = new char[5][5];
        int letters = 97;

        int count = tunedKeyword.length;

        //setting up characters from the keyword
        int countForLettersFromKeyword = 0;
        for (int i = 0; i < tunedKeyword.length / 5; i++) {
            for (int j = 0; j < 5; j++) {
                matrixOnTunedKeyword[i][j] = tunedKeyword[countForLettersFromKeyword];
                countForLettersFromKeyword++;
            }
        }
        for (int i = 0; i < tunedKeyword.length % 5; i++) {
            matrixOnTunedKeyword[tunedKeyword.length / 5][i]
                    = tunedKeyword[tunedKeyword.length - tunedKeyword.length % 5 + i];
        }

        //setting the rest of letters for the matrix
        int isFirstRun = 0;
        for (int i = tunedKeyword.length / 5; i < 5; i++) {
            if (isFirstRun > 0) {
                for (int j = 0; j < 5; j++) {
                    char letter = (char) letters;
                    if (!isCharacterPresentAndCurrent(letter, tunedKeyword) && letter != 'q') {
                        matrixOnTunedKeyword[i][j] = letter;
                        letters++;
                    } else {
                        letters++;
                        j--;
                    }
                }
            } else {
                for (int j = tunedKeyword.length % 5; j < 5; j++) {
                    char letter = (char) letters;
                    if (!isCharacterPresentAndCurrent(letter, tunedKeyword) && letter != 'q') {
                        matrixOnTunedKeyword[i][j] = letter;
                        letters++;
                    } else {
                        letters++;
                        j--;
                    }

                }
            }

            isFirstRun++;
        }

        return matrixOnTunedKeyword;
    }

    //work in progress
    public String encryptMessage(String tunedMessage) {
        StringBuilder encryptedMessage = new StringBuilder();
        for (int i = 0; i < tunedMessage.length() - 1; i += 2) {
            char firstCharacter = tunedMessage.charAt(i);
            char secondCharacter = tunedMessage.charAt(i + 1);

            char encryptedCharacter1 = '!';
            char encryptedCharacter2 = '!';

            int coordinates1 = 0;
            int coordinates2 = 0;

            for (int j = 0; j < 25; j++) {
                if (firstCharacter == this.SQUARE_FOR_ENCRYPTION[j / 5][j % 5]) {
                    coordinates1 = j;
                    break;
                }
            }

            for (int j = 0; j < 25; j++) {
                if (secondCharacter == this.SQUARE_FOR_ENCRYPTION[j / 5][j % 5]) {
                    coordinates2 = j;
                    break;
                }
            }

            encryptedCharacter1 = this.squareFromKeyword1[coordinates1 / 5][coordinates2 % 5];
            encryptedCharacter2 = this.squareFromKeyword2[coordinates2 / 5][coordinates1 % 5];

            encryptedMessage.append(encryptedCharacter1);
            encryptedMessage.append(encryptedCharacter2);


        }

        return encryptedMessage.toString();
    }

    public String decryptMessage(String encryptedMessage) {
        StringBuilder decryptedMessage = new StringBuilder();

        for (int i = 0; i < encryptedMessage.length() - 1; i += 2) {
            char firstCharacter = encryptedMessage.charAt(i);
            char secondCharacter = encryptedMessage.charAt(i + 1);

            char decryptedCharacter1;
            char decryptedCharacter2;

            int coordinates1 = 0;
            int coordinates2 = 0;

            for (int j = 0; j < 25; j++) {
                if (firstCharacter == this.squareFromKeyword1[j / 5][j % 5]) {
                    coordinates1 = j;
                    break;
                }
            }

            for (int j = 0; j < 25; j++) {
                if (secondCharacter == this.squareFromKeyword2[j / 5][j % 5]) {
                    coordinates2 = j;
                    break;
                }
            }

            decryptedCharacter1 = this.SQUARE_FOR_ENCRYPTION[coordinates1 / 5][coordinates2 % 5];
            decryptedCharacter2 = this.SQUARE_FOR_ENCRYPTION[coordinates2 / 5][coordinates1 % 5];

            decryptedMessage.append(decryptedCharacter1);
            decryptedMessage.append(decryptedCharacter2);
            


        }
        return decryptedMessage.toString();
    }

    public String messageHandler(String message) {
        try {
            int count = 0;
            for (char i : message.toCharArray()) {
            }
            if (count > 0) {
                throw new NonAlphaNumericException();
            }
        } catch (NonAlphaNumericException e) {
            return null;
        }
        return message;
    }

}
