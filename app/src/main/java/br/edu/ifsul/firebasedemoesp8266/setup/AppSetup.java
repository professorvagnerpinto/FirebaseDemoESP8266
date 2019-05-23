package br.edu.ifsul.firebasedemoesp8266.setup;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AppSetup {

    private static final String TAG = "appSetup";
    private static DatabaseReference myRef = null;
    private static FirebaseAuth mAuth = null;
    public static FirebaseUser user = null;

    public static DatabaseReference getDBInstance(){
        //se ainda não tem a referência para o realtimedatabase, a pega
        if(myRef == null) {
            // obtém o objeto de acesso ao seviço de database do Firebase
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            // obtém o objeto de referência para a coleção de objetos
            myRef = database.getReference("arcondicionado");
            Log.d(TAG, "Conectou ao banco de dados? " + myRef);
        }
        //se já tem a referência, só a retorna
        return myRef;
    }

    public static FirebaseAuth getAuthInstace(){
        //se ainda não tem a referência para o autenticador
        if(mAuth == null) {
            // obtém o objeto de referência para a coleção de objetos
            mAuth = mAuth = FirebaseAuth.getInstance();
            Log.d(TAG, "Obteve o objeto de acesso ao serviço Auth. " + mAuth);
        }
        //se já tem a referência, só a retorna
        return mAuth;
    }

}
