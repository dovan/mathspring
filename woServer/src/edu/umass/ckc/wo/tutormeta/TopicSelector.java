package edu.umass.ckc.wo.tutormeta;

import edu.umass.ckc.wo.content.Problem;
import edu.umass.ckc.wo.content.TopicIntro;
import edu.umass.ckc.wo.smgr.StudentState;
import edu.umass.ckc.wo.tutor.pedModel.EndOfTopicInfo;
import edu.umass.ckc.wo.tutor.pedModel.ProblemGrader;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * This is an abstraction that TopicSelector and CommonCoreContentMgr implement.   This allows BasePedagogicalModel to use
 * these classes interchangeably which means pedagogies can be created that simply specify whether content is topics or common core
 * and the pedagogical model just calls these methods.
 * User: marshall
 * Date: 2/25/14
 * Time: 3:46 PM
 * To change this template use File | Settings | File Templates.
 */
public interface TopicSelector {

    public  void init () ;


    public  void initializeTopic (int topicId, StudentState state)  throws Exception ;

    //  TopicIntro should be renamed to something else (e.g. Intro)  so that each implementation of this can return an appropriate Intro
    // means defining Intro interface.
    public  TopicIntro getIntro (int topicId) throws Exception;

    //  Passing the HintSelector is not wonderful.  The reason it is here is because it returns a sequence of hints which is what allows
    // the example to "play" as a problem being solved (i.e. its hints are played in sequence).
    public  Problem getExample (int topicId, HintSelector hs) throws Exception;

    // we're done with the current topic,  figure out what topic to give next.    Seems that this will need a StudentModel input
    // to support intelligent selection.
    public  int getNextTopicWithAvailableProblems (Connection conn, int curTopic, StudentState state)  throws Exception ;


    // are there problems available to be played in this topic?   This must weed out problems that have been solved recently or given
    // as examples, etc.
    public  boolean hasReadyContent (int topicId)  throws Exception ;

    // not sure this signature looks right for a general purpose interface.  It is too specific to our current topic system.
    // Currently these arguments are because the TopicSelector determines if the topic is over based on how long the student has been in
    // the topic and whether we are trying to find an easier/harder/same difficulty problem.
    public  EndOfTopicInfo isEndOfTopic(long probElapsedTime, ProblemGrader.difficulty nextDiff)  throws Exception ;

    /**
     * Return all the problems in a topic for a given class.   This means removing the classOmittedProblems for this topic.
     * @param topicId
     * @param classId
     * @param includeTestProblems
     * @return  The list of problems that are not solved, seen as an example, or turned off (status != ready), for a class/topic
     * @throws SQLException
     */
    public List<Integer> getClassTopicProblems(int topicId, int classId, boolean includeTestProblems) throws SQLException;

    // return a list of ids of problems that have been given to the student
    public List<Integer> getPracticeProblemsSeen (int topicId) throws  Exception;

    // return a list of problems that have not been solved or given as examples or omitted for this class
    public List<Integer> getUnsolvedProblems(int topicId, int classId, boolean includeTestProblems) throws Exception ;




}
