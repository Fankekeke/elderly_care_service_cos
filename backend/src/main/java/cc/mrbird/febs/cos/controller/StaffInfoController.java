package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.service.CacheService;
import cc.mrbird.febs.common.service.RedisService;
import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.ServiceReserveInfo;
import cc.mrbird.febs.cos.entity.StaffInfo;
import cc.mrbird.febs.cos.entity.UserInfo;
import cc.mrbird.febs.cos.service.IMailService;
import cc.mrbird.febs.cos.service.IServiceReserveInfoService;
import cc.mrbird.febs.cos.service.IStaffInfoService;
import cc.mrbird.febs.system.service.UserService;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK
 */
@RestController
@RequestMapping("/cos/staff-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StaffInfoController {

    private final IStaffInfoService staffInfoService;

    private final IServiceReserveInfoService serviceReserveInfoService;

    private final UserService userService;

    private final CacheService cacheService;

    private final TemplateEngine templateEngine;

    private final IMailService iMailService;

    private final RedisService redisService;

    /**
     * 验证码验证
     *
     * @param validateCode
     * @return
     */
    @SneakyThrows
    @GetMapping("/verification/check")
    public R verificationCheck(String validateCode, String email) {
        //1:获取redis里面的验证码信息
        String code = redisService.get(email);
        //2:判断验证码是否正确
        if (!StringUtils.isEmpty(validateCode)) {
            validateCode = validateCode.toLowerCase();
            code = code.toLowerCase();
            if (validateCode.equals(code)) {
                // 删除key
                redisService.del(email);
                return R.ok(true);
            }
        }
        return R.ok(false);
    }

    /**
     * 发送注册邮件
     *
     * @param email
     * @return
     */
    @SneakyThrows
    @GetMapping("/register/email")
    public R sendRegisterEmail(String email) {
        // 判断邮箱是否重复
        int count = staffInfoService.count(Wrappers.<StaffInfo>lambdaQuery().eq(StaffInfo::getMail, email));
        if (count > 0) {
            return R.ok(false);
        }
        // 绘制随机字符
        String randomString = "";
        for (int i = 1; i <= 50; i++) {
            randomString = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
        }

        Context context = new Context();
        context.setVariable("today", DateUtil.formatDate(new Date()));
        context.setVariable("verifyCode", randomString);
        String emailContent = templateEngine.process("registerEmail", context);
        iMailService.sendHtmlMail(email, "账户验证", emailContent);
        // 将随机生成的验证码放入Redis中
        redisService.set(email, randomString);
        return R.ok(true);
    }

    /**
     * 分页获取志愿者信息
     *
     * @param page      分页对象
     * @param staffInfo 志愿者信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<StaffInfo> page, StaffInfo staffInfo) {
        return R.ok(staffInfoService.selectStaffPage(page, staffInfo));
    }

    /**
     * 审核志愿者信息
     *
     * @param staffId 志愿者ID
     * @param status  审核状态
     * @return 结果
     */
    @PutMapping("/auditStaff")
    public R auditStaff(Integer staffId, Integer status) {
        return R.ok(staffInfoService.update(Wrappers.<StaffInfo>lambdaUpdate().set(StaffInfo::getStatus, status).eq(StaffInfo::getId, staffId)));
    }

    /**
     * 查询用户信息详情【公告信息】
     *
     * @param id 主键ID
     * @return 结果
     */
    @GetMapping("/selectBulletinDetail/{id}")
    public R selectBulletinDetail(@PathVariable("id") Integer id) {
        return R.ok(staffInfoService.selectBulletinDetail(id));
    }

    /**
     * 获取志愿者列表
     *
     * @param enterpriseId 志愿者ID
     * @return 结果
     */
    @GetMapping("/queryStaffList")
    public R queryStaffList(@RequestParam(value = "enterpriseId") Integer enterpriseId) {
        return R.ok(staffInfoService.selectStaffList(enterpriseId));
    }

    /**
     * 获取志愿者列表
     *
     * @param staffId 志愿者ID
     * @return 结果
     */
    @GetMapping("/queryStaffList/staff")
    public R queryStaffListByStaff(@RequestParam(value = "staffId") Integer staffId) {
        StaffInfo staffInfo = staffInfoService.getOne(Wrappers.<StaffInfo>lambdaQuery().eq(StaffInfo::getUserId, staffId));
        return R.ok(staffInfoService.queryStaffListByStaff(staffInfo.getEnterpriseId(), staffInfo.getId()));
    }

    /**
     * 获取志愿者信息
     *
     * @return 结果
     */
    @GetMapping("/userInfo/detail/{userId}")
    public R queryStaffByUserId(@PathVariable("userId") Integer userId) {
        return R.ok(staffInfoService.queryStaffByUserId(userId));
    }

    /**
     * 获取志愿者信息
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(staffInfoService.selectStaffList(null));
    }

    /**
     * 获取志愿者详细信息
     *
     * @param id ID
     * @return 结果
     */
    @GetMapping("/detail/{id}")
    public R detail(@PathVariable("id") Integer id) {
        // 返回数据
        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>() {
            {
                put("user", null);
                put("order", Collections.emptyList());
            }
        };
        StaffInfo userInfo = staffInfoService.getOne(Wrappers.<StaffInfo>lambdaQuery().eq(StaffInfo::getUserId, id));
        List<ServiceReserveInfo> orderList = serviceReserveInfoService.list(Wrappers.<ServiceReserveInfo>lambdaQuery().eq(ServiceReserveInfo::getWorkUserId, userInfo.getId()));
        result.put("user", userInfo);
        result.put("order", orderList);
        return R.ok(result);
    }

    /**
     * 新增志愿者信息
     *
     * @param staffInfo 志愿者信息
     * @return 结果
     */
    @PostMapping
    public R save(StaffInfo staffInfo) throws Exception {
        staffInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        staffInfo.setCode("STF-" + System.currentTimeMillis());

//        userService.registStaff(staffInfo.getCode(), "1234qwer", staffInfo);
        return R.ok(true);
    }

    /**
     * 修改志愿者信息
     *
     * @param staffInfo 志愿者信息
     * @return 结果
     */
    @PutMapping
    public R edit(StaffInfo staffInfo) {
        return R.ok(staffInfoService.updateById(staffInfo));
    }

    /**
     * 删除志愿者信息
     *
     * @param ids 主键IDS
     * @return 结果
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(staffInfoService.removeByIds(ids));
    }
}
