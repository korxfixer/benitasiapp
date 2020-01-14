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

















