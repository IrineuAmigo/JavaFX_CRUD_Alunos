package com.template;

public class AlunosDTO {
    private int id;
    private String nome;
    private String email;
    private String endereco;
    private String telefone;
    private String cidade;
    private String turno; // Nova propriedade (UX)

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }

    public String getTurno() { return turno; }
    public void setTurno(String turno) { this.turno = turno; }
}