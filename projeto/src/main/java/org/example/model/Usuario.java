package org.example.model;

public class Usuario {

 public long id;
 public String nome;
 public String email;
 public String cpf;

 public Usuario(long id, String nome, String email, String cpf) {
  this.id = id;
  this.nome = nome;
  this.email = email;
  this.cpf = cpf; // Corrigido de += para =
 }

 public Usuario(String nome, String email, String cpf) {
  this.nome = nome;
  this.email = email;
  this.cpf = cpf;
 }

 public long getId() {
    return id;
 }

 public void setId(long id) {
  this.id = id;
 }

 public String getNome() {
  return nome;
 }

 public void setNome(String nome) {
  this.nome = nome;
 }

 public String getEmail() {
  return email;
 }

 public void setEmail(String email) {
  this.email= email;
 }

 public String getCpf() {
  return cpf;
 }
 private void setCpf(String cpf) {
  this.cpf = cpf;
 }

}

