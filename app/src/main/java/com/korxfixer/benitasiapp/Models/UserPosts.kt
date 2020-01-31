package com.korxfixer.benitasiapp.Models


class UserPosts {

    var userID:String?=null
    var userName:String?=null
    var userPhotoURL:String?=null
    var postID:String?=null
    var postAciklama:String?=null

    var koltuk_sayisi:String? = null
    var marka_model:String? = null
    var saatler:String? = null
    var gunler:String? = null

    var postURL:String?=null
    var postYuklenmeTarih:Long?=null

    constructor(userID: String?, userName: String?, userPhotoURL: String?, postID: String?, postAciklama: String?, koltuk_sayisi:String?, marka_model:String?, saatler:String?, gunler:String?, postURL: String?, postYuklenmeTarih: Long?) {
        this.userID = userID
        this.userName = userName
        this.userPhotoURL = userPhotoURL
        this.postID = postID
        this.postAciklama = postAciklama

        this.koltuk_sayisi = koltuk_sayisi
        this.marka_model = marka_model
        this.saatler = saatler
        this.gunler = gunler

        this.postURL = postURL
        this.postYuklenmeTarih = postYuklenmeTarih
    }

    constructor(){}

    override fun toString(): String {
        return "UserPosts(userID=$userID, userName=$userName, userPhotoURL=$userPhotoURL, postID=$postID, postAciklama=$postAciklama,koltuk_sayisi=$koltuk_sayisi, marka_model=$marka_model,saatler=$saatler,gunler=$gunler, postURL=$postURL, postYuklenmeTarih=$postYuklenmeTarih)"
    }


}