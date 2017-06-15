package aplicacion.android.danielvm.quicktestandroid.models.android;

import org.junit.Test;

import aplicacion.android.danielvm.quicktestandroid.R;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Clase QuestionnaireTest encargada de realizar los test unitarios sobre la
 * clase Questionnaire.
 *
 * @author Daniel Puente Gabarri
 */

public class QuestionnaireTest {

    @Test
    public void questionnaireTest(){
        int idQuestionnaire = 1;
        String description = "Cuestionario de prueba";
        int imgIcon = R.mipmap.ic_icon_cuestionario;
        String courseName = "TFG";
        String clientKey = "clave";
        Questionnaire questionnaire = new Questionnaire(idQuestionnaire, description, imgIcon,courseName, clientKey);

        // Comprobamos el acceso al contenido
        assertEquals(1, questionnaire.getIdQuestionnaire());
        assertEquals("Cuestionario de prueba", questionnaire.getDescription());
        assertEquals(R.mipmap.ic_icon_cuestionario, questionnaire.getImgIcon());
        assertEquals("TFG", questionnaire.getCourseName());
        assertEquals("clave", questionnaire.getClientKey());

        // Modificamos la informacion
        questionnaire.setIdQuestionnaire(2);
        questionnaire.setDescription("Cuestionario 2");
        questionnaire.setImgIcon(R.mipmap.ic_icon_user);
        questionnaire.setCourseName("Trabajo final de grado");
        questionnaire.setClientKey("ddrq2");

        // Comprobamos como la informacion a cambiado
        assertNotEquals(1, questionnaire.getIdQuestionnaire());
        assertNotEquals("Cuestionario de prueba", questionnaire.getDescription());
        assertNotEquals(R.mipmap.ic_icon_cuestionario, questionnaire.getImgIcon());
        assertNotEquals("TFG", questionnaire.getCourseName());
        assertNotEquals("clave", questionnaire.getClientKey());

        // Comprobamos la nueva informacion
        assertEquals(2, questionnaire.getIdQuestionnaire());
        assertEquals("Cuestionario 2", questionnaire.getDescription());
        assertEquals(R.mipmap.ic_icon_user, questionnaire.getImgIcon());
        assertEquals("Trabajo final de grado", questionnaire.getCourseName());
        assertEquals("ddrq2", questionnaire.getClientKey());

    }
}
