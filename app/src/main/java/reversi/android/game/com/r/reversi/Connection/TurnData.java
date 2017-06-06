package reversi.android.game.com.r.reversi.Connection;

import org.json.JSONException;
import org.json.JSONObject;

import reversi.android.game.com.r.reversi.R;
import reversi.android.game.com.r.reversi.utility.App;

/**
 * Created by LENOVO on 16/03/2016.
 */
public class TurnData
{
    private int row;
    private int col;
    private String type;
    private String actionType;
    private String value;
    private JSONObject json;

    public TurnData(String jsonString)
    {
        try {
            json  = new JSONObject(jsonString);
            row = json.getInt(App.Instance.getString(R.string.json_row));
            col = json.getInt(App.Instance.getString(R.string.json_col));
            actionType = json.getString(App.Instance.getString(R.string.json_actionType));
            type = json.getString(App.Instance.getString(R.string.json_type));
            value = json.getString(App.Instance.getString(R.string.json_value));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public TurnData(String type, String actionType,String value)
    {
        this.actionType = actionType;
        this.type = type;
        this.value = value;
        this.row = 0;
        this.col = 0;
    }

    public TurnData(int row, int col, String type, String actionType)
    {
        this.col = col;
        this.row = row;
        this.type = type;
        this.actionType = actionType;
        this.value = "";
    }

    public int getCol()
    {
        return col;
    }

    public int getRow()
    {
        return row;
    }

    public String getType()
    {
        return type;
    }

    public String getActionType()
    {
        return actionType;
    }

    public String getJsonStr()
    {
        json = new JSONObject();
        try {
            json.put(App.Instance.getString(R.string.json_type), type);
            json.put(App.Instance.getString(R.string.json_actionType), actionType);
            json.put(App.Instance.getString(R.string.json_row), row);
            json.put(App.Instance.getString(R.string.json_col), col);
            json.put(App.Instance.getString(R.string.json_value), value);
        } catch (JSONException e) {}
        return json.toString();
    }

    public String getValue()
    {
        return value;
    }
}
