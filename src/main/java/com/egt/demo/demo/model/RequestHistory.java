package com.egt.demo.demo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class RequestHistory implements Serializable {

    // this is String, some of the ids from the json service can have format
    //b8723-112333-asfg1123-2333, which i valid format
    @Id
    String uniqueRequestId;

    //In the task there is no string client ids, so long will do the job
    long clientId;

    String serviceName; // EXT_SERVICE_Х,

    @Column(columnDefinition = "TIMESTAMP")
    LocalDateTime date;
}
