package com.example.demo.controller;

import com.example.demo.dal.ProductDAL;
import com.example.demo.model.FavouriteProduct;
import com.example.demo.model.Product;
import com.example.demo.payload.response.ResponseModel;
import com.example.demo.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    ProductDAL productDAL;

    @GetMapping("/getAllProducts")
    public ResponseEntity<?> getAllProducts() {
        return ResponseEntity.ok(new ResponseModel(true, "Success", productDAL.getAllProducts()));
    }

    @GetMapping("/getProducts")
    public ResponseEntity<?> getProducts(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return ResponseEntity.ok(new ResponseModel(true, "Success", productDAL.getProducts(userDetails.getId())));
    }

    @PostMapping("/addProduct")
    public ResponseEntity<?> addProduct(@Valid @RequestBody Product product, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        product.setUserId(userDetails.getId());
        return ResponseEntity.ok(new ResponseModel(true, "Success", productDAL.addProduct(product)));
    }

    @GetMapping("/addFavouriteProduct/{productId}")
    public ResponseEntity<?> addFavouriteProduct(@Valid @PathVariable String productId, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        FavouriteProduct likedProduct = productDAL.getFavouriteProduct(productId, userDetails.getId());
        if (likedProduct != null) {
            if (likedProduct.getStatus() == 1) {
                return ResponseEntity.ok(new ResponseModel(true, "Success", productDAL.addRemoveFavourite(likedProduct.getId(), 0)));
            } else {
                return ResponseEntity.ok(new ResponseModel(true, "Success", productDAL.addRemoveFavourite(likedProduct.getId(), 1)));
            }
        } else {
            FavouriteProduct product = new FavouriteProduct();
            product.setProductId(productId);
            product.setUserId(userDetails.getId());
            return ResponseEntity.ok(new ResponseModel(true, "Success", productDAL.addFavourite(product)));
        }
    }

    @GetMapping("/removeFavouriteProduct/{_id}")
    public ResponseEntity<?> disLikeProduct(@Valid @PathVariable String _id) {
        return ResponseEntity.ok(new ResponseModel(true, "Success", productDAL.removeFavourite(_id)));
    }

    @GetMapping("/getFavouriteProduct")
    public ResponseEntity<?> getLikedProducts(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return ResponseEntity.ok(new ResponseModel(true, "Success", productDAL.getFavourites(userDetails.getId())));
    }

}
