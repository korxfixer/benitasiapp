package com.korxfixer.benitasiapp.utils

import android.content.Context
import android.content.Intent
import androidx.constraintlayout.widget.ConstraintLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.korxfixer.benitasiapp.Generic.CommentFragment
import com.korxfixer.benitasiapp.Generic.UserProfileActivity
import com.korxfixer.benitasiapp.Home.HomeActivity
import com.korxfixer.benitasiapp.Models.UserPosts
import com.korxfixer.benitasiapp.Profile.ProfileActivity
import com.korxfixer.benitasiapp.R
import com.korxfixer.benitasiapp.VideoRecyclerView.view.Video
import com.korxfixer.benitasiapp.VideoRecyclerView.view.VideoView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.hoanganhtuan95ptit.autoplayvideorecyclerview.VideoHolder
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_tek_gonderi.view.*
import kotlinx.android.synthetic.main.tek_post_recycler_item.view.*
import kotlinx.android.synthetic.main.tek_post_recycler_item.view.frameLayout
import kotlinx.android.synthetic.main.tek_post_recycler_item.view.imgBegen
import kotlinx.android.synthetic.main.tek_post_recycler_item.view.imgPostResim
import kotlinx.android.synthetic.main.tek_post_recycler_item.view.imgUserProfile
import kotlinx.android.synthetic.main.tek_post_recycler_item.view.imgYorum
import kotlinx.android.synthetic.main.tek_post_recycler_item.view.insta_like_view
import kotlinx.android.synthetic.main.tek_post_recycler_item.view.tvBegenmeSayisi
import kotlinx.android.synthetic.main.tek_post_recycler_item.view.tvKacZamanOnce
import kotlinx.android.synthetic.main.tek_post_recycler_item.view.tvKullaniciAdiBaslik
import kotlinx.android.synthetic.main.tek_post_recycler_item.view.tvKullaniciAdiveAciklama
import kotlinx.android.synthetic.main.tek_post_recycler_item.view.tvYorumlariGoster
import kotlinx.android.synthetic.main.tek_post_recycler_item.view.videoView
import org.greenrobot.eventbus.EventBus
import java.util.*


class HomeFragmentRecyclerAdapter(var context: Context, var tumGonderiler: ArrayList<UserPosts>) : androidx.recyclerview.widget.RecyclerView.Adapter<HomeFragmentRecyclerAdapter.MyViewHolder>() {


    override fun getItemCount(): Int {
        return tumGonderiler.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var viewHolder = LayoutInflater.from(context).inflate(R.layout.tek_post_recycler_item, parent, false)

        return MyViewHolder(viewHolder, context)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        var videoMu=false
        var dosyaYolu=tumGonderiler.get(position).postURL
        //https://asdasdasdasdasdasd.mp4
        var dosyaTuru=dosyaYolu!!.substring(dosyaYolu.lastIndexOf("."), dosyaYolu.lastIndexOf(".")+4)

        if(dosyaTuru.equals(".mp4")) {
            holder.videoCameraAnim.start()
            holder.videoKaranlikImage.visibility=View.VISIBLE
            videoMu=true
        }

        holder.setData(position, tumGonderiler.get(position),videoMu)
    }


    class MyViewHolder(itemView: View?, myHomeActivity: Context) : VideoHolder(itemView) {

        var tumLayout = itemView as ConstraintLayout
        var profileImage = tumLayout.imgUserProfile
        var userNameTitle = tumLayout.tvKullaniciAdiBaslik
        var gonderi = tumLayout.imgPostResim
        var framelayout=tumLayout.frameLayout
        var userNameveAciklama = tumLayout.tvKullaniciAdiveAciklama

        var koltukSayisi = tumLayout.tvKoltukSayisi
        var markaModel = tumLayout.tvMarkaModel
        var saatler  = tumLayout.tvSaatler
        var gunler = tumLayout.tvGunler

