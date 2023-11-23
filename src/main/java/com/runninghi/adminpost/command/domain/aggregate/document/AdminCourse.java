package com.runninghi.adminpost.command.domain.aggregate.document;

import com.runninghi.common.gpx.GpxDataDTO;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@Document(collection = "adminCourse")
public class AdminCourse {

    @Id
    private String id;

    @Field
    private Long adminPostNo;

    @Field
    private List<GpxDataDTO> points;

    @Builder
    public AdminCourse (String id, Long adminPostNo, List<GpxDataDTO> points) {
        this.id = id;
        this.adminPostNo = adminPostNo;
        this.points = points;
    }

    public void update (List<GpxDataDTO> points) {
        this.points = points;
    }

}
