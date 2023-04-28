package cart.controller;

import cart.dao.ItemDao;
import cart.domain.Item;
import cart.dto.ItemRequest;
import cart.dto.ItemUpdateRequest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import static org.hamcrest.Matchers.equalTo;

@Sql("classpath:test_init.sql")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AdminControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ItemDao itemDao;

    @BeforeEach
    void setUp() {
        itemDao.save(new Item("치킨", "a", 10000));
        itemDao.save(new Item("피자", "b", 20000));
    }

    @Test
    @DisplayName("상품 추가 테스트")
    void addItemTest() {
        //given
        ItemRequest itemRequest = new ItemRequest("국밥", "c", 30000);

        //then
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(itemRequest)
                .when().post("http://localhost:"+port+"/admin/item")
                .then().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("data.id", equalTo(3));
    }

    @Test
    @DisplayName("상품 수정 테스트")
    void updateItemTest() {
        //given
        ItemUpdateRequest itemRequest = new ItemUpdateRequest(1L, "국밥", "c", 30000);

        //then
        RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(itemRequest)
                .when().put("http://localhost:"+port+"/admin/item")
                .then().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE);
    }

    @Test
    @DisplayName("상품 삭제 테스트")
    void deleteItemTest() {
        //then
        RestAssured.given().log().all()
                .when().delete("http://localhost:"+port+"/admin/admin/1")
                .then().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE);
    }
//
//    @ParameterizedTest
//    @DisplayName("상품 이름이 올바르지 않은 경우 테스트")
//    @CsvSource(value = {":상품명은 공백일 수 없습니다.", "    :상품명은 공백일 수 없습니다.", "ㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁ:상품명의 길이는 30자 이하여야합니다."}, delimiter = ':')
//    void addItemNameFailTest(String name, String message) {
//        //given
//        ItemRequest itemRequest = new ItemRequest(name, "c", 10000);
//
//        //then
//        RestAssured.given().log().all()
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .body(itemRequest)
//                .when().post("/admin/items/add")
//                .then().log().all()
//                .statusCode(HttpStatus.BAD_REQUEST.value())
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .body("name", equalTo(message));
//    }
//
//    @ParameterizedTest
//    @DisplayName("상품 이미지 url이 올바르지 않은 경우 테스트")
//    @CsvSource(value = {":이미지 url은 공백일 수 없습니다.", "    :이미지 url은 공백일 수 없습니다."}, delimiter = ':')
//    void addItemImageUrlFailTest(String url, String message) {
//        //given
//        ItemRequest itemRequest = new ItemRequest("name", url, 10000);
//
//        //then
//        RestAssured.given().log().all()
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .body(itemRequest)
//                .when().post("/admin/items/add")
//                .then().log().all()
//                .statusCode(HttpStatus.BAD_REQUEST.value())
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .body("imageUrl", equalTo(message));
//    }
//
//    @ParameterizedTest
//    @DisplayName("상품 가격이 올바르지 않은 경우 테스트")
//    @CsvSource(value = {":가격은 공백일 수 없습니다.", "-1:가격은 최소 0원 이상이어야합니다.", "1000001:가격은 최대 100만원 이하여야합니다."}, delimiter = ':')
//    void addItemPriceFailTest(Integer price, String message) {
//        //given
//        ItemRequest itemRequest = new ItemRequest("국밥", "c", price);
//
//        //then
//        RestAssured.given().log().all()
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .body(itemRequest)
//                .when().post("/admin/items/add")
//                .then().log().all()
//                .statusCode(HttpStatus.BAD_REQUEST.value())
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .body("price", equalTo(message));
//    }
}
