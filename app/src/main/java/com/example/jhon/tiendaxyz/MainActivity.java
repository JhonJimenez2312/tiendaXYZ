package com.example.jhon.tiendaxyz;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView total;
    private Resources resources;
    private Spinner spinnerMateriales,spinnerDijes,spinnerTipos,spinnerTipoMoneda;
    private String arrayOpciones[];
    ArrayAdapter<String> adapter;
    private EditText cantidad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /* ----------------------------------------------------------------------------
           | cuero 40,ancla 40,oro 40 =120                                            |
           | cuero 40,martillo 20,plata 20 = 80                                       |
           | cuero 40,martillo 20,niquel 10 =70                                       |
           | cuero 40,ancla 40,plata 20 =100                                          |
           | cuerda 30,ancla 40,oro 40 = 110                                          |
           | cuerda 30,martillo 20,oro 40=90                                          |
           | cuerda 30, martillo 20,plata 20 = 70                                     |
           | cuerda 30, martillo 20,niquel 0 = 50 (Error niquel deberia valer 10 )    |
           | cuerda 30,ancla 40, oro 40= 110                                          |
           | cuerda 30,ancla 40,plata 20 =90                                          |
           | cuerda 30,ancla 40,niquel 10 =80                                         |
           ---------------------------------------------------------------------------*/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resources = this.getResources();
        total = (TextView)findViewById(R.id.txtTotal);

        //llenado del spinner de materiales
        spinnerMateriales =(Spinner)findViewById(R.id.cmbMateriales);
        arrayOpciones = resources.getStringArray(R.array.arrayMateriales);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,arrayOpciones);
        spinnerMateriales.setAdapter(adapter);

        //llenado del spinner de dijes
        spinnerDijes =(Spinner)findViewById(R.id.cmbDijes);
        arrayOpciones = resources.getStringArray(R.array.arrayDijes);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,arrayOpciones);
        spinnerDijes.setAdapter(adapter);

        //llenado del spinner de tipos de materiales de construccion del dije
        spinnerTipos =(Spinner)findViewById(R.id.cmbTipos);
        arrayOpciones = resources.getStringArray(R.array.arrayTipos);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,arrayOpciones);
        spinnerTipos.setAdapter(adapter);

        //llenado del spinner de tipo de moneda
        spinnerTipoMoneda =(Spinner)findViewById(R.id.cmbMoneda);
        arrayOpciones = resources.getStringArray(R.array.arrayTipoDeMoneda);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,arrayOpciones);
        spinnerTipoMoneda.setAdapter(adapter);
    }

    public void calcular(View v){
        int opcionMaterial,opcionTipos,opcionDijes,opcionMoneda, valorMaterial;
        double valorTotal=0.0;
        total.setText("");

        if(validar()) {
            //calculo del costo total
            opcionMaterial = spinnerMateriales.getSelectedItemPosition();
            opcionTipos = spinnerTipos.getSelectedItemPosition();
            opcionDijes = spinnerDijes.getSelectedItemPosition();
            opcionMoneda = spinnerTipoMoneda.getSelectedItemPosition();

            valorTotal += opcionMaterial == 0?40:30;
            valorTotal += opcionDijes == 0?20:40;
            valorTotal += (opcionTipos == 0 || opcionTipos == 1) ?40:opcionTipos == 2?20:10;

            //caso de excepcion
            if(opcionMaterial==1 && opcionDijes==0 && opcionTipos==3){
                valorTotal=50;
            }
            valorTotal=opcionMoneda==0?(valorTotal*1000):((valorTotal*1000)/3200);
            String r = opcionMoneda==0?"\n$ COP "+valorTotal:"$ USD "+valorTotal;
            cantidad = (EditText)findViewById(R.id.txtCantidad);
            int cant = Integer.parseInt(cantidad.getText().toString());
          total.setText("\n"+(opcionMoneda==0?"$ COP ":"$ USD ")+valorTotal*cant);
        }
    }
    public void borrar(View v){
        total.setText("");
        cantidad.setText("");
        spinnerTipoMoneda.setSelection(0);
        spinnerMateriales.setSelection(0);
        spinnerTipos.setSelection(0);
        spinnerDijes.setSelection(0);
    }

    public boolean validar(){
        cantidad = (EditText)findViewById(R.id.txtCantidad);
        String x =cantidad.getText().toString();

        if(x.isEmpty()||cantidad.getText().toString().trim().isEmpty()){
            cantidad.setError(resources.getString(R.string.mensaje_error_uno));
            cantidad.setText("");
            cantidad.requestFocus();
            return false;
        }

        return true;
    }
}
