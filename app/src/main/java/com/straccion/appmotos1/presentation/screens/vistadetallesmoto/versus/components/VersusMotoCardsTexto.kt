package com.straccion.appmotos1.presentation.screens.vistadetallesmoto.versus.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.straccion.appmotos1.R


@Composable
fun VersusMotoCardsTexto(
    titulo: String,
    iconoTitulo: Painter,
    texto1: String,
    texto2: String,
    chulo1: Boolean,
    chulo2: Boolean
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(15.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardColors(
            contentColor = MaterialTheme.colorScheme.surface,
            containerColor = MaterialTheme.colorScheme.surface,
            disabledContentColor = MaterialTheme.colorScheme.onSurface,
            disabledContainerColor = MaterialTheme.colorScheme.onSurface
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = titulo,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Image(
                    modifier = Modifier
                        .size(40.dp),
                    painter = iconoTitulo,
                    contentDescription = ""
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    if (chulo1) {
                        Image(
                            modifier = Modifier
                                .size(15.dp),
                            painter = painterResource(R.drawable.ic_chulo),
                            contentDescription = ""
                        )
                    } else {
                        Image(
                            modifier = Modifier
                                .size(15.dp),
                            painter = painterResource(R.drawable.ic_x),
                            contentDescription = ""
                        )
                    }
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = texto1,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Spacer(modifier = Modifier.height(5.dp))
            }
            Spacer(modifier = Modifier.height(25.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    if (chulo2) {
                        Image(
                            modifier = Modifier
                                .size(15.dp),
                            painter = painterResource(R.drawable.ic_chulo),
                            contentDescription = ""
                        )
                    } else {
                        Image(
                            modifier = Modifier
                                .size(15.dp),
                            painter = painterResource(R.drawable.ic_x),
                            contentDescription = ""
                        )
                    }
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = texto2,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

            }
        }
    }
}