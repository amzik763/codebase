package com.amzi.codebase.utility.firebaseproject.firestoreDb

data class FirestoreModelResponse(

    val item: FirestoreItem,
    val key:String?=""

){

    data class FirestoreItem(
        val title:String?="",
        val description:String?=""
    )



}
