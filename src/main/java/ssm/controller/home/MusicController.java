package ssm.controller.home;

import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ssm.controller.annotation.BussinessLog;
import ssm.dto.JsonResult;
import ssm.entity.Music;
import ssm.enums.ResultCodeEnum;
import ssm.service.MusicService;

import java.util.List;

/**
 * @author chen
 * @create 2019-09-20 18:07
 * music
 */
@Controller
@Slf4j
public class MusicController {

    @Autowired
    private MusicService musicService;

    /**
     * 前台music显示
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/api/musics")
    @ResponseBody
    public List<Music> ApifindAll(Model model) {
        List<Music> musicList = musicService.findAll();
        return musicList;
    }

    /**
     * 后台music显示
     *
     * @return
     */
    @RequestMapping(value = "/admin/music")
    @PreAuthorize("hasRole('ROLE_ROOT')")  // 指定角色权限才能操作方法
    public String musicList(Model model) {
        return this.indexPage(1,  model);
    }

    /**
     * 后台音乐列表分页显示
     *
     * @param page   第几页开始
     * @param model
     * @return
     */
    @RequestMapping(value = "/admin/music/page/{page}")
    public String indexPage(@PathVariable(value = "page") Integer page, Model model) {
        //默认显示10条
        Integer pageSize = 10;
        PageInfo<Music> musicPageInfo = musicService.listMusicByPage(page, pageSize);
        model.addAttribute("pageInfo", musicPageInfo);
        model.addAttribute("pageUrlPrefix","/admin/music/page");
        return "admin/testPage/musicList";
    }

    /**
     * 后台添加用户页面显示
     *
     * @return
     */
    @RequestMapping(value = "/admin/music/insert")
    public ModelAndView insertUserView() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/testPage/insert-music");
        return modelAndView;
    }

    /**
     * 新增music
     *
     * @param music
     * @return
     */
    @RequestMapping(value = "/admin/music/saveMusic", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult saveMusic(Music music) {
        try {
            musicService.save(music);
            return new JsonResult(ResultCodeEnum.SUCCESS.getCode(), "添加成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResult(ResultCodeEnum.FAIL.getCode(), "添加失败");
    }

    /**
     * 编辑歌曲页面显示
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/admin/music/edit")
    public ModelAndView editUserView(@RequestParam Integer id) {
        ModelAndView modelAndView = new ModelAndView();

        Music music = musicService.getMusicById(id);
        modelAndView.addObject("music", music);

        modelAndView.setViewName("admin/testPage/edit-music");
        return modelAndView;
    }

    /**
     * 修改music
     *
     * @param music
     * @return
     */
    @RequestMapping(value = "/admin/music/updateMusic", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult updateMusic(Music music) {
        try {
            musicService.update(music);
            return new JsonResult(ResultCodeEnum.SUCCESS.getCode(), "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResult(ResultCodeEnum.FAIL.getCode(), "修改失败");
    }

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/admin/music/delMusic/{id}")
    @ResponseBody
    @BussinessLog("删除音乐")
    public JsonResult deleteUser(@PathVariable("id") Integer id) {
        try {
            musicService.delMusicById(id);
            return new JsonResult(ResultCodeEnum.SUCCESS.getCode(), "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResult(ResultCodeEnum.FAIL.getCode(), "删除失败");

    }


}
