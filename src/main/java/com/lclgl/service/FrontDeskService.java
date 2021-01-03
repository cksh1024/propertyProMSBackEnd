package com.lclgl.service;

import com.lclgl.dao.CustomerMapper;
import com.lclgl.dao.StaffInfoMapper;
import com.lclgl.dao.StatusLevelMapper;
import com.lclgl.pojo.Customer;
import com.lclgl.pojo.StaffInfo;
import com.lclgl.pojo.StatusLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class FrontDeskService {

    private String defaultPic = "";

    @Autowired
    private StaffInfoMapper staffInfoMapper;
    @Autowired
    private StatusLevelMapper statusLevelMapper;
    @Autowired
    private CustomerMapper customerMapper;

    public Map<String, Object> getStaffs() {
        HashMap<String, Object> map = new HashMap<>();
        List<StaffInfo> staffs = staffInfoMapper.getStaffs();

        ArrayList<Map<String, Object>> staffInfos = new ArrayList<>();
        for (StaffInfo staff : staffs) {
            HashMap<String, Object> temp = new HashMap<>();
            temp.put("userId", staff.getUserId());
            StatusLevel statusLevel = statusLevelMapper.getStatusLevelById(staff.getStatusId());
            temp.put("statusType", statusLevel.getStatusType());
            temp.put("staffSex", staff.getStaffSex());
            temp.put("staffAge", staff.getStaffAge());
            temp.put("staffName", staff.getStaffName());
            temp.put("staffPic", staff.getStaffPic());
            temp.put("staffPhone", staff.getStaffPhone());
            temp.put("staffQq", staff.getStaffQq());
            temp.put("staffEmail", staff.getStaffEmail());
            temp.put("staffIdnum", staff.getStaffIdnum());
            temp.put("staffNaplace", staff.getStaffNaplace());
            temp.put("staffNation", staff.getStaffNation());
            temp.put("staffBirplace", staff.getStaffBirplace());
            temp.put("staffBirthday", staff.getStaffBirthday());
            temp.put("staffResidence", staff.getStaffResidence());
            temp.put("staffEdu", staff.getStaffEdu());
            temp.put("staffJob", staff.getStaffJob());
            staffInfos.add(temp);
        }
        map.put("staffs", staffInfos);
        return map;
    }
    public Map<String, Object> getStatusLevels() {
        HashMap<String, Object> map = new HashMap<>();
        List<StatusLevel> statusLevels = statusLevelMapper.getStatusLevels();
        map.put("statusLevels", statusLevels);
        map.put("status", 1);
        return map;
    }

    public Map<String, Object> addStaff(StaffInfo staffInfo) {
        HashMap<String, Object> map = new HashMap<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentTime = simpleDateFormat.format(new Date());
        String staffBirthday = simpleDateFormat.format(staffInfo.getStaffBirthday());
        String[] currentSplit = currentTime.split("-");
        String[] birthdaySplit = staffBirthday.split("-");
        int age = Integer.parseInt(currentSplit[0]) - Integer.parseInt(birthdaySplit[0]);
        if (Integer.parseInt(currentSplit[1]) < Integer.parseInt(birthdaySplit[1])) {
            age--;
        } else if (Integer.parseInt(currentSplit[1]) == Integer.parseInt(birthdaySplit[1])) {
            if (Integer.parseInt(currentSplit[2]) < Integer.parseInt(birthdaySplit[2])) {
                age--;
            }
        }
        staffInfo.setStaffAge(age);
        if (staffInfo.getStaffPic() == null) staffInfo.setStaffPic(defaultPic);
        int i = staffInfoMapper.addStaff(staffInfo);
        if (i == 1) map.put("status", 1);
        else map.put("status", -1);
        return map;
    }

    public Map<String, Object> getCustomers(Customer customer) {
        HashMap<String, Object> map = new HashMap<>();
        List<Customer> customers = customerMapper.getCustomers();
        map.put("customers", customers);
        return map;
    }

    public Map<String, Object> getCustomerById(int cusId) {
        HashMap<String, Object> map = new HashMap<>();

        Customer customer = customerMapper.getCustomerById(cusId);
        map.put("customer", customer);

        return map;
    }

    public Map<String, Object> modifyCustomer(Customer customer) {

        HashMap<String, Object> map = new HashMap<>();

        int i = customerMapper.modifyCustomer(customer);

        if (i == 1) {
            map.put("status", 1);
        } else {
            map.put("status", -1);
        }

        return map;
    }
    public Map<String, Object> delCustomer(int cusId) {

        HashMap<String, Object> map = new HashMap<>();

        int i = customerMapper.delCustomer(cusId);
        if (i == 1) {
            map.put("status", 1);
        } else {
            map.put("status", -1);
        }

        return map;
    }
    public Map<String, Object> addCustomer(Customer customer) {
        HashMap<String, Object> map = new HashMap<>();
        int i = customerMapper.addCustomer(customer);
        if (i == 1) map.put("status", 1);
        else map.put("status", -1);
        return map;
    }

    public Map<String, Object> modifyStaff(StaffInfo staffInfo) {

        HashMap<String, Object> map = new HashMap<>();
        int i = staffInfoMapper.modifyStaff(staffInfo);
        if (i == 1) map.put("status", 1);
        else map.put("status", -1);

        return map;

    }

    public Map<String, Object> getStaffById(int staffId) {
        HashMap<String, Object> map = new HashMap<>();

        StaffInfo staff = staffInfoMapper.getStaff(staffId);
        map.put("staffInfo", staff);

        return map;
    }
}
