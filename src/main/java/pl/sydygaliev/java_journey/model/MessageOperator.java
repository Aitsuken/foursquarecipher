package pl.sydygaliev.java_journey.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import pl.sydygaliev.java_journey.model.exception.NonAlphaNumericException;
import pl.sydygaliev.java_journey.view.View;

/**
 * Handles encrypting and decrypting operations. The heart of this program.
 *
 * @author Ulan Sydygaliev
 * @version f2
 */
public class MessageOperator {

    /**
     * Generic 5x5 matrix containing all letters from the English alphabet
     * except q
     */
    private final List<Character> squareForEncryption = new ArrayList<>();
    
    /**
     * 5x5 that will be generated based on provided keyword1
     */
    private List<Character> squareFromKeyword1 = new ArrayList<>();
    /**
     * 5x5 that will be generated based on provided keyword2
     */
    private List<Character> squareFromKeyword2 = new ArrayList<>();

    /**
     * Operates with keywords. Fining up the keywords for stable matrix
     * generation.
     *
     * @param keyword1 used for generating the top-right square
     * @param keyword2 used for generating the bottom-left square
     */
    public MessageOperator(String keyword1, String keyword2) {

        //generic "matrix" initialization
        for (int i = (int) 'a'; i <= (int) 'z'; i++) {
            if ((char) i != 'q') {
                squareForEncryption.add((char) i);
            }
        }

        //preparing keywords for squares
        List<Character> tunedKeyword1 = keywordPolish(keyword1);

        List<Character> tunedKeyword2 = keywordPolish(keyword2);

        squareFromKeyword1 = generateMatrixOnTunedKeyword(tunedKeyword1);
        squareFromKeyword2 = generateMatrixOnTunedKeyword(tunedKeyword2);
    }

    /**
     * Used to tune keyword and leaves only English letters valid for encryption
     *
     * @param keyword keyword to be tuned
     * @return polished keyword
     */
    private List<Character> keywordPolish(String keyword) {
        keyword = keyword.toLowerCase();
        String tunedKeyword = "";

        List<Character> keywordList = keyword.chars()
                .mapToObj(character -> (char) character)
                //
                .filter(p -> p != 'q' && (p >= 97 && p <= 122))
                .collect(Collectors.toList());
        
        for (char i : keywordList) {
            if (i != 'q' && (i >= 97 && i <= 122)) {
                tunedKeyword += i;
            }
        }
        List<Character> finalTunedKeyword = new ArrayList<>();
        // loop to ensure that every letter is unique
        for (char keywordLetter : tunedKeyword.toCharArray()) {
            int contains = 0;
            for (int i = 0; i < finalTunedKeyword.size(); i++) {
                if (keywordLetter == finalTunedKeyword.get(i)) {
                    contains++;
                }
            }
            if (contains == 0) {
                finalTunedKeyword.add(keywordLetter);
            }
        }

        return finalTunedKeyword;
    }

    /**
     * Detects if character is present within the matrix
     *
     * @param character character to be checked with matrix
     * @param characters matrix to be checked
     * @return true if character is present and current or false otherwise
     */
    private boolean isCharacterPresentAndCurrent(char character, List<Character> characters) {
        List<Character> sortedList = new ArrayList<>(characters);
        Collections.sort(sortedList);
        for (char i : sortedList) {
            if (character == i) {
                return true;
            }
        }
        return false;
    }

    /**
     * Generates matrix based on provided keyword
     *
     * @param tunedKeyword polished keyword
     * @return generated matrix
     */
    private List<Character> generateMatrixOnTunedKeyword(List<Character> tunedKeyword) {
        List<Character> matrixOnTunedKeyword = new ArrayList<>(25);
        int letters = 97;

        int count = tunedKeyword.size();

        //setting up characters from the keyword
        int countForLettersFromKeyword = 0;
        for (int i = 0; i < tunedKeyword.size(); i++) {
            matrixOnTunedKeyword.add(tunedKeyword.get(countForLettersFromKeyword));
            countForLettersFromKeyword++;
        }

        //setting the rest of letters for the matrix
        int isFirstRun = 0;
        for (int i = tunedKeyword.size() / 5; i < 5; i++) {
            if (isFirstRun > 0) {
                for (int j = 0; j < 5; j++) {
                    char letter = (char) letters;
                    if (!isCharacterPresentAndCurrent(letter, tunedKeyword) && letter != 'q') {
                        matrixOnTunedKeyword.add(letter);
                        letters++;
                    } else {
                        letters++;
                        j--;
                    }
                }
            } else {
                for (int j = tunedKeyword.size() % 5; j < 5; j++) {
                    char letter = (char) letters;
                    if (!isCharacterPresentAndCurrent(letter, tunedKeyword) && letter != 'q') {
                        matrixOnTunedKeyword.add(letter);
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

    /**
     * Encrypts provided tuned message using four square cipher
     *
     * @param tunedMessage message that was tuned, it's important to have
     * message with only English letters
     * @return encrypted message
     */
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
                if (firstCharacter == this.squareForEncryption.get(j)) {
                    coordinates1 = j;
                    break;
                }
            }

            for (int j = 0; j < 25; j++) {
                if (secondCharacter == this.squareForEncryption.get(j)) {
                    coordinates2 = j;
                    break;
                }
            }

            encryptedCharacter1 = this.squareFromKeyword1.get(coordinates1 / 5 * 5 + coordinates2 % 5);
            encryptedCharacter2 = this.squareFromKeyword2.get(coordinates2 / 5 * 5 + coordinates1 % 5);

            encryptedMessage.append(encryptedCharacter1);
            encryptedMessage.append(encryptedCharacter2);

        }

        return encryptedMessage.toString();
    }

    /**
     * Decrypts provided encrypted message
     *
     * @param encryptedMessage message that is encrypted using cipher
     * @return decrypted message
     */
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
                if (firstCharacter == this.squareFromKeyword1.get((j / 5 * 5 + j % 5))) {
                    coordinates1 = j;
                    break;
                }
            }

            for (int j = 0; j < 25; j++) {
                if (secondCharacter == this.squareFromKeyword2.get(j / 5 * 5 + j % 5)) {
                    coordinates2 = j;
                    break;
                }
            }

            decryptedCharacter1 = this.squareForEncryption.get(coordinates1 / 5 * 5 + coordinates2 % 5);
            decryptedCharacter2 = this.squareForEncryption.get(coordinates2 / 5 * 5 + coordinates1 % 5);

            decryptedMessage.append(decryptedCharacter1);
            decryptedMessage.append(decryptedCharacter2);

        }
        return decryptedMessage.toString();
    }
}
