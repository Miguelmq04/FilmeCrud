
package Entities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;


public class MetodosDAO {
    Connection conn ;
    PreparedStatement st = null;
    
    String url = "jdbc:mysql://localhost:3306/cenaflixuc10";
    String name = "root";
    String password = "@Miguel_1415";
    
    
    /**
     * Conecta ao banco de dados.
     * 
     * @return true se a conexão for bem-sucedida, false se a conexão falhou.
     */
    public boolean conectarDB(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, name, password);
            return true;
        }
        catch(ClassNotFoundException | SQLException ex){
             System.out.println("Erro ao conectar: " + ex.getMessage());
             return false;
        }
    }
    /**
     * Desconecta do banco de dados.
     */
    public void DesconectarDB(){
        try{
            conn.close();
        }
        catch(SQLException ex){
        }
    }
    
    /**
     * Cadastra um filme no banco de dados.
     * 
     * @param dadosFilme dados contento as informações do filme que será cadastrado.
     * @return 1 se a operação foi realizada ou sera retornado um exceção SQL.
     */
    public int cadastrar(DadosFilme dadosFilme){
        try{
            int status;
        
            st = conn.prepareStatement("insert into filmes (nome, datalancamento, categoria) values (?, ?, ?)");
            st.setString(1, dadosFilme.getNome());
            st.setDate(2, java.sql.Date.valueOf(dadosFilme.getData()));
            st.setString(3, dadosFilme.getCategoria());
            status = st.executeUpdate();
        
            return status;
        }
        catch(SQLException ex){
            System.out.println("Erro ao cadastrar: " + ex.getMessage());
            return ex.getErrorCode();
        }
    }
    /**
     * Recupera todos os filmes registrados no banco de dados.
     * 
     * Este metodo irá executar uma consulta SQL para buscar todos os filme na tabela e retorna em formato de lista.
     * 
     * @return Uma lista de Objetos `DadosFilmes` terá todos os filmes registrados no banco de dados, caso ocorrer algum erro, será retornado `null`.
     */
    public List<DadosFilme> GetFilmes(){
        String sql = "select * from filmes";
        
        try{
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            
            ResultSet rs = stmt.executeQuery();
            
            List<DadosFilme> listaFilmes = new ArrayList<>();
            while(rs.next()){
                DadosFilme dadosFilme = new DadosFilme();
                
                dadosFilme.setId(rs.getInt("id"));
                dadosFilme.setNome(rs.getString("nome"));
                dadosFilme.setData(rs.getDate("datalancamento").toLocalDate());
                dadosFilme.setCategoria(rs.getString("categoria"));
                
                listaFilmes.add(dadosFilme);
            }
            return listaFilmes;
        }
        catch(Exception e){
            return null;
        }
    }
    
    /**
     * Recupera a lista de filmes de uma determinada categoria escolhida.
     * 
     * Este método realiza uma consulta SQL para buscar filmes da categoria fornecida.
     * 
     * @param categoria o nome da categoria e retorna os filmes da categoria escolhida.
     * @return Uma lista de Objetos `DadosFilmes` terá todos os filmes registrados no banco de dados da categoria especificada. Caso ocorra um erro
     * será retornado `null`.
     */
    public List<DadosFilme> GetFilmesCategoria(String categoria){
        String sql = "select * from filmes where categoria = ?";
        
        try{
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            stmt.setString(1, categoria);
            ResultSet rs = stmt.executeQuery();
            
            List<DadosFilme> listaFilmesCategoria = new ArrayList<>();
            while(rs.next()){
                DadosFilme dadosFilme = new DadosFilme();
                
                dadosFilme.setId(rs.getInt("id"));
                dadosFilme.setNome(rs.getString("nome"));
                dadosFilme.setData(rs.getDate("datalancamento").toLocalDate());
                dadosFilme.setCategoria(rs.getString("categoria"));
                
                listaFilmesCategoria.add(dadosFilme);
            }
            return listaFilmesCategoria;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao mostrar os filmes " + e.getMessage() + ", se o erro persistir, entre em contato.");
            return null;
        }
    }
    
    /**
     * Exclui o filme com ID especificado no banco de dados.
     * 
     * Este método irá deletar o filme no banco de dados com base do id fornecido.
     * 
     * @param id o identificador único do Filme a ser excluído.
     */
    public void Excluir(int id){
        String sql = "delete from filmes where id = ?";
        try{
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            stmt.setInt(1, id);
            
            stmt.execute();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao excluir filme: " + e.getMessage() + ", se o erro persistir, entre em contato.");
        }
    }
    
    /**
     * Editara o filme com ID especificado no banco de dados.
     * 
     * Este método irá editar as informações do filme no banco de dados com base no id fornecido. 
     * As informações que podem ser editadas são nome do filme, data de lançamento e a categoria do filme.
     * 
     * @param dadosFilme o identificador único do filme a ser editado, incluindo as novas informações do filme.
     */
    public void Editar(DadosFilme dadosFilme){
        String sql = "update filmes set nome = ?, datalancamento = ?, categoria = ? where id = ?";
        
        try{
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            stmt.setString(1, dadosFilme.getNome());
            stmt.setDate(2, java.sql.Date.valueOf(dadosFilme.getData()));
            stmt.setString(3, dadosFilme.getCategoria());
            stmt.setInt(4, dadosFilme.getId());
            
            stmt.executeUpdate();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao editar o filme " + e.getMessage() + ", se o erro persistir, entre em contato.");
        }
    }
}
