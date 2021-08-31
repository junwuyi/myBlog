package ssm.controller.exception;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author chen
 * @create 2019-08-06 16:20
 */
@Controller
public class ExceptionHandler {

    /**
     * 404 - Not Found
     */
    @RequestMapping("/404")
    public String NotFound(@RequestParam(required = false) String Param, Model model) {
        model.addAttribute("Param", Param);
        String message = "页面不存在";
        model.addAttribute("message", message);
        model.addAttribute("code", 404);
        model.addAttribute("title", "404 Not Found");
        return "admin/error/error";
    }

    /**
     * 403 - Forbidden
     */
    @RequestMapping("/403")
    public String Forbidden(@RequestParam(required = false) String Param, Model model) {
        model.addAttribute("Param", Param);
        String message = "您没有权限";
        model.addAttribute("message", message);
        model.addAttribute("code", 403);
        model.addAttribute("title", "403 Forbidden");
        return "admin/error/error";
    }

    /**
     * 500 - Internal Server Error
     */
    @RequestMapping("/500")
    public String ServerError(@RequestParam(required = false) String Param, Model model) {
        model.addAttribute("Param", Param);
        String message = "系统在休息";
        model.addAttribute("message", message);
        model.addAttribute("code", 500);
        model.addAttribute("title", "500 Internal Server Error");
        return "admin/error/error";
    }

    /**
     * 400 - BadRequest
     */
    @RequestMapping("/400")
    public String  BadRequest(@RequestParam(required = false) String Param, Model model) {

        return "redirect:/";
    }
}
