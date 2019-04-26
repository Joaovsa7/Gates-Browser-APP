package br.com.klenne.gates_app

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
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

        //Logando em um endereço web
        wvGates.loadUrl("https://www.google.com.br")

        //Mecher na internet pelo app


        class Gates : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                view?.loadUrl(request?.url.toString())
                return true
            }

            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }

        }

        //Ao clicar no enter carrega a página

        txtUrl.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if(keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP){
                wvGates.loadUrl("https://" + txtUrl.text.toString())

                return@OnKeyListener true}
            false

        } )

        //WebView com zoom
        wvGates.settings.setSupportZoom(true)
        wvGates.settings.builtInZoomControls = true
        wvGates.settings.displayZoomControls = true

        //para selecionar url quando clicar no edt
        txtUrl.setOnClickListener {
            txtUrl.selectAll()
        }



        //Programando os botões

        //botão voltar
        btnVoltar.setOnClickListener {

            if (wvGates.canGoBack())
                wvGates.goBack()

            //Toast
            else Toast.makeText(this, "Sem histórico disponível", Toast.LENGTH_SHORT).show()

        }

        //Botão home
        btnHome.setOnClickListener {
            wvGates.loadUrl("https://www.google.com.br")
        }



        //Botão ir
        btnAvancar.setOnClickListener {

            if (wvGates.canGoForward())
                wvGates.goForward()

            //Toast
            else Toast.makeText(this, "Sem histórico disponível", Toast.LENGTH_SHORT).show()

        }

        //Botão atualizar

        btnAtualizar.setOnClickListener {
            wvGates.reload()
        }


        //para colocar a url no edt url
        fun ColocarUrl() {
            val url: String = wvGates.url
            txtUrl.setText(url, TextView.BufferType.EDITABLE)

        }



        wvGates.webViewClient = Gates()



        //Habilitando java script
        wvGates.settings.javaScriptEnabled = true

        // logar na pagina web home
        //webGates.loadUrl("https://www.google.com")
        var loadUrldefault = "https://www.google.com"
        //navegar pela url digitada


        btnGo.setOnClickListener {
        if (txtUrl.text.isNotEmpty()){
            var urlDigitada = txtUrl.text.toString()

            if (urlDigitada.contains("https://", true) ){ // verifica se a url tem o prefiso https://
                wvGates.loadUrl(urlDigitada)
                txtUrl.setText(urlDigitada)

            }else{
                urlDigitada = "https://" + urlDigitada
                wvGates.loadUrl(urlDigitada)
                txtUrl.setText(urlDigitada)
            }
        }else{
            Toast.makeText(this, "Digite uma url !",Toast.LENGTH_LONG).show()
            //load default
            wvGates.loadUrl(loadUrldefault)
            }
        }
    }
}

