package chattatt;

import java.sql.Time;

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


public class Incident {
    private String reference;
    private Time time;
    private boolean status;
}
