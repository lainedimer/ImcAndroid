package com.example.comp8.atividade;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.math.BigDecimal;
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

        btnLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                textPeso.setText("");
                textAltura.setText("");
                textPesoIdeal.setText("");
                textImc.setText("");
                textInt.setText("");

            }
        });

        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                int peso = Integer.parseInt(textPeso.getText().toString());
                double altura = Double.parseDouble(textAltura.getText().toString());
                float alturaCm = (float) (altura * 100);
                float pesoIdeal = (float) ((alturaCm - 100) - (((alturaCm - peso)/ 4) * 0.05));
                double imc = peso / Math.pow(altura, 2);
                imc = Math.round(imc);
                pesoIdeal = Math.round(pesoIdeal);


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


        new AlertDialog.Builder(this).setTitle("Confirmação").
                setMessage("Deseja continuar com os dados anteriores?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharedPreferences leitura = getSharedPreferences("GRAVA_DISCO", MODE_PRIVATE);
                        final String peso = leitura.getString("key_peso", "");
                        final String altura = leitura.getString("key_altura", "");
                        if (!peso.isEmpty() || !altura.isEmpty()) {
                            textPeso.setText(peso);
                            textAltura.setText(altura);
                        }
                    }
                })
                .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        textPeso.setText("");
                        textAltura.setText("");
                        textPesoIdeal.setText("");
                        textImc.setText("");
                        textInt.setText("");
                    }
                })
                .show();
    }
}

