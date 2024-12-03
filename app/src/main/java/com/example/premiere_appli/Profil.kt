package com.example.premiere_appli


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass


@Composable
fun ProfilScreen(
    classes: WindowSizeClass,
    innerPadding: PaddingValues,
    navController: NavHostController
) {
    val classeLargeur = classes.windowWidthSizeClass


    when (classeLargeur) {
        WindowWidthSizeClass.COMPACT -> {
            // Mode portrait ou petit écran : les éléments sont empilés verticalement
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(0.dp, 100.dp, 0.dp, 0.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                MonImage(R.drawable.pp)
                Texte("Clément Lorieau")
                TexteDesc("Étudiant en 3e année de BUT MMI")
                ContactInfo(
                    email = "clement.lorieau@gmail.com",
                    linkedin = "www.linkedin.com/in/clement-lorieau"
                )
                Spacer(modifier = Modifier.height(30.dp))
                StartButton(navController)
            }
        }

        WindowWidthSizeClass.MEDIUM, WindowWidthSizeClass.EXPANDED -> {
            // Mode paysage ou grand écran : les éléments sont disposés en ligne



            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(0.dp, 0.dp, 0.dp, 0.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(end = 20.dp)
                ) {
                    MonImage(R.drawable.pp)
                    Texte("Clément Lorieau")
                }

                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.padding(start = 50.dp)
                ) {
                    TexteDesc("Étudiant en 3e année de BUT MMI")
                    TexteDesc("Chef de projet web chez D2COM")
                    ContactInfo(
                        email = "clement.lorieau@gmail.com",
                        linkedin = "www.linkedin.com/in/clement-lorieau"
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    StartButton(navController)
                }

            }
        }
    }
}

@Composable
fun MonImage(id: Int) {
    Image(
        painter = painterResource(id),
        contentDescription = "Photo de profil",
        modifier = Modifier
            .size(170.dp)
            .clip(CircleShape)
    )
}

@Composable
fun Texte(contenu: String) {
    Text(
        text = contenu,
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(10.dp)
    )
}

@Composable
fun TexteDesc(contenu: String) {
    Text(
        text = contenu,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(0.dp,10.dp,0.dp,0.dp),
        color = Color.Gray
    )
}

@Composable
fun ContactInfo(email: String, linkedin: String) {
    Column(horizontalAlignment = Alignment.Start) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(6.dp, 30.dp, 0.dp, 5.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.email),
                contentDescription = "Email Icon",
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = email, fontSize = 14.sp)
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(5.dp, 5.dp, 5.dp, 50.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.linkedin),
                contentDescription = "LinkedIn Icon",
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = linkedin, fontSize = 14.sp)
        }
    }
}

@Composable
fun StartButton(navController: NavHostController) {
    Button(
        onClick = { navController.navigate(FilmsListDest::class.java.simpleName) },
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))
    ) {
        Text(text = "Démarrer", color = Color.White)
    }
}
