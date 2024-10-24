package com.example.arrowdb.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import java.util.ArrayList;
import java.util.List;

import static org.hibernate.envers.RelationTargetAuditMode.NOT_AUDITED;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "quality")
public class Quality {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qty_id")
    private Integer qtyId;

    @Column(name = "qualities")
    private String qualityName;

    @Audited(targetAuditMode = NOT_AUDITED)
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "quality", fetch = FetchType.LAZY)
    private List<Profession> professionList = new ArrayList<>();

    public void addProfessionToQuality(){
        professionList.forEach(e -> e.setQuality(this));
    }

    public void setQualityName(String qualityName) {
        try {
            this.qualityName = qualityName.substring(0, 1).toUpperCase() + qualityName.substring(1);
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    @Override
    public String toString() {
        return qualityName;
    }

}