import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/bank")
public class BankController {

    private Map<Integer, BankAccount> bankAccounts = new HashMap<>();

    @PostMapping("/createAccount")
    public ResponseEntity<String> createAccount(@RequestParam int accountNumber, @RequestParam String accountHolder) {
        if (bankAccounts.containsKey(accountNumber)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Account already exists");
        }
        bankAccounts.put(accountNumber, new BankAccount(accountNumber, accountHolder));
        return ResponseEntity.status(HttpStatus.CREATED).body("Account created successfully");
    }

    @PostMapping("/deposit")
    public ResponseEntity<String> deposit(@RequestParam int accountNumber, @RequestParam BigDecimal amount) {
        if (!bankAccounts.containsKey(accountNumber)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Account not found");
        }
        BankAccount bankAccount = bankAccounts.get(accountNumber);
        bankAccount.deposit(amount);
        return ResponseEntity.status(HttpStatus.OK).body("Amount deposited successfully");
    }

    @PostMapping("/withdraw")
    public ResponseEntity<String> withdraw(@RequestParam int accountNumber, @RequestParam BigDecimal amount) {
        if (!bankAccounts.containsKey(accountNumber)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Account not found");
        }
        BankAccount bankAccount = bankAccounts.get(accountNumber);
        if (!bankAccount.canWithdraw(amount)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient balance");
        }
        bankAccount.withdraw(amount);
        return ResponseEntity.status(HttpStatus.OK).body("Amount withdrawn successfully");
    }

    @GetMapping("/getAccount")
    public ResponseEntity<BankAccount> getAccount(@RequestParam int accountNumber) {
        if (!bankAccounts.containsKey(accountNumber)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        BankAccount bankAccount = bankAccounts.get(accountNumber);
        return ResponseEntity.status(HttpStatus.OK).body(bankAccount);
    }

    @DeleteMapping("/removeAccount")
    public ResponseEntity<String> removeAccount(@RequestParam int accountNumber) {
        if (!bankAccounts.containsKey(accountNumber)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Account not found");
        }
        bankAccounts.remove(accountNumber);
        return ResponseEntity.status(HttpStatus.OK).body("Account removed successfully");
    }

}

