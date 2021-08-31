package ssm.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ssm.controller.annotation.BussinessLog;
import ssm.dto.JsonResult;
import ssm.dto.UploadFileVO;
import ssm.entity.User;
import ssm.enums.ResultCodeEnum;
import ssm.service.UserService;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.security.Principal;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * @author chen
 * @create 2019-07-16 9:01
 * 个人资料
 */
@Controller
@RequestMapping(value = "/admin/user")
@Slf4j
//@PreAuthorize("hasRole('ROLE_ROOT')")  // 指定角色权限才能操作方法
public class ProfileController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 个人资料页面显示
     *
     * @return
     */
    @RequestMapping(value = "/profile")
    public ModelAndView userProfileView(Principal principal) {

        ModelAndView modelAndView = new ModelAndView();
        //User sessionUser = (User) session.getAttribute("user");
        //User user =  userService.getUserById(sessionUser.getUserId());
        User user = userService.getUserByName(principal.getName());
        System.out.println("user: " + principal.getName());
        modelAndView.addObject("user", user);

        modelAndView.setViewName("admin/user/profile");
        return modelAndView;
    }

    /**
     * 处理修改用户资料的请求
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/profile/save", method = RequestMethod.POST)
    @ResponseBody
    @BussinessLog("修改用户资料")
    public JsonResult editUserSubmit(User user) {

        try {
            //1.添加/更新用户名检查用户名是否存在 //root
            User nameCheck = userService.getUserByName(user.getUserName());
            if ((user.getUserId() == null && nameCheck != null) || (user.getUserId() != null && !Objects.equals(nameCheck.getUserId(), user.getUserId()))) {
                return new JsonResult(ResultCodeEnum.FAIL.getCode(), "用户名已存在");
            }
            //2.添加/更新用户名检查用户名是否存在
            User emailCheck = userService.getUserByEmail(user.getUserEmail());
            if ((user.getUserId() == null && emailCheck != null) || (user.getUserId() != null && !Objects.equals(emailCheck.getUserId(), user.getUserId()))) {
                return new JsonResult(ResultCodeEnum.FAIL.getCode(), "邮箱已存在");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String userPass = user.getUserPass();
        if (userPass != null) {
            String encodedPassword = bCryptPasswordEncoder.encode(userPass);
            user.setUserPass(encodedPassword);
        }
        if (user.getUserId() != null) {
            //更新用户
            userService.updateUser(user);
        } else {
            // 添加用户
            user.setUserRegisterTime(new Date());
            user.setUserStatus(1);
            userService.insertUser(user);
            return new JsonResult(ResultCodeEnum.SUCCESS.getCode(), "添加成功");
        }
        return new JsonResult(ResultCodeEnum.SUCCESS.getCode(), "修改成功");
    }


    /**
     * 修改密码
     *
     * @param principal
     * @param oldPwd
     * @param newPwd
     * @return
     */
    @RequestMapping(value = "/updatePwd", method = RequestMethod.POST)
    @ResponseBody
    @BussinessLog("修改密码")
    public JsonResult updatePwd(Principal principal, @ModelAttribute("oldPwd") String oldPwd,
                                @ModelAttribute("newPwd") String newPwd) {
        try {
            //查询当前用户信息
            User sessionUser = userService.getUserByName(principal.getName());
            //数据存储的密文密码
            String userPass = sessionUser.getUserPass();
            // 判断前台来密码和数据库密文密码是否匹配
            boolean isMatch = bCryptPasswordEncoder.matches(oldPwd, userPass);
            System.out.println("matches: " + isMatch);
            if (isMatch) {
                //加密密码
                String password = bCryptPasswordEncoder.encode(newPwd);
                userService.updatePassword(sessionUser.getUserId(), password);
            } else {
                return new JsonResult(ResultCodeEnum.FAIL.getCode(), "原密码不正确");
            }
        } catch (Exception e) {
            log.error("修改密码失败：{}", e.getMessage());
            return new JsonResult(ResultCodeEnum.FAIL.getCode(), "修改失败");
        }
        return new JsonResult(ResultCodeEnum.SUCCESS.getCode(), "修改成功");
    }

    /**
     * 文件保存目录，物理路径
     */
    public final String rootPath = "C:/Users/chenj/junwuyi/uploads";

    public final String allowSuffix = ".bmp.jpg.jpeg.png.gif.pdf.doc.zip.rar.gz";

    /**
     * 上传文件
     *
     * @param file
     * @return
     * @throws
     */
    @RequestMapping(value = "/img", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult uploadFile(@RequestParam(value = "file", required = false) MultipartFile file) {

        if (file == null) {
            return new JsonResult().fail("请选择文件");
        }

        //1.文件后缀过滤，只允许部分后缀
        //文件的完整名称,如spring.jpeg
        String filename = file.getOriginalFilename();
        //文件名,如spring
        String name = filename.substring(0, filename.indexOf("."));
        //文件后缀,如.jpeg
        String suffix = filename.substring(filename.lastIndexOf("."));

        if (allowSuffix.indexOf(suffix) == -1) {
            return new JsonResult().fail("不允许上传该后缀的文件！");
        }


        //2.创建文件目录
        //创建年月文件夹
        Calendar date = Calendar.getInstance();
        File dateDirs = new File(date.get(Calendar.YEAR)
                + File.separator + (date.get(Calendar.MONTH) + 1));

        //目标文件
        File descFile = new File(rootPath + File.separator + dateDirs + File.separator + filename);
        int i = 1;
        //若文件存在重命名
        String newFilename = filename;
        while (descFile.exists()) {
            newFilename = name + "(" + i + ")" + suffix;
            String parentPath = descFile.getParent();
            descFile = new File(parentPath + File.separator + newFilename);
            i++;
        }
        //判断目标文件所在的目录是否存在
        if (!descFile.getParentFile().exists()) {
            //如果目标文件所在的目录不存在，则创建父目录
            descFile.getParentFile().mkdirs();
        }

        //3.存储文件
        //将内存中的数据写入磁盘
        try {
            file.transferTo(descFile);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("上传失败，cause:{}", e);
        }
        //完整的url
        String fileUrl = "/uploads/" + dateDirs + "/" + newFilename;

        //4.返回URL
        UploadFileVO uploadFileVO = new UploadFileVO();
        uploadFileVO.setTitle(filename);
        uploadFileVO.setSrc(fileUrl);
        return new JsonResult().ok(uploadFileVO);
    }

}
