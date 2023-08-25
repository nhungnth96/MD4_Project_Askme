package project.askme.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import project.askme.dto.FormAnswerDto;
import project.askme.dto.FormQuestDto;
import project.askme.dto.FormQuestEditDto;
import project.askme.model.Answer;
import project.askme.model.Category;
import project.askme.model.Question;
import project.askme.model.User;
import project.askme.service.AnswerService;
import project.askme.service.CategoryService;
import project.askme.service.QuestionService;
import project.askme.service.UserService;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    @Autowired
    private UserService userService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private AnswerService answerService;
    @Autowired
    private CategoryService categoryService;
    public User getCurrentUserFromSession(HttpSession session) {
        return (User) session.getAttribute("userIsLogin");
    }
    // ============================== GET QUESTION ==============================
    @GetMapping()
    public String showAllQuestion(Model model) {
        List<Question> questions = questionService.findAll();
        model.addAttribute("questions", questions);
        return "question/questions";
    }
    // ============================== GET ASK ==============================
    @GetMapping("/ask")
    public ModelAndView showAskForm(Model model) {
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        return new ModelAndView("question/askForm", "askForm", new FormQuestDto());
    }
    // ============================== POST QUESTION ==============================
    @Value("${uploadImagePath}")
    private String uploadImagePath;
    @PostMapping("/ask")
    public String actionAsk(@SessionAttribute("userIsLogin") User user,@ModelAttribute("askForm") FormQuestDto formQuestDto) {
        questionService.formToModel(formQuestDto,user);
        return "redirect:/questions";
    }
    // ============================== GET DETAIL ==============================
    @GetMapping("/{id}")
    public String questionDetail(@PathVariable Long id, Model model, HttpSession session) {
        Question question = questionService.findById(id);
        User user = userService.findById(question.getUserId());
        User userIsLogin = getCurrentUserFromSession(session);
        List<Answer> answers = answerService.findAllByQuestId(id);
        session.setAttribute("questionId", id);
        model.addAttribute("answers", answers);
        model.addAttribute("question", question);
        model.addAttribute("user", user);
        model.addAttribute("userIsLogin", userIsLogin);

        return "question/detail";
    }
    @GetMapping("/edit/{questionId}")
    public ModelAndView edit(@PathVariable Long questionId, Model model, HttpSession session) {
        Question question = questionService.findById(questionId);

        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        FormQuestEditDto formQuestEditDto = new FormQuestEditDto();
        formQuestEditDto.setTitle(question.getTitle());
        formQuestEditDto.setCategoryId(question.getCategory().getId());
        formQuestEditDto.setBody(question.getBody());
        return new ModelAndView("question/edit", "editQuest", formQuestEditDto);
    }

    @PostMapping("/update")
    public String updateQuestion(@ModelAttribute("editQuest") FormQuestEditDto formQuestEditDto, HttpSession session) {
        Long editQuestionId = (Long) session.getAttribute("questionId");
        Question question = questionService.findById(editQuestionId);
            MultipartFile imageFile = formQuestEditDto.getImage();
            String image = imageFile.getOriginalFilename();
            try {
                FileCopyUtils.copy(imageFile.getBytes(), new File(uploadImagePath + image));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            question.setTitle(formQuestEditDto.getTitle());
            question.setCategory(categoryService.findById(formQuestEditDto.getCategoryId()));
            question.setImage(image);
            question.setBody(formQuestEditDto.getBody());
            questionService.save(question);

        return "redirect:/questions/" + editQuestionId;
    }
    @PostMapping("/addAnswer")
    public String addAnswer(@ModelAttribute FormAnswerDto formAnswerDto, HttpSession session,Model model) {
        Long questionId = (Long) session.getAttribute("questionId");
        User user = (User) session.getAttribute("userIsLogin");
        answerService.formToModel(formAnswerDto, questionId,user);
        return "redirect:/questions/" + questionId;
    }

}
