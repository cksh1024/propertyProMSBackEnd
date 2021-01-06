package com.lclgl.service;

import com.lclgl.dao.*;
import com.lclgl.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.util.*;

/**
 * @author cksh
 * @create 2021-01-04 11:30
 */
@Service
public class SuperUserService {

    @Autowired
    private TeamMapper teamMapper;
    @Autowired
    private StaffInfoMapper staffInfoMapper;
    @Autowired
    private ProInfoMapper proInfoMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private ProStageMapper proStageMapper;
    @Autowired
    private ProJournalMapper proJournalMapper;
    @Autowired
    private StatusLevelMapper statusLevelMapper;
    @Autowired
    private GradeMapper gradeMapper;
    @Autowired
    private SalaryMapper salaryMapper;

    public Map<String, Object> getTeamInfos() {
        HashMap<String, Object> map = new HashMap<>();

        List<Team> teams = teamMapper.getTeamInfos();
        ArrayList<Map<String, Object>> teamInfos = new ArrayList<>();

        for (Team team : teams) {
            HashMap<String, Object> temp = new HashMap<>();
            temp.put("teamId", team.getTeamId());
            temp.put("teamName", team.getTeamName());
            temp.put("teamType", team.getTeamType());
            List<String> teamManagers = teamMapper.getTeamManagers(team.getTeamId());
            temp.put("teamManagers", teamManagers);
            teamInfos.add(temp);
        }

        map.put("teamInfos", teamInfos);

        return map;
    }

    public List<StaffInfo> filtrateUndistributedManagers(List<StaffInfo> staffInfos) {
        ArrayList<StaffInfo> staffs = new ArrayList<>();

        for (StaffInfo staffInfo : staffInfos) {
            if (staffInfo.getTeamId() == 0) {
                staffs.add(staffInfo);
            }
        }

        return staffs;
    }

    public Map<String, Object> getManagersByTeamType(String teamType) {

        HashMap<String, Object> map = new HashMap<>();

        if ("渲染团队".equals(teamType)) {
            map.put("managers", filtrateUndistributedManagers(teamMapper.getManagersByTeamType("渲染主管")));
        } else if ("建模团队".equals(teamType)) {
            map.put("managers", filtrateUndistributedManagers(teamMapper.getManagersByTeamType("模型主管")));
        } else if ("后期团队".equals(teamType)) {
            map.put("managers", filtrateUndistributedManagers(teamMapper.getManagersByTeamType("后期主管")));
        } else {
            map.put("msg", "请选择正确的团队类型！");
        }

        return map;

    }

    public Map<String, Object> addTeam(String teamName, String teamType, int managerId) {
        HashMap<String, Object> map = new HashMap<>();

        Team team = new Team();
        team.setTeamName(teamName);
        team.setTeamType(teamType);
        int r = teamMapper.addTeam(team);
        int r1 = staffInfoMapper.addTeamToManager(managerId);
        if (r == 1 && r1 == 1) {
            map.put("status", 1);
            map.put("msg", "添加成功！");
        } else {
            map.put("status", -1);
            map.put("msg", "添加失败！");
        }

        return map;
    }

    public Map<String, Object> getPros() {
        HashMap<String, Object> map = new HashMap<>();

        List<ProInfo> pros = proInfoMapper.getPros();
        ArrayList<Map<String, Object>> proInfos = new ArrayList<>();
        for (ProInfo proInfo : pros) {
            HashMap<String, Object> temp = new HashMap<>();
            temp.put("proId", proInfo.getProId());
            temp.put("proName", proInfo.getProName());
            temp.put("cusId", proInfo.getCusId());
            temp.put("cusName", customerMapper.getCustomerByCustomerId(proInfo.getCusId()).getCusName());
            temp.put("proCreate", proInfo.getProCreate());
            temp.put("proCondition", proInfo.getProCondition());
            if ("建模中".equals(proInfo.getProCondition())) {
                temp.put("proCondition_number", 1);
            } else if ("渲染中".equals(proInfo.getProCondition())) {
                temp.put("proCondition_number", 2);
            } else if ("后期中".equals(proInfo.getProCondition())) {
                temp.put("proCondition_number", 3);
            } else {
                temp.put("proCondition_number", 4);
            }
            temp.put("proEndtime", proInfo.getProEndtime());
            proInfos.add(temp);
        }
        map.put("proInfos", proInfos);
        return map;
    }

    public Map<String, Object> getProStages() {
        HashMap<String, Object> map = new HashMap<>();
        List<ProStage> proStages = proStageMapper.proStageList();
        ArrayList<Map<String, Object>> proStageInfos = new ArrayList<>();
        for (ProStage proStage : proStages) {
            HashMap<String, Object> temp = new HashMap<>();
            temp.put("proId", proStage.getProId());
            temp.put("proName", proInfoMapper.getProById(proStage.getProId()).getProName());
            temp.put("stageType", proStage.getStageType());
            temp.put("stageStateTime", proStage.getStageStateTime());
            temp.put("stageCondition", proStage.getStageCondition());
            temp.put("stateEndtime", proStage.getStageEndtime());
            temp.put("stagePay", proStage.getStagePay());
            proStageInfos.add(temp);
        }
        map.put("proStageInfos", proStageInfos);
        return map;
    }

