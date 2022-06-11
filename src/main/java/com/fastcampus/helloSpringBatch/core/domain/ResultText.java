package com.fastcampus.helloSpringBatch.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate // 일부 컬럼 값만 변경되었을때 해당 컬럼만 변경되는 쿼리 발생하게 하는 어노테이션
@Table(name = "result_text")
public class ResultText {

    public ResultText(String text) {
        this.text = text;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String text;

}