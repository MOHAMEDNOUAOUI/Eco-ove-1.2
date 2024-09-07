package Model;


import Enum.StatutBillets;
import Enum.TypeTransport;

import java.util.UUID;


public class Billets {
    private UUID id;
    private String prix_achat;
    private String prix_vente;
    private String date_vente;
    private StatutBillets statut_billet;
    private TypeTransport type_transport;


}
