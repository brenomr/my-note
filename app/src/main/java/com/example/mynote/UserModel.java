    package com.example.mynote;

    import java.util.List;

    public class UserModel {
    String id;
    String email;
    String password;
    List<NoteModel> noteModelList;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public List<NoteModel> getNoteModelList() {
            return noteModelList;
        }

        public void setNoteModelList(List<NoteModel> noteModelList) {
            this.noteModelList = noteModelList;
        }
    }
