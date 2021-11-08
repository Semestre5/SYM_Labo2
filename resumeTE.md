# Résumé SYM

Défi : Connections peu fiable, autonomies, interface restreintes, environnement local variable, appareils peuvent être perdu, sécurité hasardeuse, différents usage...

Portabilité: Les périphériques doivent être portables et permettre de se déplacer facilement

Connectivité: Possibilité de rester connecté en permanence, sans être affecté par le déplacement

Interactivité: Les périphériques et utilisateurs appartenant à un système informatique mobile sont interconnectés dans le but de pouvoir collaborer 

Individualité: adaptation de la technologie aux besoins personnels 

### IOS

OS dérivé de MacOS, basé sur un noyau hybride XNU (Unix)

Environnement de développement : XCode, (un IDE, un LLVM Toolchain (Outil de compilation) et un simulateur). Il existe des alternative, mais la compilation sera effectuée par XCode, et donc sur Mac. License payant pour développement et distribution.

### Autre OS

Windows Phone : dead depuis fin 2019

FireFox OS : 0,1% des téléphone en 2014, utilisé sur TV (Panasonic), dernière version stable 2015.

Librem: Orienté matériel. Hardware + software 100% opensource. Librem5 basé sur PureOS

### Android

Noyau Linux avec machine virtuelle dédiée (Dalvik et ensuite ART), pour isoler dans une sandbox.

Propriété : Multi-tâche (de base), open-source, et donc permet des surcouche des constructeur.

Multiple constructeurs mobile -> Utilisation une machine virtuelle dédiée,

Dalvik était très efficace, et ART compile toute les applications lors de l'install. Calqué sur la JDK.

Kotlin est supproté depuis 2017, et sur Android depuis 2019.

Architecture Android Open Sourc Projet (AOSP) 1. Linux Kernel (Classique, Blootooth driver ..) 2. Hardware Abstration Layer (Blutooth, Wifi...) 3. Librairie (Media Framework, Surface Manager, Libc...) 4. Application FramerWork (Serveur applicatif (Apache2), Ressource Manager, Activity Manager, Window Mannager ...) 5. Application (Application complète)

Architecture Google : Pareil avec l'Android Runtime (Core Librairie, Dalvik.. ) dans la couche librairie.

### Kotlin

Language orienté objet et fonctionnalité. Développé majoritairement par JetBrains. "Compilé" en bytecode Java (compatible JVM ou JS). Interopérabilité possible avec Java. Assez proche de Swift.$

## Android

### Activité

Une activité est une unique chose que l'utilisateur peut effectuer. Presque toutes les activités interagissent avec l'utilisateur, la classe Activity s'occupe donc de créer une fenêtre pour vous dans laquelle vous pouvez placer votre interface utilisateur avec setContentView(View). Alors que les activités sont souvent présentées à l'utilisateur sous forme de fenêtres plein écran. Au besoin, elle peut être tuée par l'OS. Initialisée avec une `Intention`, qui a les paramètres important pour cette application.

<img src="https://developer.android.com/images/activity_lifecycle.png" alt="State diagram for an Android Activity Lifecycle." style="zoom:45%;" />

### Intents

Explicite : Permet de démarrer une Activité définie, quand on a un vision claire de ce qu'on souhaite faire.

```java
Intent(applicationContext,DetailActivity::class.java)
startActivity(intent) 

// shared content
val intent = Intent(this, SecondActivity:: class.java).apply{
putExtra(“key”,”New Value”)
}
startActivity(intent)
```

Implicite : Permet de démarrer une action, ce qui démarrera une action sans pour autant l'appeler. Exemple : On clique sur un boutton pour "Partager", on sait pas si l'utilisateur veut faire en blootooth, avec Gmail...

### Fragments

Partie d'une interface graphique réutilisable. Permet de partictionner une activité. Exemple d'utilisation : onglet. Chaque onglet est un fragment, et ils sont inclus dans une activité qui a tous les onglet, et sont donc tous un sous-ensemble d'une activité. Un fragment définit et gère sa propre mise en page, a son propre cycle de vie et peut gérer ses propres événements d'entrée. La hiérarchie des vues du fragment devient partie intégrante de la hiérarchie des vues de l'hôte ou s'y attache. Permet donc une grande modularité de l'interface utilisateur.

https://developer.android.com/guide/fragments

### Services

Tâche en arrière plan qui permet d'envoyer des signaux. Un service est exécuté dans le thread principal. Permet d'effectuer toutes les opérations pour lesquelle l'utilisateur n'a pas besoin d'intéragir directement, comme un téléchargement, on valide une activité pour télécharger, et le service va s'occuper de télécharger en arrière plan. Il peut aussi maintenir des connection (mail) ou même parfois peut founir un service d'API.

### Environnement de dev

Android Studio. Vue en XML, Descriptif de l'application dans `AndroidManifest.xml` (privilège et propriété). Possibilité d'intégrer du code C/C++ (performance). Pour mitiger le problème de taille d'écran, utilisation des `Layout`. 

