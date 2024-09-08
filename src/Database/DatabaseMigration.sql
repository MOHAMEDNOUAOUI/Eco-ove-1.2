CREATE TYPE statut_user AS ENUM ('ACTIVE' , 'SUSPENDU')
CREATE TYPE statut_reservation AS ENUM ('ACTIVE' , 'CANCELLED')
CREATE TYPE statut_billet AS ENUM ('VENDU' , 'ANNULE' , 'ENATTENTE')
CREATE TYPE  trajet_status AS ENUM ('AVAILABLE' , 'UNAVAILABLE')

CREATE TABLE TRAJET (
    id UUID primary key,
    ville_depart VARCHAR(255),
    ville_arrivee VARCHAR(255),
    distanceKm DECIMAL(6, 2),
    travelTime VARCHAR(255)
)

CREATE TABLE USERS (
    id UUID primary key ,
    Nom VARCHAR(20),
    Prenom VARCHAR(20),
    Email VARCHAR(30),
    Numero_de_telephon VARCHAR(20),
    statut_user statut_user
)

CREATE TABLE RESERVATION(
    id UUID primary key,
    date_reservation DATE,
    origin VARCHAR(255),
    destination VARCHAR(255),
    statut_reservation statut_reservation,
    user_id UUID references USERS(id)
)

CREATE TABLE BILLETS (
    id UUID primary key,
    prix_achat DECIMAL(10, 2),
    prix_vente DECIMAL(10, 2),
    date_vente DATE,
    statut_billet statut_billet,
    type_transport type_transport,
    trajet_id UUID references TRAJET(id),
    reservation_id UUID references RESERVATION(id)
)