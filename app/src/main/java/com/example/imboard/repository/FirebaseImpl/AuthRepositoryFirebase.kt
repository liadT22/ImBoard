package com.example.imboard.repository.FirebaseImpl

import android.net.Uri
import com.example.imboard.model.Lobby
import com.example.imboard.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import il.co.syntax.myapplication.util.Resource
import il.co.syntax.myapplication.util.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import safeCall
import java.util.*

class AuthRepositoryFirebase : AuthRepository {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val userRef = FirebaseFirestore.getInstance().collection("users")
    override suspend fun currentUser(): Resource<User> {
        return withContext(Dispatchers.IO){
            safeCall {
                val user = userRef.document(firebaseAuth.currentUser!!.uid).get().await().toObject(User::class.java)
                Resource.success(user!!)
            }
        }
    }

    override suspend fun login(email: String, password: String): Resource<User> {
        return withContext(Dispatchers.IO){
            safeCall {
                val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
                val user = userRef.document(result.user?.uid!!).get().await().toObject(User::class.java)!!
                Resource.success(user)
            }
        }
    }

    override suspend fun addLobby(userID:String, lobbies: ArrayList<Lobby>) = withContext(Dispatchers.IO) {
        safeCall {
            val result = userRef.document(userID).update("lobbies", lobbies).await()
            Resource.success(result)
        }
    }

    override suspend fun createUser(
        userName: String,
        userEmail: String,
        userLoginPassword: String,
        imageUri: Uri
    ): Resource<User> {
        return withContext(Dispatchers.IO){
            safeCall {
                val registrationResult =
                    firebaseAuth.createUserWithEmailAndPassword(userEmail, userLoginPassword)
                    .await()
                val userId = registrationResult.user?.uid!!
                val newUser = User(userName, userEmail, userId, imageUri.toString())
                val check = userRef.document(userId).set(newUser).await()
                Resource.success(newUser)
            }
        }
    }

    override fun logout() {
        firebaseAuth.signOut()
    }
}