    public Map<String, Object> updateStagePay(double stagePay, int proId, String stageType) {
        HashMap<String, Object> map = new HashMap<>();
        HashMap<String, Object> updateInfo = new HashMap<>();
        updateInfo.put("stagePay", stagePay);
        updateInfo.put("proId", proId);
        updateInfo.put("stageType", stageType);
        int r = proStageMapper.updateStagePay(updateInfo);
        if (r == 1) {
            map.put("status", 1);
            map.put("msg", "修改成功！");
        } else {
            map.put("status", -1);
            map.put("msg", "修改失败！");
        }
        return map;
    }

    public Map<String, Object> getProJournals() {
        HashMap<String, Object> map = new HashMap<>();
        List<ProJournal> journals = proJournalMapper.getJournals();
        ArrayList<Map<String, Object>> proJournals = new ArrayList<>();
        for (ProJournal proJournal : journals) {
            HashMap<String, Object> temp = new HashMap<>();
            temp.put("jourTime", proJournal.getJourTime());
            temp.put("staffName", staffInfoMapper.getStaff(proJournal.getUserId()).getStaffName());
            temp.put("proName", proInfoMapper.getProById(proJournal.getProId()).getProName());
            temp.put("jourType", proJournal.getJourType());
            temp.put("jourRemark", proJournal.getJourRemark());
            temp.put("fileName", proJournal.getFileName());
            proJournals.add(temp);
        }
        map.put("journals", proJournals);
        return map;
    }

    public Map<String, Object> getSalaryInfo() {
        HashMap<String, Object> map = new HashMap<>();
        List<StaffInfo> staffs = staffInfoMapper.getStaffs();
        ArrayList<Map<String, Object>> salaryInfo = new ArrayList<>();
        for (StaffInfo staffInfo : staffs) {
            HashMap<String, Object> temp = new HashMap<>();

            temp.put("userID", staffInfo.getUserId());

            temp.put("userName", staffInfo.getStaffName());

            //工资发放时间
//            DateFormat df = DateFormat.getDateTimeInstance();
//            temp.put("salaryTime", df.format(new Date()));
            temp.put("salaryTime", "2020-12-31");
            //基础工资
            double statusSalary = statusLevelMapper.getStatusLevelById(staffInfo.getStatusId()).getStatusSalary();
            temp.put("statusSalary", statusSalary);
            //上班天数
            int salaryDays = 25;
            temp.put("salaryDays", salaryDays);
            //全勤奖奖金
            double salaryBonus = 0;
            if (salaryDays > 22) salaryBonus = 500;
            temp.put("salaryBonus", salaryBonus);
            //绩效奖金
            double salaryAll = 0;
            HashMap<String, Object> infoOfStagePay = new HashMap<>();
            infoOfStagePay.put("startTime", "2020-12-01");
            infoOfStagePay.put("endTime", "2020-12-31");
            infoOfStagePay.put("teamId", staffInfo.getTeamId());
            List<ProStage> proStages = proInfoMapper.getStagePayOfStaff(infoOfStagePay);
            for (ProStage proStage : proStages) salaryAll += proStage.getStagePay();
            temp.put("salaryAll", salaryAll);
            //个人奖金
            HashMap<String, Object> gradeInfo = new HashMap<>();
            gradeInfo.put("staffId", staffInfo.getUserId());
            gradeInfo.put("startTime", "2020-12-01");
            gradeInfo.put("endTime", "2020-12-31");
            Grade grade = gradeMapper.getCurrentGradeById(gradeInfo);
            int salaryPersonal = 0;
            if (grade == null) salaryPersonal = 0;
            else if (grade.getGradeLevel() == 1) salaryPersonal = -500;
            else if (grade.getGradeLevel() <= 3) salaryPersonal = 0;
            else if (grade.getGradeLevel() == 4) salaryPersonal = 500;
            else salaryPersonal = 800;
            temp.put("salaryPersonal", salaryPersonal);
            //总发放工资
            double salaryPractical = statusSalary/22*salaryDays + salaryBonus + salaryAll + salaryPersonal;
            temp.put("salaryPractical", Math.round(salaryPractical*0.95));
            //税额
            temp.put("salaryTax", Math.round(salaryPractical*0.05));

            salaryInfo.add(temp);
        }
        map.put("salaryInfo", salaryInfo);
        return map;
    }

