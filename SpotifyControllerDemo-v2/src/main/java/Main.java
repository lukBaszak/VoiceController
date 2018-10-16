import GoogleSpeech.GoogleClient;

public class Main {
    public static void main(String[] args) {

        try {
            new GoogleClient().streamingMicRecognize();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}