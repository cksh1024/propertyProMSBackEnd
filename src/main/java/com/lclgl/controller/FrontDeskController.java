package com.lclgl.controller;

import com.lclgl.pojo.Customer;
import com.lclgl.pojo.StaffInfo;
import com.lclgl.service.FrontDeskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/lclgl")
public class FrontDeskController {

    @Autowired
    private FrontDeskService frontDeskService;

    @PostMapping("/getStaffs")
    public Map<String, Object> getStaffs() {
        return frontDeskService.getStaffs();
    }

    @PostMapping("/getStatusLevels")
    public Map<String, Object> getStatusLevels(){return frontDeskService.getStatusLevels();}

    @PostMapping("/addStaff")
    public Map<String, Object> addStaff(StaffInfo staffInfo){return frontDeskService.addStaff(staffInfo);}

    @PostMapping("/modifyStaff")
    public Map<String, Object> modifyStaff(String staffBirthday,
                                           String staffName,
                                           String staffSex,
                                           String statusId,
                                           String staffPhone,
                                           String staffQq,
                                           String staffEmail,
                                           String staffNaplace,
                                           String staffIdnum,
                                           String staffNation,
                                           String staffBirplace,
                                           String staffResidence,
                                           String staffEdu,
                                           String staffJob,
                                           String staffId){
        StaffInfo staffInfo = new StaffInfo();

        staffInfo.setStaffBirplace(staffBirplace);
        staffInfo.setStaffEdu(staffEdu);
        staffInfo.setStaffEmail(staffEmail);
        staffInfo.setStaffName(staffName);
        staffInfo.setStaffSex(staffSex);
        staffInfo.setStatusId(Integer.parseInt(statusId));
        staffInfo.setStaffPhone(staffPhone);
        staffInfo.setStaffQq(Integer.parseInt(staffQq));
        staffInfo.setStaffNaplace(staffNaplace);
        staffInfo.setStaffIdnum(staffIdnum);
        staffInfo.setStaffNation(staffNation);
        staffInfo.setStaffResidence(staffResidence);
        staffInfo.setStaffJob(staffJob);
        staffInfo.setUserId(Integer.parseInt(staffId));


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentTime = simpleDateFormat.format(new Date());
        String[] currentSplit = currentTime.split("-");
        String[] birthdaySplit = staffBirthday.split("-");
        staffInfo.setStaffBirthday(new Date(Integer.parseInt(birthdaySplit[0])-1900, Integer.parseInt(birthdaySplit[1])-1, Integer.parseInt(birthdaySplit[2])));
        int age = Integer.parseInt(currentSplit[0]) - Integer.parseInt(birthdaySplit[0]);
        if (Integer.parseInt(currentSplit[1]) < Integer.parseInt(birthdaySplit[1])) {
            age--;
        } else if (Integer.parseInt(currentSplit[1]) == Integer.parseInt(birthdaySplit[1])) {
            if (Integer.parseInt(currentSplit[2]) < Integer.parseInt(birthdaySplit[2])) {
                age--;
            }
        }
        staffInfo.setStaffAge(age);
        if (staffInfo.getStaffPic() == null) staffInfo.setStaffPic("");
        return frontDeskService.modifyStaff(staffInfo);
    }

    @PostMapping("/getStaffById")
    public Map<String, Object> getStaffById(String staffId) {
        return frontDeskService.getStaffById(Integer.parseInt(staffId));
    }

    @PostMapping("/getCustomers")
    public Map<String, Object> getCustomer(Customer customer){return frontDeskService.getCustomers(customer);}

    @PostMapping("/getCustomerById")
    public Map<String, Object> getCustomerById(String cusId) {
        return frontDeskService.getCustomerById(Integer.parseInt(cusId));
    }

    @PostMapping("/modifyCustomer")
    public Map<String, Object> modifyCustomer(Customer customer) {
        return frontDeskService.modifyCustomer(customer);
    }

    @PostMapping("/delCustomer")
    public Map<String, Object> delCustomer(String cusId) {
        return frontDeskService.delCustomer(Integer.parseInt(cusId));
    }

    @PostMapping("/addCustomer")
    public Map<String, Object> addCustomer(Customer customer) {
        return frontDeskService.addCustomer(customer);
    }
}
