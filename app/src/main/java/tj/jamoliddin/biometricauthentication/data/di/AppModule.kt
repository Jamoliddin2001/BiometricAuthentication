package tj.jamoliddin.biometricauthentication.data.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tj.jamoliddin.biometricauthentication.domain.util.USERS
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirestoreInstance() = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideFirestoreAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideUserCollection(
        firebaseFirestore: FirebaseFirestore
    ) = firebaseFirestore.collection(USERS)

}