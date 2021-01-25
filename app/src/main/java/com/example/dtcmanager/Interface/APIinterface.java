package com.example.dtcmanager.Interface;

import com.example.dtcmanager.ModelClass.AddEmploye.AddEmployee;
import com.example.dtcmanager.ModelClass.AddLocation.AddLocation;
import com.example.dtcmanager.ModelClass.AddProject.AddProject;
import com.example.dtcmanager.ModelClass.AddVechile.AddVechile;
import com.example.dtcmanager.ModelClass.AddVechileImage.AddVechileImage;
import com.example.dtcmanager.ModelClass.ChangeStatus.ChangeStatus;
import com.example.dtcmanager.ModelClass.DailyReportDeatil.DailyReportDetail;
import com.example.dtcmanager.ModelClass.EditEmloyee.EditEmployee;
import com.example.dtcmanager.ModelClass.EditLocation.EditLocation;
import com.example.dtcmanager.ModelClass.EditVehicle.EditVehicle;
import com.example.dtcmanager.ModelClass.EmployeeAttendanceModel.GetEmployeeAttendance;
import com.example.dtcmanager.ModelClass.EmployeeDailyReportModel.GetEmployeeDailyReport;
import com.example.dtcmanager.ModelClass.EmployeeDetailMax.EmployeeDetail;
import com.example.dtcmanager.ModelClass.EmployeeDetailMax.EmployeeDetail_;
import com.example.dtcmanager.ModelClass.GetAllEmployee.GetAllEmploye;
import com.example.dtcmanager.ModelClass.GetAllEmployeeAttendence.GetAllEmployeeAttendence;
import com.example.dtcmanager.ModelClass.GetAllEmployeecheckOutByDate.GetAllEmployeeCheckOutByDate;
import com.example.dtcmanager.ModelClass.GetAllEmployeecheckout.GetAllEmployeecheckOutTime;
import com.example.dtcmanager.ModelClass.GetAllLocation.GettAllLocation;
import com.example.dtcmanager.ModelClass.GetAllNotification.GetNotificationManager;
import com.example.dtcmanager.ModelClass.GetAllProject.GetAllProject;
import com.example.dtcmanager.ModelClass.GetAllVacations.GetAllVacations;
import com.example.dtcmanager.ModelClass.GetAllVehcile.GetAllVechile;
import com.example.dtcmanager.ModelClass.GetDailyReport.GetDailyReport;
import com.example.dtcmanager.ModelClass.GetEmployeeAttendenceByDate.GetAttendenceByDate;
import com.example.dtcmanager.ModelClass.GetEmployeeDetail.GetEmployeeDetail;
import com.example.dtcmanager.ModelClass.GetEmployeeReportByDate.GetDailyReportByDate;
import com.example.dtcmanager.ModelClass.GetLocationDetail.GetLocationDetail;
import com.example.dtcmanager.ModelClass.GetProjectDetail.GetProjectDetail;
import com.example.dtcmanager.ModelClass.GetReportDetail.GetReportDetail;
import com.example.dtcmanager.ModelClass.Notification.Notification;
import com.example.dtcmanager.ModelClass.RemoveEmployee.RemoveEmployee;
import com.example.dtcmanager.ModelClass.RemoveLocation.RemoveLocation;
import com.example.dtcmanager.ModelClass.RemoveProject.RemoveProject;
import com.example.dtcmanager.ModelClass.Removevehicle.Removevehcile;
import com.example.dtcmanager.ModelClass.SignIn.ManagerId;
import com.example.dtcmanager.ModelClass.SignIn.SignIn;
import com.example.dtcmanager.ModelClass.UpdateProject.UpdateProject;
import com.example.dtcmanager.ModelClass.UploadID.UploadID;
import com.example.dtcmanager.ModelClass.UploadJoingImage.UploadJoingImages;
import com.example.dtcmanager.ModelClass.UploadJoiningFile.UploadJoingFile;
import com.example.dtcmanager.ModelClass.UploadPassport.UploadPassport;
import com.example.dtcmanager.ModelClass.UploadProjectContract.UploadprojectContract;
import com.example.dtcmanager.ModelClass.UploadSchdualProject.Uploadprojectschedul;
import com.example.dtcmanager.ModelClass.VehicleDetailMax.GetVehicleDeailMy;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface APIinterface {

    @POST("Sign_In.php")
    Call<SignIn> SignIn(
            @Query("user_name") String user_name,
            @Query("password") String password,
            @Query("imei_number") String imei_number
    );

    @POST("update_login_status.php")
    Call<ManagerId> updateloginstatus(
            @Query("manager_id") String manager_id,
            @Query("imei_number") String imei_number
    );

    @POST("get_all_employee.php")
    Call<GetAllEmploye> GetAllEmployee(

            @Query("manager_id") String manager_id
    );

    @POST("add_location.php")
    Call<AddLocation> AddLocation(

            @Query("name") String name,
            @Query("title") String title,
            @Query("manager_id") String manager_id,
            @Query("latitudes") String latitudes,
            @Query("longitudes") String longitudes

    );

    @POST("get_all_location.php")
    Call<GettAllLocation> GetAllLocation(
            @Query("manager_id") String manager_id
    );

    @FormUrlEncoded
    @POST("add_employee.php")
    Call<AddEmployee> AddEmployee(
            @Query("manager_id") String manager_id,
            @Query("user_name") String user_name,
            @Query("password") String password,
            @Query("position") String position,
            @Query("phone") String phone,
            @Query("basic_salary") String basic_salary,
            @Query("expenses") String expenses,
            @Query("over_time") String over_time,
            @Query("unique_id") String unique_id,
            @Query("end_date") String end_date,
            @Query("passport_no") String passport_no,
            @Query("passport_end_date") String passport_end_date,
            @Query("joining_date") String joining_date,
            @Query("contract_end_date") String contract_end,
            @Field("sub_emp_id[]") List<String> sub_emp_id,
            @Query("profile_image") String profile_image,
            @Query("device_token") String device_token,
            @Query("file_Id") String file_Id,
            @Query("file_Joining") String file_Joining,
            @Query("file_Passport") String file_Passport,
            @Query("file_image") String file_image,
            @Query("l_status") String l_status
    );


    @Multipart
    @POST("Upload_vehicle_image.php")
    Call<AddVechileImage> AddVechileImage(
            @Query("id") String id,
            @Part MultipartBody.Part picture
    );


    @FormUrlEncoded
    @POST("add_vehicle.php")
    Call<AddVechile> AddVechile(
            @Query("manager_id") String manager_id,
            @Query("vehicle_number") String vehicle_number,
            @Query("vehicle_name") String vehicle_name,
            @Query("model") String model,
            @Query("kilometers") String kilometers,
            @Query("insurance_date_start") String insurance_date_start,
            @Query("insurance_date_end") String insurance_date_end,
            @Query("license_date_end") String license_date_end,
            @Query("examination_date") String examination_date,
            @Query("employee_id") String employee_id,
            @Query("image") String image,
            @Field("location_id[]") ArrayList<String> location_id
            // @Query("child_id") String child_id
    );


    @POST("get_all_vehicles.php")
    Call<GetAllVechile> GetAllVechile(

            @Query("manager_id") String manager_id
    );
    @POST("remove_vehicle.php")
    Call<Removevehcile> Removevehcile(
            @Query("id") String id
    );

    @POST("remove_location.php")
    Call<RemoveLocation> RemoveLocation(
            @Query("id") String id
    );


    @POST("remove_employee.php")
    Call<RemoveEmployee> RemoveEmployee(
            @Query("id") String id
    );

    @POST("get_all_projects.php")
    Call<GetAllProject> GetAllProject(
            @Query("manager_id") String manager_id
    );

    @POST("remove_project.php")
    Call<RemoveProject> RemoveProject(
            @Query("id") String id
    );

    @FormUrlEncoded
    @POST("add_project.php")
    Call<AddProject> AddProject(
            @Query("manager_id") String manager_id,
            @Query("name") String name,
            @Query("value") String value,
            @Query("start_date") String start_date,
            @Query("dead_line") String dead_line,
            @Query("location_id") String ProjectLocationId,
            @Query("schedule_file") String schedule_file,
            @Query("contract_file") String contract_file,
            @Field("vehicle_id[]") ArrayList<String> vehicle_id,
            @Field("employee_id[]") ArrayList<String> employee_id
            // @Query("child_id") String child_id
    );

    @Multipart
    @POST("Upload_project_schedule_file.php")
    Call<Uploadprojectschedul> UploadSchedual(
            @Query("id") String id,
            @Part MultipartBody.Part files
    );
    @Multipart
    @POST("Upload_project_contract_file.php")
    Call<UploadprojectContract> UploadContract(
            @Query("id") String id,
            @Part MultipartBody.Part files
    );

    @FormUrlEncoded
    @POST("edit_project.php")
    Call<UpdateProject> UpdateProject(
            @Query("id") String id,
            @Query("name") String name,
            @Query("value") String value,
            @Query("start_date") String start_date,
            @Query("dead_line") String dead_line,
            @Query("location_id") String ProjectLocationId,
            @Field("vehicle_id[]") List<String> vehicleIdList,
            @Field("employee_id[]") List<String> employeeidList
            // @Query("child_id") String child_id
    );

    @POST("edit_location.php")
    Call<EditLocation> EditLocation(
            @Query("name") String name,
            @Query("id") String id,
            @Query("title") String title,
            @Query("latitudes") String latitudes,
            @Query("longitudes") String longitudes

    );

    @Multipart
    @POST("Upload_Id.php")
    Call<UploadID> UploadId(
            @Query("emp_id") String emp_id,
            @Part MultipartBody.Part files
    );
    @Multipart
    @POST("Upload_passport.php")
    Call<UploadPassport> UploadPassport(
            @Query("emp_id") String emp_id,
            @Part MultipartBody.Part files
    );
    @Multipart
    @POST("Upload_joining_file.php")
    Call<UploadJoingFile> UploadJoiningfile(
            @Query("emp_id") String emp_id,
            @Part MultipartBody.Part files
    );
    @Multipart
    @POST("Upload_joining_image.php")
    Call<UploadJoingImages> UploadJoingImage(
            @Query("emp_id") String emp_id,
            @Part MultipartBody.Part picture
    );

    @FormUrlEncoded
    @POST("edit_employee.php")
    Call<EditEmployee> Editemployee(
            @Query("id") String id,
            @Query("user_name") String user_name,
            @Query("password") String password,
            @Query("position") String position,
            @Query("phone") String phone,
            @Query("basic_salary") String basic_salary,
            @Query("expenses") String expenses,
            @Query("over_time") String over_time,
            @Query("unique_id") String unique_id,
            @Query("end_date") String end_date,
            @Query("passport_no") String passport_no,
            @Query("passport_end_date") String passport_end_date,
            @Query("joining_date") String joining_date,
            @Query("contract_end_date") String contract_end_date,
            @Field("sub_emp_id[]") List<String> sub_emp_id
    );


    @FormUrlEncoded
    @POST("edit_vehicle.php")
    Call<EditVehicle> EditVehcile(
            @Query("id") String id,
            @Query("vehicle_number") String vehicle_number,
            @Query("vehicle_name") String vehicle_name,
            @Query("model") String model,
            @Query("kilometers") String kilometers,
            @Query("insurance_date_start") String insurance_date_start,
            @Query("insurance_date_end") String insurance_date_end,
            @Query("license_date_end") String license_date_end,
            @Query("examination_date") String examination_date,
            @Query("employee_id") String employee_id,
            @Field("location_id[]") ArrayList<String> location_id
            // @Query("child_id") String child_id
    );

    @POST("get_location_details.php")
    Call<GetLocationDetail> LocationDetail(
            @Query("id") String id
    );

    @POST("get_vehicle_details.php")
    Call<GetVehicleDeailMy> GetVehcileDetail(
            @Query("id") String id
    );
    @POST("get_employee_detail.php")
    Call<GetEmployeeDetail> GetEmployeeDetail(
            @Query("id") String id
    );
    @POST("get_vehicle_details.php")
    Call<GetVehicleDeailMy> VehicleDetail(
            @Query("id") String id
    );

    @POST("get_employee_detail.php")
    Call<EmployeeDetail> EmployeeDetail(
            @Query("id") String id
    );


    @POST("get_employee_detail.php")
    Call<EmployeeDetail> EmployeeDetailMax(
            @Query("id") String id
    );

    @POST("update_l_status.php")
    Call<EmployeeDetail_> updatel_status(
            @Query("emp_id") String emp_id
    );

    @POST("get_employee_attendence_report.php")
    Call<GetEmployeeAttendance> employeeAttendance(
            @Query("emp_id") String id
    );

    @POST("get_employee_daily_report.php")
    Call<GetEmployeeDailyReport> employeeDailyReport(
            @Query("emp_id") String id,
            @Query("date") String date

    );

    @POST("get_all_vacation_request.php")
    Call<GetAllVacations> GetAllVacation(
            @Query("manager_id") String manager_id
    );
    @POST("change_status.php")
    Call<ChangeStatus> changeStatus(
            @Query("id") String id,
            @Query("emp_id") String emp_id,
            @Query("status") String status
    );


    @POST("get_all_employee_check_out_time.php")
    Call<GetAllEmployeecheckOutTime> GetAllEmployeeCheckout(
            @Query("manager_id") String manager_id,
            @Query("date") String date

    );
    @POST("get_all_employee_attendence_report.php")
    Call<GetAllEmployeeAttendence> GetAllEmployeeAttendence(
            @Query("manager_id") String manager_id,
            @Query("date") String date


    );

    @POST("get_project_details.php")
    Call<GetProjectDetail> GetProjectDetail(
            @Query("id") String id
    );

    @POST("update_manager_device_token.php")
    Call<ResponseBody> updateToken(
            @Query("id") String id,
            @Query("device_token") String token
    );

    @POST("get_report_detail.php")
    Call<GetReportDetail> GetReportDetail(
            @Query("id") String id
    );

    @POST("get_report_detail.php")
    Call<DailyReportDetail> DailyReportDetail(
            @Query("id") String id
    );

    @POST("manager_notifications.php")
    Call<Notification> CreateNotification(
            @Query("manager_id") String manager_id,
            @Query("employee_id") String employee_id,
            @Query("title") String title,
            @Query("notifications") String notifications,
            @Query("date") String date,
            @Query("time") String time
    );

    @POST("get_notifications.php")
    Call<GetNotificationManager> GetAllNotification(
            @Query("manager_id") String manager_id
    );


    @POST("get_daily_report.php")
    Call<GetDailyReport> GetDailyReport(
            @Query("manager_id") String manager_id,
            @Query("date") String date
     );

    @POST("get_employee_report_by_date.php")
    Call<GetDailyReportByDate> GetDailyReportByDate(
            @Query("manager_id") String manager_id,
            @Query("from_date") String from_date,
            @Query("to_date") String to_date
    );

    @POST("get_all_employee_attendence_report_by_date.php")
    Call<GetAttendenceByDate> GetEmployeeAttendenceByDate(
            @Query("manager_id") String manager_id,
            @Query("from_date") String from_date,
            @Query("to_date") String to_date
    );


    @POST("get_all_check_out_time_by_date.php")
    Call<GetAllEmployeeCheckOutByDate> GetAllEmployeeCheckOutByDate(
            @Query("manager_id") String manager_id,
            @Query("from_date") String from_date,
            @Query("to_date") String to_date
    );
}
