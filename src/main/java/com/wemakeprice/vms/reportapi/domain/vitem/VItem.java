package com.wemakeprice.vms.reportapi.domain.vitem;

import com.google.common.collect.Lists;
import com.wemakeprice.vms.reportapi.domain.AbstractEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.List;

@Slf4j
@Table(name = "v_list")
@Entity
@Getter
@NoArgsConstructor
public class VItem extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String vCategoryName;

    @Column(length = 3)
    private Integer vCategoryCode;

    @Column(length = 100)
    private String vSubCategoryName;

    @Column(length = 3)
    private Integer vSubCategoryCode;

    @Column(length = 1000)
    private String vDetail;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "vItem" , cascade = CascadeType.ALL)
    private List<VItemDetail> vItemDetailsList = Lists.newArrayList();

    @Column(length = 10)
    private String caseTag;

    @Column(length = 10)
    private String respondTag;

    private VGrade vGrade;
    private Integer ordering;

    @Getter
    @RequiredArgsConstructor
    public enum VGrade {
        HIGH("상"),
        MEDIUM("중"),
        LOW("하");

        private final String description;
    }

    @Builder
    public VItem(String vCategoryName,
                 Integer vCategoryCode,
                 String vSubCategoryName,
                 Integer vSubCategoryCode,
                 String vDetail,
                 String caseTag,
                 String respondTag,
                 VGrade vGrade,
                 Integer ordering) {
        this.vCategoryName = vCategoryName;
        this.vCategoryCode = vCategoryCode;
        this.vSubCategoryName = vSubCategoryName;
        this.vSubCategoryCode = vSubCategoryCode;
        this.vDetail = vDetail;
        this.caseTag = caseTag;
        this.respondTag = respondTag;
        this.vGrade = vGrade;
        this.ordering = ordering;
    }

    public void updateVItem(VItemCommand.UpdateVItemRequest command) {
        if (!StringUtils.isEmpty(command.getVCategoryName())) this.vCategoryName = command.getVCategoryName();
        if (!StringUtils.isEmpty(command.getVSubCategoryName())) this.vSubCategoryName = command.getVSubCategoryName();
        if (!StringUtils.isEmpty(command.getVDetail())) this.vDetail = command.getVDetail();
        if (command.getVGrade() != null) this.vGrade = command.getVGrade();
    }
}
