package com.example.locationsharingapp_dipti_16.Model16

import com.google.firebase.firestore.PropertyName

data class User16(
    val userId:String,
    @get:PropertyName("DisplayName")
    @set:PropertyName("DisplayName")
    var displayname:String="",


    @get:PropertyName("email")
    @set:PropertyName("email")
    var email:String ="",

    @get:PropertyName("location")
    @set:PropertyName("location")
    var location:String =""

){
    constructor():this("","","")
}

