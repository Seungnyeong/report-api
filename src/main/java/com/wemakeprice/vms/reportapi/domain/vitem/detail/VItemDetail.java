package com.wemakeprice.vms.reportapi.domain.vitem.detail;

import com.google.common.collect.Lists;
import com.wemakeprice.vms.reportapi.common.exception.InvalidParamException;
import com.wemakeprice.vms.reportapi.domain.vitem.VItem;
import com.wemakeprice.vms.reportapi.domain.vitem.VItemCommand;
import com.wemakeprice.vms.reportapi.domain.vitem.detailGroup.VItemDetailGroup;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.procedure.ParameterMisuseException;

import javax.persistence.*;
import java.util.List;

@Entity
@Table( name = "v_item_detail")
@Getter
@NoArgsConstructor
@Slf4j
public class VItemDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(length = 1000)
    private String vDetail;

    @ManyToOne
    @JoinColumn(name = "v_item_detail_group_id")
    private VItemDetailGroup vItemDetailGroup;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "v_detail_respond",
            joinColumns = @JoinColumn(name = "v_item_detail_id"))
    private List<String> respond = Lists.newArrayList();


    @Builder
    public VItemDetail(VItemDetailGroup vItemDetailGroup, String vDetail, List<String> respond) {
        this.vItemDetailGroup = vItemDetailGroup;
        this.vDetail = vDetail;
        this.respond = respond;
    }

    public void updateDetail(VItemCommand.UpdateVItemDetailRequest request) {
        if(!StringUtils.isEmpty(request.getDetail())) this.vDetail = request.getDetail();
        if( request.getRespond() != null) {
            if ( request.getRespond().size() > 0) request.getRespond().forEach(item -> this.respond.add(0, item));
        }
    }

    public void removeResponse(int index) {
        if (this.respond.size() > 0) {
            this.respond.remove(index);
        } else {
            throw new InvalidParamException("삭제할 데이터가 없습니다.");
        }
    }
}
