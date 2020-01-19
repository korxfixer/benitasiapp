const functions = require('firebase-functions');

const admin = require('firebase-admin');
admin.initializeApp();

exports.takipistegiBildiriGonder=functions.database.ref("/takip_istekleri/{takip_edilmek_istenen_user_id}/{takip_etmek_isteyen_user_id}").onCreate((data, context)=>{
	
	const takipEdilmekIstenenUserID = context.params.takip_edilmek_istenen_user_id;
	const takipEtmekIsteyenUserID = context.params.takip_etmek_isteyen_user_id;
	
	console.log("takip edilmek isteyenin idsi : ",takipEdilmekIstenenUserID);
	console.log("takip etmek isteyenin ki : ",takipEtmekIsteyenUserID);
	
	const token=admin.database().ref(`/users/${takipEdilmekIstenenUserID}/fcm_token`).once('value');
	const user_name=admin.database().ref(`/users/${takipEtmekIsteyenUserID}/user_name`).once('value');
	
	return token.then(result=>{
		const takipEdilmekIstenenUserFCMToken = result.val();	
		
		console.log("takip Edilmek istenen userin fcm tokenı : ",takipEdilmekIstenenUserFCMToken);
		
		return user_name.then(result =>{
			const takipEtmekIsteyenUserName = result.val();
			
			console.log("takip etmek isteyen userin namesi : ",takipEtmekIsteyenUserName);
			
			const yeniTakipIstegiBildirimi = {
				
				notification : {

				title : 'Takip İsteği',
				body :`${takipEtmekIsteyenUserName} seni takip etmek istiyor`,
				icon :'default'
				}
			};
			return admin.messaging().sendToDevice(takipEdilmekIstenenUserFCMToken,yeniTakipIstegiBildirimi).then(result =>{
				console.log("yeni takip isteği bildirimi gönderildi");
			});
			
		});
	});
});


exports.yeniMesajBildirimiGonder=functions.database.ref("/mesajlar/{mesaj_gonderilen_user_id}/{mesaj_gonderen_user_id}/{yeni_mesaj_id}").onCreate((data,context)=>{
	
	const mesajGonderilenUserID=context.params.mesaj_gonderilen_user_id;
	const mesajGonderenUserID  =context.params.mesaj_gonderen_user_id;
	const yeniMesajID          =context.params.yeni_mesaj_id;
	
	console.log("kime mesaj gonderilmiş idsi: ",mesajGonderilenUserID);
	console.log("kim mesaj gondermiş idsi: ",mesajGonderenUserID);
	console.log("gönderilen mesj idsi: ",yeniMesajID);
	
	const mesajGonderilenUserToken = admin.database().ref(`/user/${mesajGonderilenUserID}/fcm_token`).once('value');	
	const mesajGonderenUserName = admin.database().ref(`/users/${mesajGonderenUserID}/user_name`).once('value');
	const gonderilenSonMesaj    = admin.database().ref(`/mesajlar/${mesajGonderilenUserID}/${mesajGonderenUserID}/${yeniMesajID}`).once('value');
	
	return mesajGonderilenUserToken.then(result=>{
		
		const user_token = result.val();
		return mesajGonderenUserName.then(result =>{
			
			const user_name=result.val();
			return gonderilenSonMesaj.then(result=>{
				const son_yazilan_mesaj = result.child('mesaj').val();
				const son_mesaji_yazan_userin_idsi = result.child('user_id').val();
				
				if(son_mesaji_yazan_userin_idsi ==mesajGonderenUserID){
					
					const yeniMesajBildirimi = {
					notification : {
						click_action : '.HomeActivitty',
						title : 'Yeni Mesaj',
						body : `${user_name} : ${son_yazilan_mesaj}`,
						icon : 'default'
					
					},
					data  : {
						
						secilenUserID : `${mesajGonderenUserID}`
					}
				};
				return admin.messaging().sendToDevice(user_token, yeniMesajBildirimi).then(result =>{
					
				});
				}
				
			});
		});
	});
});














