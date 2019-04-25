package br.com.klenne.gates_app

import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var acessoSharedPref : SharedPreferences? = null
    var editorSharedPref : SharedPreferences.Editor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        acessoSharedPref = getSharedPreferences("meusDados", Context.MODE_PRIVATE)
        editorSharedPref = acessoSharedPref!!.edit() // !!garanti que tera algo (!! igual a forced)

        //Habilitando java script
        webGates.settings.javaScriptEnabled = true

        // logar na pagina web home
        //webGates.loadUrl("https://www.google.com")
        var loadUrldefault = "https://www.google.com"
        //navegar pela url digitada


        btnGo.setOnClickListener {
        if (txtUrl.text.isNotEmpty()){
            var urlDigitada = txtUrl.text.toString()

            if (urlDigitada.contains("https://", true) ){ // verifica se a url tem o prefiso https://
                webGates.loadUrl(urlDigitada)
                txtUrl.setText(urlDigitada)

            }else{
                urlDigitada = "https://" + urlDigitada
                webGates.loadUrl(urlDigitada)
                txtUrl.setText(urlDigitada)
            }
        }else{
            Toast.makeText(this, "Digite uma url !",Toast.LENGTH_LONG).show()
            //load default
            webGates.loadUrl(loadUrldefault)
            }
        }
    }
}

