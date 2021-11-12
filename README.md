# SYM_Labo2

## GraphQL

Pour l'activité, on crée des listes et des adapter liés à notre spinner (pour les auteurs) et à notre listView (pour les livres). Sur le spinner, l'élément onItemSelectedListener est utilisé pour détecter un changement et afficher les livres liés à un auteur. Pour la récupération de la réponse, on teste si elle contient l'intitulé des requêtes (findAllAuthors) pour savoir si il faut ajuster les auteurs (à la création) ou les livres (à la création et lors d'un changement d'auteur). On remplit les listes liées aux adapter puis on notifie ce dernier qu'une modification a été effectuée afin de mettre à jour les données.

## Compressé

Au niveau de l'activité, exactement pareil que l'asynchrone, mise à part que dans l'envoi au SymComManager on rajoute un booléen pour la configuration.

Dans le SymComManager, on ajoute les en-têtes X-Network et X-Content-Encoding et on compresse le contenu uniquement si le booléen compressed est à true. A la réception, on teste si l'en-tête X-Content-Encoding a été spécifié pour savoir s'il faut décompresser le message ou non.