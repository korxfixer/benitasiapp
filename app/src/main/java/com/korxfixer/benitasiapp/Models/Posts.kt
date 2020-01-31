package com.korxfixer.benitasiapp.Models


class Posts {

    var user_id:String? = null
    var post_id:String? = null
    var yuklenme_tarih:Long? = null
    var aciklama:String? = null

    var koltuk_sayisi:String? = null
    var marka_model:String? = null
    var saatler:String? = null
    var gunler:String? = null

    var file_url:String? = null

    constructor(){}
    constructor(user_id: String?, post_id: String?, yuklenme_tarih: Long?, aciklama: String?, koltuk_sayisi:String?, marka_model:String?, saatler:String?, gunler:String?, photo_url: String?) {
        this.user_id = user_id
        this.post_id = post_id
        this.yuklenme_tarih = yuklenme_tarih
        this.aciklama = aciklama

        this.koltuk_sayisi = koltuk_sayisi
        this.marka_model = marka_model
        this.saatler = saatler
        this.gunler = gunler

        this.file_url = photo_url
    }

    override fun toString(): String {
        return "Posts(user_id=$user_id, post_id=$post_id, yuklenme_tarih=$yuklenme_tarih, aciklama=$aciklama,koltuk_sayisi=$koltuk_sayisi,marka_model=$marka_model,saatler=$saatler,gunler=$gunler file_url=$file_url)"
    }


}