package biz.argirc.Magick.ARGAntiPirate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.avaje.ebean.validation.NotNull;

@Entity()
@Table(name = "playerRanks")
public class RankData {
	@Id
	private int		id;

	@NotNull
	private String	playerName;

	@NotNull
	private int		rank;

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public int getRank() {
		return rank;
	}

}