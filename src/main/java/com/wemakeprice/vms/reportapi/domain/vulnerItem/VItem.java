package com.wemakeprice.vms.reportapi.domain.vulnerItem;

import com.google.common.collect.Lists;
import com.wemakeprice.vms.reportapi.domain.AbstractEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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

    @ElementCollection
    @CollectionTable(name = "v_item_detail", joinColumns = @JoinColumn(name = "v_item_detail_id"))
    private List<String> vDetailList = Lists.newArrayList();

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
}
