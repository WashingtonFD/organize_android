package com.organize4event.organize.services;


import com.organize4event.organize.models.ErrorReturn;
import com.organize4event.organize.models.User;
import com.organize4event.organize.models.UserType;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;


public interface UserService {

    @GET("user_type/{locale}/{code_enum}")
    Call<UserType> getUserType(
            @Path("locale") String locale,
            @Path("code_enum") int code_enum);

    @FormUrlEncoded
    @POST("user/save")
    Call<User> saveUser(
            @Field("user_type") int user_type_id,
            @Field("plan") int plan_id,
            @Field("privacy") int privacy_id,
            @Field("full_name") String full_name,
            @Field("mail") String mail,
            @Field("password") String password,
            @Field("cpf") String cpf,
            @Field("birth_date") String birth_date,
            @Field("gender") String gender
    );

    @FormUrlEncoded
    @POST("user/{user_id}")
    Call<User> updateUserToken(@Path("user_id") int user_id,
                               @Field("token") int token_id);

    @FormUrlEncoded
    @POST("user/{user_id}")
    Call<User> updateUserPrivacy(@Path("user_id") int user_id,
                                 @Field("privacy") int privacy_id);

    @Multipart
    @POST("user/{user_id}/photo")
    Call<ErrorReturn> uploadPhoto(@Path("user_id") int user_id,
                                  @Part MultipartBody.Part photo);

    @FormUrlEncoded
    @POST("user/{user_id}")
    Call<User> updateProfileFacebook(@Path("user_id") int user_id,
                                     @Field("full_name") String full_name,
                                     @Field("mail") String mail,
                                     @Field("profile_picture") String profile_picture);

}
