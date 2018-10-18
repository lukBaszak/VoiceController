package SpotifyApi;

import com.sun.istack.internal.NotNull;
import com.wrapper.spotify.SpotifyApi;

import com.wrapper.spotify.SpotifyHttpManager;

import com.wrapper.spotify.exceptions.SpotifyWebApiException;

import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;

import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRefreshRequest;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.io.InputStream;
import java.net.URI;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

import java.util.concurrent.Future;



public class AuthorizationTokenSpotify {
    private static Properties loginProperties = new Properties();
    private static InputStream inputStream;
    private static String clientId;
    private static String clientSecret;
    private static String refreshToken;

    private String accessToken;





    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()

            .setClientId("xxxxxxxxxxxxx")
            .setClientSecret( "xxxxxxxxxxxxxxxxxx")
            .setRefreshToken("xxxxxxxxxxxxxxxxxxx")
            .build();


    private static final AuthorizationCodeRefreshRequest authorizationCodeRefreshRequest = spotifyApi.authorizationCodeRefresh()

            .build();

    public static void authorizationCodeRefresh_Sync() {

        try {
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRefreshRequest.execute();

             spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());

            spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());



        } catch (IOException | SpotifyWebApiException e) {

            System.out.println("Error: " + e.getMessage());

        }

    }

    public String getAccessToken() {

        authorizationCodeRefresh_Sync();

        return spotifyApi.getAccessToken();
    }
}


