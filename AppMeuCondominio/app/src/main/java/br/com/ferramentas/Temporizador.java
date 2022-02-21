package br.com.ferramentas;

import android.util.Log;

public class Temporizador {
    String TAG;

    public void time() {
        Log.w(TAG, "debug TIME 03");
       /* String TAG = null;
        Timer timer = new Timer();//instaciemnto da ferramenta TIMER.
        timer.schedule(new TimerTask() {// temporizador para executar a troca de tela em 3 segundos,
            // deixando a messagem de sucesso visivel por esse tempo.
            @Override
            public void run() {

            }
        }, segundos);*/
        for(int t = 0; t <= 3; t++){
            try {
                Thread.sleep(1000);
                t = t+1;
                Log.w(TAG, "debug TIME 04");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Log.w(TAG, "debug TIME 05");
    }
}
