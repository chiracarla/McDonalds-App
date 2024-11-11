package Service;
import Enums.Locations;
import Model.*;
import Repository.IRepository;

import java.util.List;

public class OfferService {
    private final IRepository<Offer> offerRepository;///???????

    public OfferService(IRepository repository) {
        this.offerRepository = repository;
    }

    public void addOffer( int newPrice, List<Product> products) {
        int price = 0;
        for (Product product : products) {
            price += product.getProductPrice();
        }
        Offer offer = new Offer(price, newPrice, products);
        offerRepository.create(offer);
    }

    public Offer getOffer(int id) {
        return offerRepository.read(id);
    }
    //TODO:doar manageri sa adauge oferte si odar manageri sa aprobe sign up la employee

    public void deleteOffer(int id) {
        offerRepository.delete(id);
    }//a expirat termenul

}
