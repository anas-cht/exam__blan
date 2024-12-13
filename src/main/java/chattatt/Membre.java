package chattatt;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Membre {
    private String identifiant;
    private String nom;
    private String prenom;
    private String email;
    private String phone;

    @Override
    public boolean equals(Object o){
        if(o instanceof Membre){
            if(this == o) return true;
            Membre mbr = (Membre)o;
            return mbr.identifiant.equals(this.identifiant);
        }
        return false;
    }

    @Override
    public int hashCode(){
        return identifiant.hashCode();
    }

}

