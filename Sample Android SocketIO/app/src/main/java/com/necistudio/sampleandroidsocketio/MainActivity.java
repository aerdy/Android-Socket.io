package com.necistudio.sampleandroidsocketio;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView txtMessage;
    private EditText editMessage;
    private Button btnSend;
    private Socket mSocket;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtMessage = findViewById(R.id.txtMessage);
        editMessage = findViewById(R.id.editMessage);
        btnSend = findViewById(R.id.btnSend);
        btnSend.setOnClickListener(this);


        try {
            mSocket = IO.socket("http://192.168.0.105:3000");
        } catch (URISyntaxException e) {
        }

        mSocket.on("bb", onNewMessage);
        mSocket.connect();
    }


    private void sendMessage(String message) {
        mSocket.emit("bb", message);
    }


    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Log.e("data", args[0].toString());
                        txtMessage.append(args[0].toString());
                    } catch (Exception e) {

                    }

                }
            });
        }
    };

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btnSend) {
            sendMessage(editMessage.getText().toString());
            editMessage.setText("");
        }
    }
}
