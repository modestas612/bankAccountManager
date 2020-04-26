package lt.inventiCodingChallange.bankAccountManager.controller;

import lombok.RequiredArgsConstructor;
import lt.inventiCodingChallange.bankAccountManager.model.Balance;
import lt.inventiCodingChallange.bankAccountManager.model.BankStatement;
import lt.inventiCodingChallange.bankAccountManager.repository.BalanceRepository;
import lt.inventiCodingChallange.bankAccountManager.repository.BankStatementRepository;
import lt.inventiCodingChallange.bankAccountManager.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
public class BankStatementController {

    @Autowired
    private BankAccountService bankAccountService;
    @Autowired
    private BalanceRepository balanceRepository;

    @GetMapping("/")
    public ModelAndView list(Model model){
        ModelAndView modelAndView = new ModelAndView();
        List<BankStatement> bankStatementList = bankAccountService.findAll();
        model.addAttribute("bankStatements", bankStatementList);
        modelAndView.setViewName("statements");
        return modelAndView;
    }

    @GetMapping("/allBalances")
    public ModelAndView listBalances(Model model){
        ModelAndView modelAndView = new ModelAndView();
        List<Balance> balanceList = balanceRepository.findAll();
        model.addAttribute("balanceList", balanceList);
        modelAndView.setViewName("allBalances");
        return modelAndView;
    }

    @GetMapping("/import")
    public ModelAndView importForm(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("import-form");
        return modelAndView;
    }

    @GetMapping("/balance")
    public ModelAndView balanceForm(Model model){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("balance-form");
        return modelAndView;
    }

    @PostMapping("/importFile")
    public void importCSVFile(@RequestParam("file") MultipartFile file,
                              HttpServletResponse response) throws IOException {
        bankAccountService.importBankStatement(file);
        response.sendRedirect("/");
    }

    @GetMapping("/exportFile")
    public void exportCSVFile(HttpServletResponse response) throws IOException {
        bankAccountService.exportBankStatement();
        response.sendRedirect("/");
    }

    @PostMapping("/balanceCalc")
    public void calculateBalance(@RequestParam("dateFrom") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFrom,
                                 @RequestParam("dateTo") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTo,
                                 @RequestParam("accountNumber") String accountNumber, Model model,
                                 HttpServletResponse response) throws IOException{
        Double accBalance = bankAccountService.balance(dateFrom, dateTo, accountNumber);
        Balance bl = new Balance();
        bl.setAccountNumber(accountNumber);
        bl.setAccountBalance(accBalance);
        bl.setDateFrom(dateFrom);
        bl.setDateTo(dateTo);
        balanceRepository.save(bl);

        response.sendRedirect("/allBalances");
    }
}
