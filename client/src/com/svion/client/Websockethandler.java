package com.svion.client;

import android.app.Activity;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.Log;
import com.codebutler.android_websockets.WebSocketClient;
import omsu.svion.MessagesHandlersResolver;
import omsu.svion.game.handler.MessageFromServerHandler;
import omsu.svion.messages.KeepAliveMessage;
import omsu.svion.messages.MessageFromServer;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

/**
 * Created by victor on 03.05.14.
 */
public class Websockethandler {
    private static WebSocketClient webSocketClient;
    private static KeepAliveSender keepAliveSender;
    public static void openWebosket(String url) {
        List<BasicNameValuePair> extraHeaders = Arrays.asList(
                new BasicNameValuePair("Cookie", "JSESSIONID=" + HttpClientHandler.getInstance().getSessionId())
        );
           webSocketClient  = new WebSocketClient(URI.create(url), new WebSocketClient.Listener() {
            private ObjectMapper objectMapper = new ObjectMapper();
            @Override
            public void onConnect() {

            }

            @Override
            public void onMessage(String message) {
                Log.d("got message ", message);
                try {
                    String className = objectMapper.readTree(message).path("className").asText();
                    Log.d("className is",className);
                    if (className == null)
                        return;
                    try {
                        Class messageClass =  Class.forName(className);
                        Log.d("lol 1",messageClass.getCanonicalName());
                        if (MessageFromServer.class.isAssignableFrom(messageClass)) {
                            Log.d("lol 1/1","assignable");
                            MessageFromServer msg = objectMapper.readValue(message,(Class<? extends MessageFromServer>)messageClass);
                            Log.d("lol 2 message class",msg.getClass().getCanonicalName());
                            MessageFromServerHandler[] handlers = MessagesHandlersResolver.getHandlers().get(msg.getClass());
                            if (handlers == null) {
                                Log.d("lol 3","no handlers");
                                return;
                            }
                            Log.d("lol 4",((Integer)handlers.length).toString());
                            for (MessageFromServerHandler messageFromServerHandler: handlers) {
                                Log.d("lol 5 ",messageFromServerHandler.toString());
                                messageFromServerHandler.handle(msg);
                            }
                        }

                    }
                    catch (ClassNotFoundException e) {
                        Log.d("error",e.toString());
                    }
                }
                catch (IOException e) {
                    Log.d("error",e.toString());
                }
            }

            @Override
            public void onMessage(byte[] data) {

            }

            @Override
            public void onDisconnect(int code, String reason) {

            }

            @Override
            public void onError(Exception error) {

            }

        }, extraHeaders);

        webSocketClient.connect();
         keepAliveSender = new KeepAliveSender(webSocketClient);
        keepAliveSender.execute();
    }
    public static WebSocketClient getWebsocketClient() {
        return webSocketClient;
    }
    public static void closeWebsocket() {
        if (keepAliveSender != null) {
            keepAliveSender.cancel(true);
        }
        if (webSocketClient != null) {
            webSocketClient.disconnect();
            webSocketClient = null;
        }
    }
    private static class KeepAliveSender extends AsyncTask{
        public KeepAliveSender(WebSocketClient webSocketClient) {
            this.webSocketClient = webSocketClient;
        }
        private WebSocketClient webSocketClient;
        private ObjectMapper objectMapper = new ObjectMapper();
        private KeepAliveMessage keepAliveMessage = new KeepAliveMessage();
        @Override
        protected Object doInBackground(Object[] params) {
            while (!isCancelled()) {
                try {
                    Thread.sleep(15000);
                    try {
                        webSocketClient.send(objectMapper.writeValueAsString(keepAliveMessage));
                        Log.d("lol","sent keepalive");
                    } catch (IOException e) {
                        Log.d("error",e.toString());
                    }
                }
                catch (InterruptedException e) {
                    return null;
                }

            }
            return null;

        }
    }
}
