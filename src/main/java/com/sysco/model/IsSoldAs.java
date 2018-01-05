package com.sysco.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class IsSoldAs {
    private boolean isCase;
    private boolean isSplit;

}
