package lt.inventiCodingChallange.bankAccountManager.controller;

import lombok.RequiredArgsConstructor;
import lt.inventiCodingChallange.bankAccountManager.model.BankStatement;
import lt.inventiCodingChallange.bankAccountManager.repository.BankStatementRepository;
import lt.inventiCodingChallange.bankAccountManager.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
public class BankStatementController {

    @Autowired
    private BankAccountService bankAccountService;

    @GetMapping("/")
    public ModelAndView list(Model model){
        ModelAndView modelAndView = new ModelAndView();
        List<BankStatement> bankStatementList = bankAccountService.findAll();
        model.addAttribute("bankStatements", bankStatementList);
        modelAndView.setViewName("statements");
        return modelAndView;
    }

    @GetMapping("/import")
    public ModelAndView importForm(Model model){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("import-form");
        return modelAndView;
    }

    @PostMapping("/importFile")
    public void importCSVFile(@RequestParam("file") MultipartFile file, HttpServletResponse response) throws IOException {
        bankAccountService.importBankStatement(file);
        response.sendRedirect("/");
    }

    @GetMapping("/exportFile")
    public void exportCSVFile(HttpServletResponse response) throws IOException {
        bankAccountService.exportBankStatement();
        response.sendRedirect("/");
    }
}
