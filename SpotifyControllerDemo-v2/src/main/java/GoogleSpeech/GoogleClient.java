package GoogleSpeech;

import Commands.Commands;
import Utilty.MicrophoneConfig;
import com.google.api.gax.rpc.ClientStream;
import com.google.api.gax.rpc.ResponseObserver;
import com.google.api.gax.rpc.StreamController;
import com.google.cloud.speech.v1.*;
import com.google.protobuf.ByteString;

import javax.sound.sampled.*;
import java.util.ArrayList;

public class GoogleClient {

    static MicrophoneConfig microphoneConfig = new MicrophoneConfig();
    static boolean shouldRecord = true;




    /** Performs microphone streaming speech recognition with a duration of 1 minute. */
    public static void streamingMicRecognize() throws Exception {

        TargetDataLine targetDataLine =  microphoneConfig.getMicrophone();

        AudioFormat audioFormat = microphoneConfig.getAudioFormat();


        ResponseObserver<StreamingRecognizeResponse> responseObserver = null;
        try (SpeechClient client = SpeechClient.create()) {

            responseObserver = new ResponseObserver<StreamingRecognizeResponse>() {
                        ArrayList<StreamingRecognizeResponse> responses = new ArrayList<>();

                        public void onStart(StreamController controller) {}

                        public void onResponse(StreamingRecognizeResponse response) {

                            responses.add(response);
                            onComplete();
                            responses.clear();
                        }

                public void onComplete() {
                    for (StreamingRecognizeResponse response : responses) {
                        StreamingRecognitionResult result = response.getResultsList().get(0);
                        SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
                        System.out.printf("Transcript : %s\n", alternative.getTranscript());

                        String command = alternative.getTranscript().toLowerCase();
                        new Commands().checkCommand(command);
                    }
                }

                        public void onError(Throwable t) {
                            System.out.println(t);
                        }
                    };

            ClientStream<StreamingRecognizeRequest> clientStream = client.streamingRecognizeCallable().splitCall(responseObserver);

           StreamingRecognizeRequest request = new Config().getRecognizeRequest();
            clientStream.send(request);



            targetDataLine.open(audioFormat);
            AudioInputStream audio = new AudioInputStream(targetDataLine);
            targetDataLine.start();

            System.out.println("Start speaking");

            while (shouldRecord) {
                byte[] data = new byte[6400];
                audio.read(data);
                request = StreamingRecognizeRequest.newBuilder()
                        .setAudioContent(ByteString.copyFrom(data))
                        .build();
                clientStream.send(request);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