        var gonderiKacZamanOnce = tumLayout.tvKacZamanOnce
        var yorumYap = tumLayout.imgYorum
        var gonderiBegen = tumLayout.imgBegen
        var myHomeActivity = myHomeActivity
        var mInstaLikeView = tumLayout.insta_like_view
        var begenmeSayisi = tumLayout.tvBegenmeSayisi
        var yorumlariGoster = tumLayout.tvYorumlariGoster
        var myVideo = tumLayout.videoView
        var videoCameraAnim = tumLayout.cameraAnimation
        var videoKaranlikImage=tumLayout.videoKaranlik
        var progressbar=tumLayout.progressBar10


        var olusturulanElemanVideoMu=false

        override fun getVideoLayout(): View? {
            return myVideo
        }

        override fun playVideo() {
            if(olusturulanElemanVideoMu){

                myVideo.play(object : VideoView.OnPreparedListener{
                    override fun onPrepared() {
                        videoKaranlikImage.visibility=View.GONE
                        videoCameraAnim.stop()
                        progressbar.visibility=View.GONE
                    }

                })
            }
        }

        override fun stopVideo() {
            if(olusturulanElemanVideoMu){
                videoCameraAnim.stop()
                myVideo.stop()
            }
        }




        fun setData(position: Int, oankiGonderi: UserPosts, videoMu: Boolean) {

            olusturulanElemanVideoMu=videoMu
            if(olusturulanElemanVideoMu){
                myVideo.visibility=View.VISIBLE
                gonderi.visibility=View.GONE
                myVideo.setVideo(Video(oankiGonderi.postURL,0))
            }else {
                myVideo.visibility=View.GONE
                gonderi.visibility=View.VISIBLE
                UniversalImageLoader.setImage(oankiGonderi.postURL!!, gonderi, progressbar, "")
            }




            userNameTitle.setText(oankiGonderi.userName)

            userNameTitle.setOnClickListener {

                var tiklanilanUserID=oankiGonderi.userID

                if(!tiklanilanUserID!!.equals(FirebaseAuth.getInstance().currentUser!!.uid)){
                    var intent=Intent(myHomeActivity,UserProfileActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    intent.putExtra("secilenUserID", oankiGonderi.userID)
                    myHomeActivity.startActivity(intent)
                }else {

                    var intent=Intent(myHomeActivity, ProfileActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    myHomeActivity.startActivity(intent)
                }


            }


            userNameveAciklama.setText(oankiGonderi.userName.toString()+" "+oankiGonderi.postAciklama.toString())
/**           userNameveAciklama.setText(" "+oankiGonderi.postAciklama.toString())

            koltukSayisi.setText(" "+oankiGonderi.koltuk_sayisi.toString())
            markaModel.setText(" "+oankiGonderi.marka_model.toString())
            saatler.setText(" "+oankiGonderi.saatler.toString())
            gunler.setText(" "+oankiGonderi.gunler.toString())
*/
            UniversalImageLoader.setImage(oankiGonderi.userPhotoURL!!, profileImage, null, "")
            gonderiKacZamanOnce.setText(TimeAgo.getTimeAgo(oankiGonderi.postYuklenmeTarih!!))

            begeniKontrol(oankiGonderi)
            yorumlariGoruntule(position, oankiGonderi)


            yorumYap.setOnClickListener {

               if(myVideo.visibility == View.VISIBLE){
                   myVideo.stop()
               }
               yorumlarFragmentiniBaslat(oankiGonderi)
            }

            yorumlariGoster.setOnClickListener {
                if(myVideo.visibility == View.VISIBLE){
                    myVideo.stop()
                }
                yorumlarFragmentiniBaslat(oankiGonderi)
            }

            gonderiBegen.setOnClickListener {

                var mRef = FirebaseDatabase.getInstance().reference
                var userID = FirebaseAuth.getInstance().currentUser!!.uid
                mRef.child("likes").child(oankiGonderi.postID!!).addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {

                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        if (p0!!.hasChild(userID)) {

                            mRef.child("likes").child(oankiGonderi.postID!!).child(userID).removeValue()
                            Bildirimler.bildirimKaydet(oankiGonderi.userID!!,Bildirimler.GONDERI_BEGENISI_GERI_CEK,oankiGonderi!!.postID!!)
                            //Log.e("VVV","HA BEGENME gerı cekme BILDIRIM:"+oankiGonderi!!.postID)
                            gonderiBegen.setImageResource(R.drawable.ic_like)

                        } else {

                            mRef.child("likes").child(oankiGonderi.postID!!).child(userID).setValue(userID)


                            if(!oankiGonderi.userID!!.equals(userID))
                            Bildirimler.bildirimKaydet(oankiGonderi.userID!!,Bildirimler.GONDERI_BEGENILDI,oankiGonderi!!.postID!!)

                            //Log.e("VVV","HA BEGENME BILDIRIM:"+oankiGonderi!!.postID)
                            gonderiBegen.setImageResource(R.drawable.ic_begen_kirmizi)
                            mInstaLikeView.start()
                            begenmeSayisi.visibility=View.VISIBLE
                            begenmeSayisi.setText(""+p0!!.childrenCount!!.toString()+" beğenme")
                        }
                    }


                })


            }

            var ilkTiklama: Long = 0
            var sonTiklama: Long = 0

            gonderi.setOnClickListener {

                ilkTiklama = sonTiklama
                sonTiklama = System.currentTimeMillis()

                if (sonTiklama - ilkTiklama < 300) {
                    mInstaLikeView.start()

                    FirebaseDatabase.getInstance().getReference().child("likes").child(oankiGonderi.postID!!)
                            .child(FirebaseAuth.getInstance().currentUser!!.uid).setValue(FirebaseAuth.getInstance().currentUser!!.uid)


                    sonTiklama = 0
                }


            }

            myVideo.setOnClickListener {

                ilkTiklama = sonTiklama
                sonTiklama = System.currentTimeMillis()

                if (sonTiklama - ilkTiklama < 300) {
                    mInstaLikeView.start()

                    FirebaseDatabase.getInstance().getReference().child("likes").child(oankiGonderi.postID!!)
                            .child(FirebaseAuth.getInstance().currentUser!!.uid).setValue(FirebaseAuth.getInstance().currentUser!!.uid)


                    sonTiklama = 0
                }


            }



        }

