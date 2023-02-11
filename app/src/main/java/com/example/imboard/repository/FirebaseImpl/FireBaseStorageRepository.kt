package com.example.imboard.repository.FirebaseImpl

import android.net.Uri
import com.example.imboard.model.User
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import il.co.syntax.myapplication.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import safeCall


class FireBaseStorageRepository {
    private val fireBaseStorage = FirebaseStorage.getInstance()
    suspend fun getUserPhoto(userId : String) : Resource<Uri>{
        return withContext(Dispatchers.IO){
            safeCall {
//                val filesRef: StorageReference = fireBaseStorage.getReferenceFromUrl("account_photo/$userId")
//                //val fileRef = filesRef.child(fullName)
                val photoURI = fireBaseStorage.getReference("account_photo/$userId").downloadUrl.await()
                Resource.Success(photoURI)
               // Resource.Success(photoURI)
            }
        }
    }

    suspend fun setUserPhoto(userId : String, imageUri: Uri) = withContext(Dispatchers.IO){
        safeCall {
            val upload = fireBaseStorage.getReference().child("account_photo/$userId").putFile(imageUri)
            Resource.Success(upload)
        }
    }
}