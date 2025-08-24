package com.pix.poc.interactors.web.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public class ResponsePixCustom {

    private ResponseType type;
    private List<String> reasons;
    private LocalDateTime timestamp;

    public ResponsePixCustom(ResponseType type, List<String> reasons) {
        this.type = type;
        this.reasons = reasons;
        this.timestamp = LocalDateTime.now();
    }

    public ResponseType getType() {
        return type;
    }

    public void setType(ResponseType type) {
        this.type = type;
    }

    public List<String> getReasons() {
        return reasons;
    }

    public void setReasons(List<String> reasons) {
        this.reasons = reasons;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public static ResponsePixCustom success(String reason) {
        return new ResponsePixCustom(ResponseType.SUCCESS, List.of(reason));
    }

    public static ResponsePixCustom error(String reason) {
        return new ResponsePixCustom(ResponseType.ERROR, List.of(reason));
    }
}
