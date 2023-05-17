/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sptech.aplicacao.java.cli;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author samuc
 */
public class MySql {
        private JdbcTemplate conexaoDoBanco;

    public MySql() {
        BasicDataSource dataSource = new BasicDataSource();

        ////        MYSQL;
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/ToodDatabase");
//      dataSource.setUsername("root");
        dataSource.setUsername("aluno");
        dataSource.setPassword("sptech");

        this.conexaoDoBanco = new JdbcTemplate(dataSource);
    }

    public JdbcTemplate getConexaoDoBanco() {
        return conexaoDoBanco;
    }
}
