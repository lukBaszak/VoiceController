package Utilty;

import javax.sound.sampled.*;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

public class MicrophoneConfig {

    private Map<Integer, Mixer.Info> microphoneList = new TreeMap<>();
    private Scanner scanner = new Scanner(System.in);

    public TargetDataLine getMicrophone() {

       TargetDataLine targetDataLine = null;

        try {

            Mixer mixer = getAvailableMicrophones();

            AudioFormat audioFormat = getAudioFormat();

            DataLine.Info targetInfo =
                    new DataLine.Info(
                            TargetDataLine.class,
                            audioFormat); // Set the system information to read from the microphone audio stream

            if (!AudioSystem.isLineSupported(targetInfo)) {
                System.out.println("Microphone not supported");

            }
            // Target data line captures the audio stream the microphone produces.
            targetDataLine = (TargetDataLine) mixer.getLine(targetInfo);
        } catch(LineUnavailableException e) {
            System.out.println("Provide good microphone");
        }



        return targetDataLine;
    }

    //error, no microphone available!!!!!!

    public Mixer getAvailableMicrophones() {

        Mixer.Info[] mixerInfo =
                AudioSystem.getMixerInfo();

        int a = 1;

        for(int i=0; i<mixerInfo.length; i++) {

            if (mixerInfo[i].getName().contains("Mikrofon") && !mixerInfo[i].getName().contains("Port")) {
                microphoneList.put(a, mixerInfo[i]);
                a++;
            }
        }

        System.out.println("Which microphone would you like to use?" + "\n");

        Set<Map.Entry<Integer,Mixer.Info>> entrySet = microphoneList.entrySet();

        for(Map.Entry<Integer, Mixer.Info> entry: entrySet) {
            System.out.println(entry.getKey() + ". " + entry.getValue());
        }


            int microphoneNumber = scanner.nextInt();

            while(microphoneList.get(microphoneNumber) == null) {
                System.out.println("Choose correct number");

                microphoneNumber = scanner.nextInt();

            }

         Mixer.Info chosenMicrophone = microphoneList.get(microphoneNumber);

        Mixer mixer = AudioSystem.getMixer(chosenMicrophone);

        return mixer;
    }

    public AudioFormat getAudioFormat() {
        AudioFormat audioFormat = new AudioFormat(16000, 16, 1, true, false);

        return audioFormat;
    }
 }
