package com.straccion.appmotos1.di

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.straccion.appmotos1.core.Constants.MOTOS
import com.straccion.appmotos1.core.Constants.MOTOSFAV
import com.straccion.appmotos1.data.repository.AuthRepositoryImpl
import com.straccion.appmotos1.data.repository.FavoritasRepositoryImpl
import com.straccion.appmotos1.data.repository.MotosRepositoryImpl
import com.straccion.appmotos1.domain.repository.AuthRepository
import com.straccion.appmotos1.domain.repository.FavoritasRepository
import com.straccion.appmotos1.domain.repository.MotosRepository
import com.straccion.appmotos1.domain.use_cases.auth.AuthUsesCases
import com.straccion.appmotos1.domain.use_cases.auth.GetCurrentUser
import com.straccion.appmotos1.domain.use_cases.auth.Login
import com.straccion.appmotos1.domain.use_cases.favoritos.FavoritasUsesCase
import com.straccion.appmotos1.domain.use_cases.obtener_motos.ObtenerMotosById
import com.straccion.appmotos1.domain.use_cases.favoritos.ObtenerMotosFavoritas
import com.straccion.appmotos1.domain.use_cases.favoritos.QuitarMotosFav
import com.straccion.appmotos1.domain.use_cases.obtener_motos.ObtenerMotosUsesCase
import com.straccion.appmotos1.domain.use_cases.obtener_motos.ObtenerMotosVisibles
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

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
    @Named(MOTOSFAV)
    fun providesUsersMotosFav(db: FirebaseFirestore): CollectionReference = db.collection(MOTOSFAV)

    @Provides
    fun providesObtenerMotossesCases(repository: MotosRepository) = ObtenerMotosUsesCase(
        obtenerMotosVisibles = ObtenerMotosVisibles(repository),
        obtenerMotosById = ObtenerMotosById(repository)
    )

    @Provides
    fun providerMotosRepository(impl: MotosRepositoryImpl): MotosRepository = impl

    @Provides
    fun providesFavoritasUsesCases(repository: FavoritasRepository) = FavoritasUsesCase(
        obtenerMotosFavoritas = ObtenerMotosFavoritas(repository),
        quitarMotosFav = QuitarMotosFav(repository)
    )
    @Provides
    fun providerFavoritasRepository(impl: FavoritasRepositoryImpl): FavoritasRepository = impl
}