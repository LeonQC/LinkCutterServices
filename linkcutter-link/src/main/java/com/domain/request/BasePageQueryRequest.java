package com.domain.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasePageQueryRequest implements Serializable {

    private String username;

    private int pageSize = 20;

    private int pageNo = 1;
}
