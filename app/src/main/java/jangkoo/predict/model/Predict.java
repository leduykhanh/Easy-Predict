package jangkoo.predict.model;

/**
 * Created by User on 2/9/2017.
 */

public class Predict {
    public String name, _id;
    public Team firstTeam,secondTeam;
    public int firstScore,secondScore,firstTeamBets,secondTeamBets,drawBets,result;
    public Predict(String _id, String name, Team firstTeam, Team secondTeam, int firstScore){
        this._id = _id;
        this.name = name;
        this.firstTeam = firstTeam;
        this.secondTeam = secondTeam;
        this.firstScore = firstScore;
    }
}

