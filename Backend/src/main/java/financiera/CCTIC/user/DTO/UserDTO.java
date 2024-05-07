    package financiera.CCTIC.user.DTO;

    import financiera.CCTIC.person.model.Person;
    import financiera.CCTIC.person.model.PersonDTO;
    import financiera.CCTIC.rol.model.Rol;


    import java.util.Set;

    public class UserDTO {
        private long id;
        private Person person;
        private String username;
        private String password;
        private String code;
        private Set<Rol> authorities;
        private int status;
        private PersonDTO personDTO;

        public UserDTO() {
        }

        public UserDTO(long id, String password, String code, Person person, Set<Rol> authorities, int status) {
            this.id = id;
            this.person = person;
            this.password = password;
            this.code = code;
            this.authorities = authorities;
            this.status = status;
            this.username = person.getEmail();
        }

        public UserDTO(String password, String code, Person person, Set<Rol> authorities, int status) {
            this.password = password;
            this.person = person;
            this.username = person.getEmail();
            this.code = code;
            this.authorities = authorities;
            this.status = status;
        }

        public UserDTO(String username, String password, String code, Set<Rol> authorities, int status, PersonDTO personDTO) {
            this.username = username;
            this.password = password;
            this.code = code;
            this.authorities = authorities;
            this.status = status;
            this.personDTO = personDTO;
        }
        public UserDTO(String username, String password, String code, int status, Person personDTO) {
            this.username = username;
            this.password = password;
            this.code = code;
            this.status = status;
            this.person = personDTO;
        }
        public UserDTO(String username, String password, String code, Set<Rol> authorities, int status, Person personDTO) {
            this.username = username;
            this.password = password;
            this.code = code;
            this.authorities = authorities;
            this.status = status;
            this.person = personDTO;
        }
        public UserDTO(long id, String password, String code, String code1, Set<Rol> authorities, int status, String username) {
            this.password = password;
            this.username = username;
            this.code = code1;
            this.authorities = authorities;
            this.status = status;

        }

        public UserDTO(long id, String username, String password, String code, int status) {
            this.id = id;
            this.username = username;
            this.password = password;
            this.code = code;
            this.status = status;
        }

        public UserDTO(long id, String username, String password, Set<Rol> authorities, int status) {
            this.id = id;
            this.username = username;
            this.password = password;
            this.authorities = authorities;
            this.status = status;
        }

        public PersonDTO getPersonDTO() {
            return personDTO;
        }

        public void setPersonDTO(PersonDTO personDTO) {
            this.personDTO = personDTO;
        }
        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public Person getPerson() {
            return person;
        }

        public void setPerson(Person person) {
            this.person = person;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public Set<Rol> getAuthorities() {
            return authorities;
        }

        public void setAuthorities(Set<Rol> authorities) {
            this.authorities = authorities;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        @Override
        public String toString() {
            return "UserDTO{" +
                    "id=" + id +
                    ", person=" + person +
                    ", username='" + username + '\'' +
                    ", password='" + password + '\'' +
                    ", code='" + code + '\'' +
                    ", authorities=" + authorities +
                    ", status=" + status +
                    ", personDTO=" + personDTO +
                    '}';
        }
    }
