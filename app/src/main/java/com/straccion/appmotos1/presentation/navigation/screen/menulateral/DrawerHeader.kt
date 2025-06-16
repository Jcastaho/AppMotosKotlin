package com.straccion.appmotos1.presentation.navigation.screen.menulateral

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.straccion.appmotos1.R
import com.straccion.appmotos1.presentation.navigation.screen.inicio.DrawerScreen

@Composable
fun DrawerHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 64.dp)
    ) {
        Image(
            painterResource(
                id = R.mipmap.ic_launcher_foreground
            ),
            modifier = Modifier
                .size(148.dp)
                .offset(x = (-15).dp, y = (-5).dp)
                .align(Alignment.Center)
                .clip(CircleShape),
            contentDescription = ""
        )
    }
}

@Composable
fun DrawerBody(
    items: List<DrawerScreen>,
    onItemClick: (DrawerScreen) -> Unit
) {
    LazyColumn {
        items(items) { item ->
            NavigationDrawerItem(
                icon = { Icon(item.icon, contentDescription = null) },
                label = { Text(item.title) },
                selected = false,
                onClick = { onItemClick(item) },
                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
            )
        }
    }
}