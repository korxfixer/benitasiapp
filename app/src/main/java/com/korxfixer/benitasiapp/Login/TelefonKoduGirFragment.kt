package com.korxfixer.benitasiapp.Login


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast

import com.korxfixer.benitasiapp.R
import com.korxfixer.benitasiapp.utils.EventbusDataEvents
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.fragment_telefon_kodu_gir.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.concurrent.TimeUnit
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential



class TelefonKoduGirFragment : Fragment() {

    var gelenTelNo = ""
    lateinit var mCallbacks:PhoneAuthProvider.OnVerificationStateChangedCallbacks
    var verificationID = ""
    var gelenKod=""
    lateinit var progressBar: ProgressBar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        var view=inflater!!.inflate(R.layout.fragment_telefon_kodu_gir, container, false)

        view.tvKullaniciTelNo.setText(gelenTelNo)
        progressBar=view.pbTelNoOnayla

        setupCallback()

        view.btnTelKodIleri.setOnClickListener {

            if(gelenKod.equals(view.etOnayKodu.text.toString().trim())){
                EventBus.getDefault().postSticky(EventbusDataEvents.KayitBilgileriniGonder(gelenTelNo,null, verificationID,gelenKod,false))
                var transaction=activity!!.supportFragmentManager.beginTransaction()
                transaction.replace(R.id.loginContainer,KayitFragment())
                transaction.addToBackStack("kayitFragmentEklendi")
                transaction.commit()
            }else {
                Toast.makeText(activity,"Kod Hatalı",Toast.LENGTH_SHORT).show()
            }

        }


        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                gelenTelNo,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this!!.activity!!,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks


        return view
    }

    private fun setupCallback() {
        progressBar.visibility=View.VISIBLE
        mCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                if(!credential.smsCode.isNullOrEmpty())
                {
                    gelenKod = credential.smsCode!!
                    progressBar.visibility=View.INVISIBLE
                    //Log.e("HATA","on verification completed sms gelmiş:"+ gelenKod)

                }else{
                    //Log.e("HATA","on verification completed sms gelmeyecek")
                }


            }

            override fun onVerificationFailed(e: FirebaseException) {
                 //Log.e("HATA","Hata çıktı: "+e.message)
                Toast.makeText(activity,"hata:"+e.message,Toast.LENGTH_SHORT).show()
                progressBar.visibility=View.INVISIBLE
            }

            override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
               verificationID=p0
                progressBar.visibility=View.VISIBLE
                super.onCodeSent(p0, p1)
            }
/*  #değiştirdim üstekiyle
 override fun onCodeSent(verificationId: String?, token: PhoneAuthProvider.ForceResendingToken?) {
                verificationID =verificationId!!
                progressBar.visibility=View.VISIBLE
                //Log.e("HATA","oncodesent çalıştı")
            }*/
        }
    }

    @Subscribe (sticky = true)
    internal fun onTelefonNoEvent(kayitBilgileri : EventbusDataEvents.KayitBilgileriniGonder){

        gelenTelNo= kayitBilgileri.telNo!!
        //Log.e("emre","Gelen tel no : "+gelenTelNo)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        EventBus.getDefault().register(this)
    }

    override fun onDetach() {
        super.onDetach()
        EventBus.getDefault().unregister(this)
    }
}
