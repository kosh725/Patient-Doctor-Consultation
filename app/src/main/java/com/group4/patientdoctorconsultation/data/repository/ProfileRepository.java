package com.group4.patientdoctorconsultation.data.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;
import com.group4.patientdoctorconsultation.common.LiveDocument;
import com.group4.patientdoctorconsultation.common.LiveQuery;
import com.group4.patientdoctorconsultation.common.LiveResultListener;
import com.group4.patientdoctorconsultation.data.model.Profile;

import java.util.Map;
import java.util.Objects;

public class ProfileRepository {

    private static ProfileRepository instance;
    private final CollectionReference profileCollection;
    private final FirebaseFirestore firestore;

    private ProfileRepository(FirebaseFirestore firestore) {
        this.firestore = firestore;
        this.profileCollection = firestore.collection(Profile.COLLECTION_NAME);
    }

    public LiveDocument<Profile> profileFromUserId(String userId){
        if(userId == null || userId.isEmpty()){
            userId = "1";
        }
        return new LiveDocument<>(
                profileCollection.document(userId),
                Profile.class
        );
    }

    public LiveResultListener<Boolean> updateProfile(Profile profile){
        LiveResultListener<Boolean> liveCompleteListener = new LiveResultListener<>();
        profileCollection.document(profile.getId())
                .set(profile)
                .addOnSuccessListener(runnable -> liveCompleteListener.onSuccess(true))
                .addOnFailureListener(liveCompleteListener);
        return liveCompleteListener;
    }

    public LiveQuery<Profile> getLinkedProfiles(String profileId){

        if(profileId == null || profileId.equals("")){
            profileId = "1";
        }

        return new LiveQuery<>(
                profileCollection.whereEqualTo(Profile.FIELD_LINKED_PROFILES + "." + profileId, true),
                Profile.class
        );
    }

    public LiveQuery<Profile> getAllProfiles(){
        return new LiveQuery<>(
                profileCollection,
                Profile.class
        );
    }

    public LiveResultListener<Boolean> addLinkedProfile(String currentProfileId, String linkedProfileId){
        final DocumentReference profileReference = profileCollection.document(currentProfileId);
        LiveResultListener<Boolean> liveCompleteListener = new LiveResultListener<>();

        firestore.runTransaction((Transaction.Function<Void>) transaction -> {
            Profile profile = transaction.get(profileReference).toObject(Profile.class);
            Map<String, Boolean> linkedProfiles = Objects.requireNonNull(profile).getLinkedProfiles();
            if (linkedProfiles == null) return null;
            linkedProfiles.put(linkedProfileId, true);
            profile.setLinkedProfiles(linkedProfiles);
            transaction.set(profileReference, profile);
            return null;
        })
        .addOnSuccessListener(runnable -> {liveCompleteListener.onSuccess(true);})
        .addOnFailureListener(liveCompleteListener);

        return liveCompleteListener;
    }

    public static synchronized ProfileRepository getInstance(FirebaseFirestore firestore){
        if(instance == null){
            instance = new ProfileRepository(firestore);
        }

        return instance;
    }


}
