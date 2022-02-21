package br.com.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UsuarioAdm {
        //==================== Atributos da classe ====================================================
        private String id;
        private String nome;
        private String email;
        private String senha;
        // ======= Construtor =========================================================================
        public UsuarioAdm(String nome, String email, String senha){
            this.nome = nome;
            this.email = email;
            this.senha = senha;
        }

        public UsuarioAdm(){}
        //======= Setter e Getter =====================================================================
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }    public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getSenha() {
            return senha;
        }

        public void setSenha(String senha) {
            this.senha = senha;
        }

        public void Salvar(){
          DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
          databaseReference.child("Usuarios").child(getId()).setValue(this);
        }
}
