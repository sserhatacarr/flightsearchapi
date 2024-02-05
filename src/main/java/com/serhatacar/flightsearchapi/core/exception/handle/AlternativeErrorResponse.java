package com.serhatacar.flightsearchapi.core.exception.handle;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlternativeErrorResponse {
    private int status;
    private String message;
    private long timeStamp;
}