    public Map<String, Object> setSalaryInfo(Salary salary) {
        HashMap<String, Object> map = new HashMap<>();
        HashMap<String, Object> salaryInfo = new HashMap<>();
        salaryInfo.put("userId", salary.getUserId());
        salaryInfo.put("startTime", "2020-12-01");
        salaryInfo.put("endTime", "2020-12-31");
        Salary salary1 = salaryMapper.getSalaryByIdAndTime(salaryInfo);
        int r = 0;
        if (salary1 == null) {
            r = salaryMapper.addSalary(salary);
        } else {
            r = salaryMapper.updateSalary(salary);
        }
        if (r == 1) {
            map.put("status", 1);
            map.put("msg", "修改成功！");
        } else {
            map.put("status", -1);
            map.put("msg", "修改失败！");
        }
        return map;
    }

    public Map<String, Object> getStaffs() {
        HashMap<String, Object> map = new HashMap<>();
        List<Salary> salarys = salaryMapper.getSalarys();
        ArrayList<Map<String, Object>> salaryInfos = new ArrayList<>();
        for (Salary salary : salarys) {
            HashMap<String, Object> temp = new HashMap<>();
            temp.put("userID", salary.getUserId());
            temp.put("userName", staffInfoMapper.getStaff(salary.getUserId()).getStaffName());
            DateFormat df = DateFormat.getDateTimeInstance();
            temp.put("salaryTime", df.format(salary.getSalaryTime()));
            temp.put("statusSalary", statusLevelMapper.getStatusLevelById(staffInfoMapper.getStaff(salary.getUserId()).getStatusId()).getStatusSalary());
            temp.put("salaryDays", salary.getSalaryDays());
            temp.put("salaryBonus", salary.getSalaryBonus());
            temp.put("salaryAll", salary.getSalaryAll());
            temp.put("salaryPersonal", salary.getSalaryPersonal());
            temp.put("salaryPractical", salary.getSalaryPractical());
            temp.put("salaryTax", salary.getSalaryTax());
            salaryInfos.add(temp);
        }
        map.put("salaryInfos", salaryInfos);
        return map;
    }

    public Map<String, Object> getStaffEvaluate() {

        HashMap<String, Object> map = new HashMap<>();

        List<StaffInfo> staffs = staffInfoMapper.getStaffs();
        ArrayList<Map<String, Object>> staffEvaluate = new ArrayList<>();

        for (StaffInfo staffInfo : staffs) {
            HashMap<String, Object> temp = new HashMap<>();
            temp.put("userId", staffInfo.getUserId());
            temp.put("userName", staffInfo.getStaffName());
            HashMap<String, Object> gradeInfo = new HashMap<>();
            gradeInfo.put("staffId", staffInfo.getUserId());
            gradeInfo.put("startTime", "2020-12-01");
            gradeInfo.put("endTime", "2020-12-31");
            Grade currentGrade = gradeMapper.getCurrentGradeById(gradeInfo);
            if (currentGrade == null) temp.put("value", 0);
            else temp.put("value", currentGrade.getGradeLevel());
            staffEvaluate.add(temp);
        }
        map.put("staffEvaluate", staffEvaluate);

        return map;

    }

    public Map<String, Object> getHistoryStaffEvaluate() {
        HashMap<String, Object> map = new HashMap<>();
        List<Grade> historyGrade = gradeMapper.getHistoryGrade();
        ArrayList<Map<String, Object>> gradeInfos = new ArrayList<>();
        for (Grade grade : historyGrade) {
            HashMap<String, Object> temp = new HashMap<>();
            temp.put("userId", grade.getUserId());
            temp.put("userName", staffInfoMapper.getStaff(grade.getUserId()).getStaffName());
            temp.put("gradeLevel", grade.getGradeLevel());
            DateFormat df = DateFormat.getDateTimeInstance();
            temp.put("gradeTime", df.format(grade.getGradeTime()));
            gradeInfos.add(temp);
        }
        map.put("gradeInfos", gradeInfos);
        return map;
    }

    public Map<String, Object> setStaffEvaluate(List<Grade> objList) {

        HashMap<String, Object> map = new HashMap<>();

        int r = 0;
        for (Grade grade : objList) {
            HashMap<String, Object> gradeInfo = new HashMap<>();
            gradeInfo.put("staffId", grade.getUserId());
            gradeInfo.put("startTime", "2020-12-01");
            gradeInfo.put("endTime", "2020-12-31");
            Grade currentGrade = gradeMapper.getCurrentGradeById(gradeInfo);
            if (currentGrade == null) {
                r = gradeMapper.addGrade(grade);
                if (r != 1) {
                    map.put("status", -1);
                    map.put("msg", "添加失败！");
                    break;
                }
            } else {
                gradeInfo.put("gradeLevel", grade.getGradeLevel());
                r = gradeMapper.updateGrade(gradeInfo);
                if (r != 1) {
                    map.put("status", -1);
                    map.put("msg", "添加失败！");
                    break;
                }
            }
        }

        if (r == 1) {
            map.put("status", 1);
            map.put("msg", "修改成功！");
        }
        return map;

    }
}
