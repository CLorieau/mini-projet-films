package com.example.premiere_appli

abstract class Piece (val longueur : Double, val largeur : Double, val nom : String){
    fun surface(){
        val surface = longueur*largeur
        println("La surface est de ${surface}")
    }
}

class Cuisine : Piece(longueur = 1.5, largeur = 2.0, nom = "cuisine" )

class Salon : Piece(longueur = 15.0, largeur = 18.5, nom = "salon" )

class Etudiant (val name : String, val promo : String, val matieres : List<String>)

val etudiants = listOf(
    Etudiant ("Paul", "2025", listOf("mobile", "web", "BDD")),
    Etudiant ("Yazid", "2024", listOf("mobile", "Android", "Réseau")),
    Etudiant ("Caroline", "2025", listOf("SE", "Anglais")),
)



fun main (items: List<Cuisine>, elements: List<Salon>) {
    for (item in items) println("La ${item.nom} mesure ${item.surface()} m²")
    for (element in elements) println("Le ${element.nom} mesure ${element.surface()} m²")
    etudiants.filter {it.promo == "2024"}.forEach{ println(it.name)}
    etudiants.filter { it.matieres.size > 2 }.forEach { println(it.name) }


}




