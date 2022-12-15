package pl.sydygaliev.java_journey.model;

/**
 *
 * @author Ulan Sydygaliev
 */
public class MessageModel {

    private String message;

    private String keyword1;
    private String keyword2;

    private int[] messageStatus;

    private String encryptedMessage;
    private String decryptedMessage;

    private String originalMessageKeys;
    private String tunedMessage;
    private MessageOperator messageOperator;

    public MessageModel(String message, String keyword1, String keyword2) {

        this.messageOperator = new MessageOperator(keyword1, keyword2);

        this.tunedMessage = tuneMessage(message);
        

        this.encryptedMessage = messageOperator.encryptMessage(tunedMessage);
        this.decryptedMessage = messageOperator.decryptMessage(encryptedMessage);
    }

    private String tuneMessage(String message) {
        char[] messageArray = message.toCharArray();
        StringBuilder originalMessageKeys = new StringBuilder();

        for (int i = 0; i < messageArray.length; i++) {
            char currentLetter = messageArray[i];
            if (currentLetter == 'q' || currentLetter == 'Q') {
                originalMessageKeys.append(currentLetter);
            } else if (currentLetter >= 65 && currentLetter <= 90) {
                originalMessageKeys.append('0');
                

            } else if (currentLetter >= 97 && currentLetter <= 122) {
                originalMessageKeys.append('1');
                
            } else {
                originalMessageKeys.append(currentLetter);
            }
        }

        StringBuilder tunedMessage = new StringBuilder();
        message = message.toLowerCase();

        
        for (int i = 0; i < message.length(); i++) {
            char currentLetter = message.charAt(i);
            if(currentLetter != 'q' && 
                    (originalMessageKeys.charAt(i) == '0' ||
                    originalMessageKeys.charAt(i) == '1')){
                 tunedMessage.append(currentLetter);
            }
            
        }
        if(tunedMessage.length() % 2 != 0){
            originalMessageKeys.setCharAt(
                    originalMessageKeys.length() - 1, tunedMessage.charAt(tunedMessage.length() - 1));
            tunedMessage.deleteCharAt(tunedMessage.length() - 1);
        }
        
        this.originalMessageKeys = originalMessageKeys.toString();



        return tunedMessage.toString();
    }
    
    private String restoreMessage(String decryptedMessage){
        StringBuilder restoredMessage = new StringBuilder();
        
        int nonTunedCounter = 0;
        for(int i = 0; i < originalMessageKeys.length(); i++){
            char currentLetter = originalMessageKeys.charAt(i);
            switch (currentLetter) {
                case '0' -> restoredMessage.append((char)(decryptedMessage.charAt(i-nonTunedCounter) - 32));
                case '1' -> restoredMessage.append(decryptedMessage.charAt(i-nonTunedCounter));
                default -> {
                    restoredMessage.append(originalMessageKeys.charAt(i));
                    nonTunedCounter++;
                }
            }
        }
        
        return restoredMessage.toString();
    }

    public String getEncryptedMessage() {
        return this.encryptedMessage;
    }

    public String getDecryptedMessage() {
        return restoreMessage(this.decryptedMessage);
    }


    public String getMessage() {
        return this.message;
    }

}
