package com.niit.stackroute.domain.workspace;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@ToString
@Data
@NoArgsConstructor
@Document public class Workspace {

    @Id
    private String spaceID;
    private String spaceName;
    private String spaceDiscription;
    private String email;
    private List<Status> status;
    private  List<String> members;

    public void addStatus(Status status1) {

        if (Objects.isNull(status)) {
            status = new ArrayList<>();
        }
        status.add(status1);
    }


    public void addEmail(String emailId) {

        if (Objects.isNull(members)) {
            members = new ArrayList<>();
        }
        members.add(emailId);
    }
}
