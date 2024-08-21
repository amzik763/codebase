package com.amzi.codebase.utility.firebaseRealtimeDb

data class RealtimeModelResponse(

    val item:RealtimeItems?,
    val key:String? = ""

    ) {

    data class RealtimeItems(

        val title:String? = "",
        val description:String? = ""

    )


}