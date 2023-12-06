package com.example.websocketter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;

import io.socket.client.IO;
import io.socket.client.Socket;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class MainActivity extends AppCompatActivity {
    private WebSocket webSocket;

    public WebSocket mainWebSocket;
    private OkHttpClient okHttpClient;

    private Socket socket;

    Dialog dialog;

    private final class MyWebSocketListener extends WebSocketListener {

        @Override
        public void onOpen(WebSocket webSocket, okhttp3.Response response) {
            System.out.println("SANTHOSH Connection Created in Progress");

            super.onOpen(webSocket, response);
            // WebSocket connection opened, you can perform actions here
            System.out.println(" SANTHOSH Connection Created");
//            webSocket.send("San Opened");
            mainWebSocket = webSocket;
            try {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView textView = findViewById(R.id.connectionStatus);
                        textView.setText("Connected Successfully");
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            super.onMessage(webSocket, text);
            System.out.println("SANTHOSH Message Received "+ text);

            try {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showImageDialog(text);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

//            dialog.setContentView(R.layout.dialog);
//            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            // Handle received messages
        }

        @Override
        public void onMessage(WebSocket webSocket, ByteString text) {
            super.onMessage(webSocket, text);
            System.out.println("SANTHOSH Message Received Byte");

            // Handle received messages
        }

        @Override
        public void onClosed(WebSocket webSocket, int code, String reason) {
            super.onClosed(webSocket, code, reason);
            System.out.println("SANTHOSH Connection Closed");

            // WebSocket connection closed, handle accordingly
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, okhttp3.Response response) {
            super.onFailure(webSocket, t, response);
            System.out.println("SANTHOSH Connection Failure " + t.getMessage());

            // Handle failures
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dialog = new Dialog(this);

        okHttpClient = new OkHttpClient();
//        connectWebSocket();
    }


    public void connectWebSocket(View view) {
        // Button click handler to connect to WebSocket
        System.out.println("SANTHOSH Creating a Connection");
        Request request = new Request.Builder()
                .url("ws://192.168.10.11:8080/test")
                .build();

        MyWebSocketListener listener = new MyWebSocketListener();
        webSocket = okHttpClient.newWebSocket(request, listener);
//        okHttpClient.dispatcher().executorService().shutdown();


        // Socket io implementation
//
//        try {
//            // Change the URL to your Socket.IO server
//            socket = IO.socket("http://localhost:8878");
//            socket.connect();
//            System.out.println("SOCKET SUCCESS INIT");
//            socket.emit("new message", "hello this is santhosh");
//
//        } catch (URISyntaxException e) {
//            System.out.println("SOCKET ERROR INIT");
//            Log.v("SOCKET ERROR INI", "SOCKET ERROR INIT " + e.getLocalizedMessage());
//            e.printStackTrace();
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        socket.disconnect();
    }

    public void sendMessage(View view) {
        EditText editText = findViewById(R.id.response);
        String enteredText = editText.getText().toString();
        mainWebSocket.send(enteredText);
    }


    public void showImageDialog(String message) {

        //            dialog.setContentView(R.layout.dialog);
//            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Context context = MainActivity.this;

        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog, null);
        TextView descriptionTextView = dialogView.findViewById(R.id.description);
        descriptionTextView.setText(message);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();
        dialog.show();

//        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//        builder.setTitle("abc");
//        builder.setMessage("abcde");
//        builder.show();


    }




    }