package com.example.hoteltask.model.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class TaskClaimRequest implements Serializable {
    private Long taskId;
}
