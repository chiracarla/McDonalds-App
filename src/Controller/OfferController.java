package Controller;
import Enums.Locations;
import Model.*;
import Service.OfferService;

import java.util.List;

public class OfferController {
    private OfferService offerService;

    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    public void add( int newPrice, List<Product> products) {
        offerService.addOffer( newPrice, products);
    }//TODO: user input

}
