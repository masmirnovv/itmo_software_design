package ru.masmirnov.sd.rxjava.db;

import com.mongodb.rx.client.MongoClient;
import com.mongodb.rx.client.MongoClients;
import com.mongodb.rx.client.Success;
import rx.Observable;

import static com.mongodb.client.model.Filters.eq;
import static ru.masmirnov.sd.rxjava.db.User.ID_COLUMN;

public class MongoDriver {

    private static MongoClient client;

    private static final String DB = "db";
    private static final String USER_COLLECTION = "user";
    private static final String PRODUCT_COLLECTION = "product";

    public MongoDriver(String url) {
        client = MongoClients.create(url);
    }


    public Observable<Success> register(User user) {
        return client.getDatabase(DB)
                     .getCollection(USER_COLLECTION)
                     .insertOne(user.toDocument());
    }

    public Observable<User> getUser(int id) {
        return client.getDatabase(DB)
                .getCollection(USER_COLLECTION)
                .find(eq(ID_COLUMN, id))
                .toObservable()
                .map(User::new);
    }


    public Observable<Success> addProduct(Product product) {
        return client.getDatabase(DB)
                .getCollection(PRODUCT_COLLECTION)
                .insertOne(product.toDocument());
    }

    public Observable<Product> listProducts(User user) {
        return client.getDatabase(DB)
                .getCollection(PRODUCT_COLLECTION)
                .find()
                .toObservable()
                .map(Product::new)
                .map(p -> p.convertPrice(user.getCurrency()));
    }

    public Observable<Product> listSpecifiedProducts(User user, String query) {
        return listProducts(user)
                .filter(p -> p.getName().toLowerCase().contains(query.toLowerCase()));
    }

}

