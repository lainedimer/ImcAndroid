package com.example.comp8.atividade;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.DecimalFormat;
import java.util.Locale;

/**
 * Created by comp8 on 22/03/2018.
 */

public class IMC extends AppCompatActivity {
    private Button btnCalcular, btnLimpar;
    private EditText textPeso, textAltura, textPesoIdeal, textImc, textInt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal);

        textPeso = (EditText) findViewById(R.id.textPeso);
        textAltura = (EditText) findViewById(R.id.textAltura);
        textPesoIdeal = (EditText) findViewById(R.id.textPesoIdeal);
        textImc = (EditText) findViewById(R.id.textImc);
        textInt = (EditText) findViewById(R.id.textInt);
        btnCalcular = (Button) findViewById(R.id.btnCalcular);
        btnLimpar = (Button) findViewById(R.id.btnLimpar);


        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                double peso = Double.parseDouble(textPeso.getText().toString());
                double altura = Double.parseDouble(textAltura.getText().toString());
                double alturaCm = (altura * 100);
                double pesoIdeal = (alturaCm - 100) - ((alturaCm - peso) / 4) * (5 / 100);
                double imc = peso / Math.pow(2,altura);

                if (imc < 20) {
                    textInt.setText("Baixo Peso");
                } else if (imc < 25) {
                    textInt.setText("Normal");
                } else if (imc < 30 || imc == 30) {
                    textInt.setText("Acima do Peso");
                } else {
                    textInt.setText("Obeso");
                }
                String pesoIdealS = String.valueOf(pesoIdeal);
                String imcS = String.valueOf(imc);
                textPesoIdeal.setText(pesoIdealS);
                textImc.setText(imcS);

                SharedPreferences.Editor ed = getSharedPreferences("GRAVA_DISCO", MODE_PRIVATE).edit();
                ed.putString("key_peso", textPeso.getText().toString());
                ed.putString("key_altura", textAltura.getText().toString());
                ed.commit();

            }
        });

        SharedPreferences leitura = getSharedPreferences("GRAVA_DISCO", MODE_PRIVATE);
        final String peso = leitura.getString("key_peso", "");
        final String altura = leitura.getString("key_altura", "");
//
//        if (!peso.isEmpty()) {
//            text.setText(nome);
//       }
    }
}

