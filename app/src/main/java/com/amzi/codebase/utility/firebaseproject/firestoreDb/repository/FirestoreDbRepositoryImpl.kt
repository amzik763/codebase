package com.amzi.codebase.utility.firebaseproject.firestoreDb.repository

import com.amzi.codebase.utility.ResultState
import com.amzi.codebase.utility.firebaseproject.firestoreDb.FirestoreModelResponse
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class FirestoreDbRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore
) : FirestoreRepository {

    override fun insert(item: FirestoreModelResponse.FirestoreItem): Flow<ResultState<String>> = callbackFlow{
        trySend(ResultState.Loading)
        db.collection("user").add(
            item
        ).addOnSuccessListener {
            trySend(ResultState.Success("Data Inserted Successfully with ${it.id}"))
        }.addOnFailureListener {
            trySend(ResultState.Failure(it))
        }

        awaitClose {
            close()
        }
    }

    override fun getItems(): Flow<ResultState<List<FirestoreModelResponse>>> = callbackFlow{

        trySend(ResultState.Loading)
        db.collection("user").get(

        ).addOnSuccessListener {
            val items = it.map{ data ->
                FirestoreModelResponse(
                    item = FirestoreModelResponse.FirestoreItem(
                        title = data.getString("title"),
                        description = data.getString("description")
                    ),
                    key = data.id
                )
            }
            trySend(ResultState.Success(items))
        }.addOnFailureListener{
            trySend(ResultState.Failure(it))
        }

        awaitClose{
            close()
        }

    }

    override fun delete(key: String): Flow<ResultState<String>> = callbackFlow{

        trySend(ResultState.Loading)
        db.collection("user").document(key).delete().addOnSuccessListener {
            trySend(ResultState.Success("Data Deleted Successfully"))
        }.addOnFailureListener{
            trySend(ResultState.Failure(it))
        }

        awaitClose {
            close()
        }

     }

    override fun update(res: FirestoreModelResponse): Flow<ResultState<String>> = callbackFlow{
        trySend(ResultState.Loading)
        val map = HashMap<String,Any>()
        map["title"] = res.item.title.toString()
        map["description"] = res.item.description.toString()

        db.collection("user").document(res.key.toString()).update(map).addOnSuccessListener {
            trySend(ResultState.Success("Data Updated Successfully"))
        }.addOnFailureListener{
            trySend(ResultState.Failure(it))
        }

        awaitClose {
            close()
        }
    }

}