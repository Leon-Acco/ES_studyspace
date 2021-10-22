package com.acho.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @Author:Acho-leon
 * @Modified By:
 * @params:
 * @creat:2021-10-20-9:10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    private int id;
    private String name;
    private String address;
    private  int age;

}
