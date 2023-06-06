package com.example.demo.model;

import java.util.List;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_clients")
public class Client {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    @NotBlank(message = "Không được để trống")
    private String name;

    @Column(name = "address")
    @NotBlank(message = "Không được để trống")
    private String address;

    @Column(name = "email")
    @NotBlank(message = "Không được để trống")
	@Pattern(regexp ="^[a-z0-9](\\.?[a-z0-9]){5,}@g(oogle)?mail\\.com$",message="Email không hợp lệ")
    private String email;

    @Column(name = "phone")
    @NotBlank(message = "Không được để trống")
    @Pattern(regexp="(^$|[0-9]{10})", message = "Số điện thoại sai định dạng")
    private String phone;

    @Column(name = "username")
    @NotBlank(message = "Không được để trống")
    @Size(min=5,max=15,message = "Độ dài tên đăng nhập tối thiếu từ 6 kí tự trở lên")
    private String username;

    @Column(name = "password")
    @NotBlank(message = "Không được để trống")
    @Size(min=6,message = "Yêu cầu mật khẩu từ 6 kí tự trở lên")
    private String password;

    @OneToMany(mappedBy = "client")
    private List<Contract> contracts;

    @OneToMany(mappedBy = "client")
    private List<SupportTicket> supportTickets;
}
/*
Table: tbl_clients
Columns:
id int AI PK 
name varchar(45) 
address varchar(255) 
email varchar(45) 
phone varchar(10) 
username varchar(20) 
password varchar(20)
 */
/*
Ý nghĩa của đoạn này regexp ="^[a-z0-9](\\.?[a-z0-9]){5,}@g(oogle)?mail\\.com$"
    "^" là ký tự bắt đầu chuỗi.
    "[a-z0-9]" là ký tự đầu tiên của địa chỉ email phải là một chữ cái thường hoặc một số.
    "(\.?[a-z0-9])" là ký tự tiếp theo có thể là dấu chấm và sau đó là một chữ cái thường hoặc một số.
    "{5,}" chỉ ra rằng địa chỉ email phải có ít nhất 5 ký tự (không tính ký tự "@" và "gmail.com").
    "@" là ký tự "@" phải có trong địa chỉ email.
    "g(oogle)?" là ký tự "g" hoặc "google" có thể xuất hiện trong địa chỉ email, tuy nhiên không bắt buộc.
    "mail\.com" là phần cuối của địa chỉ email, chỉ định rằng địa chỉ email phải kết thúc bằng chuỗi "mail.com".
    "$" là ký tự kết thúc chuỗi.
Ý nghĩa của đoạn này regexp="(^$|[0-9]{10})"
    ^: bắt đầu của chuỗi
    $: kết thúc của chuỗi
    |: hoặc
    [0-9]: bất kỳ chữ số từ 0 đến 9
    {10}: chính xác 10 ký tự
    Vậy đoạn regex này có nghĩa là chuỗi phải có 10 chữ số hoặc không có gì cả (chuỗi rỗng). Nó thường được sử dụng để kiểm tra tính hợp lệ của số điện thoại vì số điện thoại có thể có 10 chữ số hoặc không có gì cả.
 */