package com.juaracoding.werewolf.APIService;

/**
 * Created by user on 1/10/2018.
 */

import com.juaracoding.werewolf.model.player.PlayerModel;
import com.juaracoding.werewolf.model.roles.RolesModel;
import com.juaracoding.werewolf.model.room.RoomModel;
import com.juaracoding.werewolf.model.skill.SkillModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;


public interface APIInterfacesRest {


 @GET("roles/all")
 Call<RolesModel> getDataModel(@Query("X-Api-Key") String apikey);

 @GET("player/all")
 Call<PlayerModel> getDataPlayer(@Query("X-Api-Key") String apikey);

 @GET("skill/all")
 Call<SkillModel> getDataSkill(@Query("X-Api-Key") String apikey);

 @GET("room/all")
 Call<RoomModel> getDataRoom(@Query("X-Api-Key") String apikey);





 @Multipart
 @POST("roles/add")
 Call<RolesModel> sendDataRoles(

         @Part("name") RequestBody name,
         @Part("description") RequestBody description,
         @Part("skill") RequestBody skill,
         @Part MultipartBody.Part image
 );

 @Multipart
 @POST("player/add")
 Call<PlayerModel> sendDataPlayer(

         @Part("name") RequestBody name,
         @Part("password") RequestBody password,
         @Part MultipartBody.Part photo
 );

 @Multipart
 @POST("skill/add")
 Call<SkillModel> sendDataSkill(

         @Part("name") RequestBody name,
         @Part("description") RequestBody description,
         @Part("type") RequestBody type
 );

 @Multipart
 @POST("room/add")
 Call<RoomModel> sendDataRoom(

         @Part("name") RequestBody name,
         @Part("status") RequestBody status
 );
}




