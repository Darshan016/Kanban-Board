package com.niit.stackroute.domain.workspace;

import com.niit.stackroute.domain.workspace.Task;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Document
public class Status {
    @Id
    private String statusName;
    private List<Task> task;

    public void addTask(Task task1) {

        if (Objects.isNull(task)) {
            task = new ArrayList<>();
        }
        task.add(task1);
    }

}
