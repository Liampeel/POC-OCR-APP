package com.example.myfirstapp.API;

import com.example.myfirstapp.Model.AAPDiagnosisResponse;
import com.example.myfirstapp.Model.DefaultResponse;
import com.example.myfirstapp.Model.LoginResponse;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Api {
    @FormUrlEncoded
    @POST("users")
    Call<DefaultResponse> createUser(
            @Field("email") String email,
            @Field("password") String password,
            @Field("name") String name,
            @Field("date_of_birth") String dateOfBirth
    );

    @FormUrlEncoded
    @POST("tokens")
    Call<LoginResponse> userLogin(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @PUT("users/password-reset")
    Call<ResponseBody> passwordReset(
            @Field("email") String email
    );

    @FormUrlEncoded
    @PUT("users/password")
    Call<ResponseBody> changePassword(
            @Field("token") String token,
            @Field("new_password") String newPassword
    );

    @POST("aap-diagnosis/")
    Call<AAPDiagnosisResponse> aapCreate(
            @Body RequestBody body
    );

    @GET("aap-diagnosis/")
    Call<List<AAPDiagnosisResponse>> aapList();

    @GET("aap-diagnosis/{doc_id}")
    Call<AAPDiagnosisResponse> aapDetail(
            @Path("doc_id") String id
    );

    @DELETE("aap-diagnosis/{doc_id}")
    Call<ResponseBody> aapDelete(
            @Path("doc_id") String id
    );

    @FormUrlEncoded
    @PATCH("aap-diagnosis/{doc_id}")
    Call<AAPDiagnosisResponse> aapConfirmDiagnosis(
            @Path("doc_id") String id,
            @Field("l_actual_diagnosis") String actualDiagnosis
    );

    @POST("aap-gyn-diagnosis/")
    Call<AAPDiagnosisResponse> aapGynCreate(
            @Body RequestBody body
    );

    @GET("aap-gyn-diagnosis/")
    Call<List<AAPDiagnosisResponse>> aapGynList();

    @GET("aap-gyn-diagnosis/{doc_id}")
    Call<AAPDiagnosisResponse> aapGynDetail(
            @Path("doc_id") String id
    );

    @DELETE("aap-gyn-diagnosis/{doc_id}")
    Call<ResponseBody> aapGynDelete(
            @Path("doc_id") String id
    );

    @FormUrlEncoded
    @PATCH("aap-gyn-diagnosis/{doc_id}")
    Call<AAPDiagnosisResponse> aapGynConfirmDiagnosis(
            @Path("doc_id") String id,
            @Field("l_actual_diagnosis") String actualDiagnosis
    );
}