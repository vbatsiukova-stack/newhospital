package com.solvd.hospital.model;

import com.solvd.hospital.enums.InsuranceType;
import com.solvd.hospital.exceptions.InvalidInsuranceException;

public class Insurance {
    private String companyName;
    private String policyNumber;
    private InsuranceType type = InsuranceType.NONE;


    public Insurance() {
    }

    public Insurance(String companyName, String policyNumber) {
        this.companyName = companyName;
        this.policyNumber = policyNumber;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public void validate() throws InvalidInsuranceException {
        if (companyName == null || companyName.isEmpty()) {
            throw new InvalidInsuranceException("model.Insurance company name is missing");
        }

        if (policyNumber == null || policyNumber.isEmpty()) {
            throw new InvalidInsuranceException("model.Insurance policy number is missing");
        }

        if (policyNumber.length() < 5) {
            throw new InvalidInsuranceException("model.Insurance policy number is invalid");
        }
    }
    public InsuranceType getType() {
        return type;
    }

    public void setType(InsuranceType type) {
        this.type = type;
    }
    public boolean canCover(double amount) {
        return switch (type) {
            case NONE -> false;
            case PUBLIC -> amount <= 1000;
            case PRIVATE -> true;
        };
    }

}
