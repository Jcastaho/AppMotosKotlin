package com.straccion.appmotos1.presentation.screens.vistabasededatos.agregar.database_agregar_manual

import androidx.lifecycle.ViewModel
import com.straccion.appmotos1.domain.use_cases.databases.DataBasesUsesCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AgregarRegistroManualViewModel @Inject constructor(
    private val dataBasesUsesCase: DataBasesUsesCase
) : ViewModel() {

}