package pl.sydygaliev.java_journey.model;

import java.util.Random;
import pl.sydygaliev.java_journey.model.exception.NonAlphaNumericException;
import pl.sydygaliev.java_journey.model.exception.WrongFormatException;

/**
 *
 * @author Ulan Sydygaliev
 */
public class InitialHandlerAndStorer {

    private String message;
    private String keyword1;
    private String keyword2;

    private String mode;
    public String handleMessage(String message) {
        try {
            for (int i = 0; i < message.length(); i++) {
                if (!(
                        (message.charAt(i) <= 122 && message.charAt(i) >= 97) || 
                        (message.charAt(i) <= 90 && message.charAt(i) >= 63) ||
                        (message.charAt(i) <= 57 && message.charAt(i) >= 32)
                        ) || message.charAt(i) == 63) {
                    throw new NonAlphaNumericException();
                }
            }
        }catch(NonAlphaNumericException e){
            return null;
        }

        return message;
    }

    public String handleKeyword(String keyword) {
        try {
            for (int i = 0; i < keyword.length(); i++) {

                if (keyword.charAt(i) > 122 || keyword.charAt(i) < 32) {
                    throw new WrongFormatException();
                }
            }

        } catch (WrongFormatException e) {
            return null;
        }
        return keyword;
    }

    public void handleMode(String mode) {
        if ("1".equals(mode) || "0".equals(mode)) {
            this.mode = mode;
        } else {
            this.mode = null; 
        }
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = handleMessage(message);
    }

    public String getKeyword1() {
        return keyword1;
    }

    public void setKeyword1(String keyword1) {
        this.keyword1 = handleKeyword(keyword1);
    }

    public String getKeyword2() {
        return keyword2;
    }

    public void setKeyword2(String keyword2) {
        this.keyword2 = handleKeyword(keyword2);
    }
    public String getMode(){
        return this.mode;
    }
    
    public String automaticKeywordSetter(){
        StringBuilder keyword = new StringBuilder();
        Random rand = new Random();
        for(int i = 0; i < 10; i++){
            char letter = (char)( 97 + rand.nextInt(26));
            int count = 0;
            for(int j = 0; j < keyword.length(); j++){
                if(letter == keyword.charAt(j)){
                    count++;
                }
            }
            if(count == 0){
                keyword.append(letter);
            }else{
                i--;
            }
            
        }
        return keyword.toString();
    }

}
