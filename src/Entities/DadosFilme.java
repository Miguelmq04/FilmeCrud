
package Entities;

import java.time.LocalDate;

/**
 * Essa classe representa os dados do filme
 * 
 * Contém informaçoes como o id, nome, data de lançamento e categoria do filme.
 * 
 * @author quart
 */
public class DadosFilme {
    private Integer id;
    private String nome;
    private LocalDate data;
    protected String categoria;
    
    public DadosFilme() {
    }
    
    public DadosFilme(Integer id, String nome, LocalDate data, String categoria) {
       this.id = id;
       this.nome = nome;
       this.data = data;
       this.categoria = categoria;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    @Override
    public String toString(){
        return this.categoria;
    }
   
}
