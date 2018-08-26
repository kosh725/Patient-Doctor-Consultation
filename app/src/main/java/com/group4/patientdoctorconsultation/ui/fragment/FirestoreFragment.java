package com.group4.patientdoctorconsultation.ui.fragment;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.group4.patientdoctorconsultation.common.FirestoreResource;

public abstract class FirestoreFragment extends Fragment {

    protected boolean handleFirestoreResult(FirestoreResource resource){
        if(resource == null || (resource.getResource() == null && resource.getError() == null)){
            throw new IllegalStateException("Null result passed from Firestore Resource");
        }

        if(resource.isSuccessful()){
            return true;
        }else {
            Log.w("TAG", resource.getError());
            Toast.makeText(
                    requireContext(), resource.getError().getMessage(),
                    Toast.LENGTH_LONG)
                    .show();
            return false;
        }
    }

}
