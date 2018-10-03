package com.rdavepatient.soft.Community.remote;

import com.rdavepatient.soft.Community.Models.Category;
import com.rdavepatient.soft.Community.Models.Commentrepone;
import com.rdavepatient.soft.Community.Models.GetCatagary;
import com.rdavepatient.soft.Community.Models.GetComments;
import com.rdavepatient.soft.Community.Models.GetLikes;
import com.rdavepatient.soft.Community.Models.GetPostList;
import com.rdavepatient.soft.Community.Models.GetSinglePosts;
import com.rdavepatient.soft.Community.Models.GetUserProfile;
import com.rdavepatient.soft.Community.Models.OTPverify;
import com.rdavepatient.soft.Community.Models.OtpData;
import com.rdavepatient.soft.Community.Models.Refrral;
import com.rdavepatient.soft.Community.Models.Registers;
import com.rdavepatient.soft.Community.Models.SavePots;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import rx.Observable;

public interface APIService {




    @POST("User/Register")
    Observable<Registers> User_register(@Body Registers.UserEntity data);


    @POST("User/Login")
    Observable<Registers> checkLogin(@Body Registers.UserEntity data);


    @GET("User/GetProfile")
    Observable<GetUserProfile> GetProfile(@Query("userid") int Userid);

    @POST("User/UpdateProfile")
    Observable<GetUserProfile> UpdateProfile(@Body GetUserProfile.ProfileEntity data);

    @Multipart
    @POST("User/ProfileUpload")
    Observable<GetUserProfile> UpdateProfileImage(@Part MultipartBody.Part Image, @Part("Userid") int Userid);

    @POST("UserPosts/SavePots")
    Observable<Registers> SavePots(@Body SavePots datas);

//    @Multipart
//    @POST("UserPosts/PostwithImage")
//    Observable<Registers> SavePotswithimage(@Part MultipartBody.Part Image, @Body RequestBody datas);


    @Multipart
    @POST("UserPosts/PostwithImage")
    Observable<Registers> SavePotswithimage(@Part MultipartBody.Part ProfileFile,@Part("Posts") RequestBody Content);




    @GET("UserPosts/GetList")
    Observable<GetPostList> GetPostList( @Query("category") String category , @Query("Userid") int id);

//    @GET("UserPosts/GetList")
//    Observable<GetPostList> GetAllPostList();


    @GET("UserPosts/GetSingle")
    Observable<GetSinglePosts> GetSinglePost(@Query("Postid") int id);



    @GET("UserPosts/GetComments")
    Observable<GetComments> GetComments(@Query("postid") int P_id);

    @POST("UserPosts/Comments")
    Observable<Commentrepone> PostComments(@Body GetComments.CommentsEntity data);

    @GET("Reffral/GetRefrral")
    Observable<Refrral> GetRefrral(@Query("UserId") int U_id);


    @GET("User/VerifyOtp")
    Observable<OTPverify> VerifyOtp(@Query("mobile") String mobile, @Query("otp") String otp);


    @GET("UserPosts/DeletePost")
    Observable<Commentrepone> DeletePost(@Query("Postid") int P_id);

    @GET("UserPosts/makeType")
    Observable<Commentrepone> ChangeType(@Query("Postid") int P_id,@Query("typeid") int Type);

    @GET("User/ChangePassword")
    Observable<Commentrepone> ChangePassword(@Query("userid") int U_id,@Query("password") String NewPassword,@Query("oldpassword") String OldPassword);


    @GET("Category/GetCategory")
    Observable<Category> GetCategory();

    @POST("Category/SetCategory")
    Observable<Category> SetCategory(@Body List<Category.CategoryEntity> data, @Query("userid") int P_id);

    @POST("UserPosts/PostLikes")
    Observable<Commentrepone> PostLikes(@Query("postid") int P_id,@Query("userid") int Type);

    @GET("UserPosts/GetLikes")
    Observable<GetLikes> GetLikes(@Query("postid") int P_id);


    @GET("Category/GetCategory")
    Observable<GetCatagary> GetCategory(@Query("userid") int P_id);