### Layout

Permet de définir les emplacement des éléments sur l'écran. Il existe différentes stratégies, comme du positionnement relatif (RelativeLayout ou ContraintLayout), Les un après les autre (LinearLayout), ou comme un tableau (TableLayout). Il existe une structure de layout permettant de les combinée et et les imbriquer. Il est possible de créer son propre Layout personnalisé.

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android" <!-- vue placé dans LineraLayout -->
              android:layout_width="match_parent" <!-- utilise tout l'espace -->
              android:layout_height="match_parent" <!-- utilise tout l'espace -->
              android:orientation="vertical" 
			  android:padding="10dp" > <!--px : pixel, pas dynmamique; in(inch) mm : valeur physique; pt 1/72 in; dp : density independant pixels : 1 dp is 1px sur 160dpi; sp scale-independant pixels -->
    <TextView android:id="@+id/text" <!-- identifiant concerné, permet link -->
              android:layout_width="wrap_content" <!-- warp_content : constant -->
              android:layout_height="wrap_content"
              android:text="Hello, I am a TextView" />
    <Button android:id="@+id/button"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Hello, I am a Button" />
</LinearLayout>
```

Pour appliquer un layout, il faut `setContentView(R.layout.nomdufichiersansxml)` dans une activité

`findViewById(R.id.text)` permet de link le titre avec l'activité

Ensuite, on peut mettre en place des listener sur les objet linké pour traiter les interractions.

### Android JetPack

Se documenter sur web, cours pas explicite par lui seul

### Programmer en android

Grand principe mobilité : Ce qui n'est pas une interaction direct et immédiate doit être délégué à un autre thread.

### Application native

Possibilité qu'une migration vers des thin client soit mise en place -> besoin de beaucoup de communication, il est possible que cette migration n'ait lieu que pour de la consultation

Slide 69-70 pas compris

Il existe des applications basée sur web packagées sous la forme d'app. (Ionic(angular ou React), Adoble PhoneGap)

Ou même qui génère du code Android/iOS du même code (React Native, Xamarin, Unity3D, Goo)

Parfois basé sur une VM, comme React.

Que choisir ? Natif pour application complexe, et cross-plateforme pour des application "simple"

React est très mis à jour, et open source. Flutter, très documenté et facile.

## Communication

**Relation de Shannon**
$$
C = W * log{_2}{(1 + \frac{S}{N})}
$$
Cette formule montre la limite des ondes sur le réseau. On ne peut pas exploiter sans limite le milieu air.

4 caractéristiques des antenne émettrice et réceptrices : le gain (endroit), le milieu (contexte), distance et puissance. 

2 types d'antenne : omnidirectionnelle et parabolique

La puissance de réception diminue proportionnellement au carré avec la distance et la fréquence, indépendamment.

### GSM / Wireless Application Protocol (2G)

Différents protocoles

HTTP passe à WAE, fournit une architecture pour la communication entre les appareils sans fil et les serveurs Web. De plus, on utilise WTP (Wireless transaction protocole). Ce protocole gère les types de connexion et la fiabilité du service. Cette couche se situe au niveau applicatif.

WML est un langage à balises conçu spécifiquement pour le WAP (Wireless access point), de manière à pouvoir s'afficher sur un écran de téléphone mobile.

### UMTS / Universal Mobile Telecommuniation System (3G)

Meilleur débit, technologie à étalement de spectre, affectée à chaque cellule radio **est** de 5 MHz avec une largeur spectrale réelle de 4,685 MHz .

- Duplex temporel [TDD](https://fr.wikipedia.org/wiki/TDD_(télécommunications)) : 1 885,00 à 1 920,00 MHz (bande de 35 MHz de largeur) et 2 010,00 à 2 025,00 MHz (bande de 15 MHz) ;
- Duplex fréquentiel [FDD](https://fr.wikipedia.org/wiki/FDD_(télécommunications)) : 1 920,00 à 1 980,00 MHz (*uplink* de 60 MHz) et 2 110,00 à 2 170,00 MHz (*downlink* de 60 MHz) ;
- Bandes satellites : 1 980,00 à 2 010,00 MHz (uplink de 30 MHz) et 2 170,00 à 2 200,00 MHz (*downlink* de 30 MHz).

Ces valeurs peuvent changer en fonction du pays

Amélioration possible du début avec HSDPA

### LTE / Long Term Evolution

Extension de la 3g. Ceci n'est pas la 4g. 150 Mps théorique. Les débit en dessus de 1 MPs sont souvent bloqué par les lieu donc on en met beaucoup. Fréquence entre 800 MHZ  (LTE) et 3.8 GHz (LTE Advanced). On récupère un bout des fréquence de GSM (2G) Différents dans certains pays.

Flemme milieu air à partir de page 29

## Communication internet





