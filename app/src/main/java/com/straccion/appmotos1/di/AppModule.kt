package com.straccion.appmotos1.di

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.BlockThreshold
import com.google.ai.client.generativeai.type.HarmCategory
import com.google.ai.client.generativeai.type.SafetySetting
import com.google.ai.client.generativeai.type.generationConfig
import com.google.firebase.BuildConfig
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.straccion.appmotos1.core.Constants.APIKEY
import com.straccion.appmotos1.core.Constants.MOTOS
import com.straccion.appmotos1.core.Constants.MOTOSFAV
import com.straccion.appmotos1.data.remote.GeminiService
import com.straccion.appmotos1.data.repository.AuthRepositoryImpl
import com.straccion.appmotos1.data.repository.DataBasesRepositoryImpl
import com.straccion.appmotos1.data.repository.FavoritasRepositoryImpl
import com.straccion.appmotos1.data.repository.IARepositoryImpl
import com.straccion.appmotos1.data.repository.MotosRepositoryImpl
import com.straccion.appmotos1.domain.repository.AuthRepository
import com.straccion.appmotos1.domain.repository.DataBaseRepository
import com.straccion.appmotos1.domain.repository.FavoritasRepository
import com.straccion.appmotos1.domain.repository.IARepository
import com.straccion.appmotos1.domain.repository.MotosRepository
import com.straccion.appmotos1.domain.use_cases.auth.AuthUsesCases
import com.straccion.appmotos1.domain.use_cases.auth.GetCurrentUser
import com.straccion.appmotos1.domain.use_cases.auth.Login
import com.straccion.appmotos1.domain.use_cases.databases.ActualizacionesRealizadas
import com.straccion.appmotos1.domain.use_cases.databases.ActualizarMotos
import com.straccion.appmotos1.domain.use_cases.databases.DataBasesUsesCase
import com.straccion.appmotos1.domain.use_cases.databases.EliminarMoto
import com.straccion.appmotos1.domain.use_cases.databases.ObtenerAllMotos
import com.straccion.appmotos1.domain.use_cases.databases.OcultarMotocicleta
import com.straccion.appmotos1.domain.use_cases.databases.UpdateFichaTec
import com.straccion.appmotos1.domain.use_cases.favoritos.AgregarMotoFav
import com.straccion.appmotos1.domain.use_cases.favoritos.FavoritasUsesCase
import com.straccion.appmotos1.domain.use_cases.obtener_motos.ObtenerMotosById
import com.straccion.appmotos1.domain.use_cases.favoritos.ObtenerMotosFavoritas
import com.straccion.appmotos1.domain.use_cases.favoritos.QuitarMotosFav
import com.straccion.appmotos1.domain.use_cases.iamessage.AIMessage
import com.straccion.appmotos1.domain.use_cases.iamessage.IaMessageUseCase
import com.straccion.appmotos1.domain.use_cases.obtener_motos.ObtenerMotosUsesCase
import com.straccion.appmotos1.domain.use_cases.obtener_motos.ObtenerMotosVisibles
import com.straccion.appmotos1.domain.use_cases.obtener_motos.SumarVisitas
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
    @Singleton
    fun provideGenerativeModel(): GenerativeModel {
        return GenerativeModel(
            modelName = "gemini-1.5-flash-001",
            apiKey = APIKEY,
            generationConfig = generationConfig {
                temperature = 0.3f
                topK = 40
                topP = 0.9f
                maxOutputTokens = 2048
            },
            safetySettings = listOf(
                SafetySetting(HarmCategory.HARASSMENT, BlockThreshold.MEDIUM_AND_ABOVE),
                SafetySetting(HarmCategory.HATE_SPEECH, BlockThreshold.MEDIUM_AND_ABOVE),
                SafetySetting(HarmCategory.SEXUALLY_EXPLICIT, BlockThreshold.MEDIUM_AND_ABOVE),
                SafetySetting(HarmCategory.DANGEROUS_CONTENT, BlockThreshold.MEDIUM_AND_ABOVE),
            )
        )
    }

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
        obtenerMotosById = ObtenerMotosById(repository),
        sumarVisitas = SumarVisitas(repository)
    )

    @Provides
    fun providerMotosRepository(impl: MotosRepositoryImpl): MotosRepository = impl

    @Provides
    fun providesFavoritasUsesCases(repository: FavoritasRepository) = FavoritasUsesCase(
        obtenerMotosFavoritas = ObtenerMotosFavoritas(repository),
        quitarMotosFav = QuitarMotosFav(repository),
        agregarMotoFav = AgregarMotoFav(repository)
    )

    @Provides
    fun providerFavoritasRepository(impl: FavoritasRepositoryImpl): FavoritasRepository = impl


    @Provides
    fun providesDataBaseUsesCases(repository: DataBaseRepository) = DataBasesUsesCase(
        updateFichaTec = UpdateFichaTec(repository),
        ocultarMotocicleta = OcultarMotocicleta(repository),
        eliminarMoto = EliminarMoto(repository),
        obtenerAllMotos = ObtenerAllMotos(repository),
        actualizarMotos = ActualizarMotos(repository),
        actualizacionesRealizadas = ActualizacionesRealizadas(repository)
    )

    @Provides
    fun providerDataBaseRepository(impl: DataBasesRepositoryImpl): DataBaseRepository = impl


    @Provides
    @Singleton
    fun provideGeminiService(generativeModel: GenerativeModel): GeminiService {
        return GeminiService(generativeModel)
    }

    @Provides
    @Singleton
    fun provideIARepository(service: GeminiService): IARepository {
        return IARepositoryImpl(service)
    }

    @Provides
    @Singleton
    fun provideSendPromptUseCase(repository: IARepository) = IaMessageUseCase(
        aiMessage = AIMessage(repository)
    )

}