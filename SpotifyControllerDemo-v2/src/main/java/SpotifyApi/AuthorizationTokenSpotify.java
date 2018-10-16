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

            .setClientId("ba32c2aa4d634b7c907cae0acee3460d")
            .setClientSecret( "d07ae50b68dc488b9c67892d8761521e")
            .setRefreshToken("AQBnelTAi0FduTBYT0XhtJi9JlDEZVt8GOHVqonAZujlMEr7aTZ4DJx0J9M_eOOSum_bK198P0LbGw1O3Q3e_WVLJdH5QPa_106pCL-5SwSZE7N0Y9q1-XaF-jI_CpQcapl9Bg")
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


