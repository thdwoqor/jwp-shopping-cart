package cart.controller;

import cart.annotation.login.Login;
import cart.dto.ResultResponse;
import cart.dto.SuccessCode;
import cart.dto.cart.CartResponse;
import cart.dto.cart.CartSaveRequest;
import cart.entity.MemberEntity;
import cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping("/carts")
    public List<CartResponse> getCarts(@Login MemberEntity member) {
        return cartService.findAll(member.getEmail());
    }

    @PostMapping("/cart")
    public ResponseEntity<ResultResponse> addCart(@Login MemberEntity member, @RequestBody CartSaveRequest cart) {
        Long save = cartService.save(member.getEmail(), cart.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResultResponse(SuccessCode.CREATE_CART, save));
    }

    @DeleteMapping("/cart/{itemId}")
    public ResponseEntity<ResultResponse> deleteCart(@Login MemberEntity member, @PathVariable Long itemId) {
        cartService.delete(member.getEmail(), itemId);
        return ResponseEntity.status(HttpStatus.OK).body(new ResultResponse(SuccessCode.DELETE_CART, member.getEmail()));
    }
}
