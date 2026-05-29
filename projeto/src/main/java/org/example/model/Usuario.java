package org.example.model;

public class Usuario {

 public Long id;
 public String nome;
 public String email;
 public String cpf;
 public String periodoAtual;

 public Usuario(Long id, String nome, String email, String cpf, String periodoAtual) {
  this.id = id;
  this.nome = nome;
  this.email = email;
  this.cpf = cpf;
  this.periodoAtual = periodoAtual;
 }

 public Usuario(String nome, String email, String cpf) {
  this.nome = nome;
  this.email = email;
  this.cpf = cpf;
 }

 public String getPeriodoAtual() {
  return periodoAtual;
 }

 public void setPeriodoAtual(String periodoAtual) {
  this.periodoAtual = periodoAtual;
 }

 public long getId() {
    return id;
 }

 public void setId(Long id) {
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

