/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package br.com.senac.dao;

import br.com.senac.entidade.Cliente;
import br.com.senac.util.GeradorUtil;
import static br.com.senac.util.GeradorUtil.*;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jessica.santos19
 */
public class ClienteDaoImplTest {

    private Cliente cliente;

    private ClienteDao clienteDao;

    private Session sessao;

    public ClienteDaoImplTest() {
        clienteDao = new ClienteDaoImpl();
    }

    @Test
    public void testSalvar() {
        System.out.println("salvar");
        cliente =  new Cliente(null, "juh", "juh@teste.com", "121222", "14418");
        sessao = HibernateUtil.abrirConexao();
        clienteDao.salvarOuAlterar(cliente, sessao);
        sessao.close();
        assertNotNull(cliente.getId());
        
    }
    @Test
    public void testAlterar() {
        System.out.println("alterar");
        buscarClienteBd();
        
        cliente.setNome(gerarNome());
        cliente.setCpf(gerarCpf());
        cliente.setEmail(gerarEmail());
        cliente.setRg(gerarRg());
        
        sessao = HibernateUtil.abrirConexao();        
        clienteDao.salvarOuAlterar(cliente, sessao);
        sessao.close();
        
        sessao = HibernateUtil.abrirConexao(); 
        Cliente clienteAlt = clienteDao.pesquisarPorId(cliente.getId(), sessao);
        sessao.close();
        
        assertEquals(cliente.getId(), clienteAlt.getId());
        assertEquals(cliente.getNome(), clienteAlt.getNome());
        assertEquals(cliente.getCpf(), clienteAlt.getCpf());
        assertEquals(cliente.getEmail(), clienteAlt.getEmail());
        assertEquals(cliente.getRg(), clienteAlt.getRg());
        
        
    }
    
    @Test
    public void testPesquisarPorId() {
        System.out.println("pesquisarPorId");
        buscarClienteBd();
        sessao = HibernateUtil.abrirConexao();
        Cliente clientePesquisado = clienteDao.pesquisarPorId(cliente.getId(), sessao);
        sessao.close();        
        assertNotNull(clientePesquisado);
        
    }
    @Test
    public void testExcluir() {
        System.out.println("excluir");
        buscarClienteBd();
        sessao = HibernateUtil.abrirConexao();
        clienteDao.excluir(cliente, sessao);
        
        Cliente clienteExcluido = clienteDao.pesquisarPorId(cliente.getId(), sessao);
        sessao.close();        
        assertNull(clienteExcluido);
        
    }
    
    public Cliente buscarClienteBd(){
        
        String hql = "from Cliente";
        sessao = HibernateUtil.abrirConexao();
        Query<Cliente> consulta = sessao.createQuery(hql);
        List<Cliente> clientes =  consulta.list();
        sessao.close();
        
        if(clientes.isEmpty()){
            testSalvar();
        }else {
            cliente = clientes.get(0);
        }
        return cliente;
    }
}
