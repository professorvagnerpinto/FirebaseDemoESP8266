package br.edu.ifsul.firebasedemoesp8266.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import br.edu.ifsul.firebasedemoesp8266.R;
import br.edu.ifsul.firebasedemoesp8266.model.ArCondicionado;
import br.edu.ifsul.firebasedemoesp8266.setup.AppSetup;

public class ArCondicioandoActivity extends AppCompatActivity {

    private static final String TAG = "arCondicioandoActivity";
    private DatabaseReference myRef;
    private Switch tbLigaDesliga;
    private SeekBar sbVelociadeVentilador, sbTemperatura, sbUmidade;
    private TextView tvTemperatura, tvVelocidade, tvUmidade;
    private ArCondicionado arCondicionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arcondicionado);

        //obtém a referência para o nó "/arcondicionado" do banco de dados
        myRef = AppSetup.getDBInstance();

        //mapeia os componentes de UI
        tbLigaDesliga = findViewById(R.id.st_ligaDesliga);
        sbVelociadeVentilador = findViewById(R.id.sb_velocidadeVentilador);
        sbTemperatura = findViewById(R.id.sb_temperatura);
        sbUmidade = findViewById(R.id.sb_umidade);
        tvTemperatura = findViewById(R.id.tvTemperatura);
        tvVelocidade = findViewById(R.id.tvVelocidade);
        tvUmidade = findViewById(R.id.tvUmidade);

        //lê o estado inicial do aparelho
        setup();

        //trata eventos do switch liga/desliga
        tbLigaDesliga.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(tbLigaDesliga.isChecked()){
                    myRef.child("estado").setValue(true);
                }
                else{
                    myRef.child("estado").setValue(false);
                }
            }
        });

        //trata eventos da velocidade do ventilador
        sbVelociadeVentilador.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int velocidade = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                velocidade = progress + 1;
                tvVelocidade.setText(String.valueOf(velocidade));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                myRef.child("velocidadeVentilador").setValue(velocidade);
            }
        });

        //trata eventos da velocidade da temperatura
        sbTemperatura.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int temperatura = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                temperatura = progress + 18;
                tvTemperatura.setText(String.valueOf(temperatura) + " ºC");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                myRef.child("temperatura").setValue(temperatura);
            }
        });

        //trata eventos da umidade
        sbUmidade.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvUmidade.setText(String.valueOf(progress)+ " %");
                sbUmidade.setProgress(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                myRef.child("umidade").setValue(sbUmidade.getProgress());
            }
        });

    }//fim onCreate()

    //setup da UI e handler do RealTimeDatabase
    private void setup() {
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //obtém o objeto representando o nó /arcondicionado
                arCondicionado = dataSnapshot.getValue(ArCondicionado.class);
                Log.d(TAG, "Value is: " + arCondicionado.toString());
                //realiza o setup
                tbLigaDesliga.setChecked(arCondicionado.getEstado());
                sbVelociadeVentilador.setProgress(arCondicionado.getVelocidadeVentilador()-1);
                tvVelocidade.setText(arCondicionado.getVelocidadeVentilador().toString());
                sbTemperatura.setProgress(arCondicionado.getTemperatura()-18);
                tvTemperatura.setText(arCondicionado.getTemperatura().toString() + " ºC");
                sbUmidade.setProgress(arCondicionado.getUmidade());
                tvUmidade.setText(arCondicionado.getUmidade().toString() + " %");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
}
