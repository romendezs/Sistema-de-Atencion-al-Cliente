/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import modelo.db.pgConexion.pgConexion;
import modelo.dominio.Carrera;
import modelo.repo.IRepository.ICarreraRepository;

/**
 *
 * @author Méndez
 */
public class CarreraRepository implements ICarreraRepository{

    @Override
    public List<Carrera> findAll() {
        List<Carrera> carreras = new ArrayList<>();
        String sql = "SELECT * FROM carrera";
        
        try
            (Connection cn = pgConexion.conectar();
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){
            
            while(rs.next()){
                Carrera c = new Carrera(
                        rs.getInt("id_carrera"), 
                        rs.getString("nombre"),
                        rs.getInt("id_facultad")
                );
                carreras.add(c);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        
        return carreras;
    }

    @Override
    public List<Carrera> findByFacultadId(int idFacultad) {
        List<Carrera> carreras = new ArrayList<>();
        String sql = "SELECT * FROM carrera WHERE id_facultad = ?";
        
        try
            (Connection cn = pgConexion.conectar();
            PreparedStatement ps = cn.prepareStatement(sql);){
            
            ps.setString(1, Integer.toString(idFacultad));
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                Carrera c = new Carrera(
                        rs.getInt("id_carrera"), 
                        rs.getString("nombre"),
                        rs.getInt("id_facultad"));
                carreras.add(c);
            }
        }catch(SQLException e){
            e.printStackTrace();
        } 
        
        return carreras;
    }
    
}
