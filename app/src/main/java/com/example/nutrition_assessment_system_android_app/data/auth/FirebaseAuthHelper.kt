package com.example.nutrition_assessment_system_android_app.data.auth

import com.example.nutrition_assessment_system_android_app.domain.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseAuthHelper @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {
    // Thêm method kiểm tra auth status
    fun isUserAuthenticated(): Boolean {
        return firebaseAuth.currentUser != null
    }

    // Thêm method lấy current user ID token
    suspend fun getCurrentUserIdToken(): Resource<String?> {
        return try {
            val user = firebaseAuth.currentUser
            if (user == null) {
                Resource.Success(null)
            } else {
                val token = user.getIdToken(true).await().token
                Resource.Success(token)
            }
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Failed to get token")
        }
    }

    // Thêm Flow để observe auth state changes
    fun observeAuthState(): Flow<Boolean> = callbackFlow {
        // Emit giá trị hiện tại ngay lập tức
        trySend(firebaseAuth.currentUser != null)

        val authStateListener = FirebaseAuth.AuthStateListener { auth ->
            trySend(auth.currentUser != null)
        }
        firebaseAuth.addAuthStateListener(authStateListener)
        awaitClose {
            firebaseAuth.removeAuthStateListener(authStateListener)
        }
    }

    suspend fun getIdToken(
        email: String,
        password: String
    ): Resource<String> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val idToken = result.user?.getIdToken(true)?.await()?.token
                ?: return Resource.Error("Failed to retrieve ID token")

            Resource.Success(idToken)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "An unexpected error occurred")
        }
    }

    suspend fun getGoogleIdToken(googleToken: String): Resource<String> {
        return try {
            val credential = GoogleAuthProvider.getCredential(googleToken, null)
            val result = firebaseAuth.signInWithCredential(credential).await()
            val firebaseUser = result.user ?: return Resource.Error("Google sign-in failed")
            val firebaseIdToken = firebaseUser.getIdToken(true).await().token
                ?: return Resource.Error("Failed to retrieve Firebase ID token")

            Resource.Success(firebaseIdToken)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "An unexpected error occurred")
        }
    }

    fun signOut() {
        firebaseAuth.signOut()
    }
}