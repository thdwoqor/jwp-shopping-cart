package cart.dao;

import cart.domain.Item;
import cart.entity.ItemEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@Sql({"classpath:test_init.sql"})
class JdbcItemDaoTestEntity {

    @Autowired
    private ItemDao itemDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        itemDao.save(new Item("치킨", "a", 10000));
        itemDao.save(new Item("피자", "b", 20000));
    }

    @Test
    @DisplayName("상품을 저장할 수 있다.")
    void save() {
        Item item = new Item("햄버거", "c", 2000);

        itemDao.save(item);

        List<ItemEntity> itemEntities = itemDao.findAll();

        assertAll(
                () -> assertThat(itemEntities.size()).isEqualTo(3),
                () -> assertThat(itemEntities.get(2).getId()).isEqualTo(3)
        );
    }

    @Test
    @DisplayName("상품 목록을 조회할 수 있다.")
    void findAll() {
        List<ItemEntity> itemEntities = itemDao.findAll();

        assertAll(
                () -> assertThat(itemEntities.size()).isEqualTo(2),
                () -> assertThat(itemEntities.get(1).getId()).isEqualTo(2)
        );
    }

    @Test
    @DisplayName("상품 정보를 수정할 수 있다.")
    void update() {
        Item item = new Item("햄버거", "c", 2000);

        itemDao.update(2L, item);

        List<ItemEntity> itemEntities = itemDao.findAll();

        assertAll(
                () -> assertThat(itemEntities.size()).isEqualTo(2),
                () -> assertThat(itemEntities.get(1).getId()).isEqualTo(2),
                () -> assertThat(itemEntities.get(1).getName()).isEqualTo("햄버거"),
                () -> assertThat(itemEntities.get(1).getImageUrl()).isEqualTo("c"),
                () -> assertThat(itemEntities.get(1).getPrice()).isEqualTo(2000)
        );
    }

    @Test
    @DisplayName("상품 삭제 할 수 있다.")
    void delete() {
        itemDao.delete(2L);

        List<ItemEntity> itemEntities = itemDao.findAll();
        assertThat(itemEntities.size()).isEqualTo(1);
    }
}
