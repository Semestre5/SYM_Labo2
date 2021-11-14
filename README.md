# SYM_Labo2

## 3. Manipulation

### 3.1 Service de transmission asynchrone

### 3.2 Transmission différée

### 3.3 Transmission d’objets

#### 3.3.1 Format JSON
#### 3.3.2 Format XML
#### 3.3.3 Protocol Buffers

### 3.4 GraphQL – Format JSON

Pour l'activité, on crée des listes et des adapter liés à notre spinner (pour les auteurs) et à notre listView (pour les livres). Sur le spinner, l'élément onItemSelectedListener est utilisé pour détecter un changement et afficher les livres liés à un auteur. Pour la récupération de la réponse, on teste si elle contient l'intitulé des requêtes (findAllAuthors) pour savoir si il faut ajuster les auteurs (à la création) ou les livres (à la création et lors d'un changement d'auteur). On remplit les listes liées aux adapter puis on notifie ce dernier qu'une modification a été effectuée afin de mettre à jour les données.

### 3.5 Transmission compressée

Au niveau de l'activité, exactement pareil que l'asynchrone, mise à part que dans l'envoi au SymComManager on rajoute un booléen pour la configuration.

Dans le SymComManager, on ajoute les en-têtes X-Network et X-Content-Encoding et on compresse le contenu uniquement si le booléen compressed est à true. A la réception, on teste si l'en-tête X-Content-Encoding a été spécifié pour savoir s'il faut décompresser le message ou non.

## 4. Questions

### 4.1 Traitement des erreurs
> Les classes et interfaces SymComManager et CommunicationEventListener, utilisées au point 3.1,
restent très (et certainement trop) simples pour être utilisables dans une vraie application : que se
passe-t-il si le serveur n’est pas joignable dans l’immédiat ou s’il retourne un code HTTP d’erreur ?
Veuillez proposer une nouvelle version, mieux adaptée, de ces deux classes / interfaces pour vous aider
à illustrer votre réponse.

### 4.2 Authentification

> Si une authentification par le serveur est requise, peut-on utiliser un protocole asynchrone ? Quelles seraient les restrictions ? Peut-on utiliser une transmission différée ?

### 4.3 Threads concurrents

> Lors de l'utilisation de protocoles asynchrones, c'est généralement deux threads différents qui se
répartissent les différentes étapes (préparation, envoi, réception et traitement des données) de la
communication. Quels problèmes cela peut-il poser ?



### 4.4 Ecriture différée

> Lorsque l'on implémente l'écriture différée, il arrive que l'on ait soudainement plusieurs transmissions en attente qui deviennent possibles simultanément. Comment implémenter proprement cette situation ? Voici deux possibilités :
>
> > Effectuer une connexion par transmission différée 
> 
> > Multiplexer toutes les connexions vers un même serveur en une seule connexion de transport. Dans ce dernier cas, comment implémenter le protocole applicatif, quels avantages peut-on espérer de ce multiplexage, et surtout, comment doit-on planifier les réponses du serveur lorsque ces dernières s'avèrent nécessaires ?

### 4.5 Transmission d’objets

> a. Quel inconvénient y a-t-il à utiliser une infrastructure de type REST/JSON n'offrant aucun service de validation (DTD, XML-schéma, WSDL) par rapport à une infrastructure comme SOAP offrant ces possibilités ? Est-ce qu’il y a en revanche des avantages que vous pouvez citer ?
>
> b. Par rapport à l’API GraphQL mise à disposition pour ce laboratoire. Avez-vous constaté des points qui pourraient être améliorés pour une utilisation mobile ? Veuillez en discuter, vous pouvez élargir votre réflexion à une problématique plus large que la manipulation effectuée.

### 4.6 Transmission compressée

> Quel gain de compression (en volume et en temps) peut-on constater en moyenne sur des fichiers texte (xml et json sont aussi du texte) en utilisant de la compression du point 3.5 ? Vous comparerez plusieurs tailles et types de contenu, vous pouvez vous aider des valeurs «Received Size» (taille en bytes du contenu transféré) et «Payload Size» (taille en bytes du contenu après décompression) indiquées dans l’interface de logs du serveur utilisé pour les manipulations de ce laboratoire.
