package lt.inventiCodingChallange.bankAccountManager.service;

import lombok.RequiredArgsConstructor;
import lt.inventiCodingChallange.bankAccountManager.model.BankStatement;
import lt.inventiCodingChallange.bankAccountManager.repository.BankStatementRepository;
import lt.inventiCodingChallange.bankAccountManager.storage.StorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BankAccountService{

    private String line = "";
    private final Path rootLocation;

    @Autowired
    public BankAccountService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Autowired
    private BankStatementRepository bankStatementRepository;


    public List<BankStatement> findAll() {
        return bankStatementRepository.findAll();
    }

    public void importBankStatement(MultipartFile file){
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                BankStatement bs = new BankStatement();
                bs.setAccountNumber(data[0]);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
                Date date = formatter.parse(data[1]);
                bs.setOperationDate(date);
                bs.setBeneficiary(data[2]);
                bs.setComment(data[3]);
                bs.setAmount(Double.valueOf(data[4]));
                bs.setCurrency(data[5]);
                bankStatementRepository.save(bs);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void exportBankStatement(){
        File csvFile = new File(rootLocation + "/bankStatements.csv");
        try {
            FileWriter writer = new FileWriter(csvFile);
            BufferedWriter bw = new BufferedWriter(writer);
            PrintWriter pw = new PrintWriter(bw);
            for(BankStatement st: findAll()){
                pw.println(st.getAccountNumber()+","+st.getOperationDate()+","+st.getBeneficiary()+","+st.getComment()+","+st.getAmount()+","+st.getCurrency());
            }
            pw.flush();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Double balance(Date dateFrom, Date dateTo, String accountNumber){
        List<BankStatement> statements = bankStatementRepository.findAllByAccountNumber(accountNumber);
        Double sum = 0.0;
        for (BankStatement st: statements){
            if (isBetWeen(st.getOperationDate(), dateFrom, dateTo) && st.getAmount() != null){
                sum = sum + st.getAmount();
            }
        }
        return sum;
    }

    public static boolean isBetWeen(Date date, Date from, Date to){
        if(date == null){
            return false;
        }
        return from == null ? (to == null ? true : to.after(date)) : (to == null ? from.before(date) : (to.after(date) && from.before(date)));
    }
}
