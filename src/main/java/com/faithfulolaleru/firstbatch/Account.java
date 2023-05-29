package com.faithfulolaleru.firstbatch;

//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "accounts")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue
    private Integer id;

    private String code;
    private String accountName;
    private String type;
    private String allowReconciliation;
    private String externalId;

    public Account(String code, String accountName, String type, String allowReconciliation, String externalId) {
        this.code = code;
        this.accountName = accountName;
        this.type = type;
        this.allowReconciliation = allowReconciliation;
        this.externalId = externalId;
    }
}
