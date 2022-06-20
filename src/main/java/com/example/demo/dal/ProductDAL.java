package com.example.demo.dal;

import com.example.demo.model.FavouriteProduct;
import com.example.demo.model.Product;

import java.util.List;

public interface ProductDAL {

    List<Product> getAllProducts();

    List<Product> getProducts(String userId);

    Product addProduct(Product product);

    FavouriteProduct addFavourite(FavouriteProduct likedProduct);

    FavouriteProduct addRemoveFavourite(String id, int status);

    FavouriteProduct removeFavourite(String id);

    List<FavouriteProduct> getFavourites(String userId);

    FavouriteProduct getFavouriteProduct(String id, String userId);

}
