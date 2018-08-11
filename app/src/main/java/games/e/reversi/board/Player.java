package games.e.reversi.board;

/**
 * Created by LENOVO on 14/03/2016.
 */
public class Player
{
    private String name;
    private GamePiece gamePiece;

    public Player(String name, GamePiece gamePiece )
    {
        this.name = name;
        this.gamePiece = gamePiece;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setGamePiece(GamePiece gamePiece)
    {
        this.gamePiece = gamePiece;
    }

    public GamePiece getGamePiece()
    {
        return gamePiece;
    }
}
