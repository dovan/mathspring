package edu.umass.ckc.wo.tutor.intervSel2;

import edu.umass.ckc.wo.event.tutorhut.*;
import edu.umass.ckc.wo.interventions.HighlightHintButtonIntervention;
import edu.umass.ckc.wo.smgr.SessionManager;
import edu.umass.ckc.wo.smgr.StudentState;
import edu.umass.ckc.wo.tutor.pedModel.PedagogicalModel;
import edu.umass.ckc.wo.tutormeta.Intervention;

/**
 * Created with IntelliJ IDEA.
 * User: marshall
 * Date: 11/14/13
 * Time: 12:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class HighlightHintButtonIS extends AttemptInterventionSelector {

    public HighlightHintButtonIS(SessionManager smgr, PedagogicalModel pedagogicalModel) {
        super(smgr, pedagogicalModel);
    }

    @Override
    public void init(SessionManager smgr, PedagogicalModel pedagogicalModel) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Intervention selectIntervention(AttemptEvent e) throws Exception {
        StudentState st = smgr.getStudentState();
        boolean isSolved= pedagogicalModel.isAttemptCorrect(st.getCurProblem(),e.getUserInput());
        String threshold= getParameter("threshold",this.params);
        int numMistakesAllowedBeforeHighlight=2;
        if (threshold!=null)
            numMistakesAllowedBeforeHighlight= Integer.parseInt(threshold);
        if (!isSolved && st.getNumMistakesOnCurProblem()==numMistakesAllowedBeforeHighlight && st.getNumHintsGivenOnCurProblem()==0)
            return new HighlightHintButtonIntervention();
        else return null;
    }

    @Override
    public Intervention processContinueAttemptInterventionEvent(ContinueAttemptInterventionEvent e) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Intervention processInputResponseAttemptInterventionEvent(InputResponseAttemptInterventionEvent e) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }


}
