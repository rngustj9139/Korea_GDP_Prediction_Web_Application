package project.gdp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Domain {

    private Long economicalActivePopulation;
    private Long equipmentInvestment;

    public Domain() {
    }

    public Domain(Long economicalActivePopulation, Long equipmentInvestment) {
        this.economicalActivePopulation = economicalActivePopulation;
        this.equipmentInvestment = equipmentInvestment;
    }
}
