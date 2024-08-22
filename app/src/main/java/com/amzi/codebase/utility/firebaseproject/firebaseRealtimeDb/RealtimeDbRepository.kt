package com.amzi.codebase.utility.firebaseproject.firebaseRealtimeDb

import com.amzi.codebase.utility.ResultState
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class RealtimeDbRepository @Inject constructor(
    private val db: DatabaseReference
): RealtimeRepository {

    override fun insert(items: RealtimeModelResponse.RealtimeItems): Flow<ResultState<String>> = callbackFlow {
        trySend(ResultState.Loading)
        db.push().setValue(items).addOnCompleteListener {
            if (it.isSuccessful) {
                trySend(ResultState.Success("Data Inserted Successfully"))
            } else {
                trySend(ResultState.Failure(it.exception!!))
            }
        }.addOnFailureListener{
            trySend(ResultState.Failure(it))
        }

        awaitClose {
            close()
        }

    }

    override fun getItems(): Flow<ResultState<List<RealtimeModelResponse>>>  = callbackFlow{
        val valueEvent = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val items = snapshot.children.map { dataSnapshot ->

                    RealtimeModelResponse(dataSnapshot.getValue(RealtimeModelResponse.RealtimeItems::class.java),
                        key = dataSnapshot.key)

                }
                trySend(ResultState.Success(items))

            }

            override fun onCancelled(error: DatabaseError) {
                trySend(ResultState.Failure(error.toException()))
            }

        }


        db.addValueEventListener(valueEvent)


        awaitClose{
            db.removeEventListener(valueEvent)
            close()
        }
    }

    override fun delete(key: String): Flow<ResultState<String>>  = callbackFlow{
        trySend(ResultState.Loading)
        db.child(key).removeValue().addOnSuccessListener {
            trySend(ResultState.Success("Data Deleted Successfully"))
        }.addOnFailureListener{
            trySend(ResultState.Failure(it))
        }
        awaitClose {
            close()
        }
    }

    override fun update(res: RealtimeModelResponse): Flow<ResultState<String>>  = callbackFlow{

        trySend(ResultState.Loading)
        val map = HashMap<String,Any>()
        map["title"] = res.item?.title?:"not found"
        map["description"] = res.item?.description?:"not found"
        db.child(res.key!!).updateChildren(map).addOnSuccessListener {
            trySend(ResultState.Success("Data Updated Successfully"))
        }.addOnFailureListener{
            trySend(ResultState.Failure(it))
        }

        awaitClose {
            close()
        }
    }
}