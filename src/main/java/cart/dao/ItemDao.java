package cart.dao;

import cart.entity.CreateItem;
import cart.entity.Item;

import java.util.List;

public interface ItemDao {
    void save(CreateItem createItem);

    List<Item> findAll();

    void update(Item item);

    void delete(Long id);
}