    @GET("User/ForgetPassword")
    Observable<OtpData> ForgetPassword(@Query("mobileno") String M_number);




//    @POST("api/User/Register")
//    Observable<Register> User_register(@Body Register.DataEntity data);
//
//    @POST("api/Setting/SaveRequest")
//    Observable<Contactus> ContactUS(@Body Contactus.DataEntity datas);
//
//    @GET("api/User/GetTeam")
//    Observable<GetTeam> getallteamdata(@Query("Lang") int Lang);
//
//
//    @GET("api/User/GetOtp")
//    Observable<otps> Sendotptouser(@Query("mobileno") String mobile);
//
//    @GET("api/Setting/GetTermCondition")
//    Observable<GetTerm> GetTermConditions();
//
//    @POST("api/User/FollowTeam")
//    Observable<FollowTeam> FollowTeams(@Body FollowTeam.DataEntity datas);
//
//
//    @GET("api/Setting/GetFAQS")
//    Observable<faqcategory> Getcategory(@Query("category") int id);
//
//    @GET("api/User/GetTeamNotify")
//    Observable<GetTeam_notifaction> TeamNotifaction(@Query("userid") int id, @Query("Lang") int Lang);
//
//
//    @POST("api/User/SetTeamNotify")
//    Observable<ChangeNotifaction> ChageNotifaction(@Query("userid") int id, @Query("teamid") int teamid);
//
//
//    @GET("api/Setting/GetLeadPartner")
//    Observable<LeadPartners> GetLeadPartner();
//
//    @GET("api/Setting/GetBankPartner")
//    Observable<LeadPartners> GetBankPartner();
//
//    @GET("api/Stats/GetStats")
//    Observable<GetallStates> GetallStats();
//
//    @GET("api/Stats/GetTopPlayer")
//    Observable<GetTopPlayers> GetTopPlayer(@Query("Lang") int Lang);
//
//
//    @GET("api/Stats/GetTopTeam")
//    Observable<GetTopTeamLIst> GetTopTeam(@Query("Lang") int Lang);
//
//
//    @GET("api/stats/GetTopPlayerAssist")
//    Observable<GetTopPlayersAssist> GetTopPlayerAssist(@Query("Lang") int Lang);
//
//
//    @GET("api/PlayerProfile/GetProfile")
//    Observable<GetPlayerDeatailes> GetPlayerDeatails(@Query("Playerid") int id, @Query("Lang") int Lang);
//
//    @GET("api/UsersTeam/GetPlayer")
//    Observable<GetPlayerDeatails> GetPlayers(@Query("pos") String pos, @Query("Lang") int Lang);
//
//
//    @POST("api/User/JoinLeague")
//    Observable<JoinLeagues> JoinLeague(@Query("userid") int id, @Query("type") int type, @Query("leaguecode") String leaguecode);
//
//    @POST("api/User/CreateLeague")
//    Observable<CreateLeagues> CreateLeague(@Query("userid") int id, @Query("LeagueName") String LeagueName, @Query("week") int week);
//
//    @GET("api/UsersTeam/AutoSetTeam")
//    Observable<Autocomplteplayer> Autocomplte(@Query("Lang") int id, @Query("userid") int Lang);
//
//    @POST("api/User/favourite")
//    Observable<Favratelistmodel> favratelist(@Query("userid") int id, @Query("teamid") int teamid);
//
//    @GET("api/league/GetcLeague")
//    Observable<Getprivateleage> Getprivateleage(@Query("userid") int id, @Query("type") int type, @Query("Lang") int Lang);
//
//    @GET("api/league/GetcLeague")
//    Observable<Getprivateleage1> Getprivateleage1(@Query("userid") int id, @Query("type") int type, @Query("Lang") int Lang);
//
//    @GET("api/Setting/GetCountry")
//    Observable<Getallcuntry> getallcuntry();
//
//    @GET("api/User/GetWeek")
//    Observable<Getallweeks> getallweeks();  //Shots
//
//
//    @POST("api/Record/TopShots")
//    Observable<GetTopShots> GetTopShots(@Query("category") String ptype, @Query("Lang") int Lang);
//
//    @GET("api/Stats/GetSavePlayer")
//    Observable<GetSavePlayer> GetTopsaves(@Query("Lang") int Lang);
//
//    @POST("api/Record/TopRecord")
//    Observable<GetTopRecordlw> GetTopLooser(@Query("category") String ptype, @Query("Lang") int Lang);
//
//    @POST("api/Record/TopShots")
//    Observable<GetTeamTopShots> GetTeamTopShots(@Query("category") String ptype, @Query("Lang") int Lang);
//
//    @GET("api/Events/GetStats")
//    Observable<GetHomeStats> HomeStats(@Query("eventid") int E_id);
//
//
//    @GET("api/Events/GetTeamlayer")
//    Observable<GetTeamdata> GetTeam1datas(@Query("Teamid") int id, @Query("Lang") int Lang);
//
//
//    @GET("api/Events/GetTeamlayer")
//    Observable<GetTeamdata1> GetTeam1datas1(@Query("Teamid") int id, @Query("Lang") int Lang);
//
//    @GET("api/Events/GetPriviusResult")
//    Observable<GetPriviusResults> GetPriviusResult();
//
//
//    @GET("api/Events/GetPriviusResult")
//    Observable<GetPriviusResults> GetPriviusResultteamvsteam(@Query("Eventid") int id);
//
//    @GET("api/Events/GeLeaguetbl")
//    Observable<GetLeagetables> Leagetable();
//
//    @GET("api/UsersTeam/GetTeamoverview")
//    Observable<GetTeamoverviews> GetTeamoverview(@Query("userid") int id);
//
//    @GET("api/UsersTeam/GetweekPointcount")
//    Observable<GetweekPointcounts> GetweekPointcount(@Query("userid") int id);
//
//    @GET("api/UsersTeam/GetweekPointcount")
//    Observable<GetweekPointcounts> GetweekPointcountwithweek(@Query("userid") int id, @Query("Weekid") int Lang);
//
//    @POST("/api/UsersTeam/SaveUserTeam")
//    Observable<LeadPartners> SaveAllplayersforteam(@Body SaveallaPlayersdata data);
//
//    @POST("api/UsersTeam/SaveFreeHit")
//    Observable<LeadPartners> FreehitSaveteam(@Body SaveallaPlayersdata data);
//
//
//    @POST("api/Record/DetailsByTeam")
//    Observable<GetPlayerwith> GetPlayerwith(@Query("Teamid") int id);
//
//
//    @GET("api/Events/GeFormStats")
//    Observable<GetlatformGuides> GetlatformGuide(@Query("Eventid") int id);
//
//
//    @GET("api/Stats/GetTopPlayerPass")
//    Observable<GetTopPlayerPassandalls> GetTopPlayerPassandall(@Query("category") String cat, @Query("Lang") int Lang);
//
//    @GET("api/Stats/GetTopTeamStats")
//    Observable<GetTopTeamStatss> GetTopTeamStats(@Query("category") String cat, @Query("Lang") int Lang);
//
//    @GET("api/Events/GeFormGuide")
//    Observable<GeFormGuidebothteam> GeFormGuides(@Query("Eventid") int id);
//
//    @GET("api/Events/GeUpcomingFixure")
//    Observable<GeUpcomingFixures> GeUpcomingFixure();
//
//    @GET("api/UsersTeam/GetUserTeam")
//    Observable<GetUserSavedteams> GetUserTeam(@Query("UserId") int U_id, @Query("Lang") int Lang);
//
//    @GET("api/User/GetUserchipls")
//    Observable<GetUserchipl> GetUserchipls(@Query("userid") int U_id);
//
//    @POST("api/UsersTeam/SetTransfer")
//    Observable<SetTransferss> SetTransfer(@Query("userid") int U_id, @Query("inplayer") int inplayer, @Query("outplayer") int outplayer);
//
//    @GET("api/League/GetLeague")
//    Observable<GetLeagues> GetLeague(@Query("Leaguecode") String L_code);
//
//
//    @GET("api/UsersTeam/GetUserTeamList")
//    Observable<GetUserTeamLists> GetUserTeamList(@Query("UserId") int U_id, @Query("Lang") int Lang);
//
//    @GET("api/UsersTeam/GetPlayerDetails")
//    Observable<GetPlayerDetailswithLists> GetPlayerDetailswithList(@Query("playerid") int U_id, @Query("Lang") int Lang);
//
//
//    @POST("api/User/varifyOTP")
//    Observable<Verifyotps> varifyOTP(@Query("Mobile") String Mobile, @Query("OTP") String OTP, @Query("Macaddress") String Macaddress);
//
//
//    @GET("api/UsersTeam/GetUserTeam")
//        Observable<GetUserSavedteams> GetUserTeambyGameweek(@Query("UserId") int U_id, @Query("Weekid") int Gameweek);
//
//    @GET("api/Events/GeUpcomingFixure")
//    Observable<GeUpcomingFixures> GeUpcomingFixureandresult(@Query("weekid") int Gameweek);
//
//    @POST("api/UsersTeam/SetTriplCaptain")
//    Observable<Bentchboost> SetTriplCaptain(@Query("userid") int U_id);
//
//
//    @POST("api/UsersTeam/SetBanchboot")
//    Observable<Bentchboost> SetBanchboot(@Query("userid") int U_id);
//
//
//    @POST("api/User/SendLeagueCode")
//    Observable<Emaildata> SendLeagueCodeEmail(@Body List<Emails> data, @Query("userid") int U_id, @Query("code") String code, @Query("LeagueName") String LeagueName);
//
//
//    @GET("api/PlayerProfile/PlayerStats")
//    Observable<GetPlayerStat> GetPlayerStats(@Query("playerid") int U_id);
//
//
//    @GET("api/PlayerProfile/TeamStats")
//    Observable<TeamDetailsdata> GetTeamStatsteamdetils(@Query("Teamid") int U_id);
//
//
//
//    @GET("api/PlayerProfile/GetPlayerStats")
//    Observable<GetPlayerStats> GetPlayerStats(@Query("Playerid") int P_id, @Query("Lang") int Lang);
//
//    @POST("api/UsersTeam/SetWildcard")
//    Observable<Playerinoutdata> SetWildcard(@Body Inplayerout data);
//
//
//    @GET("api/UsersTeam/CancelChips")
//    Observable<CancelChips> CancelChips(@Query("userid") int U_id, @Query("chipid") int chipid);     //2 for banthboost Cancel , 3 triplecaption Cancel
//
//
//
//
//    @POST("api/User/ForgutPassword")
//    Observable<Forgot> ForgutPassword(@Query("mobileno") String Mob);
//
//    @POST("api/User/ResetPassword")
//    Observable<Forgot> ResetPassword(@Query("userid") int U_id, @Query("password") String Pasword);


}
