package com.straccion.appmotos1.di

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.straccion.appmotos1.core.Constants.MOTOS
import com.straccion.appmotos1.data.repository.AuthRepositoryImpl
import com.straccion.appmotos1.data.repository.MotosRepositoryImpl
import com.straccion.appmotos1.domain.repository.AuthRepository
import com.straccion.appmotos1.domain.repository.MotosRepository
import com.straccion.appmotos1.domain.use_cases.auth.AuthUsesCases
import com.straccion.appmotos1.domain.use_cases.auth.GetCurrentUser
import com.straccion.appmotos1.domain.use_cases.auth.Login
import com.straccion.appmotos1.domain.use_cases.obtener_motos.ObtenerMotos
import com.straccion.appmotos1.domain.use_cases.obtener_motos.ObtenerMotosUsesCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    fun providerFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun providesFirebaseFirestore(): FirebaseFirestore = Firebase.firestore

    @Provides
    fun providerAuthRepository(impl: AuthRepositoryImpl): AuthRepository = impl

    @Provides
    fun providesAuthUsesCases(repository: AuthRepository) = AuthUsesCases(
        getCurrentUser = GetCurrentUser(repository),
        login = Login(repository)
    )

    @Provides
    @Named(MOTOS)
    fun providesUsersRef(db: FirebaseFirestore): CollectionReference = db.collection(MOTOS)

    @Provides
    fun providesObtenerMotossesCases(repository: MotosRepository) = ObtenerMotosUsesCase(
        obtenerMotos = ObtenerMotos(repository)
    )
    @Provides
    fun providerMotosRepository(impl: MotosRepositoryImpl): MotosRepository = impl
}