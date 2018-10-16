package Commands;

import com.google.gson.JsonParser;

import com.wrapper.spotify.SpotifyApi;

import com.wrapper.spotify.exceptions.SpotifyWebApiException;

import com.wrapper.spotify.requests.data.player.StartResumeUsersPlaybackRequest;

import SpotifyApi.AuthorizationTokenSpotify;

import java.io.IOException;

import java.util.concurrent.ExecutionException;

import java.util.concurrent.Future;



public class StartStopCommand {


    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()

            .setAccessToken(new AuthorizationTokenSpotify().getAccessToken())

            .build();

    private static final StartResumeUsersPlaybackRequest startResumeUsersPlaybackRequest = spotifyApi

            .startResumeUsersPlayback()

            .device_id("41457776bfe3e6701ddb05af2767d4a1b6c3985f")

            .build();


    public static void startResumeUsersPlayback_Sync() {

        try {

            final String string = startResumeUsersPlaybackRequest.execute();





        } catch (IOException | SpotifyWebApiException e) {

            System.out.println("Error: " + e.getMessage());

        }

    }







}