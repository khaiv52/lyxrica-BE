package com.vanlang.lyxrica_springb.service;

import com.vanlang.lyxrica_springb.exception.CartItemException;
import com.vanlang.lyxrica_springb.exception.UserException;
import com.vanlang.lyxrica_springb.model.Cart;
import com.vanlang.lyxrica_springb.model.CartItem;
import com.vanlang.lyxrica_springb.model.Product;
import com.vanlang.lyxrica_springb.model.User;
import com.vanlang.lyxrica_springb.repository.CartItemRepository;
import com.vanlang.lyxrica_springb.repository.CartRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final UserService userService;
    private final CartRepository cartRepository;

    public CartItemServiceImpl(CartItemRepository cartItemRepository, UserService userService, CartRepository cartRepository) {
        this.cartItemRepository = cartItemRepository;
        this.userService = userService;
        this.cartRepository = cartRepository;
    }

    @Override
    public CartItem createCartItem(CartItem cartItem) {
        cartItem.setQuantity(cartItem.getQuantity());
        cartItem.setPrice(cartItem.getProduct().getPrice() * cartItem.getQuantity());
        cartItem.setDiscountedPrice(cartItem.getProduct().getDiscountedPrice() * cartItem.getQuantity());

        CartItem createdCartItem = cartItemRepository.save(cartItem);
        return createdCartItem;
    }

    @Override
    public CartItem updateCartItem(Long userId, Long cartItemId, CartItem cartItem) throws CartItemException, UserException {
        // Tìm mục giỏ hàng cần cập nhật
        CartItem item = findCartItemById(cartItemId);

        // Tìm thông tin người dùng sở hữu mục giỏ hàng
        User user = userService.findUserById(item.getUserId());

        // Tìm thông tin người dùng yêu cầu cập nhật mục giỏ hàng
        User reqUer = userService.findUserById(userId);

        // Kiểm tra xem người dùng hiện tại có quyền cập nhật mục giỏ hàng hay không
        if (user.getId().equals(reqUer.getId())) {
            item.setQuantity(cartItem.getQuantity());
            item.setPrice(item.getProduct().getPrice() * cartItem.getQuantity());
            item.setDiscountedPrice(item.getProduct().getDiscountedPrice() * cartItem.getQuantity());
        } else {
            // Nếu không phải chủ sở hữu, ném ra ngoại lệ UserException
            throw new UserException("You can't update another user's item");
        }
        return cartItemRepository.save(item);
    }

    @Override
    public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId) {
        CartItem cartItem = cartItemRepository.isCartItemExist(cart, product, size, userId);
        return cartItem;
    }

    @Override
    public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException {
        // Tìm mục giỏ hàng cần xóa
        CartItem cartItem = findCartItemById(cartItemId);

        // Tìm thông tin người dùng sở hữu mục giỏ hàng
        User user = userService.findUserById(cartItem.getUserId());

        // Tìm thông tin người dùng hiện tại
        User reqUser = userService.findUserById(userId);

        // Kiểm tra xem người dùng hiện tại có quyền xóa mục giỏ hàng hay không
        if (user.getId().equals(reqUser.getId())) {
            // Nếu người dùng hiện tại là chủ sở hữu của mục giỏ hàng, thực hiện xóa
            cartItemRepository.deleteById(cartItemId);
        } else {
            // Nếu không phải chủ sở hữu, ném ra ngoại lệ UserException
            throw new UserException("You can't remove another user's item");
        }
    }


    @Override
    public CartItem findCartItemById(Long cartItemId) throws CartItemException {
        Optional<CartItem> cartItemOption = cartItemRepository.findById(cartItemId);

        if (cartItemOption.isPresent()) {
            return cartItemOption.get();
        }
        throw new CartItemException("CartItem not found with id: " + cartItemId);
    }
}
