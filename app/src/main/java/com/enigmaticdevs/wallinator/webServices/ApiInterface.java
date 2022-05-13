package com.enigmaticdevs.wallinator.webServices;


import com.enigmaticdevs.wallinator.models.Collection;
import com.enigmaticdevs.wallinator.models.Download;
import com.enigmaticdevs.wallinator.models.Photo;
import com.enigmaticdevs.wallinator.models.SearchResultModel;
import com.enigmaticdevs.wallinator.models.User;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("photos")
    Call<List<Photo>> getRecentPhotos(
            @Query("page") int page,
            @Query("per_page") int pageLimit,
            @Query("order_by") String order);

    @GET("collections/featured")
    Call<List<Collection>> getCollections(
            @Query("per_page") int perPage,
            @Query("page") int page);

    @GET("collections/{id}")
    Call<Collection> getInformationOfCollection(
            @Path("id") int id);

    @GET("collections/{id}/photos")
    Call<List<Photo>> getPhotosOfCollection(
            @Path("id") int id,
            @Query("per_page") int perPage,
            @Query("page") int page);

    @GET("photos/{id}")
    Call<Photo> getPhoto(
            @Path("id") String id);

    @GET("search/photos")
    Call<SearchResultModel> getSearchPhotos(
            @Query("query") String query,
            @Query("page") int page,
            @Query("per_page") int pageLimit);

    @GET("photos/random/")
    Call<List<Photo>> getRandomPhoto(
            @Query("count") int count);

    @GET("photos/{id}/download")
    Call<Download> Download(@Path("id") String id);

    @GET("/users/{user}")
    Call<User> GetUserProfile(@Path("user") String user);

    @GET("/users/{user}/photos/")
    Call<List<Photo>> GetUserPhotos(
                    @Path("user") String user ,
                    @Query("page") int page,
                    @Query("per_page") int pageLimit);

    @GET("/users/{user}/collections/")
    Call<List<Collection>> GetUserCollections(
            @Path("user") String user ,
            @Query("page") int page,
            @Query("per_page") int pageLimit);

    @GET("search/collections/")
    Call<SearchResultModel> getSearchCollections(
            @Query("query") String query,
            @Query("page") int page,
            @Query("per_page") int pageLimit);
}
