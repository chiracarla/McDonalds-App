package Service;
import Enums.Locations;
import Model.*;
import Repository.IRepository;

import java.util.List;

public class OfferService {
    private final IRepository offerRepository;

    public OfferService(IRepository repository) {
        this.offerRepository = repository;
    }

    public void addOffer(int originalPrice, int newPrice, List<Product> products) {
        Offer offer = new Offer(originalPrice, newPrice, products);
        offerRepository.create(offer);
    }

    //doar manageri sa adauge oferte si odar manageri sa aprobe sign up la employee

}
