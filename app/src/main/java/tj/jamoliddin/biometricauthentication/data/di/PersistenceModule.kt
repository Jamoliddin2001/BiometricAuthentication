package tj.jamoliddin.biometricauthentication.data.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import tj.jamoliddin.biometricauthentication.domain.util.PERSISTENCE_NAME
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences {
        return context.getSharedPreferences(PERSISTENCE_NAME, MODE_PRIVATE)
    }

}