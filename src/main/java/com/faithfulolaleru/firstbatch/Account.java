package com.faithfulolaleru.firstbatch;

//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Entity(name = "accounts")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account {

//    @Id
//    @GeneratedValue
    private Long id;

    private String code;
    private String accountName;
    private String type;
    private String allowReconciliation;
    private String externalId;
}