        fun yorumlarFragmentiniBaslat(oankiGonderi: UserPosts) {
            EventBus.getDefault().postSticky(EventbusDataEvents.YorumYapilacakGonderininIDsiniGonder(oankiGonderi!!.postID))

            (myHomeActivity as HomeActivity).homeViewPager.visibility = View.INVISIBLE
            (myHomeActivity as HomeActivity).homeFragmentContainer.visibility = View.VISIBLE


            var transaction = (myHomeActivity as HomeActivity).supportFragmentManager.beginTransaction()
            transaction.replace(R.id.homeFragmentContainer, CommentFragment())
            transaction.addToBackStack("commentFragmentEklendi")
            transaction.commit()
        }

        fun yorumlariGoruntule(position: Int, oankiGonderi: UserPosts){

            var mRef=FirebaseDatabase.getInstance().reference
            mRef.child("comments").child(oankiGonderi!!.postID!!).addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(p0: DataSnapshot) {
                    var yorumSayisi=0

                    for(ds in p0!!.children){
                        if(!ds!!.key.toString().equals(oankiGonderi!!.postID)){
                            yorumSayisi++
                        }
                    }


                    if(yorumSayisi >= 1){
                        yorumlariGoster.visibility=View.VISIBLE
                        yorumlariGoster.setText(yorumSayisi.toString()+" yorumun tümünü gör")
                    }else {
                        yorumlariGoster.visibility=View.GONE
                    }

                }


            })

        }

        fun begeniKontrol(oankiGonderi: UserPosts) {

            var mRef = FirebaseDatabase.getInstance().reference
            var userID = FirebaseAuth.getInstance().currentUser!!.uid
            mRef.child("likes").child(oankiGonderi.postID!!).addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(p0: DataSnapshot) {

                    if(p0!!.getValue()!=null){
                        begenmeSayisi.visibility=View.VISIBLE
                        begenmeSayisi.setText(""+p0!!.childrenCount!!.toString()+" beğenme")
                    }else {
                        begenmeSayisi.visibility=View.GONE
                    }

                    if (p0!!.hasChild(userID)) {
                        gonderiBegen.setImageResource(R.drawable.ic_begen_kirmizi)
                    } else {
                        gonderiBegen.setImageResource(R.drawable.ic_like)
                    }
                }


            })


        }


    }
}