package ca.home.novacom.restfull.service;

import ca.home.novacom.restfull.domain.Basket;
import ca.home.novacom.restfull.repository.BasketRepository;
import org.springframework.stereotype.Service;

@Service
public class BasketServiceImpl implements BasketService {
    private final BasketRepository basketRepository;

    public BasketServiceImpl(BasketRepository basketRepository){
        this.basketRepository = basketRepository;
    }
    /**@CreateBasket
     * get object Basket, and save to Db
     * */
    @Override
    public Basket createBasket(Basket basket) {
        return basketRepository.save(basket);
    }
}
