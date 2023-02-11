package com.example.imboard.repository.FirebaseImpl

import android.net.Uri
import com.example.imboard.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import il.co.syntax.myapplication.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import safeCall

class AuthRepositoryFirebase : AuthRepository {

    private val storage = FirebaseStorage.getInstance()
    private val storageReference = storage.reference

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val userRef = FirebaseFirestore.getInstance().collection("users")
    override suspend fun currentUser(): Resource<User> {
        return withContext(Dispatchers.IO) {
            safeCall {
                val user = userRef.document(firebaseAuth.currentUser!!.uid).get().await()
                    .toObject(User::class.java)
                Resource.Success(user!!)
            }
        }
    }

    override suspend fun login(email: String, password: String): Resource<User> {
        return withContext(Dispatchers.IO) {
            safeCall {
                val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
                val user =
                    userRef.document(result.user?.uid!!).get().await().toObject(User::class.java)!!
                Resource.Success(user)
            }
        }
    }

    override suspend fun createUser(
        userName: String,
        userEmail: String,
        userLoginPassword: String,
        imageUri: Uri?
    ): Resource<User> {
        return withContext(Dispatchers.IO) {
            safeCall {
                val registrationResult =
                    firebaseAuth.createUserWithEmailAndPassword(userEmail, userLoginPassword)
                        .await()
                val userId = registrationResult.user?.uid!!
                uploadPhotoToFireBase(imageUri,userId)
                val newUser = User(userName, userEmail, imageUri)
                val check = userRef.document(userId).set(newUser).await()
                Resource.Success(newUser)
            }
        }
    }

    override fun logout() {
        firebaseAuth.signOut()
    }

    private fun uploadPhotoToFireBase(imageUri: Uri?, userId: String?) {
        val fileReference = FirebaseStorage.getInstance().reference.child("images/$userId")
        val uploadTask = imageUri?.let { fileReference.putFile(it) }
//        if (uploadTask != null) {
//            uploadTask.addOnFailureListener {
//                Toast.makeText(context, "Upload failed!", Toast.LENGTH_SHORT).show()
//            }.addOnSuccessListener {
//                Toast.makeText(context, "Upload succeeded!", Toast.LENGTH_SHORT).show()
//            }
        }
    }

