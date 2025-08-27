package com.pix.poc.interactors.web.dto.response;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class PixResponse<T> {

    private ResponseType type;
    private List<String> reasons;
    private LocalDateTime timestamp;
    private List<T> result;

    public PixResponse(ResponseType type, List<String> reasons, List<T> result) {
        this.type = type;
        this.reasons = reasons;
        this.timestamp = LocalDateTime.now();
        this.result = result != null ? result : Collections.emptyList();
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

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result != null ? result : Collections.emptyList();
    }


    public static <T> PixResponse<T> success(String reason) {
        return new PixResponse<>(ResponseType.SUCCESS, List.of(reason), Collections.emptyList());
    }

    public static <T> PixResponse<T> success(List<T> result) {
        return new PixResponse<>(ResponseType.SUCCESS, List.of("Operação realizada com sucesso"), result);
    }

    public static <T> PixResponse<T> error(String reason) {
        return new PixResponse<>(ResponseType.ERROR, List.of(reason), Collections.emptyList());
    }

    public static <T> PixResponse<T> error(List<String> reasons) {
        return new PixResponse<>(ResponseType.ERROR, reasons, Collections.emptyList());
    }
}
