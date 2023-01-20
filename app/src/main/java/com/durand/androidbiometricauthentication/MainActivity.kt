package com.durand.androidbiometricauthentication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.FragmentActivity
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    private lateinit var authenticate:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        authenticate = findViewById(R.id.authenticate)

        val executor = Executors.newSingleThreadExecutor()
        val activity: FragmentActivity = this // reference to activity

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Hola por favor usa tu huella digital o rostro!")
            .setSubtitle("Aqui debo escribir un subtitulo!")
            .setDescription("Mi descripcion!")
            .setNegativeButtonText("Cancelar")
            .build()

        val biometricPrompt = BiometricPrompt(activity, executor, object : BiometricPrompt.AuthenticationCallback() {

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
                    // user clicked negative button
                } else {
                    Log.d("josue","aqui entro: onAuthenticationError")
                }
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                val intent = Intent(this@MainActivity, SecondActivity::class.java)
                startActivity(intent)
                finish()

            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                //Toast.makeText(applicationContext,"Hay un error vuelva a intentarlo!",Toast.LENGTH_SHORT).show()
            }
        })

        authenticate.setOnClickListener {
            biometricPrompt.authenticate(promptInfo)
        }
    }

}