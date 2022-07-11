//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.taozi.common.system.base.entity;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import java.util.*;
import lombok.Data;

@Data
public class TaoziOneItemPage<T> {
    protected List<T> records;
    protected long total = 0L;
    protected long size = 1L;
    protected long current = 1L;
    protected List<OrderItem> orders = new ArrayList<>();
    protected boolean optimizeCountSql = true;
    protected boolean searchCount = true;
    protected String countId = null;
    protected long maxLimit = 1L;
    protected long pages = 1L;

    public TaoziOneItemPage() {
        if (records == null) {
            records = new ArrayList<>();
        }
    }

    public TaoziOneItemPage(T data) {
        if (records == null) {
            records = new ArrayList<>();
        }
        if (data != null) {
            records.add(data);
            total++;
        }
    }

    public void Add(T data) {
        if (records == null) {
            return;
        }
        if (data != null) {
            records.add(data);
            total++;
        }
    }

}
