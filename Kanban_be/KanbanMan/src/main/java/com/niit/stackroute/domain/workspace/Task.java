package com.niit.stackroute.domain.workspace;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@Document
public class Task
{
    @Id
    private String priority;
    private String taskName;
    private String taskDescription;
    private Date startDate;
    private Date endDate;
    private String assignTo;

}
